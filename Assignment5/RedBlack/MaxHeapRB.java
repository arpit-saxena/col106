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
public class MaxHeapRB<T extends Comparable> {
    static int creationTime = 0;
    int size = 0;

    public int size() {
        return size;
    }

    class Node implements Comparable<Node> {
        T val;
        private int time;

        Node(T val) {
            this.val = val;
            time = creationTime;
            creationTime++;
            size++;
        }

        public int compareTo(Node node) {
            int valCompare = val.compareTo(node.val);
            if (valCompare != 0) return valCompare;
            return -(time - node.time);
        }
    }

    private RBTree<Node, Object> rbt = new RBTree<>();

    public void insert(T element) {
        rbt.insert(new Node(element), null);
    }

    /**
     * @param k the number of largest elements to return
     * @return An ArrayList with k largest elements, in descending order
     */
    public ArrayList<T> topNumElements(int k) {
        ArrayList<T> top = new ArrayList<>();
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
    public ArrayList<T> elementsGreaterThanEqualTo(Comparator<T> comparator) {
        ArrayList<T> ret = new ArrayList<>();
        if (rbt.root == null) return ret;
        RedBlackNode<Node, Object> curr = rbt.root;
        while (curr != null) {
            int comparisonResult = comparator.compareTo(curr.key.val);

            // val <= curr.key => Have to add whole right subtree
            if (comparisonResult >= 0 && curr.right != null) {
                topElementsHelper(ret, -1, curr.right);
                ret.add(curr.key.val);
                curr = curr.left;
            } else { // val > curr.key => Greater than whole left subtree. Go right
                curr = curr.right;
            }
        }
        return ret;
    }

    /**
     * Helper to insert k largest elements into the ArrayList top of all
     * elements in the tree rooted at node, in descending order.
     * All elements in descending order are added if k == -1
     */
    private void topElementsHelper(ArrayList<T> top, int k, 
                                    RedBlackNode<Node, Object> node
    ){
        if (k != -1 && top.size() >= k) return;
        if (node.right != null) topElementsHelper(top, k, node.right);
        if (k != -1 && top.size() >= k) return;
        top.add(node.key.val);
        if (node.left != null) topElementsHelper(top, k, node.left);
    }
}