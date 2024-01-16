package model.statement.file;

import adt.IDictionary;
import adt.IHeap;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.ProgramState;
import model.statement.Statement;
import model.type.StringType;
import model.type.Type;
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
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();

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
        } catch (ExpressionException | IOException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type expressionType = this.expression.typeCheck(typeEnvironment);
            if (!expressionType.equals(new StringType())) {
                throw new StatementException("Expression " + this.expression + " is not of type string.");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new CloseFileReadStatement(this.expression.deepCopy());
    }

    public String toString() {
        return "closeRFile(" + this.expression + ")";
    }
}
