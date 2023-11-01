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
        synchronized (this) {
            this.heap.put(this.nextFreeAddress, value);
            this.moveNextFreeAddress();
            return this.nextFreeAddress - 1;
        }
    }

    @Override
    public void remove(Integer key) {
        synchronized (this.heap) {
            this.heap.remove(key);
        }
    }

    @Override
    public Value get(Integer key) {
        synchronized (this.heap) {
            return this.heap.get(key);
        }
    }

    @Override
    public void update(Integer key, Value value) {
        synchronized (this.heap) {
            this.heap.put(key, value);
        }
    }

    @Override
    public boolean search(Integer key) {
        synchronized (this.heap) {
            return this.heap.containsKey(key);
        }
    }

    @Override
    public int size() {
        synchronized (this.heap) {
            return this.heap.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this.heap) {
            return this.heap.isEmpty();
        }
    }

    @Override
    public Set<Integer> keys() {
        synchronized (this.heap) {
            return new HashSet<>(this.heap.keySet());
        }
    }

    @Override
    public Collection<Value> values() {
        synchronized (this.heap) {
            return new ArrayList<>(this.heap.values());
        }
    }

    public String toString() {
        synchronized (this.heap) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Integer key : this.heap.keySet()) {
                stringBuilder.append(key).append(" -> ").append(this.heap.get(key)).append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
