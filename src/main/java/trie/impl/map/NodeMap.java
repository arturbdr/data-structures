package trie.impl.map;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
class NodeMap {

    private boolean lastWordOfThisLeaf;
    private Map<Character, NodeMap> trieMap;
    private static final int MAX_CHARS = 26;
    private String nodeFullValue;

    NodeMap() {
        this.trieMap = new HashMap<>(MAX_CHARS);
    }

    boolean containsChar(char charToCheck) {
        return trieMap.containsKey(charToCheck);
    }

    NodeMap getNextNodeFromTrie(char c) {
        return this.trieMap.get(c);
    }

    void insertNewElementInTrie(char currentChar, final String nodeFullValue, final NodeMap nodeMap) {
        this.nodeFullValue = nodeFullValue;
        this.trieMap.put(currentChar, nodeMap);
    }
}
