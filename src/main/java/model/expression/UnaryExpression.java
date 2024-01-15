package model.expression;

import adt.IDictionary;
import adt.IHeap;
import exception.ExpressionException;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class UnaryExpression implements Expression {
    private final String operator;
    private final Expression expression;
    private static final Map<String, UnaryOperator<Boolean>> booleanOperators = new HashMap<>(
            Map.of("!", (a) -> !a)
    );
    private static final Map<String, UnaryOperator<Integer>> integerOperators = new HashMap<>(
            Map.of("--", (a) -> a - 1, "++", (a) -> a + 1, "~", (a) -> ~a)
    );

    public UnaryExpression(String operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException {
        Value value = this.expression.eval(symbolTable, heap);
        if (value.getType().equals(new BoolType())) {
            return new BoolValue(UnaryExpression.booleanOperators.get(this.operator).apply(((BoolValue) value).getValue()));
        }
        if (value.getType().equals(new IntType())) {
            return new IntValue(UnaryExpression.integerOperators.get(this.operator).apply(((IntValue) value).getValue()));
        }
        throw new ExpressionException("invalid operand type");
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException {
        if (!booleanOperators.containsKey(this.operator) && !integerOperators.containsKey(this.operator)) {
            throw new ExpressionException("invalid operator");
        }

        Type type = this.expression.typeCheck(typeEnvironment);
        if (type.equals(new BoolType())) {
            return new BoolType();
        }
        if (type.equals(new IntType())) {
            return new IntType();
        }
        throw new ExpressionException("invalid operand type");
    }

    @Override
    public Expression deepCopy() {
        return new UnaryExpression(this.operator, this.expression.deepCopy());
    }

    public String toString() {
        if (this.operator.equals("--") || this.operator.equals("++")) {
            return this.expression.toString() + this.operator;
        } else if (this.operator.equals("!") || this.operator.equals("~")) {
            return this.operator + this.expression.toString();
        }
        return "";
    }
}
