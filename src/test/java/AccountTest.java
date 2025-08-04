import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void getID() {
    Account a1 = new Account("1", "my savings", new Customer("Avi", "1"), 20.0);
    assertEquals("1", a1.getID(), "Account ID should be '1'");
    }

    @Test
    void getName() {
        Account a1 = new Account("1", "my savings", new Customer("Avi", "1"), 20.0);
        assertEquals("my savings", a1.getName(), "name should be 'my savings'");
    }

    @Test
    void getCustomer() {
        Account a1 = new Account("1", "my savings", new Customer("Avi", "1"), 20.0);
        Customer expected = new Customer("Avi", "1");
        assertEquals(expected.getName(), a1.getCustomer().getName(), "Customer should be Avi");
    }

    @Test
    void getBalance() {
        Account a1 = new Account("1", "my savings", new Customer("Avi", "1"), 20.0);
        assertEquals(20.0, a1.getBalance(), "Balance should be 20.0");
    }

    @Test
    void deposit() {
        Account a1 = new Account("1", "my savings", new Customer("Avi", "1"), 20.0);
        a1.deposit(30.0);
        assertEquals(50.0, a1.getBalance(), "Balance should be 50.0 after depositing 30");
    }

    @Test
    void withdraw() {
        Account a1 = new Account("1", "my savings", new Customer("Avi", "1"), 20.0);
        boolean result = a1.withdraw(10.0);
        assertTrue(result, "Withdraw of 10 should be successful");
    }
}