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

    public void customerMenu(Customer customer) {
        int choice;
        do {
            System.out.println("===================================================");
            System.out.println("|                  Customer Menu                  |");
            System.out.println("|=================================================|");
            System.out.println("| 1. Saham                                        |");
            System.out.println("| 2. SBN                                          |");
            System.out.println("| 3. Portofolio                                   |");
            System.out.println("| 4. Logout                                       |");
            System.out.println("===================================================");
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
                case 4:
                    View view = new View();
                    view.mainView();
            }
        } while (choice < 1 || choice > 3);
    }

    public void showAllCustomerSaham(Customer customer) {
        int count = 0;

        System.out.println("===================================================");
        System.out.println("|             Saham yang Anda miliki              |");
        System.out.println("|=================================================|");
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if(customerSaham.getCustomerName().equals(customer.getName())) {
                count++;
                System.out.printf("| %2d | Kode saham  : %-29s|\n", count, customerSaham.getSaham().getCode());
                System.out.printf("|    | Jumlah saham: %-29s|\n", String.format("%,.2f", customerSaham.getQuantity()));
            }
        }
        if (count <= 0) {
            System.out.println("| Anda tidak memiliki saham tersisa!              |");
        }
        System.out.println("===================================================");
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

    public void customerSahamMenu(Customer customer) {
        int choice;

        showAllCustomerSaham(customer);
        do {
            System.out.println("===================================================");
            System.out.println("|              Customer - Menu Saham              |");
            System.out.println("|=================================================|");
            System.out.println("| 1. Jual Saham                                   |");
            System.out.println("| 2. Beli Saham                                   |");
            System.out.println("| 3. Kembali                                      |");
            System.out.println("===================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerSellSaham(customer);
                    break;
                case 2:
                    customerBuySaham(customer);
                    break;
                case 3:
                    customerSahamMenu(customer);
                default:
                    System.out.println("Pilihan tidak valid! Silahkan coba lagi.");
            }
        } while (choice < 1 || choice > 3);
    }

    public void customerSellSaham(Customer customer) {
        while (true) {
            showAllCustomerSaham(customer);

            System.out.println("===================================================");
            System.out.println("|              Customer - Jual Saham              |");
            System.out.println("|=================================================|");

            String sahamCode = input.inputNextLine("| Masukkan kode saham: ");
            CustomerSaham custSaham = sahamService.getCustomerSahamBySahamCode(sahamCode);

            if (custSaham == null) {
                System.out.println("Kode saham tidak ditemukan di portofolio Anda.");
                input.enterToContinue();
                if (!retry()) {
                    customerSahamMenu(customer);
                    return;
                }
                continue;
            }

            double quantity = input.inputNextDouble("| Masukkan jumlah saham: ");
            boolean qtyStatus = sahamService.checkCustomerSahamQuantity(quantity, custSaham.getQuantity());

            if (!qtyStatus) {
                System.out.printf("Jumlah saham tidak mencukupi. Anda hanya memiliki %.2f saham %s.\n",
                        custSaham.getQuantity(), custSaham.getSaham().getCode());
                if (!retry()) {
                    customerMenu(customer);
                    return;
                }
                continue;
            }

            boolean isFullySold = sahamService.customerSellSaham(sahamCode, quantity);

            System.out.println("===================================================");
            System.out.println("|            Penjualan Saham Berhasil!            |");
            System.out.println("===================================================");

            if (isFullySold) {
                System.out.printf("| Anda sudah tidak memiliki saham %-15s |\n", sahamCode);
                System.out.println("===================================================");
            } else {
                System.out.println("| Saldo Anda saat ini:                            |");
                System.out.printf("| Kode saham : %-34s |\n", sahamCode);
                System.out.printf("| Jumlah     : %-34s |\n", String.format("%,.2f", quantity));
                System.out.println("===================================================");
            }

            input.enterToContinue();
            customerSahamMenu(customer);
            return;
        }
    }

    public void customerBuySaham(Customer customer) {
        while (true) {
            showAllAvailableSaham();

            System.out.println("===================================================");
            System.out.println("|              Customer - Beli Saham              |");
            System.out.println("|=================================================|");

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

            boolean isSahamExists = false;
            double currSahamQuantity = -1;
            String currSahamCode = null;

            for (CustomerSaham customerSaham : DataStore.customerSaham) {
                if (customerSaham.getSaham().getCode().equals(sahamCode)) {
                    customerSaham.setQuantity(customerSaham.getQuantity() + quantity);

                    currSahamCode = customerSaham.getSaham().getCode();
                    currSahamQuantity = customerSaham.getQuantity();

                    isSahamExists = true;
                    break;
                }
            }

            if (!isSahamExists) {
                CustomerSaham newCustomerSaham = new CustomerSaham(customer.getName(), sahamToBuy, quantity);
                DataStore.customerSaham.add(newCustomerSaham);
            }

            System.out.println("===================================================");
            System.out.println("|            Pembelian Saham Berhasil!            |");
            System.out.println("===================================================");
            System.out.println("| Detail pembelian Saham:                         |");
            System.out.printf("| Kode saham : %-34s |\n", currSahamCode);
            System.out.printf("| Jumlah     : %-34s |\n", String.format("%,.2f", currSahamQuantity));
            System.out.println("===================================================");

            input.enterToContinue();
            customerSahamMenu(customer);
            return;
        }
    }

    public void showAllSBN() {
        int count = 0;

        System.out.println("===================================================");
        System.out.println("|        Surat Berharga Negara yang tersedia      |");
        System.out.println("|=================================================|");
        for(SBN sbn: DataStore.sbn) {
            count++;
            System.out.printf("| %2d | Nama SBN      : %-26s |\n", count, sbn.getName());
            System.out.printf("|    | Bunga (tahun) : %-26s |\n", String.format("%,.2f", sbn.getInterestRate()));
            System.out.printf("|    | Tanggal tempo : %-26s |\n", sbn.getTanggalJatuhTempo());
            System.out.printf("|    | Jangka waktu  : %-26s |\n", String.format("%2d", sbn.getJangkaWaktu()));
            System.out.printf("|    | Kuota nasional: %-26s |\n", sbn.getKuotaNasional());
        }
        System.out.println("|=================================================|");
    }

    public void showAllCustomerSBN(Customer customer) {
        int count = 0;

        System.out.println("===================================================");
        System.out.println("|     Surat Berharga Negara yang Anda miliki      |");
        System.out.println("|=================================================|");
        for (CustomerSBN customerSBN: DataStore.customerSBN) {
            if (customerSBN.getCustomerName().equals(customer.getName())) {
                count++;
                System.out.printf("| %2d | Nama SBN      : %-26s |\n", count, customerSBN.getSBN().getName());
                System.out.printf("|    | Bunga (tahun) : %-26s |\n", String.format("%,.2f", customerSBN.getSBN().getInterestRate()));
                System.out.printf("|    | Tanggal tempo : %-26s |\n", customerSBN.getSBN().getTanggalJatuhTempo());
                System.out.printf("|    | Jangka waktu  : %-26s |\n", String.format("%2d", customerSBN.getSBN().getJangkaWaktu()));
                System.out.printf("|    | Nominal       : %-26s |\n", customerSBN.getNominalInvestasi());
            };
        }
        System.out.println("|=================================================|");
//        input.enterToContinue();
    }

    public void customerSBNMenu(Customer customer) {
        int choice;

        showAllCustomerSBN(customer);
        do {
            System.out.println("===================================================");
            System.out.println("|               Customer - SBN Menu               |");
            System.out.println("|=================================================|");
            System.out.println("| 1. Beli Surat Berharga Negara                   |");
            System.out.println("| 2. Simulasi Surat Berharga Negara               |");
            System.out.println("| 3. Keluar                                       |");
            System.out.println("===================================================");
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

            boolean found = false;
            CustomerSBN currCustomerSBN = null;
            for (CustomerSBN customerSBN : DataStore.customerSBN) {
                if (customerSBN.getSBN().getName().equals(sbnName)) {
                    customerSBN.setNominalInvestasi(customerSBN.getNominalInvestasi() + nominal);
                    currCustomerSBN = customerSBN;
                    found = true;
                    break;
                }
            }

            if (!found) {
                currCustomerSBN = new CustomerSBN(customer.getName(), sbnToBuy, nominal);
                DataStore.customerSBN.add(currCustomerSBN);
            }

            System.out.println("===================================================");
            System.out.println("|     Pembelian Surat Berharga Negara Berhasil!   |");
            System.out.println("|=================================================|");
            System.out.println("| Detail pembelian Surat Berharga Negara:         |");
            System.out.printf("| Nama SBN      : %-35s |\n", currCustomerSBN.getSBN().getName());
            System.out.printf("| Bunga (tahun) : %-35s |\n", String.format("%,.2f", currCustomerSBN.getSBN().getInterestRate()));
            System.out.printf("| Tanggal tempo : %-35s |\n", currCustomerSBN.getSBN().getTanggalJatuhTempo());
            System.out.printf("| Jangka waktu  : %-35s |\n", String.format("%2d", currCustomerSBN.getSBN().getJangkaWaktu()));
            System.out.printf("| Nominal       : %-35s |\n", currCustomerSBN.getNominalInvestasi());
            System.out.println("===================================================");

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
            double monthlyRate = annualRate / 12;
            double kuponPerBulan = nominal * monthlyRate;
            double totalInterest = kuponPerBulan * jangkaWaktu;

            System.out.println("====================================================");
            System.out.println("|           Hasil Simulasi Investasi SBN           |");
            System.out.println("|==================================================|");
            System.out.printf("| %-22s : %-23s |\n", "Nama SBN", sbnToSimulate.getName());
            System.out.printf("| %-22s : Rp %,-20.2f |\n", "Nominal Investasi", nominal);
            System.out.printf("| %-22s : %-23s |\n", "Suku Bunga", String.format("%.2f%% per tahun", annualRate * 100));
            System.out.printf("| %-22s : %-23s |\n", "Jangka Waktu", jangkaWaktu + " bulan");
            System.out.printf("| %-22s : %-23s |\n", "Tanggal Jatuh Tempo", sbnToSimulate.getTanggalJatuhTempo());
            System.out.println("|--------------------------------------------------|");
            System.out.printf("| %-22s : Rp %,-20.2f |\n", "Kupon per Bulan", kuponPerBulan);
            System.out.printf("| %-22s : Rp %,-20.2f |\n", "Total Bunga", totalInterest);
            System.out.printf("| %-22s : Rp %,-20.2f |\n", "Total Nominal Investasi", nominal + totalInterest);
            System.out.println("====================================================");

            break;
        }

        input.enterToContinue();
        customerSBNMenu(customer);
    }

    public void customerPortofolio(Customer customer) {
        System.out.println("Saham yang Anda miliki: ");
        showAllCustomerSaham(customer);

        System.out.println("Surat Berharga Negara yang Anda miliki: ");
        showAllCustomerSBN(customer);

        input.enterToContinue();
        customerMenu(customer);
    }
}
