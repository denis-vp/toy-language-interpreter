package model.expression;

import datastructure.MyIDictionary;
import datastructure.MyIHeap;
import exception.ExpressionException;
import exception.HeapException;
import model.type.ReferenceType;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapReadExpression implements Expression {
    private final Expression expression;

    public HeapReadExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heap) throws ExpressionException {
        try {
            Value value = this.expression.eval(symbolTable, heap);
            if (!value.getType().equals(new ReferenceType(null))) {
                throw new ExpressionException("Expression " + this.expression + " is not a reference type.");
            }
            ReferenceValue referenceValue = (ReferenceValue) value;
            if (!heap.search(referenceValue.getAddress())) {
                throw new ExpressionException("Address " + referenceValue.getAddress() + " is not in the heap.");
            }
            return heap.get(referenceValue.getAddress());
        } catch (HeapException e) {
            throw new ExpressionException(e.getMessage());
        }
    }

    @Override
    public Expression deepCopy() throws ExpressionException {
        return null;
    }

    public String toString() {
        return "rH(" + this.expression + ")";
    }
}
