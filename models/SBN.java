package models;

public class SBN {
    private String name;
    private double interestRate;
    private int maturityPeriod;
    private String maturityDate;
    private double nationalQuota;

    public SBN(String name, double interestRate, int maturityPeriod, String maturityDate, double nationalQuota) {
        this.name = name;
        this.interestRate = interestRate;
        this.maturityPeriod = maturityPeriod;
        this.maturityDate = maturityDate;
        this.nationalQuota = nationalQuota;
    }

    public String getName() {
        return name;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getMaturityPeriod() {
        return maturityPeriod;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public double getNationalQuota() {
        return nationalQuota;
    }

    public void setNationalQuota(double nationalQuota) {
        this.nationalQuota = nationalQuota;
    }
}