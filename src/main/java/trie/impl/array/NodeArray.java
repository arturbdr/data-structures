package trie.impl.array;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
class NodeArray {

    private boolean lastWordOfThisLeaf;
    private char nodeValue;
    private NodeArray[] children;
    private static final int MAX_CHARS = 26;
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

    void insertNewElementInTrie(char currentChar, final String nodeFullValue, final NodeArray nodeArray) {
        nodeArray.nodeValue = currentChar;
        nodeArray.nodeFullValue = nodeFullValue;

        for (int i = 0; i < this.children.length; i++) {
            if (children[i] == null) {
                children[i] = nodeArray;
                break;
            }
        }
    }
}
