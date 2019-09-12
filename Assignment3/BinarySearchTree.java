class NodeBST<K extends Comparable<K>, V> {
    K key;
    V value;
    NodeBST<K, V> left;
    NodeBST<K, V> right;

    NodeBST(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class BinarySearchTree<K extends Comparable<K>, V> {
    NodeBST<K, V> root;

    public BinarySearchTree() {
        this(null); // Initialize the root as null
        return;
    }

    public BinarySearchTree(NodeBST<K, V> root) {
        this.root = root;
    }

    public int insert(K key, V value) {
        int count = 0; // No node touched initially
        if (root == null) {
            root = new NodeBST<>(key, value);
        } else {
            NodeBST<K, V> curr = root;

            // break statement used internally to break out of the loop.
            // Since the structure is a tree, we are guaranteed to hit a leaf
            // where this loop would perform the insertion and exit
            while (true) {
                int comparisonResult = curr.key.compareTo(key);
                count++; // Touched curr
                if (comparisonResult > 0) { // curr.key > key
                    if (curr.left == null) {
                        curr.left = new NodeBST<>(key, value);
                        break;
                    }
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        curr.right = new NodeBST<>(key, value);
                        break;
                    }
                    curr = curr.right;
                }
            }
        }

        count++; // Would've created a new node and touched it
        return count;
    }

    public int update(K key, V value) {
        try {
            Pair<NodeBST<K, V>, Pair<Integer, String>> nodeInfo = addressInfo(key);
            nodeInfo.first.value = value;
            return nodeInfo.second.first;
        } catch (NotFoundException e) {
            return -1;
        }
    }

    public boolean contains(K key) {
        try {
            addressInfo(key);
        } catch (NotFoundException e) {
            return false;
        }
        return true;
    }

    public V get(K key) throws NotFoundException {
        return addressInfo(key).first.value;
    }

    public String address(K key) throws NotFoundException {
        return addressInfo(key).second.second;
    }

    // Helper function for all things related to finding a particular key
    // Returns the found node, the number of nodes touched and a string with
    // the steps (LR..)
    private Pair<NodeBST<K, V>, Pair<Integer, String>> addressInfo(K key) throws NotFoundException {
        int count = 0; // No node touched initially
        if (root == null) {
            throw new NotFoundException();
        }

        StringBuilder sb = new StringBuilder();
        NodeBST<K, V> curr = root;
        while (true) {
            int comparisonResult = curr.key.compareTo(key);
            count++; // Touched curr
            if (comparisonResult > 0) { // curr.key > key
                if (curr.left == null) throw new NotFoundException();

                curr = curr.left;
                sb.append('L');
            } else {
                if (curr.key.equals(key)) break; // Found!
                if (curr.right == null) throw new NotFoundException();

                curr = curr.right;
                sb.append('R');
            }
        }

        return new Pair<>(curr, new Pair<>(count, sb.toString()));
    }

    public int delete(K key) {
        int count = 0;
        if (root == null) return -1;

        // Finding the node to delete
        NodeBST<K, V> curr = root;
        NodeBST<K, V> parent = null; // parent = null <=> curr = root
        while (true) {
            int comparisonResult = key.compareTo(curr.key);
            count++; //touched curr
            if (comparisonResult < 0) { // key < curr.key
                if (curr.left == null) return -1;
                parent = curr;
                curr = curr.left;
            } else {
                if (key.equals(curr.key)) break;
                if (curr.right == null) return -1;
                parent = curr;
                curr = curr.right;
            }
        }

        // assert: curr.key equals key.
        // So we have to delete curr
        NodeBST<K, V> nodeToDelete = curr;

        if (nodeToDelete.right == null) {
            if (parent == null) { // => curr = nodeToDelete = root
                root = nodeToDelete.left;
            } else if (parent.right == nodeToDelete) {
                parent.right = nodeToDelete.left;
            } else if (parent.left == nodeToDelete) {
                parent.left = nodeToDelete.left;
            } else {
                System.err.println("Parent not the parent of nodeToDelete");
            }
        } else {
            // Finding successor of nodeToDelete. Using curr & parent for this
            parent = nodeToDelete;
            curr = nodeToDelete.right;
            count--; // Next loop counts touching curr again, which has already been
                     // counted, so decreasing it once
            while (true) {
                count++; // Will touch curr in the next if condition
                if (curr.left == null) break;
                parent = curr;
                curr = curr.left;
            }

            // Assert: curr.left == null => curr is the successor of nodeToDelete
            NodeBST<K, V> replacementNode = curr;
            NodeBST<K, V> replacementNodeParent = parent;
            nodeToDelete.key = replacementNode.key;
            nodeToDelete.value = replacementNode.value;
            count++;

            if (replacementNodeParent.right == replacementNode) {
                replacementNodeParent.right = replacementNode.right;
            } else if (replacementNodeParent.left == replacementNode) {
                replacementNodeParent.left = replacementNode.right;
            } else {
                System.err.println("curr is not a child of parent.. Unexpected!!");
            }

        }

        return count;
    }
}