package datastructure;

import exception.ListException;

import java.util.Comparator;

public interface MyIList<Type> {
    Type first() throws ListException;

    void pushFront(Type element);

    Type popFront() throws ListException;

    Type last() throws ListException;

    void pushBack(Type element);

    Type popBack() throws ListException;

    void add(int index, Type element);

    Type remove(int index) throws ListException;

    Type get(int index) throws ListException;

    void set(int index, Type element) throws ListException;

    int size();

    boolean isEmpty();

    void sort(Comparator<Type> comparator);
}
