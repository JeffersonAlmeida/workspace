/*
 * @(#)LexiconBase.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.motorola.btc.research.cnlframework.exceptions.RepositoryException;
import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;
import com.motorola.btc.research.cnlframework.vocabulary.TrieWord;
import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;
import com.motorola.btc.research.cnlframework.vocabulary.terms.VerbTerm;
import com.motorola.btc.research.cnlframework.vocabulary.trie.TrieVocabulary;

/**
 * This class represents the lexicon base.
 *  
 * @author Dante Gama Torres 
 */
public class LexiconBase
{

    private ILexiconRepository respository;

    private TrieVocabulary<TrieWord> trieVocabulary;

    /**
     * Class constructor
     * @param repository the lexicon repository
     */
    public LexiconBase(ILexiconRepository repository)
    {

        this.respository = repository;

        this.mountTrie();
    }
    
    /**
     * This method is responsible to mount the trie vocabulary.
     */
    private void mountTrie()
    {
        List<LexicalEntry> lEntries = new ArrayList<LexicalEntry>();
        try
        {
            lEntries.addAll(this.respository.getNouns());
            lEntries.addAll(this.respository.getVerbs());
            lEntries.addAll(this.respository.getDeterminers());
            lEntries.addAll(this.respository.getQualifiers());
            lEntries.addAll(this.respository.getPrepositions());
            lEntries.addAll(this.respository.getParticles());
            lEntries.addAll(this.respository.getCardinals());
        }
        catch (RepositoryException re)
        {
            // TODO
        }
        lEntries.add(VerbTerm.VERB_TO_BE);
        this.trieVocabulary = new TrieVocabulary<TrieWord>();
        for (LexicalEntry lEntry : lEntries)
        {
            for (PartsOfSpeech pos : lEntry.getAvailablePOSTags())
            {
                String str = lEntry.getTerm(pos);
                TrieWord tWord = new TrieWord(str, lEntry, pos);
                this.trieVocabulary.addWord(str.toCharArray(), tWord);
            }
        }
    }
    
    /**
     * 
     * @return the trie vocabulary
     */
    public TrieVocabulary<TrieWord> getTrieVocabulary()
    {

        return this.trieVocabulary;
    }
    
    /**
     * Adds a lexical entry to the lexicon repository.
     * @param lexicalEntry the entry to be added.
     * @throws RepositoryException
     */
    public void addLexicalEntry(LexicalEntry lexicalEntry) throws RepositoryException
    {
        this.respository.addLexicalEntry(lexicalEntry);

        for (TrieWord tWord : this.getTrieWordFromLexicalEntry(lexicalEntry))
        {
            this.trieVocabulary.addWord(tWord.getWord().toCharArray(), tWord);
        }
    }
    
    /**
     * This method returns the intersection between a given term and the trie vocabulary.
     * @param term the term to be analyzed
     * @return a set that represents the intersection
     */
    public Set<LexicalEntry> getLexicalEntryIntersection(LexicalEntry term)
    {
        Set<LexicalEntry> result = new HashSet<LexicalEntry>();

        for (PartsOfSpeech pos : term.getAvailablePOSTags())
        {
            String termStr = term.getTerm(pos);

            Set<TrieWord> trieWordSet = this.trieVocabulary.getWords(termStr.toCharArray());
            if (trieWordSet != null)
            {
                for (TrieWord tWord : trieWordSet)
                {
                    result.add(tWord.getLexicalEntry());
                }
            }
        }

        return result;
    }
    
    /**
     * Returns a collection containing the trie words of the lexical entry.
     * @param lexicalEntry the entry to be analyzed.
     * @return
     */
    private Collection<TrieWord> getTrieWordFromLexicalEntry(LexicalEntry lexicalEntry)
    {
        Collection<TrieWord> result = new HashSet<TrieWord>();
        for (PartsOfSpeech pos : lexicalEntry.getAvailablePOSTags())
        {
            result.add(new TrieWord(lexicalEntry.getTerm(pos), lexicalEntry, pos));
        }
        return result;
    }

    /*
     * This method was added by tnd783 in order to attend Prof. Flavia's requests. Not in
     * the original CNL Framework code.
     */
    public List<VerbTerm> getVerbs() throws RepositoryException
    {
        return this.respository.getVerbs();
    }

}