package datastructure;

import exception.ListException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private final List<T> list = new ArrayList<>();

    @Override
    public void pushFront(T element) {
        this.list.addFirst(element);
    }

    @Override
    public T popFront() throws ListException {
        if (this.list.isEmpty()) {
            throw new ListException("List is empty.");
        }
        return this.list.removeFirst();
    }

    @Override
    public T first() throws ListException {
        if (this.list.isEmpty()) {
            throw new ListException("List is empty.");
        }
        return this.list.getFirst();
    }

    @Override
    public void pushBack(T element) {
        this.list.addLast(element);
    }

    @Override
    public T popBack() throws ListException {
        if (this.list.isEmpty()) {
            throw new ListException("List is empty.");
        }
        return this.list.removeLast();
    }

    @Override
    public T last() throws ListException {
        if (this.list.isEmpty()) {
            throw new ListException("List is empty.");
        }
        return this.list.getLast();
    }

    @Override
    public void add(int index, T element) {
        this.list.add(index, element);
    }

    @Override
    public T remove(int index) throws ListException {
        if (this.list.isEmpty() || index < 0 || index >= this.list.size()) {
            throw new ListException("List is empty.");
        }
        return this.list.remove(index);
    }

    @Override
    public T get(int index) throws ListException {
        if (this.list.isEmpty() || index < 0 || index >= this.list.size()) {
            throw new ListException("List is empty.");
        }
        return this.list.get(index);
    }

    @Override
    public void set(int index, T element) throws ListException {
        if (this.list.isEmpty() || index < 0 || index >= this.list.size()) {
            throw new ListException("List is empty.");
        }
        this.list.remove(index);
        this.list.add(index, element);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void sort(Comparator<T> comparator) {
        this.list.sort(comparator);
    }

    public ArrayList<T> getAll() {
        return new ArrayList<>(this.list);
    }

    public String toString() {
        return this.list.toString();
    }
}
