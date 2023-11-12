package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type {
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        return another instanceof BoolType;
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    public String toString() {
        return "bool";
    }
}
