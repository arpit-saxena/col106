package Util;

import Util.LinkedList.Consumer;

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
            expandCapacity((int) (capacity * capacityMultiplier) + 1);
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

    public void forEach(Consumer<T> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.consume(arr[i]);
        }
    }

    /**
     * Assuming triangles in sorted order and the triangle appearing
     * in both the lists are only added once
     * @param list1
     * @param list2
     * @return
     */
    public static <T extends Comparable<T>> ArrayList<T> merge2Lists(
        ArrayList<T> list1,
        ArrayList<T> list2
    ){
        ArrayList<T> finalList = new ArrayList<>(
            list1.size() + list2.size()
        );
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            int comp;
            if ((comp = list1.get(i).compareTo(list2.get(j))) == 0) {
                i++;
            } else if (comp < 0) {
                finalList.add(list1.get(i));
                i++;
            } else {
                finalList.add(list2.get(j));
                j++;
            }
        }

        while (i < list1.size()) {
            finalList.add(list1.get(i));
            i++;
        }

        while (j < list2.size()) {
            finalList.add(list2.get(j));
            j++;
        }
        return finalList;
    }

    /**
     * Merging done in such a way that same triangle in more than one
     * list is only added once
     */
    @SafeVarargs
    public static <T extends Comparable<T>> ArrayList<T> merge3Lists(ArrayList<T>... lists) {
            if (lists.length != 3) {
                throw new IllegalArgumentException("Expected 3 lists");
            }
            ArrayList<T> temp = merge2Lists(lists[0], lists[1]);
            return merge2Lists(temp, lists[2]);
        }
}