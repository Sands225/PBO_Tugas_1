package views;

import data.DataStore;
import models.*;
import services.SBNService;
import services.SahamService;
import utils.*;

public class CustomerView {
    public static void customerMenu(Customer customer) {
        int choice;

        do {
            Clear.clearScreen();

            System.out.println("=============================================================");
            System.out.println("|                       Customer Menu                       |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Saham                                                  |");
            System.out.println("| 2. SBN                                                    |");
            System.out.println("| 3. Portofolio                                             |");
            System.out.println("| 4. Logout                                                 |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerSahamMenu(customer);
                    break;
                case 2:
                    customerSBNMenu(customer);
                    break;
                case 3:
                    customerPortofolio(customer);
                    break;
                case 4:
                    View.mainView();
                    break;
            }
        } while (choice < 1 || choice > 4);
    }

    public static void showAllCustomerSaham(Customer customer) {
        Clear.clearScreen();
        int count = 0;

        System.out.println("=============================================================");
        System.out.println("|                   Saham yang Anda miliki                  |");
        System.out.println("=============================================================");

        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if(customerSaham.getCustomerName().equals(customer.getName())) {
                count++;
                System.out.printf("| %2d | Kode saham  : %-39s|\n", count, customerSaham.getSaham().getCode());
                System.out.printf("|    | Perusahaan  : %-39s|\n", customerSaham.getSaham().getCompany());
                System.out.printf("|    | Jumlah saham: %-39s|\n", String.format("%d", customerSaham.getQuantity()));
                System.out.println("|    |                                                      |");
            }
        }
        if (count <= 0) {
            System.out.println("|           Anda tidak memiliki saham tersisa!              |");
        }
        System.out.println("=============================================================");

