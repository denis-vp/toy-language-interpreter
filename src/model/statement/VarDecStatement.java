package model.statement;

import datastructures.MyIDictionary;
import datastructures.MyIStack;
import exception.MyException;
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
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        if (symbolTable.search(this.name)) {
            throw new MyException("Variable " + this.name + " is already defined.");
        } else {
            symbolTable.add(this.name, VarDecStatement.defaultValues.get(this.type));
        }
        return state;
    }

    String getName() {
        return this.type + " " + this.name;
    }
}
