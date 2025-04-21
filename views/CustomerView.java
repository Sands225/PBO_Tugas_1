package views;

import data.DataStore;
import models.*;
import services.SahamService;
import utils.Input;
import views.View;

public class CustomerView {
    private final Input input = new Input();
    private final SahamService sahamService = new SahamService();

    public void customerMenu(Customer customer) {
        int choice;
        do {
            System.out.println("1. Saham");
            System.out.println("2. SBN");
            System.out.println("3. Logout");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    customerSahamMenu(customer);
                    break;
                case 2:
//                    customerSBNMenu(customer);
                    break;
                case 3:
                    View view = new View();
                    view.mainView();
            }
        } while (choice < 1 || choice > 3);
    }

    public void showAllCustomerSaham(Customer customer) {
        System.out.println("Saham yang Anda miliki: ");
        for (CustomerSaham customerSaham: DataStore.customerSaham) {
            if(customerSaham.getCustomerName().equals(customer.getName())) {
                System.out.println("Kode saham: " + customerSaham.getSaham().getCode());
                System.out.println("Jumlah saham: " + customerSaham.getQuantity());
            }
        }
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
            System.out.println("3. Kelar");
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
                if (!retry()) return;
                continue;
            }

            double quantity = input.inputNextDouble("Masukkan jumlah saham: ");
            boolean qtyStatus = sahamService.checkCustomerSahamQuantity(quantity, custSaham.getQuantity());

            if (!qtyStatus) {
                System.out.printf("Jumlah saham tidak mencukupi. Anda hanya memiliki %.2f saham %s.\n",
                        custSaham.getQuantity(), custSaham.getSaham().getCode());
                if (!retry()) return;
                continue;
            }

            sahamService.customerSellSaham(sahamCode, quantity, customer);
            customerMenu(customer);
            return;
        }
    }

    public void customerBuySaham(Customer customer) {
        showAllAvailableSaham();
        String sahamCode = input.inputNextLine("Masukkan kode saham: ");
        Saham sahamToBuy = sahamService.getSahamByCode(sahamCode);

        if (sahamToBuy == null) {
            System.out.println("Kode saham tidak ditemukan.");
            return;
        }

        double quantity = input.inputNextDouble("Masukkan jumlah saham yang ingin dibeli: ");
        if (quantity <= 0) {
            System.out.println("Jumlah saham harus lebih dari 0.");
            return;
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
    }
}
