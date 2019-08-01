import interfaces.Position_;

class Position<T> implements Position_<T> {
	private Position<T> next;
	private T val;

	public Position(T val) {
		this.val = val;
	}

	public void setNext(Position<T> next) {
		this.next = next;
	}

	public T value() {
		return val;
	}

	public Position<T> after() {
		return next;
	}
}
