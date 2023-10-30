package model.programstate;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import datastructure.MyIList;
import datastructure.MyIStack;
import exception.ProgramStateException;
import exception.StatementException;
import model.statement.Statement;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
    Statement originalProgram;
    private MyIStack<Statement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> output;
    private MyIHeap<Value> heap;
    private MyIDictionary<String, BufferedReader> fileTable;

    public ProgramState(Statement originalProgram, MyIStack<Statement> executionStack,
                        MyIDictionary<String, Value> symbolTable, MyIList<Value> output,
                        MyIHeap<Value> heap, MyIDictionary<String, BufferedReader> fileTable) throws ProgramStateException {

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
        return "Execution Stack:\n" + this.executionStack.toString() +
                "\nSymbol Table:\n" + this.symbolTable.toString() +
                "\nOutput:\n" + this.output.toString() +
                "\nFile Table:\n" + this.fileTable.toString() + "\n";
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
}
