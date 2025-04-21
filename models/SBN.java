package models;

public class SBN {
    String name;
    double interestRate;
    int jangkaWaktu; // in years or months
    String tanggalJatuhTempo; // could use java.time.LocalDate for better handling
    int kuotaNasional;

    public SBN(String name, double interestRate, int jangkaWaktu, String tanggalJatuhTempo, int kuotaNasional) {
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

    public int getKuotaNasional() {
        return kuotaNasional;
    }
}