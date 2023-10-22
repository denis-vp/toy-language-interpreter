package datastructure;

import exception.DictionaryException;

import java.util.ArrayList;
import java.util.Set;

public interface MyIDictionary<KeyType, ValueType> {
    void add(KeyType key, ValueType value);

    void remove(KeyType key) throws DictionaryException;

    ValueType get(KeyType key) throws DictionaryException;

    void update(KeyType key, ValueType value) throws DictionaryException;

    boolean search(KeyType key);

    int size();

    boolean isEmpty();

    Set<KeyType> keys();

    ArrayList<ValueType> values();
}
