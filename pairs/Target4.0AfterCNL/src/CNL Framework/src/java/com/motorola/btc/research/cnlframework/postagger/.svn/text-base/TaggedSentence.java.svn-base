/*
 * @(#)TaggedSentence.java
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

import java.util.ArrayList;
import java.util.List;

import com.motorola.btc.research.cnlframework.tokenizer.TokenizedSentence;

/**
 * This class represents a tagged sentence.
 * In a tagged sentence the sentence terms are related to part of speech tags. 
 * @author 
 *
 */
public class TaggedSentence {

	private TokenizedSentence tokenSentence;
	
	private List<TaggedTerm> taggedTerms;
	
	/**
	 * Class constructor.
	 * @param taggedTerms a list containing the sentence tagged terms
	 * @param tokenSentence the tokenized sentence
	 */
	public TaggedSentence(List<TaggedTerm> taggedTerms, TokenizedSentence tokenSentence)
	{
		this.taggedTerms = taggedTerms;
		this.tokenSentence = tokenSentence;
	}

	/**
	 * 
	 * @return the tokenized sentence
	 */
	public TokenizedSentence getTokenSentence() {
		return tokenSentence;
	}

	/**
	 * 
	 * @return a list containing the sentence tagged terms
	 */
	public List<TaggedTerm> getTaggedTerms() {
		return new ArrayList<TaggedTerm>(taggedTerms);
	}
	
	/**
	 * This method returns the tagged term that matches the given position.
	 * @param pos the given term position
	 * @return the tagged term that matches the given position
	 */
	public TaggedTerm getTaggedTerm(int pos) {
		return this.taggedTerms.get(pos);
	}
	
	/**
	 * 
	 * @return the size of the tagged terms list.
	 */
	public int getSize() {
		return this.taggedTerms.size();
	}



    /**
     * This method returns a string containing the tagged terms.
     */
    public String toString()
    {
        String result = "";
        
        for(TaggedTerm tgTerm : taggedTerms)
        {
            result += "'" + tgTerm.getTermString() + "'/" + tgTerm.getPartOfSpeech() + " ";
        }
        return result;
    }
    
    
	
//	public List<TrieWord> getTrieWords() {
//		
//		ArrayList<TrieWord> result = new ArrayList<TrieWord>();
//		for(TaggedTerm tagTerm : this.taggedTerms)
//		{
//			result.add(tagTerm.getTerm());
//		}
//		return result;
//	}
	
}
