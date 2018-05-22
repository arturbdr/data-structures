package trie.impl.map;


import trie.TrieContract;

public class TrieMapImpl implements TrieContract {

    private final NodeMap root;

    public TrieMapImpl() {
        this.root = new NodeMap();
    }

    private void validateInput(final String word) {
        if (word == null || word.trim().length() == 0) {
            throw new IllegalArgumentException("invalid word");
        }
    }

    public boolean find(final String wordToSearch) {
        validateInput(wordToSearch);

        char[] input = wordToSearch.toCharArray();
        NodeMap currentNodeMap = root;
        for (char currentChar : input) {
            if (!currentNodeMap.containsChar(currentChar)) {
                return false;
            }
            currentNodeMap = currentNodeMap.getNextNodeFromTrie(currentChar);
        }
        return currentNodeMap.isLastWordOfThisLeaf();
    }

    public void addEntry(final String wordToInclude) {
        validateInput(wordToInclude);

        char[] input = wordToInclude.toCharArray();
        NodeMap currentRoot = root;
        final StringBuilder nodeFullValue = new StringBuilder();
        for (char currentChar : input) {
            nodeFullValue.append(currentChar);
            if (!currentRoot.containsChar(currentChar)) {
                currentRoot.insertNewElementInTrie(currentChar, nodeFullValue.toString(), new NodeMap());
            }
            currentRoot = currentRoot.getNextNodeFromTrie(currentChar);
        }
        currentRoot.setLastWordOfThisLeaf(true);
    }

}
