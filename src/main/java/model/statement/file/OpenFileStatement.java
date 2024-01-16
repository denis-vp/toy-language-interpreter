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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileStatement implements Statement {
    private final Expression expression;

    public OpenFileStatement(Expression expression) {
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
            if (fileTable.search(stringValue.getValue())) {
                throw new StatementException("File " + stringValue.getValue() + " already opened.");
            }

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new FileReader(stringValue.getValue()));
            fileTable.add(stringValue.getValue(), bufferedReader);
        } catch (ExpressionException | FileNotFoundException e) {
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
        return new OpenFileStatement(this.expression.deepCopy());
    }

    public String toString() {
        return "openRFile(" + this.expression + ")";
    }
}
