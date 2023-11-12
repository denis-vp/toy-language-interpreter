package adt;


import java.util.*;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public synchronized void add(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public synchronized void remove(K key) {
        this.map.remove(key);
    }

    @Override
    public synchronized V get(K key) {
        return this.map.get(key);
    }

    @Override
    public synchronized void update(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public synchronized boolean search(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public synchronized int size() {
        return this.map.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public synchronized Set<K> keys() {
        return new HashSet<>(this.map.keySet());
    }

    @Override
    public synchronized Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (K key : this.map.keySet()) {
            values.add(this.map.get(key));
        }
        return values;
    }

    @Override
    public synchronized IDictionary<K, V> deepCopy() {
        IDictionary<K, V> copy = new MyDictionary<>();
        for (K key : this.map.keySet()) {
            copy.add(key, this.map.get(key));
        }
        return copy;
    }

    public synchronized String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (K key : this.map.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
