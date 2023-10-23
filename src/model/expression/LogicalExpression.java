package model.expression;

import datastructure.MyIDictionary;
import exception.DictionaryException;
import exception.ExpressionException;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

import java.util.HashMap;
import java.util.function.BiPredicate;

public class LogicalExpression implements Expression {
    private final Expression e1;
    private final Expression e2;
    private static final HashMap<Integer, BiPredicate<Boolean, Boolean>> operators = new HashMap<>();
    private static final HashMap<Integer, String> operatorsString = new HashMap<>();
    private final int operator;

    public LogicalExpression(Expression e1, Expression e2, int operator) {
        operators.put(1, (a, b) -> a && b);
        operatorsString.put(1, "&&");
        operators.put(2, (a, b) -> a || b);
        operatorsString.put(2, "||");
        operators.put(3, (a, b) -> a ^ b);
        operatorsString.put(3, "^");
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws ExpressionException {
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
                throw new ExpressionException("second operand is not a boolean");
        } else
            throw new ExpressionException("first operand is not a boolean");
    }

    @Override
    public Expression deepCopy() throws ExpressionException {
        return new LogicalExpression(this.e1.deepCopy(), this.e2.deepCopy(), this.operator);
    }

    public String toString() {
        return this.e1.toString() + " " + LogicalExpression.operatorsString.get(this.operator) + " " + this.e2.toString();
    }
}
