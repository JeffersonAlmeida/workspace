/*
 * @(#)DuplicatedTermInLexiconException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   May 19, 2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cnl.exceptions;

import java.util.Set;

import com.motorola.btc.research.cnlframework.exceptions.CNLException;
import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;
import com.motorola.btc.research.cnlframework.vocabulary.terms.VerbTerm;

@SuppressWarnings("serial")
/**
 * This class represents an exception thrown while trying to add a term to the lexicon.
 */
public class DuplicatedTermInLexiconException extends CNLException
{
    private LexicalEntry lEntry;

    private Set<LexicalEntry> duplicatedEntries;

    /**
     * Class constructor.
     * @param lEntry the entry to be added
     * @param duplicated a set with the duplicated entries.
     */
    public DuplicatedTermInLexiconException(LexicalEntry lEntry, Set<LexicalEntry> duplicated)
    {
        this.lEntry = lEntry;
        this.duplicatedEntries = duplicated;
    }


    /**
     * Returns the error message.
     */
    public String getMessage()
    {
        String message = "The following terms somehow intersects with the lexical entry to be added:\n";

        for (LexicalEntry dupEntry : this.duplicatedEntries)
        {
            if (dupEntry == VerbTerm.VERB_TO_BE)
            {
                message += "\n\tVerb to Be: " + dupEntry.toString();
            }
            else if (dupEntry.getClass().getCanonicalName().equals(
                    LexicalEntry.class.getCanonicalName()))
            {
                message += "\n\tParticle: " + dupEntry.toString();
            }
            else
            {
                message += "\n\t" + dupEntry.toString();
            }
        }

        return message;
    }

    /**
     * 
     * @return the entry to be added
     */
    public LexicalEntry getLEntry()
    {
        return this.lEntry;
    }
    
    /**
     * 
     * @return a set with the duplicated entries.
     */
    public Set<LexicalEntry> getDuplicatedEntries()
    {
        return this.duplicatedEntries;
    }

}
