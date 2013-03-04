/*
 * @(#)TokenizedText.java
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
package br.ufpe.cin.cnlframework.tokenizer;

import java.util.List;

/**
 * This class represents a tokenized text. It contains the original 
 * text and the list containing the text sentences.
 * @author
 *
 */
public class TokenizedText {
	
	
	private String text;
	
	private List<TokenizedSentence> sentenceList;
	
	/**
	 * Class constructor.
	 * @param text the original text
	 * @param sentenceList the list containing the text sentences
	 */
	public TokenizedText(String text, List<TokenizedSentence> sentenceList)
	{
		this.text = text;
		this.sentenceList = sentenceList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		return this.text;
	}
	
	/**
	 * 
	 * @return the text sentences list
	 */
	public List<TokenizedSentence> getSentenceList() {
		return sentenceList;
	}
}
