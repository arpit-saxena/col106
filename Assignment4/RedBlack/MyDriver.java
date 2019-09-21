package RedBlack;

import java.util.Queue;

import PriorityQueue.Student;
import RedBlack.RedBlackNode.Color;

import java.util.LinkedList;

public class MyDriver {
    public static void main(String[] args) {
        RBTree<Integer, Student> tree = new RBTree<>();
        tree.insert(30, null);
        tree.insert(40, null);
        tree.insert(50, null);
        tree.insert(20, null);
        tree.insert(10, null);
        tree.insert(15, null);
        print(tree);
    }

    public static <T extends Comparable<T>, E> void print(RBTree<T, E>  tree) {
        if (tree == null || tree.root == null) return;
        Queue<RedBlackNode<T, E>> queue = new LinkedList<>();
        queue.offer(tree.root);
        int numElements = 1;

        boolean oneNonNull = true;
        while (!queue.isEmpty() && oneNonNull){
            oneNonNull = false;
            for (int i = 0; i < numElements; i++) {
                RedBlackNode<T, E> node = queue.poll();
                if (node == null) {
                    System.out.print("_ ");
                } else {
                    System.out.print(node.key + (node.color == Color.BLACK ? "B " : "R "));
                }

                if (node == null) {
                    queue.offer(null);
                    queue.offer(null);
                } else {
                    queue.offer(node.left);
                    queue.offer(node.right);

                    if (node.left != null || node.right != null) {
                        oneNonNull = true;
                    }
                }
            }
            numElements *= 2;
            System.out.println();
        }
    }
}