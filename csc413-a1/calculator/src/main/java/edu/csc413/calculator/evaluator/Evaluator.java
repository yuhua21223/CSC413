package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.exceptions.InvalidExpressionException;
import edu.csc413.calculator.operators.Operator;
import java.util.Stack;
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
        Stack<Operand> operandStack = new Stack<>();
        Stack<Operator> operatorStack = new Stack<>();

        // TODO: Set up data structures needed for operands and operators.
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        while (expressionTokenizer.hasMoreTokens()) {
            // Filter out whitespace.
            String tokenExpresion = expressionTokenizer.nextToken();
            if (tokenExpresion.trim().isEmpty()) {
                continue;
            }
            if (Operand.isValid(tokenExpresion)) {
                // TODO: Implement this.
                Operand operandToken = new Operand(tokenExpresion);
                operandStack.push(operandToken);
            } else {

                Operator operatorToken = Operator.create(tokenExpresion);
                if (Operator.create(tokenExpresion) == null) {
                    System.out.println("Invalid Expression: " + tokenExpresion);
                    throw new InvalidExpressionException("Invalid Expression");
                }else {
                    if (!operatorStack.empty() && (operatorStack.peek().precedence() >= operatorToken.precedence())) {
                        Operator Top = operatorStack.pop();
                        Operand TopA = operandStack.pop();
                        Operand TopB = operandStack.pop();

                        operandStack.push(Top.execute(TopA, TopB));
                    }else if (tokenExpresion.charAt(0) == '+' || tokenExpresion.charAt(0) == '-'
                            || tokenExpresion.charAt(0) == '*' || tokenExpresion.charAt(0) == '/') {
                        // While top of 'ops' has same or greater precedence to current
                        // token, which is an operator. Apply operator on top of 'ops'
                        // to top two elements in values stack
                        while (!ops.empty() && hasPrecedence(tokenExpresion.charAt(0), ops.peek())) {
                            if(ops.peek() == '+'){
                                values.push(new Operator.AddOperator().execute(new Operand(values.pop()), new Operand(values.pop())).getValue());
                            }
                            else if(ops.peek() == '-'){
                                values.push(new Operator.SubtractOperator().execute(new Operand(values.pop()), new Operand(values.pop())).getValue());
                            }
                            else if(ops.peek() == '*'){
                                values.push(new Operator.MultiplyOperator().execute(new Operand(values.pop()), new Operand(values.pop())).getValue());
                            }
                            else if(ops.peek() == '/'){
                                values.push(new Operator.DivideOperator().execute(new Operand(values.pop()), new Operand(values.pop())).getValue());
                            }
                            else if(ops.peek() == '^'){
                                values.push(new Operator.PowerOperator().execute(new Operand(values.pop()), new Operand(values.pop())).getValue());
                            }
                        }
                        // Push current token to 'ops'.
                        ops.push(tokenExpresion.charAt(0));
                    }

                }

                operatorStack.push(operatorToken);



            }
        }

        // We reach this point when all tokens in the expression string have been processed. At this point, if the
        // algorithm has been implemented correctly, we should expect to have some number of (partially processed)
        // operands and operators in their corresponding stacks.
        // TODO: Implement this.
        while (!operatorStack.empty()) {   //This portion the same
            Operator Top = operatorStack.pop();

            Operand TopA = operandStack.pop();
            Operand TopB = operandStack.pop();

            Operand Answer = Top.execute(TopA, TopB);

            operandStack.push(Answer);

        }

        return operandStack.pop().getValue(); // difference here,
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }
}
