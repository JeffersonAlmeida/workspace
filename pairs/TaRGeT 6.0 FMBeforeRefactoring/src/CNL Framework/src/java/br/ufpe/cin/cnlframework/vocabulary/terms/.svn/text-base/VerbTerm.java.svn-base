/*
 * @(#)VerbTerm.java
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

import br.ufpe.cin.cnlframework.util.UtilNLP;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;

/**
 * This class represents a lexical entry of the type Verb Term.
 * 
 * @author
 */
public class VerbTerm extends LexicalEntry
{
    public static LexicalEntry VERB_TO_BE;
    static
    {
        HashMap<PartsOfSpeech, String> termsMap = new HashMap<PartsOfSpeech, String>();
        termsMap.put(PartsOfSpeech.VTB, UtilNLP.beInfinitive);
        termsMap.put(PartsOfSpeech.VTBDZ, UtilNLP.bePastSingular);
        termsMap.put(PartsOfSpeech.VTBDP, UtilNLP.bePastPlural);
        termsMap.put(PartsOfSpeech.VTBG, UtilNLP.beGerund);
        termsMap.put(PartsOfSpeech.VTBN, UtilNLP.bePastParticiple);
        termsMap.put(PartsOfSpeech.VTBP1, UtilNLP.bePresentFisrtSingular);
        termsMap.put(PartsOfSpeech.VTBP, UtilNLP.bePresentPlural);
        termsMap.put(PartsOfSpeech.VTBZ, UtilNLP.bePresentSingular);
        VERB_TO_BE = new LexicalEntry(termsMap);
    }

    /**
     * Class constructor.
     */
    protected VerbTerm()
    {
        super();
    }

    /**
     * Class constructor. Creates a verb term with the given verb tenses.
     * 
     * @param baseForm the verb base form
     * @param past the verb past form
     * @param pastParticiple the verb participle form
     * @param gerund the verb gerund form
     * @param thirdPersonPresent the verb third person present form.
     * @param nonThirdPersonPresent the verb form in the present and not in third person
     */
    public VerbTerm(String baseForm, String past, String pastParticiple, String gerund,
            String thirdPersonPresent, String nonThirdPersonPresent)
    {

        super();
        this.termsMap.put(PartsOfSpeech.VB, baseForm.toLowerCase());
        this.termsMap.put(PartsOfSpeech.VBD, past.toLowerCase());
        this.termsMap.put(PartsOfSpeech.VBG, gerund.toLowerCase());
        this.termsMap.put(PartsOfSpeech.VBN, pastParticiple.toLowerCase());
        this.termsMap.put(PartsOfSpeech.VBP, nonThirdPersonPresent.toLowerCase());
        this.termsMap.put(PartsOfSpeech.VBZ, thirdPersonPresent.toLowerCase());

    }

    /**
     * @return the verb participle form
     */
    public String getParticiple()
    {
        return this.termsMap.get(PartsOfSpeech.VBN);
    }

    /**
     * @return the verb past form
     */
    public String getPast()
    {
        return this.termsMap.get(PartsOfSpeech.VBD);
    }

    /**
     * @return the verb base form
     */
    public String getTerm()
    {
        return this.termsMap.get(PartsOfSpeech.VB);
    }

    /**
     * @return the verb third person present form.
     */
    //INSPECT Lais Changed VBP por VBZ
    public String getThirdPerson()
    {
        return this.termsMap.get(PartsOfSpeech.VBZ);
    }

    /**
     * @return the verb gerund form
     */
    public String getGerund()
    {
        return this.termsMap.get(PartsOfSpeech.VBG);
    }

    /**
     * Given a noun of a verb, return if the is present in VerbTerm.
     * 
     * @param verb The verb to find.
     * @return if the verb is in VerbTerm.
     */
    public boolean contains(String verb)
    {
        return this.termsMap.values().contains(verb);
    }

    /**
     * @return the verb string value
     */
    //INSPECT: Felype - Mudei o toString.
    
    public String toString()
    {
        String result = "";

        for (PartsOfSpeech pos : termsMap.keySet())
        {
            if (pos.equals(PartsOfSpeech.VB))
            {
                result += this.termsMap.get(pos);

                result += " - Verb\n";
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
            String thisString = this.termsMap.get(PartsOfSpeech.VB);
            String otherString = otherLex.termsMap.get(PartsOfSpeech.VB);

            if(thisString != null)
            {
                result = thisString.compareTo(otherString);
            }
        }

        return result;
    }

}
