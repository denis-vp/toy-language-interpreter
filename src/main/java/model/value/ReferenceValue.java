package model.value;

import model.type.ReferenceType;
import model.type.Type;

public class ReferenceValue implements Value {
    private final int address;
    private final Type locationType;

    public ReferenceValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    public Type getLocationType() {
        return this.locationType;
    }

    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (another == this) {
            return true;
        }

        if (another instanceof ReferenceValue) {
            return this.address == ((ReferenceValue) another).getAddress() &&
                    this.locationType.equals(((ReferenceValue) another).getLocationType());
        }
        return false;
    }

    @Override
    public Type getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(this.address, this.locationType.deepCopy());
    }

    public String toString() {
        return "(" + this.address + ", " + this.locationType + ")";
    }
}
