package model.programstate;

import datastructure.MyIDictionary;
import datastructure.MyIList;
import datastructure.MyIStack;
import exception.MyException;
import exception.StatementException;
import model.statement.IStatement;
import model.value.Value;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> output;
    IStatement originalProgram;

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, Value> symbolTable,
                        MyIList<Value> output, IStatement originalProgram) throws MyException {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        try {
            this.originalProgram = originalProgram.deepCopy();
        } catch (StatementException e) {
            throw new MyException(e.getMessage());
        }
        executionStack.push(originalProgram);
    }

    public String toString() {
        return "Execution Stack:\n" + this.executionStack.toString() +
                "\nSymbol Table:\n" + this.symbolTable.toString() +
                "\nOutput:\n" + this.output.toString() + "\n";
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

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }
}
