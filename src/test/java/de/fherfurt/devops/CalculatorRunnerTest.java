package de.fherfurt.devops;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalculatorRunnerTest {
    @Test
    void testRunWithEnvOrArgs_validArgs() {
        // Should not throw and should log result
        assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(new String[]{"2.0", "+", "3.0"}, 3)
        );
    }

    @Test
    void testRunWithEnvOrArgs_invalidOperator() {
        assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(new String[]{"2.0", "x", "3.0"}, 3)
        );
    }

    @Test
    void testRunWithEnvOrArgs_invalidNumber() {
        assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(new String[]{"foo", "+", "3.0"}, 3)
        );
    }

    @Test
    void testRunWithEnvOrArgs_missingArgs() {
        assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(new String[]{}, 3)
        );
    }
    
    @Test
    void testRunWithEnvOrArgs_invalidOperator_logsWarning() {
        Logger logger = Logger.getLogger(CalculatorRunner.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) { records.add(record); }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            CalculatorRunner.runWithEnvOrArgs(new String[]{"2.0", "x", "3.0"}, 3);
            boolean found = records.stream().anyMatch(r -> r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue() && r.getMessage().contains("Ungültiger Operator"));
            assertTrue(found, "Logger sollte Warnung für ungültigen Operator enthalten");
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void testRunWithEnvOrArgs_multiCharOperator_logsWarning() {
        Logger logger = Logger.getLogger(CalculatorRunner.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) { records.add(record); }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            CalculatorRunner.runWithEnvOrArgs(new String[]{"2.0", "++", "3.0"}, 3);
            boolean found = records.stream().anyMatch(r -> r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue() && r.getMessage().contains("Ungültiger Operator"));
            assertTrue(found, "Logger sollte Warnung für mehrstelligen ungültigen Operator enthalten");
        } finally {
            logger.removeHandler(handler);
        }
    }

}
