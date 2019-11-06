package Util;

public class LinkedList<T> {
    public class Node {
        public T value;
        public Node next;
        public Node previous;

        public Node(T value) {
            this.value = value;
        }
    }

    public interface Consumer<T> {
        public void consume(T obj);
    }

    Node head = null;
    Node tail = null;
    int size = 0;

    public Node add(T value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            tail = head;
        } else {
            tail.next = node;
            tail.next.previous = tail;
            tail = tail.next;
        }
        size++;
        return node;
    }

    public void delete(Node node) {
        if (node == null) return;

        if (node == head) head = node.next;
        if (node == tail) tail = node.previous;

        if (node.next != null) {
            node.next.previous = node.previous;
        }

        if (node.previous != null) {
            node.previous.next = node.next;
        }

        size--;
    }

    public int size() {
        return size;
    }

    public T[] toArray() {
        Object[] ret = new Object[size];
        if (size == 0) return (T[]) ret;
        return (T[]) toArray(ret);
    }

    private Object[] toArray(Object[] arr) {
        Node curr = head;
        for(int i = 0; i < size; i++) {
            arr[i] = curr.value;
            curr = curr.next;
        }
        return arr;
    }

    public void copyToArray(Object[] arr) {
        if (arr.length < size) {
            throw new RuntimeException("Array length less than list size");
        }
        toArray(arr);
    }

    public void forEach(Consumer<T> consumer) {
        for (Node curr = head; curr != null; curr = curr.next) {
            consumer.consume(curr.value);
        }
    }
}