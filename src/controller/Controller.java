package controller;

import datastructure.MyIStack;
import exception.*;
import model.programstate.ProgramState;
import model.statement.Statement;
import repository.IRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;
    private boolean displayFlag = true;
    private ExecutorService executorService;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws ControllerException {
        programStates.forEach(programState -> {
            try {
                this.repository.logProgramStateExecution(programState);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState programState) -> (Callable<ProgramState>) programState::oneStep)
                .toList();

        try {
            List<ProgramState> newProgramStates = this.executorService.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            programStates.addAll(newProgramStates);
        } catch (InterruptedException e) {
            throw new ControllerException(e.getMessage());
        }

        programStates.forEach(programState -> {
            try {
                this.repository.logProgramStateExecution(programState);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });

        this.repository.setProgramStates(programStates);
    }

    public void allSteps() throws ControllerException {
        this.executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = this.removeCompletedPrograms(this.repository.getProgramStates());
        while (!programStates.isEmpty()) {
            try {
                this.oneStepForAllPrograms(programStates);
            } catch (RuntimeException e) {
                throw new ControllerException(e.getMessage());
            }
            programStates = this.removeCompletedPrograms(this.repository.getProgramStates());
        }
        this.executorService.shutdownNow();
        this.repository.setProgramStates(programStates);
    }

    public boolean getDisplayFlag() {
        return this.displayFlag;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public void toggleDisplayFlag() {
        this.displayFlag = !this.displayFlag;
    }

    public void addProgram(ProgramState program) {
        this.repository.add(program);
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }
}
