/*
 * @(#)TextPreprocessor.java
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
 * wxx###   04/09/2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.cnlframework.preprocessor;

import java.util.List;

import br.ufpe.cin.cnlframework.exceptions.RepositoryException;
import br.ufpe.cin.cnlframework.vocabulary.base.LexiconBase;
import br.ufpe.cin.cnlframework.vocabulary.terms.VerbTerm;

/*
 * This class was added by tnd783 in order to attend Prof. Flavia's requests. Not in the original
 * CNL Framework code.
 */

/**
 * This class is responsible to make some specific process to the text before the parsing phase.
 */
public class TextPreprocessor
{

    private List<VerbTerm> verbs;
    
    /**
     * Class constructor.
     * @param lexiconBase the base lexicon
     */
    public TextPreprocessor(LexiconBase lexiconBase)
    {
        try
        {
            this.verbs = lexiconBase.getVerbs();
        }
        catch (RepositoryException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * This method is responsible to perform the text process.
     * Basically, it goes through the text searching for sentences 
     * that could be divided into two or more simple sentences
     * @param text the text to be processed
     * @return the processed text
     */
    public String preprocess(String text)
    {
        String[] split = text.split("\\p{Space}+");

        String result = "";

        for (int i = 0; i < split.length; i = i + 1)
        {
            boolean isAnd = this.isAnd(split, i);
            boolean isAndThen = this.isAndThen(split, i);
            boolean isTryTo = isTryTo(split, i);

            if (isAnd)
            {
                result = addPeriod(result);
                split[i + 1] = this.capitalize(split[i + 1]);
            }
            else if (isAndThen)
            {
                result = addPeriod(result);
                split[i + 2] = this.capitalize(split[i + 2]);
                i = i + 1;
            }
            else if (isTryTo)
            {
                split[i + 2] = this.capitalize(split[i + 2]);
                i = i + 1;
            }
            else
            {
                result = result + " " + split[i];
            }
        }
        result = result.substring(1);

        return result;
    }
    
    /**
     * This method is responsible to add a full stop to the given text.
     * @param text the text to be modified
     * @return the given text with a full stop in its end.
     */
    private String addPeriod(String text)
    {
        if (text.length() > 0){
            text = text + ".";
        }
        return text;
    }
    
    /**
     * Verifies if the given text is an "and" statement.
     * @param split the text to be analyzed
     * @param i the word index
     * @return the result of the analysis
     */
    private boolean isAnd(String[] split, int i)
    {
        boolean isAnd;
        try
        {
            isAnd = split[i].equalsIgnoreCase("and") && this.isVerb(split[i + 1]);
        }
        catch (Exception e)
        {
            isAnd = false;
        }
        return isAnd;
    }
    
    /**
     * Verifies if the given text is an "and then" statement.
     * @param split the text to be analyzed
     * @param i the word index
     * @return the result of the analysis
     */
    private boolean isAndThen(String[] split, int i)
    {
        boolean isAndThen;
        try
        {
            isAndThen = split[i].equalsIgnoreCase("and") && split[i + 1].equalsIgnoreCase("then")
                    && this.isVerb(split[i + 2]);
        }
        catch (Exception e)
        {
            isAndThen = false;
        }
        return isAndThen;
    }

	/**
	 * Verifies if the given text is a "try to" statement.
     * @param split the text to be analyzed
     * @param i the word index
     * @return the result of the analysis
	 */
    private boolean isTryTo(String[] split, int i)
    {
        boolean isTryTo;
        try
        {
            isTryTo = split[i].equalsIgnoreCase(("try")) && split[i + 1].equalsIgnoreCase("to");
        }
        catch (Exception e)
        {
            isTryTo = false;
        }
        return isTryTo;
    }

    /**
     * Verifies if the given text is a verb.
     * @param word the text to be analyzed
     * @return the result of the analysis
     */
    private boolean isVerb(String word)
    {
        boolean isVerb = false;
        for (VerbTerm verbTerm : this.verbs)
        {
            if (verbTerm.contains(word))
            {
                isVerb = true;
                break;
            }
        }
        return isVerb;
    }
    
    /**
     * This method sets to upper case the word´s first letter.
     * @param word  the word to be changed
     * @return the changed word.
     */
    private String capitalize(String word)
    {
        String firstLetter = String.valueOf(word.toCharArray()[0]);
        String capitalizedWord = firstLetter.toUpperCase() + word.substring(1);
        return capitalizedWord;
    }
}
