package services;

import data.DataStore;
import models.*;

public class SBNService {
    public SBN getSBNByName(String sbnName) {
        for (SBN sbn: DataStore.sbn) {
            if (sbn.getName().equals(sbnName)) {
                return sbn;
            }
        }
        return null;
    }

    public boolean checkNominalInvestasi(SBN sbnToBuy, double nominal) {
        if (nominal <= 0) {
            System.out.println("Nominal harus lebih dari 0.");
            return false;
        }

        if (nominal > sbnToBuy.getKuotaNasional()) {
            System.out.printf("Kuota tidak mencukupi. Maksimum pembelian: %.2f\n", sbnToBuy.getKuotaNasional());
            return false;
        }
        return true;
    }

    public double calculateInterest(SBN sbnToSimulate, double nominal) {
        double interestRate = sbnToSimulate.getInterestRate();
        int jangkaWaktu = sbnToSimulate.getJangkaWaktu();

        double kuponBulanan = (interestRate / 12) * 0.9 * nominal;
//        int totalBulan = jangkaWaktu * 12;
//        double totalBunga = kuponBulanan;
        return kuponBulanan;
    }
}
