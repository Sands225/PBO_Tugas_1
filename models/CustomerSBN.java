package models;

public class CustomerSBN {
    private String customerName;
    private SBN sbn;
    private double nominalInvestasi;

    public CustomerSBN(String customerName, SBN sbn, double nominalInvestasi) {
        this.customerName = customerName;
        this.sbn = sbn;
        this.nominalInvestasi = nominalInvestasi;
    }

    public String getCustomerName() {
        return customerName;
    }

    public SBN getSBN() {
        return sbn;
    }

    public double getNominalInvestasi() {
        return nominalInvestasi;
    }
}
