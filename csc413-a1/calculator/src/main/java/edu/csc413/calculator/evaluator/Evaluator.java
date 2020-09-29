package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.exceptions.InvalidExpressionException;

import java.util.StringTokenizer;

/** Class containing functionality for evaluating arithmetic expressions. */
public class Evaluator {
    // Delimiter characters.
    private static final String DELIMITERS = " +-*/^()";

    /**
     * Evaluates an arithmetic expression and returns the result. The expression may contain parentheses.
     *
     * @param expression The arithmetic expression as a string
     * @return The integer result of evaluating the arithmetic expression
     * @throws InvalidExpressionException The expression provided is invalid
     */
    public int evaluateExpression(String expression) throws InvalidExpressionException {
        // If there are any parentheses in the expression, we will evaluate the expression inside a matching pair and
        // replace the entire parenthetical with a single operand value. For example, for the expression
        // "2 * (3 + 4) - 5", we will separately evaluate "3 + 4", and update the expression to "2 * 7 - 5".
        while (expression.contains("(") || expression.contains(")")) {
            // In order to make sure we find a parenthetical expression without more nested parentheses inside, we'll
            // look for the rightmost '('. If there are no '(' characters (if lastOpenIndex is -1), then the entire
            // expression is invalid due to an imbalance in parentheses characters.
            int lastOpenIndex = expression.lastIndexOf('(');
            if (lastOpenIndex == -1) {
                throw new InvalidExpressionException("Mismatched parentheses.");
            }

            // Once the rightmost '(' is found, there must be at least one ')' character that appears later in the
            // expression, or else the entire expression is invalid (due to no matching closing parenthesis). We'll find
            // the index of the first one that appears, which is the matching one.
            int matchingCloseIndex = expression.indexOf(')', lastOpenIndex);
            if (matchingCloseIndex == -1) {
                throw new InvalidExpressionException("Mismatched parentheses.");
            }

            // The method we used above to find lastOpenIndex and matchingCloseIndex ensures that there are no
            // parentheses between the two, so it can be evaluated as a simple arithmetic expression. One possible
            // invalid case is "()" appearing in the expression, which is invalid.
            String subExpression = expression.substring(lastOpenIndex + 1, matchingCloseIndex);
            if (subExpression.isEmpty()) {
                throw new InvalidExpressionException("Invalid '()' in expression.");
            }
            int subExpressionValue = evaluateSimpleExpression(subExpression);

            // We can replace the entire sub-expression (removing both '(' and ')' in the process) with its evaluated
            // integer value. The result is that expression should be simpler, with one pair of parentheses removed.
            expression =
                    String.format(
                            "%s %d %s",
                            expression.substring(0, lastOpenIndex),
                            subExpressionValue,
                            expression.substring(matchingCloseIndex + 1));
        }

        // If the while loop exits, then there are no more parentheses characters in the expression, so
        // evaluateSimpleExpression should be able to process it.
        return evaluateSimpleExpression(expression);
    }

    /**
     * Evaluates a simple arithmetic expression and returns the result. The expression will not contain any parentheses.
     *
     * @param expression The arithmetic expression as a string
     * @return The integer result of evaluating the arithmetic expression
     * @throws InvalidExpressionException The expression provided is invalid
     */
    public int evaluateSimpleExpression(String expression) throws InvalidExpressionException {
        // The third argument is true to indicate that the delimiters should be used as tokens, too.
        StringTokenizer expressionTokenizer = new StringTokenizer(expression, DELIMITERS, true);

        // TODO: Set up data structures needed for operands and operators.

        while (expressionTokenizer.hasMoreTokens()) {
            // Filter out whitespace.
            String expressionToken = expressionTokenizer.nextToken();
            if (expressionToken.trim().isEmpty()) {
                continue;
            }

            // Check if the token is an operand, operator, or parentheses.
            if (Operand.isValid(expressionToken)) {
                // TODO: Implement this.
            } else {
                // TODO: Implement this.
            }
        }

        // We reach this point when all tokens in the expression string have been processed. At this point, if the
        // algorithm has been implemented correctly, we should expect to have some number of (partially processed)
        // operands and operators in their corresponding stacks.
        // TODO: Implement this.
        return 0;
    }
}
