package de.fherfurt.devops;

import java.util.logging.Logger;

/**
 * Handles calculation using environment variables or command-line arguments.
 */
public final class CalculatorRunner {
    /** Logger für Berechnungs-Ausgaben. */
    private static final Logger LOGGER = Logger.getLogger(
        CalculatorRunner.class.getName()
    );

    /**
     * Privater Konstruktor (Utility-Klasse).
     */
    private CalculatorRunner() { }

    /**
     * Führt die Berechnung mit Umgebungsvariablen
     * oder Kommandozeilenargumenten aus.
     *
     * @param args Kommandozeilenargumente
     * @param argCount Erwartete Anzahl Argumente
     */
    public static void runWithEnvOrArgs(
        final String[] args,
        final int argCount
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
                "Fehlende Werte für die Berechnung."
            );
            return;
        }
        double num1;
        double num2;
        try {
            num1 = Double.parseDouble(zahl1);
            num2 = Double.parseDouble(zahl2);
        } catch (final NumberFormatException e) {
            LOGGER.log(
                java.util.logging.Level.WARNING,
                "Ungültige Zahl: {0}",
                e.getMessage()
            );
            return;
        }
        Double result = null;
        if (op.length() == 1) {
            try {
                result = Calculator.calculate(
                    num1,
                    op.charAt(0),
                    num2
                );
            } catch (final Exception e) {
                LOGGER.log(
                    java.util.logging.Level.WARNING,
                    "Fehler: {0}",
                    e.getMessage()
                );
            }
        } else {
            LOGGER.log(
                java.util.logging.Level.WARNING,
                "Ungültiger Operator: {0}",
                op
            );
        }
        if (result != null) {
            LOGGER.log(
                java.util.logging.Level.INFO,
                "Rechnung: {0,number,#.0} {1} {2,number,#.0} = {3,number,#.0}",
                new Object[]{
                    num1,
                    op,
                    num2,
                    result
                }
            );
        }
    }
}
