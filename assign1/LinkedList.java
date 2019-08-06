import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements LinkedList_<T> {
	protected Position<T> head;
	protected Position<T> tail;

	public Position<T> add(T element) {
		if (head == null) { // also implies that tail is null
			head = new Position<T>(element);
			tail = head;
			return head;
		}
		
		if (head == tail) {
			tail = new Position<T>(element);
			head.setNext(tail);
			return tail;
		}

		Position<T> temp = new Position<T>(element);
		tail.setNext(temp);
		tail = tail.after();
		return temp;
	}

	public PositionIterator<T> positions() {
		return new PositionIterator<T>(head);
	}

	public int count() {
		Position<T> curr = head;
		int c = 0;
		while (curr != null) {
			curr = curr.after();
			c++;
		}
		return c;
	}

	public Iterator<T> elements() {
		return new ElementIterator<T>(head);
	}
}

class PositionIterator<T> implements Iterator<Position_<T>> {
	Position<T> position;

	public PositionIterator (Position<T> position) {
		this.position = position;
	}

	public Position<T> next() throws NoSuchElementException{
		if (position == null) {
			throw new NoSuchElementException();
		}
		
		Position<T> temp = position;
		position = position.after();
		return temp;
	}

	public boolean hasNext() {
		return position != null;
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}

class ElementIterator<T> implements Iterator<T> {
	Position<T> position;

	public ElementIterator (Position<T> position) {
		this.position = position;
	}

	public T next() throws NoSuchElementException {
		if (position == null) {
			throw new NoSuchElementException();
		}

		T currVal = position.value();
		position = position.after();
		return currVal;
	}

	public boolean hasNext() {
		return position != null;
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}