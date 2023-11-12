package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value {
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        if (another instanceof IntValue) {
            return this.value == ((IntValue) another).getValue();
        }
        return false;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }

    public String toString() {
        return Integer.toString(this.value);
    }
}
