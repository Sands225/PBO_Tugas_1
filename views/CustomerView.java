package views;

import models.Customer;
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
//                    customerSahamMenu(customer);
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
}
