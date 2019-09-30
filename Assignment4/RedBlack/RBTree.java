package RedBlack;

import java.util.LinkedList;
import java.util.List;

import RedBlack.RedBlackNode.Color;

public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {
    RedBlackNode<T, E> root;

    @Override
    public void insert(T key, E value) {
        if (root == null) {
            root = new RedBlackNode<>(key, value);
            root.color = Color.BLACK;
            return;
        }

        RedBlackNode<T, E> problemNode = root.insert(key, value);
        problemNode.fixUp(this);
    }

    @Override
    public RedBlackNode<T, E> search(T key) {
        RedBlackNode<T, E> curr = root;
        while (curr != null) {
            int comparisonResult = key.compareTo(curr.key);
            
            if (comparisonResult == 0) {
                return curr;
            } else if (comparisonResult > 0) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        return RedBlackNode.nullNode; // Not found
    }

    public List<RedBlackNode<T, E>> allNodes() {
        List<RedBlackNode<T, E>> list = new LinkedList<>();
        if (root == null) return list;
        root.addAllNodes(list);
        return list;
    }
}