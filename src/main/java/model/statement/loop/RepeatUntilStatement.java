package model.statement.loop;

import adt.IDictionary;
import adt.IHeap;
import adt.IStack;
import exception.ExpressionException;
import exception.StatementException;
import model.ProgramState;
import model.expression.Expression;
import model.expression.UnaryExpression;
import model.statement.Statement;
import model.type.BoolType;
import model.type.Type;
import model.value.Value;

public class RepeatUntilStatement implements Statement {
    private final Statement innerStatement;
    private final Expression stopCondition;

    public RepeatUntilStatement(Statement innerStatement, Expression stopCondition) {
        this.innerStatement = innerStatement;
        this.stopCondition = stopCondition;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> stack = state.getExecutionStack();
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();

        try {
            Value stopConditionValue = this.stopCondition.eval(symbolTable, heap);
            if (!stopConditionValue.getType().equals(new BoolType())) {
                throw new StatementException("stop condition is not a boolean");
            }
        } catch (ExpressionException exception) {
            throw new StatementException("stop condition could not be evaluated");
        }

        Expression invertedStopCondition = new UnaryExpression("!", this.stopCondition);
        Statement whileStatement = new WhileStatement(invertedStopCondition, this.innerStatement);

        stack.push(whileStatement);
        stack.push(this.innerStatement);

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type stopConditionType = this.stopCondition.typeCheck(typeEnvironment);
            if (!stopConditionType.equals(new BoolType())) {
                throw new StatementException("stop condition is not a boolean");
            }
            this.innerStatement.typeCheck(typeEnvironment.deepCopy());
        } catch (ExpressionException exception) {
            throw new StatementException("stop condition could not be evaluated");
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new RepeatUntilStatement(this.innerStatement.deepCopy(), this.stopCondition.deepCopy());
    }

    public String toString() {
        return "repeat " + this.innerStatement.toString() + " until " + this.stopCondition.toString();
    }
}
