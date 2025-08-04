# mock-bank

An in-memory demonstration of a banking system using threads. More specifically, Java's ExecutorService in the java.util.concurrent library, which relies on locking resources to avoid deadlocks. 
The demonstration shows payment and transfers across accounts, as well as printing out reports on each transfer/payment. At the end, all accounts and customers are listed.

The programs include a class for the account, which includes functions for transferring and paying money, a customer class that stores customer information, a bank class that facilitates the banking functions by using both account and customer classes, and a demo class that shows how the programs work together. 
