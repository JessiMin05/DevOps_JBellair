
package de.fherfurt.devops;

/**
 * Utility for checking if environment variables or command-line
 * arguments are set.
 */
public final class CalculatorInputChecker {
    private CalculatorInputChecker() { }

    /**
     * Interface for environment variable access (for testability).
     */
    public interface EnvProvider {
        /**
         * Liefert den Wert einer Umgebungsvariablen.
         * @param name Name der Variable
         * @return Wert oder null
         */
        String getenv(String name);
    }

    /**
     * Default EnvProvider using System.getenv.
     */
    public static class DefaultEnvProvider implements EnvProvider {
        /**
         * Liefert den Wert einer Umgebungsvariablen.
         * @param name Name der Variable
         * @return Wert oder null
         */
        @Override
        public String getenv(final String name) {
            return System.getenv(name);
        }
    }

    /**
     * Checks if environment variables or command-line arguments are set.
     *
     * @param args Kommandozeilenargumente
     * @param argCount Erwartete Anzahl Argumente
     * @param envProvider Provider für Umgebungsvariablen (für Tests)
     * @return true, wenn Umgebungsvariablen oder Argumente gesetzt sind
     */
    public static boolean hasEnvOrArgs(
        final String[] args,
        final int argCount,
        final EnvProvider envProvider
    ) {
        boolean hasArgs = args != null && args.length == argCount;
        boolean hasEnv = envProvider.getenv("ZAHL1") != null
            && envProvider.getenv("ZAHL2") != null
            && envProvider.getenv("OP") != null;
        return hasArgs || hasEnv;
    }

    /**
     * Überladene Methode für Kompatibilität (nutzt System.getenv).
     * @param args Kommandozeilenargumente
     * @param argCount Erwartete Anzahl Argumente
     * @return true, wenn Umgebungsvariablen oder Argumente gesetzt sind
     */
    public static boolean hasEnvOrArgs(
        final String[] args, final int argCount
        ) {
        return hasEnvOrArgs(
            args,
            argCount,
            new DefaultEnvProvider()
        );
    }
}
