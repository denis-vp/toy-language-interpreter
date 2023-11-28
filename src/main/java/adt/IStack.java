package adt;

import exception.ADTException;

import java.util.Collection;

public interface IStack<T> {
    void push(T element);

    T pop() throws ADTException;

    T top() throws ADTException;

    int size();

    boolean isEmpty();

    Collection<T> reversed();
}
