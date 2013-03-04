/*
 * @(#)LexicalEntry.java
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

import java.util.HashMap;
import java.util.Set;

import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;

/**
 * This class represents a lexical entry. A lexical entry contains a HashMap 
 * that associates a PartsOfSpeech tag to a string value. 
 * @author 
 *
 */
/**
 * @author jrm687
 *
 */
public class LexicalEntry
{
    protected HashMap<PartsOfSpeech, String> termsMap;

    /**
     * Class constructor.
     * Creates an empty hash map.
     */
    protected LexicalEntry()
    {
        this.termsMap = new HashMap<PartsOfSpeech, String>();
    }
    
    /**
     * Class constructor. 
     * Creates a hash mapping containing the parts of speech tag and the term passed as parameter
     * @param pos the parts of speech tag
     * @param term the text of the term
     */
    public LexicalEntry(PartsOfSpeech pos, String term)
    {
        this.termsMap = new HashMap<PartsOfSpeech, String>();
        this.termsMap.put(pos, term);
    }
    
    /**
     * Class constructor.
     * Associates the LexicalEntry HashMap attribute to the HashMap passed as parameter.
     * @param termsMap a hashMap to be associated to the LexicalEntry
     */
    public LexicalEntry(HashMap<PartsOfSpeech, String> termsMap)
    {
        this.termsMap = new HashMap<PartsOfSpeech, String>(termsMap);
    }
    
    /**
     * This method returns all the Parts of Speech tags existing in the LexicalEntry.
     * @return all the Parts of Speech tags existing in the LexicalEntry
     */
    public Set<PartsOfSpeech> getAvailablePOSTags()
    {
        return this.termsMap.keySet();
    }
    
    /**
     * Returns the term corresponding to the given Parts of Speech tag.
     * @param pos a Parts of Speech tag
     * @return the term corresponding to the given Parts of Speech tag
     */
    public String getTerm(PartsOfSpeech pos)
    {
        return this.termsMap.get(pos);
    }

    /**
     * 
     */
    public boolean equals(Object obj)
    {

        boolean result = false;
        if (obj instanceof LexicalEntry)
        {
            LexicalEntry lexEntry = (LexicalEntry) obj;

            result = lexEntry.termsMap.equals(this.termsMap);
        }
        return result;
    }
    
    /**
	 * Returns all terms presents on LexicalEntry.
	 * 
	 */
    public String toString()
    {
        String result = ", ";

        for (PartsOfSpeech pos : termsMap.keySet())
        {
            result += "\'" + this.termsMap.get(pos) + "\'/" + pos;
        }

        return result.substring(2);
    }

}
