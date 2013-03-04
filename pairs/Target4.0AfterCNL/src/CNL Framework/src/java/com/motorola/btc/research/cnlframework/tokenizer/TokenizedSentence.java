/*
 * @(#)TokenizedSentence.java
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

/**
 * This class represents a tokenized sentence. It stores the sentence start
 * and end character in the text.
 * @author 
 */
public class TokenizedSentence {

	private int startInText;

	private int endInText;

	private String actualSentence;
	
	/**
	 * Class constructor.
	 * @param actualSentence the actual sentence
	 * @param start the index of the sentence start character
	 * @param end the index of the sentence end character
	 */
	public TokenizedSentence(String actualSentence, int start, int end) {
		this.actualSentence = actualSentence;
		this.startInText = start;
		this.endInText = end;
	}
	
	/**
	 * 
	 * @return the index of the sentence end character
	 */
	public int getEndInText() {
		return endInText;
	}
	
	/**
	 * 
	 * @return start the index of the sentence start character
	 */
	public int getStartInText() {
		return startInText;
	}
	
	/**
	 * 
	 * @return the actual sentence
	 */
	public String getActualSentence() {
		return actualSentence;
	}


	/**
	 * Returns a string representing a tokenized sentence.
	 */
	public String toString() {
		return "[" + this.startInText + "," + this.endInText + "] - "
				+ this.actualSentence;
	}

}
