/**
 * 
 */
package classes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PrefixMatcherTest {

    @Mock
    private Trie mockTrie;

    @InjectMocks
    private PrefixMatcher prefMatcherInj;

    private PrefixMatcher prefMatcher;

    /**
     * @throws java.lang.Exception
     */

    @Before
    public void setUp() throws Exception {
        fillMockPrefMatcher();

        fillPrefMatcherForJunit();

    }

    private void fillPrefMatcherForJunit() {
        // initializing prefMatcher for Junit testing
        prefMatcher = new PrefixMatcher();
        String[] strMass = { "abb", "abbc" };
        prefMatcher.add("a ab abc abc abcdx abcdxs  asdasdawdsd	");
        prefMatcher.add("abcds", "abcdw");
        prefMatcher.add(strMass);
    }

    private void fillMockPrefMatcher() {
        // initializing prefMatcher for Mock testing
        prefMatcherInj.add("a ab abc abc abcdx abcdxs  asdasdawdsd	");
        prefMatcherInj.add("abcds", "abcdw");
        String[] strMass = { "abb", "abbc" };
        prefMatcherInj.add(strMass);

    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorThrowsNoSuchElemExsTest() {
        
        Iterator<String> iterator = prefMatcher.wordsWithPrefix("abcdx").iterator();
        
        iterator.next();
        iterator.next();
        iterator.next();
    }
    
    @Test
    public void hasNextReturnsTrueTest() {
        
        Iterator<String> iterator = prefMatcher.wordsWithPrefix("abcdx").iterator();
        
        assertEquals(true, iterator.hasNext());       
    }
    
    @Test
    public void hasNextReturnsFalseTest() {
        Iterator<String> iterator = prefMatcher.wordsWithPrefix("abcdx").iterator();
        
        iterator.next();
        iterator.next();
        assertEquals(false, iterator.hasNext());        
    }
    
    @Test
    public void addTest() {
        verify(mockTrie, times(9)).add(any(Tuple.class));
    }

    @Test
    public void wordsWithPrefixStringIntTest() {

        Iterator<String> iterator = prefMatcher.wordsWithPrefix("abcdx").iterator();

        assertEquals("abcdx", iterator.next());
        assertEquals("abcdxs", iterator.next());
    }

    @Test
    public void wordsWithPrefixStringTest() {

        Iterator<String> iterator = prefMatcher.wordsWithPrefix("abc", 3).iterator();

        assertEquals("abc", iterator.next());
        assertEquals("abcds", iterator.next());
        assertEquals("abcdw", iterator.next());
        assertEquals("abcdx", iterator.next());
        assertEquals("abcdxs", iterator.next());
    }

    @Test
    public void deleteTest() {

        assertTrue(prefMatcher.delete("abb"));
        assertFalse(prefMatcher.contains("abb"));
        assertTrue(prefMatcher.size() == 7);
    }

    @Test
    public void addAndSizeTest() {

        assertTrue(prefMatcher.size() == 8);
    }

    @Test
    public void containsTest() {

        assertTrue(prefMatcher.contains("abc"));
        assertTrue(prefMatcher.contains("abcdx"));
        assertTrue(prefMatcher.contains("abcdxs"));
        assertTrue(prefMatcher.contains("asdasdawdsd"));
        assertTrue(prefMatcher.contains("abcds"));
        assertTrue(prefMatcher.contains("abcdw"));
        assertTrue(prefMatcher.contains("abb"));
        assertTrue(prefMatcher.contains("abbc"));
        assertFalse(prefMatcher.contains("ab"));
    }
}
