/*
 * @(#)CNLParser.java
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
package com.motorola.btc.research.cnlframework.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.motorola.btc.research.cnlframework.postagger.TaggedSentence;
import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;

/**
 * This class is responsible for parsing a tagged sentence, given a grammar. The
 * tagged sentence is a sentence whose terms are related to Part-Of-Speech tags.
 * As result of the parsing operation, the class returns a collection of parser
 * trees, which are the abstract syntax trees of the sentence. <br>
 * If class returns zero parse trees, this means that the input tagged sentence
 * has no match with the grammar. If the class returns more than one parse tree,
 * this means that the tagged sentence is ambiguous according to the given
 * grammar.
 * <li>
 */
public class CNLParser {
	/**
	 * The cache of all grammar node that was matched (successfully or not) in a
	 * given tagged sentence. This cache is a hash map that links a
	 * <code>CacheObject</code> to the list of all possible matches for a
	 * given grammar node.
	 */
	private HashMap<CacheObject, List<ParserTreeNode>> cache;

	private GrammarNode parentNode = null;

	//TODO changed
	private List<ParsingPossibility> parsingPossibilities;

	/**
	 * It is the key object in the cache hash map. It represents the grammar
	 * node that was matched (successfully or not) and the position of the match
	 * in the tagged sentence.
	 */
	private class CacheObject {
		/** The match position in the tagged sentence */
		int pos;

		/** The grammar node the */
		GrammarNode grammarNode;

		/**
		 * Builds a cache object.
		 * 
		 * @param pos
		 *            The position where the match started.
		 * @param grammarNode
		 *            The grammar node that was matched.
		 */
		public CacheObject(int pos, GrammarNode grammarNode) {
			this.pos = pos;
			this.grammarNode = grammarNode;
		}

		/**
		 * The equals method verifies if both
		 * <code>position<code> and <code>grammarNode</code> attributes are equal.
		 */

		public boolean equals(Object obj) {
			boolean equals = false;

			if (obj instanceof CacheObject) {
				CacheObject cacheObject = (CacheObject) obj;
				equals = this.pos == cacheObject.pos
						&& this.grammarNode.equals(cacheObject.grammarNode);
			}

			return equals;
		}

		/**
		 * Hash code for the cache object. It is the hash code of the grammar
		 * node name plus the <code>position</code> attribute.
		 */

		public int hashCode() {
			return grammarNode.getName().hashCode() + pos;
		}
	}

	public List<ParsingPossibility> getParsingPossibilities() {
		return parsingPossibilities;
	}

	/**
	 * This method is responsible for starting the parsing processing.
	 * 
	 * @param taggedSentence
	 *            The tagged sentence.
	 * @param grammar
	 *            The grammar that will be matched to the sentence.
	 * @return A collection with all parse trees found.
	 */
	public List<ParserTreeNode> parseTaggedSentence(
			TaggedSentence taggedSentence, GrammarNode grammar) {

		List<ParserTreeNode> result = new ArrayList<ParserTreeNode>();
		this.cache = new HashMap<CacheObject, List<ParserTreeNode>>();
		
		this.parsingPossibilities = new ArrayList<ParsingPossibility>();

		/*
		 * this iteration verifies if all returned parse trees are exact matches
		 * of the tagged sentence. For example, if sentence A,B,C,D exactly
		 * matches with the grammar, the sentence A,B,C would also be returned
		 * as a match.
		 */
		for (ParserTreeNode node : parseTaggedSentence(taggedSentence, 0,
				grammar)) {
			// Verify if the sentence was fully matched.
			if (node.getEnd() == taggedSentence.getSize() - 1) {
				result.add(node);
			}
		}
		
		if(!result.isEmpty()){
			parsingPossibilities.clear();
		}
		return result;
	}

