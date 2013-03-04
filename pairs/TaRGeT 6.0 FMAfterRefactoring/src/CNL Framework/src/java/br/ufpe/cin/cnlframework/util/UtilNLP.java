/*
 * @(#)UtilNLP.java
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
package br.ufpe.cin.cnlframework.util;

import br.ufpe.cin.cnlframework.vocabulary.terms.VerbTerm;

/**
 * This class provides some useful methods to treat the lexicon terms.
 * @author jrm687
 *
 */
public class UtilNLP
{

    public static final String beInfinitive = "be";

    public static final String bePresentFisrtSingular = "am";

    public static final String bePresentSingular = "is";

    public static final String bePresentPlural = "are";

    public static final String bePastSingular = "was";

    public static final String bePastPlural = "were";

    public static final String beGerund = "being";

    public static final String bePastParticiple = "been";

    public static final String ARTICLE_INDEFINITE_A = "a";

    public static final String ARTICLE_INDEFINITE_AN = "an";

    public static final String ARTICLE_DEFINITE = "the";

    public static final String TENSE_PAST = "past";

    public static final String TENSE_INFINITIVE = "infinitive";

    public static final String TENSE_PRESENT = "present";

    public static final String TENSE_PRESENT_PERFECT = "present perfect";

    public static final String VOICE_ACTIVE = "active";

    public static final String VOICE_PASSIVE = "passive";

    public static final String NUMBER_SINGULAR = "singular";

    public static final String NUMBER_PLURAL = "plural";

    /**
     * It returns the right indefinite article (<i>a</i> or <i>an</i>) according to the word
     * passed as parameter.
     * 
     * @param word The word to be analyzed.
     * @return The indefinite article <i>a</i> or <i>an</i>.
     */
    public static String getIndefiniteArticle(String word)
    {

        String result = ARTICLE_INDEFINITE_A;

        if (word.length() > 2)
        {
            char first = word.toLowerCase().charAt(0);
            // char second = word.toLowerCase().charAt(1);
            // char third = word.toLowerCase().charAt(2);
            if (UtilNLP.isVowel(first))
            {
                result = ARTICLE_INDEFINITE_AN;
            }/*
                 * else if (first == 's' && !UtilNLP.isVowel(second) && (UtilNLP.isVowel(third) ||
                 * third == 'y')) { result = OutputArticle.ARTICLE_INDEFINITE_AN; }
                 */
        }
        return result;
    }

    /**
     * Verifies if a character is a vowel
     * 
     * @param character The character to be verified.
     * @return True if <code>character</code> is a vowel.
     */
    public static boolean isVowel(char character)
    {
        return character == 'a' || character == 'e' || character == 'i' || character == 'o'
                || character == 'u';
    }

    /**
     * It returns the plural form of an term passed as parameter.
     * 
     * @param term The lexical term.
     * @return The plural form term.
     */
    public static String getPluralForm(String term)
    {

        String result = term + "s";

        if (term.length() > 2)
        {
            if (term.charAt(term.length() - 1) == 'y' && !isVowel(term.charAt(term.length() - 2)))
            {
                // study, mercy
                result = term.substring(0, term.length() - 1) + "ies";
            }
            else if (term.endsWith("ss") || term.endsWith("ch")
            // watch, wish, class
                    || term.endsWith("sh"))
            {
                result = term + "es";
            } /*
                 * else if (term.endsWith("on")) { // criterion, phenomenon result =
                 * term.substring(0, term.length() - 2) + "a"; }
                 */
            else if (term.endsWith("f"))
            { // loaf, half, wolf
                result = term.substring(0, term.length() - 1) + "ves";
            }
            else if (term.endsWith("o"))
            { // potato, tomato
                result = term + "es";
            }
            else if (term.endsWith("fe"))
            { // knife, life, wife
                result = term.substring(0, term.length() - 2) + "ves";
            }
            else if (term.endsWith("us"))
            { // cactus, nucleus
                result = term.substring(0, term.length() - 2) + "i";
            }
            else if (term.endsWith("s"))
            { // cactus, nucleus
                result = term;
            }
        }
        return result;
    }

    /**
     * Generates the gerund form of a verb.
     * 
     * @param str The verb whose gerund will be returned
     * @return The gerund form
     */
    public static String getGerund(String str)
    {
        String result = null;

        char first = str.charAt(str.length() - 1);

        if (first == 'e')
        {
            result = str.substring(0, str.length() - 1);
        }
        else if (isConsonantVowelConsonant(str))
        {
            result = str + first;
        }
        else
        {
            result = str;
        }

        return result + "ing";
    }
    
