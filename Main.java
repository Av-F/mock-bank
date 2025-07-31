// Imports to do threads
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main (String[] args) {
        // Create bank instance 
        Bank bank = new Bank(); 
        // Create the customers in the bank
        Customer c1 = new Customer("Avi", "1");
        Customer c2 = new Customer("Barry", "2");    
        // Add customers to the bank which is saved as a map
        bank.addCustomer(c1);
        bank.addCustomer(c2);   
        //Then create and add in their accounts 
        Account a1 = new Account("1", "Avi's Savings", c1, 1000.0);
        Account a2 = new Account("2", "Barry's Checking", c2, 500.0);
        bank.addAccount(a1);
        bank.addAccount(a2);

        //Create an executorservice for the threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Example of a transfer
         Runnable transferTask1 = () -> {
            for (int i = 0; i < 10; i++) {
                boolean success = bank.transfer("1", "2", 50.0);
                if (success) {
                    System.out.println(Thread.currentThread().getName() + " transferred $50 from Avi to Barry");
                }
            }
        };

        // Example of payment
        //Make a boolean that checks if the payment was sucessful
        Runnable paymentTask = () -> {
        for(int i = 0; i < 3; i++) {
        boolean payResult = bank.pay("1", 50.0, "Some Service");
        if(payResult) {
            System.out.println(Thread.currentThread().getName() +  "Payment successful");
        }
        else {
            System.out.println( Thread.currentThread().getName()+ "Payment failed from Avi's account.");
        }
        System.out.println("Avi's balance after payment: " + a1.getBalance());
            }    
        };
        
        // We can add more customers and accounts even after 
        Runnable addTask = () -> {
        Customer c3 = new Customer("Charles", "3");
        bank.addCustomer(c3);
        Account a3 = new Account("3", "Charles' Investment", c3, 500.0);
        bank.addAccount(a3);
        System.out.println("Charles' balance: " + a3.getBalance());
        };
        
        //Submit tasks to pool
        executor.submit(transferTask1);
        executor.submit(paymentTask);
        executor.submit(addTask);

        // Once tasks are done, shutdown the executor
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted while waiting for termination.");
            Thread.currentThread().interrupt();
        }

        // List all customers and their accounts
        for (Customer customer : bank.getCustomers().values()) {
            System.out.println("Customer: " + customer.getName() + ", ID: " + customer.getID());
            for (Account account : customer.getAccounts()) {
                System.out.println("  Account: " + account.getName() + ", Balance: " + account.getBalance());
            }
        }

        // Print accounts for a specific customer
        Customer specificCustomer = bank.getCustomer("1");  
        if (specificCustomer != null) {
            System.out.println("Accounts for " + specificCustomer.getName() + ":");
            for (Account account : specificCustomer.getAccounts()) {
                System.out.println("  Account ID: " + account.getID() + ", Name: " + account.getName() + ", Balance: " + account.getBalance());
            }
        } else {
            System.out.println("Customer not found.");
        }
    }   
}