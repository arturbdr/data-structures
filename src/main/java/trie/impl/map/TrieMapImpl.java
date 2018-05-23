package trie.impl.map;


import trie.TrieContract;

import java.util.Collections;
import java.util.List;

public class TrieMapImpl implements TrieContract {

    private final NodeMap root;

    TrieMapImpl() {
        this.root = new NodeMap();
    }

    private void validateInput(final String word) {
        if (word == null || word.trim().length() == 0) {
            throw new IllegalArgumentException("invalid word");
        }
    }

    public boolean contains(final String wordToCheck) {
        validateInput(wordToCheck);

        NodeMap currentNodeMap = root;
        for (char currentChar : wordToCheck.toLowerCase().toCharArray()) {
            if (!currentNodeMap.containsChar(currentChar)) {
                return false;
            }
            currentNodeMap = currentNodeMap.getNextNodeFromTrie(currentChar);
        }
        return currentNodeMap.isFinalCharOfWord();
    }

    public void addEntry(final String wordToInclude) {
        validateInput(wordToInclude);

        NodeMap currentNodeMap = root;
        final StringBuilder nodeFullValue = new StringBuilder();
        for (char currentChar : wordToInclude.toLowerCase().toCharArray()) {
            nodeFullValue.append(currentChar);
            if (!currentNodeMap.containsChar(currentChar)) {
                currentNodeMap.insertNewElementInTrie(currentChar, nodeFullValue.toString());
            }
            currentNodeMap = currentNodeMap.getNextNodeFromTrie(currentChar);
        }
        currentNodeMap.setFinalCharOfWord(true);
    }

    public List<String> suggestionsOf(final String wordToSuggest) {
        validateInput(wordToSuggest);

        NodeMap currentNodeMap = root;
        for (char currentChar : wordToSuggest.toLowerCase().toCharArray()) {
            if (!currentNodeMap.containsChar(currentChar)) {
                return Collections.emptyList();
            }
            currentNodeMap = currentNodeMap.getNextNodeFromTrie(currentChar);
        }
        return currentNodeMap.getAllChildrenWords();
    }
}
