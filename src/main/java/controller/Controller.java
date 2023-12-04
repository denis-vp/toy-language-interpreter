package controller;

import adt.IStack;
import exception.*;
import model.ProgramState;
import model.statement.CompoundStatement;
import model.statement.Statement;
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
    private Boolean logOutput;
    private final ExecutorService executor = Executors.newFixedThreadPool(8);

    public Controller(IRepository repository, Boolean logOutput) {
        this.repository = repository;
        this.logOutput = logOutput;
        this.skipInitialCompoundStatementUnpacking();
    }

    public IRepository getRepository() {
        return this.repository;
    }

    public ExecutorService getExecutor() {
        return this.executor;
    }

    private void skipInitialCompoundStatementUnpacking() {
        boolean aux = logOutput;
        logOutput = false;

        List<ProgramState> programStates = this.removeCompletedPrograms(this.repository.getProgramStateList());

        while (!programStates.isEmpty()) {
            IStack<Statement> executionStack = programStates.get(0).getExecutionStack();
            try {
                if (!(executionStack.top() instanceof CompoundStatement)) {
                    break;
                }
            } catch (ADTException e) {
                throw new RuntimeException(e);
            }

            this.repository.getHeap().setContent(
                    this.garbageCollector(
                            this.getAddressesFromSymbolTable(this.repository.getSymbolTable().values()),
                            this.repository.getHeap().getContent()
                    ));

            try {
                this.oneStepForAllPrograms(programStates);
            } catch (RuntimeException | ControllerException e) {
                throw new RuntimeException(e);
            }

            programStates = this.removeCompletedPrograms(this.repository.getProgramStateList());
        }

        this.repository.setProgramStateList(programStates);

        logOutput = aux;
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws ControllerException {
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

        if (this.logOutput) {
            programStates.forEach(programState -> {
                try {
                    this.repository.logProgramStateExecution(programState);
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        this.repository.setProgramStateList(programStates);
    }

    public void allSteps() throws ControllerException {
        List<ProgramState> programStates = this.removeCompletedPrograms(this.repository.getProgramStateList());
        if (this.logOutput) {
            programStates.forEach(programState -> {
                try {
                    this.repository.logProgramStateExecution(programState);
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        while (!programStates.isEmpty()) {
            this.repository.getHeap().setContent(
                    this.garbageCollector(
                            this.getAddressesFromSymbolTable(this.repository.getSymbolTable().values()),
                            this.repository.getHeap().getContent()
                    ));

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

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> garbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(entry -> {
                    for (Value value : heap.values()) {
                        if (value instanceof ReferenceValue referenceValue) {
                            if (referenceValue.getAddress() == entry.getKey()) {
                                return true;
                            }
                        }
                    }
                    return symbolTableAddresses.contains(entry.getKey());
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues) {
        return symbolTableValues.stream()
                .filter(value -> value instanceof ReferenceValue)
                .map(value -> ((ReferenceValue) value).getAddress())
                .toList();
    }
}
