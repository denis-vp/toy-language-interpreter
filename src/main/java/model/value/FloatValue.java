package model.value;

import model.type.FloatType;
import model.type.Type;

public class FloatValue implements Value {
    private final float value;

    public FloatValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        if (another instanceof FloatValue) {
            return this.value == ((FloatValue) another).getValue();
        }
        return false;
    }

    @Override
    public Type getType() {
        return new FloatType();
    }

    @Override
    public Value deepCopy() {
        return new FloatValue(this.value);
    }

    public String toString() {
        return Float.toString(this.value);
    }
}
