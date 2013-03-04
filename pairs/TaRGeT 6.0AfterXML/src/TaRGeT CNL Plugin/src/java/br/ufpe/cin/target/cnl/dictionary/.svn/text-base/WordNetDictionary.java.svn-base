/*
 * @(#)WordNetDictionary.java
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
 * lmn3      24/09/2009                  Initial creation.
 */
package br.ufpe.cin.target.cnl.dictionary;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

/**
 * This class interacts with the WordNet Dictionary through the MIT Java WordNet Interface (Copyright
 * (c) 2007-2008 Massachusetts Institute of Technology.) It retrieves synonyms of a term and
 * verifies if a term that the user is trying to add in the CNL Lexicon is correct syntactically and
 * semantically. COLABORATORS: This class uses the MIT Java WordNet Interface (Copyright (c)
 * 2007-2008 Massachusetts Institute of Technology).
 * 
 * It implements the main operations to interact with the CNL Plugin. 
 */
public class WordNetDictionary implements ICNLDictionary
{
    private static final String DICTIONARY_FILES = "resources/bases/dict";

    // Instance of WordNet Dictionary
    private IDictionary dictionary;

    /**
     * Class constructor.
     */
    public WordNetDictionary()
    {
        try
        {
            URL url = new URL("file", null, DICTIONARY_FILES);

            // construct the dictionary object and open it
            this.dictionary = new Dictionary(url);
            this.dictionary.open();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }
    
    /*
     * (non-Javadoc)
     * @br.ufpe.cin.target.cnl.dictionary.ICNLDictionary#getSynonyms(String word)
     * 
     */
    public List<CNLSynonym> getSynonyms(String word)
    {
        List<CNLSynonym> result = new ArrayList<CNLSynonym>();

        // looks up the sense of the word
        IIndexWord nounIndexWord = this.dictionary.getIndexWord(word, POS.NOUN);
        IIndexWord verbIndexWord = this.dictionary.getIndexWord(word, POS.VERB);
        IIndexWord adjectiveIndexWord = this.dictionary.getIndexWord(word, POS.ADJECTIVE);
        IIndexWord adverbIndexWord = this.dictionary.getIndexWord(word, POS.ADVERB);
        
        List<String> synonyms = new ArrayList<String>();
        
        if (nounIndexWord != null)
        {
            synonyms = this.getSynonyms(nounIndexWord);                
            result.add(new CNLSynonym(word, PartsOfSpeech.NN, synonyms));            
        }
        
        if (verbIndexWord != null)
        {
            synonyms = this.getSynonyms(verbIndexWord);                
            result.add(new CNLSynonym(word, PartsOfSpeech.VB, synonyms)); 
        }
        
        if (adjectiveIndexWord != null)
        {
            synonyms = this.getSynonyms(adjectiveIndexWord);                
            result.add(new CNLSynonym(word, PartsOfSpeech.JJ, synonyms)); 
        }
        
        if (adverbIndexWord != null)
        {
            synonyms = this.getSynonyms(adverbIndexWord);                
            result.add(new CNLSynonym(word, PartsOfSpeech.ADV, synonyms)); 
        }

        return result;
    }
    
    /**
     * Returns a list of synonyms from the indexWord
     * @param indexWord the indexWord to be treated
     * @return a list of synonyms from the indexWord
     */
    private List<String> getSynonyms(IIndexWord indexWord){
        
        List<IWordID> wordIDs = indexWord.getWordIDs();
        
        List<String> synonyms = new ArrayList<String>();
        
        for (IWordID wordID : wordIDs)
        {
            IWord dictWord = this.dictionary.getWord(wordID);
            ISynset synset = dictWord.getSynset();

            // iterates over words associated with the synset
            for (IWord w : synset.getWords())
            {
                if(!synonyms.contains(w.getLemma()))
                {
                    synonyms.add(w.getLemma().replaceAll("_", " "));
                }
            }
        }
        
        return synonyms;
    }

    /*
     * (non-Javadoc)
     * @br.ufpe.cin.target.cnl.dictionary.ICNLDictionary#isValidWord(LexicalEntry lexicalEntry)
     * 
     */
    public boolean isValidWord(LexicalEntry lexicalEntry)
    {

        try
        {
            POS pos = this.convertCNLPartsOfSpeechToPOS(lexicalEntry.getAvailablePOSTags());
            
            IIndexWord indexWord = this.dictionary.getIndexWord(lexicalEntry.getTerm(this.convertPOSToCNLPartsOfSpeech(pos)), pos);

            if (indexWord != null)
            {
                return true;
            }

            else
            {
                return false;
            }
        }
        //if the PartsOfSpeech is not a valid WordNet POS, it is not possible to verify if the term in invalid.
        catch (InvalidPartsOfSpeechException e)
        {
           return true;
        }

        
    }

    /**
     * Converts a CNL <code>PartsOfSpeech</code> object to a WordNet <code>POS</code>
     * 
     * @param set the set of CNL <code>PartsOfSpeech</code> objects
     * @return a WordNet <code>POS</code> object
     * @throws InvalidPartsOfSpeechException 
     */
    public POS convertCNLPartsOfSpeechToPOS(Set<PartsOfSpeech> set) throws InvalidPartsOfSpeechException
    {
        if (set.contains(PartsOfSpeech.NN))
        {
            return POS.NOUN;
        }
        else if (set.contains(PartsOfSpeech.ADV))
        {
            return POS.ADVERB;
        }
        else if (set.contains(PartsOfSpeech.JJ))
        {
            return POS.ADJECTIVE;
        }
        else if (set.contains(PartsOfSpeech.VB))
        {
            return POS.VERB;
        }
        else
        {
            throw new InvalidPartsOfSpeechException(
                    "The PartsOfSpeech is not a valid WordNet POS. Valid POS are Verb, Noun, Adjective and Adverb");
        }

    }
    
    /**
     * 
     * Converts a WordNet <code>POS</code> object to a CNL <code>PartsOfSpeech</code> object.
     * @param pos the object to be converted
     * @return a CNL <code>PartsOfSpeech</code> object
     * @throws InvalidPartsOfSpeechException
     */
    public PartsOfSpeech convertPOSToCNLPartsOfSpeech(POS pos) throws InvalidPartsOfSpeechException{
        if (pos.equals(POS.ADJECTIVE))
        {
            return PartsOfSpeech.JJ;
        }

        else if (pos.equals(POS.ADVERB))
        {
            return PartsOfSpeech.ADV;
        }
        
        else if (pos.equals(POS.NOUN))
        {
            return PartsOfSpeech.NN;
        }
        
        else if (pos.equals(POS.VERB))
        {
            return PartsOfSpeech.VB;
        }
        else{
            throw new InvalidPartsOfSpeechException(
            "The PartsOfSpeech is not a valid WordNet POS. Valid POS are Verb, Noun, Adjective and Adverb");
        }
    }

    /**
     * Main method for tests.
     */
    public static void main(String[] args)
    {
        try
        {
            // construct the URL to the Wordnet dictionary directory
            URL url = new URL("file", null, "lib/dict");

            // construct the dictionary object and open it
            IDictionary dict = new Dictionary(url);
            dict.open();

            // look up first sense of the word "dog"
            IIndexWord idxWord = dict.getIndexWord("work", POS.NOUN);
            IWordID wordID = idxWord.getWordIDs().get(0);
            IWord word = dict.getWord(wordID);
            System.out.println("Id = " + wordID);
            System.out.println("Lemma = " + word.getLemma());
            System.out.println("Gloss = " + word.getSynset().getGloss());
            System.out.println("Sense " + word.getSenseKey().getLexicalID());

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }
}
