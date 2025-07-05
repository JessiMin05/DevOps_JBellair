package de.fherfurt.devops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Calculator class.
 *
 * This test class covers all basic arithmetic operations, including error cases,
 * for both the calculate and calculateAndShow methods.
 * It is designed to achieve high code coverage.
 */
class CalculatorTest {

    /** Test operand with value -1.0. */
    private static final double NUM1 = -1.0d;
    /** Test operand with value 1.5. */
    private static final double NUM15 = 1.5d;
    /** Test operand with value 2.0. */
    private static final double NUM2 = 2.0d;
    /** Test operand with value 3.0. */
    private static final double NUM3 = 3.0d;
    /** Test operand with value 5.0. */
    private static final double NUM5 = 5.0d;
    /** Test operand with value 6.0. */
    private static final double NUM6 = 6.0d;
    /** Test operand with value 10.0. */
    private static final double NUM10 = 10.0d;

    /**
     * Tests addition with the calculate method.
     */
    @Test
    void testAddition() {
        assertEquals(NUM5, Calculator.calculate(NUM2, '+', NUM3));
    }

    /**
     * Tests subtraction with the calculate method.
     */
    @Test
    void testSubtraction() {
        assertEquals(NUM1, Calculator.calculate(NUM2, '-', NUM3));
    }

    /**
     * Tests multiplication with the calculate method.
     */
    @Test
    void testMultiplication() {
        assertEquals(NUM6, Calculator.calculate(NUM2, '*', NUM3));
    }

    /**
     * Tests division with the calculate method.
     */
    @Test
    void testDivision() {
        assertEquals(NUM15, Calculator.calculate(NUM3, '/', NUM2));
    }

    /**
     * Tests division by zero with the calculate method and expects an ArithmeticException.
     */
    @Test
    void testDivisionByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () ->
                Calculator.calculate(NUM10, '/', 0.0d)
        );
        assertEquals("Division durch 0", exception.getMessage());
    }

    /**
     * Tests multiplication by zero with the calculate method.
     */
    @Test
    void testMultiplicationByZero() {
        assertEquals(0.0d, Calculator.calculate(NUM2, '*', 0.0d));
    }

    /**
     * Tests the calculateAndShow method for addition.
     */
    @Test
    void testCalculateAndShowAddition() {
        assertEquals(
                "2.0 + 3.0 = 5.0",
                Calculator.calculateAndShow(NUM2, '+', NUM3)
        );
    }

    /**
     * Tests the calculateAndShow method for subtraction.
     */
    @Test
    void testCalculateAndShowSubtraction() {
        assertEquals(
                "2.0 - 3.0 = -1.0",
                Calculator.calculateAndShow(NUM2, '-', NUM3)
        );
    }

    /**
     * Tests the calculateAndShow method for multiplication.
     */
    @Test
    void testCalculateAndShowMultiplication() {
        assertEquals(
                "2.0 * 3.0 = 6.0",
                Calculator.calculateAndShow(NUM2, '*', NUM3)
        );
    }

    /**
     * Tests the calculateAndShow method for division.
     */
    @Test
    void testCalculateAndShowDivision() {
        assertEquals(
                "3.0 / 2.0 = 1.5",
                Calculator.calculateAndShow(NUM3, '/', NUM2)
        );
    }

    /**
     * Tests the calculateAndShow method for division by zero and expects an ArithmeticException.
     */
    @Test
    void testCalculateAndShowDivisionByZero() {
        Exception exception = assertThrows(
                ArithmeticException.class,
                () -> Calculator.calculateAndShow(NUM3, '/', 0.0)
        );
        assertEquals("Division durch 0", exception.getMessage());
    }

    /**
     * Tests the calculateAndShow method for an invalid operator and expects an IllegalArgumentException.
     */
    @Test
    void testCalculateAndShowInvalidOperator() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> Calculator.calculateAndShow(NUM3, '%', NUM2)
        );
        assertEquals("Ungültiger Operator: %", exception.getMessage());
    }
}
