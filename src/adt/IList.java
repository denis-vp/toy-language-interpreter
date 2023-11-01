package adt;

import exception.ADTException;

import java.util.Collection;

public interface IList<T> {
    void pushFront(T element);

    T popFront() throws ADTException;

    T first() throws ADTException;

    void pushBack(T element);

    T popBack() throws ADTException;

    T last() throws ADTException;

    void add(int index, T element) throws ADTException;

    T remove(int index) throws ADTException;

    T get(int index) throws ADTException;

    void set(int index, T element) throws ADTException;

    int size();

    boolean isEmpty();

    Collection<T> values();
}
