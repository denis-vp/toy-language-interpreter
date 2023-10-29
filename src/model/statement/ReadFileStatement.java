package model.statement;

import datastructure.MyIDictionary;
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

public class ReadFileStatement implements IStatement{
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

         try {
            if (symbolTable.search(this.id)) {
                Value value = symbolTable.get(this.id);

                if (value.getType().equals(new IntType())) {
                    value = this.expression.eval(symbolTable);

                    if (value.getType().equals(new StringType())) {
                        StringValue stringValue = (StringValue) value;

                        if (fileTable.search(stringValue.getValue())) {
                            BufferedReader fileDescriptor = fileTable.get(stringValue.getValue());
                            String line = fileDescriptor.readLine();
                            if (line == null) {
                                symbolTable.add(this.id, new IntValue(0));
                            } else {
                                symbolTable.add(this.id, new IntValue(Integer.parseInt(line)));
                            }
                        } else {
                            throw new StatementException("File " + stringValue.getValue() + " not opened.");
                        }
                    } else {
                        throw new StatementException("Expression " + this.expression + " is not of type string.");
                    }
                } else {
                    throw new StatementException("Expression " + this.expression + " is not of type int.");
                }
            } else {
                throw new StatementException("Variable " + this.id + " not declared.");
            }
        } catch (DictionaryException | ExpressionException | IOException e) {
            throw new StatementException(e.getMessage());
        }

        return state;
    }

    @Override
    public IStatement deepCopy() throws StatementException {
        return null;
    }
}
