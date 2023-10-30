package datastructure;

import exception.ListException;

import java.util.ArrayList;
import java.util.Comparator;

public interface MyIList<T> {
    void pushFront(T element);

    T popFront() throws ListException;

    T first() throws ListException;

    void pushBack(T element);

    T popBack() throws ListException;

    T last() throws ListException;

    void add(int index, T element);

    T remove(int index) throws ListException;

    T get(int index) throws ListException;

    void set(int index, T element) throws ListException;

    int size();

    boolean isEmpty();

    void sort(Comparator<T> comparator);

    ArrayList<T> getAll();
}
