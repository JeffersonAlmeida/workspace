/*
 * @(#)TextTokenizer.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package com.motorola.btc.research.cnlframework.tokenizer;

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
	private List<TokenizedSentence> extractSentences(String text) {
		List<TokenizedSentence> result = new ArrayList<TokenizedSentence>();

		//accepts 0..n blank spaces + (full stop | exclamation mark | question mark) + 0..n blank spaces
		Pattern sentenceSeparator = Pattern.compile("\\p{Space}*[\\u0021\\u003F\\u002E]+\\p{Space}*");

		Matcher matcher = sentenceSeparator.matcher(text);

		int last = 0;
		String sentence = null;
		while (matcher.find()) {
			sentence = text.substring(last, matcher.start());
			if (!sentence.trim().equals("")) {
				// String processedSentence = this.processSentence(sentence);

				TokenizedSentence tSentence = new TokenizedSentence(sentence,
						last, matcher.start());

				result.add(tSentence);
			}
			last = matcher.end();
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
