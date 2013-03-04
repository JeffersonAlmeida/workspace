/*
 * @(#)LexicalEntry.java
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
package br.ufpe.cin.cnlframework.vocabulary.terms;

import java.util.HashMap;
import java.util.Set;

import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;

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
public class LexicalEntry implements Comparable<LexicalEntry>
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
        this.termsMap.put(pos, term.toLowerCase());
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
    //INSPECT: Felype - Mudei o toString.
    
    public String toString()
    {
        String result = "";

        for (PartsOfSpeech pos : termsMap.keySet())
        {
            result += this.termsMap.get(pos);
            
            if(pos.equals(PartsOfSpeech.CJ))
            {
                result += " - Conjunction\n";
            }
            else
            {
                result += "//" + pos;
            }
        }

        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    
    public int compareTo(LexicalEntry otherLex)
    {
        int result = 0;
        
        if(this.termsMap != null && !this.termsMap.isEmpty() && otherLex != null && !otherLex.termsMap.isEmpty())
        {
            String thisString = this.termsMap.values().iterator().next();
            String otherString = otherLex.termsMap.values().iterator().next();
            
            PartsOfSpeech thisPartsOfSpeech = this.termsMap.keySet().iterator().next();
            PartsOfSpeech otherPartsOfSpeech = otherLex.termsMap.keySet().iterator().next();
            
            result = thisPartsOfSpeech.toString().compareTo(otherPartsOfSpeech.toString());
            
            if(result == 0)
            {
                result = thisString.compareTo(otherString);
            }
        }
        
        return result;
    }

    /**
     * Comment for method.
     * @return
     */
    public HashMap<PartsOfSpeech, String> getTermsMap()
    {
        return this.termsMap;
    }

}
