package model.expression;

import datastructure.MyIDictionary;
import exception.DictionaryException;
import exception.ExpressionException;
import model.value.Value;

public interface Expression {
    Value eval(MyIDictionary<String, Value> table) throws ExpressionException, DictionaryException;

    Expression deepCopy() throws ExpressionException;
}
