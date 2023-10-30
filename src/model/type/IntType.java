package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type {
    @Override
    public boolean equals(Type another) {
        return another instanceof IntType;
    }

    public Value defaultValue() {
        return new IntValue(0);
    }

    public Type deepCopy() {
        return new IntType();
    }

    public String toString() {
        return "int";
    }
}
