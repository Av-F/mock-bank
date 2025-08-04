import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void getName() {
        Customer c1 = new Customer("John Doe", "12345");
        assertEquals("John Doe", c1.getName(), "The name should match the one provided during construction.");
    }

    @Test
    void getID() {
        Customer c1 = new Customer("Steven", "2468");
        assertEquals("2468", c1.getID(), "The ID should match the one provided during construction.");
    }

    @Test
    void getAccounts() {
    Customer c1 = new Customer("Jane", "13579");
        assertTrue(c1.getAccounts().isEmpty(), "New customer should have an empty account list initially.");
    }

    @Test
    void addAccount() {
    Customer c1 = new Customer("Emily", "98765");
        Account account = new Account("001", "Savings", c1, 1000.0);
        c1.addAccount(account);
        assertTrue(c1.getAccounts().contains(account));
    }
}