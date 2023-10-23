package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIStack;
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
    private static final HashMap<Type, Value> defaultValues = new HashMap<>();

    public VarDecStatement(String id, Type type) {
        VarDecStatement.defaultValues.put(new IntType(), new IntValue(0));
        VarDecStatement.defaultValues.put(new BoolType(), new BoolValue(false));
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIStack<IStatement> stack = state.getExecutionStack();
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
