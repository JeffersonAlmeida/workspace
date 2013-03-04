/*
 * @(#)CNLSynonym.java
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
 * wxx###   29/09/2009    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.dictionary;

import java.util.List;

import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;

public class CNLSynonym
{
    private String word;
    
    private PartsOfSpeech partsOfSpeech;
    
    private List<String> synonyms;

    public CNLSynonym(String word, PartsOfSpeech partsOfSpeech, List<String> synonyms)
    {
        this.word = word;
        this.partsOfSpeech = partsOfSpeech;
        this.synonyms = synonyms;
    }

    /**
     * Gets the word value.
     *
     * @return Returns the word.
     */
    public String getWord()
    {
        return word;
    }

    /**
     * Sets the word value.
     *
     * @param word The word to set.
     */
    public void setWord(String word)
    {
        this.word = word;
    }

    /**
     * Gets the partsOfSpeech value.
     *
     * @return Returns the partsOfSpeech.
     */
    public PartsOfSpeech getPartsOfSpeech()
    {
        return partsOfSpeech;
    }

    /**
     * Sets the partsOfSpeech value.
     *
     * @param partsOfSpeech The partsOfSpeech to set.
     */
    public void setPartsOfSpeech(PartsOfSpeech partsOfSpeech)
    {
        this.partsOfSpeech = partsOfSpeech;
    }

    /**
     * Gets the synonyms value.
     *
     * @return Returns the synonyms.
     */
    public List<String> getSynonyms()
    {
        return synonyms;
    }

    /**
     * Sets the synonyms value.
     *
     * @param synonyms The synonyms to set.
     */
    public void setSynonyms(List<String> synonyms)
    {
        this.synonyms = synonyms;
    }
    
    
    
    
}
