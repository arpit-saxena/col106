
public class PriorityQueue<V> implements QueueInterface<V> {

    private NodeBase<V>[] queue;
    private int capacity, currentSize;
    private int front, rear;

    @SuppressWarnings("unchecked")
    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        queue = new NodeBase[capacity];
        currentSize = 0;
        front = rear = 0; 
    }

    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean isFull() {
        return currentSize == capacity;
    }

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

    public void add(int priority, V value) {
        enqueue(new Node<V>(priority, value));
    }

    // In case of priority queue, the dequeue() should
    // always remove the element with minimum priority value
    public NodeBase<V> dequeue() {
        if (isEmpty()) {
            System.err.println("dequeue called on empty queue");
            return null;
        }

        NodeBase<V> ret = queue[front];
        int minPriority = ret.getPriority();
        int indexRet = front;
        for(int i = 1; i < currentSize; i++) {
            NodeBase<V> curr = queue[(front + i) % capacity];
            if (curr.getPriority() < minPriority) {
                ret = curr;
                minPriority = curr.getPriority();
                indexRet = (front + i) % capacity;
            }
        }

        currentSize--;

        if (currentSize != 0) {
            queue[indexRet] = queue[rear];
            rear = (rear + capacity - 1) % capacity;
        }

        return ret;
    }

    /* public void display() {
        if (this.isEmpty()) {
            System.out.println("Queue is empty");
        }
        for (int i = 0; i < currentSize; i++) {
            queue[i + 1].show();
        }
    } */
}
