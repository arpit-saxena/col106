// This class implements the Queue
public class Queue<V> implements QueueInterface<V>{
    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;

    // currentSize = 0 => Next element is to be added at rear
    // else, next element is to be added at rear + 1

    @SuppressWarnings("unchecked")
    public Queue(int capacity) {
        this.capacity = capacity;
        queue = new NodeBase[capacity];
        currentSize = 0;
        front = rear = 0; 
    }

    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }
	
    public boolean isFull() {
        return size() == capacity;
    }

    // Won't do anything if it's full
    public void enqueue(Node<V> node) {
        if (isFull()) {
            System.err.println("enqueue called on already full queue");
            return;
        }

        if (currentSize == 0) {
            queue[rear] = node;
        } else {
            rear = (rear + 1) % capacity;
            queue[rear] = node;
        }

        currentSize++;
    }

    public NodeBase<V> dequeue() {
        if (isEmpty()) {
            System.err.println("dequeue called on empty queue");
            return null;
        }

        NodeBase<V> ret = queue[front];
        currentSize--;
        if (currentSize != 0) {
            front = (front + 1) % capacity;
        }

        return ret;
    }

}

