package controller;

import exception.*;
import model.programstate.ProgramState;
import model.value.ReferenceValue;
import model.value.Value;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;
    private ExecutorService executor;
    private boolean displayFlag = true;

    public Controller(IRepository repository) {
        this.repository = repository;
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
            List<ProgramState> newProgramStates = this.executor.invokeAll(callList).stream()
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

        this.repository.setProgramStateList(programStates);
    }

    public void allSteps() throws ControllerException {
        this.executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programStates = this.removeCompletedPrograms(this.repository.getProgramStateList());
        while (!programStates.isEmpty()) {
            try {
                this.oneStepForAllPrograms(programStates);
            } catch (RuntimeException e) {
                throw new ControllerException(e.getMessage());
            }
            programStates = this.removeCompletedPrograms(this.repository.getProgramStateList());
        }

        this.executor.shutdownNow();
        this.repository.setProgramStateList(programStates);
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(entry -> symbolTableAddresses.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Set<Integer> getAddressesFromSymbolTable(List<Collection<Value>> symbolTableValues, Map<Integer, Value> heap) {
        Set<Integer> toReturn = new HashSet<>();
        symbolTableValues.forEach(symbolTable -> symbolTable.stream()
                .filter(value -> value instanceof ReferenceValue)
                .forEach(value -> {
                    while (value instanceof ReferenceValue) {
                        toReturn.add(((ReferenceValue) value).getAddress());
                        value = heap.get(((ReferenceValue) value).getAddress());
                    }
                }));
        return toReturn;
    }
}
