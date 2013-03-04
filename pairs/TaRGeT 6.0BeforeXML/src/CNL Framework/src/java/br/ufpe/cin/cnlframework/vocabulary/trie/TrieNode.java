/*
 * @(#)TrieNode.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package br.ufpe.cin.cnlframework.vocabulary.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a node of the vocabulary trie.
 * @author
 *
 * @param <WORD>
 */
class TrieNode<WORD> {

	private char character;

	private HashMap<Character, TrieNode<WORD>> children;

	private Set<WORD> words;
	
	/**
	 * Class constructor.
	 * Creates a trie node with the given char.
	 * @param chr the trie node character
	 */
	TrieNode(char chr) {
		this.character = chr;
		this.children = new HashMap<Character, TrieNode<WORD>>();
		this.words = null;
	}
	
	/**
	 * This method creates a new trie node with the given char and adds it to the children hash map.
	 * @param chr the trie node character
	 * @return the created trie node.
	 */
	TrieNode<WORD> addChild(char chr) {

		TrieNode<WORD> trieNode = new TrieNode<WORD>(chr);

		this.children.put(chr, trieNode);

		return trieNode;
	}
	
	/**
	 * 
	 * @return the trie node character.
	 */
	public char getCharacter() {
		return this.character;
	}

	/**
	 * Adds the given word to the trie node words hash map.
	 * @param word the word to be added.
	 */
	void addWord(WORD word) {

		if (this.words == null) {
			this.words = new HashSet<WORD>();
		}
		this.words.add(word);
	}
	
	/**
	 * Returns the trie word child corresponding to the char value.
	 * @param chr the trie word char value
	 * @return the trie word child corresponding to the char value
	 */
	TrieNode<WORD> getChild(char chr) {
		return this.children.get(chr);
	}
	
	/**
	 * Checks if the node has children.
	 * @return the results of the analysis.
	 */
	boolean hasChildren() {
        return this.children.size() > 0;
    }
	
	/**
	 * 
	 * @return all the words in the node words hash map.
	 */
	Set<WORD> getWords() {
		HashSet<WORD> result = null;
		if (this.words != null) {
			result = new HashSet<WORD>(this.words);
		}
		return result;
	}

}
