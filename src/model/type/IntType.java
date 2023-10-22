package model.type;

public class IntType implements Type {

    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "int";
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
