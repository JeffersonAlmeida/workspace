/*
 * @(#)TextTokenizer.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents is responsible to separate the text sentences.
 * @author
 *
 */
public class TextTokenizer {
    
    /**
     * This class represents an occurrence of a quotation pair in the text.
     * It stores the indexes of left and right quotations. 
     *
     */
    //INSPECT - Lais Added
    class DoubleQuotationPair
    {
        // the index of the left quotation on the text
        int indexLeftQuotation;

        // the index of the right quotation in the text
        int indexRightQuotation;

        /**
         * Class Constructor.
         * 
         * @param indexLeftQuotation the index of the left quotation on the text
         * @param indexRightQuotation the index of the right quotation in the text
         */
        public DoubleQuotationPair(int indexLeftQuotation, int indexRightQuotation)
        {
            this.indexLeftQuotation = indexLeftQuotation;
            this.indexRightQuotation = indexRightQuotation;
        }

        /**
         * Gets the indexLeftQuotation value.
         * 
         * @return Returns the indexLeftQuotation.
         */
        public int getIndexLeftQuotation()
        {
            return indexLeftQuotation;
        }

        /**
         * Sets the indexLeftQuotation value.
         * 
         * @param indexLeftQuotation The indexLeftQuotation to set.
         */
        public void setIndexLeftQuotation(int indexLeftQuotation)
        {
            this.indexLeftQuotation = indexLeftQuotation;
        }

        /**
         * Gets the indexRightQuotation value.
         * 
         * @return Returns the indexRightQuotation.
         */
        public int getIndexRightQuotation()
        {
            return indexRightQuotation;
        }

        /**
         * Sets the indexRightQuotation value.
         * 
         * @param indexRightQuotation The indexRightQuotation to set.
         */
        public void setIndexRightQuotation(int indexRightQuotation)
        {
            this.indexRightQuotation = indexRightQuotation;
        }
    }

	private static TextTokenizer instance;
	
	/**
	 * 
	 * @return an instance of TextTokenizer
	 */
	public static TextTokenizer getInstance() {
		if (instance == null) {
			instance = new TextTokenizer();
		}
		return instance;
	}
	
	/**
	 * Returns a TokenizedText object containing the list of sentences from the text. 
	 * @param text the text to be tokenized
	 * @return a TokenizedText object containing the list of sentences from the text. 
	 */
	public TokenizedText tokenizeText(String text) {

		List<TokenizedSentence> sentences = this.extractSentences(text);
		TokenizedText result = new TokenizedText(text, sentences);

		return result;
	}
	
	/**
	 * This method returns the list of sentences from the text
	 * @param text the text to be processed
	 * @return the list of sentences from the text
	 */
	//INSPECT Lais - inclusao do tratamento para os casos: ".NET" "33.21" "Studio 3.0 deluxe", etc 
	private List<TokenizedSentence> extractSentences(String text) {
		List<TokenizedSentence> result = new ArrayList<TokenizedSentence>();
		
		//INSPECT Lais - start of inserted code
		String word = text;
		word = word.replaceAll("\\u201C", "\"");
		word = word.replaceAll("\\u201D", "\"");
		
		List<DoubleQuotationPair> doubleQuotationPairs = new ArrayList<DoubleQuotationPair>();
		
		//check occurrences of double quotations in the text
		int leftQuotation = -1;
        for (int i = 0; i < word.length(); i++)
        {
            if (word.charAt(i) == '\"')
            {
                if (leftQuotation == -1)
                {
                    leftQuotation = i;
                }
                else if (leftQuotation > -1)
                {
                    DoubleQuotationPair doubleQuotationPair = new DoubleQuotationPair(
                            leftQuotation, i);
                    doubleQuotationPairs.add(doubleQuotationPair);
                    leftQuotation = -1;
                }
            }
        }
		//INSPECT Lais - end of inserted code.
		

		//accepts 0..n blank spaces + (full stop | exclamation mark | question mark) + 0..n blank spaces
		Pattern sentenceSeparator = Pattern.compile("\\p{Space}*[\\u0021\\u003F\\u002E]+\\p{Space}*");

		Matcher matcher = sentenceSeparator.matcher(text);

		int last = 0;
		String sentence = null;
		boolean sentenceSeparatorBetweenQuotations;
		
		while (matcher.find()) {
		    
		    //INSPECT Lais - start of inserted code
		    sentenceSeparatorBetweenQuotations = false;
		    
		    //checks if the occurrences of the pattern is between double quotations. if yes, this occurrence shall be ignored.
		    for (DoubleQuotationPair doubleQuotationPair : doubleQuotationPairs)
            {
                if (doubleQuotationPair.getIndexLeftQuotation() < matcher.start()
                        && doubleQuotationPair.getIndexRightQuotation() > matcher.start())
                {
                    sentenceSeparatorBetweenQuotations = true;
                }
            }
		    
		    if(!sentenceSeparatorBetweenQuotations){
		    //INSPECT Lais - end of inserted code.
		        
    			sentence = text.substring(last, matcher.start());
    			if (!sentence.trim().equals("")) {
    				// String processedSentence = this.processSentence(sentence);
    
    				TokenizedSentence tSentence = new TokenizedSentence(sentence,
    						last, matcher.start());
    
    				result.add(tSentence);
    			}
    			last = matcher.end();
		    }
		}

		sentence = text.substring(last, text.length());
		if (!sentence.trim().equals("")) {
			//String processedSentence = this.processSentence(sentence);

			TokenizedSentence tSentence = new TokenizedSentence(sentence, last,
					text.length());

			result.add(tSentence);
		}

		return result;
	}

	/*
	 * private String processSentence(String sentence) { // removing any
	 * apostrophes (' " etc.) sentence = sentence.trim().replaceAll(
	 * "[\\u2019\\u2018\\u2032\\u201D\\u201C\\u2033\\u2022\\u2027]+", "");
	 * sentence = sentence.replaceAll("\\p{Punct}+", " "); sentence =
	 * sentence.replaceAll("\\p{Space}+", " "); sentence =
	 * sentence.toLowerCase();
	 * 
	 * return sentence; }
	 */
	
	/**
	 * Main for tests.
	 */
	public static void main(String[] args) {

		TextTokenizer tt = TextTokenizer.getInstance();

		TokenizedText tText = tt
				.tokenizeText("Create a message. Send the Message to Dante.");

		System.out.println(tText);
	}
}
