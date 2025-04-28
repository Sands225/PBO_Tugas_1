package services;

import data.DataStore;
import models.*;

public class SBNService {
    public static SBN getSBNByName(String sbnName) {
        for (SBN sbn: DataStore.sbn) {
            if (sbn.getName().equalsIgnoreCase(sbnName)) {
                return sbn;
            }
        }
        return null;
    }

    public static boolean checkNominalInvestasi(SBN sbnToBuy, double nominal) {
        if (nominal <= 0) {
            System.out.println("Nominal harus lebih dari 0.");
            return false;
        }

        if (nominal > sbnToBuy.getNationalQuota()) {
            System.out.printf("Kuota tidak mencukupi. Maksimum pembelian: %.2f\n", sbnToBuy.getNationalQuota());
            return false;
        }
        return true;
    }

    public static CustomerSBN getCustomerSBNBySBNName(String sbnName) {
        for (CustomerSBN customerSBN : DataStore.customerSBN) {
            if (customerSBN.getSBN().getName().equalsIgnoreCase(sbnName)) {
                return customerSBN;
            }
        }
        return null;
    }

    public static void addNominalInvestasi(CustomerSBN customerSBN, double nominal) {
        customerSBN.setNominalInvestasi(customerSBN.getNominalInvestasi() + nominal);
    }
}
