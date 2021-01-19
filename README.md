# data-structures
Project containing Java implementations of data structures

## Trie
Currently there're two types of implementation of Trie data structure
- One using HashMap
``` java
trie.impl.TrieImpl
trie.impl.map.NodeMapImpl
```
- One using Arrays
``` java
trie.impl.array.TrieArrayImpl
trie.impl.array.NodeArray
```

Both are implementing a single interface with the following methods:
```java
boolean contains(final String wordToSearch);
List<String> suggestionsOf(final String wordToSuggest);
void addEntry(final String wordToInclude);
void removeEntry(final String wordToRemove);
boolean containsPrefix(final String wordPrefix);
```

## Brief Description of Methods
#### contains
Check if the given full word is in the Trie

#### suggestionsOf
Returns a list of all possible suggestion given the input word

#### addEntry
Insert an Entry in the trie

#### removeEntry
Remove an entry from the trie

#### containsPrefix
Checks if in the trie exists a prefix for the given word 