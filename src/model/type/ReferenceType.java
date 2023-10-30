package model.type;

import model.value.ReferenceValue;
import model.value.Value;

public class ReferenceType implements Type {
    private final Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return this.inner;
    }

    @Override
    public boolean equals(Type another) {
        return another instanceof ReferenceType && this.inner.equals(((ReferenceType) another).getInner());
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0, this.inner);
    }

    @Override
    public Type deepCopy() {
        return new ReferenceType(this.inner.deepCopy());
    }

    public String toString() {
        return "ref(" + this.inner + ")";
    }
}
