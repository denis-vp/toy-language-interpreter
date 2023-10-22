package model.statement;

import datastructure.MyIStack;
import exception.StackException;
import model.programstate.ProgramState;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws StackException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        stack.pop();
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }
}
