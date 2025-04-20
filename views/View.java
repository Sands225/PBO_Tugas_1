package views;

import services.AuthService;
import utils.Input;

public class View {
    private Input input = new Input();

    public void mainView() {
        int choice;

        do {
            System.out.println("===================================================");
            System.out.println("|    Investasi Saham dan Surat Berharga Negara    |");
            System.out.println("===================================================");
            System.out.println("| [1] Login                                       |");
            System.out.println("| [2] Keluar dari Program                         |");
            System.out.println("===================================================");
            choice = input.inputNextInt("Masukkan pilihan Anda: ");

            switch(choice) {
                case 1:
                    loginView();
                    return;
                case 2:
                    System.out.println("Terima kasih telah menggunakan program kami!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid! Coba kembali");
//                    mainView();
            }
        } while (choice < 1 || choice > 3);
    }

    public void loginView() {
        AuthService authService = new AuthService();
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            String inputName = input.inputNextLine("Masukkan nama: ");
            String inputPassword = input.inputNextLine("Masukkan password: ");

            isLoggedIn = authService.loginHandler(inputName, inputPassword);
        }
    }
}
