/*
 * @(#)TextualGrammarNode.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 22, 2008   LIBqq41824   Initial creation.
 */
package com.motorola.btc.research.cnlframework.grammar;

import java.util.HashSet;
import java.util.Set;

import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;
import com.motorola.btc.research.cnlframework.vocabulary.TrieWord;

/**
 * Represents a terminal node related to a lexical term defined in the grammar. For example, the
 * grammar below defines the terms 'the', 'a' and 'first' inside the grammar. These grammar nodes
 * are represented in the grammar as <code>TextualGrammarNode</code>.
 * 
 * <pre>
 * NounPhrase = 'the', Noun
 * NounPhrase = 'a', Noun
 * NounPhrase = 'the', 'first' Noun
 * </pre>
 */
public class TextualGrammarNode extends GrammarNode
{
    /** 
     * 
     * Creates a new <code>TextualGrammarNode</code> instance.
     * 
     * @param name The name of the node. It is the same name written in the grammar.
     */
    public TextualGrammarNode(String name)
    {
        super(name);
    }

    /**
     * Returns itself as result.
     */

    protected Set<TrieWord> getTextualNodesAuxiliar(Set<GrammarNode> gNodes)
    {
        Set<TrieWord> result = new HashSet<TrieWord>();

        result.add(new TrieWord(this.getName(), null, PartsOfSpeech.GRAMMAR_TERM));
        gNodes.add(this);

        return result;
    }
}
