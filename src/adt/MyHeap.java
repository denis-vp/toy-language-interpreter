package adt;

import model.value.Value;

import java.util.*;

public class MyHeap implements IHeap {
    private final Map<Integer, Value> heap = new HashMap<>();
    private int nextFreeAddress = 0;

    private void moveNextFreeAddress() {
        while (this.heap.containsKey(this.nextFreeAddress)) {
            this.nextFreeAddress++;
        }
    }

    @Override
    public int add(Value value) {
        int id = this.nextFreeAddress;
        this.heap.put(id, value);
        this.moveNextFreeAddress();
        return id;
    }

    @Override
    public void remove(Integer key) {
        this.heap.remove(key);
        this.nextFreeAddress = key;
    }

    @Override
    public Value get(Integer key) {
        return this.heap.get(key);
    }

    @Override
    public void update(Integer key, Value value) {
        this.heap.put(key, value);
    }

    @Override
    public boolean search(Integer key) {
        return this.heap.containsKey(key);
    }

    @Override
    public int size() {
        return this.heap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    @Override
    public Set<Integer> keys() {
        return new HashSet<>(this.heap.keySet());
    }

    @Override
    public Collection<Value> values() {
        Collection<Value> values = new ArrayList<>();
        for (Integer key : this.heap.keySet()) {
            values.add(this.heap.get(key));
        }
        return values;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer key : this.heap.keySet()) {
            stringBuilder.append(key.toString()).append(" -> ").append(this.heap.get(key).toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
