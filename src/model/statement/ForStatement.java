package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import datastructure.MyIStack;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.value.Value;

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
        MyIStack<Statement> stack = state.getExecutionStack();

        stack.push(new WhileStatement(this.condition, new CompoundStatement(this.body, this.advancement)));
        stack.push(this.initialization);

        return null;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        try {
            return new ForStatement(this.initialization.deepCopy(), this.condition.deepCopy(),
                    this.advancement.deepCopy(), this.body.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "for (" + this.initialization + "; " + this.condition + "; " + this.advancement + ") " + this.body;
    }
}
