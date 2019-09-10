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

        if (root == null) {
            return -1; // Not found
        }

        NodeBST<K, V> curr = root;
        NodeBST<K, V> parent = null; // parent = null <=> curr = root
        while (true) {
            int comparisonResult = curr.key.compareTo(key);
            count++; // Touched curr
            if (comparisonResult > 0) { // curr.key > key
                if (curr.left == null) return -1;
                parent = curr;
                curr = curr.left;
            } else {
                if (curr.key.equals(key)) break; // Found!
                if (curr.right == null) return -1;
                parent = curr;
                curr = curr.right;
            }
        }
        
        NodeBST<K, V> nodeToDelete = curr;

        if (nodeToDelete.left == null && nodeToDelete.right == null) { // Have to delete a leaf node
            if (parent == null) { // => Have to delete root
                root = null;
            } else if (parent.left == nodeToDelete) {
                parent.left = null;
            } else if (parent.right == nodeToDelete) {
                parent.right = null;
            } else {
                System.err.println("parent of nodeToDelete does not have it as child");
            }
            return count;
        }

        NodeBST<K, V> replacementNode; // The node that will take the place of deleted node
        if (nodeToDelete.right == null) {
            replacementNode = nodeToDelete.left;
        } else { // nodeToDelete.right != null
            // Find the leftmost node of right subtree of nodeToDelete
            
            parent = nodeToDelete; // parent of curr
            curr = nodeToDelete.right;
            while (true) {
                count++; // Will touch curr in the next if condition
                if (curr.left == null) break;
                parent = curr;
                curr = curr.left;
            }

            replacementNode = curr;

            if (parent == nodeToDelete) { // parent's right child is to be deleted
                parent.right = replacementNode.right;
            } else { // parent's left child is to be deleted
                parent.left = replacementNode.right;
            }
        }

        nodeToDelete.value = replacementNode.value;
        nodeToDelete.key = replacementNode.key;
        return count;
    }
}