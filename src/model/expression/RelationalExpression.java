package model.expression;

import datastructure.MyIDictionary;
import exception.ExpressionException;
import model.type.IntType;
import model.value.IntValue;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class RelationalExpression implements Expression {
    private final Expression e1;
    private final Expression e2;
    private final Map<String, BiPredicate<Integer, Integer>> operators = new HashMap<>(
            Map.of("<", (a, b) -> a < b, "<=", (a, b) -> a <= b, "==", Integer::equals,
                    "!=", (a, b) -> !a.equals(b), ">", (a, b) -> a > b, ">=", (a, b) -> a >= b)
    );
    private final String operator;

    public RelationalExpression(String operator, Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws ExpressionException {
        Value v1 = e1.eval(table);
        if (!v1.getType().equals(new IntType())) {
            throw new ExpressionException("first operand is not an integer");
        }
        Value v2 = e2.eval(table);
        if (!v2.getType().equals(new IntType())) {
            throw new ExpressionException("second operand is not an integer");
        }

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1 = i1.getValue();
        int n2 = i2.getValue();

        return new IntValue(operators.get(this.operator).test(n1, n2) ? 1 : 0);
    }

    @Override
    public Expression deepCopy() throws ExpressionException {
        return new RelationalExpression(this.operator, this.e1.deepCopy(), this.e2.deepCopy());
    }
}
