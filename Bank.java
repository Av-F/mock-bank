import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
    private final ConcurrentMap<String, Customer> customers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

    public void addCustomer(Customer customer) {
        customers.put(String.valueOf(customer.getID()), customer);
    }

    public void addAccount(Account account) {
        accounts.put(account.getID(), account);
        account.getCustomer().addAccount(account);
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    // Thread-safe transfer with lock ordering to avoid deadlocks
    public boolean transfer(String fromId, String toId, double amount) {
        if (fromId.equals(toId)) throw new IllegalArgumentException("Same account");
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);

        if (from == null || to == null) throw new IllegalArgumentException("Account not found");

        // Lock ordering by accountId to prevent deadlock
        Account firstLock = from.getID().compareTo(to.getID()) < 0 ? from : to;
        Account secondLock = (firstLock == from) ? to : from;

        firstLock.getLock().lock();
        secondLock.getLock().lock();
        try {
            if (from.withdraw(amount)) {
                to.deposit(amount);
                return true;
            }
            return false;
        } finally {
            secondLock.getLock().unlock();
            firstLock.getLock().unlock();
        }
    }

    public boolean pay(String fromId, double amount, String vendor) {
        Account from = accounts.get(fromId);
        if (from == null) throw new IllegalArgumentException("Account not found");
        boolean success = from.withdraw(amount);
        if (success) {
            System.out.println("Paid $" + amount + " to vendor: " + vendor);
        }
        return success;
    }

    public void printCustomerAccounts(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        System.out.println("Accounts for " + customer.getName() + ":");
        for (Account acc : customer.getAccounts()) {
            System.out.println(" - " + acc.getID() + " | Balance: $" + acc.getBalance());
        }
    }
}