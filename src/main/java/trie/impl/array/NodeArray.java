package trie.impl.array;

import lombok.Getter;
import lombok.Setter;
import trie.impl.Node;

import java.util.*;

@Getter
@Setter
public class NodeArray implements Node {

    private boolean finalCharOfWord;
    private char nodeValue;
    private NodeArray[] children;
    private String nodeFullValue;

    public NodeArray() {
        this.setChildren(new NodeArray[MAX_CHARS]);
    }

    @Override
    public boolean containsChar(char charToCheck) {
        return Arrays.stream(children)
                .anyMatch(child -> child != null && child.getNodeValue() == charToCheck);
    }

    @Override
    public NodeArray getNextNodeFromTrie(char charToCheck) {
        NodeArray nodeArray = null;
        for (NodeArray currentNodeArray : children) {
            if (currentNodeArray != null && currentNodeArray.getNodeValue() == charToCheck) {
                nodeArray = currentNodeArray;
                break;
            }
        }
        return nodeArray;
    }

    @Override
    public void insertNewElementInTrie(char currentChar, final String nodeFullValue) {
        final NodeArray nodeArray = new NodeArray();
        nodeArray.setNodeValue(currentChar);
        nodeArray.setNodeFullValue(nodeFullValue);

        for (int i = 0; i < this.children.length; i++) {
            if (children[i] == null) {
                children[i] = nodeArray;
                break;
            }
        }
    }

    @Override
    public List<String> getAllChildrenWords() {
        final List<String> allMatchingWords = new LinkedList<>();

        if (this.isFinalCharOfWord()) {
            allMatchingWords.add(this.getNodeFullValue());
        }

        Arrays.stream(this.getChildren())
                .forEach(currentChild -> {
                    if (currentChild != null) {
                        final Collection<String> childMatchingWords = currentChild.getAllChildrenWords();
                        allMatchingWords.addAll(childMatchingWords);
                    }
                });
        return allMatchingWords;
    }

    @Override
    public boolean hasChildren() {
        return Arrays
                .stream(this.getChildren())
                .anyMatch(Objects::nonNull);
    }

    @Override
    public void excludeChild(final Node nodeToRemove) {
        for (int i = 0; i < this.children.length; i++) {
            if (this.children[i] != null && this.children[i].getNodeValue() == nodeToRemove.getNodeValue()) {
                this.children[i] = null;
                break;
            }
        }
    }
}
