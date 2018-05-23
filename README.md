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
boolean find(final String wordToSearch);
void addEntry(final String wordToInclude);
```

TODOs - due date 23/May
- [ ] Implement autocomplete (given a word, sugest all possible 'solutions').
- [ ] Delete a node from the trie
