package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

/**
 * Base class representing a mathematical operator used in an arithmetic expression. Every Operator has a precedence
 * which determines how expressions with multiple operands and operators should be evaluated.
 */
public abstract class Operator {
    /**
     * Returns the precedence of an Operator. Larger precedence values represent higher-priority operators that should
     * be evaluated before lower-priority ones.
     *
     * @return The precedence of an Operator as an integer.
     */
    public abstract int precedence();

    /**
     * Evaluates an expression by applying this operator to the provided operands.
     *
     * @param lhs Left-hand side operand for this operation
     * @param rhs Right-hand side operand for this operation
     * @return A single Operand representing the result of applying the operator
     */
    public abstract Operand execute(Operand lhs, Operand rhs);

    /**
     * Creates a new Operator of the correct subtype (e.g. AddOperator) for the given token (e.g. "+"). If the token
     * does not represent a valid operator, null is returned.
     *
     * @param token String representation of an operator; we assume that isValid has already been used to verify that
     *              the token is a valid operator
     * @return Operator An instantiation of the Operator subclass corresponding to token or null if the token is invalid
     */
    public static Operator create(String token) {
        if(token.charAt(0) == '+'){
            return new AddOperator();
        }
        else if(token.charAt(0) == '-'){
            return new SubtractOperator();
        }
        if(token.charAt(0) == '*'){
            return new MultiplyOperator();
        }
        if(token.charAt(0) == '/'){
            return new DivideOperator();
        }
        if(token.charAt(0) == '^'){
            return new PowerOperator();
        }
        return null;
    }
}
