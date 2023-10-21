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

    public String toString() {
        return Boolean.toString(this.value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }
}
