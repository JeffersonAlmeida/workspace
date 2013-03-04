/*
 * @(#)ILexiconRepository.java
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
package com.motorola.btc.research.cnlframework.vocabulary.base;

import java.util.List;

import com.motorola.btc.research.cnlframework.exceptions.RepositoryException;
import com.motorola.btc.research.cnlframework.vocabulary.terms.AdjectiveTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.CardinalTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.DeterminerTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;
import com.motorola.btc.research.cnlframework.vocabulary.terms.NounTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.PrepositionTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.VerbTerm;


/**
 * This interface represents a lexicon repository.
 * 
 * @author Dante Gama Torres
 *
 */
public interface ILexiconRepository {
	
	/**
	 * This method returns a list containing the verbs from the lexicon.
	 * @return a list containing the verbs from the lexicon
	 * @throws RepositoryException
	 */
	public List<VerbTerm> getVerbs() throws RepositoryException;
	
	/**
	 * This method returns a list containing the nouns from the lexicon.
	 * @return  list containing the nouns from the lexicon.
	 * @throws RepositoryException
	 */
	public List<NounTerm> getNouns() throws RepositoryException;
	
	/**
	 * This method returns a list containing the determiners from the lexicon.
	 * @return a list containing the determiners from the lexicon.
	 * @throws RepositoryException
	 */
	public List<DeterminerTerm> getDeterminers() throws RepositoryException;
	
	/**
	 * This method returns a list containing the qualifiers from the lexicon.
	 * @return a list containing the qualifiers from the lexicon.
	 * @throws RepositoryException
	 */
	public List<AdjectiveTerm> getQualifiers() throws RepositoryException;
	
	/**
	 * This method returns a list containing the prepositions from the lexicon.
	 * @return a list containing the qualifiers from the lexicon.
	 * @throws RepositoryException
	 */
	public List<PrepositionTerm> getPrepositions() throws RepositoryException;
	
	/**
	 * This method returns a list containing the particles from the lexicon.
	 * @return a list containing the particles from the lexicon.
	 * @throws RepositoryException
	 */
	public List<LexicalEntry> getParticles() throws RepositoryException;
	
	/**
	 * This method returns a list containing the cardinals from the lexicon.
	 * @return a list containing the cardinals from the lexicon.
	 * @throws RepositoryException
	 */
	public List<CardinalTerm> getCardinals() throws RepositoryException;
	
	/**
	 * This method adds a lexical entry to the lexicon.
	 * @param lexicalEntry the entry to be added
	 * @throws RepositoryException
	 */
	public void addLexicalEntry(LexicalEntry lexicalEntry) throws RepositoryException;
	
	
	
	/*public void addLexicalTerm(LexicalTerm lexicalTerm) throws RepositoryException;
	public List<ModifierTerm> getModifiers() throws RepositoryException;
	public List<VerbTerm> getVerbs() throws RepositoryException;
	public List<NounTerm> getNouns() throws RepositoryException;
	public ModifierTerm getModifierTerm(String term) throws RepositoryException;
	public VerbTerm getVerbTerm(String term) throws RepositoryException;
	public NounTerm getNounTerm(String term) throws RepositoryException;
	//public NounTerm getNounTermRegardlessPlural(String term) throws RepositoryException;
	public boolean existsModifierTerm(String term) throws RepositoryException;
	public boolean existsVerbTerm(String term) throws RepositoryException;
	public boolean existsNounTerm(String term) throws RepositoryException;
	
	/**
	 * This method searches for NounTerms by its model string.  
	 * 
	 * @param modelCode The model string used in the search.
	 * @return An ArrayList of NounTerm instances.
	 *
	public List<NounTerm> getNounTermByModelCode(String modelCode)  throws RepositoryException;
	
	/**
	 * This method searches for ModifierTerms by its model string.  
	 * 
	 * @param modelCode The model string used in the search.
	 * @return An ArrayList of ModifierTerm instances.
	 *
	public List<ModifierTerm> getModifierTermByModelCode(String modelCode)  throws RepositoryException;
	*/
}
