package model.expression;

import datastructures.MyIDictionary;
import exception.MyException;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

import java.util.HashMap;
import java.util.function.BiPredicate;

public class LogicalExpression implements Expression {
    private Expression e1;
    private Expression e2;
    private static final HashMap<Integer, BiPredicate<Boolean, Boolean>> operators = new HashMap<>();
    private int operator;

    public LogicalExpression(Expression e1, Expression e2, int operator) {
        operators.put(1, (a, b) -> a && b);
        operators.put(2, (a, b) -> a || b);
        operators.put(3, (a, b) -> a ^ b);
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value v1, v2;
        v1 = e1.eval(table);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(table);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = b1.getValue();
                n2 = b2.getValue();
                return new BoolValue(LogicalExpression.operators.get(this.operator).test(n1, n2));
            } else
                throw new MyException("second operand is not a boolean");
        } else
            throw new MyException("first operand is not a boolean");
    }
}
