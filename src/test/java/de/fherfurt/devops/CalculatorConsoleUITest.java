package de.fherfurt.devops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.ArrayList;

class CalculatorConsoleUITest {

    /** Test operand with value 2.0. */
    private static final String NUM2 = "2.0";
    /** Test operand with value 3.0. */
    private static final String NUM3 = "3.0";
    /** Test operand with value 0.0. */
    private static final String NUM0 = "0.0";
    /** Operator for addition. */
    private static final String PLUS = "+";
    /** Operator for division. */
    private static final String DIV = "/";
    /** Exit command. */
    private static final String EXIT = "exit";
    /** Newline separator. */
    private static final String NL = "\n";

    @Test
    void runInteractiveAddition() {
        String input = NUM2 + NL + PLUS + NL + NUM3 + NL + EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void runInteractiveExitFirst() {
        String input = EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertFalse(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void runInteractiveInvalidNumber() {
        String input = "foo" + NL + EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void runInteractiveInvalidOperator() {
        String input = NUM2 + NL + "x" + NL + EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void runInteractiveInvalidSecondNumber() {
        String input = NUM2 + NL + PLUS + NL + "foo" + NL + EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void runInteractiveCalculateThrowsException() {
        // Division durch 0 löst Exception in Calculator aus, Fehlerpfad wird getestet
        String input = NUM2 + NL + DIV + NL + NUM0 + NL + EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertTrue(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void runInteractiveInvalidOperatorLogsWarning() {
        Logger logger = Logger.getLogger(
            CalculatorConsoleUI.class.getName());
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
            String input = NUM2 + NL + "x" + NL + EXIT + NL;
            Scanner scanner = new Scanner(
                new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r ->
                r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue()
                && r.getMessage().contains("gültigen Operator")
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
    void runInteractiveInvalidFirstNumberLogsWarning() {
        Logger logger = Logger.getLogger(
            CalculatorConsoleUI.class.getName());
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
            String input = "foo" + NL + EXIT + NL;
            Scanner scanner = new Scanner(
                new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r ->
                r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue()
                && r.getMessage().contains("gültige Zahl")
            );
            Assertions.assertTrue(
                found,
                "Logger sollte Warnung für ungültige erste Zahl enthalten"
            );
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void runInteractiveInvalidSecondNumberLogsWarning() {
        Logger logger = Logger.getLogger(
            CalculatorConsoleUI.class.getName());
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
            String input = NUM2 + NL + PLUS + NL + "foo" + NL + EXIT + NL;
            Scanner scanner = new Scanner(
                new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r ->
                r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue()
                && r.getMessage().contains("gültige Zahl")
            );
            Assertions.assertTrue(
                found,
                "Logger sollte Warnung für ungültige zweite Zahl enthalten"
            );
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void runInteractiveCalculateThrowsExceptionLogsWarning() {
        Logger logger = Logger.getLogger(
            CalculatorConsoleUI.class.getName());
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
            String input = NUM2 + NL + DIV + NL + NUM0 + NL + EXIT + NL;
            Scanner scanner = new Scanner(
                new ByteArrayInputStream(input.getBytes()));
            CalculatorConsoleUI.runInteractive(scanner);
            boolean found = records.stream().anyMatch(r ->
                r.getLevel().intValue() >= java.util.logging.Level.WARNING.intValue()
                && r.getMessage().contains("Fehler")
            );
            Assertions.assertTrue(
                found,
                "Logger sollte Warnung für Exception beim Berechnen enthalten"
            );
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void runInteractiveExitAsOperator() {
        String input = NUM2 + NL + EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertFalse(CalculatorConsoleUI.runInteractive(scanner));
    }

    @Test
    void runInteractiveExitAsSecondNumber() {
        String input = NUM2 + NL + PLUS + NL + EXIT + NL;
        Scanner scanner = new Scanner(
            new ByteArrayInputStream(input.getBytes()));
        Assertions.assertFalse(CalculatorConsoleUI.runInteractive(scanner));
    }
}