	/**
	 * This method really starts the parse processing. The processing starts
	 * from the tag informed in the position passed as parameter. The method
	 * verifies if the grammar element is terminal or non-terminal, and triggers
	 * different processing for each type of element.
	 * 
	 * @param childListFirstNode
	 * 
	 * @param taggedSentence
	 *            The tagged sentence.
	 * @param pos
	 *            The position of the tagged sentence in which the processing
	 *            will be performed.
	 * @param subGrammar
	 *            The grammar element that will be matched to the tagged
	 *            sentence.
	 * @return The list of parse trees that were matched to the tagged sentence.
	 */
	private List<ParserTreeNode> parseTaggedSentence(
			TaggedSentence taggedSentence, int pos, GrammarNode subGrammar) {

		List<ParserTreeNode> result = new ArrayList<ParserTreeNode>();

		// Verify if the tagged was fully processed
		if (pos < taggedSentence.getSize()) {

			// Verify if the grammar element is a terminal or non-terminal node
			if (subGrammar instanceof NonTerminalGrammarNode) {
				if (subGrammar.getName().equals("ImperativeSentence")
						|| subGrammar.getName().equals(
								"NegativeImperativeSentence")
						|| subGrammar.getName().equals(
								"SubordinatedImperativeSentence")) {

					this.parentNode = subGrammar;

				}

				NonTerminalGrammarNode nonTerminal = (NonTerminalGrammarNode) subGrammar;
				// Method that deals with non-terminal node from the grammar
				result = this.dealWithNonTerminalGrammarNode(taggedSentence,
						pos, nonTerminal);
			} else {
				// Verifies if the next element in the tagged sentence is a
				// GRAMMAR_TERM
				if (taggedSentence.getTaggedTerm(pos).getPartOfSpeech().equals(
						PartsOfSpeech.GRAMMAR_TERM)
						&& subGrammar instanceof TextualGrammarNode
						&& subGrammar.getName().equals(
								taggedSentence.getTaggedTerm(pos)
										.getTermString())) {
					result.add(new ParserTreeNode(pos, pos, subGrammar,
							taggedSentence.getTaggedTerms().subList(pos,
									pos + 1), null));
				} // Verifies if the tagged term matches with the grammar
				else if (taggedSentence.getTaggedTerm(pos).getPartOfSpeech()
						.name().equals(subGrammar.getName()))

				{
					result.add(new ParserTreeNode(pos, pos, subGrammar,
							taggedSentence.getTaggedTerms().subList(pos,
									pos + 1), null));
					
				//a parsing error has occurred	
				} else {
					ParsingPossibility parsingPossibility = new ParsingPossibility(
							taggedSentence, pos, subGrammar, parentNode);
					
					if(parsingPossibilities.isEmpty()){
						parsingPossibilities.add(parsingPossibility);
					}
					//only gets the parsing possibilities that have the greatest pos value 
					else if(pos >= parsingPossibilities.get(0).getErrorPosition()){
						if(pos > parsingPossibilities.get(0).getErrorPosition()){
							parsingPossibilities.clear();
						}
						parsingPossibilities.add(parsingPossibility);
					}
					
					
				}
			}
		}

		return result;
	}

	/**
	 * This is an auxiliary method that deals with non-terminal grammar
	 * elements. The method tries to match the non-terminal element passed as
	 * parameter to the next element in the tagged sentence.
	 * 
	 * @param taggedSentence
	 *            The tagged sentence that is under evaluation.
	 * @param pos
	 *            The position of the element in the tagged sentence that is
	 *            under evaluation.
	 * @param nonTerminal
	 *            The non-terminal element to be matched to the tagged sentence.
	 * @return The collection of all parse trees that were matched to the
	 *         sentence.
	 */
	private List<ParserTreeNode> dealWithNonTerminalGrammarNode(
			TaggedSentence taggedSentence, int pos,
			NonTerminalGrammarNode nonTerminal) {

		List<ParserTreeNode> result = new ArrayList<ParserTreeNode>();

		List<List<GrammarNode>> productions = nonTerminal.getProductions();
		// Each available production of the non-terminal element is evaluated
		for (List<GrammarNode> childrenList : productions) {

			List<List<ParserTreeNode>> expandedProductions = new ArrayList<List<ParserTreeNode>>();

			// Each production (represented by the childrenList variable) is
			// expanded. The result is
			// stored in the expandedProductions variable, passed as parameter.
			this.expandProductionChildren(expandedProductions,
					new Stack<ParserTreeNode>(), childrenList, 0,
					taggedSentence, pos);

			// Each result of the expanded productions is mapped to the
			// nonTerminal element that
			// comprehends the productions.
			for (List<ParserTreeNode> expandedProduction : expandedProductions) {
				int start = expandedProduction.get(0).getStart();
				int end = expandedProduction.get(expandedProduction.size() - 1)
						.getEnd();

				// Each expanded production is mapped into a ParserTreeNode that
				// represents the
				// parent non terminal element.
				result
						.add(new ParserTreeNode(start, end, nonTerminal,
								taggedSentence.getTaggedTerms().subList(start,
										end + 1), expandedProduction));
			}
		}

		return result;
	}

