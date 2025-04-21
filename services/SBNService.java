package services;

import models.*;

public class SBNService {
    public boolean checkNominalInvestasi(SBN sbnToBuy, double nominal) {
        if (nominal <= 0) {
            System.out.println("❌ Nominal harus lebih dari 0.");
            return false;
        }

        if (nominal > sbnToBuy.getKuotaNasional()) {
            System.out.printf("❌ Kuota tidak mencukupi. Maksimum pembelian: %.2f\n", sbnToBuy.getKuotaNasional());
            return false;
        }
        return true;
    }
}
