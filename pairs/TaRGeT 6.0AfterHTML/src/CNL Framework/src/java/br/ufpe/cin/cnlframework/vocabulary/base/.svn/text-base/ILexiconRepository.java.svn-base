/*
 * @(#)ILexiconRepository.java
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
import java.util.List;

import br.ufpe.cin.cnlframework.exceptions.RepositoryException;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdjectiveTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdverbTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.CardinalTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.DeterminerTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;
import br.ufpe.cin.cnlframework.vocabulary.terms.NounTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.PrepositionTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.VerbTerm;


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
	public List<AdjectiveTerm> getAdjectives() throws RepositoryException;
	
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

    /**
     * Comment for method.
     * @return
     * @throws RepositoryException
     */
    public List<AdverbTerm> getAdverbs() throws RepositoryException;

    /**
     * Comment for method.
     * @throws RepositoryException 
     */
    public ArrayList<LexicalEntry> getAllVocabulary() throws RepositoryException;

    /**
     * Comment for method.
     * @param lexicalEntry
     * @throws RepositoryException
     */
    void removeWord(LexicalEntry lexicalEntry) throws RepositoryException;
	
}
