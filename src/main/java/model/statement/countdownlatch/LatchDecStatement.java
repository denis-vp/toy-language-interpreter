package model.statement.countdownlatch;

import adt.IDictionary;
import adt.IHeap;
import adt.ISyncTable;
import exception.ExpressionException;
import exception.StatementException;
import model.ProgramState;
import model.expression.Expression;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class LatchDecStatement implements Statement {
    private final String id;
    private final Expression expression;

    public LatchDecStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        ISyncTable latchTable = state.getLatchTable();

        Value value;
        try {
            value = this.expression.eval(symbolTable, heap);
            if (!value.getType().equals(new IntType())) {
                throw new StatementException("Expression " + this.expression + " is not of type int");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        if (!symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined");
        }
        Value variableValue = symbolTable.get(this.id);
        if (!variableValue.getType().equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not of type int");
        }

        int number = ((IntValue) value).getValue();
        int address = latchTable.add(number);
        symbolTable.update(this.id, new IntValue(address));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        Type typeId = typeEnvironment.get(this.id);
        if (!typeId.equals(new IntType())) {
            throw new StatementException("Variable " + this.id + " is not of type int");
        }

        try {
            Type typeExpression = this.expression.typeCheck(typeEnvironment);
            if (!typeExpression.equals(new IntType())) {
                throw new StatementException("Expression " + this.expression + " is not of type int");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new LatchDecStatement(this.id, this.expression.deepCopy());
    }

    public String toString() {
        return "newLatch(" + this.id + ", " + this.expression + ")";
    }
}
