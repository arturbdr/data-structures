# data-structures
Project containing Java implementations of data structures

## Trie
Currently there're two types of implementation of Trie data structure
- One using HashMap
``` java
trie.impl.map.TrieMapImpl
trie.impl.map.NodeMapImpl
```
- One using Arrays
``` java
trie.impl.array.TrieArrayImpl
trie.impl.array.NodeArray
```

Both are implementing a single interface with the following methods
```java
boolean contains(final String wordToSearch);
List<String> suggestionsOf(final String wordToSuggest);
void addEntry(final String wordToInclude);
void removeEntry(final String wordToRemove);
```
