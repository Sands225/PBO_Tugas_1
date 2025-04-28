package views;

import data.DataStore;
import models.*;
import services.SahamService;
import services.SBNService;
import utils.*;

import static views.View.retry;

public class CustomerView {
    private final Input input = new Input();
    private final SahamService sahamService = new SahamService();
    private final SBNService sbnService = new SBNService();
    private final Clear clear = new Clear();

    private boolean isGreet = true;

    public void customerMenu(Customer customer) {
        int choice;

        if (!isGreet) {
            clear.clearScreen();
        } else {
            isGreet = false;
        }

        do {
            System.out.println("=============================================================");
            System.out.println("|                       Customer Menu                       |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Saham                                                  |");
            System.out.println("| 2. SBN                                                    |");
            System.out.println("| 3. Portofolio                                             |");
            System.out.println("| 4. Logout                                                 |");
            System.out.println("=============================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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
                    View view = new View();
                    view.mainView();
                    break;
            }
        } while (choice < 1 || choice > 4);
    }

    public void showAllCustomerSaham(Customer customer) {
        clear.clearScreen();
        int count = 0;

        System.out.println("=============================================================");
        System.out.println("|                   Saham yang Anda miliki                  |");
        System.out.println("=============================================================");

        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if(customerSaham.getCustomerName().equals(customer.getName())) {
                count++;
                System.out.printf("| %2d | Kode saham  : %-39s|\n", count, customerSaham.getSaham().getCode());
                System.out.printf("|    | Perusahaan  : %-39s|\n", customerSaham.getSaham().getCompany());
                System.out.printf("|    | Jumlah saham: %-39s|\n", String.format("%,.2f", customerSaham.getQuantity()));
                System.out.println("|    |                                                      |");
            }
        }
        if (count <= 0) {
            System.out.println("|           Anda tidak memiliki saham tersisa!              |");
        }
        System.out.println("=============================================================");

