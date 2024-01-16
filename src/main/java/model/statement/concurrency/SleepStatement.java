package model.statement.concurrency;

import adt.IDictionary;
import adt.IStack;
import exception.StatementException;
import model.ProgramState;
import model.statement.Statement;
import model.type.Type;

public class SleepStatement implements Statement {
    private final int number;

    public SleepStatement(int number) {
        this.number = number;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> stack = state.getExecutionStack();

        if (this.number != 0) {
            stack.push(new SleepStatement(this.number - 1));
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new SleepStatement(this.number);
    }

    public String toString() {
        return "sleep(" + this.number + ")";
    }
}
