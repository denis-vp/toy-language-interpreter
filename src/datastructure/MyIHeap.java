package datastructure;

import exception.HeapException;

import java.util.ArrayList;
import java.util.Set;

public interface MyIHeap<V> {
    int getNextFreeAddress();

    void add(V value);

    void add(Integer key, V value);

    void remove(Integer key) throws HeapException;

    V get(Integer key) throws HeapException;

    void update(Integer key, V value) throws HeapException;

    boolean search(Integer key);

    int size();

    boolean isEmpty();

    Set<Integer> keys();

    ArrayList<V> values();
}
