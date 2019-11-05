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
        T[] ret = (T[]) new Object[size];
        if (size == 0) return ret;
        Node curr = head;
        for(int i = 0; i < size; i++) {
            ret[i] = curr.value;
            curr = curr.next;
        }
        return ret;
    }

    public void forEach(Consumer<T> consumer) {
        for (Node curr = head; curr != null; curr = curr.next) {
            consumer.consume(curr.value);
        }
    }
}