/*
 * @(#)TrieWord.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package com.motorola.btc.research.cnlframework.vocabulary;

import java.util.Collection;

import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;
import com.motorola.btc.research.cnlframework.vocabulary.trie.TrieVocabulary;

/**
 * This class represents a trie word. It contains a parts of speech tag, 
 * the corresponding lexical entry and the word.
 * @author 
 *
 */
public class TrieWord
{

    private PartsOfSpeech partOfSpeech;

    private LexicalEntry lexicalEntry;

    private String word;
    
    /**
     * Class constructor
     * @param word the word value
     * @param lexicalEntry the corresponding lexical entry
     * @param pos the parts of speech tag
     */
    public TrieWord(String word, LexicalEntry lexicalEntry, PartsOfSpeech pos)
    {
        this.lexicalEntry = lexicalEntry;
        this.partOfSpeech = pos;
        this.word = word;
    }

    /**
     * 
     * @return the parts of speech tag
     */
    public PartsOfSpeech getPartOfSpeech()
    {
        return this.partOfSpeech;
    }

    /**
     * 
     * @return the trie word corresponding lexical entry.
     */
    public LexicalEntry getLexicalEntry()
    {
        return this.lexicalEntry;
    }
    
    /**
     * 
     * @return the trie word
     */
    public String getWord()
    {
        return this.word;
    }

    /**
	 * 
	 * @return the word string format. 
	 */
    public String toString()
    {
        return this.word;
    }

    /**
     * Two TrieWord are equals if they have the same partOfSpeechs and the same words 
     */
    public boolean equals(Object obj)
    {
        TrieWord tWord = (TrieWord) obj;

        return tWord != null && tWord.partOfSpeech.equals(this.partOfSpeech)
                && tWord.word.equals(this.word);
    }
    
    

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */

    public int hashCode()
    {
        return this.partOfSpeech.hashCode() + this.word.hashCode();
    }
    
    /**
     * This method receives a collection of trie words and returns a vocabulary 
     * containing all the words in the collection.
     * @param tWords the words collection 
     * @return a trie vocabulary containing all the words in the collection.
     */
    public static TrieVocabulary<TrieWord> getVocabularyFromCollection(Collection<TrieWord> tWords)
    {
        TrieVocabulary<TrieWord> grammarTrieVocabulary = new TrieVocabulary<TrieWord>();

        for (TrieWord tWord : tWords)
        {
            grammarTrieVocabulary.addWord(tWord.getWord().toCharArray(), tWord);
        }

        return grammarTrieVocabulary;
    }
}
