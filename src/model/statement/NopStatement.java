package model.statement;

import datastructures.MyIStack;
import exception.MyException;
import model.programstate.ProgramState;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        stack.pop();
        return state;
    }
}
