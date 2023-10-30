package model.expression;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.ExpressionException;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class LogicalExpression implements Expression {
    private final String operator;
    private final Expression e1;
    private final Expression e2;
    private static final Map<String, BiPredicate<Boolean, Boolean>> operators = new HashMap<>(
            Map.of("&&", (a, b) -> a && b, "||", (a, b) -> a || b, "^", (a, b) -> a ^ b)
    );

    public LogicalExpression(String operator, Expression e1, Expression e2) {
        this.operator = operator;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws ExpressionException {
        Value v1 = this.e1.eval(symbolTable, heap);
        if (!v1.getType().equals(new BoolType())) {
            throw new ExpressionException("first operand is not a boolean");
        }
        Value v2 = this.e2.eval(symbolTable, heap);
        if (!v2.getType().equals(new BoolType())) {
            throw new ExpressionException("second operand is not a boolean");
        }

        BoolValue b1 = (BoolValue) v1;
        BoolValue b2 = (BoolValue) v2;
        boolean n1 = b1.getValue();
        boolean n2 = b2.getValue();

        return new BoolValue(LogicalExpression.operators.get(this.operator).test(n1, n2));
    }

    @Override
    public Expression deepCopy() throws ExpressionException {
        return new LogicalExpression(this.operator, this.e1.deepCopy(), this.e2.deepCopy());
    }

    public String toString() {
        return this.e1.toString() + " " + this.operator + " " + this.e2.toString();
    }
}
