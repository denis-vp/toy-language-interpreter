package model.statement;

import adt.IStack;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;

public class ForStatement implements Statement {
    private final Statement initialization;
    private final Expression condition;
    private final Statement advancement;
    private final Statement body;

    public ForStatement(Statement initialization, Expression condition, Statement advancement, Statement body) {
        this.initialization = initialization;
        this.condition = condition;
        this.advancement = advancement;
        this.body = body;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> executionStack = state.getExecutionStack();

        executionStack.push(new WhileStatement(this.condition, new CompoundStatement(this.body, this.advancement)));
        executionStack.push(this.initialization);

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ForStatement(this.initialization.deepCopy(), this.condition.deepCopy(),
                this.advancement.deepCopy(), this.body.deepCopy());
    }

    public String toString() {
        return "for (" + this.initialization + "; " + this.condition + "; " + this.advancement + ") " + this.body;
    }
}
