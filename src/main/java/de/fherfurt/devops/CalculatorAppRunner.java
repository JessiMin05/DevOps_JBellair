package de.fherfurt.devops;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Steuert den Ablauf der Calculator-App (Endlosschleife, Moduswahl).
 */
public final class CalculatorAppRunner {
    /** Wartezeit zwischen Berechnungen (ms). */
    private static final int SLEEP_MILLIS = 500;
    /** Logger für die App-Ausgabe. */
    private static final Logger LOGGER = Logger.getLogger(
        CalculatorAppRunner.class.getName()
    );

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
        // Env/Args mode: run for a fixed duration, then exit
    final int envModeDurationMillis = 10_000; // 10 Sekunden
        if (CalculatorInputChecker.hasEnvOrArgs(args, argCount)) {
            long start = System.currentTimeMillis();
            do {
                CalculatorRunner.runWithEnvOrArgs(args, argCount);
                try {
                    Thread.sleep(SLEEP_MILLIS); // 0.5 Sekunde
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } while (
                System.currentTimeMillis() - start
                    < envModeDurationMillis
            );
            LOGGER.info(
                "Taschenrechner beendet (env/args mode, Zeit abgelaufen)"
            );
            System.exit(0);
        }
        // Interactive mode: loop until user exits
        while (true) {
            try (Scanner scanner = new Scanner(System.in)) {
                while (CalculatorConsoleUI.runInteractive(scanner)) {
                    LOGGER.fine(
                        "Block intentionally left empty for Checkstyle."
                    );
                }
            }
            break;
        }
        LOGGER.info("Taschenrechner beendet (interaktiv)");
    }
}
