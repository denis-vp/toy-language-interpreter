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
    String name;
    Type type;
    private static final HashMap<Type, Value> defaultValues = new HashMap<>();

    public VarDecStatement(String name, Type type) {
        VarDecStatement.defaultValues.put(new IntType(), new IntValue(0));
        VarDecStatement.defaultValues.put(new BoolType(), new BoolValue(false));
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        if (symbolTable.search(this.name)) {
            throw new StatementException("Variable " + this.name + " is already defined.");
        } else {
            symbolTable.add(this.name, VarDecStatement.defaultValues.get(this.type));
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDecStatement(this.name, this.type.deepCopy());
    }

    String getName() {
        return this.type + " " + this.name;
    }
}
