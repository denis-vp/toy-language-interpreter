package model.statement;

import datastructure.MyIStack;
import exception.StatementException;
import model.programstate.ProgramState;

public class CompoundStatement implements Statement {
    private final Statement first;
    private final Statement second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<Statement> stack = state.getExecutionStack();

        stack.push(this.second);
        stack.push(this.first);

        return state;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        return new CompoundStatement(this.first.deepCopy(), this.second.deepCopy());
    }

    public String toString() {
        return "(" + this.first + "; " + this.second + ")";
    }
}
