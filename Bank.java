import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
    // Use a concurrent map to store the customers and accounts
    private final ConcurrentMap<String, Customer> customers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

    // Function to add a customer with their ID as the key and custoemr as the value
    public void addCustomer(Customer customer) {
        customers.put(String.valueOf(customer.getID()), customer);
    }
    // Add an account using the name of the account as the key and the account as the value
    public void addAccount(Account account) {
         accounts.put(account.getID(), account);
        account.getCustomer().addAccount(account);
    }
    // Get the account id from a specific account in the list of accounts
    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }  
    // Get the customer from a spicific customer in the list of customers
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }
    // Function to get all of the customers
    public ConcurrentMap<String, Customer> getCustomers() {
        return customers;
    }   

    // Thread-safe transfer with lock ordering to avoid deadlocks
    public boolean transfer(String startId, String endId, double amount) {
        if (startId.equals(endId)) throw new IllegalArgumentException("Same account");
        // Save the accounts as variales
        Account start = accounts.get(startId);
        Account end = accounts.get(endId);
        // Check if the accounts exist
        if (start == null || end == null) throw new IllegalArgumentException("Account not found");

        // Lock ordering by accountId to prevent deadlock
        Account firstLock = start.getID().compareTo(end.getID()) < 0 ? start : end; // if start's ID is less than end's ID, lock start first
        Account secondLock = (firstLock == start) ? end : start; //if the firstlock is start, then secondLock is end, otherwise it's the opposite
        // Lock the accounts in order
        firstLock.getLock().lock();
        secondLock.getLock().lock();
        // Java threading requires try catchs 
        try {
            if (start.withdraw(amount)) { //if withdrawing from start is successful, then deposit into end
                end.deposit(amount);
                return true;
            }
            return false; //otherwise return false
        } finally {
            // Unclock accounts in reverse order to keep the order
            secondLock.getLock().unlock();
            firstLock.getLock().unlock();
        }
    }
    // Function to pay someone
    public boolean pay(String fromId, double amount, String target) {
        // Save the account as a variable
        Account from = accounts.get(fromId);
        // If the account is null, then throw an exception
        if (from == null) {
             throw new IllegalArgumentException("Account not found");
        }
        // Otherwise withdraw from the account
        boolean success = from.withdraw(amount);
        if (success) { //if we have a successful withdraw, then print the message
            System.out.println("Paid $" + amount + " to target: " + target);
        }
        return success; //return if the withdraw was successful or not
    }
    // Function to print the customer's accounts
    public void printCustomerAccounts(String customerId) {
        // Save the customer as a variable
        Customer customer = customers.get(customerId);
        // IF the customer doesnt exist, print a fail message and return out
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        // Otherwise, print the customer's accounts
        System.out.println("Accounts for " + customer.getName() + ":");
        // I use a for each loop to iterate through the customer's accounts 
        for (Account acc : customer.getAccounts()) {
            System.out.println(" * " + acc.getID() + " | Balance: $" + acc.getBalance());
        }
    }
}