        input.enterToContinue();
    }

    public void showAllAvailableSaham() {
        clear.clearScreen();
        int count = 0;

        System.out.println("=============================================================");
        System.out.println("|                     Saham yang tersedia                   |");
        System.out.println("=============================================================");
        for (Saham saham: DataStore.saham) {
            count++;
            System.out.printf("| %2d | Kode saham : %-29s |\n", count, saham.getCode());
            System.out.printf("|    | Perusahaan : %-29s |\n", saham.getCompany());
            System.out.printf("|    | Harga saham: %-29s |\n", String.format("%,.2f", saham.getPrice()));
            System.out.println("|   |                                                       |");
        }
        System.out.println("=============================================================");

        input.enterToContinue();
    }

    public void customerSahamMenu(Customer customer) {
        int choice;

//        showAllCustomerSaham(customer);
        do {
            System.out.println("=============================================================");
            System.out.println("|                     Customer - Menu Saham                 |");
            System.out.println("=============================================================");
            System.out.println("| 1. Jual Saham                                             |");
            System.out.println("| 2. Beli Saham                                             |");
            System.out.println("| 3. Kembali                                                |");
            System.out.println("=============================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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

    public void customerSellSaham(Customer customer) {
        while (true) {
            showAllCustomerSaham(customer);

            System.out.println("=============================================================");
            System.out.println("|                   Customer - Jual Saham                   |");
            System.out.println("=============================================================");

            String sahamCode = input.inputNextLine("| Masukkan kode saham: ");
            CustomerSaham customerSaham = sahamService.getCustomerSahamBySahamCode(sahamCode);

            if (customerSaham == null) {
                System.out.println("Kode saham tidak ditemukan di portofolio Anda.");
                input.enterToContinue();
                if (!retry()) {
                    customerSahamMenu(customer);
                    return;
                }
                continue;
            }

            double quantity = input.inputNextDouble("| Masukkan jumlah saham: ");
            boolean qtyStatus = sahamService.checkCustomerSahamQuantity(quantity, customerSaham.getQuantity());

            if (!qtyStatus) {
                System.out.printf("Jumlah saham tidak mencukupi. Anda hanya memiliki %.2f saham %s.\n", customerSaham.getQuantity(), customerSaham.getSaham().getCode());
                if (!retry()) {
                    customerMenu(customer);
                    return;
                }
                continue;
            }

            sahamService.subtractCustomerSahamQuantity(customerSaham, quantity);

            System.out.println("=============================================================");
            System.out.println("|                 Berhasil Menjual Saham!                   |");
            System.out.println("=============================================================");

            if (customerSaham.getQuantity() <= 0) {

                System.out.printf("| Anda sudah tidak memiliki saham %-25s |\n", sahamCode);
                System.out.println("=============================================================");
            } else {
                System.out.println("| Saldo Anda saat ini:                            |");
                System.out.printf("| Kode saham : %-44s |\n", sahamCode);
                System.out.printf("| Jumlah     : %-44s |\n", String.format("%,.2f", quantity));
                System.out.println("=============================================================");

            }

            input.enterToContinue();
            customerSahamMenu(customer);
            return;
        }
    }

    public void customerBuySaham(Customer customer) {
        while (true) {
            showAllAvailableSaham();

            System.out.println("=============================================================");
            System.out.println("|                    Customer - Beli Saham                  |");
            System.out.println("=============================================================");

            String sahamCode = input.inputNextLine("| Masukkan kode saham: ");
            Saham sahamToBuy = sahamService.getSahamByCode(sahamCode);

            if (sahamToBuy == null) {
                System.out.println("Kode saham tidak ditemukan.");
                if (!retry()) {
                    customerSahamMenu(customer);
                    return;
                }
                continue;
            }

            double quantity = input.inputNextDouble("| Masukkan jumlah saham yang ingin dibeli: ");
            if (quantity <= 0) {
                System.out.println("Jumlah saham harus lebih dari 0.");
                if (!retry()) {
                    customerSahamMenu(customer);
                    return;
                }
                continue;
            }

            double totalPurchase = quantity * sahamToBuy.getPrice();
            CustomerSaham customerSaham = sahamService.getCustomerSahamBySahamCode(sahamCode);

            if (customerSaham != null) {
                sahamService.addQuantityAndTotalPurchase(customerSaham, quantity, totalPurchase);
            } else {
                customerSaham = new CustomerSaham(customer.getName(), sahamToBuy, quantity, totalPurchase);
                DataStore.customerSaham.add(customerSaham);
            }

            System.out.println("=============================================================");
            System.out.println("|                   Pembelian Saham Berhasil!               |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail pembelian Saham:                                   |");
            System.out.printf("| Kode saham : %-44s |\n", customerSaham.getSaham().getCode());
            System.out.printf("| Jumlah     : %-44s |\n", String.format("%,.2f", customerSaham.getQuantity()));
            System.out.println("|===========================================================|");

            input.enterToContinue();
            customerSahamMenu(customer);
            return;
        }
    }

    public void showAllSBN() {
        clear.clearScreen();
        int count = 0;

        System.out.println("|===========================================================|");
        System.out.println("|              Surat Berharga Negara yang tersedia          |");
        System.out.println("|===========================================================|");

        for(SBN sbn: DataStore.sbn) {
            count++;
            System.out.printf("| %2d | Nama SBN      : %-36s |\n", count, sbn.getName());
            System.out.printf("|    | Bunga (tahun) : %-36s |\n", String.format("%,.2f", sbn.getInterestRate()));
            System.out.printf("|    | Tanggal tempo : %-36s |\n", sbn.getTanggalJatuhTempo());
            System.out.printf("|    | Jangka waktu  : %-36s |\n", String.format("%2d", sbn.getJangkaWaktu()));
            System.out.printf("|    | Kuota nasional: %-36s |\n", sbn.getKuotaNasional());
            System.out.println("|    |                                                      |");
        }
        System.out.println("|===========================================================|");
    }

    public void showAllCustomerSBN(Customer customer) {
        clear.clearScreen();
        int count = 0;

        System.out.println("|===========================================================|");
        System.out.println("|           Surat Berharga Negara yang Anda miliki          |");
        System.out.println("|===========================================================|");

        for (CustomerSBN customerSBN: DataStore.customerSBN) {
            if (customerSBN.getCustomerName().equals(customer.getName())) {
                count++;
                System.out.printf("| %2d | Nama SBN      : %-36s |\n", count, customerSBN.getSBN().getName());
                System.out.printf("|    | Bunga (tahun) : %-36s |\n", String.format("%,.2f", customerSBN.getSBN().getInterestRate()));
                System.out.printf("|    | Tanggal tempo : %-36s |\n", customerSBN.getSBN().getTanggalJatuhTempo());
                System.out.printf("|    | Jangka waktu  : %-36s |\n", String.format("%2d", customerSBN.getSBN().getJangkaWaktu()));
                System.out.printf("|    | Nominal       : %-36s |\n", customerSBN.getNominalInvestasi());
                System.out.println("|    |                                                      |");
            };
        }
        System.out.println("|===========================================================|");
    }

    public void customerSBNMenu(Customer customer) {
        clear.clearScreen();
        int choice;

        showAllCustomerSBN(customer);
        do {
            System.out.println("|===========================================================|");
            System.out.println("|                     Customer - SBN Menu                   |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Beli Surat Berharga Negara                             |");
            System.out.println("| 2. Simulasi Surat Berharga Negara                         |");
            System.out.println("| 3. Keluar                                                 |");
            System.out.println("|===========================================================|");
            choice = input.inputNextInt("| Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerBuySBN(customer);
                    break;
                case 2:
                    customerSBNSimulation(customer);
                    break;
                case 3:
                    View view = new View();
                    view.mainView();
                    break;
            }
        } while (choice < 1 || choice > 3);
    }

    public void customerBuySBN(Customer customer) {
        while (true) {
            showAllSBN();
            String sbnName = input.inputNextLine("| Masukkan nama SBN: ");
            SBN sbnToBuy = sbnService.getSBNByName(sbnName);

            if (sbnToBuy == null) {
                System.out.println("| Nama SBN tidak ditemukan.");
                if (!retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            double nominal = input.inputNextDouble("| Masukkan jumlah nominal pembelian SBN: ");
            boolean isNominalValid = sbnService.checkNominalInvestasi(sbnToBuy, nominal);

            if (!isNominalValid) {
                if (!retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            sbnToBuy.setKuotaNasional(sbnToBuy.getKuotaNasional() - nominal);

            CustomerSBN customerSBN = sbnService.getCustomerSBNBySBNName(sbnName);

            if (customerSBN != null) {
                sbnService.addNominalInvestasi(customerSBN, nominal);
            } else  {
                customerSBN = new CustomerSBN(customer.getName(), sbnToBuy, nominal);
                DataStore.customerSBN.add(customerSBN);
            }

            System.out.println("|===========================================================|");
            System.out.println("|          Pembelian Surat Berharga Negara Berhasil!        |");
            System.out.println("|===========================================================|");
            System.out.println("| Detail pembelian Surat Berharga Negara:                   |");
            System.out.printf("| Nama SBN      : %-45s |\n", customerSBN.getSBN().getName());
            System.out.printf("| Bunga (tahun) : %-45s |\n", String.format("%,.2f", customerSBN.getSBN().getInterestRate()));
            System.out.printf("| Tanggal tempo : %-45s |\n", customerSBN.getSBN().getTanggalJatuhTempo());
            System.out.printf("| Jangka waktu  : %-45s |\n", String.format("%2d", customerSBN.getSBN().getJangkaWaktu()));
            System.out.printf("| Nominal       : %-45s |\n", String.format("%,.2f", customerSBN.getNominalInvestasi()));
            System.out.println("|===========================================================|");

            customerSBNMenu(customer);
            break;
        }
    }

    public void customerSBNSimulation(Customer customer) {
        while (true) {
            showAllSBN();
            String sbnName = input.inputNextLine("| Masukkan nama SBN: ");
            SBN sbnToSimulate = sbnService.getSBNByName(sbnName);

            if (sbnToSimulate == null) {
                System.out.println("| Nama SBN tidak ditemukan.");
                if (!retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            double nominal = input.inputNextDouble("| Masukkan jumlah nominal SBN: ");
            boolean isNominalValid = sbnService.checkNominalInvestasi(sbnToSimulate, nominal);

            if (!isNominalValid) {
                System.out.println("| Nominal tidak boleh melebihi kuota nasional");
                if (!retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            int jangkaWaktu = sbnToSimulate.getJangkaWaktu();
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
            System.out.printf("| %-22s : %-33s |\n", "Tanggal Jatuh Tempo", sbnToSimulate.getTanggalJatuhTempo());
            System.out.println("|-----------------------------------------------------------|");
            System.out.printf("| %-22s : Rp %,-30.2f |\n", "Bunga per Bulan", monthlyInterest);
            System.out.printf("| %-22s : Rp %,-30.2f |\n", "Total Bunga", totalInterest);
            System.out.printf("| %-22s : Rp %,-30.2f |\n", "Total Nominal Investasi", nominal + totalInterest);
            System.out.println("|===========================================================|");

            break;
        }

        input.enterToContinue();
        customerSBNMenu(customer);
    }

    public void showAllDetailCustomerSaham(Customer customer) {
        int count = 0;
        double currMarketValue;

        System.out.println("=============================================================");
        System.out.println("|                   Saham yang Anda miliki                  |");
        System.out.println("=============================================================");
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if(customerSaham.getCustomerName().equals(customer.getName())) {
                currMarketValue = sahamService.getMarketBySahamCode(customerSaham.getSaham().getCode());
                count++;
                System.out.printf("| %2d | Kode saham       : %-33s |\n", count, customerSaham.getSaham().getCode());
                System.out.printf("|    | Jumlah saham     : %,-33.2f |\n", customerSaham.getQuantity());
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

    public void showAllDetailCustomerSBN(Customer customer) {
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
                System.out.printf("|    | Bunga (tahun)   : %-34s |\n", String.format("%.2f%%", customerSBN.getSBN().getInterestRate()));
                System.out.printf("|    | Tanggal tempo   : %-34s |\n", customerSBN.getSBN().getTanggalJatuhTempo());
                System.out.printf("|    | Jangka waktu    : %-34s |\n", String.format("%d", customerSBN.getSBN().getJangkaWaktu()) + " tahun");
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

    public void customerPortofolio(Customer customer) {
        clear.clearScreen();

        showAllDetailCustomerSaham(customer);
        System.out.println(" ");
        showAllDetailCustomerSBN(customer);

        input.enterToContinue();
        customerMenu(customer);
    }
}
