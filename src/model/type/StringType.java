package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type {
    @Override
    public boolean equals(Type another) {
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }

    public String toString() {
        return "string";
    }
}