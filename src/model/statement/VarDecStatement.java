package model.statement;

import datastructure.MyIDictionary;
import exception.StatementException;
import model.programstate.ProgramState;
import model.type.Type;
import model.value.Value;


public class VarDecStatement implements IStatement {
    String id;
    Type type;

    public VarDecStatement(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is already defined.");
        } else {
            symbolTable.add(this.id, this.type.defaultValue());
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDecStatement(this.id, this.type.deepCopy());
    }

    public String toString() {
        return this.type + " " + this.id;
    }
}
