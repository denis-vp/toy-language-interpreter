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
    public ProgramState execute(ProgramState state) throws StackException, ExpressionException, DictionaryException, StatementException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        stack.pop();
        if (symbolTable.search(this.id)) {
            Value value = this.expression.eval(symbolTable);
            Type typeId = (symbolTable.get(this.id)).getType();
            if (value.getType().equals(typeId)) {
                symbolTable.update(this.id, value);
            } else {
                throw new StatementException("Declared type of variable " + this.id + " and type of the assigned expression do not match.");
            }
        } else {
            throw new StatementException("Variable " + this.id + " is not defined.");
        }
        return state;
    }

    @Override
    public IStatement deepCopy() throws ExpressionException {
        return new AssignmentStatement(this.id, this.expression.deepCopy());
    }

    public String toString() {
        return this.id + "=" + this.expression.toString();
    }
}
