package model.type;

import model.value.Value;

public interface Type {
    Value defaultValue();

    Type deepCopy();
}
