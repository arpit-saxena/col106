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

    public void copyToArray(Object[] copyTo) {
        if (copyTo.length < size) {
            throw new RuntimeException("Size of passed array less");
        }
        System.arraycopy(arr, 0, copyTo, 0, size);
    }

    public T[] internalArray() {
        return arr;
    }

    public void forEach(Consumer<T> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.consume(arr[i]);
        }
    }

    public boolean linearSearch(T val, Comparator<T> comparator) {
        for (int i = 0; i < size; i++) {
            if (comparator.compare(val, arr[i]) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean binarySearch(T val, Comparator<T> comparator) {
        int begin = 0;
        int end = size - 1;
        while (end >= begin) {
            int mid = (begin + end) / 2;
            int res = comparator.compare(arr[mid], val);
            if (res == 0) {
                return true;
            } else if (res < 0) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return false;
    }

    public boolean search(T value, Comparator<T> comparator) {
        if (size < 10) {
            return linearSearch(value, comparator);
        }

        return binarySearch(value, comparator);
    }

    /**
     * Add at the end if value does not exist already
     * Uses comparator to check for equality
     * @param value What to insert
     * @param comparator Check for equality
     * @return true, if value was inserted; false if it already existed
     */
    public boolean addIfNotExists(T value, Comparator<T> comparator) {
        for (int i = size - 1; i >= 0; i--) {
            if (comparator.compare(arr[i], value) == 0) {
                return false;
            }
        }
        add(value);
        return true;
    }

    /**
     * Assuming triangles in sorted order and the triangle appearing
     * in both the lists are only added once
     * @param list1
     * @param list2
     * @return
     */
    public static <T extends Comparable<T>> ArrayList<T> merge2Lists(
        Comparator<T> comparator,
        ArrayList<T> list1,
        ArrayList<T> list2,
        final T elemExclude
    ){
        ArrayList<T> finalList = new ArrayList<>(
            list1.size() + list2.size()
        );
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (elemExclude != null) {
                if (comparator.compare(list1.get(i), elemExclude) == 0) {
                    i++;
                    continue;
                }
                if (comparator.compare(list2.get(j), elemExclude) == 0) {
                    j++;
                    continue;
                }
            }
            int comp;
            if ((comp = comparator.compare(list1.get(i), list2.get(j))) == 0) {
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
            if (
                elemExclude != null
                && comparator.compare(list1.get(i), elemExclude) == 0
            ){
                i++;
                continue;
            }
            finalList.add(list1.get(i));
            i++;
        }

        while (j < list2.size()) {
            if (
                elemExclude != null
                && comparator.compare(list2.get(j), elemExclude) == 0
            ){
                j++;
                continue;
            }
            finalList.add(list2.get(j));
            j++;
        }
        return finalList;
    }

    public static <T extends Comparable<T>> ArrayList<T> merge2Lists(
        Comparator<T> comparator,
        ArrayList<T> list1,
        ArrayList<T> list2
    ){
        return merge2Lists(comparator, list1, list2, null);
    }

    /**
     * Merging done in such a way that same triangle in more than one
     * list is only added once
     */
    public static <T extends Comparable<T>> ArrayList<T> merge3Lists(
        Comparator<T> comparator,
        ArrayList<T> list1,
        ArrayList<T> list2,
        ArrayList<T> list3
    ){
            ArrayList<T> temp = merge2Lists(comparator, list1, list2, null);
            return merge2Lists(comparator, temp, list3, null);
        }

    /**
     * Merging done in such a way that same triangle in more than one
     * list is only added once
     */
    public static <T extends Comparable<T>> ArrayList<T> merge3Lists(
        Comparator<T> comparator,
        ArrayList<T> list1,
        ArrayList<T> list2,
        ArrayList<T> list3,
        T elemExclude
    ){
            ArrayList<T> temp = merge2Lists(comparator, list1, list2, elemExclude);
            return merge2Lists(comparator, temp, list3, elemExclude);
        }

    /* Sorts */

    static final int SORT_SWITCH_SIZE = 8; // Switch to insertion sort below this
    Comparator<T> comparator = null;

    public static <T> void sortArray(T[] arr, Comparator<T> comparator) {
        ArrayList<T> list = new ArrayList<>();
        list.arr = arr;
        list.size = arr.length;
        list.capacity = arr.length;
        list.sort(comparator);
    }

    public void sort(Comparator<T> comparator) {
        if (comparator == null) return;
        this.comparator = comparator;
        sort(0, size - 1);
    }

    void sort(int begin, int end) {
        if (begin >= end) return;
        int size = end - begin + 1;
        if (size < SORT_SWITCH_SIZE) {
            insertionSort(begin, end);
        } else {
            quickSort(begin, end);
        }
    }

    void insertionSort(int begin, int end) {
        //Assuming begin <= end

        // arr[begin..i-1] is sorted, begin <= i <= end + 1
        int i = begin + 1;
        while (i <= end) {
            int j = i;
            T elem = arr[i];
            //INV: elem < arr[j+1..i], begin <= j <= i
            // Have to compare with arr[begin..j-1], arr[j] is empty
            while (j > begin) {
                int res = comparator.compare(elem, arr[j - 1]);
                if (res < 0) {
                    arr[j] = arr[j-1];
                } else {
                    break;
                }
                j--;
            }
            //assert: arr[begin..j-1] <= elem < arr[j+1..end]
            arr[j] = elem;
            i++;
        }
    }


    /**
     * Returns a random integer between low and high (both inclusive)
     */
    static int randomInt(int low, int high) {
        if (high < low) return 0;
        if (high == low) return low;
        long time = System.nanoTime();
        return (int) (low + time % (high - low + 1));
    }

    void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    void quickSort(int begin, int end) {
        // Assuming begin <= end

        T pivot = arr[randomInt(begin, end)];

        int i = begin, j = begin, k = end;
        // INV: arr[begin..i-1] < pivot, arr[i..j-1] == pivot, arr[k+1..end] > pivot
        // begin <= i <= j <= k + 1 <= end
        while (j < k + 1) {
            int res = comparator.compare(arr[j], pivot);
            if (res < 0) {
                swap(i, j);
                i++;
                j++;
            } else if (res == 0) {
                j++;
            } else {
                swap(j, k);
                k--;
            }
        }

        sort(begin, i - 1);
        sort(k + 1, end);
    }

    public static <T extends Comparable<T>> T[] bubbleSort(T[] arr) {
        sortArray(arr, (a, b) -> a.compareTo(b));
        return arr;
    }
}