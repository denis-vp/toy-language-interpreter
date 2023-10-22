package controller;

import datastructure.MyIStack;
import exception.MyException;
import exception.StackException;
import model.programstate.ProgramState;
import model.statement.IStatement;
import repository.IRepository;

public class Controller {
    private final IRepository repository;
    private boolean displayFlag = false;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState) throws MyException {
        MyIStack<IStatement> stack = programState.getExecutionStack();
        if (stack.isEmpty()) {
            throw new MyException("Execution stack is empty!");
        }
        IStatement currentStatement = stack.pop();
        return currentStatement.execute(programState);
    }

    public void allSteps() throws MyException {
        ProgramState programState = this.repository.getCurrentProgram();
        if (this.displayFlag) {
            System.out.println(programState);
        }
        while (!programState.getExecutionStack().isEmpty()) {
            this.oneStep(programState);
            if (this.displayFlag) {
                System.out.println(programState);
            }
        }
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public boolean getDisplayFlag() {
        return this.displayFlag;
    }

    public void toggleDisplayFlag() {
        this.displayFlag = !this.displayFlag;
    }

    public void setProgram(IStatement program) throws StackException {
        while (!this.repository.getCurrentProgram().getExecutionStack().isEmpty()) {
            this.repository.getCurrentProgram().getExecutionStack().pop();
        }
        this.repository.getCurrentProgram().getExecutionStack().push(program);
    }
}
