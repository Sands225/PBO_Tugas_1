package models;

public class CustomerSBN {
    String customerName;
    SBN sbn;

    public CustomerSBN(String customerName, SBN sbn) {
        this.customerName = customerName;
        this.sbn = sbn;
    }

    public String getCustomerName() {
        return customerName;
    }

    public SBN getSBN() {
        return sbn;
    }
}
