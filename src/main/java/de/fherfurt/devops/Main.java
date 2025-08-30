package de.fherfurt.devops;

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
        LOGGER.info(
            "Willkommen zu Ihrem Taschenrechner (Tippe 'exit' zum Beenden)"
        );
        CalculatorAppRunner.run(args);
    }

    // Input-Check ausgelagert nach CalculatorInputChecker

    // Berechnung mit Umgebungsvariablen/Kommandozeilenargumenten ausgelagert

    // Interaktive Logik ausgelagert nach CalculatorConsoleUI

    // Berechnungslogik ausgelagert nach Calculator
}
