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

    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        return another instanceof ReferenceType;
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
        return "ref (" + this.inner + ")";
    }
}
