package services;

import data.DataStore;
import models.*;

public class SBNService {
    public SBN getSBNByName(String sbnName) {
        for (SBN sbn: DataStore.sbn) {
            if (sbn.getName().equalsIgnoreCase(sbnName)) {
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

    public CustomerSBN getCustomerSBNBySBNName(String sbnName) {
        for (CustomerSBN customerSBN : DataStore.customerSBN) {
            if (customerSBN.getSBN().getName().equalsIgnoreCase(sbnName)) {
                return customerSBN;
            }
        }
        return null;
    }

    public void addNominalInvestasi(CustomerSBN customerSBN, double nominal) {
        customerSBN.setNominalInvestasi(customerSBN.getNominalInvestasi() + nominal);
    }
}
