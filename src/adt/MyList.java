package adt;

import exception.ADTException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    @Override
    public void pushFront(T element) {
        this.list.addFirst(element);
    }

    @Override
    public T popFront() throws ADTException {
        try {
            return this.list.removeFirst();
        } catch (NoSuchElementException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public T first() throws ADTException {
        try {
            return this.list.getFirst();
        } catch (NoSuchElementException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public void pushBack(T element) {
        this.list.addLast(element);
    }

    @Override
    public T popBack() throws ADTException {
        try {
            return this.list.removeLast();
        } catch (NoSuchElementException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public T last() throws ADTException {
        try {
            return this.list.getLast();
        } catch (NoSuchElementException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public void add(int index, T element) throws ADTException {
        try {
            this.list.add(index, element);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public T remove(int index) throws ADTException {
        try {
            return this.list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public T get(int index) throws ADTException {
        try {
            return this.list.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public void set(int index, T element) throws ADTException {
        try {
            this.list.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            throw new ADTException(e.getMessage());
        }
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Collection<T> values() {
        return new ArrayList<>(this.list);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T element : this.list) {
            stringBuilder.append(element.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
