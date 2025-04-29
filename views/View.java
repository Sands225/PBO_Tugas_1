package views;

import services.AuthService;
import utils.*;

public class View {
    public static void mainView() {
        Clear.clearScreen();
        int choice;

        do {
            System.out.println("=============================================================");
            System.out.println("|                                                           |");
            System.out.println("|         Investasi Saham dan Surat Berharga Negara         |");
            System.out.println("|                                                           |");
            System.out.println("|===========================================================|");
            System.out.println("| 1. Login                                                  |");
            System.out.println("| 2. Keluar dari Program                                    |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch(choice) {
                case 1:
                    loginView();
                    return;
                case 2:
                    System.out.println("Terima kasih telah menggunakan program kami!");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Pilihan tidak valid! Coba lagi");
            }
        } while (true);
    }

    public static void loginView() {
        AuthService authService = new AuthService();
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            Clear.clearScreen();
            System.out.println("=============================================================");
            System.out.println("|                           Login                           |");
            System.out.println("|                Masukkan Nama dan Password                 |");
            System.out.println("=============================================================");
            String inputName = Input.inputNextLine("Masukkan nama : ");
            String inputPassword = Input.inputNextLine("Masukkan password : ");

            isLoggedIn = authService.loginHandler(inputName, inputPassword);
        }
    }

    public static void greetUser(String username) {
        Clear.clearScreen();

        System.out.println("=============================================================");
        System.out.println("|                                                           |");
        System.out.printf("|                 Selamat Datang, %-22s    |\n", username);
        System.out.println("|                                                           |");
        System.out.println("=============================================================");
        Input.enterToContinue();
    }

    public static boolean retry() {
        int choice;

        do {
            System.out.println("=============================================================");
            System.out.println("| 1. Coba lagi                                              |");
            System.out.println("| 2. Kembali ke menu sebelumnya                             |");
            System.out.println("=============================================================");
            choice = Input.inputNextInt("Masukkan pilihan Anda: ");

            switch (choice) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        } while (true);
    }
}
