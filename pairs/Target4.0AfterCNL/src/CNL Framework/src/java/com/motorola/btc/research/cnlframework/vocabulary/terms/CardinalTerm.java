/*
 * @(#)CardinalTerm.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 22, 2008   LIBqq41824   Initial creation.
 */
package com.motorola.btc.research.cnlframework.vocabulary.terms;

import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;

/**
 * This class represents a lexical entry of the type Cardinal Term.
 * @author
 *
 */
public class CardinalTerm extends LexicalEntry
{
	/**
	 * Class constructor.
	 * Creates a cardinal term with the term value passed as parameter. 
	 * @param term the term value
	 */
	public CardinalTerm(String cardinal)
    {
        super(PartsOfSpeech.CD, cardinal);
    }
    
	/**
	 * 
	 * @return the cardinal term value
	 */
    public String getTerm()
    {
        return this.getTerm(PartsOfSpeech.CD);
    }
}
