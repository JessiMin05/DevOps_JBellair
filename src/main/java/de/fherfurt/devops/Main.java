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
        try {
            System.out.println("Willkommen zu Ihrem Taschenrechner (Tippe 'exit' zum Beenden)");
            while (true) {
                System.out.print("\nErste Zahl: ");
                String input1 = scanner.next();
                if (input1.equalsIgnoreCase("exit")) break;
                double num1;
                try {
                    num1 = Double.parseDouble(input1);
                } catch (NumberFormatException e) {
                    System.out.println("Bitte eine gültige Zahl eingeben!");
                    continue;
                }

                System.out.print("Operator (+, -, *, /): ");
                String opInput = scanner.next();
                if (opInput.equalsIgnoreCase("exit")) break;
                if (opInput.length() != 1 || "+-*/".indexOf(opInput.charAt(0)) == -1) {
                    System.out.println("Bitte einen gültigen Operator eingeben!");
                    continue;
                }
                char operator = opInput.charAt(0);

                System.out.print("Zweite Zahl: ");
                String input2 = scanner.next();
                if (input2.equalsIgnoreCase("exit")) break;
                double num2;
                try {
                    num2 = Double.parseDouble(input2);
                } catch (NumberFormatException e) {
                    System.out.println("Bitte eine gültige Zahl eingeben!");
                    continue;
                }

                try {
                    String bill = Calculator.calculateAndShow(num1, operator, num2);
                    System.out.println("Rechnung: " + bill);
                } catch (Exception e) {
                    System.out.println("Fehler: " + e.getMessage());
                }
            }
            System.out.println("Taschenrechner beendet");
        } finally {
            scanner.close();
        }
    }
}
