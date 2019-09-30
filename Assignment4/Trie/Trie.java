package Trie;

import java.util.LinkedList;
import java.util.Queue;

class Counter {
    int count = 0;
}

public class Trie<T> implements TrieInterface<T> {
    TrieNode<T> root = new TrieNode<T>('*');

    @Override
    public boolean delete(String word) {
        try {
            root.delete(word, 0);
        } catch (NotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public TrieNode search(String word) {
        TrieNode<T> node = root.findPrefixInChildren(word, 0);
        if (node == null || node.value == null) {
            return null;
        }
        return node;
    }

    @Override
    public TrieNode startsWith(String prefix) {
        return root.findPrefixInChildren(prefix, 0);
    }

    @Override
    public void printTrie(TrieNode trieNode) {
        trieNode.printAll();
    }

    @Override
    public boolean insert(String word, Object value) {
        TrieNode<T> endNode = root.insert(word, 0);
        endNode.value = (T) value;
        return true;
    }

    @Override
    public void printLevel(int level) {
        print(level);
    }

    @Override
    public void print() {
        print(-1);
    }

    /** 
     * Helper function to print a particular level
     * Prints all levels if argument is -1
    */
    public void print(int level) {

        // TODO: Verify what to do in this edge case
        if (root == null) {
            return;
        }

        if (level == -1) {
            System.out.println("-------------");
            System.out.println("Printing Trie");
        }

        Queue<TrieNode<T>> queue = new LinkedList<>();
        queue.offer(root);
        int currLevel = 0;
        int numElements = 1;

        while (numElements != 0) {
            int[] levelChars = new int[128];
            Counter newNumElements = new Counter();
            for (int i = 0; i < numElements; i++) {
                TrieNode<T> node = queue.poll();
                levelChars[node.c]++;
                node.children.forEach((key, value) -> {
                    queue.add(value);
                    newNumElements.count++;
                });
            }

            if (currLevel == level || level == -1 && currLevel != 0) {
                Queue<Character> printChars = new LinkedList<>();
                for (int i = 0; i < levelChars.length; i++) {
                    if (levelChars[i] != 0 && ((char) i) != ' ') {
                        for (int j = 0; j < levelChars[i]; j++) {
                            printChars.add((char) i);
                        }
                    }
                }

                System.out.print("Level " + currLevel + ": ");
                while (true) {
                    System.out.print(printChars.poll());
                    if (printChars.peek() != null) {
                        System.out.print(",");
                    } else {
                        break;
                    }
                }
                System.out.println();
            }

            numElements = newNumElements.count;
            if (currLevel == level) {
                break;
            }
            currLevel++;
        }

        // Print last empty level if all levels had to be printed
        if (level == -1) {
            System.out.println("Level " + currLevel + ": ");
            System.out.println("-------------");
        }
    }
}