    /**
     * This method analyzes if the the word ends in consonant-vowel-consonant.
     * @param word the word to be analyzed
     * @return the analysis result
     */
    private static boolean isConsonantVowelConsonant(String word)
    {
        boolean result = false;
        
        if(word.length() > 2)
        {
            char first = word.charAt(word.length() - 1);
            char second = word.charAt(word.length() - 2);
            char third = word.charAt(word.length() - 3);
            
            result = !isVowel(third) && isVowel(second) && !isVowel(first) && first != 'y' && first == 'w';
        }
        
        return result;
    }
    
    /**
     * This method returns the past tense of a given verb.
     * @param infinitive the given verb
     * @return the verb past tense
     */
    public static String getPast(String infinitive)
    {
        String result = infinitive; 
        
        char first = infinitive.charAt(infinitive.length() - 1);
        char second = infinitive.charAt(infinitive.length() - 2);
        
        if(isConsonantVowelConsonant(infinitive))
        {
           result += first;  
        } else if(first == 'y' && !isVowel(second))
        {
            result = result.substring(0, result.length() - 1) + "i";
        } else if(first == 'e')
        {
            result = result.substring(0, result.length() - 1);
        }
        
        return result + "ed";
    }
    
    /**
     * This method returns the past participle tense of a given verb.
     * @param infinitive the given verb
     * @return the verb past participle tense
     */
    public static String getPastParticiple(String infinitive)
    {
        return getPast(infinitive);
    }

    /**
     * Generates the third person form of a verb.
     * 
     * @param str The verb whose singular form will be returned
     * @return The third person form
     */
    public static String getThirdPerson(String term)
    {
        String result = term + "s";

        if (term.length() > 2)
        {
            if (term.charAt(term.length() - 1) == 'y' && !isVowel(term.charAt(term.length() - 2)))
            {
                // study, mercy
                result = term.substring(0, term.length() - 1) + "ies";
            }
            else if (term.endsWith("ss") || term.endsWith("ch")
            // watch, wish, harass
                    || term.endsWith("sh"))
            {
                result = term + "es";
            }
        }
        return result;
    }

    /**
     * Generates a verb form specified by the parameters.
     * 
     * @param verb The input verb.
     * @param voice The voice of the output verb formation.
     * @param tense The tense of the output verb formation.
     * @param number The number inflection of the output verb formation.
     * @return A string containing the verb formation.
     */
    public static String getVerbForm(VerbTerm verb, String voice, String tense, String number)
    {
        String result = null;

        if (voice == VOICE_PASSIVE)
        {
            String passiveVoiceModal[][] = new String[][] { { bePresentSingular, bePresentPlural },
                    { bePastSingular, bePastPlural } };
            int i, j;

            if (tense == TENSE_PAST)
            {
                i = 1;
            }
            else
            {
                i = 0;
            }

            if (number == NUMBER_PLURAL)
            {
                j = 1;
            }
            else
            {
                j = 0;
            }

            result = passiveVoiceModal[i][j] + " " + verb.getParticiple();

        }
        else
        {

            if (tense == TENSE_PAST)
            {
                if (verb.getTerm().equals(beInfinitive))
                {
                    if (number == NUMBER_PLURAL)
                    {
                        result = bePastPlural;
                    }
                    else
                    {
                        result = bePastSingular;
                    }
                }
                else
                {
                    result = verb.getPast();
                }
            }
            else if (tense == TENSE_PRESENT)
            {
                if (verb.getTerm().equals(beInfinitive))
                { // special treatment for to be verb
                    if (number == NUMBER_PLURAL)
                    {
                        result = bePresentPlural;
                    }
                    else
                    {
                        result = bePresentSingular;
                    }
                }
                else if (number == NUMBER_PLURAL)
                { // if it is a normal verb (not to be)
                    result = verb.getTerm();
                }
                else
                {
                    result = verb.getThirdPerson();
                }
            }
            else if (tense == TENSE_PRESENT_PERFECT)
            {
                if (number == NUMBER_PLURAL)
                {
                    result = "have " + verb.getParticiple();
                }
                else
                {
                    result = "has " + verb.getParticiple();
                }
            }
            else
            {
                result = verb.getTerm();
            }
        }

        return result;
    }
}
