package model.statement.lock;

import adt.IDictionary;
import adt.ISyncTable;
import exception.StatementException;
import model.ProgramState;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class LockStatement implements Statement {
    private final String id;

    public LockStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        ISyncTable lockTable = state.getLockTable();

        if (!symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined.");
        }

        Value value = symbolTable.get(this.id);
        if (!value.getType().equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not an integer type.");
        }
        int address = ((IntValue) value).getValue();

        if (!lockTable.search(address)) {
            throw new StatementException("Address " + address + " is not in the lock table.");
        }

        if ((Integer) lockTable.get(address) == -1) {
            lockTable.update(address, state.getId());
        } else {
            state.getExecutionStack().push(this);
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        if (!typeEnvironment.get(this.id).equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not an integer type.");
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new LockStatement(this.id);
    }

    public String toString() {
        return "lock(" + this.id + ")";
    }
}
