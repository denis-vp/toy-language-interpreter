package adt;

import model.statement.Statement;

import java.util.*;

public class MyProcedureTable implements IDictionary<String, Pair<List<String>, Statement>> {
    private final Map<String, Pair<List<String>, Statement>> map = new HashMap<>();

    @Override
    public void add(String key, Pair<List<String>, Statement> value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(String key) {
        this.map.remove(key);
    }

    @Override
    public Pair<List<String>, Statement> get(String key) {
        return this.map.get(key);
    }

    @Override
    public void update(String key, Pair<List<String>, Statement> value) {
        this.map.put(key, value);
    }

    @Override
    public boolean search(String key) {
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
    public Collection<Map.Entry<String, Pair<List<String>, Statement>>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public Set<String> keys() {
        return new HashSet<>(this.map.keySet());
    }

    @Override
    public Collection<Pair<List<String>, Statement>> values() {
        Collection<Pair<List<String>, Statement>> values = new ArrayList<>();
        for (String key : this.map.keySet()) {
            values.add(this.map.get(key));
        }
        return values;
    }

    @Override
    public IDictionary<String, Pair<List<String>, Statement>> deepCopy() {
        IDictionary<String, Pair<List<String>, Statement>> copy = new MyProcedureTable();
        for (String key : this.map.keySet()) {
            Pair<List<String>, Statement> pair = this.map.get(key);
            List<String> copyList = new ArrayList<>(pair.getFirst());
            copy.add(key, new Pair<>(copyList, pair.getSecond()));
        }
        return copy;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : this.map.keySet()) {
            stringBuilder.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
