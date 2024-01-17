package model.statement.semaphore;

import adt.*;
import exception.ExpressionException;
import exception.StatementException;
import model.ProgramState;
import model.expression.Expression;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class SemaphoreDecStatement implements Statement {
    private final String id;
    private final Expression expression;

    public SemaphoreDecStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        ISyncTable semaphoreTable = state.getSemaphoreTable();

        Value valExp;
        try {
            valExp = this.expression.eval(symbolTable, heap);
            if (!valExp.getType().equals(new IntType())) {
                throw new StatementException("Expression " + this.expression + " is not of type int!");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        if (!symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined!");
        }
        Value val = symbolTable.get(this.id);
        if (!val.getType().equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not of type int!");
        }

        int value = ((IntValue) valExp).getValue();
        Pair<Integer, List<Integer>> pair = new Pair<>(value, new ArrayList<>());
        int address = semaphoreTable.add(pair);
        symbolTable.update(this.id, new IntValue(address));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type typeExp1 = this.expression.typeCheck(typeEnvironment);
            if (!typeExp1.equals(new IntType())) {
                throw new StatementException("Expression " + this.expression + " is not of type int!");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

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
        return new SemaphoreDecStatement(this.id, this.expression.deepCopy());
    }

    public String toString() {
        return "newSemaphore(" + this.id + ", " + this.expression + ")";
    }
}
