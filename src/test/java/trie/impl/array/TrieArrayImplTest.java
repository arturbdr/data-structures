package trie.impl.array;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import trie.TrieContract;
import trie.impl.map.TrieMapImpl;

import static org.junit.Assert.*;

public class TrieArrayImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFindSingleElementsInTrie() {
        TrieContract trieContract = new TrieArrayImpl();
        trieContract.addEntry("ab");

        assertTrue(trieContract.find("ab"));
    }

    @Test
    public void shouldNotFindSingleElementsInTrie() {
        TrieContract trieContract = new TrieMapImpl();
        trieContract.addEntry("ab");

        assertFalse(trieContract.find("aba"));
    }

    @Test
    public void shouldFindMultipleElements() {
        TrieContract trieContract = new TrieMapImpl();
        trieContract.addEntry("ab");
        trieContract.addEntry("a");
        trieContract.addEntry("abc");
        trieContract.addEntry("abcadfrew");
        trieContract.addEntry("z");

        assertTrue(trieContract.find("ab"));
        assertTrue(trieContract.find("a"));
        assertTrue(trieContract.find("abc"));
        assertTrue(trieContract.find("abcadfrew"));
        assertTrue(trieContract.find("z"));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToInsertNullCharInTrie() {
        TrieContract trieContract = new TrieMapImpl();

        thrown.expect(IllegalArgumentException.class);
        trieContract.addEntry(null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToInserEmptyCharInTrie() {
        TrieContract trieContract = new TrieMapImpl();

        thrown.expect(IllegalArgumentException.class);
        trieContract.addEntry("");
    }

}