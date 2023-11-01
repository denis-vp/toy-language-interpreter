package model.statement;

import adt.IDictionary;
import adt.IHeap;
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

public class OpenFileReadStatement implements Statement {
    private final Expression expression;

    public OpenFileReadStatement(Expression expression) {
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
    public Statement deepCopy() {
        return new OpenFileReadStatement(this.expression.deepCopy());
    }

    public String toString() {
        return "openRFile(" + this.expression + ")";
    }
}
