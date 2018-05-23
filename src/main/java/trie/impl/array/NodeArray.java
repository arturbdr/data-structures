package trie.impl.array;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
class NodeArray {

    private static final int MAX_CHARS = 26;
    private boolean finalCharOfWord;
    private char nodeValue;
    private NodeArray[] children;
    private String nodeFullValue;

    NodeArray() {
        this.children = new NodeArray[MAX_CHARS];
    }

    boolean containsChar(char charToCheck) {
        return Arrays.stream(children)
                .anyMatch(child -> child != null && child.nodeValue == charToCheck);
    }

    NodeArray getNextNodeFromTrie(char charToCheck) {
        NodeArray nodeArray = null;
        for (NodeArray currentNodeArray : children) {
            if (currentNodeArray != null && currentNodeArray.nodeValue == charToCheck) {
                nodeArray = currentNodeArray;
                break;
            }
        }
        return nodeArray;
    }

    void insertNewElementInTrie(char currentChar, final String nodeFullValue) {
        final NodeArray nodeArray = new NodeArray();
        nodeArray.nodeValue = currentChar;
        nodeArray.nodeFullValue = nodeFullValue;

        for (int i = 0; i < this.children.length; i++) {
            if (children[i] == null) {
                children[i] = nodeArray;
                break;
            }
        }
    }

    private NodeArray[] getAllChildren() {
        return this.children;
    }

    List<String> getAllChildrenWords() {
        final List<String> allMatchingWords = new LinkedList<>();

        if (this.isFinalCharOfWord()) {
            allMatchingWords.add(this.getNodeFullValue());
        }

        Arrays.stream(this.getAllChildren())
                .forEach(currentChild -> {
                    if (currentChild != null) {
                        final Collection<String> childMatchingWords = currentChild.getAllChildrenWords();
                        allMatchingWords.addAll(childMatchingWords);
                    }
                });
        return allMatchingWords;
    }

    boolean hasChildren() {
        return Arrays
                .stream(this.children)
                .anyMatch(Objects::nonNull);
    }

    void excludeChild(final NodeArray nodeToRemove) {
        for (int i = 0; i < this.children.length; i++) {
            if (this.children[i].nodeValue == nodeToRemove.nodeValue) {
                this.children[i] = null;
            }
        }
    }
}
