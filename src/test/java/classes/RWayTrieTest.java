/**
 * 
 */
package classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;


public class RWayTrieTest {

    private Trie trie;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void fillTrie() throws Exception {

        trie = new RWayTrie();

    }
    
    private void fillRWayTrie() {
        trie.add(new Tuple<String, Integer>("abc", "abc".length()));
        trie.add(new Tuple<String, Integer>("abc", "abc".length()));
        trie.add(new Tuple<String, Integer>("abcd", "abcd".length()));
        trie.add(new Tuple<String, Integer>("abcd", "abcd".length()));
        trie.add(new Tuple<String, Integer>("abcdxs", "abcdxs".length()));
    }

    public void deleteNonContainedWord (){
        assertEquals(false, trie.delete("NonExistingPrefix"));
    }
    
    @Test(expected = NullPointerException.class)
    public void getByNullStrString(){
        RWayTrie rTrie = new RWayTrie();
        rTrie.get(null);
    }
    
    @Test
    public void addAndSizeTest() {
        fillRWayTrie();

        assertTrue(trie.size() == 3);
    }

    @Test
    public void trieMustContainWords() {
        fillRWayTrie();

        assertTrue(trie.contains("abc"));
        assertTrue(trie.contains("abcd"));
        assertTrue(trie.contains("abcdxs"));
    }

    @Test
    public void trieMustNotContainWords() {

        fillRWayTrie();

        assertTrue(!trie.contains("abcs"));

    }

    @Test
    public void trieMustReturnAllWords() {

        fillRWayTrie();

        Iterator<String> iterator = trie.words().iterator();

        assertEquals("abc", iterator.next());
        assertEquals("abcd", iterator.next());
        assertEquals("abcdxs", iterator.next());
    }

    @Test
    public void wordsWithPrefixTest() {

        fillRWayTrie();

        Iterator<String> iterator = trie.wordsWithPrefix("abcd").iterator();

        assertEquals("abcd", iterator.next());
        assertEquals("abcdxs", iterator.next());
    }

    @Test
    public void deleteWordMustNotContainWord() {

        fillRWayTrie();

        trie.delete("abcd");

        assertTrue(!trie.contains("abcd"));

    }

    @Test
    public void deleteWordMustDecreaseSize() {

        fillRWayTrie();

        trie.delete("abcd");

        assertTrue(trie.size() == 2);

    }

}
