package adt;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyLatchTable implements ISyncTable {
    private final Map<Integer, Integer> latchTable = new ConcurrentHashMap<>();
    private int nextFreeAddress = 1;

    private void moveNextFreeAddress() {
        while (this.latchTable.containsKey(this.nextFreeAddress)) {
            this.nextFreeAddress++;
        }
    }

    @Override
    public synchronized int add(Object obj) {
        Integer value = (Integer) obj;
        this.latchTable.put(this.nextFreeAddress, value);
        this.moveNextFreeAddress();
        return this.nextFreeAddress - 1;
    }

    @Override
    public void remove(Integer key) {
        this.latchTable.remove(key);
    }

    @Override
    public Object get(Integer key) {
        return this.latchTable.get(key);
    }

    @Override
    public synchronized void update(Integer key, Object value) {
        Integer val = (Integer) value;
        this.latchTable.put(key, val);
    }

    @Override
    public boolean search(Integer key) {
        return this.latchTable.containsKey(key);
    }

    @Override
    public int size() {
        return this.latchTable.size();
    }

    @Override
    public boolean isEmpty() {
        return this.latchTable.isEmpty();
    }

    @Override
    public Set<Integer> keys() {
        return new HashSet<>(this.latchTable.keySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LatchTable:\n");
        for (Integer key : this.latchTable.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.latchTable.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
