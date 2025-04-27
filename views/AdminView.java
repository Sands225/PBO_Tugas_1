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

    public void adminAddSaham() {
        while (true) {
            showAllAvailableSaham();

            Saham currSaham;

            System.out.println("===================================================");
            System.out.println("|             Admin - Tambahkan Saham             |");
            System.out.println("|==================================================");
            String sahamCode = input.inputNextLine("| Masukkan kode saham: ");
            currSaham = sahamService.getSahamByCode(sahamCode);

            if (currSaham != null) {
                System.out.println("Saham dengan kode " + sahamCode + " sudah ada!");
                continue;
            }

            String company = input.inputNextLine("| Masukkan nama perusahaan: ");
            double price = input.inputNextDouble("| Masukkan harga saham: ");

            Saham newSaham = new Saham(sahamCode, company, price);
            DataStore.saham.add(newSaham);

            System.out.println("===================================================");
            System.out.println("|            Penambahan Saham Berhasil!           |");
            System.out.println("|=================================================|");
            System.out.println("| Detail Saham:                                   |");
            System.out.printf("| Kode saham : %-34s |\n", sahamCode);
            System.out.printf("| Perusahaan : %-34s |\n", company);
            System.out.printf("| Jumlah     : %-34s |\n", String.format("%,.2f", price));
            System.out.println("===================================================");

            break;
        }
        adminMenu();
        return;
    }

    public void adminUpdateSaham() {
        while (true) {
            showAllAvailableSaham();

            System.out.println("===================================================");
            System.out.println("|              Admin - Mengubah Saham             |");
            System.out.println("|==================================================");
            String sahamCode = input.inputNextLine("| Masukkan kode saham: ");
            Saham saham = sahamService.getSahamByCode(sahamCode);

            if (saham == null) {
                System.out.println("Saham dengan kode tersebut tidak ditemukan.");
                return;
            }

            double newPrice = input.inputNextDouble("| Masukkan harga baru untuk saham " + sahamCode + ": ");
            saham.setPrice(newPrice);

            System.out.println("===================================================");
            System.out.println("|          Saham Berhasil Diperbaharui!           |");
            System.out.println("|=================================================|");
            System.out.println("| Detail Saham:                                   |");
            System.out.printf("| Kode saham : %-34s |\n", sahamCode);
            System.out.printf("| Perusahaan : %-34s |\n", saham.getCompany());
            System.out.printf("| Jumlah     : %-34s |\n", String.format("%,.2f", saham.getPrice()));
            System.out.println("===================================================");

            break;
        }
        adminMenu();
        return;
    }
}