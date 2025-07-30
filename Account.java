import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private  String ID;
    private  Customer customer; 
    private double balance;
    private final ReentrantLock lock = new ReentrantLock(); 

    public Account(String accountId, Customer customer, double initialBalance) {
        this.ID = accountId;
        this.customer = customer;
        this.balance = initialBalance;
    }

    public String getID() { 
        return ID; 
    }

    public Customer getCustomer() { 
        return customer; 
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be > 0");
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw must be > 0");
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

    public ReentrantLock getLock() {
        return lock;
    }
}
