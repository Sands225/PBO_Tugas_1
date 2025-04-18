package models;

public class SuratBerhargaNegara {
    String name;
    double interestRate;
    int jangkaWaktu; // in years or months
    String tanggalJatuhTempo; // could use java.time.LocalDate for better handling
    int kuotaNasional;

    public SuratBerhargaNegara(String name, double interestRate, int jangkaWaktu, String tanggalJatuhTempo, int kuotaNasional) {
        this.name = name;
        this.interestRate = interestRate;
        this.jangkaWaktu = jangkaWaktu;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.kuotaNasional = kuotaNasional;
    }
}