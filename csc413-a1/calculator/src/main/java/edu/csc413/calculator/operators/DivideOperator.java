package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class DivideOperator extends Operator{

    @Override
    public int precedence() {
        return 2;
    }

    @Override
    public Operand execute(Operand lhs, Operand rhs) {
        Operand op = new Operand(lhs.getValue() / rhs.getValue());
        return op;
    }
    
}
