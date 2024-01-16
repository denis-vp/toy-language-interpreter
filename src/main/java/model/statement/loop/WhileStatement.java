package model.statement.loop;

import adt.IDictionary;
import adt.IHeap;
import adt.IStack;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.ProgramState;
import model.statement.Statement;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class WhileStatement implements Statement {
    private final Expression expression;
    private final Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> executionStack = state.getExecutionStack();
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();

        try {
            Value value = this.expression.eval(symbolTable, heap);
            if (!value.getType().equals(new BoolType())) {
                throw new StatementException("Expression " + this.expression + " is not a boolean");
            }
            BoolValue boolValue = (BoolValue) value;
            if (boolValue.getValue()) {
                executionStack.push(this);
                executionStack.push(this.statement);
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type expressionType = this.expression.typeCheck(typeEnvironment);
            if (!expressionType.equals(new BoolType())) {
                throw new StatementException("Expression " + this.expression + " is not a boolean");
            }
            this.statement.typeCheck(typeEnvironment.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    public String toString() {
        return "while (" + this.expression.toString() + ") " + this.statement;
    }
}
