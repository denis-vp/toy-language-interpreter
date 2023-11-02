package controller;

import adt.IDictionary;
import adt.IHeap;
import exception.*;
import model.ProgramState;
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

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws ControllerException {
//        programStates.forEach(programState -> {
//            try {
//                this.repository.logProgramStateExecution(programState);
//            } catch (RepositoryException e) {
//                throw new RuntimeException(e);
//            }
//        });

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
        programStates.forEach(programState -> {
            try {
                this.repository.logProgramStateExecution(programState);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });

        while (!programStates.isEmpty()) {
            this.conservativeGarbageCollector(this.repository.getSymbolTable(), this.repository.getHeap());

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

    private void conservativeGarbageCollector(IDictionary<String, Value> symbolTable, IHeap heap) {
        Set<Integer> freedAddresses = new HashSet<>(heap.keys());

        for (Value value : symbolTable.values()) {
            while (value instanceof ReferenceValue referenceValue) {
                freedAddresses.remove(referenceValue.getAddress());
                value = heap.get(referenceValue.getAddress());
            }
        }

        for (Integer freedAddress : freedAddresses) {
            heap.remove(freedAddress);
        }
    }
}
