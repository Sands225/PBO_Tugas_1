package services;

import models.Customer;
import models.CustomerSaham;
import models.Saham;
import data.DataStore;

public class SahamService {
    public CustomerSaham getCustomerSahamBySahamCode(String sahamCode) {
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

    public boolean customerSellSaham(String sahamCode, double sahamQuantity) {
        CustomerSaham toRemove = null;

        for (CustomerSaham customerSaham : DataStore.customerSaham) {
            if (customerSaham.getSaham().getCode().equals(sahamCode)) {
                customerSaham.setQuantity(customerSaham.getQuantity() - sahamQuantity);

                if (customerSaham.getQuantity() <= 0) {
                    toRemove = customerSaham;
                }
                break;
            }
        }

        if (toRemove != null) {
            DataStore.customerSaham.remove(toRemove);
            return true;
        }

        return false;
    }

    public double getRemainingQuantity(String sahamCode) {
        for (CustomerSaham customerSaham : DataStore.customerSaham) {
            if (customerSaham.getSaham().getCode().equals(sahamCode)) {
                return customerSaham.getQuantity();
            }
        }
        return 0;
    }

    public Saham getSahamByCode(String sahamCode) {
        for (Saham saham: DataStore.saham) {
            if (saham.getCode().equals(sahamCode)) {
                return saham;
            }
        }
        return null;
    }
}
