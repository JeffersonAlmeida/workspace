/*
 * @(#)TrieVocabulary.java
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

import java.util.Set;

/**
 * This class is a data structure to represent a vocabulary.
 * It contains an attribute that represents the vocabulary tree root.
 * @author
 *
 * @param <T>
 */
public class TrieVocabulary<T>
{

    private TrieNode<T> root;
    
    /**
     * Class constructor.
     * Initializes the tree root.
     */
    public TrieVocabulary()
    {

        this.root = new TrieNode<T>(' ');
    }
    
    /**
     * This method adds a word to the trie vocabulary.
     * @param str a char array representing the word to be added.
     * @param word the word to b added
     */
    public void addWord(char[] str, T word)
    {

        TrieNode<T> tNode = root;
        for (int i = 0; i < str.length; i++)
        {

            TrieNode<T> child = tNode.getChild(str[i]);
            if (child == null)
            {
                child = tNode.addChild(str[i]);
            }
            tNode = child;
        }
        tNode.addWord(word);
    }
    
    /**
     * Returns a set containing the words of the specified node.
     * @param str 
     * @return a set containing the words of the specified node
     */
    public Set<T> getWords(char[] str)
    {

        Set<T> result = null;

        TrieNode<T> tNode = root;
        for (int i = 0; i < str.length && tNode != null; i++)
        {
            tNode = tNode.getChild(str[i]);
        }
        if (tNode != null)
        {
            result = tNode.getWords();
        }

        return result;
    }

    /**
     * Checks if the given string is an end word.
     * @param str
     * @return the analysis result.
     */
    public boolean isEndWord(char[] str)
    {

        TrieNode<T> tNode = root;
        boolean result = false;
        for (int i = 0; i < str.length && tNode != null; i++)
        {
            tNode = tNode.getChild(str[i]);
        }
        if (tNode != null)
        {
            result = !tNode.hasChildren();
        }
        return result;
    }

}
