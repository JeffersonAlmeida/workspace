package br.ufpe.cin.cnlframework.grammar;

import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.cnlframework.postagger.TaggedSentence;

/**
 * This class represents a parsing possibility when there is an error in the parsing process.
 * It contains the sentence that is being analyzed, the position in the sentences of the word
 * that caused the parsing error, the expected grammar node for that position and the initial gramar node 
 * where the parsing process has started.
 * 
 * @author jrm687
 *
 */
public class ParsingPossibility {
	
	private TaggedSentence taggedSentence;

	private int errorPosition;

	private GrammarNode expectedGrammarNode;

	private GrammarNode initialGrammarNode;

	/**
	 * Class Constructor.
	 * @param taggedSentence
	 * @param errorPosition
	 * @param expectedGrammarNode
	 * @param initialGrammarNode
	 */
	public ParsingPossibility(TaggedSentence taggedSentence, int errorPosition,
			GrammarNode expectedGrammarNode, GrammarNode initialGrammarNode) {
		super();
		this.taggedSentence = taggedSentence;
		this.errorPosition = errorPosition;
		this.expectedGrammarNode = expectedGrammarNode;
		this.initialGrammarNode = initialGrammarNode;
	}

	/**
	 * Returns a String corresponding the parsingPossibility.
	 * Ex.: In the position: 1, select Expected: Conjunction. Found: Verb Base Form 
	 * In the Branch: ImperativeSentence
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();
		int errorPosition = this.getErrorPosition();
		result.append("In the position: " + (errorPosition + 1) + ", "
				+ this.getTaggedSentence().getTaggedTerm(errorPosition));
		result.append(". Expected: "
				+ POSUtil.getInstance().explainTaggedTerm(
						this.getExpectedGrammarNode().getName())
				+ ". Found: "
				+ POSUtil.getInstance().explainTaggedTerm(
						this.getTaggedSentence().getTaggedTerm(errorPosition)
								.getPartOfSpeech()));
		if(this.getInitialGrammarNode() != null){
			result.append(". In the Branch: "
			+ this.getInitialGrammarNode().getName());
		}
		result.append(".");

		return result.toString();
	}

	public TaggedSentence getTaggedSentence() {
		return taggedSentence;
	}

	public void setTaggedSentence(TaggedSentence taggedSentence) {
		this.taggedSentence = taggedSentence;
	}

	public int getErrorPosition() {
		return errorPosition;
	}

	public void setErrorPosition(int errorPosition) {
		this.errorPosition = errorPosition;
	}

	public GrammarNode getExpectedGrammarNode() {
		return expectedGrammarNode;
	}

	public void setExpectedGrammarNode(GrammarNode expectedGrammarNode) {
		this.expectedGrammarNode = expectedGrammarNode;
	}

	public GrammarNode getInitialGrammarNode() {
		return initialGrammarNode;
	}

	public void setInitialGrammarNode(GrammarNode initialGrammarNode) {
		this.initialGrammarNode = initialGrammarNode;
	}

}
