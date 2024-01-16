package model.statement.assignment;

import adt.IDictionary;
import adt.IStack;
import exception.ExpressionException;
import exception.StatementException;
import model.ProgramState;
import model.expression.Expression;
import model.statement.Statement;
import model.statement.selection.IfStatement;
import model.type.BoolType;
import model.type.Type;

public class CondAssignmentStatement implements Statement {
    private final String id;

    private final Expression condition;

    private final Expression trueExpression;

    private final Expression falseExpression;

    public CondAssignmentStatement(String id, Expression condition, Expression trueExpression, Expression falseExpression) {
        this.id = id;
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> stack = state.getExecutionStack();

        stack.push(new IfStatement(
                this.condition,
                new AssignmentStatement(this.id, this.trueExpression),
                new AssignmentStatement(this.id, this.falseExpression)
        ));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type conditionType = this.condition.typeCheck(typeEnvironment);
            if (!conditionType.equals(new BoolType())) {
                throw new StatementException("Condition " + this.condition + " is not a boolean");
            }

            Type trueExpressionType = this.trueExpression.typeCheck(typeEnvironment);
            Type falseExpressionType = this.falseExpression.typeCheck(typeEnvironment);
            Type idType = typeEnvironment.get(this.id);
            if (!trueExpressionType.equals(falseExpressionType)) {
                throw new StatementException("Expressions " + this.trueExpression + " and " + this.falseExpression + " have different types");
            } else if (!idType.equals(trueExpressionType)) {
                throw new StatementException("Variable " + this.id + " and expression " + this.trueExpression + "/ " + this.falseExpression + " have different types");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new CondAssignmentStatement(this.id, this.condition.deepCopy(), this.trueExpression.deepCopy(), this.falseExpression.deepCopy());
    }

    public String toString() {
        return this.id + " = " + this.condition.toString() + " ? " + this.trueExpression.toString() + " : " + this.falseExpression.toString();
    }
}
