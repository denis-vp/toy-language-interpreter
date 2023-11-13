package model.type;

import model.value.FloatValue;
import model.value.Value;

public class FloatType implements Type {
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        return another instanceof FloatType;
    }

    @Override
    public Value defaultValue() {
        return new FloatValue(0);
    }

    @Override
    public Type deepCopy() {
        return new FloatType();
    }

    public String toString() {
        return "float";
    }
}
