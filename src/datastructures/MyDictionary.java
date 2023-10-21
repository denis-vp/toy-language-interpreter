package datastructures;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<KeyType, ValueType> implements MyIDictionary<KeyType, ValueType> {
    private final Map<KeyType, ValueType> map = new HashMap<KeyType, ValueType>();

    @Override
    public void add(KeyType key, ValueType value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(KeyType key) {
        this.map.remove(key);
    }

    @Override
    public ValueType get(KeyType key) {
        return this.map.get(key);
    }

    @Override
    public void update(KeyType key, ValueType value) {
        this.map.put(key, value);
    }

    @Override
    public boolean search(KeyType key) {
        return this.map.containsKey(key);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<KeyType> keys() {
        return this.map.keySet();
    }

    @Override
    public ArrayList<ValueType> values() {
        return new ArrayList<ValueType>(this.map.values());
    }

    public String toString() {
        return this.map.toString();
    }
}
