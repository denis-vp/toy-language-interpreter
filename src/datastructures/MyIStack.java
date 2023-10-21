package datastructures;

public interface MyIStack<Type> {
    Type top();

    void push(Type element);

    Type pop();

    int size();

    boolean isEmpty();
}
