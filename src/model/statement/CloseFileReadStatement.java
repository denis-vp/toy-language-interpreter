package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.DictionaryException;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileReadStatement implements Statement {
    private final Expression expression;

    public CloseFileReadStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heap = state.getHeap();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        try {
            Value value = this.expression.eval(symbolTable, heap);
            if (!value.getType().equals(new StringType())) {
                throw new StatementException("Expression " + this.expression + " is not of type string.");
            }
            StringValue stringValue = (StringValue) value;
            if (!fileTable.search(stringValue.getValue())) {
                throw new StatementException("File " + stringValue.getValue() + " not opened.");
            }
            BufferedReader bufferedReader = fileTable.get(stringValue.getValue());
            bufferedReader.close();
            fileTable.remove(stringValue.getValue());
        } catch (ExpressionException | DictionaryException | IOException e) {
            throw new StatementException(e.getMessage());
        }

        return state;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        return null;
    }

    public String toString() {
        return "closeRFile(" + this.expression + ")";
    }
}
