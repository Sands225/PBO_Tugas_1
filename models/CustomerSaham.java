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

    public double subtractQuantity(double amount) {
        if (amount > quantity) {
            System.out.println("Jumlah saham tidak mencukupi");
        } else {
            quantity -= amount;
        }
        return amount;
    }

    public void addQuantity(double amount) {
        quantity += amount;
    }
}
