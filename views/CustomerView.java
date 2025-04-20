package views;

import data.DataStore;
import models.Customer;
import models.CustomerSaham;
import utils.Input;
import views.View;

public class CustomerView {
    private Input input = new Input();

    public void customerMenu(Customer customer) {
        int choice;
        do {
            System.out.println("1. Saham");
            System.out.println("2. SBN");
            System.out.println("3. Logout");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerSahamMenu(customer);
                    break;
                case 2:
//                    customerSBNMenu(customer);
                    break;
                case 3:
                    View view = new View();
                    view.mainView();
            }
        } while (choice < 1 || choice > 3);
    }

    public void showAllCustomerSaham(Customer customer) {
        System.out.println("Saham yang Anda miliki: ");
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if(customerSaham.getCustomerName().equals(customer.getName())) {
                System.out.println("Kode saham: " + customerSaham.getSaham().getCode());
                System.out.println("Jumlah saham: " + customerSaham.getQuantity());
            }
        }
    }

    public void customerSahamMenu(Customer customer) {
        int choice;

        showAllCustomerSaham(customer);
        do {
            System.out.println("1. Jual Saham");
            System.out.println("2. Beli Saham");
            System.out.println("3. Kelar");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
//                    customerSellSaham(customer);
                    break;
                case 2:
//                    customerBuySahamu(customer);
                    break;
                case 3:
                    View view = new View();
                    view.mainView();
            }
        } while (choice < 1 || choice > 3);
    }
}
