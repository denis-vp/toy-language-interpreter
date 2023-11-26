package adt;

import model.value.Value;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IHeap {
    int add(Value value);

    void remove(Integer key);

    Value get(Integer key);

    void update(Integer key, Value value);

    boolean search(Integer key);

    int size();

    boolean isEmpty();

    Map<Integer, Value> getContent();

    void setContent(Map<Integer, Value> heap);

    Set<Integer> keys();

    Collection<Value> values();
}
