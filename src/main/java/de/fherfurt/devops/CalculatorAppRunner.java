package de.fherfurt.devops;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Steuert den Ablauf der Calculator-App (Endlosschleife, Moduswahl).
 */
public final class CalculatorAppRunner {
    /** Logger für die App-Ausgabe. */
    private static final Logger LOGGER = Logger.getLogger(
        CalculatorAppRunner.class.getName()
    );
    /** Wartezeit zwischen Berechnungen (ms). */
    private static final int SLEEP_MILLIS = 1000;

    /**
     * Privater Konstruktor (Utility-Klasse).
     */
    private CalculatorAppRunner() { }

    /**
     * Startet die Hauptschleife der App.
     * @param args Kommandozeilenargumente
     */
    public static void run(final String[] args) {
        final int argCount = 3;
        while (true) {
            if (CalculatorInputChecker.hasEnvOrArgs(args, argCount)) {
                CalculatorRunner.runWithEnvOrArgs(args, argCount);
                try {
                    Thread.sleep(SLEEP_MILLIS);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            try (Scanner scanner = new Scanner(System.in)) {
                while (CalculatorConsoleUI.runInteractive(scanner)) {
                    LOGGER.fine(
                        "Block intentionally left empty for Checkstyle."
                    );
                }
            }
            break;
        }
        LOGGER.info("Taschenrechner beendet");
    }
}
