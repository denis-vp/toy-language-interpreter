package datastructure;


import exception.DictionaryException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Map<K, V> map = new HashMap<K, V>();

    @Override
    public void add(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) throws DictionaryException {
        if (!this.map.containsKey(key)) {
            throw new DictionaryException("Key does not exist.");
        }
        this.map.remove(key);
    }

    @Override
    public V get(K key) throws DictionaryException {
        if (!this.map.containsKey(key)) {
            throw new DictionaryException("Key does not exist.");
        }
        return this.map.get(key);
    }

    @Override
    public void update(K key, V value) throws DictionaryException {
        if (!this.map.containsKey(key)) {
            throw new DictionaryException("Key does not exist.");
        }
        this.map.put(key, value);
    }

    @Override
    public boolean search(K key) {
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
    public Set<K> keys() {
        return this.map.keySet();
    }

    @Override
    public ArrayList<V> values() {
        return new ArrayList<V>(this.map.values());
    }

    public String toString() {
        return this.map.toString();
    }
}
