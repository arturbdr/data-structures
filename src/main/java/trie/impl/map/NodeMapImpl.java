package trie.impl.map;

import lombok.Getter;
import lombok.Setter;
import trie.impl.Node;

import java.util.*;

@Getter
@Setter
public class NodeMapImpl implements Node {

    private boolean finalCharOfWord;
    private Map<Character, NodeMapImpl> trieMap;
    private String nodeFullValue;
    private char nodeValue;

    public NodeMapImpl() {
        this.trieMap = new HashMap<>(MAX_CHARS);
        nodeFullValue = "";
    }

    @Override
    public boolean containsChar(char charToCheck) {
        return trieMap.containsKey(charToCheck);
    }

    @Override
    public NodeMapImpl getNextNodeFromTrie(char c) {
        return this.trieMap.get(c);
    }

    @Override
    public void insertNewElementInTrie(char currentChar, final String nodeFullValue) {
        final NodeMapImpl nodeMap = new NodeMapImpl();
        nodeMap.setNodeValue(currentChar);
        nodeMap.setNodeFullValue(nodeFullValue);
        this.trieMap.put(currentChar, nodeMap);
    }

    @Override
    public List<String> getAllChildrenWords() {
        final List<String> allMatchingWords = new LinkedList<>();

        if (this.isFinalCharOfWord()) {
            allMatchingWords.add(this.getNodeFullValue());
        }

        this.getTrieMap().forEach((key, value) -> {
            final Collection<String> childMatchingWords = value.getAllChildrenWords();
            allMatchingWords.addAll(childMatchingWords);
        });
        return allMatchingWords;
    }

    @Override
    public boolean hasChildren() {
        return !this.trieMap.isEmpty();
    }

    @Override
    public void excludeChild(final Node nodeFromStack) {
        this.getTrieMap().remove(nodeFromStack.getNodeValue());
    }
}
