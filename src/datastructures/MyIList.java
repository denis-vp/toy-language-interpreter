package datastructures;

import java.util.Comparator;

public interface MyIList<Type> {
    Type first();

    void pushFront(Type element);

    Type popFront();

    Type last();

    void pushBack(Type element);

    Type popBack();

    void add(int index, Type element);

    Type remove(int index);

    Type get(int index);

    void set(int index, Type element);

    int size();

    boolean isEmpty();

    void sort(Comparator<Type> comparator);
}
