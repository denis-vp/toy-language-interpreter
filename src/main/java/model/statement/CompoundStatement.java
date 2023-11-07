package model.statement;

import adt.IDictionary;
import adt.IStack;
import exception.StatementException;
import model.ProgramState;
import model.type.Type;

public class CompoundStatement implements Statement {
    private final Statement first;
    private final Statement second;

    public CompoundStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<Statement> stack = state.getExecutionStack();

        stack.push(this.second);
        stack.push(this.first);

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, model.type.Type> typeEnvironment) throws StatementException {
        return this.second.typeCheck(this.first.typeCheck(typeEnvironment));
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(this.first.deepCopy(), this.second.deepCopy());
    }

    public String toString() {
        return "(" + this.first + "; " + this.second + ")";
    }
}
