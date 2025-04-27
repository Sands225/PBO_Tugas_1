package models;

public class CustomerSaham {
    private String customerName;
    private Saham saham;
    private double quantity;
    private double totalPurchaseValue;

    public CustomerSaham(String customerName, Saham saham, double quantity, double totalPurchaseValue) {
        this.customerName = customerName;
        this.saham = saham;
        this.quantity = quantity;
        this.totalPurchaseValue = totalPurchaseValue;
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

    public double getTotalPurchaseValue() {
        return totalPurchaseValue;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setTotalPurchaseValue(double newValue) {
        this.totalPurchaseValue = newValue;
    }
}
