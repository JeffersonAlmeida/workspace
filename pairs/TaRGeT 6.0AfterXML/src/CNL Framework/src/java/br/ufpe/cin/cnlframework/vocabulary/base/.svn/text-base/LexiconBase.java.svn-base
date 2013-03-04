/*
 * @(#)LexiconBase.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package br.ufpe.cin.cnlframework.vocabulary.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.cnlframework.exceptions.RepositoryException;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.TrieWord;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdjectiveTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdverbTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.CardinalTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.DeterminerTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;
import br.ufpe.cin.cnlframework.vocabulary.terms.NounTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.PrepositionTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.VerbTerm;
import br.ufpe.cin.cnlframework.vocabulary.trie.TrieVocabulary;

/**
 * This class represents the lexicon base.
 *  
 * @author Dante Gama Torres 
 */
public class LexiconBase
{

    private ILexiconRepository repository;

    private TrieVocabulary<TrieWord> trieVocabulary;

    /**
     * Class constructor
     * @param repository the lexicon repository
     */
    public LexiconBase(ILexiconRepository repository)
    {

        this.repository = repository;

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
            lEntries.addAll(this.repository.getNouns());
            lEntries.addAll(this.repository.getVerbs());
            lEntries.addAll(this.repository.getDeterminers());
            lEntries.addAll(this.repository.getAdjectives());
            lEntries.addAll(this.repository.getPrepositions());
            lEntries.addAll(this.repository.getParticles());
            lEntries.addAll(this.repository.getCardinals());
            lEntries.addAll(this.repository.getAdverbs());
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
        this.repository.addLexicalEntry(lexicalEntry);

        for (TrieWord tWord : this.getTrieWordFromLexicalEntry(lexicalEntry))
        {
            this.trieVocabulary.addWord(tWord.getWord().toCharArray(), tWord);
        }
    }
    
    //INSPECT Lais - New Method
    public void removeLexicalEntry(LexicalEntry lexicalEntry) throws RepositoryException
    {
        this.repository.removeWord(lexicalEntry);   
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
    
    public ArrayList<LexicalEntry> getVocabulary() throws RepositoryException
    {
        return this.repository.getAllVocabulary();
    }

    /**
     * Gets the respository value.
     *
     * @return Returns the respository.
     */
    //INSPECT - Lais new method.
    public ILexiconRepository getRepository()
    {
        return repository;
    }
    
    /*
     * This method was added by tnd783 in order to attend Prof. Flavia's requests. Not in
     * the original CNL Framework code.
     */
    public List<VerbTerm> getVerbs() throws RepositoryException
    {
        return this.repository.getVerbs();
    }
    
    public List<NounTerm> getNouns() throws RepositoryException
    {
        return this.repository.getNouns();
    }
    
    public List<DeterminerTerm> getDeterminers() throws RepositoryException
    {
        return this.repository.getDeterminers();
    }
    
    public List<AdjectiveTerm> getAdjectives() throws RepositoryException
    {
        return this.repository.getAdjectives();
    }
    
    public List<PrepositionTerm> getPrepositions() throws RepositoryException
    {
        return this.repository.getPrepositions();
    }
    
    public List<LexicalEntry> getParticles() throws RepositoryException
    {
        return this.repository.getParticles();
    }
    
    public List<CardinalTerm> getCardinals() throws RepositoryException
    {
        return this.repository.getCardinals();
    }
    
    public List<AdverbTerm> getAdverbs() throws RepositoryException
    {
        return this.repository.getAdverbs();
    }
}
