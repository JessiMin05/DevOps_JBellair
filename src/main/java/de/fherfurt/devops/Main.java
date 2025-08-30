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
    /** Sleep duration in ms for automated mode. */
    private static final int SLEEP_MILLIS = 1000;
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
    final int argCount = 3;
        LOGGER.info(
            "Willkommen zu Ihrem Taschenrechner "
                + "(Tippe 'exit' zum Beenden)"
        );
        // Endlosschleife für Automatik-Modus
        while (true) {
            if (hasEnvOrArgs(args, argCount)) {
                handleEnvOrArgs(args, argCount);
                // Kurze Pause, damit die Schleife nicht zu schnell läuft
                try {
                    Thread.sleep(SLEEP_MILLIS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            // Interaktiver Modus, wenn keine Variablen/Argumente gesetzt
            try (Scanner scanner = new Scanner(System.in)) {
                while (handleInteractive(scanner)) {
                    // läuft bis exit
                    LOGGER.fine(
                        "Block intentionally left empty for Checkstyle."
                    );
                }
            }
            break;
        }
        LOGGER.info("Taschenrechner beendet");
    }

    /**
     * Prüft, ob Umgebungsvariablen oder Kommandozeilenargumente gesetzt sind.
     *
     * @param args Kommandozeilenargumente
     * @param argCount Erwartete Anzahl Argumente
     * @return true, wenn Umgebungsvariablen oder Argumente gesetzt sind
     */
    private static boolean hasEnvOrArgs(
        final String[] args, final int argCount
        ) {
        boolean hasArgs = args != null
            && args.length == argCount;
        boolean hasEnv = System.getenv("ZAHL1") != null
            && System.getenv("ZAHL2") != null
            && System.getenv("OP") != null;
        return hasArgs || hasEnv;
    }

    /**
     * Führt die Berechnung mit Umgebungsvariablen
     * oder Kommandozeilenargumenten aus.
     *
     * @param args Kommandozeilenargumente
     * @param argCount Erwartete Anzahl Argumente
     */
    private static void handleEnvOrArgs(
        final String[] args, final int argCount
        ) {
        String zahl1;
        String zahl2;
        String op;
        if (args != null && args.length == argCount) {
            zahl1 = args[0];
            op = args[1];
            zahl2 = args[2];
        } else {
            zahl1 = System.getenv("ZAHL1");
            zahl2 = System.getenv("ZAHL2");
            op = System.getenv("OP");
        }
        if (zahl1 == null || zahl2 == null || op == null) {
            LOGGER.warning(
                "Fehlende Werte für die "
                + "Berechnung."
            );
            return;
        }
        double num1;
        double num2;
        try {
            num1 = Double.parseDouble(zahl1);
            num2 = Double.parseDouble(zahl2);
        } catch (NumberFormatException e) {
            LOGGER.warning(
                "Ungültige Zahl: "
                + e.getMessage()
            );
            return;
        }
        Double result = calculate(num1, num2, op);
        if (result != null) {
            LOGGER.info(String.format(
                "Rechnung: %.1f %s %.1f = %.1f",
                num1, op, num2, result
            ));
        }
    }

    private static boolean handleInteractive(final Scanner scanner) {
    LOGGER.info(""); // Leere Zeile für bessere Lesbarkeit
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
        if (opInput.length() != 1
                || "+-*/".indexOf(opInput.charAt(0)) == -1) {
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
                // Block muss laut Checkstyle nicht leer sein
                break;
        }
        return null;
    }
}
