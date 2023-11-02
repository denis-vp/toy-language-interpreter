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
        synchronized (this.list) {
            this.list.add(0, element);
        }
    }

    @Override
    public T popFront() throws ADTException {
        synchronized (this.list) {
            try {
                return this.list.remove(0);
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public T first() throws ADTException {
        synchronized (this.list) {
            try {
                return this.list.getFirst();
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public void pushBack(T element) {
        synchronized (this.list) {
            this.list.add(element);
        }
    }

    @Override
    public T popBack() throws ADTException {
        synchronized (this.list) {
            try {
                return this.list.remove(this.list.size() - 1);
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public T last() throws ADTException {
        synchronized (this.list) {
            try {
                return this.list.getLast();
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public void add(int index, T element) throws ADTException {
        synchronized (this.list) {
            try {
                this.list.add(index, element);
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public T remove(int index) throws ADTException {
        synchronized (this.list) {
            try {
                return this.list.remove(index);
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public T get(int index) throws ADTException {
        synchronized (this.list) {
            try {
                return this.list.get(index);
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public void set(int index, T element) throws ADTException {
        synchronized (this.list) {
            try {
                this.list.set(index, element);
            } catch (IndexOutOfBoundsException e) {
                throw new ADTException(e.getMessage());
            }
        }
    }

    @Override
    public int size() {
        synchronized (this.list) {
            return this.list.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this.list) {
            return this.list.isEmpty();
        }
    }

    public Collection<T> values() {
        synchronized (this.list) {
            return new ArrayList<>(this.list);
        }
    }

    public String toString() {
        synchronized (this.list) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (T element : this.list) {
                stringBuilder.append(element.toString());
                stringBuilder.append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("]\n");
            return stringBuilder.toString();
        }
    }
}
