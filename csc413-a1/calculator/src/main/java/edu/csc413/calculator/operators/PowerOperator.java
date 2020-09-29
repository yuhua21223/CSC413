package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator{

    @Override
    public int precedence() {
        return 3;
    }

    @Override
    public Operand execute(Operand lhs, Operand rhs) {
        Operand op = new Operand((int)Math.pow(lhs.getValue() , rhs.getValue()));
        return op;
    }
    
}
