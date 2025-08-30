package de.fherfurt.devops;

import java.util.Scanner;
import java.util.logging.Logger;

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
    /** Logger for application output. */
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
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
            LOGGER.info(
                "Willkommen zu Ihrem Taschenrechner (Tippe 'exit' zum Beenden)"
            );
            boolean running = true;
            while (running) {
                if (handleEnvOrArgs(args, argCount, envUsed)) {
                    envUsed = true;
                    // Check if interactive input is possible
                    if (System.console() == null) {
                        LOGGER.info("No interactive terminal detected. Exiting after calculation.");
                        break;
                    }
                    continue;
                }
                running = handleInteractive(scanner);
            }
            LOGGER.info("Taschenrechner beendet");
        } finally {
            scanner.close();
        }
    }

    private static boolean handleEnvOrArgs(
            final String[] args, final int argCount, final boolean envUsed) {
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
        boolean valid = (!envUsed
            && (
                (useArgs && argZahl1 != null && argZahl2 != null && argOp != null)
                || (zahl1 != null && zahl2 != null
                    && op != null)
            )
        );
        if (!valid) {
            return false;
        }
        String usedZahl1 = useArgs ? argZahl1 : zahl1;
        String usedZahl2 = useArgs ? argZahl2 : zahl2;
        String usedOp = useArgs ? argOp : op;
        if (usedOp == null) {
            LOGGER.warning("Operator is null!");
            return false;
        }
        double num1 = Double.parseDouble(usedZahl1);
        double num2 = Double.parseDouble(usedZahl2);
        Double result = calculate(num1, num2, usedOp);
        if (result != null) {
            LOGGER.info(String.format(
                "Rechnung: %.1f %s %.1f = %.1f",
                num1, usedOp, num2, result
            ));
        }
        return true;
    }

    private static boolean handleInteractive(final Scanner scanner) {
        LOGGER.info("");
        LOGGER.info("Erste Zahl: ");
        String input1 = scanner.next();
        if (input1.equalsIgnoreCase("exit")) {
            return false;
        }
        double num1;
        try {
            num1 = Double.parseDouble(input1);
        } catch (NumberFormatException e) {
            LOGGER.warning("Bitte eine gültige Zahl eingeben!");
            return true;
        }

        LOGGER.info("Operator (+, -, *, /): ");
        String opInput = scanner.next();
        if (opInput.equalsIgnoreCase("exit")) {
            return false;
        }
        if (opInput.length() != 1 || "+-*/".indexOf(opInput.charAt(0)) == -1) {
            LOGGER.warning("Bitte einen gültigen Operator eingeben!");
            return true;
        }
        char operator = opInput.charAt(0);

        LOGGER.info("Zweite Zahl: ");
        String input2 = scanner.next();
        if (input2.equalsIgnoreCase("exit")) {
            return false;
        }
        double num2;
        try {
            num2 = Double.parseDouble(input2);
        } catch (NumberFormatException e) {
            LOGGER.warning("Bitte eine gültige Zahl eingeben!");
            return true;
        }

        try {
            String bill = Calculator.calculateAndShow(num1, operator, num2);
            LOGGER.info("Rechnung: " + bill);
        } catch (Exception e) {
            LOGGER.warning("Fehler: " + e.getMessage());
        }
        return true;
    }

    private static Double calculate(
            final double num1, final double num2, final String op) {
        switch (op) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num2 != 0 ? num1 / num2 : null;
            default:
                LOGGER.warning("Unbekannter Operator!");
                return null;
        }
    }
}
