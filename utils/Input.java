package utils;

import java.util.Scanner;

public class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String inputNextLine(String prompt) {
        System.out.print(prompt);
        
        String input = scanner.nextLine();
        return input;
    }

    public int inputNextInt(String prompt) {
        System.out.print(prompt);

        int inputNumber = -1;
        try {
            inputNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Pilihan harus berupa angka!");
        }
        return inputNumber;
    }

    public double inputNextDouble(String prompt) {
        System.out.print(prompt);

        double inputNumber = -1;
        try {
            inputNumber = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka desimal!");
        }
        return inputNumber;
    }

    public void enterToContinue() {
        System.out.print("Tekan enter untuk melanjutkan...");
        scanner.nextLine();
        System.out.println(" ");
    }
}
