package model.expression;

import datastructures.MyIDictionary;
import exception.MyException;
import model.value.Value;

public class VarNameExpression implements Expression {
    String id;

    public VarNameExpression(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return table.get(this.id);
    }
}
