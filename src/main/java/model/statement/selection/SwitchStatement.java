package model.statement.selection;

import adt.IDictionary;
import adt.IHeap;
import adt.IStack;
import exception.ExpressionException;
import exception.StatementException;
import model.ProgramState;
import model.expression.Expression;
import model.expression.RelationalExpression;
import model.statement.Statement;
import model.type.Type;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class SwitchStatement implements Statement {
    private final Expression conditionExpression;
    private final List<Expression> caseExpressions;
    private final List<Statement> caseStatements;

    public SwitchStatement(Expression conditionExpression, List<Expression> caseExpressions, List<Statement> caseStatements) {
        this.conditionExpression = conditionExpression;
        this.caseExpressions = caseExpressions;
        this.caseStatements = caseStatements;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStack<Statement> stack = state.getExecutionStack();
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();

        try {
            Value conditionValue = this.conditionExpression.eval(symbolTable, heap);
            for (Expression caseExpression : this.caseExpressions) {
                Value caseValue = caseExpression.eval(symbolTable, heap);
                if (!conditionValue.getType().equals(caseValue.getType())) {
                    throw new StatementException("Switch condition and case value types do not match!");
                }
            }
        } catch (ExpressionException exception) {
            throw new StatementException(exception.getMessage());
        }

        Statement ifStatement = new IfStatement(
                new RelationalExpression(
                        "==",
                        this.conditionExpression,
                        this.caseExpressions.get(this.caseExpressions.size() - 1)
                ),
                this.caseStatements.get(this.caseStatements.size() - 2),
                this.caseStatements.get(this.caseStatements.size() - 1)
        );
        for (int i = this.caseExpressions.size() - 2; i >= 0; i--) {
            ifStatement = new IfStatement(
                    new RelationalExpression(
                            "==",
                            this.conditionExpression,
                            this.caseExpressions.get(i)
                    ),
                    this.caseStatements.get(i),
                    ifStatement
            );
        }

        stack.push(ifStatement);

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type conditionType = this.conditionExpression.typeCheck(typeEnvironment);
            for (Expression caseExpression : this.caseExpressions) {
                Type caseType = caseExpression.typeCheck(typeEnvironment);
                if (!conditionType.equals(caseType)) {
                    throw new StatementException("Switch condition and case value types do not match!");
                }
            }
        } catch (ExpressionException exception) {
            throw new StatementException(exception.getMessage());
        }

        for (Statement caseStatement : this.caseStatements) {
            caseStatement.typeCheck(typeEnvironment.deepCopy());
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        List<Expression> newCaseExpressions = new ArrayList<>();
        for (Expression expression : caseExpressions) {
            newCaseExpressions.add(expression.deepCopy());
        }
        List<Statement> newCaseStatements = new ArrayList<>();
        for (Statement statement : caseStatements) {
            newCaseStatements.add(statement.deepCopy());
        }

        return new SwitchStatement(conditionExpression.deepCopy(), newCaseExpressions, newCaseStatements);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("switch (").append(this.conditionExpression.toString()).append(") {");
        for (int i = 0; i < this.caseExpressions.size(); i++) {
            stringBuilder.append("case (").append(this.caseExpressions.get(i).toString()).append("): ");
            stringBuilder.append(this.caseStatements.get(i).toString()).append("; ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
