package classes;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class PrefixMatcher {

    private Trie trie;

    /**
     * Constructor
     */
    public PrefixMatcher() {
        trie = new RWayTrie();
    }


    public int add(String... strings) {
        // [a-zA-Z]{3,} - regex for matching ;-)
        for (String str : strings) {
            String[] splitedStr = str.trim().split("\\s");
            for (String splStr : splitedStr) {
                addString(splStr);
            }
        }
        return trie.size();
    }

    /**
     * Inserts words started only with letter with at least 3 symbols length.
     * Convert all letters of the words to lower-case letters.
     * 
     * @param str
     *            input word
     */
    private void addString(String str) {
        if (!str.isEmpty() && str.charAt(0) > 64 && str.length() > 2) {
            trie.add(new Tuple(str.toLowerCase(), str.length()));
        }
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }


    public boolean delete(String word) {
        return trie.delete(word);
    }


    public int size() {
        return trie.size();
    }



    public Iterable<String> wordsWithPrefix(String pref, int k) {
        return getWordsByPrefAndLength(pref, k);
    }

    /**
     * Get words out from trie by pref and specified index k
     * 
     * @param pref- prefix of the words
     * @param k = k.length() + pref.length() of the words
     * @return LinkedList<Strings>
     */

    private Iterable<String> getWordsByPrefAndLength(String pref, int k) {
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new PrefixMatcherIterator(trie.wordsWithPrefix(pref).iterator(), k);
            }
        };
    }

    class PrefixMatcherIterator implements Iterator<String> {

        private Iterator<String> trieIterator;
        private int countDown;
        private String currentWord;
        private int currentWeight;
        private boolean hasMore;

        public PrefixMatcherIterator(Iterator<String> trieIterator, int contDown) {
            super();
            this.trieIterator = trieIterator;
            this.countDown = contDown;
            currentWord = trieIterator.next();
            currentWeight = currentWord.length();
            hasMore = true;
        }

        @Override
        public boolean hasNext() {
            return hasMore;
        }

        @Override
        public String next() {
            if (!hasMore) {
                throw new NoSuchElementException();
            }
            String result = currentWord;
            if (trieIterator.hasNext()) {
                currentWord = trieIterator.next();
                int weight = currentWord.length();
                if (currentWeight < weight) {
                    currentWeight = weight;
                    countDown--;
                }
                if (countDown < 0) {
                    hasMore = false;
                }
            } else {
                hasMore = false;
            }

            return result;
        }

    }


    public Iterable<String> wordsWithPrefix(String pref) {
        return getWordsByPrefAndLength(pref, 3);
    }
}
