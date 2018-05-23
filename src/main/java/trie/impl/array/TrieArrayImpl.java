package trie.impl.array;


import trie.TrieContract;

import java.util.Collections;
import java.util.List;

public class TrieArrayImpl implements TrieContract {

    private final NodeArray root;

    TrieArrayImpl() {
        this.root = new NodeArray();
    }

    private void validateInput(final String word) {
        if (word == null || word.trim().length() == 0) {
            throw new IllegalArgumentException("invalid word");
        }
    }

    public boolean contains(final String wordToCheck) {
        validateInput(wordToCheck);

        NodeArray currentNodeArray = root;
        for (char currentChar : wordToCheck.toLowerCase().toCharArray()) {
            if (!currentNodeArray.containsChar(currentChar)) {
                return false;
            }
            currentNodeArray = currentNodeArray.getNextNodeFromTrie(currentChar);
        }
        return currentNodeArray.isFinalCharOfWord();
    }

    public void addEntry(final String wordToInclude) {
        validateInput(wordToInclude);

        NodeArray currentRoot = root;
        final StringBuilder nodeValue = new StringBuilder();
        for (char currentChar : wordToInclude.toLowerCase().toCharArray()) {
            nodeValue.append(currentChar);
            if (!currentRoot.containsChar(currentChar)) {
                currentRoot.insertNewElementInTrie(currentChar, nodeValue.toString());
            }
            currentRoot = currentRoot.getNextNodeFromTrie(currentChar);
        }
        currentRoot.setFinalCharOfWord(true);
    }

    @Override
    public List<String> suggestionsOf(String wordToSuggest) {
        validateInput(wordToSuggest);

        NodeArray currentNodeMap = root;
        for (char currentChar : wordToSuggest.toLowerCase().toCharArray()) {
            if (!currentNodeMap.containsChar(currentChar)) {
                return Collections.emptyList();
            }
            currentNodeMap = currentNodeMap.getNextNodeFromTrie(currentChar);
        }
        return currentNodeMap.getAllChildrenWords();
    }

}
