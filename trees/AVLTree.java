import java.util.LinkedList;

class AVLNode {
    int key;
    int height;
    AVLNode left;
    AVLNode right;
    AVLNode parent;

    public AVLNode(int key) {
        this.key = key;
        this.height = 0;
    }

    public boolean isImbalanced() {
        int balanceFactor = balanceFactor();
        return balanceFactor > 1 || balanceFactor < -1;
    }

    public int balanceFactor() {
        int leftHeight, rightHeight;
        if (left == null) {
            leftHeight = -1;
        } else {
            leftHeight = left.height;
        }

        if (right == null) {
            rightHeight = -1;
        } else {
            rightHeight = right.height;
        }

        return rightHeight - leftHeight;
    }

    public void updateHeight() {
        int leftHeight, rightHeight;
        if (left == null) {
            leftHeight = -1;
        } else {
            leftHeight = left.height;
        }

        if (right == null) {
            rightHeight = -1;
        } else {
            rightHeight = right.height;
        }

        height = (leftHeight > rightHeight ? leftHeight : rightHeight) + 1;
    }
}

public class AVLTree {
    AVLNode root = null;

    private void basicInsert(AVLNode node) {
        if (root == null) {
            root = node;
        } else {
            AVLNode curr = root;
            while (true) {
                if (node.key < curr.key) {
                    if (curr.left == null) {
                        curr.left = node;
                        node.parent = curr;
                        break;
                    } 
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        curr.right = node;
                        node.parent = curr;
                        break;
                    }
                    curr = curr.right;
                }
            }
        }
    }

    // Rotate the subtree rooted at node
    private void rotate(AVLNode node) {
        AVLNode low, mid, high;
        AVLNode c1, c2, c3, c4;

        if (node.balanceFactor() < 0) {
            high = node;
            c4 = node.right;
            if (node.left.balanceFactor() <= 0) {
                mid = node.left;
                low = mid.left;
                c3 = mid.right;
                c1 = low.left;
                c2 = low.right;
            } else {
                low = node.left;
                mid = low.right;
                c1 = low.left;
                c2 = mid.left;
                c3 = mid.right;
            }
        } else {
            low = node;
            c1 = node.left;
            if (node.right.balanceFactor() < 0) {
                high = node.right;
                mid = high.left;
                c2 = mid.left;
                c3 = mid.right;
                c4 = high.right;
            } else {
                mid = node.right;
                high = mid.right;
                c2 = mid.left;
                c3 = high.left;
                c4 = high.right;
            }
        }

        mid.parent = node.parent;
        if (mid.parent != null) {
            if (mid.parent.left == node) {
                mid.parent.left = mid;
            } else {
                mid.parent.right = mid;
            }
        } else { // node is root
            root = mid;
        }

        mid.left = low;
        mid.right = high;
        low.parent = mid;
        high.parent = mid;

        low.left = c1;
        if (c1 != null) c1.parent = low;
        low.right = c2;
        if (c2 != null) c2.parent = low;

        high.left = c3;
        if (c3 != null) c3.parent = high;
        high.right = c4;
        if (c4 != null) c4.parent = high;

        low.updateHeight();
        high.updateHeight();
        mid.updateHeight();
    }

    void insert(int key) {
       AVLNode node = new AVLNode(key);
       basicInsert(node);

       AVLNode curr = node;
       do {
            if (curr == root) {
                return; // No imbalance was caused
            }
            curr = curr.parent;
            curr.updateHeight();
        } while (!curr.isImbalanced());

        rotate(curr);
    }

    // Returns first node of potential imbalance.
    private AVLNode basicDelete(int key) {
        if (root == null) return null;

        AVLNode curr = root;
        while (true) {
            if (curr.key == key) break;
            if (key < curr.key) {
                if (curr.left == null) return null;
                curr = curr.left;
            } else {
                if (curr.right == null) return null;
                curr = curr.right;
            }
        }

        AVLNode nodeDelete = curr;
        if (nodeDelete.right == null && nodeDelete.left == null) {
            if (nodeDelete.parent == null) {
                root = null;
                return null;
            }

            if (nodeDelete.parent.left == nodeDelete) {
                nodeDelete.parent.left = null;
            } else {
                nodeDelete.parent.right = null;
            }
            return nodeDelete.parent;
        }
        
        if (nodeDelete.right == null) { // nodeDelete.left != null
            nodeDelete.key = nodeDelete.left.key;
            nodeDelete.left = nodeDelete.left.left;
            nodeDelete.right = nodeDelete.left.right;
            return nodeDelete.parent;
        }
        
        curr = nodeDelete.right;
        while (curr.left != null) {
            curr = curr.left;
        }

        if (curr.parent.left == curr) {
            curr.parent.left = curr.right;
        } else {
            curr.parent.right = curr.right;
        }

        nodeDelete.key = curr.key;
        return curr.parent;
    }

    void delete(int key) {
        AVLNode node = basicDelete(key);
        while (node != null) {
            node.updateHeight();
            if (node.isImbalanced()) {
                rotate(node);
            }
            node = node.parent;
        }
    }

    private int getHeight() {
        return getHeight(root);
    }

    private int getHeight(AVLNode root) {
        if (root == null) return -1;

        int h1 = getHeight(root.left);
        int h2 = getHeight(root.right);
        return h1 >= h2 ? h1 + 1 : h2 + 1;
    }

    private int getNumDigits(int n) {
        if (n == 0) return 1;
        int digits = 0;
        if (n < 0) {
            n *= -1;
            digits++;
        }
        while (n != 0) {
            n /= 10;
            digits++;
        }
        return digits;
    }

    private int getMaxDigits() {
        return getMaxDigits(root);
    }

    private int getMaxDigits(AVLNode root) {
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
        LinkedList<AVLNode> queue = new LinkedList<>();
        queue.addLast(root);

        for(int h = 0; h <= height; h++) {
            int numNodes = 1 << h;
            int spaceOffset = keySpace * ((1 << (height - h)) - 1);
            for (int i = 0; i < numNodes; i++) {
                printSpaces(spaceOffset);
                AVLNode node = queue.poll();
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