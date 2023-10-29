package controller;

import datastructure.MyIStack;
import exception.*;
import model.programstate.ProgramState;
import model.statement.IStatement;
import repository.IRepository;

public class Controller {
    private final IRepository repository;
    private boolean displayFlag = false;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState programState) throws ControllerException {
        MyIStack<IStatement> stack = programState.getExecutionStack();
        if (stack.isEmpty()) {
            throw new ControllerException("Execution stack is empty!");
        }

        IStatement currentStatement;
        try {
            currentStatement = stack.pop();
            return currentStatement.execute(programState);
        } catch (StackException | StatementException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public void allSteps() throws ControllerException {
        ProgramState programState = this.repository.getCurrentProgram();

        try {
            this.repository.logProgramStateExecution();
            if (this.displayFlag) {
                System.out.println(programState);
            }

            while (!programState.getExecutionStack().isEmpty()) {
                this.oneStep(programState);

                this.repository.logProgramStateExecution();
                if (this.displayFlag) {
                    System.out.println(programState);
                }
            }
        } catch (RepositoryException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    public String getProgramOutput() {
        return this.repository.getCurrentProgram().getOutput().toString();
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

    public void setProgram(ProgramState program) {
        this.repository.add(program);
    }
}
