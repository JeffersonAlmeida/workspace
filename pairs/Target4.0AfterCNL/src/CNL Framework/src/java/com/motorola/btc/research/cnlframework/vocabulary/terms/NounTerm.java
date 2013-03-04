/*
 * @(#)NounTerm.java
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
package com.motorola.btc.research.cnlframework.vocabulary.terms;

import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;

/**
 * This class represents a lexical entry of the type Noun Term.
 * @author
 */
public class NounTerm extends LexicalEntry {

	/**
	 * Creates a noun term with its singular and plural forms.
	 * @param termSingular the term singular form
	 * @param termPlural the term plural form
	 */
	public NounTerm(String termSingular, String termPlural)
	{
		super();
		this.termsMap.put(PartsOfSpeech.NN, termSingular);
		this.termsMap.put(PartsOfSpeech.NNS, termPlural);
	}
	
	/**
	 * 
	 * @return the term singular form.
	 */
	public String getSingular()
	{
		return this.getTerm(PartsOfSpeech.NN);	
	}
	
	/**
	 * 
	 * @return the term plural form.
	 */
	public String getPlural()
	{
		return this.getTerm(PartsOfSpeech.NNS);
	}

	/**
	 * 
	 * @return the noun string format. 
	 */
    public String toString()
    {
        return "Noun: " + super.toString();
    }
}
