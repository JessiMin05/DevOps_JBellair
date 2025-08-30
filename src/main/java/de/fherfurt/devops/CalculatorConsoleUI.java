package de.fherfurt.devops;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Handles the user interaction for the calculator (interactive mode).
 */
public final class CalculatorConsoleUI {
    /** Logger für Konsolenausgaben. */
    private static final Logger LOGGER = Logger.getLogger(
        CalculatorConsoleUI.class.getName()
    );

    /**
     * Privater Konstruktor (Utility-Klasse).
     */
    private CalculatorConsoleUI() { }

    /**
     * Runs the interactive calculator loop.
     * @param scanner Eingabequelle
     * @return false if user types 'exit', true otherwise
     */
    public static boolean runInteractive(final Scanner scanner) {
        LOGGER.info("");
        LOGGER.info("Erste Zahl: ");
        String input1 = scanner.next();
        if (input1.equalsIgnoreCase("exit")) {
            return false;
        }
        double num1;
        try {
            num1 = Double.parseDouble(input1);
        } catch (final NumberFormatException e) {
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
        } catch (final NumberFormatException e) {
            LOGGER.warning("Bitte eine gültige Zahl eingeben!");
            return true;
        }

        if (operator == '/' && num2 == 0.0) {
            LOGGER.warning("Division durch null ist nicht erlaubt!");
        } else {
            String result = null;
            try {
                result = Calculator.calculateAndShow(
                    num1,
                    operator,
                    num2
                );
            } catch (final Exception e) {
                LOGGER.warning("Fehler: " + e.getMessage());
            }
            if (result != null) {
                if (LOGGER.isLoggable(Level.INFO)) {
                    LOGGER.info(String.format("Rechnung: %s", result));
                }
            }
        }
        return true;
    }
}
