package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        if (another instanceof StringValue) {
            return this.value.equals(((StringValue) another).getValue());
        }
        return false;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(this.value);
    }

    public String toString() {
        return String.format("\"%s\"", this.value);
    }
}
