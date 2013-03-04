/*
 * @(#)TokenizedText.java
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
package com.motorola.btc.research.cnlframework.tokenizer;

import java.util.List;

/**
 * This class represents a tokenized text. It contains the original 
 * text and the list containing the text sentences.
 * @author
 *
 */
public class TokenizedText {
	
	
	private String text;
	
	private List<TokenizedSentence> sentenceList;
	
	/**
	 * Class constructor.
	 * @param text the original text
	 * @param sentenceList the list containing the text sentences
	 */
	public TokenizedText(String text, List<TokenizedSentence> sentenceList)
	{
		this.text = text;
		this.sentenceList = sentenceList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	public String toString() {
		return this.text;
	}
	
	/**
	 * 
	 * @return the text sentences list
	 */
	public List<TokenizedSentence> getSentenceList() {
		return sentenceList;
	}
}
