package utils;

import java.util.Scanner;

public class Input {
    public String inputNextLine(String prompt) {
        System.out.print(prompt);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        return input;
    }

    public int inputNextInt(String prompt) {
        System.out.print(prompt);

        Scanner scanner = new Scanner(System.in);
        int inputNumber = scanner.nextInt();
        scanner.close();

        return inputNumber;
    }

    public double inputNextDouble(String prompt) {
        System.out.print(prompt);

        Scanner scanner = new Scanner(System.in);
        double inputNumber = scanner.nextDouble();
        scanner.close();

        return inputNumber;
    }
}
