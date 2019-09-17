import java.util.LinkedList;

class Node {
    int key;
    Node left;
    Node right;

    public Node(int key) {
        this.key = key;
    }
}

public class BinaryTree {
    Node root = null;

    Node getRoot() {
        return root;
    }

    void insert(int key) {
        if (root == null) {
            root = new Node(key);
        } else {
            Node curr = root;
            while (true) {
                if (key < curr.key) {
                    if (curr.left == null) {
                        curr.left = new Node(key);
                        break;
                    }
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        curr.right = new Node(key);
                        break;
                    }
                    curr = curr.right;
                }
            }
        }
    }

    void delete(int key) {
        if (root == null) return;

        Node curr = root;
        while (curr.key != key) {
            if (key < curr.key) {
                if (curr.left == null) {
                    return;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    return;
                }
                curr = curr.right;
            }
        }

        Node nodeDelete = curr;
        if (nodeDelete.right == null) {
            if (nodeDelete.left != null) {
                nodeDelete.key = nodeDelete.left.key;
                nodeDelete.right = nodeDelete.left.right;
                nodeDelete.left = nodeDelete.left.left;
            }
        } else {
            Node parent = nodeDelete;
            curr = nodeDelete.right;
            while (curr.left != null) {
                parent = curr;
                curr = curr.left;
            }
            nodeDelete.key = curr.key;
            if (curr.right == null) {
                if (parent.left == curr) {
                    parent.left = null;
                } else if (parent.right == curr) {
                    parent.right = null;
                } else {
                    System.err.println("ERR");
                }
            } else {
                curr.key = curr.right.key;
                curr.left = curr.right.left;
                curr.right = curr.right.right;
            }
        }
    }

    private int getHeight() {
        return getHeight(getRoot());
    }

    private int getHeight(Node root) {
        if (root == null) return -1;

        int h1 = getHeight(root.left);
        int h2 = getHeight(root.right);
        return h1 >= h2 ? h1 + 1 : h2 + 1;
    }

    private int getNumDigits(int n) {
        if (n == 0) return 1;
        if (n < 0) n *= -1;
        int digits = 0;
        while (n != 0) {
            n /= 10;
            digits++;
        }
        return digits;
    }

    private int getMaxDigits() {
        return getMaxDigits(getRoot());
    }

    private int getMaxDigits(Node root) {
        if (root == null) return 0;
        int digits = getNumDigits(root.key);
        int d1 = getMaxDigits(root.left);
        int d2 = getMaxDigits(root.right);
        if (d2 > d1) d1 = d2;
        return digits > d1 ? digits : d1;
    }

    private void printMultipleTimes(char c, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        System.out.print(sb.toString());
    }

    private void printSpaces(int n) {
        printMultipleTimes(' ', n);
    }

    void printCenter(int a, int space) {
        int digits = getNumDigits(a);
        printSpaces((space - digits) / 2);
        System.out.print(a);
        printSpaces(space - (space - digits) / 2 - digits);
    }

    void printCenter(char a, int space) {
        printSpaces((space - 1) / 2);
        System.out.print(a);
        printSpaces(space - (space - 1) / 2 - 1);
    }

    void print() {
        int height = getHeight();
        int keySpace = getMaxDigits();
        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(getRoot());

        for(int h = 0; h <= height; h++) {
            int numNodes = 1 << h;
            int spaceOffset = keySpace * ((1 << (height - h)) - 1);
            for (int i = 0; i < numNodes; i++) {
                printSpaces(spaceOffset);
                Node node = queue.poll();
                if (node == null) {
                    /* printCenter('_', keySpace); */
                    printSpaces(keySpace);
                    queue.addLast(null);
                    queue.addLast(null);
                } else {
                    printCenter(node.key, keySpace);
                    queue.addLast(node.left);
                    queue.addLast(node.right);
                }
                printSpaces(spaceOffset);
                printSpaces(keySpace);
            }
            System.out.println();
        }
    }
}