package model.statement.selection;

import adt.IDictionary;
import adt.IHeap;
import adt.IStack;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.ProgramState;
import model.statement.Statement;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;

public class IfStatement implements Statement {
    private final Expression expression;
    private final Statement thenStatement;
    private final Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> executionStack = state.getExecutionStack();
        IHeap heap = state.getHeap();

        try {
            BoolValue condition = (BoolValue) this.expression.eval(state.getSymbolTable(), heap);
            if (condition.getValue()) {
                executionStack.push(this.thenStatement);
            } else {
                executionStack.push(this.elseStatement);
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type typeExpression = this.expression.typeCheck(typeEnvironment);
            if (!typeExpression.equals(new BoolType())) {
                throw new StatementException("The condition of IF has not the type bool.");
            }
            this.thenStatement.typeCheck(typeEnvironment.deepCopy());
            this.elseStatement.typeCheck(typeEnvironment.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(),
                this.elseStatement.deepCopy());
    }

    public String toString() {
        return "if (" + this.expression + ") then (" + this.thenStatement + ") else (" + this.elseStatement + ")";
    }
}
