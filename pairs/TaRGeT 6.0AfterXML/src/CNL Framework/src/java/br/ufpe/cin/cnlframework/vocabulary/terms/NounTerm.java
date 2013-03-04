/*
 * @(#)NounTerm.java
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
 * This class represents a lexical entry of the type Noun Term.
 * @author
 */
public class NounTerm extends LexicalEntry {

	/**
	 * Creates a noun term with its singular and plural forms.
	 * @param termSingular the term singular form
	 * @param termPlural the term plural form
	 */
	public NounTerm(String termSingular, String termPlural)
	{
		super();
		this.termsMap.put(PartsOfSpeech.NN, termSingular.toLowerCase());
		this.termsMap.put(PartsOfSpeech.NNS, termPlural.toLowerCase());
	}
	
	/**
	 * 
	 * @return the term singular form.
	 */
	public String getSingular()
	{
		return this.getTerm(PartsOfSpeech.NN);	
	}
	
	/**
	 * 
	 * @return the term plural form.
	 */
	public String getPlural()
	{
		return this.getTerm(PartsOfSpeech.NNS);
	}

	/**
	 * 
	 * @return the noun string format. 
	 */
    //INSPECT: Felype - Mudei o toString.
	
    public String toString()
    {
        String result = "";

        for (PartsOfSpeech pos : termsMap.keySet())
        {
            if (pos.equals(PartsOfSpeech.NN))
            {
                result += this.termsMap.get(pos);

                result += " - Noun\n";
            }
        }

        return result;
    }
    
    
    public int compareTo(LexicalEntry otherLex)
    {
        int result = 0;

        if (this.termsMap != null && !this.termsMap.isEmpty() && otherLex != null
                && !otherLex.termsMap.isEmpty())
        {
            String thisString = this.termsMap.get(PartsOfSpeech.NN);
            String otherString = otherLex.termsMap.get(PartsOfSpeech.NN);

            if(thisString != null)
            {
                result = thisString.compareTo(otherString);
            }
        }

        return result;
    }
}
