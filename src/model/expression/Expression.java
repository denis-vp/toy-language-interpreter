package model.expression;

import datastructure.MyIDictionary;
import exception.ExpressionException;
import model.value.Value;

public interface Expression {
    Value eval(MyIDictionary<String, Value> table) throws ExpressionException;

    Expression deepCopy() throws ExpressionException;
}
