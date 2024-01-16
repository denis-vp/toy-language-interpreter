package adt;

import java.util.Set;

public interface ISyncTable {
    int add(Object obj);

    void remove(Integer key);

    int get(Integer key);

    void update(Integer key, Integer value);

    boolean search(Integer key);

    int size();

    boolean isEmpty();

    Set<Integer> keys();
}
