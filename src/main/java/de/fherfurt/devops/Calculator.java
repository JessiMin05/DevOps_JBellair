package de.fherfurt.devops;

/**
 * A simple calculator for basic arithmetic operations.
 */
public class Calculator {
    private Calculator() { }
    /**
     * Performs an arithmetic operation on two numbers.
     * @param num1 First operand
     * @param operator Arithmetic operator (+, -, *, /)
     * @param num2 Second operand
     * @return The result of the operation
     */
    public static double calculate(final double num1,
                                   final char operator,
                                   final double num2) {
        return switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Division durch 0");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Ungültiger Operator: " + operator);
        };
    }
}
