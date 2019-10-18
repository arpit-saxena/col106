package Trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import Util.NodeInterface;

class NotFoundException extends Exception {}

public class TrieNode<T> implements NodeInterface<T> {
    static class InternalNode<T> {
        char c;
        TrieNode<T> node;

        InternalNode(char c, TrieNode<T> node) {
            this.c = c;
            this.node = node;
        }
    }

    List<InternalNode> children = new LinkedList<>();
    T value;

    @Override
    public T getValue() {
        return value;
    }

    /**
     * Inserting word: Put word.charAt(index) in appropriate child
     * Returns the node next to the node where the last character is inserted
     */
    public TrieNode<T> insert(String word, int index) {
        if (index >= word.length()) return this;

        char currChar = word.charAt(index);
        ListIterator<InternalNode> iterator = children.listIterator();
        while(iterator.hasNext()) {
            InternalNode node = iterator.next();
            if (node.c == currChar) {
                return node.node.insert(word, index + 1);
            } else if (node.c > currChar) {
                iterator.previous();
                TrieNode<T> newChild = new TrieNode<T>();
                iterator.add(new InternalNode(currChar, newChild));
                return newChild.insert(word, index + 1);
            }
        }

        // All elements in children are less than currChar
        TrieNode<T> newChild = new TrieNode<T>();
        iterator.add(new InternalNode(currChar, newChild));
        return newChild.insert(word, index + 1);
    }

    /**
     * Performs the action specified by consumer on all values stored in
     * the trie rooted at this node
     */
    public void forEachValue(Trie.Consumer<T> consumer) {
        if (value != null) {
            consumer.consume(value);
        }

        for (InternalNode node : children) {
            node.node.forEachValue(consumer);
        }
    }

    public void forEachString(Trie.Consumer<String> consumer, StringBuilder sb) {
        for (InternalNode node : children) {
            if (node.node.value != null) {
                consumer.consume(sb.toString());
            }
            sb.append(node.c);
            node.node.forEachString(consumer, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * Prints all strings stored in the trie rooted at this node
     */
    public void printAll() {
        forEachString(string -> System.out.println(string), 
            new StringBuilder());
    }

    /**
     * Prints all values stored in the trie rooted at this node
     */
    public void printAllValues() {
        forEachValue(value -> System.out.println(value));
    }

    /**
     * Finding prefix in children. Check if prefix.charAt(index) is a child
     * Returns the node next to the node that matched the last character of prefix. 
     * Null if no match
     */
    public TrieNode<T> findPrefixInChildren(String prefix, int index) {
        if (index >= prefix.length()) {
            return this;
        }

        char ch = prefix.charAt(index);
        for (InternalNode node : children) {
            if (node.c > ch) {
                return null; // Not found
            } else if (node.c == ch) {
                return node.node.findPrefixInChildren(prefix, index + 1);
            }
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
            ListIterator<InternalNode> iterator = children.listIterator();
            while (iterator.hasNext()) {
                InternalNode node = iterator.next();
                if (node.c > ch) throw new NotFoundException();
                if (node.c == ch) {
                    boolean ret = node.node.delete(word, index + 1);
                    if (ret) {
                        iterator.remove();
                    }
                    break;
                }
            }
        }

        return children.isEmpty();
    }
}