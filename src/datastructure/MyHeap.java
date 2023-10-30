package datastructure;

import exception.HeapException;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class MyHeap<V> implements MyIHeap<V> {
    private final Map<Integer, V> heap = new HashMap<>();
    private int nextFreeAddress = 0;

    private void moveNextFreeAddressForward() {
        while (this.heap.containsKey(this.nextFreeAddress)) {
            this.nextFreeAddress++;
        }
    }

    @Override
    public int getNextFreeAddress() {
        return this.nextFreeAddress;
    }

    @Override
    public void add(V value) {
        this.heap.put(this.nextFreeAddress, value);
        this.moveNextFreeAddressForward();
    }

    @Override
    public void add(Integer key, V value) {
        this.heap.put(key, value);
        if (key == this.nextFreeAddress) {
            this.moveNextFreeAddressForward();
        }
    }

    @Override
    public void remove(Integer key) throws HeapException {
        if (!this.heap.containsKey(key)) {
            throw new HeapException("Key does not exist.");
        }
        this.heap.remove(key);
        this.nextFreeAddress = key;
    }

    @Override
    public V get(Integer key) throws HeapException {
        if (!this.heap.containsKey(key)) {
            throw new HeapException("Key does not exist.");
        }
        return this.heap.get(key);
    }

    @Override
    public void update(Integer key, V value) throws HeapException {
        if (!this.heap.containsKey(key)) {
            throw new HeapException("Key does not exist.");
        }
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
        return this.heap.keySet();
    }

    @Override
    public ArrayList<V> values() {
        return new ArrayList<>(this.heap.values());
    }

    public String toString() {
        return this.heap.toString();
    }
}
