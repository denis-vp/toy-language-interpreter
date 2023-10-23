package model.type;

public class BoolType implements Type {
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    public String toString() {
        return "bool";
    }
}
