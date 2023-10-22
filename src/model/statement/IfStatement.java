package model.statement;

import datastructure.MyIStack;
import exception.DictionaryException;
import exception.ExpressionException;
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
    public ProgramState execute(ProgramState state) throws ExpressionException, DictionaryException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        BoolValue condition = (BoolValue) this.expression.eval(state.getSymbolTable());
        if (condition.getValue()) {
            stack.push(this.thenStatement);
        } else {
            stack.push(this.elseStatement);
        }
        return state;
    }

    @Override
    public IStatement deepCopy() throws ExpressionException {
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    public String toString() {
        return "if(" + this.expression.toString() + ") then (" + this.thenStatement.toString() + ") else(" + this.elseStatement.toString() + ")";
    }
}
