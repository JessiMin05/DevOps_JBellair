package de.fherfurt.devops;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.ArrayList;

class CalculatorConsoleUITest {
    @Test
    void testRunInteractive_addition() {
        String input = "2.0\n+\n3.0\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void testRunInteractive_exitFirst() {
        String input = "exit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertFalse(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void testRunInteractive_invalidNumber() {
        String input = "foo\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void testRunInteractive_invalidOperator() {
        String input = "2.0\nx\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void testRunInteractive_invalidSecondNumber() {
        String input = "2.0\n+\nfoo\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void testRunInteractive_calculateThrowsException() {
        // Division durch 0 löst Exception in Calculator aus, Fehlerpfad wird getestet
        String input = "2.0\n/\n0.0\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void testRunInteractive_invalidOperator_logsWarning() {
        // Logger abfangen
        Logger logger = Logger.getLogger(CalculatorConsoleUI.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) { records.add(record); }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            String input = "2.0\nx\nexit\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r -> r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue() && r.getMessage().contains("gültigen Operator"));
            assertTrue(found, "Logger sollte Warnung für ungültigen Operator enthalten");
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void testRunInteractive_invalidFirstNumber_logsWarning() {
        Logger logger = Logger.getLogger(CalculatorConsoleUI.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) { records.add(record); }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            String input = "foo\nexit\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r -> r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue() && r.getMessage().contains("gültige Zahl"));
            assertTrue(found, "Logger sollte Warnung für ungültige erste Zahl enthalten");
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void testRunInteractive_invalidSecondNumber_logsWarning() {
        Logger logger = Logger.getLogger(CalculatorConsoleUI.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) { records.add(record); }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            String input = "2.0\n+\nfoo\nexit\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r -> r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue() && r.getMessage().contains("gültige Zahl"));
            assertTrue(found, "Logger sollte Warnung für ungültige zweite Zahl enthalten");
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void testRunInteractive_calculateThrowsException_logsWarning() {
        Logger logger = Logger.getLogger(CalculatorConsoleUI.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) { records.add(record); }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        try {
            String input = "2.0\n/\n0.0\nexit\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r -> r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue() && r.getMessage().contains("Fehler"));
            assertTrue(found, "Logger sollte Warnung für Exception beim Berechnen enthalten");
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void testRunInteractive_exitAsOperator() {
        String input = "2.0\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertFalse(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void testRunInteractive_exitAsSecondNumber() {
        String input = "2.0\n+\nexit\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertFalse(CalculatorConsoleUI.runInteractive(scanner));
    }
}
