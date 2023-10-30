package model.expression;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.ExpressionException;
import model.value.Value;

public interface Expression {
    Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws ExpressionException;

    Expression deepCopy() throws ExpressionException;
}
