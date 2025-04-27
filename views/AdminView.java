package views;

import data.DataStore;
import models.*;
import services.SBNService;
import services.SahamService;
import utils.Input;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static views.View.retry;

public class AdminView {
    private final Input input = new Input();
    private final SahamService sahamService = new SahamService();
    private final SBNService sbnService = new SBNService();

    public void adminMenu() {
        int choice;
        do {
            System.out.println("===================================================");
            System.out.println("|                  Admin Menu                     |");
            System.out.println("|=================================================|");
            System.out.println("| 1. Saham                                        |");
            System.out.println("| 2. SBN                                          |");
            System.out.println("| 3. Logout                                       |");
            System.out.println("===================================================");
            choice = input.inputNextInt("| Masukkan pilihan Anda: ");

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

    public void showAllAvailableSaham() {
        int count = 0;

        System.out.println("===================================================");
        System.out.println("|               Saham yang tersedia               |");
        System.out.println("===================================================");
        for (Saham saham: DataStore.saham) {
            count++;
            System.out.printf("| %2d | Kode saham : %-29s |\n", count, saham.getCode());
            System.out.printf("|    | Perusahaan : %-29s |\n", saham.getCompany());
            System.out.printf("|    | Harga saham: %-29s |\n", String.format("%,.2f", saham.getPrice()));
        }
        System.out.println("===================================================");
    }

    public void adminSahamMenu() {
        int choice;
        do {
            System.out.println("===================================================");
            System.out.println("|                  Admin Menu                     |");
            System.out.println("|=================================================|");
            System.out.println("| 1. Tambahkan Saham                              |");
            System.out.println("| 2. Ubah Saham                                   |");
            System.out.println("| 3. Kembali                                      |");
            System.out.println("===================================================");
            choice = input.inputNextInt("| Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    adminAddSaham();
                    break;
                case 2:
                    adminUpdateSaham();
                    break;
                case 3:
                    View view = new View();
                    view.mainView();
            }
        } while (choice < 1 || choice > 3);
    }
}