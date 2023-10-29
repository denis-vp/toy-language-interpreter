package model.statement;

import datastructure.MyIStack;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.BoolValue;

public class IfStatement implements IStatement {
    Expression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIStack<IStatement> stack = state.getExecutionStack();

        BoolValue condition;
        try {
            condition = (BoolValue) this.expression.eval(state.getSymbolTable());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        if (condition.getValue()) {
            stack.push(this.thenStatement);
        } else {
            stack.push(this.elseStatement);
        }

        return state;
    }

    @Override
    public IStatement deepCopy() throws StatementException {
        try {
            return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "if(" + this.expression.toString() + ") then (" + this.thenStatement.toString() + ") else(" + this.elseStatement.toString() + ")";
    }
}
