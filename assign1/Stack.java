public class Stack<T> extends LinkedList<T> {
    public Position<T> add(T element) {
        Position<T> newPosition = new Position<T>(element);
        newPosition.setNext(head);
        head = newPosition;
        if (tail == null) { // List was empty before this
            tail = head;
        }
		return newPosition;
	}
}