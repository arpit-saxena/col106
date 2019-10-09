package Trie;

import java.util.HashMap;

import Util.NodeInterface;

class NotFoundException extends Exception {}

public class TrieNode<T> implements NodeInterface<T> {
    char c;
    HashMap<Character, TrieNode<T>> children;
    T value;

    TrieNode(char c) {
        this.c = c;
        children = new HashMap<>(128); // For 128 ascii characters
    }

    @Override
    public T getValue() {
        return value;
    }

    /**
     * Inserting word: Put word.charAt(index) in appropriate child
     * Returns the node where the last character is inserted
    */
    public TrieNode<T> insert(String word, int index) {
        if (index >= word.length()) {
            return this;
        }

        char ch = word.charAt(index);
        TrieNode<T> child = children.get(ch);
        if (child == null) {
            child = new TrieNode<>(ch);
            children.put(ch, child);
        }
        return child.insert(word, index + 1);
    }

    /**
     * Prints all strings stored in the trie rooted at this node
     */
    public void printAll() {
        if (value != null) {
            System.out.println(value.toString());
        }

        TrieNode[] arr = new TrieNode[128];
        children.forEach((ch, value) -> {
            arr[ch] = value;
        });

        for (TrieNode t : arr) {
            if (t != null) {
                t.printAll();
            }
        }
    }

    /**
     * Finding prefix in children. Check if prefix.charAt(index) is a child
     * Returns the node that matched the last character of prefix. Null if no match
     */
    public TrieNode<T> findPrefixInChildren(String prefix, int index) {
        if (index >= prefix.length()) {
            return this;
        }

        char ch = prefix.charAt(index);
        TrieNode<T> child = children.get(ch);
        if (child != null) {
            return child.findPrefixInChildren(prefix, index + 1);
        }

        return null;
    }

    /**
     * Deletes word from children of this node
     * Checks for word.charAt(index) in the children and proceeds
     * Returns true if the current node now has no children, false otherwise
     */
    public boolean delete(String word, int index) throws NotFoundException {
        if (index < word.length()) {
            char ch = word.charAt(index);
            TrieNode<T> child = children.get(ch);
            if (child == null) throw new NotFoundException();

            boolean ret = child.delete(word, index + 1);
            if (ret) {
                children.remove(ch);
            }
        }

        return children.isEmpty();
    }
}