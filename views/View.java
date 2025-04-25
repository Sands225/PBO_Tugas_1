package views;

import services.AuthService;
import utils.Input;

public class View {
    private static final Input INPUT = new Input();

    public void mainView() {
        int choice;

        do {
            System.out.println("===================================================");
            System.out.println("|    Investasi Saham dan Surat Berharga Negara    |");
            System.out.println("|=================================================|");
            System.out.println("| [1] Login                                       |");
            System.out.println("| [2] Keluar dari Program                         |");
            System.out.println("===================================================");
            choice = INPUT.inputNextInt("Masukkan pilihan Anda: ");

            switch(choice) {
                case 1:
                    loginView();
                    return;
                case 2:
                    System.out.println("Terima kasih telah menggunakan program kami!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid! Coba lagi");
            }
        } while (true);
    }

    public void loginView() {
        AuthService authService = new AuthService();
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            String inputName = INPUT.inputNextLine("Masukkan nama: ");
            String inputPassword = INPUT.inputNextLine("Masukkan password: ");

            isLoggedIn = authService.loginHandler(inputName, inputPassword);
        }
    }

    public void greetUser(String username) {
        System.out.println("===================================================");
        System.out.printf("|         Selamat Datang, %-20s    |\n", username);
        System.out.println("===================================================");
    }

    public static boolean retry() {
        int choice;

        do {
            System.out.println("===================================================");
            System.out.println("| 1. Coba lagi                                    |");
            System.out.println("| 2. Kembali ke menu sebelumnya                   |");
            System.out.println("===================================================");
            choice = INPUT.inputNextInt("Masukkan pilihan Anda: ");

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
}
