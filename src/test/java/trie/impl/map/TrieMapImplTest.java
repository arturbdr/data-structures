package trie.impl.map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import trie.TrieContract;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrieMapImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TrieContract trieContract;

    @Before
    public void setup() {
        trieContract = new TrieMapImpl();
    }

    @Test
    public void shouldFindSingleElementsInTrie() {
        trieContract.addEntry("ab");

        assertTrue(trieContract.contains("ab"));
    }

    @Test
    public void shouldNotFindSingleElementsInTrie() {
        trieContract.addEntry("ab");

        assertFalse(trieContract.contains("aba"));
    }

    @Test
    public void shouldFindMultipleElements() {
        trieContract.addEntry("ab");
        trieContract.addEntry("a");
        trieContract.addEntry("abc");
        trieContract.addEntry("abcadfrew");
        trieContract.addEntry("z");

        assertTrue(trieContract.contains("ab"));
        assertTrue(trieContract.contains("a"));
        assertTrue(trieContract.contains("abc"));
        assertTrue(trieContract.contains("abcadfrew"));
        assertTrue(trieContract.contains("z"));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToInsertNullCharInTrie() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.addEntry(null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToInsertEmptyCharInTrie() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.addEntry("");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryToCallContainsWithNull() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.contains(null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryToCallContainsWithEmpty() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.contains("");
    }

    @Test
    public void shouldMatchSingleWordInTrie() {
        trieContract.addEntry("cartoon");
        List<String> matchingWords = trieContract.suggestionsOf("c");
        assertThat(matchingWords.get(0), is(equalTo("cartoon")));
        assertThat(matchingWords, hasSize(1));
    }

    @Test
    public void shouldMatchTwoWords() {
        trieContract.addEntry("cartoon");
        trieContract.addEntry("car");
        trieContract.addEntry("Great");
        trieContract.addEntry("Good");
        List<String> matchingWords = trieContract.suggestionsOf("car");
        assertThat(matchingWords, hasSize(2));
        assertThat(matchingWords, containsInAnyOrder("cartoon", "car"));
    }

    @Test
    public void shouldMatchTwoWordsCaseInsensitive() {
        trieContract.addEntry("CaRtOon");
        trieContract.addEntry("cAr");
        trieContract.addEntry("GrEat");
        trieContract.addEntry("GOOd");
        List<String> matchingWords = trieContract.suggestionsOf("car");
        assertThat(matchingWords, hasSize(2));
        assertThat(matchingWords, containsInAnyOrder("cartoon", "car"));
    }

    @Test
    public void shouldMatchOneWord() {
        trieContract.addEntry("EnginEer");
        trieContract.addEntry("engineering");
        trieContract.addEntry("eNgIne");
        trieContract.addEntry("engaged");
        trieContract.addEntry("emeRgency");
        trieContract.addEntry("CaRtOon");
        trieContract.addEntry("cAr");
        trieContract.addEntry("GrEat");
        trieContract.addEntry("GOOd");
        trieContract.addEntry("zoo");
        List<String> matchingWords = trieContract.suggestionsOf("en");
        assertThat(matchingWords, containsInAnyOrder("engineer", "engineering", "engine", "engaged"));
        assertThat(matchingWords, hasSize(4));
    }

    @Test
    public void shouldMatchNoWordDueToEmptyTrie() {
        List<String> matchingWords = trieContract.suggestionsOf("en");
        assertThat(matchingWords, is(empty()));
    }

    @Test
    public void shouldMatchNoWord() {
        trieContract.addEntry("EnginEer");
        trieContract.addEntry("engineering");
        trieContract.addEntry("eNgIne");
        trieContract.addEntry("engaged");
        trieContract.addEntry("emeRgency");
        trieContract.addEntry("CaRtOon");
        trieContract.addEntry("cAr");
        trieContract.addEntry("GrEat");
        trieContract.addEntry("GOOd");
        trieContract.addEntry("zoo");
        List<String> matchingWords = trieContract.suggestionsOf("yo");
        assertThat(matchingWords, is(empty()));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenSuggestingWithNull() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.suggestionsOf(null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenSuggestingWithEmpty() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.suggestionsOf("");
    }

    @Test
    public void shouldRemoveOneElementFromTrie() {
        trieContract.addEntry("EnginEer");
        trieContract.addEntry("engineering");
        trieContract.addEntry("Engine");
        trieContract.addEntry("GrEat");
        trieContract.addEntry("CaRrEat");
        assertThat(trieContract.contains("engineer"), is(true));

        List<String> matchingWordsBeforeRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsBeforeRemoval, containsInAnyOrder("engineer", "engineering", "engine"));
        assertThat(matchingWordsBeforeRemoval, hasSize(3));

        trieContract.removeEntry("engineer");

        assertThat(trieContract.contains("engineer"), is(false));
        List<String> matchingWordsAfterRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsAfterRemoval, containsInAnyOrder("engineering", "engine"));
        assertThat(matchingWordsAfterRemoval, hasSize(2));
    }

    @Test
    public void shouldRemoveTwoElementsFromTrie() {
        trieContract.addEntry("EnginEer");
        trieContract.addEntry("engineering");
        trieContract.addEntry("Engine");
        trieContract.addEntry("CaRrEat");
        assertThat(trieContract.contains("engineer"), is(true));

        List<String> matchingWordsBeforeRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsBeforeRemoval, containsInAnyOrder("engineer", "engineering", "engine"));
        assertThat(matchingWordsBeforeRemoval, hasSize(3));

        trieContract.removeEntry("engineer");
        trieContract.removeEntry("engine");

        assertThat(trieContract.contains("engineer"), is(false));
        assertThat(trieContract.contains("engine"), is(false));
        List<String> matchingWordsAfterRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsAfterRemoval, containsInAnyOrder("engineering"));
        assertThat(matchingWordsAfterRemoval, hasSize(1));
    }

    @Test
    public void shouldRemoveNoEntriesFromTrie() {
        trieContract.addEntry("EnginEer");
        trieContract.addEntry("engineering");
        trieContract.addEntry("Engine");
        trieContract.addEntry("CaRrEat");
        assertThat(trieContract.contains("engineer"), is(true));

        List<String> matchingWordsBeforeRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsBeforeRemoval, containsInAnyOrder("engineer", "engineering", "engine"));
        assertThat(matchingWordsBeforeRemoval, hasSize(3));

        trieContract.removeEntry("e");
        trieContract.removeEntry("eng");

        List<String> matchingWordsAfterRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsAfterRemoval, containsInAnyOrder("engineer", "engineering", "engine"));
        assertThat(matchingWordsAfterRemoval, hasSize(3));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenRemovingWithNull() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.removeEntry(null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenRemovingWithEmpty() {
        thrown.expect(IllegalArgumentException.class);
        trieContract.removeEntry("");
    }


}