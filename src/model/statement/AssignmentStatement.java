package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIStack;
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
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
//        try {
//            stack.pop();
//        } catch (StackException e) {
//            throw new StatementException(e.getMessage());
//        }
        if (symbolTable.search(this.id)) {
            Value value = null;
            try {
                value = this.expression.eval(symbolTable);
            } catch (ExpressionException e) {
                throw new StatementException(e.getMessage());
            }
            Type typeId = null;
            try {
                typeId = (symbolTable.get(this.id)).getType();
            } catch (DictionaryException e) {
                throw new StatementException(e.getMessage());
            }
            if (value.getType().equals(typeId)) {
                try {
                    symbolTable.update(this.id, value);
                } catch (DictionaryException e) {
                    throw new StatementException(e.getMessage());
                }
            } else {
                throw new StatementException("Declared type of variable " + this.id + " and type of the assigned expression do not match.");
            }
        } else {
            throw new StatementException("Variable " + this.id + " is not defined.");
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
        return this.id + "=" + this.expression.toString();
    }
}
