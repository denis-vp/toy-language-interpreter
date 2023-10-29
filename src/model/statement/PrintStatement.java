package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIList;
import exception.*;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;

public class PrintStatement implements IStatement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIList<Value> output = state.getOutput();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        Value value;
        try {
            value = this.expression.eval(symbolTable);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        output.pushBack(value);

        return state;
    }

    @Override
    public IStatement deepCopy() throws StatementException {
        try {
            return new PrintStatement(this.expression.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
