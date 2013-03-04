/*
 * @(#)TaggedTerm.java
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
package br.ufpe.cin.cnlframework.postagger;

import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.TrieWord;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

/**
 * This class represents a tagged term, that means a term with its start and end positions.
 * @author 
 *
 */
public class TaggedTerm {
	
	private TrieWord term;
	
	private int start;
	
	private int end;
	
	/**
	 * Class constructor.
	 * @param term a trie word term
	 * @param start the term start position
	 * @param end the term end position
	 */
	public TaggedTerm(TrieWord term, int start, int end)
	{
		this.end = end;
		this.start = start;
		this.term = term;
	}

	/**
	 * 
	 * @return the term end position.
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * 
	 * @return the term start position.
	 */
	public int getStart() {
		return start;
	}
    
	/**
	 * 
	 * @return the term word.
	 */
    public String getTermString()
    {
        return term.getWord();
    }

    /**
     * 
     * @return the term corresponding lexical entry
     */
	public LexicalEntry getTerm() {
		return term.getLexicalEntry();
	}
	
	/**
	 * 
	 * @return the term parts of speech tag
	 */
	public PartsOfSpeech getPartOfSpeech() {
		return term.getPartOfSpeech();
	}

	//INSPECT Lais - changed.
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
    public String toString()
    {
        return this.getTermString() + " [" + POSUtil.getInstance().explainTaggedTerm(getPartOfSpeech())+ "]";
    }
	
	
}
