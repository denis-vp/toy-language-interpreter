package model.type;

import model.value.Value;

public interface Type {
    boolean equals(Type another);

    Value defaultValue();

    Type deepCopy();
}
