package adt;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyLockTable implements ISyncTable {
    private final Map<Integer, Integer> lockTable = new ConcurrentHashMap<>();
    private int nextFreeAddress = 1;

    private void moveNextFreeAddress() {
        while (this.lockTable.containsKey(this.nextFreeAddress)) {
            this.nextFreeAddress++;
        }
    }

    @Override
    public synchronized int add(Object obj) {
        this.lockTable.put(this.nextFreeAddress, -1);
        this.moveNextFreeAddress();
        return this.nextFreeAddress - 1;
    }

    @Override
    public void remove(Integer key) {
        this.lockTable.remove(key);
    }

    @Override
    public Object get(Integer key) {
        return this.lockTable.get(key);
    }

    @Override
    public synchronized void update(Integer key, Object value) {
        Integer val = (Integer) value;
        this.lockTable.put(key, val);
    }

    @Override
    public boolean search(Integer key) {
        return this.lockTable.containsKey(key);
    }

    @Override
    public int size() {
        return this.lockTable.size();
    }

    @Override
    public boolean isEmpty() {
        return this.lockTable.isEmpty();
    }

    @Override
    public Set<Integer> keys() {
        return new HashSet<>(this.lockTable.keySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer key : this.lockTable.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.lockTable.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
