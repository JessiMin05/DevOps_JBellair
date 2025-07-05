package de.fherfurt.devops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit-Tests for the Calculator class.
 */
class CalculatorTest {

    /**
     * Test operand with value -1.0.
     */
    private static final double NUM1 = -1.0d;
    /**
     * Test operand with value 1.5.
     */
    private static final double NUM15 = 1.5d;
    /**
     * Test operand with value 2.0.
     */
    private static final double NUM2 = 2.0d;
    /**
     * Test operand with value 3.0.
     */
    private static final double NUM3 = 3.0d;
    /**
     * Test operand with value 5.0.
     */
    private static final double NUM5 = 5.0d;
    /**
     * Test operand with value 6.0.
     */
    private static final double NUM6 = 6.0d;
    /**
     * Test operand with value 10.0.
     */
    private static final double NUM10 = 10.0d;

    @Test
    void testAddition() {
        assertEquals(NUM5, Calculator.calculate(NUM2, '+', NUM3));
    }

    @Test
    void testSubtraction() {
        assertEquals(NUM1, Calculator.calculate(NUM2, '-', NUM3));
    }

    @Test
    void testMultiplication() {
        assertEquals(NUM6, Calculator.calculate(NUM2, '*', NUM3));
    }

    @Test
    void testDivision() {
        assertEquals(NUM15, Calculator.calculate(NUM3, '/', NUM2));
    }

    @Test
    void testDivisionByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () ->
                Calculator.calculate(NUM10, '/', 0.0d));
        assertEquals("Division durch 0", exception.getMessage());
    }

    @Test
    void testMultiplicationByZero() {
        assertEquals(0.0d, Calculator.calculate(NUM2, '*', 0.0d));
    }
}
