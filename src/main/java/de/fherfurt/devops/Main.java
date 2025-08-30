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
    * environment variables, command-line arguments,
     * or interactive user input. The program continues to run
     * in a loop until the user types 'exit'.
     *
    * @param args If three arguments are provided,
    *             they are used as first number, operator, and second number.
     */
    public static void main(final String[] args) {
    Scanner scanner = new Scanner(System.in);
    final int argCount = 3;
    boolean envUsed = false;
        try {
            System.out.println(
                "Willkommen zu Ihrem Taschenrechner (Tippe 'exit' zum Beenden)"
            );
            while (true) {
                String zahl1 = System.getenv("ZAHL1");
                String zahl2 = System.getenv("ZAHL2");
                String op = System.getenv("OP");


                boolean useArgs = (!envUsed && args.length == argCount);
                String argZahl1 = null;
                String argOp = null;
                String argZahl2 = null;
                if (useArgs) {
                    argZahl1 = args[0];
                    argOp = args[1];
                    argZahl2 = args[2];
                }

        if (!envUsed
            && ((useArgs && argZahl1 != null && argZahl2 != null && argOp != null)
            || (zahl1 != null && zahl2 != null
            && op != null))) {
                    String usedZahl1 = useArgs ? argZahl1 : zahl1;
                    String usedZahl2 = useArgs ? argZahl2 : zahl2;
                    String usedOp = useArgs ? argOp : op;
                    double num1 = Double.parseDouble(usedZahl1);
                    double num2 = Double.parseDouble(usedZahl2);
                    double result = 0;
                    switch (usedOp) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            result = num1 / num2;
                            break;
                        default:
                            System.out.println("Unbekannter Operator!");
                            break;
                    }
                    System.out.printf(
                        "Rechnung: %.1f %s %.1f = %.1f\n",
                        num1, usedOp, num2, result
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
