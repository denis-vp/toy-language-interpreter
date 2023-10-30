package model.statement;

import datastructure.MyIDictionary;
import exception.*;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.Type;
import model.value.Value;

public class AssignmentStatement implements IStatement {
    private final String id;
    private final Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        if (!symbolTable.search(this.id)) {
            throw new StatementException("Variable " + this.id + " is not defined.");
        }
        try {
            Value value = this.expression.eval(symbolTable);
            Type typeId = (symbolTable.get(this.id)).getType();
            if (!value.getType().equals(typeId)) {
                throw new StatementException("Declared type of variable " + this.id +
                        " and type of the assigned expression do not match.");
            }
            symbolTable.update(this.id, value);
        } catch (ExpressionException | DictionaryException e) {
            throw new StatementException(e.getMessage());
        }

        return state;
    }

    @Override
    public IStatement deepCopy() throws StatementException {
        try {
            return new AssignmentStatement(this.id, this.expression.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return this.id + " = " + this.expression;
    }
}
