package de.fherfurt.devops;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

class CalculatorInputCheckerTest {
    @Test
    void testHasEnvOrArgs_withArgs() {
        String[] args = {"1.0", "+", "2.0"};
        assertTrue(CalculatorInputChecker.hasEnvOrArgs(args, 3));
    }

    @Test
    void testHasEnvOrArgs_withNullArgs() {
        assertFalse(CalculatorInputChecker.hasEnvOrArgs(null, 3));
    }

    @Test
    void testHasEnvOrArgs_withEmptyArgs() {
        String[] args = {};
        assertFalse(CalculatorInputChecker.hasEnvOrArgs(args, 3));
    }

    @Test
    void testHasEnvOrArgs_withPartialArgs() {
        String[] args = {"1.0", "+"};
        assertFalse(CalculatorInputChecker.hasEnvOrArgs(args, 3));
    }

    @Test
    void testHasEnvOrArgs_withTooManyArgs() {
        String[] args = {"1.0", "+", "2.0", "EXTRA"};
        assertFalse(CalculatorInputChecker.hasEnvOrArgs(args, 3));
    }

    @Test
    void testHasEnvOrArgs_withEnvVars_allPresent() {
        CalculatorInputChecker.EnvProvider mockEnv = Mockito.mock(CalculatorInputChecker.EnvProvider.class);
        Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn("5.0");
        Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn("3.0");
        Mockito.when(mockEnv.getenv("OP")).thenReturn("+");
        assertTrue(CalculatorInputChecker.hasEnvOrArgs(new String[]{}, 3, mockEnv));
    }

    @Test
    void testHasEnvOrArgs_withEnvVars_missingOne() {
        CalculatorInputChecker.EnvProvider mockEnv = Mockito.mock(CalculatorInputChecker.EnvProvider.class);
        Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn("5.0");
        Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn(null);
        Mockito.when(mockEnv.getenv("OP")).thenReturn("+");
        assertFalse(CalculatorInputChecker.hasEnvOrArgs(new String[]{}, 3, mockEnv));
    }

    @Test
    void testHasEnvOrArgs_withEnvVars_allMissing() {
        CalculatorInputChecker.EnvProvider mockEnv = Mockito.mock(CalculatorInputChecker.EnvProvider.class);
        Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn(null);
        Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn(null);
        Mockito.when(mockEnv.getenv("OP")).thenReturn(null);
        assertFalse(CalculatorInputChecker.hasEnvOrArgs(new String[]{}, 3, mockEnv));
    }

    @Test
    void testHasEnvOrArgs_withArgsAndEnvVars_bothPresent() {
        String[] args = {"1.0", "+", "2.0"};
        CalculatorInputChecker.EnvProvider mockEnv = Mockito.mock(CalculatorInputChecker.EnvProvider.class);
        Mockito.when(mockEnv.getenv("ZAHL1")).thenReturn("5.0");
        Mockito.when(mockEnv.getenv("ZAHL2")).thenReturn("3.0");
        Mockito.when(mockEnv.getenv("OP")).thenReturn("+");
        // Should return true if either is present
        assertTrue(CalculatorInputChecker.hasEnvOrArgs(args, 3, mockEnv));
    }
}
