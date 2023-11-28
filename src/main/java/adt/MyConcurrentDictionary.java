package adt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyConcurrentDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new ConcurrentHashMap<>();

    @Override
    public void add(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) {
        this.map.remove(key);
    }

    @Override
    public V get(K key) {
        return this.map.get(key);
    }

    @Override
    public void update(K key, V value) {
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
    public Collection<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public Set<K> keys() {
        return new HashSet<>(this.map.keySet());
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (K key : this.map.keySet()) {
            values.add(this.map.get(key));
        }
        return values;
    }

    @Override
    public IDictionary<K, V> deepCopy() {
        IDictionary<K, V> copy = new MyDictionary<>();
        for (K key : this.map.keySet()) {
            copy.add(key, this.map.get(key));
        }
        return copy;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (K key : this.map.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
