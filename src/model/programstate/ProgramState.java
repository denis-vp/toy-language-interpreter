package model.programstate;

import datastructure.MyIDictionary;
import datastructure.MyIList;
import datastructure.MyIStack;
import exception.ProgramStateException;
import exception.StatementException;
import model.statement.IStatement;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> output;
    private MyIDictionary<String, BufferedReader> fileTable;
    IStatement originalProgram;

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, Value> symbolTable,
                        MyIList<Value> output, MyIDictionary<String, BufferedReader> fileTable,
                        IStatement originalProgram) throws ProgramStateException {

        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
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

    public MyIStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public MyIList<Value> getOutput() {
        return this.output;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public void setExecutionStack(MyIStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(MyIDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setOutput(MyIList<Value> output) {
        this.output = output;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }
}
