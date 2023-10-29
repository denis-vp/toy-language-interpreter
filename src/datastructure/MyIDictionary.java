package datastructure;

import exception.DictionaryException;

import java.util.ArrayList;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void add(K key, V value);

    void remove(K key) throws DictionaryException;

    V get(K key) throws DictionaryException;

    void update(K key, V value) throws DictionaryException;

    boolean search(K key);

    int size();

    boolean isEmpty();

    Set<K> keys();

    ArrayList<V> values();
}
