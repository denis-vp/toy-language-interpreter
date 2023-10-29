package model.statement;

import datastructure.MyIDictionary;
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

public class CloseReadFileStatement implements IStatement {
    private final Expression expression;

    public CloseReadFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        try {
            Value value = this.expression.eval(symbolTable);
            if (value.getType().equals(new StringType())) {
                StringValue stringValue = (StringValue) value;
                if (fileTable.search(stringValue.getValue())) {
                    BufferedReader bufferedReader = fileTable.get(stringValue.getValue());
                    bufferedReader.close();
                    fileTable.remove(stringValue.getValue());
                } else {
                    throw new StatementException("File " + stringValue.getValue() + " not opened.");
                }
            } else {
                throw new StatementException("Expression " + this.expression + " is not of type string.");
            }
        } catch (ExpressionException | DictionaryException | IOException e) {
            throw new StatementException(e.getMessage());
        }

        return state;
    }

    @Override
    public IStatement deepCopy() throws StatementException {
        return null;
    }
}
