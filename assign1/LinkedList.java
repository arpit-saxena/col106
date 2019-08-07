import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements LinkedList_<T> {
	protected Position<T> head;
	protected Position<T> tail;

	public Position<T> add(T element) {
		Position<T> newPosition = new Position<T>(element);
		if (head == null) { // also implies that tail is null
			head = newPosition;
			tail = head;
			return newPosition;
		}
		
		if (head == tail) {
			tail = newPosition;
			head.setNext(tail);
			return newPosition;
		}

		tail.setNext(newPosition);
		tail = tail.after();
		return newPosition;
	}

	public PositionIterator<T> positions() {
		return new PositionIterator<T>(head);
	}

	public int count() {
		Iterator<Position_<T>> iter = positions();
		int c = 0;
		while (iter.hasNext()) {
			iter.next();
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
}