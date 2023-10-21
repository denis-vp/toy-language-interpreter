package model.expression;

import datastructures.MyIDictionary;
import exception.MyException;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

import java.util.HashMap;
import java.util.function.IntBinaryOperator;

public class ArithmeticExpression implements Expression {
    private Expression e1;
    private Expression e2;
    private static final HashMap<Integer, IntBinaryOperator> operators = new HashMap<>();
    private int operator;

    public ArithmeticExpression(Expression e1, Expression e2, int operator) throws MyException {
        operators.put(1, Integer::sum);
        operators.put(2, (a, b) -> a - b);
        operators.put(3, (a, b) -> a * b);
        operators.put(4, (a, b) -> a / b);
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value v1, v2;
        v1 = e1.eval(table);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(table);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                try {
                    return new IntValue(ArithmeticExpression.operators.get(this.operator).applyAsInt(n1, n2));
                } catch (Exception e) {
                    throw new MyException("division by zero");
                }
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }
}
