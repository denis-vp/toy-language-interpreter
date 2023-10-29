package datastructure;

import exception.StackException;

import java.util.ArrayList;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private final Stack<T> stack = new Stack<>();

    @Override
    public T top() throws StackException {
        if (this.stack.isEmpty()) {
            throw new StackException("Stack is empty.");
        }
        return this.stack.peek();
    }

    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public T pop() throws StackException {
        if (this.stack.isEmpty()) {
            throw new StackException("Stack is empty.");
        }
        return this.stack.pop();
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
        return this.stack.toString();
    }

    public ArrayList<T> getAll() {
        return new ArrayList<>(this.stack);
    }
}
