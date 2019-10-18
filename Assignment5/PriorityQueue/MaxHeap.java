package PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
    ArrayList<Node> list = new ArrayList<>();
    private static int creationCounter = 0;

    public static interface Consumer<T> {
        void consume(T val);
    }

    public static interface Comparator<T> {
        int compareTo(T val);
    }

    /**
     * Internal representation of an element
     * Used to implement the FIFO property of two elements with same keys
     * Along with keys, an insertionTime is also kept which indicates when
     * a key was inserted.
     */
    public class Node implements Comparable<Node> {
        public T key;
        private int insertionTime;
        public int index; // index in list where this is inserted. -1 if not inserted

        public Node(T key) {
            this.key = key;
            insertionTime = creationCounter;
            creationCounter++;
        }

        public int compareTo(Node node) {
            int keyCompare = key.compareTo(node.key);
            if (keyCompare != 0) {
                return keyCompare;
            }
            return -(insertionTime - node.insertionTime);
        }
    }

    @Override
    public void insert(T element) {
        Node node = new Node(element);
        insert(node);
    }

    public void insert(List<Node> elements) {
        // Deciding which method to use
        boolean useHeapify = false; // useHeapify <=> add all elements to list 
                                    //a               nd make heap
        int n = list.size();
        int k = elements.size();

        if (k == 0) return;
        int factor = 5;

        if (
            1 + n / k > 20
            || 1 << (1 + n / k) > factor * (n + k)
        ){
            useHeapify = false;
        } else {
            useHeapify = true;
        }

        if (useHeapify) {
            list.addAll(elements);
            heapify();
        } else {
            for (Node node : elements) {
                insert(node);
            }
        }
    }

    /**
     * Constructs the heap from the internal list
     */
    public void heapify() {
        int lastIndex = list.size() - 1;
        if (lastIndex == 0) return;
        int i = lastIndex;
        while (i > (lastIndex - 1) / 2) {
            list.get(i).index = i;
            i--;
        }
        while (i >= 0) {
            list.get(i).index = i;
            bubbleDown(i);
            i--;
        }
    }

    /**
     * Insert an element into the max heap and return the final node
     * in the underlying array in which it is finally inserted
     */
    public Node insertAndGetNode(T element) {
        Node node = new Node(element);
        insert(node);
        return node;
    }

    /**
     * Insert a node into the MaxHeap
     * @param node the node to insert
     * @return the final index in the underlying array where it is inserted
     */
    public int insert(Node node) {
        int index = list.size();
        list.add(node);
        node.index = index;
        return bubbleUp(index);
    }

    @Override
    public T extractMax() {
        Node node = extractMaxNode();
        return node != null ? node.key : null;
    }

    public Node extractMaxNode() {
        if (list.size() == 0) return null;

        Node ret = list.get(0);
        ret.index = -1;
        Node last = list.remove(list.size() - 1);

        // If size of list is 0, nothing to do
        if (list.size() == 0) {
            return ret;
        }

        // Now list.size() >= 1
        list.set(0, last);
        last.index = 0;
        bubbleDown(0);
        return ret;
    }

    public int size() {
        return list.size();
    }

    public T peekMax() {
        if (list.size() == 0) return null;
        return list.get(0).key;
    }

    /**
     * Bubble up the node at index
     * @return The new index of that node
     */
    public int bubbleUp(int index) {
        Node node = list.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            Node parent = list.get(parentIndex);
            int comparisonResult = parent.compareTo(node);

            // parent >= node => Heap is now made
            if (comparisonResult >= 0) break;

            // Swap node with its parents and continue
            list.set(parentIndex, node);
            node.index = parentIndex;
            list.set(index, parent);
            parent.index = index;

            index = parentIndex;
        }
        return index;
    }

    /**
     * Bubble down the node at index
     * @return the new index of that node
     */
    public int bubbleDown(int index) {
        Node node = list.get(index);

        // Loop keeps running till the current node has a child with which we 
        // might to potentially swap the node value with
        while (2 * index + 1 < list.size()) {
            int child1Index = 2 * index + 1,
                child2Index = 2 * index + 2;

            int replacementIndex; // index with which to swap current element with

            if (child2Index < list.size()) {
                Node child1 = list.get(child1Index),
                     child2 = list.get(child2Index);
                    
                if (child1.compareTo(child2) > 0) {
                    replacementIndex = child1Index;
                } else {
                    replacementIndex = child2Index;
                }
            } else {
                replacementIndex = child1Index;
            }

            Node replacementNode = list.get(replacementIndex);
            int comparisonResult = replacementNode.compareTo(node);

            // replacementNode < node => The parent greater than both children => done
            if (comparisonResult < 0) break;

            list.set(index, replacementNode);
            replacementNode.index = index;
            list.set(replacementIndex, node);
            node.index = replacementIndex;

            index = replacementIndex;
        }

        return index;
    }

    public void forEach(Consumer<T> consumer) {
        list.forEach(node -> {
           consumer.consume(node.key);
        });
    }

    public void forEachNode(Consumer<Node> consumer) {
        list.forEach(node -> {
            consumer.consume(node);
        });
    }

    public void clear() {
        list.clear();
    }

    public ArrayList<T> elementsGreaterThanEqualTo(Comparator<T> comparator) {
        ArrayList<T> ret = new ArrayList<>(list.size() + 1);
        if (list.size() == 0) return ret;
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(0);
        int numElementsCurrentLevel = 1;
        while (numElementsCurrentLevel > 0) {
            int newLevel = 0;
            ArrayList<Integer> newIndices = new ArrayList<>();
            for (int i = 0; i < numElementsCurrentLevel; i++) {
                int index = indices.get(i);
                Node node = list.get(index);
                if (comparator.compareTo(node.key) >= 0) {
                    ret.add(node.key);
                    if (2 * index + 1 < list.size()) {
                        newIndices.add(2 * index + 1);
                        newLevel++;
                    }
                    if (2 * index + 2 < list.size()) {
                        newIndices.add(2 * index + 2);
                        newLevel++;
                    }
                }
            }
            indices = newIndices;
            numElementsCurrentLevel = newLevel;
        }
        return ret;
    }

    private void greaterThanHelper(ArrayList<T> ret, Comparator<T> comparator
                                        , int index
    ){
        Node node = null;
        try {
            node = list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        if (node == null) return;
        int comparisonResult = comparator.compareTo(node.key);

        // thisValue >= value supplied
        if (comparisonResult >= 0) {
            ret.add(node.key);
            greaterThanHelper(ret, comparator, 2 * index + 1);
            greaterThanHelper(ret, comparator, 2 * index + 2);
        }
    }
}