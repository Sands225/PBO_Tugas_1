package utils;

import java.util.Scanner;

public class Input {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputNextLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input tidak boleh kosong! Silakan coba lagi.");
                continue;
            }

            return input;
        }
    }

    public static int inputNextInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input tidak boleh kosong! Masukkan angka.");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Pilihan harus berupa angka!");
            }
        }
    }


    public static double inputNextDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input tidak boleh kosong! Masukkan angka desimal.");
                continue;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka desimal!");
            }
        }
    }

    public static void enterToContinue() {
        System.out.print("Tekan enter untuk melanjutkan...");
        scanner.nextLine();
        System.out.println(" ");
    }
}
