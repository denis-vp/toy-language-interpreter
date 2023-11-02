package model.expression;

import adt.IDictionary;
import adt.IHeap;
import exception.ExpressionException;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException;

    Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException;

    Expression deepCopy();
}
