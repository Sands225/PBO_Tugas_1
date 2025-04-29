package views;

import data.DataStore;
import models.*;
import services.SBNService;
import services.SahamService;
import utils.Clear;
import utils.Input;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AdminView {
    public static void adminMenu() {
        int choice;

        do {
            Clear.clearScreen();

            System.out.println("=============================================================");
            System.out.println("|                         Admin Menu                        |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Saham                                                  |");
            System.out.println("| 2. SBN                                                    |");
            System.out.println("| 3. Logout                                                 |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    adminSahamMenu();
                    break;
                case 2:
                    adminSBNMenu();
                    break;
                case 3:
                    View.mainView();
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan coba lagi.");
                    Input.enterToContinue();
            }
        } while (choice < 1 || choice > 3);
    }

    public static void showAllAvailableSaham() {
        int count = 0;

        Clear.clearScreen();
        System.out.println("=============================================================");
        System.out.println("|                     Saham yang tersedia                   |");
        System.out.println("|===========================================================|");
        for (Saham saham : DataStore.saham) {
            count++;
            System.out.printf("| %2d | Kode saham : %-39s |\n", count, saham.getCode());
            System.out.printf("|    | Perusahaan : %-39s |\n", saham.getCompany());
            System.out.printf("|    | Harga      : %-39s |\n", String.format("%,.2f", saham.getPrice()) + " per lembar");
            System.out.println("|    |                                                      |");
        }
        System.out.println("=============================================================");
        Input.enterToContinue();
    }

    public static void adminSahamMenu() {
        int choice;
        do {
            Clear.clearScreen();

            System.out.println("=============================================================");
            System.out.println("|                     Admin - Menu Saham                    |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Tambahkan Saham                                        |");
            System.out.println("| 2. Ubah Saham                                             |");
            System.out.println("| 3. Kembali                                                |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    adminAddSaham();
                    break;
                case 2:
                    adminUpdateSaham();
                    break;
                case 3:
                    adminMenu();
                    break;
            }
        } while (choice < 1 || choice > 3);
    }

    public static void adminAddSaham() {
        while (true) {
            showAllAvailableSaham();

            System.out.println("=============================================================");
            System.out.println("|                  Admin - Menambahkan Saham                |");
            System.out.println("=============================================================");
            String sahamCode = Input.inputNextLine("Masukkan kode saham: ");
            Saham currSaham = SahamService.getSahamByCode(sahamCode);

            if (currSaham != null) {
                Clear.clearScreen();
                System.out.println("|===========================================================|");
                System.out.printf("|               Saham dengan kode %-18s        |\n", sahamCode + " sudah ada!");
                System.out.println("=============================================================");
                Input.enterToContinue();

                if (!View.retry()) {
                    adminSahamMenu();
                    return;
                }
                continue;
            }

            String company = Input.inputNextLine("Masukkan nama perusahaan : ");
            double price = Input.inputNextDouble("Masukkan harga saham per lembar : ");

            Saham newSaham = new Saham(sahamCode, company, price);
            boolean action = addSahamConfirmation(newSaham);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|             Penambahan Harga Saham dibatalkan!            |");
                System.out.println("=============================================================");
                Input.enterToContinue();

                continue;
            }

            DataStore.saham.add(newSaham);

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                 Berhasil Menambahkan Saham!               |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail Saham:                                             |");
            System.out.printf("| Kode saham : %-44s |\n", sahamCode);
            System.out.printf("| Perusahaan : %-44s |\n", company);
            System.out.printf("| Harga      : Rp %-41s |\n", String.format("%,.2f", price) + " per lembar");
            System.out.println("=============================================================");
            Input.enterToContinue();

            break;
        }
        adminMenu();
        return;
    }

    public static void adminUpdateSaham() {
        while (true) {
            showAllAvailableSaham();

            System.out.println("=============================================================");
            System.out.println("|                 Admin - Mengubah Harga Saham              |");
            System.out.println("=============================================================");
            String sahamCode = Input.inputNextLine("Masukkan kode saham: ");
            Saham saham = SahamService.getSahamByCode(sahamCode);

            if (saham == null) {
                System.out.println("=============================================================");
                System.out.printf("|         Saham dengan kode %-30s  |\n", sahamCode + " tidak ditemukan");
                System.out.println("|===========================================================|");
                Input.enterToContinue();

                if (!View.retry()) {
                    adminSahamMenu();
                    return;
                }
                continue;
            }

            double newPrice = Input.inputNextDouble("Masukkan harga baru untuk saham " + sahamCode + ": ");

            boolean action = updateSahamConfirmation(saham.getCode(), saham.getCompany(), saham.getPrice(), newPrice);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|             Pengubahan Harga Saham dibatalkan!            |");
                System.out.println("=============================================================");
                Input.enterToContinue();

                continue;
            }

            saham.setPrice(newPrice);

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|              Harga Saham Berhasil Diperbaharui!           |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail Saham:                                             |");
            System.out.printf("| Kode saham : %-44s |\n", sahamCode);
            System.out.printf("| Perusahaan : %-44s |\n", saham.getCompany());
            System.out.printf("| Harga      : Rp %-41s |\n", String.format("%,.2f", saham.getPrice()) + " per lembar");
            System.out.println("=============================================================");
            Input.enterToContinue();

            break;
        }
        adminMenu();
        return;
    }

    public static void showAllAvailableSBN() {
        int count = 0;

        Clear.clearScreen();
        System.out.println("=============================================================");
        System.out.println("|            Surat Berharga Negara yang tersedia            |");
        System.out.println("|===========================================================|");
        for (SBN sbn: DataStore.sbn) {
            count++;
            System.out.printf("| %2d | Kode saham     : %-35s |\n", count, sbn.getName());
            System.out.printf("|    | Bunga SBN      : %-35s |\n", String.format("%,.2f", sbn.getInterestRate()) + " %");
            System.out.printf("|    | Jangka Waktu   : %-35s |\n", String.format("%d", sbn.getMaturityPeriod()) + " tahun");
            System.out.printf("|    | Jatuh Tempo    : %-35s |\n", sbn.getMaturityDate());
            System.out.printf("|    | Kuota nasional : Rp %-32s |\n", String.format("%,.2f", sbn.getNationalQuota()));
            System.out.println("|    |                                                      |");
        }
        System.out.println("=============================================================");
        Input.enterToContinue();
    }

    public static void adminSBNMenu() {
        int choice;
        do {
            Clear.clearScreen();

            System.out.println("=============================================================");
            System.out.println("|          Admin - Menu Surat Berharga Negara               |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Tambahkan SBN                                          |");
            System.out.println("| 2. Kembali                                                |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    adminAddSBN();
                    break;
                case 2:
                    adminMenu();
                    break;
            }
        } while (choice < 1 || choice > 2);
    }

    public static void adminAddSBN() {
        showAllAvailableSBN();

        while (true) {
            System.out.println("=============================================================");
            System.out.println("|       Admin - Menambahkan Surat Berharga Negara           |");
            System.out.println("=============================================================");
            String sbnName = Input.inputNextLine("Masukkan nama Surat Berharga Negara: ");
            SBN sbnToAdd = SBNService.getSBNByName(sbnName);
            sbnName = sbnName.toUpperCase();

            if (sbnToAdd != null) {
                Clear.clearScreen();
                System.out.println("=============================================================");
                System.out.println("|           SBN dengan nama tersebut sudah ada!             |");
                System.out.println("=============================================================");
                Input.enterToContinue();

                if (!View.retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            double interest = Input.inputNextDouble("Masukkan persentase bunga (per tahun): ");
            if (interest <= 0) {
                System.out.println("=============================================================");
                System.out.println("|         Persentase bunga harus lebih besar dari 0.        |");
                System.out.println("=============================================================");
                if (!View.retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            int maturityPeriod = Input.inputNextInt("Masukkan jangka waktu (tahun): ");
            if (maturityPeriod <= 0) {
                System.out.println("=============================================================");
                System.out.println("|          Jangka waktu harus lebih besar dari 0.           |");
                System.out.println("=============================================================");
                if (!View.retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            String maturityDate = Input.inputNextLine("Masukkan tanggal jatuh tempo (dd-MM-yyyy): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
            try {
                LocalDate.parse(maturityDate, formatter);
            } catch (DateTimeParseException e) {
                Clear.clearScreen();
                System.out.println("=============================================================");
                System.out.println("|       Format tanggal tidak valid. Gunakan dd-MM-yyyy.     |");
                if (!View.retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            double nationalQouta = Input.inputNextDouble("Masukkan kuota nasional: ");
            if (nationalQouta <= 0) {
                Clear.clearScreen();
                System.out.println("=============================================================");
                System.out.println("|          Kuota nasional harus lebih besar dari 0.         |");
                System.out.println("=============================================================");
                if (!View.retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            boolean action = addSBNConfirmation(sbnName, interest, maturityPeriod, maturityDate, nationalQouta);
            if (!action) {
                Clear.clearScreen();
                System.out.println("=============================================================");
                System.out.println("|          Penambahan Surat Berharga Negara dibatalkan!     |");
                System.out.println("=============================================================");
                Input.enterToContinue();

                continue;
            }

            SBN newSBN = new SBN(sbnName, interest, maturityPeriod, maturityDate, nationalQouta);
            DataStore.sbn.add(newSBN);

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|              Surat Berharga Negara Ditambahkan!           |");
            System.out.println("=============================================================");
            System.out.println("| Detail SBN:                                               |");
            System.out.printf("| Nama SBN             : %-34s |\n", sbnName);
            System.out.printf("| Bunga (per tahun)    : %-34s |\n", String.format("%,.2f", interest) + " %");
            System.out.printf("| Jangka Waktu         : %-34s |\n", String.format("%d", maturityPeriod) + " tahun");
            System.out.printf("| Tanggal Jatuh Tempo  : %-34s |\n", maturityDate);
            System.out.printf("| Kuota Nasional       : RP %,-31.2f |\n", nationalQouta);
            System.out.println("=============================================================");
            Input.enterToContinue();

            break;
        }
        adminMenu();
    }

    public static boolean addSahamConfirmation(Saham saham) {
        while (true) {
            int choice;

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                 Konfirmasi Penambahan Saham               |");
            System.out.println("=============================================================");
            System.out.printf("| Nama Saham       : %-38s |\n", saham.getCode());
            System.out.printf("| Perusahaan       : %-38s |\n", saham.getCompany());
            System.out.printf("| Harga per lembar : Rp %,-35.2f |\n", saham.getPrice());
            System.out.println("|===========================================================|");
            System.out.println("| 1. Tambahkan Saham                                        |");
            System.out.println("| 2. Batalkan Penambahan                                    |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan coba lagi!");
            }
        }
    }

    public static boolean updateSahamConfirmation(String sahamCode, String company, double price, double newPrice) {
        while (true) {
            int choice;

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                Konfirmasi Perubahan Data Saham            |");
            System.out.println("=============================================================");
            System.out.printf("| Nama Saham              : %-31s |\n", sahamCode);
            System.out.printf("| Perusahaan              : %-31s |\n", company);
            System.out.printf("| Harga per lembar (lama) : Rp %,-28.2f |\n", price);
            System.out.printf("| Harga per lembar (baru) : Rp %,-28.2f |\n", newPrice);
            System.out.println("|===========================================================|");
            System.out.println("| 1. Simpan Perubahan                                       |");
            System.out.println("| 2. Batalkan Perubahan                                     |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan coba lagi!");
            }
        }
    }

    public static boolean addSBNConfirmation(String sbnName, double interest, int maturityPeriod, String maturityDate, double nationalQouta) {
        while (true) {
            int choice;

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|         Konfirmasi Penambahan Surat Berharga Negara       |");
            System.out.println("=============================================================");
            System.out.printf("| Nama SBN             : %-34s |\n", sbnName);
            System.out.printf("| Bunga (per tahun)    : %-34s |\n", String.format("%,.2f", interest) + " %");
            System.out.printf("| Jangka Waktu         : %-34s |\n", String.format("%d", maturityPeriod) + " tahun");
            System.out.printf("| Tanggal Jatuh Tempo  : %-34s |\n", maturityDate);
            System.out.printf("| Kuota Nasional       : RP %,-31.2f |\n", nationalQouta);
            System.out.println("|===========================================================|");
            System.out.println("| 1. Tambahkan Surat Berharga Negara                        |");
            System.out.println("| 2. Batalkan Penambahan                                    |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan coba lagi!");
            }
        }
    }
}