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

public class LockDecStatement implements Statement {
    private final String id;

    public LockDecStatement(String id) {
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

        int address = lockTable.add(null);
        symbolTable.update(this.id, new IntValue(address));

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
        return new LockDecStatement(this.id);
    }

    public String toString() {
        return "newLock(" + this.id + ")";
    }
}
