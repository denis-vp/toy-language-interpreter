package model.statement.heap;

import adt.IDictionary;
import adt.IHeap;
import exception.ExpressionException;
import exception.StatementException;
import model.expression.Expression;
import model.ProgramState;
import model.statement.Statement;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapWriteStatement implements Statement {
    private final String id;
    private final Expression expression;

    public HeapWriteStatement(String id, Expression expression) {
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
        Value value = symbolTable.get(this.id);
        if (!value.getType().equals(new ReferenceType(null))) {
            throw new StatementException("Variable " + this.id + " is not a reference type.");
        }
        ReferenceValue referenceValue = (ReferenceValue) value;
        if (!heap.search(referenceValue.getAddress())) {
            throw new StatementException("Address " + referenceValue.getAddress() + " is not in the heap.");
        }

        try {
            Value expressionValue = this.expression.eval(symbolTable, heap);
            if (!expressionValue.getType().equals(referenceValue.getLocationType())) {
                throw new StatementException("Expression " + this.expression + " is not of type " + referenceValue.getLocationType() + ".");
            }

            heap.update(referenceValue.getAddress(), expressionValue);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws StatementException {
        try {
            Type type = typeEnvironment.get(this.id);
            if (!type.equals(new ReferenceType(null))) {
                throw new StatementException("Variable " + this.id + " is not a reference type.");
            }
            Type expressionType = this.expression.typeCheck(typeEnvironment);
            if (!expressionType.equals(((ReferenceType) type).getInner())) {
                throw new StatementException("Expression " + this.expression + " is not of type " +
                        ((ReferenceType) type).getInner() + ".");
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new HeapWriteStatement(this.id, this.expression.deepCopy());
    }

    public String toString() {
        return "wH(" + this.id + ", " + this.expression + ")";
    }
}
