package classes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class RWayTrie implements Trie {

    /**
     * Quantity of stored characters
     */
    private static final int R = 26; // ASCII

    /**
     * Root node of stored
     */
    private Node root;

    /**
     * Constructor, initialises root node
     */
    public RWayTrie() {
        root = new Node();
    }

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];

    }

    /**
     * Gets stored value by key
     * 
     * @param key
     * @return stored value
     */
    public Object get(String key) {
        if (key == null) {
            throw new NullPointerException("Trying to get access null key");
        }
        Node node = get(root, key, 0);
        if (node == null)
            return null;
        return node.value;
    }

    /**
     * Return node associated with key in the subtrie rooted at node.
     * 
     * @param node
     * @param key
     * @param index
     * @return
     */
    private Node get(Node node, String key, int index) {
        if (node == null) {
            return null;
        }
        if (index == key.length()) {
            return node;
        }
        char c = key.charAt(index); // Use dth key char to identify subtrie
        int intIndex = (int) c - 97;
        return get(node.next[intIndex], key, index + 1);
    }

    /**
     * Retrieves all words of the vocabulary
     */
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    private void collect(Node node, String pref, Queue<String> queue) {
        if (node == null) {
            return;
        }
        if (node.value != null) {
            queue.add(pref);
        }
        for (char c = 0; c < R; c++) {
            collect(node.next[c], pref + (char) (c + 97), queue);
        }
    }

    /**
     * Retrieves words from the vocabulary by prefix
     * 
     * @param pref
     */
    public Iterable<String> wordsWithPrefix(String pref) {
        return new Iterable<String>() {

            @Override
            public Iterator<String> iterator() {

                return new Iterator<String>() {

                    private Queue<Tuple<String, Node>> queue = new LinkedList<>();
                    private Node currentNode = get(root, pref, 0);

                    {
                        if (currentNode != null) {
                            queue.add(new Tuple<>(pref, currentNode));
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return !queue.isEmpty();
                    }

                    @Override
                    public String next() {
                        String currentPref;
                        Tuple<String, Node> currentTuple;
                        while (!queue.isEmpty()) {
                            currentTuple = queue.remove();
                            currentPref = currentTuple.getKey();
                            currentNode = currentTuple.getValue();

                            for (int i = 0; i < R; i++) {
                                Node next = currentNode.next[i];
                                if (next != null) {
                                    queue.add(new Tuple<String, RWayTrie.Node>(currentPref + (char) (i + 'a'), next));
                                }
                            }
                            if (currentNode.value != null) {
                                return currentPref;
                            }
                        }
                        throw new NoSuchElementException();

                    }
                };
            }

        };
    }

    /**
     * Retrieves size of the vocabulary
     */
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        int nodeCounter = 0;
        if (node.value != null) {
            nodeCounter++;
        }
        for (char c = 0; c < R; c++) {
            nodeCounter += size(node.next[c]);
        }
        return nodeCounter;
    }

    /**
     * Add tuple to the vocabulary
     */
    public void add(Tuple tuple) {
        root = add(root, (String) tuple.getKey(), tuple.getValue(), 0);

    }

    /**
     * Change value associated with key if in subtrie rooted at node.
     * 
     * @param node
     * @param key
     * @param value
     * @param index
     * @return
     */
    private Node add(Node node, String key, Object value, int index) {
        if (node == null) {
            node = new Node();
        }
        if (index == key.length()) {
            node.value = value;
            return node;
        }
        char c = key.charAt(index);
        int intIndex = (int) c - 97;
        node.next[intIndex] = add(node.next[intIndex], key, value, index + 1);
        return node;
    }

    /**
     * Checks whether vocabulary contains word
     */
    public boolean contains(String word) {
        return get(word) != null;

    }

    public boolean delete(String word) {
        if (!this.contains(word)) {
            return false;
        }
        int initialSize = size();
        root = delete(root, word, 0);
        int sizeAfterDeletion = size();
        return (initialSize - sizeAfterDeletion) == 1;
    }

    private Node delete(Node node, String word, int index) {
        if (node == null) {
            return null;
        }
        if (index == word.length()) {
            node.value = null;
        } else {
            char c = word.charAt(index);
            int intIndex = (int) c - 97;
            node.next[intIndex] = delete(node.next[intIndex], word, index + 1);
        }

        if (node.value != null) {
            return node;
        }
        for (char c = 0; c < R; c++) {
            if (node.next[c] != null)
                return node;
        }
        return null;
    }
}
