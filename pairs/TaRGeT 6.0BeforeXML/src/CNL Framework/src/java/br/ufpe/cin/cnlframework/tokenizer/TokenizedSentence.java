/*
 * @(#)TokenizedSentence.java
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

import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;

/**
 * This class represents a tokenized sentence. It stores the sentence start
 * and end character in the text.
 * @author 
 */
public class TokenizedSentence {
    
    private TestCaseTextType testCaseTextType;
    
	private int startInText;

	private int endInText;

	private String actualSentence;
	
	/**
	 * Class constructor.
	 * @param actualSentence the actual sentence
	 * @param start the index of the sentence start character
	 * @param end the index of the sentence end character
	 */
	public TokenizedSentence(String actualSentence, int start, int end) {
		this.actualSentence = actualSentence;
		this.startInText = start;
		this.endInText = end;
	}
	
	/**
	 * 
	 * @return the index of the sentence end character
	 */
	public int getEndInText() {
		return endInText;
	}
	
	/**
	 * 
	 * @return start the index of the sentence start character
	 */
	public int getStartInText() {
		return startInText;
	}
	
	/**
	 * 
	 * @return the actual sentence
	 */
	public String getActualSentence() {
		return actualSentence;
	}

	
	/**
	 * Returns a string representing a tokenized sentence.
	 */
	public String toString() {
		return "[" + this.startInText + "," + this.endInText + "] - "
				+ this.actualSentence;
	}
	
	/**
     * Gets the testCaseTextType value.
     *
     * @return Returns the testCaseTextType.
     */
    public TestCaseTextType getTestCaseTextType()
    {
        return testCaseTextType;
    }

    /**
     * Sets the testCaseTextType value.
     *
     * @param testCaseTextType The testCaseTextType to set.
     */
    public void setTestCaseTextType(TestCaseTextType testCaseTextType)
    {
        this.testCaseTextType = testCaseTextType;
    }

}
