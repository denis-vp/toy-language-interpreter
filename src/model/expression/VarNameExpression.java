package model.expression;

import datastructure.MyIDictionary;
import exception.DictionaryException;
import model.value.Value;

public class VarNameExpression implements Expression {
    String id;

    public VarNameExpression(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws DictionaryException {
        return table.get(this.id);
    }

    @Override
    public Expression deepCopy() {
        return new VarNameExpression(this.id);
    }
}
