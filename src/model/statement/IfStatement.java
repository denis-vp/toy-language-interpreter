package model.statement;

import datastructure.MyIHeap;
import datastructure.MyIStack;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.BoolValue;
import model.value.Value;

public class IfStatement implements Statement {
    Expression expression;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIStack<Statement> stack = state.getExecutionStack();
        MyIHeap<Value> heap = state.getHeap();

        try {
            BoolValue condition = (BoolValue) this.expression.eval(state.getSymbolTable(), heap);
            if (condition.getValue()) {
                stack.push(this.thenStatement);
            } else {
                stack.push(this.elseStatement);
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        try {
            return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(),
                    this.elseStatement.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "if (" + this.expression + ") then (" + this.thenStatement + ") else (" +  this.elseStatement + ")";
    }
}
