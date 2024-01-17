package model;

import adt.*;
import exception.ADTException;
import exception.ProgramStateException;
import exception.StatementException;
import model.statement.Statement;
import model.value.Value;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ProgramState {
    private final Statement originalProgram;
    private final IStack<Statement> executionStack;
    private final IDictionary<String, Value> symbolTable;
    private final IHeap heap;
    private final IDictionary<String, BufferedReader> fileTable;
    private final IList<Value> output;
    private final ISyncTable lockTable;
    private final ISyncTable latchTable;
    private final ISyncTable semaphoreTable;
    private final int id;
    private static final Set<Integer> ids = new HashSet<>();

    public ProgramState(Statement originalProgram,
                        IStack<Statement> executionStack, IDictionary<String, Value> symbolTable,
                        IHeap heap, IDictionary<String, BufferedReader> fileTable,
                        IList<Value> output, ISyncTable lockTable, ISyncTable latchTable,
                        ISyncTable semaphoreTable) {

        this.originalProgram = originalProgram.deepCopy();
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.fileTable = fileTable;
        this.output = output;
        this.lockTable = lockTable;
        this.latchTable = latchTable;
        this.semaphoreTable = semaphoreTable;

        this.id = ProgramState.generateNewId();
        executionStack.push(originalProgram);
    }

    private static int generateNewId() {
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

    public int getId() {
        return this.id;
    }

    public Statement getOriginalProgram() {
        return this.originalProgram;
    }

    public IStack<Statement> getExecutionStack() {
        return this.executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public IHeap getHeap() {
        return this.heap;
    }

    public IDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IList<Value> getOutput() {
        return this.output;
    }

    public ISyncTable getLockTable() {
        return this.lockTable;
    }

    public ISyncTable getLatchTable() {
        return this.latchTable;
    }

    public ISyncTable getSemaphoreTable() {
        return this.semaphoreTable;
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
        } catch (StatementException | ADTException e) {
            throw new ProgramStateException(e.getMessage());
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Program State: ").append(this.id).append("\n");
        stringBuilder.append("Execution Stack:\n");
        if (this.executionStack.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.executionStack);
        }
        stringBuilder.append("-------------------------------------------\n");
        stringBuilder.append("Symbol Table:\n");
        if (this.symbolTable.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.symbolTable);
        }
        stringBuilder.append("-------------------------------------------\n");
        stringBuilder.append("Heap:\n");
        if (this.heap.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.heap);
        }
        stringBuilder.append("-------------------------------------------\n");
        stringBuilder.append("File Table:\n");
        if (this.fileTable.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.fileTable);
        }
        stringBuilder.append("-------------------------------------------\n");
        stringBuilder.append("Output:\n");
        if (this.output.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.output);
        }
        stringBuilder.append("-------------------------------------------\n");
        stringBuilder.append("Lock Table:\n");
        if (this.lockTable.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.lockTable);
        }
        stringBuilder.append("-------------------------------------------\n");
        stringBuilder.append("Latch Table:\n");
        if (this.latchTable.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.latchTable);
        }
        stringBuilder.append("-------------------------------------------\n");
        stringBuilder.append("Semaphore Table:\n");
        if (this.semaphoreTable.isEmpty()) {
            stringBuilder.append("----------Empty----------\n");
        } else {
            stringBuilder.append(this.semaphoreTable);
        }
        stringBuilder.append("-------------------------------------------\n");

        return stringBuilder.toString();
    }
}
