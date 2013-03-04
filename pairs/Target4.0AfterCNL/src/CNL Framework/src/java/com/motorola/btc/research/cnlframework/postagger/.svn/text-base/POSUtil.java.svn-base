package com.motorola.btc.research.cnlframework.postagger;

import java.util.HashMap;

import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;

public class POSUtil {
	/** The singleton instance */
    private static POSUtil instance = null;
	
	private HashMap<PartsOfSpeech, String> hashMap;
	
	/**
     * The singleton <code>getInstance</code> method.
     * 
     * @return The single instance of <code>GrammarReader</code>.
     */
    public static POSUtil getInstance()
    {
        if (instance == null)
        {
            instance = new POSUtil();
        }
        return instance;
    }
	
	private POSUtil(){
		hashMap = new HashMap<PartsOfSpeech, String>();
		this.initialize();
	}
	
	private void initialize(){
		hashMap.put(PartsOfSpeech.CD, "Cardinal");
		hashMap.put(PartsOfSpeech.CJ, "Conjunction");
		hashMap.put(PartsOfSpeech.DT, "Determiner");
		hashMap.put(PartsOfSpeech.NG, "Negation");
		hashMap.put(PartsOfSpeech.DO, "Auxiliar 'DO'");
		hashMap.put(PartsOfSpeech.EX, "Existentioal There");
		hashMap.put(PartsOfSpeech.NN, "Noun");
		hashMap.put(PartsOfSpeech.NNS, "Plural Noun");
		hashMap.put(PartsOfSpeech.OD, "Ordinal");
		hashMap.put(PartsOfSpeech.PP, "Preposition");
		hashMap.put(PartsOfSpeech.JJ, "Adjective");
		hashMap.put(PartsOfSpeech.VB, "Verb Base Form");
		hashMap.put(PartsOfSpeech.VBD, "Verb Past Tense");
		hashMap.put(PartsOfSpeech.VBG, "Gerund Verb");
		hashMap.put(PartsOfSpeech.VBN, "Past Participle Verb");
		hashMap.put(PartsOfSpeech.VBP, "Non-third Person Singular Present Verb");
		hashMap.put(PartsOfSpeech.VBZ, "Third Person Singular Present Verb");
		
		/*
		 * VERB TO BE
		 */
		
		hashMap.put(PartsOfSpeech.VTB, "Verb To Be Base Form");
		hashMap.put(PartsOfSpeech.VTBDZ, "Verb To Be Third Person Past Tense");
		hashMap.put(PartsOfSpeech.VTBDP, "Verb To Be Non-Third Person Past Tense");
		hashMap.put(PartsOfSpeech.VTBG, "Verb To Be Gerund");
		hashMap.put(PartsOfSpeech.VTBN, "Verb To Be Past Participle");
		hashMap.put(PartsOfSpeech.VTBP1, "Verb To Be First Person Singular Present");
		hashMap.put(PartsOfSpeech.VTBP, "Verb To Be Non-Third Person Singular Present");
		hashMap.put(PartsOfSpeech.VTBZ, "Verb To Be Third Person Singular Present");
		
		hashMap.put(PartsOfSpeech.GRAMMAR_TERM, "Grammar Term");
		
	}
	
	public String explainedTaggedSentence(TaggedSentence sentence){
		StringBuffer result = new StringBuffer();
		for (TaggedTerm term : sentence.getTaggedTerms()) {
			PartsOfSpeech pos = term.getPartOfSpeech();
			String posValue = this.hashMap.get(pos);
			result.append(term.getTermString());
			result.append(" ");
			result.append("("+posValue+") ");
			
		}
		return result.toString();
	}
	
	public String explainTaggedTerm(PartsOfSpeech pos){
		return this.hashMap.get(pos);
	}
	
	public String explainTaggedTerm(String pos){
		String result = "";
		try{
			PartsOfSpeech partsOfSpeech = PartsOfSpeech.valueOf(pos);
			 result = this.hashMap.get(partsOfSpeech);
		}
		catch(IllegalArgumentException e){
			result = pos;
			return result;
		}
		
		return result;
	}
}
