/*
 * @(#)PrepositionTerm.java
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

import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;

/**
 * This class represents a lexical entry of the type Preposition Term.
 * @author
 *
 */
public class PrepositionTerm extends LexicalEntry {
	
	/**
	 * Class constructor.
	 * Creates a preposition term with the term value passed as parameter. 
	 * @param term the term value
	 */
	public PrepositionTerm(String term) {
		super(PartsOfSpeech.PP, term);
	}

	/**
	 * 
	 * @return the preposition string value
	 */
    //INSPECT: Felype - Mudei o toString.
    
    public String toString()
    {
        String result = "";

        for (PartsOfSpeech pos : termsMap.keySet())
        {
            if (pos.equals(PartsOfSpeech.PP))
            {
                result += this.termsMap.get(pos);

                result += " - Preposition\n";
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
            String thisString = this.termsMap.get(PartsOfSpeech.PP);
            String otherString = otherLex.termsMap.get(PartsOfSpeech.PP);

            if(thisString != null)
            {
                result = thisString.compareTo(otherString);
            }
        }
        
        return result;
    }
}
