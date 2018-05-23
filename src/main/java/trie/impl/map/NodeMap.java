package trie.impl.map;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
class NodeMap {

    private static final int MAX_CHARS = 26;
    private boolean finalCharOfWord;
    private Map<Character, NodeMap> trieMap;
    private String nodeFullValue;
    private char nodeValue;

    NodeMap() {
        this.trieMap = new HashMap<>(MAX_CHARS);
    }

    boolean containsChar(char charToCheck) {
        return trieMap.containsKey(charToCheck);
    }

    NodeMap getNextNodeFromTrie(char c) {
        return this.trieMap.get(c);
    }

    void insertNewElementInTrie(char currentChar, final String nodeFullValue) {
        final NodeMap nodeMap = new NodeMap();
        nodeMap.setNodeValue(currentChar);
        nodeMap.setNodeFullValue(nodeFullValue);
        this.trieMap.put(currentChar, nodeMap);
    }

    List<String> getAllChildrenWords() {
        final List<String> allMatchingWords = new LinkedList<>();

        if (this.isFinalCharOfWord()) {
            allMatchingWords.add(this.getNodeFullValue());
        }

        this.getAllChildren()
                .forEach(currentChild -> {
                    final Collection<String> childPrefixes = currentChild.getValue().getAllChildrenWords();
                    allMatchingWords.addAll(childPrefixes);
                });
        return allMatchingWords;
    }

    private Set<Map.Entry<Character, NodeMap>> getAllChildren() {
        return this.getTrieMap().entrySet();
    }
}
