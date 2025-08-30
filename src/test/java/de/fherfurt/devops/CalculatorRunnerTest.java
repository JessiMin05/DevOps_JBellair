package de.fherfurt.devops;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Level;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorRunnerTest {
    /** Index for the first operand in the parameter array. */
    private static final int PARAM_INDEX_NUM1 = 0;
    /** Index for the operator in the parameter array. */
    private static final int PARAM_INDEX_OP = 1;
    /** Index for the second operand in the parameter array. */
    private static final int PARAM_INDEX_NUM2 = 2;
    /** Index for the result in the parameter array. */
    private static final int PARAM_INDEX_RESULT = 3;

    /** Number of required arguments. */
    private static final int ARG_COUNT = 3;
    /** Test operand with value 2.0. */
    private static final double NUM2 = 2.0d;
    /** Test operand with value 3.0. */
    private static final double NUM3 = 3.0d;
    /** Number of expected parameters in the LogRecord.*/
    private static final int PARAM_COUNT = 4;

    @Test
    void runWithEnvOrArgsLogsResultOnInfoLevel() {
        Logger logger = Logger.getLogger(CalculatorRunner.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override
            public void publish(final LogRecord logRecord) {
                records.add(logRecord);
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
                new String[]{
                    Double.toString(NUM2),
                    "+",
                    Double.toString(NUM3)
                }, ARG_COUNT
            );
            boolean found = records.stream()
                .anyMatch(r ->
                r.getLevel().intValue() == Level.INFO.intValue()
                && ("Rechnung: {0,number,#.0} {1} {2,number,#.0} = "
                    + "{3,number,#.0}").equals(r.getMessage())
                && r.getParameters() != null
                && r.getParameters().length == PARAM_COUNT
                && r.getParameters()[PARAM_INDEX_NUM1].equals(NUM2)
                && r.getParameters()[PARAM_INDEX_OP].equals("+")
                && r.getParameters()[PARAM_INDEX_NUM2].equals(NUM3)
                && r.getParameters()[PARAM_INDEX_RESULT]
                .equals(NUM2 + NUM3)
            );
            Assertions.assertTrue(
                found,
                "Logger sollte das Ergebnis der Rechnung mit korrektem "
                    + "Format-String und Parametern auf INFO loggen"
            );
        } finally {
            logger.removeHandler(handler);
        }
    }

    @Test
    void runWithEnvOrArgsValidArgs() {
        // Should not throw and should log result
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{
                    Double.toString(NUM2),
                    "+",
                    Double.toString(NUM3)
                },
                ARG_COUNT
            )
        );
    }

    @Test
    void runWithEnvOrArgsInvalidOperator() {
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{
                    Double.toString(NUM2),
                    "x",
                    Double.toString(NUM3)
                },
                ARG_COUNT
            )
        );
    }

    @Test
    void runWithEnvOrArgsInvalidNumber() {
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{
                    "foo",
                    "+",
                    Double.toString(NUM3)
                },
                ARG_COUNT
            )
        );
    }

    @Test
    void runWithEnvOrArgsMissingArgs() {
        Assertions.assertDoesNotThrow(() ->
            CalculatorRunner.runWithEnvOrArgs(
                new String[]{},
                ARG_COUNT
            )
        );
    }

    @Test
    void runWithEnvOrArgsMultiCharOperatorLogsWarning() {
        Logger logger = Logger.getLogger(CalculatorRunner.class.getName());
        ArrayList<LogRecord> records = new ArrayList<>();
        Handler handler = new Handler() {
            @Override
            public void publish(final LogRecord logRecord) {
                records.add(logRecord);
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
                new String[]{
                    Double.toString(NUM2),
                    "++",
                    Double.toString(NUM3)
                }, ARG_COUNT
            );
            boolean found = records.stream().anyMatch(r ->
                r.getLevel().intValue() >= Level.WARNING.intValue()
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
