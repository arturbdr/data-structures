package trie.impl;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import trie.TrieContract;
import trie.impl.array.NodeArray;
import trie.impl.map.NodeMapImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class TrieImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TrieContract trieContract;

    private Object getNodeImplementations() {
        return Arrays.asList(new NodeMapImpl(), new NodeArray());
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldFindSingleElementsInTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("ab");

        assertTrue(trieContract.contains("ab"));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldNotFindSingleElementsInTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("ab");

        assertFalse(trieContract.contains("aba"));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldFindMultipleElements(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
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
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenTryingToInsertNullCharInTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.addEntry(null);
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenTryingToInsertEmptyCharInTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.addEntry("");
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenTryToCallContainsWithNull(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.contains(null);
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenTryToCallContainsWithEmpty(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.contains("");
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldMatchSingleWordInTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("cartoon");
        List<String> matchingWords = trieContract.suggestionsOf("c");
        assertThat(matchingWords.get(0), is(equalTo("cartoon")));
        assertThat(matchingWords, hasSize(1));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldMatchTwoWords(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("cartoon");
        trieContract.addEntry("car");
        trieContract.addEntry("Great");
        trieContract.addEntry("Good");
        List<String> matchingWords = trieContract.suggestionsOf("car");
        assertThat(matchingWords, hasSize(2));
        assertThat(matchingWords, containsInAnyOrder("cartoon", "car"));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldMatchTwoWordsCaseInsensitive(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("CaRtOon");
        trieContract.addEntry("cAr");
        trieContract.addEntry("GrEat");
        trieContract.addEntry("GOOd");
        List<String> matchingWords = trieContract.suggestionsOf("car");
        assertThat(matchingWords, hasSize(2));
        assertThat(matchingWords, containsInAnyOrder("cartoon", "car"));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldMatchOneWord(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
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
    @Parameters(method = "getNodeImplementations")
    public void shouldMatchNoWordDueToEmptyTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        List<String> matchingWords = trieContract.suggestionsOf("en");
        assertThat(matchingWords, is(empty()));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldMatchNoWord(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
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
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenSuggestingWithNull(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.suggestionsOf(null);
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenSuggestingWithEmpty(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.suggestionsOf("");
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldRemoveOneElementFromTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("EnginEer");
        trieContract.addEntry("engineering");
        trieContract.addEntry("engineeri");
        trieContract.addEntry("Engine");
        trieContract.addEntry("GrEat");
        trieContract.addEntry("CaRrEat");
        assertThat(trieContract.contains("engineer"), is(true));

        List<String> matchingWordsBeforeRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsBeforeRemoval, containsInAnyOrder("engineer", "engineering", "engine", "engineeri"));
        assertThat(matchingWordsBeforeRemoval, hasSize(4));

        trieContract.removeEntry("engineering");

        assertThat(trieContract.contains("engineering"), is(false));
        List<String> matchingWordsAfterRemoval = trieContract.suggestionsOf("en");
        assertThat(matchingWordsAfterRemoval, containsInAnyOrder("engineer", "engine", "engineeri"));
        assertThat(matchingWordsAfterRemoval, hasSize(3));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldRemoveTwoElementsFromTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
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
    @Parameters(method = "getNodeImplementations")
    public void shouldRemoveNoEntriesFromTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
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
    @Parameters(method = "getNodeImplementations")
    public void shouldRemoveNoEntriesFromTrieDueToEmptyTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.removeEntry("e");
        trieContract.removeEntry("eng");

        List<String> matchingWordsAfterRemoval = trieContract.suggestionsOf("z");
        assertThat(matchingWordsAfterRemoval, hasSize(0));
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenRemovingWithNull(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.removeEntry(null);
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenRemovingWithEmpty(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.removeEntry("");
    }


    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldReturnPrefixForEngineering(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("en");
        trieContract.addEntry("Engine");
        trieContract.addEntry("CaRrEat");
        trieContract.addEntry("Energy");

        boolean expectTrue = trieContract.containsPrefix("engineering");
        assertTrue(expectTrue);

        boolean expectTrue2 = trieContract.containsPrefix("en");
        assertTrue(expectTrue2);

    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldReturnPrefixForEngineeringWithOnlyOneRegister(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("en");

        boolean expectTrue = trieContract.containsPrefix("engineering");
        assertTrue(expectTrue);

        boolean expectTrue2 = trieContract.containsPrefix("en");
        assertTrue(expectTrue2);

    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldNotReturnPrefixForEngineeringWithOnlyOneRegister(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        trieContract.addEntry("em");

        boolean expectTrue = trieContract.containsPrefix("engineering");
        assertFalse(expectTrue);

        boolean expectTrue2 = trieContract.containsPrefix("en");
        assertFalse(expectTrue2);
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldNotReturnPrefixDueToEmptyTrie(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        boolean expectTrue = trieContract.containsPrefix("engineering");
        assertFalse(expectTrue);

        boolean expectTrue2 = trieContract.containsPrefix("en");
        assertFalse(expectTrue2);
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenContainsPrefixWithNull(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.containsPrefix(null);
    }

    @Test
    @Parameters(method = "getNodeImplementations")
    public void shouldThrowIllegalArgumentExceptionWhenContainsPrefixWithEmpty(Node nodeImpl) {
        trieContract = new TrieImpl(nodeImpl);
        thrown.expect(IllegalArgumentException.class);
        trieContract.containsPrefix("");
    }
}