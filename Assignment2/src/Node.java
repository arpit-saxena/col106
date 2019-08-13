public class Node<V> extends NodeBase<V> {
	
	public Node(int priority, V value) {
		this.priority = priority;
		this.value = value;
	}

	public int getPriority() {
		return priority;
	}

	public V getValue() {
		return value;
	}

}
