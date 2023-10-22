package model.statement;

import datastructure.MyIStack;
import exception.ExpressionException;
import model.programstate.ProgramState;

public class CompoundStatement implements IStatement {
    private final IStatement first;
    private final IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> stack = state.getExecutionStack();
        stack.push(this.second);
        stack.push(this.first);
        return state;
    }

    @Override
    public IStatement deepCopy() throws ExpressionException {
        return new CompoundStatement(this.first.deepCopy(), this.second.deepCopy());
    }

    public String toString() {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }
}
