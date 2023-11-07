package adt;


import java.util.*;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public void add(K key, V value) {
        synchronized (this.map) {
            this.map.put(key, value);
        }
    }

    @Override
    public void remove(K key) {
        synchronized (this.map) {
            this.map.remove(key);
        }
    }

    @Override
    public V get(K key) {
        synchronized (this.map) {
            return this.map.get(key);
        }
    }

    @Override
    public void update(K key, V value) {
        synchronized (this.map) {
            this.map.put(key, value);
        }
    }

    @Override
    public boolean search(K key) {
        synchronized (this.map) {
            return this.map.containsKey(key);
        }
    }

    @Override
    public int size() {
        synchronized (this.map) {
            return this.map.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this.map) {
            return this.map.isEmpty();
        }
    }

    @Override
    public Set<K> keys() {
        synchronized (this.map) {
            return new HashSet<>(this.map.keySet());
        }
    }

    @Override
    public Collection<V> values() {
        synchronized (this.map) {
            Collection<V> values = new ArrayList<>();
            for (K key : this.map.keySet()) {
                values.add(this.map.get(key));
            }
            return values;
        }
    }

    @Override
    public IDictionary<K, V> deepCopy() {
        synchronized (this.map) {
            IDictionary<K, V> copy = new MyDictionary<>();
            for (K key : this.map.keySet()) {
                copy.add(key, this.map.get(key));
            }
            return copy;
        }
    }

    public String toString() {
        synchronized (this.map) {
            StringBuilder stringBuilder = new StringBuilder();
            for (K key : this.map.keySet()) {
                stringBuilder.append(key).append(" -> ").append(this.map.get(key)).append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
