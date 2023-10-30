package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements Statement {
    private final Expression expression;

    public OpenReadFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Value> heap = state.getHeap();

        try {
            Value value = this.expression.eval(symbolTable, heap);
            if (!value.getType().equals(new StringType())) {
                throw new StatementException("Expression " + this.expression + " is not of type string.");
            }
            StringValue stringValue = (StringValue) value;
            if (fileTable.search(stringValue.getValue())) {
                throw new StatementException("File " + stringValue.getValue() + " already opened.");
            }

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new FileReader(stringValue.getValue()));
            fileTable.add(stringValue.getValue(), bufferedReader);
        } catch (ExpressionException | FileNotFoundException e) {
            throw new StatementException(e.getMessage());
        }

        return state;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        try {
            return new OpenReadFileStatement(this.expression.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "openRFile(" + this.expression + ")";
    }
}
