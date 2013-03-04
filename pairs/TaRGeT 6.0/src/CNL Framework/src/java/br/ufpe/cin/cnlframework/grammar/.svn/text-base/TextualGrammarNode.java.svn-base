/*
 * @(#)TextualGrammarNode.java
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
 * wdt022    May 22, 2008   LIBqq41824   Initial creation.
 */
package br.ufpe.cin.cnlframework.grammar;

import java.util.HashSet;
import java.util.Set;

import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.TrieWord;

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
