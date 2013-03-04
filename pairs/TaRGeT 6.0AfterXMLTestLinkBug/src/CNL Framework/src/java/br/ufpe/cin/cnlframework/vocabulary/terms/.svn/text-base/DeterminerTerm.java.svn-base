/*
 * @(#)DeterminerTerm.java
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
 * This class represents a lexical entry of the type Determiner Term.
 * @author
 *
 */
public class DeterminerTerm extends LexicalEntry {
	
	/**
	 * Class constructor.
	 * Creates a determiner term with the term value passed as parameter. 
	 * @param term the term value
	 */
	public DeterminerTerm(String term) {
		super(PartsOfSpeech.DT, term);
	}

	/**
	 * 
	 * @return the determiner term value
	 */
    public String toString()
    {
        String result = "";

        for (PartsOfSpeech pos : termsMap.keySet())
        {
            if (pos.equals(PartsOfSpeech.DT))
            {
                result += this.termsMap.get(pos);

                result += " - Determiner\n";
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
            String thisString = this.termsMap.get(PartsOfSpeech.DT);
            String otherString = otherLex.termsMap.get(PartsOfSpeech.DT);

            if(thisString != null)
            {
                result = thisString.compareTo(otherString);
            }
        }
        
        return result;
    }
}
