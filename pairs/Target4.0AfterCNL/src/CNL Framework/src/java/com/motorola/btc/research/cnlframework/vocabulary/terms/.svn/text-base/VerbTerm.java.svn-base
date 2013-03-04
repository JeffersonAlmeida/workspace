/*
 * @(#)VerbTerm.java
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

import com.motorola.btc.research.cnlframework.util.UtilNLP;
import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;

/**
 * This class represents a lexical entry of the type Verb Term.
 * @author
 *
 */
public class VerbTerm extends LexicalEntry
{
    public static LexicalEntry VERB_TO_BE;
    static
    {
        HashMap<PartsOfSpeech, String> termsMap = new HashMap<PartsOfSpeech, String>();
        termsMap.put(PartsOfSpeech.VTB, UtilNLP.beInfinitive);
        termsMap.put(PartsOfSpeech.VTBDZ, UtilNLP.bePastSingular);
        termsMap.put(PartsOfSpeech.VTBDP, UtilNLP.bePastPlural);
        termsMap.put(PartsOfSpeech.VTBG, UtilNLP.beGerund);
        termsMap.put(PartsOfSpeech.VTBN, UtilNLP.bePastParticiple);
        termsMap.put(PartsOfSpeech.VTBP1, UtilNLP.bePresentFisrtSingular);
        termsMap.put(PartsOfSpeech.VTBP, UtilNLP.bePresentPlural);
        termsMap.put(PartsOfSpeech.VTBZ, UtilNLP.bePresentSingular);
        VERB_TO_BE = new LexicalEntry(termsMap);
    }
    
    /**
     * Class constructor.
     */
    protected VerbTerm()
    {
        super();
    }
    
    /**
     * Class constructor. Creates a verb term with the given verb tenses.
     * @param baseForm the verb base form
     * @param past the verb past form
     * @param pastParticiple the verb participle form
     * @param gerund the verb gerund form
     * @param thirdPersonPresent the verb third person present form.
     * @param nonThirdPersonPresent the verb form in the present and not in third person
     */
    public VerbTerm(String baseForm, String past, String pastParticiple, String gerund,
            String thirdPersonPresent, String nonThirdPersonPresent)
    {

        super();
        this.termsMap.put(PartsOfSpeech.VB, baseForm);
        this.termsMap.put(PartsOfSpeech.VBD, past);
        this.termsMap.put(PartsOfSpeech.VBG, gerund);
        this.termsMap.put(PartsOfSpeech.VBN, pastParticiple);
        //TODO changed
        //this.termsMap.put(PartsOfSpeech.VBP, nonThirdPersonPresent);
        this.termsMap.put(PartsOfSpeech.VBZ, thirdPersonPresent);

    }
    
    /**
     * 
     * @return the verb participle form
     */
    public String getParticiple()
    {
        return this.termsMap.get(PartsOfSpeech.VBN);
    }
    
    /**
     * 
     * @return the verb past form
     */
    public String getPast()
    {
        return this.termsMap.get(PartsOfSpeech.VBD);
    }

    /**
     * 
     * @return the verb base form
     */
    public String getTerm()
    {
        return this.termsMap.get(PartsOfSpeech.VB);
    }

    /**
     * 
     * @return the verb third person present form.
     */
    public String getThirdPerson()
    {
        return this.termsMap.get(PartsOfSpeech.VBP);
    }
    
    /**
     * 
     * @return the verb gerund form
     */
    public String getGerund()
    {
        return this.termsMap.get(PartsOfSpeech.VBG);
    }
    
    /**
     * 
     * Given a noun of a verb, return if the is present in VerbTerm.  
     * @param verb The verb to find.
     * @return if the verb is in  VerbTerm.
     */
    public boolean contains(String verb){
        return this.termsMap.values().contains(verb);
    }
    
    /**
	 * 
	 * @return the verb string value
	 */
    public String toString()
    {
        return "Verb: " + super.toString();
    }

}
