package models;

public class CustomerSaham {
    private String customerName;
    private Saham saham;
    private double quantity;

    public CustomerSaham(String customerName, Saham saham, double quantity) {
        this.customerName = customerName;
        this.saham = saham;
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Saham getSaham() {
        return saham;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
