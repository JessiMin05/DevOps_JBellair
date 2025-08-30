package de.fherfurt.devops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

class CalculatorInputCheckerTest {

    /** Number of required arguments. */
    private static final int ARG_COUNT = 3;
    /** Test operand with value 1.0. */
    private static final String NUM1 = "1.0";
    /** Test operand with value 2.0. */
    private static final String NUM2 = "2.0";
    /** Test operand with value 3.0. */
    private static final String NUM3 = "3.0";
    /** Test operand with value 5.0. */
    private static final String NUM5 = "5.0";

    @Test
    void hasEnvOrArgsWithArgs() {
    String[] args = {NUM1, "+", NUM2};
        Assertions.assertTrue(
            CalculatorInputChecker.hasEnvOrArgs(args, ARG_COUNT)
        );
    }

    @Test
    void hasEnvOrArgsWithNullArgs() {
        Assertions.assertFalse(
            CalculatorInputChecker.hasEnvOrArgs(null, ARG_COUNT)
        );
    }

    @Test
    void hasEnvOrArgsWithEmptyArgs() {
        String[] args = {};
        Assertions.assertFalse(
            CalculatorInputChecker.hasEnvOrArgs(args, ARG_COUNT)
        );
    }

    @Test
    void hasEnvOrArgsWithPartialArgs() {
    String[] args = {NUM1, "+"};
        Assertions.assertFalse(
            CalculatorInputChecker.hasEnvOrArgs(args, ARG_COUNT)
        );
    }

    @Test
    void hasEnvOrArgsWithTooManyArgs() {
    String[] args = {NUM1, "+", NUM2, "EXTRA"};
        Assertions.assertFalse(
            CalculatorInputChecker.hasEnvOrArgs(args, ARG_COUNT)
        );
    }

    @Test
    void hasEnvOrArgsWithEnvVarsAllPresent() {
        CalculatorInputChecker.EnvProvider mockEnv = Mockito
            .mock(CalculatorInputChecker.EnvProvider.class);
            Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn(NUM5);
            Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn(NUM3);
            Mockito.when(mockEnv.getenv("OP")).thenReturn("+");
            Assertions.assertTrue(
                CalculatorInputChecker.hasEnvOrArgs(
                    new String[]{}, ARG_COUNT, mockEnv)
                );
    }

    @Test
    void hasEnvOrArgsWithEnvVarsMissingOne() {
        CalculatorInputChecker.EnvProvider mockEnv = Mockito
            .mock(CalculatorInputChecker.EnvProvider.class);
            Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn(NUM5);
            Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn(null);
            Mockito.when(mockEnv.getenv("OP")).thenReturn("+");
            Assertions.assertFalse(
                CalculatorInputChecker.hasEnvOrArgs(
                    new String[]{}, ARG_COUNT, mockEnv)
                );
    }

    @Test
    void hasEnvOrArgsWithEnvVarsAllMissing() {
        CalculatorInputChecker.EnvProvider mockEnv = Mockito
            .mock(CalculatorInputChecker.EnvProvider.class);
            Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn(null);
            Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn(null);
            Mockito.when(mockEnv.getenv("OP")).thenReturn(null);
            Assertions.assertFalse(
                CalculatorInputChecker.hasEnvOrArgs(
                    new String[]{}, ARG_COUNT, mockEnv)
                );
    }

    @Test
    void hasEnvOrArgsWithArgsAndEnvVarsBothPresent() {
    String[] args = {NUM1, "+", NUM2};
    CalculatorInputChecker.EnvProvider mockEnv = Mockito
        .mock(CalculatorInputChecker.EnvProvider.class);
            Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn(NUM5);
            Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn(NUM3);
            Mockito.when(mockEnv.getenv("OP")).thenReturn("+");
            // Should return true if either is present
            Assertions.assertTrue(
                CalculatorInputChecker.hasEnvOrArgs(
                    args, ARG_COUNT, mockEnv)
                );
    }
}
