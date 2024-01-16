package model.statement.assignment;

import adt.IDictionary;
import adt.IHeap;
import exception.*;
import model.expression.Expression;
import model.ProgramState;
import model.statement.Statement;
import model.type.Type;
import model.value.Value;

public class AssignmentStatement implements Statement {
    private final String id;
    private final Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();

        if (!symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined.");
        }
        try {
            Value value = this.expression.eval(symbolTable, heap);
            Type typeId = (symbolTable.get(this.id)).getType();
            if (!value.getType().equals(typeId)) {
                throw new StatementException("Declared type of variable " + this.id +
                        " and type of the assigned expression do not match.");
            }
            symbolTable.update(this.id, value);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type typeId = typeEnvironment.get(this.id);
            Type typeExpression = this.expression.typeCheck(typeEnvironment);
            if (!typeId.equals(typeExpression)) {
                throw new StatementException("Declared type of variable " + this.id +
                        " and type of the assigned expression do not match.");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(this.id, this.expression.deepCopy());
    }

    public String toString() {
        return this.id + " = " + this.expression;
    }
}
