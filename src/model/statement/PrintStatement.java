package model.statement;

import datastructure.MyIList;
import datastructure.MyIStack;
import exception.DictionaryException;
import exception.ExpressionException;
import exception.MyException;
import exception.StackException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;

public class PrintStatement implements IStatement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StackException, ExpressionException, DictionaryException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIList<Value> output = state.getOutput();
        stack.pop();
        Value value = this.expression.eval(state.getSymbolTable());
        output.pushBack(value);
        return state;
    }

    @Override
    public IStatement deepCopy() throws ExpressionException {
        return new PrintStatement(this.expression.deepCopy());
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