        Input.enterToContinue();
    }

    public static void showAllAvailableSaham() {
        Clear.clearScreen();
        int count = 0;

        System.out.println("=============================================================");
        System.out.println("|                     Saham yang tersedia                   |");
        System.out.println("=============================================================");
        for (Saham saham: DataStore.saham) {
            count++;
            System.out.printf("| %2d | Kode saham : %-39s |\n", count, saham.getCode());
            System.out.printf("|    | Perusahaan : %-39s |\n", saham.getCompany());
            System.out.printf("|    | Harga saham: %-39s |\n", String.format("%,.2f", saham.getPrice()));
            System.out.println("|    |                                                      |");
        }
        System.out.println("=============================================================");

        Input.enterToContinue();
    }

    public static void customerSahamMenu(Customer customer) {
        int choice;

        Clear.clearScreen();
        do {
            System.out.println("=============================================================");
            System.out.println("|                     Customer - Menu Saham                 |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Jual Saham                                             |");
            System.out.println("| 2. Beli Saham                                             |");
            System.out.println("| 3. Kembali                                                |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerSellSaham(customer);
                    break;
                case 2:
                    customerBuySaham(customer);
                    break;
                case 3:
                    customerMenu(customer);
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan coba lagi.");
            }
        } while (choice < 1 || choice > 3);
    }

    public static void customerSellSaham(Customer customer) {
        while (true) {
            showAllCustomerSaham(customer);

            System.out.println("=============================================================");
            System.out.println("|                   Customer - Jual Saham                   |");
            System.out.println("=============================================================");

            String sahamCode = Input.inputNextLine("| Masukkan kode saham: ");
            CustomerSaham customerSaham = SahamService.getCustomerSahamBySahamCode(sahamCode);

            if (customerSaham == null) {
                System.out.println("Kode saham tidak ditemukan di portofolio Anda.");
                Input.enterToContinue();
                if (!View.retry()) {
                    customerSahamMenu(customer);
                    return;
                }
                continue;
            }

            int quantity = Input.inputNextInt("| Masukkan jumlah saham: ");
            boolean qtyStatus = SahamService.checkCustomerSahamQuantity(quantity, customerSaham.getQuantity());

            if (!qtyStatus) {
                System.out.println("=============================================================");
                System.out.println("|                 Jumlah saham tidak mencukupi              |");
                System.out.printf("|            Anda hanya memiliki %d saham %-10s    |\n", customerSaham.getQuantity(), customerSaham.getSaham().getCode());
                System.out.println("=============================================================");
                if (!View.retry()) {
                    customerMenu(customer);
                    return;
                }
                continue;
            }

            boolean action = sellSahamConfirmation(customerSaham, quantity);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|                 Penjualan Saham dibatalkan!               |");
                System.out.println("=============================================================");
                Input.enterToContinue();

                continue;
            }

            SahamService.subtractCustomerSahamQuantity(customerSaham, quantity);

            System.out.println("=============================================================");
            System.out.println("|                 Berhasil Menjual Saham!                   |");
            System.out.println("=============================================================");

            if (customerSaham.getQuantity() <= 0) {

                System.out.printf("| Anda sudah tidak memiliki saham %-25s |\n", sahamCode);
                System.out.println("=============================================================");
            } else {
                System.out.println("| Saldo Anda saat ini:                            |");
                System.out.printf("| Kode saham : %-44s |\n", sahamCode);
                System.out.printf("| Jumlah     : %-44s |\n", String.format("%d", quantity));
                System.out.println("=============================================================");

            }

            Input.enterToContinue();
            customerSahamMenu(customer);
            return;
        }
    }

    public static void customerBuySaham(Customer customer) {
        while (true) {
            showAllAvailableSaham();

            System.out.println("=============================================================");
            System.out.println("|                    Customer - Beli Saham                  |");
            System.out.println("=============================================================");

            String sahamCode = Input.inputNextLine("| Masukkan kode saham: ");
            Saham sahamToBuy = SahamService.getSahamByCode(sahamCode);

            if (sahamToBuy == null) {
                System.out.println("Kode saham tidak ditemukan.");
                if (!View.retry()) {
                    customerSahamMenu(customer);
                    return;
                }
                continue;
            }

            int quantity = Input.inputNextInt("Masukkan jumlah saham yang ingin dibeli: ");
            if (quantity <= 0) {
                System.out.println("Jumlah saham harus lebih dari 0.");
                if (!View.retry()) {
                    customerSahamMenu(customer);
                    return;
                }
                continue;
            }

            boolean action = buySahamConfirmation(sahamToBuy, quantity);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|                 Pembelian Saham dibatalkan!               |");
                System.out.println("=============================================================");
                Input.enterToContinue();

                continue;
            }

            double totalPurchase = quantity * sahamToBuy.getPrice();
            CustomerSaham customerSaham = SahamService.getCustomerSahamBySahamCode(sahamCode);

            if (customerSaham != null) {
                SahamService.addQuantityAndTotalPurchase(customerSaham, quantity, totalPurchase);
            } else {
                customerSaham = new CustomerSaham(customer.getName(), sahamToBuy, quantity, totalPurchase);
                DataStore.customerSaham.add(customerSaham);
            }

            System.out.println("=============================================================");
            System.out.println("|                   Pembelian Saham Berhasil!               |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail pembelian Saham:                                   |");
            System.out.printf("| Kode saham : %-44s |\n", customerSaham.getSaham().getCode());
            System.out.printf("| Jumlah     : %-44s |\n", String.format("%d", customerSaham.getQuantity()));
            System.out.println("|===========================================================|");

            Input.enterToContinue();
            customerSahamMenu(customer);
            return;
        }
    }

    public static void showAllSBN() {
        Clear.clearScreen();
        int count = 0;

        System.out.println("|===========================================================|");
        System.out.println("|              Surat Berharga Negara yang tersedia          |");
        System.out.println("|===========================================================|");

        for(SBN sbn: DataStore.sbn) {
            count++;
            System.out.printf("| %2d | Nama SBN      : %-36s |\n", count, sbn.getName());
            System.out.printf("|    | Bunga (tahun) : %-36s |\n", String.format("%,.2f", sbn.getInterestRate()));
            System.out.printf("|    | Tanggal tempo : %-36s |\n", sbn.getMaturityDate());
            System.out.printf("|    | Jangka waktu  : %-36s |\n", String.format("%-2d", sbn.getMaturityPeriod()));
            System.out.printf("|    | Kuota nasional: %-36s |\n", String.format("%,.2f", sbn.getNationalQuota()));
            System.out.println("|    |                                                      |");
        }
        System.out.println("|===========================================================|");
    }

    public static void customerSBNMenu(Customer customer) {
        Clear.clearScreen();
        int choice;

        do {
            System.out.println("|===========================================================|");
            System.out.println("|                     Customer - SBN Menu                   |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Beli Surat Berharga Negara                             |");
            System.out.println("| 2. Simulasi Surat Berharga Negara                         |");
            System.out.println("| 3. Kembali                                                |");
            System.out.println("|===========================================================|");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerBuySBN(customer);
                    break;
                case 2:
                    customerSBNSimulation(customer);
                    break;
                case 3:
                    customerSBNMenu(customer);
                    break;
            }
        } while (choice < 1 || choice > 3);
    }

    public static void customerBuySBN(Customer customer) {
        while (true) {
            showAllSBN();
            String sbnName = Input.inputNextLine("| Masukkan nama SBN: ");
            SBN sbnToBuy = SBNService.getSBNByName(sbnName);

            if (sbnToBuy == null) {
                System.out.println("| Nama SBN tidak ditemukan.");
                if (!View.retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            double nominal = Input.inputNextDouble("| Masukkan jumlah nominal pembelian SBN: ");
            boolean isNominalValid = SBNService.checkNominalInvestasi(sbnToBuy, nominal);

            if (!isNominalValid) {
                if (!View.retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            boolean action = buySBNConfirmation(sbnToBuy, nominal);
            if (!action) {
                System.out.println("=============================================================");
                System.out.println("|        Pembelian Surat Berharga Negara dibatalkan!        |");
                System.out.println("=============================================================");
                Input.enterToContinue();

                continue;
            }

            sbnToBuy.setNationalQuota(sbnToBuy.getNationalQuota() - nominal);

            CustomerSBN customerSBN = SBNService.getCustomerSBNBySBNName(sbnName);

            if (customerSBN != null) {
                SBNService.addNominalInvestasi(customerSBN, nominal);
            } else  {
                customerSBN = new CustomerSBN(customer.getName(), sbnToBuy, nominal);
                DataStore.customerSBN.add(customerSBN);
            }

            System.out.println("=============================================================");
            System.out.println("|          Pembelian Surat Berharga Negara Berhasil!        |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail pembelian Surat Berharga Negara:                   |");
            System.out.printf("| Nama SBN      : %-45s |\n", customerSBN.getSBN().getName());
            System.out.printf("| Bunga (tahun) : %-45s |\n", String.format("%,.2f", customerSBN.getSBN().getInterestRate()));
            System.out.printf("| Tanggal tempo : %-45s |\n", customerSBN.getSBN().getMaturityDate());
            System.out.printf("| Jangka waktu  : %-45s |\n", String.format("%2d", customerSBN.getSBN().getMaturityPeriod()));
            System.out.printf("| Nominal       : %-45s |\n", String.format("%,.2f", customerSBN.getNominalInvestasi()));
            System.out.println("=============================================================");

            customerSBNMenu(customer);
            break;
        }
    }

    public static void customerSBNSimulation(Customer customer) {
        while (true) {
            showAllSBN();
            String sbnName = Input.inputNextLine("| Masukkan nama SBN: ");
            SBN sbnToSimulate = SBNService.getSBNByName(sbnName);

            if (sbnToSimulate == null) {
                System.out.println("Nama SBN tidak ditemukan.");
                if (!View.retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            double nominal = Input.inputNextDouble("| Masukkan jumlah nominal SBN: ");
            boolean isNominalValid = SBNService.checkNominalInvestasi(sbnToSimulate, nominal);

            if (!isNominalValid) {
                System.out.println("Nominal tidak boleh melebihi kuota nasional");
                if (!View.retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            int jangkaWaktu = sbnToSimulate.getMaturityPeriod();
            double annualRate = sbnToSimulate.getInterestRate();
            double monthlyInterest = annualRate * nominal * 0.9 / 12 ;
            double totalInterest = monthlyInterest * jangkaWaktu;

            System.out.println("|===========================================================|");
            System.out.println("|                 Hasil Simulasi Investasi SBN              |");
            System.out.println("|===========================================================|");
            System.out.printf("| %-22s : %-33s |\n", "Nama SBN", sbnToSimulate.getName());
            System.out.printf("| %-22s : Rp %,-30.2f |\n", "Nominal Investasi", nominal);
            System.out.printf("| %-22s : %-33s |\n", "Suku Bunga", String.format("%.2f%% per tahun", annualRate * 100));
            System.out.printf("| %-22s : %-33s |\n", "Jangka Waktu", jangkaWaktu + " bulan");
            System.out.printf("| %-22s : %-33s |\n", "Tanggal Jatuh Tempo", sbnToSimulate.getMaturityDate());
            System.out.println("|-----------------------------------------------------------|");
            System.out.printf("| %-22s : Rp %,-30.2f |\n", "Bunga per Bulan", monthlyInterest);
            System.out.printf("| %-22s : Rp %,-30.2f |\n", "Total Bunga", totalInterest);
            System.out.printf("| %-22s : Rp %,-30.2f |\n", "Total Nominal Investasi", nominal + totalInterest);
            System.out.println("|===========================================================|");

            break;
        }

        Input.enterToContinue();
        customerSBNMenu(customer);
    }

    public static void showAllDetailCustomerSaham(Customer customer) {
        int count = 0;
        double currMarketValue;

        System.out.println("=============================================================");
        System.out.println("|                   Saham yang Anda miliki                  |");
        System.out.println("=============================================================");
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if(customerSaham.getCustomerName().equals(customer.getName())) {
                currMarketValue = SahamService.getMarketBySahamCode(customerSaham.getSaham().getCode());
                count++;
                System.out.printf("| %2d | Kode saham       : %-33s |\n", count, customerSaham.getSaham().getCode());
                System.out.printf("|    | Jumlah saham     : %-33d |\n", customerSaham.getQuantity());
                System.out.printf("|    | Nominal Pembelian: Rp %,-30.2f |\n", customerSaham.getTotalPurchaseValue());
                System.out.printf("|    | Harga saat ini   : Rp %,-30.2f |\n", currMarketValue);
                System.out.println("|    |                                                      |");
            }
        }
        if (count <= 0) {
            System.out.println("|             Anda belum memiliki saham apapun!             |");
        }
        System.out.println("=============================================================");
    }

    public static void showAllDetailCustomerSBN(Customer customer) {
        int count = 0;

        System.out.println("=============================================================");
        System.out.println("|           Surat Berharga Negara yang Anda miliki          |");
        System.out.println("=============================================================");
        for (CustomerSBN customerSBN : DataStore.customerSBN) {
            if (customerSBN.getCustomerName().equals(customer.getName())) {
                count++;

                double annualRate = customerSBN.getSBN().getInterestRate() / 100;
                double nominal = customerSBN.getNominalInvestasi();
                double monthlyInterest = annualRate * nominal * 0.9 / 12 ;

                System.out.printf("| %-2d | Nama SBN        : %-34s |\n", count, customerSBN.getSBN().getName());
                System.out.printf("|    | Bunga (tahun)   : %-34s |\n", String.format("%.2f%%", customerSBN.getSBN().getInterestRate()) + " per tahun");
                System.out.printf("|    | Tanggal tempo   : %-34s |\n", customerSBN.getSBN().getMaturityDate());
                System.out.printf("|    | Jangka waktu    : %-34s |\n", String.format("%d", customerSBN.getSBN().getMaturityPeriod()) + " tahun");
                System.out.printf("|    | Nominal         : Rp %,-31.2f |\n", customerSBN.getNominalInvestasi());
                System.out.printf("|    | Bunga per bulan : Rp %,-31.2f |\n", monthlyInterest);
                System.out.println("|    |                                                      |");

            }
        }
        if (count == 0) {
            System.out.println("|               Anda belum memiliki SBN apapun!              |");
        }
        System.out.println("=============================================================");
    }

    public static void customerPortofolio(Customer customer) {
        Clear.clearScreen();

        showAllDetailCustomerSaham(customer);
        System.out.println(" ");
        showAllDetailCustomerSBN(customer);

        Input.enterToContinue();
        customerMenu(customer);
    }

    public static boolean buySahamConfirmation(Saham sahamToBuy, double amount) {
        while (true) {
            int choice;

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                 Konfirmasi Pembelian Saham                |");
            System.out.println("=============================================================");
            System.out.printf("| Nama Saham       : %-38s |\n", sahamToBuy.getCode());
            System.out.printf("| Perusahaan       : %-38s |\n", sahamToBuy.getCompany());
            System.out.printf("| Harga per lembar : Rp %,-35.2f |\n", sahamToBuy.getPrice());
            System.out.printf("| Banyak lembar    : %,-38.2f |\n", amount);
            System.out.println("|-----------------------------------------------------------|");
            System.out.printf("| Total Harga      : %,-38.2f |\n", sahamToBuy.getPrice() * amount);
            System.out.println("|===========================================================|");
            System.out.println("| 1. Beli Saham                                             |");
            System.out.println("| 2. Batalkan Pembelian                                     |");
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

    public static boolean sellSahamConfirmation(CustomerSaham customerSaham, double amount) {
        while (true) {
            int choice;

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                Konfirmasi Penjualan Data Saham            |");
            System.out.println("=============================================================");
            System.out.printf("| Nama Saham       : %-38s |\n", customerSaham.getSaham().getCode());
            System.out.printf("| Perusahaan       : %-38s |\n", customerSaham.getSaham().getCompany());
            System.out.printf("| Harga per lembar : Rp %,-35.2f |\n", customerSaham.getSaham().getPrice());
            System.out.printf("| Banyak lembar    : %,-38.2f |\n", amount);
            System.out.println("|-----------------------------------------------------------|");
            System.out.printf("| Total Harga      : %,-38.2f |\n", customerSaham.getSaham().getPrice() * amount);
            System.out.println("|===========================================================|");
            System.out.println("| 1. Simpan Perubahan                                        |");
            System.out.println("| 2. Batalkan Perubahan                                      |");
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

    public static boolean buySBNConfirmation(SBN sbn, double nominal) {
        while (true) {
            int choice;
            double annualRate = sbn.getInterestRate() / 100;
            double monthlyInterest = annualRate * nominal * 0.9 / 12 ;

            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|         Konfirmasi Penambahan Surat Berharga Negara       |");
            System.out.println("=============================================================");
            System.out.printf("| Nama SBN             : %-34s |\n", sbn.getName());
            System.out.printf("| Bunga (per tahun)    : %-34s |\n", String.format("%,.2f", annualRate) + " %");
            System.out.printf("| Jangka Waktu         : %-34s |\n", String.format("%d", sbn.getMaturityPeriod()) + " tahun");
            System.out.printf("| Tanggal Jatuh Tempo  : %-34s |\n", sbn.getMaturityDate());
            System.out.printf("| Nominal Pembelian    : RP %,-31.2f |\n", monthlyInterest);
            System.out.println("|-----------------------------------------------------------|");
            System.out.printf("| Total Harga    : RP %,-31.2f |\n", monthlyInterest);
            System.out.println("|-----------------------------------------------------------|");
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
