package services;

import models.Customer;
import models.CustomerSaham;
import data.DataStore;

public class SahamService {
    public CustomerSaham checkCustomerSahamCode(String sahamCode) {
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if (customerSaham.getSaham().getCode().equals(sahamCode)) {
                return customerSaham;
            }
        }
        return null;
    }

    public boolean checkCustomerSahamQuantity(double inputQuantity, double custSahamQuantity) {
        return inputQuantity > 0 && inputQuantity <= custSahamQuantity;
    }

    public void customerSellSaham(String sahamCode, double sahamQuantity, Customer customer) {
        CustomerSaham toRemove = null;

        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if (customerSaham.getSaham().getCode().equals(sahamCode)) {
                customerSaham.setQuantity(customerSaham.getQuantity() - sahamQuantity);

                System.out.println("Selamat! Anda berhasil menjual " + sahamQuantity + " saham " + sahamCode);

                if (customerSaham.getQuantity() > 0) {
                    System.out.printf("Sisa saham Anda di %s: %.2f lembar\n", sahamCode, customerSaham.getQuantity());
                } else {
                    toRemove = customerSaham;
                }
            }
        }

        if (toRemove != null) {
            DataStore.customerSaham.remove(toRemove);
            System.out.println("Anda sudah tidak memiliki saham " + sahamCode + " di portofolio Anda.");
        }
    }
}
