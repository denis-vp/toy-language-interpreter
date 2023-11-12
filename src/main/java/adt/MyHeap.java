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
    public synchronized int add(Value value) {
        this.heap.put(this.nextFreeAddress, value);
        this.moveNextFreeAddress();
        return this.nextFreeAddress - 1;
    }

    @Override
    public synchronized void remove(Integer key) {
        this.heap.remove(key);
    }

    @Override
    public synchronized Value get(Integer key) {
        return this.heap.get(key);
    }

    @Override
    public synchronized void update(Integer key, Value value) {
        this.heap.put(key, value);
    }

    @Override
    public synchronized boolean search(Integer key) {
        return this.heap.containsKey(key);
    }

    @Override
    public synchronized int size() {
        return this.heap.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.heap.isEmpty();
    }

    @Override
    public synchronized Set<Integer> keys() {
        return new HashSet<>(this.heap.keySet());
    }

    @Override
    public synchronized Collection<Value> values() {
        return new ArrayList<>(this.heap.values());
    }

    public synchronized String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer key : this.heap.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.heap.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
