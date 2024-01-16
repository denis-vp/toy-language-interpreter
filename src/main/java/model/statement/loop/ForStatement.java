package model.statement.loop;

import adt.IDictionary;
import adt.IStack;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.ProgramState;
import model.statement.CompoundStatement;
import model.statement.Statement;
import model.type.Type;

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
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            this.initialization.typeCheck(typeEnvironment);
            this.condition.typeCheck(typeEnvironment.deepCopy());
            this.advancement.typeCheck(typeEnvironment.deepCopy());
            this.body.typeCheck(typeEnvironment.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnvironment;
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
