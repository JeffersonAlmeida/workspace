/*
 * @(#)InvalidEntryInDictionaryException.java
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
 * lmn3      29/09/2009                  Initial creation.
 */
package br.ufpe.cin.target.cnl.exceptions;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

public class InvalidEntryInDictionaryException extends CNLException
{
    
    /**
     * Field description.
     */
    private static final long serialVersionUID = 1L;
    
    private LexicalEntry lexicalEntry; 
    private PartsOfSpeech pos;
    
    public InvalidEntryInDictionaryException(LexicalEntry lexicalEntry, PartsOfSpeech pos)
    {
        this.lexicalEntry = lexicalEntry;
        this.pos = pos;
    }

    /**
     * Gets the lexicalEntry value.
     *
     * @return Returns the lexicalEntry.
     */
    public LexicalEntry getLexicalEntry()
    {
        return lexicalEntry;
    }

    /**
     * Sets the lexicalEntry value.
     *
     * @param lexicalEntry The lexicalEntry to set.
     */
    public void setLexicalEntry(LexicalEntry lexicalEntry)
    {
        this.lexicalEntry = lexicalEntry;
    }

    /**
     * Gets the pos value.
     *
     * @return Returns the pos.
     */
    public PartsOfSpeech getPos()
    {
        return pos;
    }

    /**
     * Sets the pos value.
     *
     * @param pos The pos to set.
     */
    public void setPos(PartsOfSpeech pos)
    {
        this.pos = pos;
    }
    
    

}
