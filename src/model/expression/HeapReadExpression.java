package model.expression;

import adt.IDictionary;
import adt.IHeap;
import exception.ExpressionException;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapReadExpression implements Expression {
    private final Expression expression;

    public HeapReadExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(IDictionary<String, Value> symbolTable, IHeap heap) throws ExpressionException {
        Value value = this.expression.eval(symbolTable, heap);
        if (!value.getType().equals(new ReferenceType(null))) {
            throw new ExpressionException("Expression " + this.expression + " is not a reference type.");
        }

        ReferenceValue referenceValue = (ReferenceValue) value;
        if (!heap.search(referenceValue.getAddress())) {
            throw new ExpressionException("Address " + referenceValue.getAddress() + " is not in the heap.");
        }

        return heap.get(referenceValue.getAddress());
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws ExpressionException {
        Type type = this.expression.typeCheck(typeEnvironment);
        if (!type.equals(new ReferenceType(null))) {
            throw new ExpressionException("Expression " + this.expression + " is not a reference type.");
        }

        return ((ReferenceType) type).getInner();
    }

    @Override
    public Expression deepCopy() {
        return new HeapReadExpression(this.expression.deepCopy());
    }

    public String toString() {
        return "rH(" + this.expression + ")";
    }
}
