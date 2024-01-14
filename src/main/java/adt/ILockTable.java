package adt;

public interface ILockTable {
    int add();

    void remove(Integer key);

    int get(Integer key);

    void update(Integer key, Integer value);

    boolean search(Integer key);

    int size();

    boolean isEmpty();
}
