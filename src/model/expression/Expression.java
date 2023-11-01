package model.expression;

import adt.IDictionary;
import adt.IHeap;
import exception.ExpressionException;
import model.value.Value;

public interface Expression {
    Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException;

    Expression deepCopy();
}
