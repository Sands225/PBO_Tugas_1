package views;

import data.DataStore;
import models.*;
import services.SahamService;
import services.SBNService;
import utils.*;

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
                System.out.printf("| %2d | Kode saham  : %-31s|\n", count, customerSaham.getSaham().getCode());
                System.out.printf("|    | Jumlah saham: %-29s|\n", String.format("%,.2f", customerSaham.getQuantity()));
            }
        }
        System.out.println("===================================================");
    }

    public void showAllAvailableSaham() {
        System.out.println("Saham yang tersedia: ");
        for (Saham saham: DataStore.saham) {
            System.out.println("Kode saham: " + saham.getCode());
            System.out.println("Perusahaan: " + saham.getCompany());
            System.out.println("Harga saham: " + saham.getPrice());
        }
    }

    public void showCustomerSaham(Customer customer) {
        System.out.println("Saham yang Anda: ");
    }

    public void customerSahamMenu(Customer customer) {
        int choice;

        showAllCustomerSaham(customer);
        do {
            System.out.println("1. Jual Saham");
            System.out.println("2. Beli Saham");
            System.out.println("3. Keluar");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerSellSaham(customer);
                    break;
                case 2:
                    customerBuySaham(customer);
                    break;
                case 3:
                    View view = new View();
                    view.mainView();
            }
        } while (choice < 1 || choice > 3);
    }

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

    public void customerSellSaham(Customer customer) {
        while (true) {
            showAllCustomerSaham(customer);

            String sahamCode = input.inputNextLine("Masukkan kode saham: ");
            CustomerSaham custSaham = sahamService.checkCustomerSahamCode(sahamCode);

            if (custSaham == null) {
                System.out.println("Kode saham tidak ditemukan di portofolio Anda.");
                if (!retry()) {
                    customerMenu(customer);
                    return;
                }
                continue;
            }

            double quantity = input.inputNextDouble("Masukkan jumlah saham: ");
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

            sahamService.customerSellSaham(sahamCode, quantity, customer);
            customerMenu(customer);
            return;
        }
    }

    public void customerBuySaham(Customer customer) {
        while (true) {
            showAllAvailableSaham();
            String sahamCode = input.inputNextLine("Masukkan kode saham: ");
            Saham sahamToBuy = sahamService.getSahamByCode(sahamCode);

            if (sahamToBuy == null) {
                System.out.println("Kode saham tidak ditemukan.");
                if (!retry()) {
                    customerMenu(customer);
                    return;
                }
                continue;
            }

            double quantity = input.inputNextDouble("Masukkan jumlah saham yang ingin dibeli: ");
            if (quantity <= 0) {
                System.out.println("Jumlah saham harus lebih dari 0.");
                if (!retry()) {
                    customerMenu(customer);
                    return;
                }
                continue;
            }

            boolean isSahamExists = false;

            for (CustomerSaham customerSaham : DataStore.customerSaham) {
                if (customerSaham.getSaham().getCode().equals(sahamCode)) {
                    customerSaham.setQuantity(customerSaham.getQuantity() + quantity);
                    isSahamExists = true;
                    break;
                }
            }

            if (!isSahamExists) {
                CustomerSaham newCustomerSaham = new CustomerSaham(customer.getName(), sahamToBuy, quantity);
                DataStore.customerSaham.add(newCustomerSaham);
            }

            System.out.println("Saham berhasil ditambahkan!");
            customerMenu(customer);
            return;
        }
    }

    public void showAllSBN() {
        for(SBN sbn: DataStore.sbn) {
            System.out.println("Nama Surat Berharga Negara: " + sbn.getName());
            System.out.println("Interest rate: " + sbn.getInterestRate());
            System.out.println("Tanggal jatuh tempo: " + sbn.getTanggalJatuhTempo());
            System.out.println("Jangka waktu: " + sbn.getJangkaWaktu());
            System.out.println("Jumlah kuota nasional: " + sbn.getKuotaNasional());
        }
    }

    public void showAllCustomerSBN(Customer customer) {
        for (CustomerSBN customerSBN: DataStore.customerSBN) {
            if (customerSBN.getCustomerName().equals(customer.getName())) {
                System.out.println("Nama Surat Berharga Negara: " + customerSBN.getSBN().getName());
                System.out.println("Interest rate: " + customerSBN.getSBN().getInterestRate());
                System.out.println("Tanggal jatuh tempo: " + customerSBN.getSBN().getTanggalJatuhTempo());
                System.out.println("Jangka waktu: " + customerSBN.getSBN().getJangkaWaktu());
                System.out.println("Jumlah kuota nasional: " + customerSBN.getSBN().getKuotaNasional());
                System.out.println("Jumlah nominal investasi: " + customerSBN.getNominalInvestasi());
            };
        }
    }

    public void customerSBNMenu(Customer customer) {
        int choice;

        showAllCustomerSBN(customer);
        do {
            System.out.println("1. Beli Surat Berharga Negara");
            System.out.println("2. Simulasi Surat Berharga Negara");
            System.out.println("3. Keluar");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

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
            String sbnName = input.inputNextLine("Masukkan nama SBN: ");
            SBN sbnToBuy = sbnService.getSBNByName(sbnName);

            if (sbnToBuy == null) {
                System.out.println("Nama SBN tidak ditemukan.");
                if (!retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            double nominal = input.inputNextDouble("Masukkan jumlah nominal pembelian SBN: ");
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
            for (CustomerSBN customerSBN : DataStore.customerSBN) {
                if (customerSBN.getSBN().getName().equals(sbnName)) {
                    customerSBN.setNominalInvestasi(customerSBN.getNominalInvestasi() + nominal);
                    found = true;
                    break;
                }
            }

            if (!found) {
                CustomerSBN newCustomerSBN = new CustomerSBN(customer.getName(), sbnToBuy, nominal);
                DataStore.customerSBN.add(newCustomerSBN);
            }

            System.out.printf("Berhasil membeli SBN %s sebesar %.2f\n", sbnName, nominal);
            break;
        }
    }

    public void customerSBNSimulation(Customer customer) {
        while (true) {
            showAllSBN();
            String sbnName = input.inputNextLine("Masukkan nama SBN: ");
            SBN sbnToSimulate = sbnService.getSBNByName(sbnName);

            if (sbnToSimulate == null) {
                System.out.println("Nama SBN tidak ditemukan.");
                if (!retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            double nominal = input.inputNextDouble("Masukkan jumlah nominal SBN: ");
            boolean isNominalValid = sbnService.checkNominalInvestasi(sbnToSimulate, nominal);

            if (!isNominalValid) {
                System.out.println("Nominal tidak boleh melebihi kuota nasional");
                if (!retry()) {
                    customerSBNMenu(customer);
                    return;
                }
                continue;
            }

            double totalInterest = sbnService.calculateInterest(sbnToSimulate, nominal);
            System.out.println("=====================================================");
            System.out.println("| Hasil Simulasi Investasi SBN:                     |");
            System.out.println("|---------------------------------------------------|");
            System.out.printf("| Nama SBN           : %-30s |\n", sbnToSimulate.getName());
            System.out.printf("| Nominal Investasi  : Rp. %-27s |\n", String.format("%,.2f", nominal));
            System.out.printf("| Suku Bunga         : %-30s %% per bulan |\n", String.format("%.2f", sbnToSimulate.getInterestRate() * 100));
            System.out.printf("| Jangka Waktu       : %-30s bulan |\n", sbnToSimulate.getJangkaWaktu() );
            System.out.printf("| Tanggal Jatuh Tempo: %-30s |\n", sbnToSimulate.getTanggalJatuhTempo());
            System.out.println("| --------------------------------------------------");
            System.out.printf("| Kupon per Bulan    : Rp. %-27s |\n", String.format("%,.2f", totalInterest));
            System.out.printf("| Total Bunga        : Rp. %-27s |\n", String.format("%,.2f", totalInterest * sbnToSimulate.getJangkaWaktu()));
            System.out.println("==================================================");
            break;
        }

        customerSBNSimulation(customer);
    }

    public void customerPortofolio(Customer customer) {
        System.out.println("Saham yang Anda miliki: ");
        showAllCustomerSaham(customer);

        System.out.println("Surat Berharga Negara yang Anda miliki: ");
        showAllCustomerSBN(customer);

        customerMenu(customer);
    }
}
