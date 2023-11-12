package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type {
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        return another instanceof IntType;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    public String toString() {
        return "int";
    }
}
