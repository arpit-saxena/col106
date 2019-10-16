package RedBlack;

import Util.RBNodeInterface;

import java.util.LinkedList;
import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
    enum Color {
        RED,
        BLACK
    };

    Color color = Color.RED;
    T key;
    List<E> values;
    RedBlackNode<T, E> parent;
    RedBlackNode<T, E> left;
    RedBlackNode<T, E> right;

    // Workaround for the weird condition to return node with null key and values
    // for indicating search failure
    private RedBlackNode() {}
    public static RedBlackNode nullNode = new RedBlackNode();

    RedBlackNode(T key, E value) {
        this.key = key;
        values = new LinkedList<>();
        values.add(value);
    }

    public void addValue(E value) {
        values.add(value);
    }

    @Override
    public E getValue() {
        return null;
    }

    @Override
    public List<E> getValues() {
        return values;
    }

    /**
     * Insert a key value pair into the children
     * Returns the node where the key-value pair was finally inserted if a new
     * node was created. Otherwise null is returned to indicate there is no possible
     * problems with the colouring
     */
    public RedBlackNode<T, E> insert(T key, E value) {
        int comparisonResult = key.compareTo(this.key);

        // Key already exists, insert here and exit. return null since no structure
        // changes, so no possible violations of red black properties
        if (comparisonResult == 0) {
            values.add(value);
            return null;
        }

        if (comparisonResult < 0) { // key < this.key
            if (left == null) {
                left = new RedBlackNode<>(key, value);
                left.parent = this;
                return left;
            }
            return left.insert(key, value);
        }

        if (right == null) {
            right = new RedBlackNode<>(key, value);
            right.parent = this;
            return right;
        }

        return right.insert(key, value);
    }

    /**
     * Fixes the violation of the property that a red node may not
     * have a red child i.e. fixes the problem if this node is red and
     * so is its parent. Also fixes the property, if violated, that the
     * root is always black.
     * Assumes the only potential violations of these properties are for
     * this node and its parent
     * Since fixups might involve changing the root of tree, the tree reference
     * is needed by this function
     */
    public void fixUp(RBTree<T, E> tree) {
        if (this.color == Color.BLACK) return;
        
        if (this.parent == null) { // => this is the root
            this.color = Color.BLACK;
            return;
        }
        
        if (this.parent.color == Color.BLACK) return;

        // Since parent is red and no violation occurs above the parent,
        // it's parent has to exist and has to have been coloured black
        RedBlackNode<T, E> grandParent = this.parent.parent;

        RedBlackNode<T, E> uncle = grandParent.left == this.parent
                                        ? grandParent.right
                                        : grandParent.left;
        
        // If the current insertion signifies a misformed 4-node
        if (uncle == null || uncle.color == Color.BLACK) { 
            this.rotate(tree);
        } else { // uncle is red. Signifies addition to a 4-node.
            parent.color = Color.BLACK;
            uncle.color = Color.BLACK;
            grandParent.color = Color.RED;
            grandParent.fixUp(tree);
        }
    }

    /**
     * Rotates the chain of this node, its parent and its grandparent
     * Colours such that the new parent is black, and both children are red
     */
    public void rotate(RBTree<T, E> tree) {
        if (parent == null || parent.parent == null) {
            return;
        }

        RedBlackNode<T, E> curr = this,
            parent = curr.parent,
            grandParent = parent.parent;

        if (grandParent.left == parent) {
            if (parent.right == curr) {
                RedBlackNode<T, E> currLeft = curr.left;
                curr.left = parent;
                curr.parent = parent.parent;
                parent.parent.replaceChild(parent, curr);
                parent.parent = curr;
                parent.right = currLeft;
                if (currLeft != null) currLeft.parent = parent;
                
                RedBlackNode<T, E> temp = parent;
                parent = curr;
                curr = temp;
            }

            // Now grandfather -> parent -> curr chain is left-left
            RedBlackNode<T, E> parentRight = parent.right;
            parent.right = grandParent;
            if (grandParent.parent == null) { // => grandParent is root of tree
                tree.root = parent;
            } else {
                grandParent.parent.replaceChild(grandParent, parent);
            }
            parent.parent = grandParent.parent;
            grandParent.parent = parent;
            grandParent.left = parentRight;
            if (parentRight != null) parentRight.parent = grandParent;
        } else { // grandParent.right == parent
            if (parent.left == curr) {
                RedBlackNode<T, E> currRight = curr.right;
                curr.right = parent;
                curr.parent = parent.parent;
                parent.parent.replaceChild(parent, curr);
                parent.parent = curr;
                parent.left = currRight;
                if (currRight != null) currRight.parent = parent;

                RedBlackNode<T, E> temp = parent;
                parent = curr;
                curr = temp;
            }

            // Now grandfather -> parent -> curr chain is right-right
            RedBlackNode<T, E> parentLeft = parent.left;
            parent.left = grandParent;
            if (grandParent.parent == null) { // => grandParent = root
                tree.root = parent;
            } else {
                grandParent.parent.replaceChild(grandParent, parent);
            }
            parent.parent = grandParent.parent;
            grandParent.parent = parent;
            grandParent.right = parentLeft;
            if (parentLeft != null) parentLeft.parent = grandParent;
        }

        parent.color = Color.BLACK;
        parent.left.color = Color.RED;
        parent.right.color = Color.RED;
    }

    public void replaceChild(RedBlackNode<T, E> child, RedBlackNode<T, E> replacement) {
        if (left == child) {
            left = replacement;
        } else if (right == child) {
            right = replacement;
        }
    }
}
