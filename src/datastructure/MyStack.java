package datastructure;

import exception.StackException;

import java.util.Stack;

public class MyStack<Type> implements MyIStack<Type> {
    private final Stack<Type> stack = new Stack<Type>();

    @Override
    public Type top() throws StackException {
        if (this.stack.isEmpty()) {
            throw new StackException("Stack is empty.");
        }
        return this.stack.peek();
    }

    @Override
    public void push(Type element) {
        this.stack.push(element);
    }

    @Override
    public Type pop() throws StackException {
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
}
