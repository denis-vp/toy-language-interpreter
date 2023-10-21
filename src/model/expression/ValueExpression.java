package model.expression;

import datastructures.MyIDictionary;
import exception.MyException;
import model.value.Value;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return this.value;
    }
}
