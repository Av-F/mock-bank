import java.util.ArrayList;
import java.util.List;

public class Customer {
    //Variables
    private String name;
    private int id;
    private List<Account> accounts = new ArrayList<>();
    // Constructor
    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
    }
    // Getters
    public String getName() {
        return name;
    }   
    public int getID() {
        return id;
    }   
    public List<Account> getAccounts() {
        return accounts;
    }   
    
    // Add an account to the customer's list of accounts
    public void addAccount(Account account) {
        accounts.add(account);
    }    

}
