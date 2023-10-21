package model.statement;

import datastructures.MyIDictionary;
import datastructures.MyIStack;
import exception.MyException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.Type;
import model.value.Value;

public class AssignmentStatement implements IStatement {
    private String id;
    private Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        stack.pop();
        if (symbolTable.search(this.id)) {
            Value value = this.expression.eval(symbolTable);
            Type typeId = (symbolTable.get(this.id)).getType();
            if (value.getType().equals(typeId)) {
                symbolTable.update(this.id, value);
            } else {
                throw new MyException("Declared type of variable " + this.id + " and type of the assigned expression do not match.");
            }
        } else {
            throw new MyException("Variable " + this.id + " is not defined.");
        }
        return state;
    }

    public String toString() {
        return this.id + "=" + this.expression.toString();
    }
}
