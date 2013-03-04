package br.ufpe.cin.target.cnl.exceptions;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.cnlframework.tokenizer.TokenizedSentence;

@SuppressWarnings("serial")
/**
 * This class represents an exception thrown when there is no tagged sentence to be processed.
 */
public class NoTaggedSentenceFoundException extends CNLException {

	private TokenizedSentence tokenizedSentence;

	/**
	 * Class constructor
	 * @param tSentence the tokenized action where the error occurred.
	 */
	public NoTaggedSentenceFoundException(TokenizedSentence tSentence) {
		this.tokenizedSentence = tSentence;
	}

	
	/**
	 * Returns the error message.
	 */
	public String getMessage() {

		return "The following sentence contains terms that are not stored in the vocabulary: \""
				+ tokenizedSentence.getActualSentence() + "\"";
	}
	
	/**
	 * 
	 * @return The tokenized action where the error occurred.
	 */
	public TokenizedSentence getTokenizedSentence() {
		return this.tokenizedSentence;
	}
}
