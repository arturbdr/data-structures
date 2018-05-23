package trie;

import java.util.List;

public interface TrieContract {

    /**
     * Check if the current word is present in the trie
     *
     * @param wordToSearch - word to be searched
     * @return - true if existing in the trie. False otherwise
     * @throws - IllegalArgumentException if the including word is null or empty
     */
    boolean contains(final String wordToSearch);


    /**
     * Inserts a new entry in the trie
     *
     * @param wordToInclude - word to be included
     * @throws - IllegalArgumentException if the including word is null or empty
     */
    void addEntry(final String wordToInclude);

    /**
     * Check for all word suggestions of given word <br/>
     * For example:
     * <br/>
     * If the tree contains the following words:
     * <ul>
     * <li>engineer</li>
     * <li>engineering</li>
     * <li>engine</li>
     * <li>engaged</li>
     * <li>emergency</li>
     * </ul>
     * <p>
     * And this method gets called passing as parameter the word ("en") it will return a list containing all the words beginning with it:
     * <p>
     * <ul>
     * <li>engineer</li>
     * <li>engineering</li>
     * <li>engine</li>
     * <li>engaged</li>
     * </ul>
     *
     * @param wordToSuggest - A prefix of word
     * @return - A list of words containing all possible suggestions
     * @throws - IllegalArgumentException if the word to suggest is null or empty
     */
    List<String> suggestionsOf(final String wordToSuggest);

    /**
     * Remove one entry from the Trie <br/>
     * For example:
     * <br/>
     * If the tree contains the following words:
     * <ul>
     * <li>engineer</li>
     * <li>engineering</li>
     * <li>engine</li>
     * <li>engaged</li>
     * <li>emergency</li>
     * </ul>
     * <p>
     * And this method gets called passing as parameter the word ("engineer") it will remove this element from the Trie:
     *
     * @param wordToRemove - The word to be removed
     * @throws - IllegalArgumentException if the word to suggest is null or empty
     */
    void removeEntry(final String wordToRemove);

}
