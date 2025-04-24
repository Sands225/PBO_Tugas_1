package views;

import data.DataStore;
import models.*;
import services.*;
import utils.Input;

public class AdminView {
    private final Input input = new Input();
    private final SahamService sahamService = new SahamService();
    private final SBNService sbnService = new SBNService();

    public void adminMenu() {
        int choice;
        do {
            System.out.println("1. Saham");
            System.out.println("2. SBN");
            System.out.println("3. Logout");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    adminSahamMenu();
                    break;
                case 2:
                    adminSBNMenu();
                    break;
                case 3:
                    View view = new View();
                    view.mainView();
            }
        } while (choice < 1 || choice > 3);
    }
}
