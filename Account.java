import java.util.concurrent.locks.ReentrantLock;

public class Account {
    
    private String id;
    private String name;
    private Customer customer; 
    private double balance;
    private ReentrantLock lock = new ReentrantLock(); 
    // Constructor
    public Account(String id, String name, Customer customer, double balance) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.balance = balance;
    }
    // Getters
    public String getID() {
        return id;
    }
    
    public String getName() { 
        return name; 
    }
    // return customer 
    public Customer getCustomer() { 
        return customer; 
    }
    // Since balance is something that can change often, we should use a lock to ensure thread safety
    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
    // Banking functions
    public void deposit(double amount) {
        // No debt is allowed, so we check if the amount is positive
        if (amount <= 0) throw new IllegalArgumentException("You can't deposit negative money");
        lock.lock(); //Since this has things to do with balance, we need to lock it at times
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }
    // Function to withdraw money from the account 
    public boolean withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Can't withdraw negative money");
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
    // Function to get the lock for thread-safe operations
    public ReentrantLock getLock() {
        return lock;
    }
}
