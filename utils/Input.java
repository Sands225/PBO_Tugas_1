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
//        int inputNumber = scanner.nextInt();
        return inputNumber;
    }

    public double inputNextDouble(String prompt) {
        System.out.print(prompt);

        double inputNumber = scanner.nextDouble();
        return inputNumber;
    }
}
