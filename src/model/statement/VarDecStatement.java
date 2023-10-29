package model.statement;

import datastructure.MyIDictionary;
import exception.StatementException;
import model.programstate.ProgramState;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

import java.util.HashMap;

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
            if (this.type.equals(new IntType())) {
                symbolTable.add(this.id, new IntValue(0));
            } else if (this.type.equals(new BoolType())) {
                symbolTable.add(this.id, new BoolValue(false));
            } else {
                throw new StatementException("Type " + this.type + " is not defined.");
            }
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
