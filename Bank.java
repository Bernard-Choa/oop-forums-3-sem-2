package pls.java;
import java.util.ArrayList;

public class Bank {
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private int numOfCustomers = 0;
    private String bankName;

    public Bank(String bName) {
        this.bankName = bName;
    }
    public void addCustomer(String f, String l) {
        Customer newCustomer = new Customer(f, l);
        this.customers.add(newCustomer);
        numOfCustomers++;
    }
    public String getBankName() {
        return this.bankName;
    }
    public int getNumOfCustomers() {
        return numOfCustomers;
    }
    public Customer getCustomer(int index) {
        return this.customers.get(index);
    }
    public String toString() {
        String text = this.customers.get(0).getFirstName()+" "+this.customers.get(0).getLastName();
        for (int i=1;i<getNumOfCustomers();i++) {
            text += ", " + this.customers.get(i).getFirstName() + " " + this.customers.get(i).getLastName();
        }
        return this.bankName+" - "+text+" - "+this.numOfCustomers;
    }
}
