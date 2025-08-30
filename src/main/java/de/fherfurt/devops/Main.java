package de.fherfurt.devops;

import java.util.Scanner;

/**
 * Main class for the calculator application.
 * <p>
 * The program can perform calculations using 
 * environment variables (ZAHL1, ZAHL2, OP),
 * command-line arguments, or interactively via user input.
 * It runs in an endless loop
 * until the user explicitly exits.
 */
public final class Main {
    private Main() { }

    /**
     * Application entry point. Performs a calculation using
     *  environment variables, command-line arguments,
     * or interactive user input. The program continues to run
     * in a loop until the user types 'exit'.
     *
     * @param args If three arguments are provided, 
     *             they are used as first number, operator, and second number.
     */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean envUsed = false;
        try {
            System.out.println(
                "Willkommen zu Ihrem Taschenrechner (Tippe 'exit' zum Beenden)"
            );
            while (true) {
                String zahl1 = System.getenv("ZAHL1");
                String zahl2 = System.getenv("ZAHL2");
                String op = System.getenv("OP");

                boolean useArgs = (args.length == 3 && !envUsed);
                if (useArgs) {
                    zahl1 = args[0];
                    op = args[1];
                    zahl2 = args[2];
                }

                if (!envUsed && zahl1 != null && zahl2 != null && op != null) {
                    double num1 = Double.parseDouble(zahl1);
                    double num2 = Double.parseDouble(zahl2);
                    double result = 0;
                    switch (op) {
                        case "+" -> result = num1 + num2;
                        case "-" -> result = num1 - num2;
                        case "*" -> result = num1 * num2;
                        case "/" -> result = num1 / num2;
                        default -> {
                            System.out.println("Unbekannter Operator!");
                            break;
                        }
                    }
                    System.out.printf(
                        "Rechnung: %.1f %s %.1f = %.1f\n",
                        num1, op, num2, result
                    );
                    envUsed = true;
                    continue;
                }

                System.out.print("\nErste Zahl: ");
                String input1 = scanner.next();
                if (input1.equalsIgnoreCase("exit")) {
                    break;
                }
                double num1;
                try {
                    num1 = Double.parseDouble(input1);
                } catch (NumberFormatException e) {
                    System.out.println("Bitte eine gültige Zahl eingeben!");
                    continue;
                }

                System.out.print("Operator (+, -, *, /): ");
                String opInput = scanner.next();
                if (opInput.equalsIgnoreCase("exit")) {
                    break;
                }
                if (opInput.length() != 1
                        || "+-*/".indexOf(opInput.charAt(0)) == -1) {
                    System.out.println(
                        "Bitte einen gültigen Operator eingeben!"
                    );
                    continue;
                }
                char operator = opInput.charAt(0);

                System.out.print("Zweite Zahl: ");
                String input2 = scanner.next();
                if (input2.equalsIgnoreCase("exit")) {
                    break;
                }
                double num2;
                try {
                    num2 = Double.parseDouble(input2);
                } catch (NumberFormatException e) {
                    System.out.println("Bitte eine gültige Zahl eingeben!");
                    continue;
                }

                try {
                    String bill = Calculator.calculateAndShow(
                        num1, operator, num2
                    );
                    System.out.println(
                        "Rechnung: " + bill
                    );
                } catch (Exception e) {
                    System.out.println(
                        "Fehler: " + e.getMessage()
                    );
                }
            }
            System.out.println("Taschenrechner beendet");
        } finally {
            scanner.close();
        }
    }
}
