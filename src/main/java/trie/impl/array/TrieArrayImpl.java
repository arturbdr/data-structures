package trie.impl.array;


import trie.TrieContract;

public class TrieArrayImpl implements TrieContract {

    private final NodeArray root;

    public TrieArrayImpl() {
        this.root = new NodeArray();
    }

    private void validateInput(final String word) {
        if (word == null || word.trim().length() == 0) {
            throw new IllegalArgumentException("invalid word");
        }
    }

    public boolean find(final String wordToSearch) {
        validateInput(wordToSearch);

        char[] input = wordToSearch.toCharArray();
        NodeArray currentNodeArray = root;
        for (char currentChar : input) {
            if (!currentNodeArray.containsChar(currentChar)) {
                return false;
            }
            currentNodeArray = currentNodeArray.getNextNodeFromTrie(currentChar);
        }
        return currentNodeArray.isLastWordOfThisLeaf();
    }

    public void addEntry(final String wordToInclude) {
        validateInput(wordToInclude);

        char[] input = wordToInclude.toCharArray();
        NodeArray currentRoot = root;
        final StringBuilder nodeValue = new StringBuilder();
        for (char currentChar : input) {
            nodeValue.append(currentChar);
            if (!currentRoot.containsChar(currentChar)) {
                currentRoot.insertNewElementInTrie(currentChar, nodeValue.toString(), new NodeArray());
            }
            currentRoot = currentRoot.getNextNodeFromTrie(currentChar);
        }
        currentRoot.setLastWordOfThisLeaf(true);
    }

}
