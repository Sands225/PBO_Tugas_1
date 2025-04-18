package models;

public class Saham {
    String code;
    String company;
    double price;

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