package trie;

public interface TrieContract {

    /**
     * Check if the current word is present in the trie
     *
     * @param wordToSearch - word to be searched
     * @return - true if existing in the trie. False otherwise
     */
    boolean find(final String wordToSearch);


    /**
     * Inserts a new entry in the trie
     *
     * @param wordToInclude - word to be included
     * @throws - IllegalArgumentException if the including word is null or empty
     */
    void addEntry(final String wordToInclude);

}
