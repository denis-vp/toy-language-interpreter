package model.statement;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.DictionaryException;
import exception.ExpressionException;
import exception.HeapException;
import exception.StatementException;
import model.expression.Expression;
import model.programstate.ProgramState;
import model.type.ReferenceType;
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
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeap<Value> heap = state.getHeap();

        try {
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

            Value expressionValue = this.expression.eval(symbolTable, heap);
            if (!expressionValue.getType().equals(referenceValue.getLocationType())) {
                throw new StatementException("Expression " + this.expression + " is not of type " + referenceValue.getLocationType() + ".");
            }

            heap.update(referenceValue.getAddress(), expressionValue);
        } catch (ExpressionException | DictionaryException | HeapException e) {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public Statement deepCopy() throws StatementException {
        try {
            return new HeapWriteStatement(this.id, this.expression.deepCopy());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString() {
        return "wH(" + this.id + ", " + this.expression + ")";
    }
}
