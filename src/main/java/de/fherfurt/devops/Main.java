package de.fherfurt.devops;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Main {
    private Main() { }
    /**
     * Entry point for user input and calculation.
     * @param args Command-line arguments
     */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Taschenrechner Service ===");
        System.out.println("Geben Sie 'exit' ein, um den Service zu beenden.");
        System.out.println();
        
        while (true) {
            try {
                System.out.print("Erste Zahl (oder 'exit' zum Beenden): ");
                String input = scanner.next();
                
                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Service wird beendet. Auf Wiedersehen!");
                    break;
                }
                
                double num1;
                try {
                    num1 = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.println("Fehler: Ungültige Zahl eingegeben. Bitte versuchen Sie es erneut.");
                    continue;
                }

                System.out.print("Operator (+, -, *, /): ");
                char operator = scanner.next().charAt(0);

                System.out.print("Zweite Zahl: ");
                double num2;
                try {
                    num2 = scanner.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Fehler: Ungültige Zahl eingegeben. Bitte versuchen Sie es erneut.");
                    scanner.nextLine(); // Clear invalid input
                    continue;
                }

                try {
                    String bill = Calculator.calculateAndShow(num1, operator, num2);
                    System.out.println("Rechnung: " + bill);
                } catch (Exception e) {
                    System.out.println("Fehler: " + e.getMessage());
                }
                
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("Unerwarteter Fehler: " + e.getMessage());
                scanner.nextLine(); // Clear any remaining input
            }
        }
        
        scanner.close();
    }
}
