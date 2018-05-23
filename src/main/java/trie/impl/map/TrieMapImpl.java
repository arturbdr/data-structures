package trie.impl.map;


import trie.TrieContract;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void removeEntry(final String wordToRemove) {
        validateInput(wordToRemove);

        Deque<NodeMap> nodesToBeDeleted = unmarkWordFromTrie(wordToRemove);
        if (nodesToBeDeleted == null) return;

        removeWordFromTrie(nodesToBeDeleted);
    }

    private void removeWordFromTrie(Deque<NodeMap> nodesToBeDeleted) {
        for (int i = 0; i < nodesToBeDeleted.size(); i++) {
            final NodeMap nodeFromStack = nodesToBeDeleted.pop();
            if (!nodeFromStack.hasChildren() && !nodeFromStack.isFinalCharOfWord()) {
                NodeMap immediateFather = nodesToBeDeleted.peek();
                immediateFather.excludeChild(nodeFromStack);
            } else {
                break;
            }
        }
    }

    private Deque<NodeMap> unmarkWordFromTrie(final String wordToRemove) {
        NodeMap currentNodeMap = root;
        Deque<NodeMap> nodesToBeDeleted = new ArrayDeque<>();
        for (char currentChar : wordToRemove.toLowerCase().toCharArray()) {
            if (!currentNodeMap.containsChar(currentChar)) {
                return null;
            }
            currentNodeMap = currentNodeMap.getNextNodeFromTrie(currentChar);
            nodesToBeDeleted.push(currentNodeMap);
        }

        currentNodeMap.setFinalCharOfWord(false);
        return nodesToBeDeleted;
    }
}
