package model.statement;

import datastructure.MyIList;
import datastructure.MyIStack;
import exception.*;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;

public class PrintStatement implements IStatement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIList<Value> output = state.getOutput();
        Value value = null;
        try {
            value = this.expression.eval(state.getSymbolTable());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        output.pushBack(value);
        return state;
    }

    @Override
    public IStatement deepCopy() throws StatementException {
        try {
            return new PrintStatement(this.expression.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
