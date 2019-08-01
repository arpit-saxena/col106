interface ComparisonMethod<T> {
    // Returns true if a comes before b
    boolean compare(T a, T b);
}

public class SortedLinkedList<T> extends LinkedList<T> {
    ComparisonMethod<T> comparisonMethod;

    SortedLinkedList(ComparisonMethod<T> comparisonMethod) {
        super();
        this.comparisonMethod = comparisonMethod;
    }

    public Position<T> add(T element) {
        Position<T> newElement = new Position<T>(element);
        if (head == null) {
            head = newElement;
            tail = head;
            return head;
        }

        if (comparisonMethod.compare(element, head.value())) {
            newElement.setNext(head);
            head = newElement;
            return head;
        }

        // Checking if newElement will go before current or not
        // We have already made sure it's after all the previous elements
        // so we basically found it's right place.
        Position<T> prev = head;
        Position<T> current = head.after();
        while (current != null) {
            if (comparisonMethod.compare(element, current.value())) {
                newElement.setNext(prev.after());
                prev.setNext(newElement);
                return newElement;
            }
            prev = current;
            current = prev.after();
        }

        // Here, current = null, so prev is basically last element of linked list
        prev.setNext(newElement);
        return newElement;
    }
}