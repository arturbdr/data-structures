package trie.impl;

import java.util.List;

public interface Node {

    int MAX_CHARS = 26;

    boolean containsChar(char charToCheck);

    boolean hasChildren();

    boolean isFinalCharOfWord();

    char getNodeValue();

    List<String> getAllChildrenWords();

    Node getNextNodeFromTrie(char charToRetrieveNextTrie);

    String getNodeFullValue();

    void excludeChild(Node nodeFromStack);

    void insertNewElementInTrie(char currentChar, String nodeFullValue);

    void setFinalCharOfWord(boolean finalCharOfWord);
}
