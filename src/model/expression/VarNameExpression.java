package model.expression;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.DictionaryException;
import exception.ExpressionException;
import model.value.Value;

public class VarNameExpression implements Expression {
    String id;

    public VarNameExpression(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws ExpressionException {
        try {
            return symbolTable.get(this.id);
        } catch (DictionaryException e) {
            throw new ExpressionException(e.getMessage());
        }
    }

    @Override
    public Expression deepCopy() {
        return new VarNameExpression(this.id);
    }

    public String toString() {
        return this.id;
    }
}
