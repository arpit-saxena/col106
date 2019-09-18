import java.util.LinkedList;
import java.util.ListIterator;

public class BTree {
    private int a;
    private int b;

    class SplitNode {
        Node node;
        int separator;
    }

    class Node {
        LinkedList<Integer> keys = new LinkedList<>();
        LinkedList<Node> children = new LinkedList<>();
        int numKeys = 0;

        public boolean isMoreThanFull() {
            return numKeys == a;
        }

        private void overFullCheck() {
            if (numKeys == a) {
                System.err.println("insert called on already overfull node.");
                System.exit(1);
            }
        }

        // Works if node is a leaf; else error
        public void insert(int key) {
            overFullCheck();

            ListIterator<Integer> iterator = keys.listIterator();

            while (iterator.hasNext()) {
                int val = iterator.next();
                if (key < val) {
                    iterator.add(key);
                    numKeys++;
                    return;
                }
            }

            iterator.add(key);
            numKeys++;
        }

        public void insertEnd(int key, Node child) {
            overFullCheck();
            keys.addLast(key);
            children.addLast(child);
            numKeys++;
        }

        public void insertFront(int key, Node child) {
            overFullCheck();
            keys.addFirst(key);
            children.addFirst(child);
            numKeys++;
        }

        public boolean isLeaf() {
            return numKeys == 0 || children.isEmpty();
        }

        // get a potential child where the key might be found
        public Node getChild(int key) {
            if (isLeaf()) return null;

            ListIterator<Integer> keyIterator = keys.listIterator();
            ListIterator<Node> childIterator = children.listIterator();
            while (keyIterator.hasNext()) {
                int currKey = keyIterator.next();
                Node currChild = childIterator.next();
                if (key < currKey) {
                    return currChild;
                }
            }
            return childIterator.next();
        }

        public SplitNode split() {
            ListIterator<Integer> keyIterator = keys.listIterator();
            ListIterator<Node> childIterator = children.listIterator();
            
            for (int i = 0; i < (numKeys - 1) / 2; i++) {
                keyIterator.next();
                childIterator.next();
            }
            childIterator.next();

            SplitNode ret = new SplitNode();
            ret.separator = keyIterator.next();
            ret.node = new Node();

            while (keyIterator.hasNext()) {
                ret.node.insertEnd(keyIterator.next(), childIterator.next());
                
            }

            Node newNode;
        }
    }

    Node root;

    public BTree(int a, int b) {
        if (2 * (a - 1) > b - 1) {
            throw new IllegalArgumentException("b must be atleast 2 * a - 1");
        }

        this.a = a;
        this.b = b;
    }

    public void insert(int key) {
        if (root == null) {
            root = new Node();
            root.insert(key);
            return;
        }

        Node curr = root;
        while (!curr.isLeaf()) {
            curr = curr.getChild(key);
        }

        curr.insert(key);
        if (curr.)
    }
}