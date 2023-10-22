package datastructure;

import exception.StackException;

public interface MyIStack<Type> {
    Type top() throws StackException;

    void push(Type element);

    Type pop() throws StackException;

    int size();

    boolean isEmpty();
}
