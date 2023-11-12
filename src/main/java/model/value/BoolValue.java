package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value {
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        if (another instanceof BoolValue) {
            return this.value == ((BoolValue) another).getValue();
        }
        return false;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.value);
    }

    public String toString() {
        return Boolean.toString(this.value);
    }
}
