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

import static views.View.retry;

public class AdminView {
    private final Input input = new Input();
    private final SahamService sahamService = new SahamService();
    private final SBNService sbnService = new SBNService();
    private final Clear clear = new Clear();

    public void adminMenu() {
        int choice;

        do {
            clear.clearScreen();

            System.out.println("=============================================================");
            System.out.println("|                         Admin Menu                        |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Saham                                                  |");
            System.out.println("| 2. SBN                                                    |");
            System.out.println("| 3. Logout                                                 |");
            System.out.println("=============================================================");
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
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan coba lagi.");
                    input.enterToContinue();
            }
        } while (choice < 1 || choice > 3);
    }

    public void showAllAvailableSaham() {
        int count = 0;

        clear.clearScreen();
        System.out.println("=============================================================");
        System.out.println("|                     Saham yang tersedia                   |");
        System.out.println("|===========================================================|");
        for (Saham saham : DataStore.saham) {
            count++;
            System.out.printf("| %2d | Kode saham : %-39s |\n", count, saham.getCode());
            System.out.printf("|    | Perusahaan : %-39s |\n", saham.getCompany());
            System.out.printf("|    | Harga saham: %-39s |\n", String.format("%,.2f", saham.getPrice()));
            System.out.println("|    |                                                      |");
        }
        System.out.println("=============================================================");
        input.enterToContinue();
    }

    public void adminSahamMenu() {
        int choice;
        do {
            clear.clearScreen();

            System.out.println("=============================================================");
            System.out.println("|                     Admin - Menu Saham                    |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Tambahkan Saham                                        |");
            System.out.println("| 2. Ubah Saham                                             |");
            System.out.println("| 3. Kembali                                                |");
            System.out.println("=============================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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

    public void adminAddSaham() {
        while (true) {
            showAllAvailableSaham();

            System.out.println("=============================================================");
            System.out.println("|                  Admin - Menambahkan Saham                |");
            System.out.println("|===========================================================|");
            String sahamCode = input.inputNextLine("| Masukkan kode saham: ");
            Saham currSaham = sahamService.getSahamByCode(sahamCode);

            if (currSaham != null) {
                clear.clearScreen();
                System.out.println("|===========================================================|");
                System.out.printf("|               Saham dengan kode %-18s        |\n", sahamCode + " sudah ada!");
                System.out.println("=============================================================");
                input.enterToContinue();

                if (!retry()) {
                    adminSahamMenu();
                    return;
                }
                continue;
            }

            String company = input.inputNextLine("| Masukkan nama perusahaan : ");
            double price = input.inputNextDouble("| Masukkan harga saham per lembar : ");

            boolean action = addSahamConfirmation(sahamCode, company, price);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|             Penambahan Harga Saham dibatalkan!            |");
                System.out.println("=============================================================");
                input.enterToContinue();

                continue;
            }

            Saham newSaham = new Saham(sahamCode, company, price);
            DataStore.saham.add(newSaham);

            clear.clearScreen();
            System.out.println("|===========================================================|");
            System.out.println("|                 Berhasil Menambahkan Saham!               |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail Saham:                                             |");
            System.out.printf("| Kode saham : %-44s |\n", sahamCode);
            System.out.printf("| Perusahaan : %-44s |\n", company);
            System.out.printf("| Jumlah     : Rp %-41s |\n", String.format("%,.2f", price) + " per lembar");
            System.out.println("|===========================================================|");
            input.enterToContinue();

            break;
        }
        adminMenu();
        return;
    }

    public void adminUpdateSaham() {
        while (true) {
            showAllAvailableSaham();

            System.out.println("=============================================================");
            System.out.println("|                 Admin - Mengubah Harga Saham              |");
            System.out.println("|===========================================================|");
            String sahamCode = input.inputNextLine("Masukkan kode saham: ");
            Saham saham = sahamService.getSahamByCode(sahamCode);

            if (saham == null) {
                System.out.println("=============================================================");
                System.out.printf("|         Saham dengan kode %-30s  |", sahamCode);
                System.out.println("|===========================================================|");
                input.enterToContinue();

                if (!retry()) {
                    adminSahamMenu();
                    return;
                }
                continue;
            }

            double newPrice = input.inputNextDouble("| Masukkan harga baru untuk saham " + sahamCode + ": ");

            boolean action = updateSahamConfirmation(saham.getCode(), saham.getCompany(), saham.getPrice(), newPrice);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|             Pengubahan Harga Saham dibatalkan!            |");
                System.out.println("=============================================================");
                input.enterToContinue();

                continue;
            }

            saham.setPrice(newPrice);

            clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|              Harga Saham Berhasil Diperbaharui!           |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail Saham:                                             |");
            System.out.printf("| Kode saham : %-44s |\n", sahamCode);
            System.out.printf("| Perusahaan : %-44s |\n", saham.getCompany());
            System.out.printf("| Jumlah     : Rp %-41s |\n", String.format("%,.2f", saham.getPrice()) + "per lembar");
            System.out.println("=============================================================");
            input.enterToContinue();

            break;
        }
        adminMenu();
        return;
    }

    public void showAllAvailableSBN() {
        int count = 0;

        clear.clearScreen();
        System.out.println("=============================================================");
        System.out.println("|            Surat Berharga Negara yang tersedia            |");
        System.out.println("|===========================================================|");
        for (SBN sbn: DataStore.sbn) {
            count++;
            System.out.printf("| %2d | Kode saham     : %-35s |\n", count, sbn.getName());
            System.out.printf("|    | Bunga SBN      : %-35s |\n", String.format("%,.2f", sbn.getInterestRate()));
            System.out.printf("|    | Jangka Waktu   : %-35s |\n", String.format("%d", sbn.getJangkaWaktu()));
            System.out.printf("|    | Jatuh Tempo    : %-35s |\n", sbn.getTanggalJatuhTempo());
            System.out.printf("|    | Kuota nasional : %-35s |\n", String.format("%,.2f", sbn.getKuotaNasional()));
            System.out.println("|    |                                                      |");
        }
        System.out.println("=============================================================");
        input.enterToContinue();
    }

    public void adminSBNMenu() {
        int choice;
        do {
            clear.clearScreen();

            System.out.println("|===========================================================|");
            System.out.println("|          Admin - Menu Surat Berharga Negara               |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Tambahkan SBN                                          |");
            System.out.println("| 2. Kembali                                                |");
            System.out.println("=============================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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

    public void adminAddSBN() {
        showAllAvailableSBN();

        while (true) {
            System.out.println("=============================================================");
            System.out.println("|       Admin - Menambahkan Surat Berharga Negara           |");
            System.out.println("|===========================================================|");
            String sbnName = input.inputNextLine("| Masukkan nama Surat Berharga Negara: ");
            SBN sbnToAdd = sbnService.getSBNByName(sbnName);
            sbnName = sbnName.toUpperCase();

            if (sbnToAdd != null) {
                System.out.println("=============================================================");
                System.out.println("|           SBN dengan nama tersebut sudah ada!             |");
                System.out.println("=============================================================");
                input.enterToContinue();

                if (!retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            double bunga = input.inputNextDouble("| Masukkan persentase bunga (per tahun): ");
            if (bunga <= 0) {
                System.out.println("=============================================================");
                System.out.println("|         Persentase bunga harus lebih besar dari 0.        |");
                System.out.println("=============================================================");
                if (!retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            int jangkaWaktu = input.inputNextInt("| Masukkan jangka waktu (tahun): ");
            if (jangkaWaktu <= 0) {
                System.out.println("| Jangka waktu harus lebih besar dari 0.           |");
                System.out.println("===================================================");
                if (!retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            String tanggalJatuhTempo = input.inputNextLine("| Masukkan tanggal jatuh tempo (dd-MM-yyyy): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
            try {
                LocalDate.parse(tanggalJatuhTempo, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("=============================================================");
                System.out.println("|       Format tanggal tidak valid. Gunakan dd-MM-yyyy.     |");
                System.out.println("=============================================================");
                if (!retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            double kuotaNasional = input.inputNextDouble("| Masukkan kuota nasional: ");
            if (kuotaNasional <= 0) {
                System.out.println("=============================================================");
                System.out.println("|          Kuota nasional harus lebih besar dari 0.         |");
                System.out.println("=============================================================");
                if (!retry()) {
                    adminSBNMenu();
                    return;
                }
                continue;
            }

            boolean action = addSBNConfirmation(sbnName, bunga, jangkaWaktu, tanggalJatuhTempo, kuotaNasional);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|          Penambahan Surat Berharga Negara dibatalkan!     |");
                System.out.println("|===========================================================|");
                input.enterToContinue();

                continue;
            }

            SBN newSBN = new SBN(sbnName, bunga, jangkaWaktu, tanggalJatuhTempo, kuotaNasional);
            DataStore.sbn.add(newSBN);

            clear.clearScreen();
            System.out.println("===================================================");
            System.out.println("|         Surat Berharga Negara Ditambahkan!      |");
            System.out.println("|=================================================|");
            System.out.println("| Detail SBN:                                     |");
            System.out.printf("| Nama SBN             : %-34s |\n", sbnName);
            System.out.printf("| Bunga (per tahun)    : %-34s |\n", String.format("%,.2f", bunga) + " %");
            System.out.printf("| Jangka Waktu         : %-34s |\n", String.format("%d", jangkaWaktu) + " tahun");
            System.out.printf("| Tanggal Jatuh Tempo  : %-34s |\n", tanggalJatuhTempo);
            System.out.printf("| Kuota Nasional       : RP %,-31.2f |\n", kuotaNasional);
            System.out.println("===================================================");
            input.enterToContinue();

            break;
        }
        adminMenu();
    }

    public boolean addSahamConfirmation(String sahamCode, String company, double price) {
        while (true) {
            int choice;

            clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                 Konfirmasi Penambahan Saham               |");
            System.out.println("=============================================================");
            System.out.printf("| Nama Saham       : %-38s |\n", sahamCode);
            System.out.printf("| Perusahaan       : %-38s |\n", company);
            System.out.printf("| Harga per lembar : Rp %,-35.2f |\n", price);
            System.out.println("|===========================================================|");
            System.out.println("| 1. Tambahkan Saham                                        |");
            System.out.println("| 2. Batalkan Penambahan                                    |");
            System.out.println("=============================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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

    public boolean updateSahamConfirmation(String sahamCode, String company, double price, double newPrice) {
        while (true) {
            int choice;

            clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                Konfirmasi Perubahan Data Saham            |");
            System.out.println("=============================================================");
            System.out.printf("| Nama Saham              : %-30s |\n", sahamCode);
            System.out.printf("| Perusahaan              : %-30s |\n", company);
            System.out.printf("| Harga per lembar (lama) : Rp %,-27.2f |\n", price);
            System.out.printf("| Harga per lembar (baru) : Rp %,-27.2f |\n", newPrice);
            System.out.println("|===========================================================|");
            System.out.println("| 1. Simpan Perubahan                                        |");
            System.out.println("| 2. Batalkan Perubahan                                      |");
            System.out.println("=============================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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

    public boolean addSBNConfirmation(String sbnName, double bunga, int jangkaWaktu, String tanggalJatuhTempo, double kuotaNasional) {
        while (true) {
            int choice;

            clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|         Konfirmasi Penambahan Surat Berharga Negara       |");
            System.out.println("=============================================================");
            System.out.printf("| Nama SBN             : %-34s |\n", sbnName);
            System.out.printf("| Bunga (per tahun)    : %-34s |\n", String.format("%,.2f", bunga) + " %");
            System.out.printf("| Jangka Waktu         : %-34s |\n", String.format("%d", jangkaWaktu) + " tahun");
            System.out.printf("| Tanggal Jatuh Tempo  : %-34s |\n", tanggalJatuhTempo);
            System.out.printf("| Kuota Nasional       : RP %,-31.2f |\n", kuotaNasional);
            System.out.println("=============================================================");
            System.out.println("| 1. Tambahkan Surat Berharga Negara                        |");
            System.out.println("| 2. Batalkan Penambahan                                    |");
            System.out.println("=============================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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