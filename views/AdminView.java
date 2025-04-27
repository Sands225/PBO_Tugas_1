package views;

import data.DataStore;
import models.*;
import services.SBNService;
import services.SahamService;
import utils.Input;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AdminView {
    private final Input input = new Input();
    private final SahamService sahamService = new SahamService();
    private final SBNService sbnService = new SBNService();

    private boolean retry() {
        int choice;

        do {
            System.out.println("==================================");
            System.out.println("1. Coba lagi");
            System.out.println("2. Kembali ke menu sebelumnya");
            System.out.println("==================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1 atau 2.");
                    break;
            }
        } while (true);
    }

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


    public void showAllAvailableSaham() {
        System.out.println("Saham yang tersedia: ");
        for (Saham saham : DataStore.saham) {
            System.out.println("Kode saham: " + saham.getCode());
            System.out.println("Perusahaan: " + saham.getCompany());
            System.out.println("Harga saham: " + saham.getPrice());
        }
    }
}