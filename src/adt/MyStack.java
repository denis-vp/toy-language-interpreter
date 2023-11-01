package adt;

import exception.ADTException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack = new Stack<>();

    @Override
    public void push(T element) {
        synchronized (this.stack) {
            this.stack.push(element);
        }
    }

    @Override
    public T pop() throws ADTException {
        synchronized (this.stack) {
            try {
                return this.stack.pop();
            } catch (EmptyStackException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public T top() throws ADTException {
        synchronized (this.stack) {
            try {
                return this.stack.peek();
            } catch (EmptyStackException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public int size() {
        synchronized (this.stack) {
            return this.stack.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this.stack) {
            return this.stack.isEmpty();
        }
    }

    public String toString() {
        synchronized (this.stack) {
            StringBuilder stringBuilder = new StringBuilder();
            for (T element : this.stack.reversed()) {
                stringBuilder.append(element.toString()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
