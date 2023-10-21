package model.statement;

import datastructures.MyIList;
import datastructures.MyIStack;
import exception.MyException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;

public class PrintStatement implements IStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIList<Value> output = state.getOutput();
        stack.pop();
        Value value = this.expression.eval(state.getSymbolTable());
        output.pushBack(value);
        return state;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
