package adt;

import exception.ADTException;

import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack = new Stack<>();

    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public T pop() throws ADTException {
        try {
            return this.stack.pop();
        } catch (EmptyStackException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public T top() throws ADTException {
        try {
            return this.stack.peek();
        } catch (EmptyStackException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T element : this.stack.reversed()) {
            stringBuilder.append(element.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
