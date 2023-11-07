package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type {
    @Override
    public boolean equals(Type another) {
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
