package models;

public class CustomerSBN {
    private final String customerName;
    private final SBN sbn;
    private double investmentAmount;

    public CustomerSBN(String customerName, SBN sbn, double investmentAmount) {
        this.customerName = customerName;
        this.sbn = sbn;
        this.investmentAmount = investmentAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public SBN getSBN() {
        return sbn;
    }

    public double getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(double investmentAmount) {
        this.investmentAmount = investmentAmount;
    }
}
