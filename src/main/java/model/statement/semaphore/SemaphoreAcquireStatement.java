package model.statement.semaphore;

import adt.*;
import exception.StatementException;
import model.ProgramState;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;

public class SemaphoreAcquireStatement implements Statement {
    private final String id;

    public SemaphoreAcquireStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> stack = state.getExecutionStack();
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        ISyncTable semaphoreTable = state.getSemaphoreTable();

        if (!symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined!");
        }
        Value val = symbolTable.get(this.id);
        if (!val.getType().equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not of type int!");
        }

        int address = ((IntValue) val).getValue();
        if (!semaphoreTable.search(address)) {
            throw new StatementException("Address " + address + " is not defined in the semaphore table!");
        }

        synchronized (semaphoreTable) {
            Pair<Integer, List<Integer>> pair = (Pair<Integer, List<Integer>>) semaphoreTable.get(address);
            int listLen = pair.getSecond().size();
            if (pair.getFirst() > listLen) {
                if (!pair.getSecond().contains(state.getId())) {
                    pair.getSecond().add(state.getId());
                }
            } else {
                stack.push(this);
            }
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        if (!typeEnvironment.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined!");
        }
        Type typeId = typeEnvironment.get(this.id);
        if (!typeId.equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not of type int!");
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new SemaphoreAcquireStatement(this.id);
    }

    public String toString() {
        return "acquire(" + this.id + ")";
    }
}
