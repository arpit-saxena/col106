package Util;

public class ArrayList<T> {
    static double capacityMultiplier = 1.5;

    T[] arr;
    int capacity;
    int size;

    public ArrayList() {
        this(10);
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(
                "Capacity expected to be non-negative, got " + capacity
            );
        }

        this.capacity = capacity;
        this.size = 0;
        arr = (T[]) new Object[capacity];
    }

    public void add(T val) {
        if (size == capacity) {
            expandCapacity((int) (capacity * capacityMultiplier));
        }

        arr[size++] = val;
    }

    public void addAll(T[] addArr) {
        if (size + addArr.length > capacity) {
            expandCapacity(
                (int)((size + addArr.length) * capacityMultiplier)
            );
        }

        System.arraycopy(addArr, 0, arr, size, addArr.length);
        size += addArr.length;
    }

    void expandCapacity(int newCapacity) {
        if (newCapacity <= capacity) return;

        T[] newArr = (T[]) new Object[newCapacity];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        capacity = newCapacity;
        arr = newArr;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return arr[index];
    }

    public int size() {
        return size;
    }

    public T[] toArray() {
        T[] ret = (T[]) new Object[size];
        System.arraycopy(arr, 0, ret, 0, size);
        return ret;
    }
}