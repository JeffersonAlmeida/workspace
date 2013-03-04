/*
 * @(#)TaggedTerm.java
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
package com.motorola.btc.research.cnlframework.postagger;

import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;
import com.motorola.btc.research.cnlframework.vocabulary.TrieWord;
import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;

/**
 * This class represents a tagged term, that means a term with its start and end positions.
 * @author 
 *
 */
public class TaggedTerm {
	
	private TrieWord term;
	
	private int start;
	
	private int end;
	
	/**
	 * Class constructor.
	 * @param term a trie word term
	 * @param start the term start position
	 * @param end the term end position
	 */
	public TaggedTerm(TrieWord term, int start, int end)
	{
		this.end = end;
		this.start = start;
		this.term = term;
	}

	/**
	 * 
	 * @return the term end position.
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * 
	 * @return the term start position.
	 */
	public int getStart() {
		return start;
	}
    
	/**
	 * 
	 * @return the term word.
	 */
    public String getTermString()
    {
        return term.getWord();
    }

    /**
     * 
     * @return the term corresponding lexical entry
     */
	public LexicalEntry getTerm() {
		return term.getLexicalEntry();
	}
	
	/**
	 * 
	 * @return the term parts of speech tag
	 */
	public PartsOfSpeech getPartOfSpeech() {
		return term.getPartOfSpeech();
	}


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

    public String toString()
    {
        return this.getTermString();
    }
	
	
}
