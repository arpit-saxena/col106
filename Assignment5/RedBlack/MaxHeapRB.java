package RedBlack;

import java.util.ArrayList;

import PriorityQueue.PriorityQueueInterface;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;

/**
 * Heap with only insertions allowed.
 * Allows efficient looking up of k top elements without affecting the heap
 * in O(k + log n) time, where n elements are there in the heap
 * @param <T>
 */
public class MaxHeapRB<T extends Comparable, E> {
    static int creationTime = 0;
    int size = 0;

    public int size() {
        return size;
    }

    public static interface Consumer<E> {
        public void consume(E val);
    }

    private RBTree<T, E> rbt = new RBTree<>();

    public void insert(T key, E val) {
        rbt.insert(key, val);
    }

    /**
     * @param k the number of largest elements to return
     * @return An ArrayList with k largest elements, in descending order
     */
    public ArrayList<E> topNumElements(int k) {
        ArrayList<E> top = new ArrayList<>();
        if (rbt.root == null) return top;
        topElementsHelper(top, k, rbt.root);
        return top;
    }

    public interface Comparator<T> {
        int compareTo(T va);
    }

    /**
     * Returns ArrayList of all elements greater than or equal to the value
     * given, sorted in descending order. The comparator must define a total order
     * @param comparator All values are added for which comparator returns >= 0
     * @return
     */
    public ArrayList<E> elementsGreaterThanEqualTo(Comparator<T> comparator) {
        ArrayList<E> ret = new ArrayList<>(rbt.size + 1);
        if (rbt.root == null) return ret;
        RedBlackNode<T, E> curr = rbt.root;
        while (curr != null) {
            int comparisonResult = comparator.compareTo(curr.key);

            // val <= curr.key => Have to add whole right subtree
            if (comparisonResult >= 0 && curr.right != null) {
                topElementsHelper(ret, -1, curr.right);
                ret.addAll(curr.getValues());
                curr = curr.left;
            } else { // val > curr.key => Greater than whole left subtree. Go right
                curr = curr.right;
            }
        }
        return ret;
    }

    /**
     * Helper to insert k largest values into the ArrayList top of all
     * key-value pairs in the tree rooted at node, in descending order (of keys).
     * All values in descending order are added if k == -1
     */
    private void topElementsHelper(ArrayList<E> top, int k, 
                                    RedBlackNode<T, E> node
    ){
        if (k != -1 && top.size() >= k) return;
        if (node.right != null) topElementsHelper(top, k, node.right);
        if (k != -1 && top.size() >= k) return;
        top.addAll(node.getValues());
        if (node.left != null) topElementsHelper(top, k, node.left);
    }

    public void forEach(Consumer<E> consumer) {
        inOrder(consumer, rbt.root);
    }

    /**
     * Performs in-order traversal of the RBTree rooted at node, performing
     * the function consumer.consume() at each value stored in the tree
     */
    private void inOrder(Consumer<E> consumer, RedBlackNode<T, E> node) {
        if (node == null) return;
        inOrder(consumer, node.left);
        node.getValues().forEach(val -> {
            consumer.consume(val);
        });
        inOrder(consumer, node.right);
    }
}