	/**
	 * It receives as input a list of <code>GrammarNode</code> (i.e. the
	 * productions) and returns as result all possible matches for those
	 * productions. It may return more that one match for the same production,
	 * since there may be ambiguity in the language.
	 * 
	 * @param expandedProductions
	 *            This parameter works as a return variable. It shall be passed
	 *            empty to the method, which will fill it.
	 * @param expandedNodes
	 *            An auxiliary stack that keeps the the production while it is
	 *            expanded.
	 * @param childrenList
	 *            The production that is being expanded.
	 * @param childPos
	 *            The next element in the children list that will be expanded.
	 * @param taggedSentence
	 *            The tagged sentence that is being parsed.
	 * @param nextPos
	 *            The next element to be evaluated in tagged sentence.
	 */
	private void expandProductionChildren(
			List<List<ParserTreeNode>> expandedProductions,
			Stack<ParserTreeNode> expandedNodes,
			List<GrammarNode> childrenList, int childPos,
			TaggedSentence taggedSentence, int nextPos) {

		if (childrenList.size() == childPos) {
			// If all production was expanded, then a new match is found.
			expandedProductions
					.add(new ArrayList<ParserTreeNode>(expandedNodes));
		} else {
			// try to match each element of the childrenList to the current
			// position in the tagged
			// sentence. The whole childrenList is matched recursively.
			List<ParserTreeNode> possibleNodes = this.getPossibleNodes(
					childrenList.get(childPos), taggedSentence, nextPos);
			for (ParserTreeNode node : possibleNodes) {
				// each possible match is pushed into the stack, and the next
				// element in the
				// production is expanded.
				expandedNodes.push(node);
				expandProductionChildren(expandedProductions, expandedNodes,
						childrenList, childPos + 1, taggedSentence, node
								.getEnd() + 1);
				expandedNodes.pop();
			}
		}
	}

	/**
	 * This method tries to match a grammar node and the tagged sentence. First,
	 * the method verifies if the matching was already tried. If it was not
	 * tried, the it calls the method <code>parseTaggedSentence</code> in
	 * order to make the match.
	 * 
	 * @param grammarNode
	 *            The grammar node to be matched.
	 * @param taggedSentence
	 *            The tagged sentence to which the grammar node will be matched.
	 * @param nextPos
	 *            The position in the tagged sentence where the matching must
	 *            start.
	 * @return All possible matches for that grammar node.
	 */
	private List<ParserTreeNode> getPossibleNodes(GrammarNode grammarNode,
			TaggedSentence taggedSentence, int nextPos) {
		CacheObject cacheObject = new CacheObject(nextPos, grammarNode);
		// verify if the grammar node was already matched.
		List<ParserTreeNode> possibleNodes = this.cache.get(cacheObject);
		if (possibleNodes == null) {
			// if it was not tried to match, try it now!
			possibleNodes = this.parseTaggedSentence(taggedSentence, nextPos,
					grammarNode);
			// and store the result in the cache.
			this.cache.put(cacheObject, possibleNodes);
		}
		return possibleNodes;
	}

}