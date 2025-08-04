import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void addCustomer() {
        Customer c1 = new Customer("Avi", "1");
        Customer c2 = new Customer("Barry", "2");
        Bank bank = new Bank();
        bank.addCustomer(c1);
        bank.addCustomer(c2);
        // Check if customers are added correctly
        assertEquals(c1, bank.getCustomer("1"));
        assertEquals(c2, bank.getCustomer("2"));
    }

    @Test
    void addAccount() {
        Account a1 = new Account("1", "Avi's Savings", new Customer("Avi", "1"), 1000.0);
        Account a2 = new Account("2", "Barry's Checking", new Customer("Barry", "2"), 500.0);
        Bank bank = new Bank();
        bank.addAccount(a1);
        bank.addAccount(a2);
        // Check if accounts are added correctly
        assertEquals(a1, bank.getAccount("1"));
        assertEquals(a2, bank.getAccount("2"));
    }

    @Test
    void getAccount() {
        Account a1 = new Account("1", "Avi's Savings", new Customer("Avi", "1"), 1000.0);
        Bank bank = new Bank();
        bank.addAccount(a1);
        // Check if the account can be retrieved correctly
        assertEquals(a1, bank.getAccount("1"));
    }

    @Test
    void getCustomer() {
        Customer c1 = new Customer("Avi", "1");
        Bank bank = new Bank();
        bank.addCustomer(c1);
        // Check if the customer can be retrieved correctly
        assertEquals(c1, bank.getCustomer("1"));
    }

    @Test
    void getCustomers() {
        Customer c1 = new Customer("Avi", "1");
        Customer c2 = new Customer("Barry", "2");
        Bank bank = new Bank();
        bank.addCustomer(c1);
        bank.addCustomer(c2);
        // Check if all customers can be retrieved correctly
        assertEquals(2, bank.getCustomers().size());
        // Check if the keys are correct (keys correspond to the customer)
        assertTrue(bank.getCustomers().containsKey("1"));
        assertTrue(bank.getCustomers().containsKey("2"));

    }
    @Test
    void transfer() {
        Account a1 = new Account("1", "Avi's Savings", new Customer("Avi", "1"), 1000.0);
        Account a2 = new Account("2", "Barry's Checking", new Customer("Barry", "2"), 500.0);
        Bank bank = new Bank();
        bank.addAccount(a1);
        bank.addAccount(a2);

        // Test a successful transfer
        assertTrue(bank.transfer("1", "2", 100.0));
        assertEquals(900.0, a1.getBalance());
        assertEquals(600.0, a2.getBalance());

    }
    @Test
    void transferfailTest() {
        Account a1 = new Account("1", "Avi's Savings", new Customer("Avi", "1"), 1000.0);
        Account a2 = new Account("2", "Barry's Checking", new Customer("Barry", "2"), 500.0);
        Bank bank = new Bank();
        bank.addAccount(a1);
        bank.addAccount(a2);

        // Test a failed transfer due to insufficient funds
        assertFalse(bank.transfer("1", "2", 1000.1));
    }

    @Test
    void pay() {
        Account a = new Account("1", "Avi's Savings", new Customer("Avi", "1"), 1000.0);
        Bank bank = new Bank();
        bank.addAccount(a);

        // Test a successful payment
        assertTrue(bank.pay("1", 100.0, "Friendly's"));
        assertEquals(900.0, a.getBalance());
    }

    @Test
    void payfail() {
        Account a = new Account("1", "Avi's Savings", new Customer("Avi", "1"), 800.0);
        Bank bank = new Bank();
        bank.addAccount(a);
        // Test a failed payment due to insufficient funds
        assertFalse(bank.pay("1", 1000.0, "Friendly's"));
        assertEquals(800.0, a.getBalance());
    }


    @Test
    void printCustomerAccounts() {
        Customer c1 = new Customer("Avi", "1");
        Bank bank = new Bank();
        bank.addCustomer(c1);
        Account a1 = new Account("1", "Avi's Savings", c1, 1000.0);
        bank.addAccount(a1);
        Account a2 = new Account("2", "Avi's Checking", c1, 500.0);
        bank.addAccount(a2);
        // Check if the customer's accounts are printed correctly
        // However, I cannot directly test print statements, so I'll check the accounts list
        assertEquals(2, c1.getAccounts().size());
        assertTrue(c1.getAccounts().contains(a1));
        assertTrue(c1.getAccounts().contains(a2));
    }
}