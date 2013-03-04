/*
 * @(#)ICNLDictionary.java
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
package br.ufpe.cin.target.cnl.dictionary;

import java.util.List;

import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

/**
 * This interface contains the main methods that a dictionary should provide to be used by the CNL Plugin.
 */
public interface ICNLDictionary
{
    /**
     * Returns a list containing all synonyms found of the given word.
     * 
     * @param word the word to processes
     * @return a list containing all synonyms.
     */
    public List<CNLSynonym> getSynonyms(String word);
    
    /**
     * Verifies if the given word exists in the dictionary with the same Parts Of Speech
     * classification.
     * 
     * @param word the word to be validated
     * @param partsOfSpeech the Parts Of Speech classification
     * @return the result of the validation
     */
    public boolean isValidWord(LexicalEntry lexicalEntry);
    
}
