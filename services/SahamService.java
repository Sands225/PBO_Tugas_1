package services;

import models.Customer;
import models.CustomerSaham;
import data.DataStore;

public class SahamService {
    // show all available saham

    // show all customer's saham

    // buy saham

    // sell saham
    public boolean checkCustomerSahamCode(String sahamCode, Customer customer) {
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if (customerSaham.getSaham().getCode().equals(sahamCode)) {
                return true;
            }
        }
        return false;
    }

//    public boolean checkCustomerSahamQuantity(double sahamQuantity) {
//
//    }

    public void customerSellSaham(String sahamCode, double sahamQuantity) {

    }
}
