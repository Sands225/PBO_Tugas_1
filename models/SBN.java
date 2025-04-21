package models;

public class SBN {
    String name;
    double interestRate;
    int jangkaWaktu; // in years or months
    String tanggalJatuhTempo; // could use java.time.LocalDate for better handling
    double kuotaNasional;

    public SBN(String name, double interestRate, int jangkaWaktu, String tanggalJatuhTempo, double kuotaNasional) {
        this.name = name;
        this.interestRate = interestRate;
        this.jangkaWaktu = jangkaWaktu;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.kuotaNasional = kuotaNasional;
    }

    public String getName() {
        return name;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getJangkaWaktu() {
        return jangkaWaktu;
    }

    public String getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    public double getKuotaNasional() {
        return kuotaNasional;
    }

    public void setKuotaNasional(double kuotaNasional) {
        this.kuotaNasional = kuotaNasional;
    }
}