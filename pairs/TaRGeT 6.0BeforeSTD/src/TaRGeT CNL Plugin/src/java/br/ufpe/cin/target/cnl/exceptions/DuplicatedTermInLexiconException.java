/*
 * @(#)DuplicatedTermInLexiconException.java
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
 * wxx###   May 19, 2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.exceptions;

import java.util.Set;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

@SuppressWarnings("serial")
/**
 * This class represents an exception thrown while trying to add a term to the lexicon.
 */
public class DuplicatedTermInLexiconException extends CNLException
{
    private LexicalEntry lEntry;

    private Set<LexicalEntry> duplicatedEntries;

    /**
     * Class constructor.
     * @param lEntry the entry to be added
     * @param duplicated a set with the duplicated entries.
     */
    public DuplicatedTermInLexiconException(LexicalEntry lEntry, Set<LexicalEntry> duplicated)
    {
        this.lEntry = lEntry;
        this.duplicatedEntries = duplicated;
    }

    //INSPECT: Felype - Removido o getMessage.
/*    
    *//**
     * Returns the error message.
     *//*
    public String getMessage()
    {
        String message = "The term that tou are adding:\n";

        for (LexicalEntry dupEntry : this.duplicatedEntries)
        {
            if (dupEntry == VerbTerm.VERB_TO_BE)
            {
                message += "\n\tVerb to Be: " + dupEntry.toString();
            }
            else if (dupEntry.getClass().getCanonicalName().equals(
                    LexicalEntry.class.getCanonicalName()))
            {
                message += "\n\tParticle: " + dupEntry.toString();
            }
            else
            {
                message += "\n\t" + dupEntry.toString();
            }
        }

        return message;
    }*/

    /**
     * 
     * @return the entry to be added
     */
    public LexicalEntry getLEntry()
    {
        return this.lEntry;
    }
    
    /**
     * 
     * @return a set with the duplicated entries.
     */
    public Set<LexicalEntry> getDuplicatedEntries()
    {
        return this.duplicatedEntries;
    }

}
