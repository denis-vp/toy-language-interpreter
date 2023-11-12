package adt;

import exception.ADTException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    @Override
    public synchronized void pushFront(T element) {
        this.list.add(0, element);
    }

    @Override
    public synchronized T popFront() throws ADTException {
        try {
            return this.list.remove(0);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized T first() throws ADTException {
        try {
            return this.list.getFirst();
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized void pushBack(T element) {
        this.list.add(element);
    }

    @Override
    public synchronized T popBack() throws ADTException {
        try {
            return this.list.remove(this.list.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized T last() throws ADTException {
        try {
            return this.list.getLast();
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized void add(int index, T element) throws ADTException {
        try {
            this.list.add(index, element);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized T remove(int index) throws ADTException {
        try {
            return this.list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized T get(int index) throws ADTException {
        try {
            return this.list.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized void set(int index, T element) throws ADTException {
        try {
            this.list.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public synchronized int size() {
        return this.list.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.list.isEmpty();
    }

    public synchronized Collection<T> values() {
        return new ArrayList<>(this.list);
    }

    public synchronized String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T element : this.list) {
            stringBuilder.append(element.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
