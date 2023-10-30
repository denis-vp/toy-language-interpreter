package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.DictionaryException;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.IntType;
import model.type.StringType;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement {
    private final Expression expression;
    private final String id;

    public ReadFileStatement(Expression expression, String id) {
        this.expression = expression;
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heap = state.getHeap();

        try {
            if (!symbolTable.search(this.id)) {
                throw new StatementException("Variable " + this.id + " is not defined.");
            }
            Value idValue = symbolTable.get(this.id);
            if (!idValue.getType().equals(new IntType())) {
                throw new StatementException("Variable " + this.id + " is not of type int.");
            }

            Value expressionValue = this.expression.eval(symbolTable, heap);
            if (!expressionValue.getType().equals(new StringType())) {
                throw new StatementException("Expression " + this.expression + " is not of type string.");
            }
            StringValue stringValue = (StringValue) expressionValue;

            if (!fileTable.search(stringValue.getValue())) {
                throw new StatementException("File " + stringValue.getValue() + " not opened.");
            }

            BufferedReader bufferedReader = fileTable.get(stringValue.getValue());
            String line = bufferedReader.readLine();
            if (line == null) {
                IntType intType = new IntType();
                symbolTable.update(this.id, intType.defaultValue());
            } else {
                symbolTable.update(this.id, new IntValue(Integer.parseInt(line)));
            }
        } catch (DictionaryException | ExpressionException | IOException e) {
            throw new StatementException(e.getMessage());
        }

        return state;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        return null;
    }

    public String toString() {
        return "readFile(" + this.expression + ", " + this.id + ")";
    }
}
