package model.statement;

import adt.IDictionary;
import adt.IHeap;
import adt.IList;
import exception.*;
import model.expression.Expression;
import model.ProgramState;
import model.type.Type;
import model.value.Value;

public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
        IList<Value> output = state.getOutput();

        try {
            Value value = this.expression.eval(symbolTable, heap);
            output.pushBack(value);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            this.expression.typeCheck(typeEnvironment);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    public String toString() {
        return "print(" + this.expression + ")";
    }
}
