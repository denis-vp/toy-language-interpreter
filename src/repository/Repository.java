package repository;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import datastructure.MyIList;
import datastructure.MyIStack;
import exception.DictionaryException;
import exception.HeapException;
import exception.RepositoryException;
import model.programstate.ProgramState;
import model.statement.Statement;
import model.value.Value;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates = new ArrayList<ProgramState>();
    private final int currentProgramIndex = 0;
    private String logFilePath;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public Repository(ProgramState initialProgram) {
        this.programStates.add(initialProgram);
    }

    public Repository(ProgramState initialProgram, String logFilePath) {
        this.programStates.add(initialProgram);
        this.logFilePath = logFilePath;
    }

    public String getLogFilePath() {
        return this.logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public void add(ProgramState programState) {
        this.programStates.add(programState);
    }

    @Override
    public ProgramState get(int index) {
        return this.programStates.get(index);
    }

    @Override
    public void set(int index, ProgramState programState) {
        this.programStates.set(index, programState);
    }

    @Override
    public int size() {
        return this.programStates.size();
    }

    @Override
    public boolean isEmpty() {
        return this.programStates.isEmpty();
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return List.copyOf(this.programStates);
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates.clear();
        this.programStates.addAll(programStates);
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws RepositoryException {
        MyIStack<Statement> executionStack = programState.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = programState.getSymbolTable();
        MyIList<Value> output = programState.getOutput();
        MyIHeap<Value> heap = programState.getHeap();
        MyIDictionary<String, BufferedReader> fileTable = programState.getFileTable();

        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.println("Program State - " + programState.getId());

            logFile.println("Execution Stack:");
            if (executionStack.isEmpty()) {
                logFile.println("-- Empty --");
            }
            for (Statement statement : executionStack.getAll()) {
                logFile.println(statement);
            }
            logFile.println("------------------------------------");

            logFile.println("Symbol Table:");
            if (symbolTable.isEmpty()) {
                logFile.println("-- Empty --");
            }
            for (String key : symbolTable.keys()) {
                logFile.println(key + " --> " + symbolTable.get(key));
            }
            logFile.println("------------------------------------");


            logFile.println("Output:");
            if (output.isEmpty()) {
                logFile.println("-- Empty --");
            }
            for (Value value : output.getAll()) {
                logFile.println(value);
            }
            logFile.println("------------------------------------");

            logFile.println("Heap:");
            if (programState.getHeap().isEmpty()) {
                logFile.println("-- Empty --");
            }
            for (Integer key : heap.keys()) {
                logFile.println(key + " --> " + heap.get(key));
            }
            logFile.println("------------------------------------");

            logFile.println("File Table:");
            if (programState.getFileTable().isEmpty()) {
                logFile.println("-- Empty --");
            }
            for (String key : fileTable.keys()) {
                logFile.println(key + " --> " + fileTable.get(key));
            }
            logFile.println("------------------------------------");

            logFile.println();
        } catch (IOException | DictionaryException | HeapException e) {
            throw new RepositoryException("Could not open log file!");
        }
    }
}
