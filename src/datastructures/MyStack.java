package datastructures;

import java.util.Stack;

public class MyStack<Type> implements MyIStack<Type> {
    private final Stack<Type> stack = new Stack<Type>();

    @Override
    public Type top() {
        return this.stack.peek();
    }

    @Override
    public void push(Type element) {
        this.stack.push(element);
    }

    @Override
    public Type pop() {
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
