package services;

import models.CustomerSaham;
import models.Saham;
import data.DataStore;

public class SahamService {
    public CustomerSaham getCustomerSahamBySahamCode(String sahamCode) {
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if (customerSaham.getSaham().getCode().equalsIgnoreCase(sahamCode)) {
                return customerSaham;
            }
        }
        return null;
    }

    public boolean checkCustomerSahamQuantity(double inputQuantity, double custSahamQuantity) {
        return inputQuantity > 0 && inputQuantity <= custSahamQuantity;
    }

    public Saham getSahamByCode(String sahamCode) {
        for (Saham saham: DataStore.saham) {
            if (saham.getCode().equalsIgnoreCase(sahamCode)) {
                return saham;
            }
        }
        return null;
    }

    public double getMarketBySahamCode(String sahamCode) {
        for (Saham saham: DataStore.saham) {
            if (saham.getCode().equalsIgnoreCase(sahamCode)) {
                return saham.getPrice();
            }
        }
        return -1;
    }

    public void addQuantityAndTotalPurchase(CustomerSaham customerSaham, int quantity, double totalPurchase) {
        customerSaham.setQuantity(customerSaham.getQuantity() + quantity);
        customerSaham.setTotalPurchaseValue(customerSaham.getTotalPurchaseValue() + totalPurchase);
    }

    public void subtractCustomerSahamQuantity(CustomerSaham customerSaham, int sahamQuantity) {
        customerSaham.setQuantity(customerSaham.getQuantity() - sahamQuantity);

        if (customerSaham.getQuantity() <= 0) {
            DataStore.customerSaham.remove(customerSaham);
        }
    }

}
