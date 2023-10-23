package model.statement;

import datastructure.MyIStack;
import exception.StackException;
import exception.StatementException;
import model.programstate.ProgramState;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    public String toString() {
        return "nop";
    }
}
