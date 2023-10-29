package datastructure;

import exception.StackException;

import java.util.ArrayList;

public interface MyIStack<T> {
    T top() throws StackException;

    void push(T element);

    T pop() throws StackException;

    int size();

    boolean isEmpty();

    ArrayList<T> getAll();
}
