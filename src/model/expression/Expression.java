package model.expression;

import datastructures.MyIDictionary;
import exception.MyException;
import model.value.Value;

public interface Expression {
    Value eval(MyIDictionary<String, Value> table) throws MyException;
}
