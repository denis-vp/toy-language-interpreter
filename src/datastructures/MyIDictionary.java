package datastructures;

import java.util.ArrayList;
import java.util.Set;

public interface MyIDictionary<KeyType, ValueType> {
    void add(KeyType key, ValueType value);

    void remove(KeyType key);

    ValueType get(KeyType key);

    void update(KeyType key, ValueType value);

    boolean search(KeyType key);

    int size();

    boolean isEmpty();

    Set<KeyType> keys();

    ArrayList<ValueType> values();
}
