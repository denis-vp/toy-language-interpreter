package adt;

import model.value.Value;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap implements IHeap {
    private final Map<Integer, Value> heap = new ConcurrentHashMap<>();
    private int nextFreeAddress = 1;

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
    public void remove(Integer key) {
        this.heap.remove(key);
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
    public Map<Integer, Value> getContent() {
        return new HashMap<>(this.heap);
    }

    @Override
    public void setContent(Map<Integer, Value> heap) {
        this.heap.clear();
        this.heap.putAll(heap);
    }

    @Override
    public Set<Integer> keys() {
        return new HashSet<>(this.heap.keySet());
    }

    @Override
    public Collection<Value> values() {
        return new ArrayList<>(this.heap.values());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer key : this.heap.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.heap.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
