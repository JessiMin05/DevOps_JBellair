package de.fherfurt.devops;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorRunnerTest {
    /** Number of required arguments. */
    private static final int ARG_COUNT = 3;
    /** Test operand with value 2.0. */
    private static final double NUM2 = 2.0d;
    /** Test operand with value 3.0. */
    private static final double NUM3 = 3.0d;

    @Test
    void runWithEnvOrArgsValidArgs() {
        // Should not throw and should log result
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{Double.toString(NUM2), "+", Double.toString(NUM3)},
                ARG_COUNT)
        );
    }

    @Test
    void runWithEnvOrArgsInvalidOperator() {
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{Double.toString(NUM2), "x", Double.toString(NUM3)},
                ARG_COUNT)
        );
    }

    @Test
    void runWithEnvOrArgsInvalidNumber() {
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{"foo", "+", Double.toString(NUM3)},
                ARG_COUNT)
        );
    }

    @Test
    void runWithEnvOrArgsMissingArgs() {
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{}, ARG_COUNT)
        );
    }

    @Test
    void runWithEnvOrArgsInvalidOperatorLogsWarning() {
        Logger logger = Logger.getLogger(
            CalculatorRunner.class.getName()
        );
    ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override
            public void publish(final LogRecord record) {
                records.add(record);
            }
            @Override
            public void flush() { }
            @Override
            public void close() throws SecurityException { }
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{Double.toString(NUM2), "x", Double.toString(NUM3)},
                ARG_COUNT);
            boolean found = records.stream().anyMatch(r ->
                r.getLevel().intValue()
                    >= java.util.logging.Level.WARNING.intValue()
                    && r.getMessage().contains("Ungültiger Operator")
            );
            Assertions.assertTrue(
                found,
                "Logger sollte Warnung für ungültigen Operator enthalten"
            );
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void runWithEnvOrArgsMultiCharOperatorLogsWarning() {
        Logger logger = Logger.getLogger(CalculatorRunner.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override
            public void publish(final LogRecord record) {
                records.add(record);
            }
            @Override
            public void flush() { }
            @Override
            public void close() throws SecurityException { }
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{Double.toString(NUM2), "++", Double
                .toString(NUM3)}, ARG_COUNT);
            boolean found = records.stream().anyMatch(r ->
                r.getLevel().intValue()
                    >= java.util.logging.Level.WARNING.intValue()
                    && r.getMessage().contains("Ungültiger Operator")
            );
            Assertions.assertTrue(
                found,
                "Logger sollte Warnung für mehrstelligen "
                    + "ungültigen Operator enthalten"
            );
        } finally {
            logger.removeHandler(handler);
        }
    }
}
