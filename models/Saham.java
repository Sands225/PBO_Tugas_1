package models;

public class Saham {
    private String code;
    private String company;
    private double price;

    public Saham(String code, String company, double price) {
        this.code = code;
        this.company = company;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getCompany() {
        return company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}