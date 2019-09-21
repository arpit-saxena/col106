package PriorityQueue;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
    ArrayList<Node> list = new ArrayList<>();
    private static int creationCounter = 0;

    /**
     * Internal representation of an element
     * Used to implement the FIFO property of two elements with same keys
     * Along with keys, an insertionTime is also kept which indicates when
     * a key was inserted.
     */
    class Node implements Comparable<Node> {
        T key;
        private int insertionTime;

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

    private void insert(Node node) {
        int index = list.size();
        list.add(node);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            Node parent = list.get(parentIndex);
            int comparisonResult = parent.compareTo(node);

            // parent >= node => Heap is now made
            if (comparisonResult >= 0) break;

            // Swap node with its parents and continue
            list.set(parentIndex, node);
            list.set(index, parent);
            index = parentIndex;
        }
    }

    @Override
    public T extractMax() {
        if (list.size() == 0) return null;

        T ret = list.get(0).key;
        Node last = list.remove(list.size() - 1);

        // If size of list is 0, nothing to do
        if (list.size() == 0) {
            return ret;
        }

        // Now list.size() >= 1
        list.set(0, last);
        int index = 0;

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
            int comparisonResult = replacementNode.compareTo(last);

            // replacementNode < last => The parent greater than both children => done
            if (comparisonResult < 0) break;

            list.set(index, replacementNode);
            list.set(replacementIndex, last);
            index = replacementIndex;
        }

        return ret;
    }

}