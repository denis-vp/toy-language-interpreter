package datastructure;

import exception.StackException;

import java.util.ArrayList;

public interface MyIStack<T> {
    void push(T element);

    T pop() throws StackException;

    T top() throws StackException;

    int size();

    boolean isEmpty();

    ArrayList<T> getAll();
}
