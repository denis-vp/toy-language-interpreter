package model.programstate;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import datastructure.MyIList;
import datastructure.MyIStack;
import exception.ProgramStateException;
import exception.StackException;
import exception.StatementException;
import model.statement.Statement;
import model.value.Value;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ProgramState {
    private static final Set<Integer> ids = new HashSet<>();
    private final int id;
    Statement originalProgram;
    private MyIStack<Statement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> output;
    private MyIHeap<Value> heap;
    private MyIDictionary<String, BufferedReader> fileTable;

    public ProgramState(Statement originalProgram,
                        MyIStack<Statement> executionStack, MyIDictionary<String, Value> symbolTable,
                        MyIList<Value> output, MyIHeap<Value> heap,
                        MyIDictionary<String, BufferedReader> fileTable) throws ProgramStateException {

        this.id = ProgramState.getNewId();
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.heap = heap;
        this.fileTable = fileTable;

        try {
            this.originalProgram = originalProgram.deepCopy();
        } catch (StatementException e) {
            throw new ProgramStateException(e.getMessage());
        }

        executionStack.push(originalProgram);
    }

    public String toString() {
        return "ProgramState{" +
                "id=" + this.id +
                ", executionStack=" + this.executionStack +
                ", symbolTable=" + this.symbolTable +
                ", output=" + this.output +
                ", heap=" + this.heap +
                ", fileTable=" + this.fileTable +
                '}';
    }

    public int getId() {
        return this.id;
    }

    private static int getNewId() {
        Random random = new Random();
        int id;
        synchronized (ProgramState.ids) {
            do {
                id = random.nextInt();
            } while (ProgramState.ids.contains(id));
            ProgramState.ids.add(id);
        }
        return id;
    }

    public Statement getOriginalProgram() {
        return this.originalProgram;
    }

    public MyIStack<Statement> getExecutionStack() {
        return this.executionStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public MyIList<Value> getOutput() {
        return this.output;
    }

    public MyIHeap<Value> getHeap() {
        return this.heap;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setOriginalProgram(Statement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public void setExecutionStack(MyIStack<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(MyIDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setOutput(MyIList<Value> output) {
        this.output = output;
    }

    public void setHeap(MyIHeap<Value> heap) {
        this.heap = heap;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

    public ProgramState oneStep() throws ProgramStateException {
        if (this.executionStack.isEmpty()) {
            throw new ProgramStateException("Execution stack is empty!");
        }

        try {
            Statement currentStatement = this.executionStack.pop();
            return currentStatement.execute(this);
        } catch (StatementException | StackException e) {
            throw new ProgramStateException(e.getMessage());
        }
    }
}
