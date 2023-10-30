package model.expression;

import datastructure.MyIDictionary;
import exception.ExpressionException;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntBinaryOperator;

public class ArithmeticExpression implements Expression {
    private final String operator;
    private final Expression e1;
    private final Expression e2;
    private static final HashMap<String, IntBinaryOperator> operators = new HashMap<>(
            Map.of("+", Integer::sum, "-", (a, b) -> a - b, "*", (a, b) -> a * b, "/", (a, b) -> a / b,
                    "%", (a, b) -> a % b, "**", (a, b) -> (int) Math.pow(a, b))
    );

    public ArithmeticExpression(String operator, Expression e1, Expression e2) {
        this.operator = operator;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws ExpressionException {
        Value v1 = this.e1.eval(table);
        if (!v1.getType().equals(new IntType())) {
            throw new ExpressionException("first operand is not an integer");
        }
        Value v2 = this.e2.eval(table);
        if (!v2.getType().equals(new IntType())) {
            throw new ExpressionException("second operand is not an integer");
        }

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1 = i1.getValue();
        int n2 = i2.getValue();

        try {
            return new IntValue(ArithmeticExpression.operators.get(this.operator).applyAsInt(n1, n2));
        } catch (Exception e) {
            throw new ExpressionException("division by zero");
        }
    }

    @Override
    public Expression deepCopy() throws ExpressionException {
        return new ArithmeticExpression(this.operator, this.e1.deepCopy(), this.e2.deepCopy());
    }

    public String toString() {
        return this.e1.toString() + " " + this.operator + " " + this.e2.toString();
    }
}
