package model.statement.countdownlatch;

import adt.IDictionary;
import adt.IStack;
import adt.ISyncTable;
import exception.StatementException;
import model.ProgramState;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class LatchCountDownStatement implements Statement {
    private final String id;

    public LatchCountDownStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        ISyncTable latchTable = state.getLatchTable();

        if (!symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined");
        }
        Value variableValue = symbolTable.get(this.id);
        if (!variableValue.getType().equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not of type int");
        }

        int address = ((IntValue) variableValue).getValue();
        if (!latchTable.search(address)) {
            throw new StatementException("Address " + address + " is not defined in the latch table");
        }

        int count = (Integer) latchTable.get(address);
        if (count > 0) {
            latchTable.update(address, count - 1);
        }

        state.getOutput().pushBack(new IntValue(state.getId()));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        Type typeId = typeEnvironment.get(this.id);
        if (!typeId.equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not of type int");
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new LatchCountDownStatement(this.id);
    }

    public String toString() {
        return "countDown(" + this.id + ")";
    }
}
