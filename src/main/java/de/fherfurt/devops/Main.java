package de.fherfurt.devops;

import java.util.Scanner;

public final class Main {
    private Main() { }
    /**
     * Entry point for user input and calculation.
     * @param args Command-line arguments
     */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Erste Zahl: ");
        double num1 = scanner.nextDouble();

        System.out.print("Operator (+, -, *, /): ");
        char operator = scanner.next().charAt(0);

        System.out.print("Zweite Zahl: ");
        double num2 = scanner.nextDouble();

        try {
            String bill = Calculator.calculateAndShow(num1, operator, num2);
            System.out.println("Rechnung: " + bill);
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
