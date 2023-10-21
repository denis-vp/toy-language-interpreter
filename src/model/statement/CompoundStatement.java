package model.statement;

import datastructures.MyIStack;
import exception.MyException;
import model.programstate.ProgramState;

public class CompoundStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        stack.push(this.second);
        stack.push(this.first);
        return state;
    }

    public String toString() {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }
}
