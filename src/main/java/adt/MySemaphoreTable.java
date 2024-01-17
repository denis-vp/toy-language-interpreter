package adt;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MySemaphoreTable implements ISyncTable {
    private final Map<Integer, Pair<Integer, List<Integer>>> semaphoreTable = new ConcurrentHashMap<>();
    private int nextFreeAddress = 1;

    private void moveNextFreeAddress() {
        while (this.semaphoreTable.containsKey(this.nextFreeAddress)) {
            this.nextFreeAddress++;
        }
    }

    @Override
    public synchronized int add(Object obj) {
        Pair<Integer, List<Integer>> pair = (Pair<Integer, List<Integer>>) obj;
        this.semaphoreTable.put(this.nextFreeAddress, pair);
        this.moveNextFreeAddress();
        return this.nextFreeAddress - 1;
    }

    @Override
    public void remove(Integer key) {
        this.semaphoreTable.remove(key);
    }

    @Override
    public Object get(Integer key) {
        return this.semaphoreTable.get(key);
    }

    @Override
    public synchronized void update(Integer key, Object value) {
        Pair<Integer, List<Integer>> pair = (Pair<Integer, List<Integer>>) value;
        this.semaphoreTable.put(key, pair);
    }

    @Override
    public boolean search(Integer key) {
        return this.semaphoreTable.containsKey(key);
    }

    @Override
    public int size() {
        return this.semaphoreTable.size();
    }

    @Override
    public boolean isEmpty() {
        return this.semaphoreTable.isEmpty();
    }

    @Override
    public Set<Integer> keys() {
        return new HashSet<>(this.semaphoreTable.keySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SemaphoreTable:\n");
        for (Integer key : this.semaphoreTable.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.semaphoreTable.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
