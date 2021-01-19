package trie.impl;


import trie.TrieContract;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class TrieImpl implements TrieContract {

    private final Node root;

    public TrieImpl(Node nodeImpl) {
        this.root = nodeImpl;
    }

    private void validateInput(final String word) {
        if (word == null || word.trim().length() == 0) {
            throw new IllegalArgumentException("invalid word");
        }
    }

    @Override
    public boolean contains(final String wordToCheck) {
        validateInput(wordToCheck);

        Node currentNode = root;
        for (char currentChar : wordToCheck.toLowerCase().toCharArray()) {
            if (!currentNode.containsChar(currentChar)) {
                return false;
            }
            currentNode = currentNode.getNextNodeFromTrie(currentChar);
        }
        return currentNode.isFinalCharOfWord();
    }

    @Override
    public void addEntry(final String wordToInclude) {
        validateInput(wordToInclude);

        Node currentNode = root;
        final StringBuilder nodeFullValue = new StringBuilder();
        for (char currentChar : wordToInclude.toLowerCase().toCharArray()) {
            nodeFullValue.append(currentChar);
            if (!currentNode.containsChar(currentChar)) {
                currentNode.insertNewElementInTrie(currentChar, nodeFullValue.toString());
            }
            currentNode = currentNode.getNextNodeFromTrie(currentChar);
        }
        currentNode.setFinalCharOfWord(true);
    }

    @Override
    public List<String> suggestionsOf(final String wordToSuggest) {
        validateInput(wordToSuggest);

        Node currentNodeMap = root;
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

        Deque<Node> nodesToBeDeleted = unmarkWordFromTrie(wordToRemove);
        if (nodesToBeDeleted == null) return;

        removeWordFromTrie(nodesToBeDeleted);
    }

    @Override
    public boolean containsPrefix(final String wordPrefix) {
        validateInput(wordPrefix);

        Node currentNode = root;

        for (char currentChar : wordPrefix.toLowerCase().toCharArray()) {

            if (currentNode.isFinalCharOfWord()) {
                return true;
            }

            if (!currentNode.containsChar(currentChar)) {
                return false;
            }

            currentNode = currentNode.getNextNodeFromTrie(currentChar);
        }

        return currentNode.getNodeFullValue().equals(wordPrefix) && currentNode.isFinalCharOfWord();
    }

    private void removeWordFromTrie(final Deque<Node> nodesToBeDeleted) {
        for (int i = 0; i < nodesToBeDeleted.size(); i++) {
            final Node nodeFromStack = nodesToBeDeleted.pop();
            if (!nodeFromStack.hasChildren() && !nodeFromStack.isFinalCharOfWord()) {
                Node immediateFather = nodesToBeDeleted.peek();
                immediateFather.excludeChild(nodeFromStack);
            } else {
                break;
            }
        }
    }

    private Deque<Node> unmarkWordFromTrie(final String wordToRemove) {
        Node currentNodeMap = root;
        Deque<Node> nodesToBeDeleted = new ArrayDeque<>();
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
