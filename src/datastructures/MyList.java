package datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MyList<Type> implements MyIList<Type>, Iterable<Type> {
    private final List<Type> list = new ArrayList<Type>();

    @Override
    public Type first() {
        return this.list.getFirst();
    }

    @Override
    public void pushFront(Type element) {
        this.list.addFirst(element);
    }

    @Override
    public Type popFront() {
        return this.list.removeFirst();
    }

    @Override
    public Type last() {
        return this.list.getLast();
    }

    @Override
    public void pushBack(Type element) {
        this.list.addLast(element);
    }

    @Override
    public Type popBack() {
        return this.list.removeLast();
    }

    @Override
    public void add(int index, Type element) {
        this.list.add(index, element);
    }

    @Override
    public Type remove(int index) {
        return this.list.remove(index);
    }

    @Override
    public Type get(int index) {
        return this.list.get(index);
    }

    @Override
    public void set(int index, Type element) {
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
    public void sort(Comparator<Type> comparator) {
        this.list.sort(comparator);
    }

    @Override
    public Iterator<Type> iterator() {
        return this.list.iterator();
    }

    public String toString() {
        return this.list.toString();
    }
}
