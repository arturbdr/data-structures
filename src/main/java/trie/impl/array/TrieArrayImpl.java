package trie.impl.array;


import trie.TrieContract;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
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

    @Override
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

    @Override
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

    @Override
    public void removeEntry(final String wordToRemove) {
        validateInput(wordToRemove);

        Deque<NodeArray> nodesToBeDeleted = unmarkWordFromTrie(wordToRemove);
        if (nodesToBeDeleted == null) return;

        removeWordFromTrie(nodesToBeDeleted);
    }

    private void removeWordFromTrie(Deque<NodeArray> nodesToBeDeleted) {
        for (int i = 0; i < nodesToBeDeleted.size(); i++) {
            final NodeArray nodeFromStack = nodesToBeDeleted.pop();
            if (!nodeFromStack.hasChildren() && !nodeFromStack.isFinalCharOfWord()) {
                NodeArray immediateFather = nodesToBeDeleted.peek();
                immediateFather.excludeChild(nodeFromStack);
            } else {
                break;
            }
        }
    }

    private Deque<NodeArray> unmarkWordFromTrie(final String wordToRemove) {
        NodeArray currentNodeMap = root;
        Deque<NodeArray> nodesToBeDeleted = new ArrayDeque<>();
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
