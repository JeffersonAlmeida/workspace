/*
 * @(#)GrammarNode.java
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
package br.ufpe.cin.cnlframework.grammar;

import java.util.HashSet;
import java.util.Set;

import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.TrieWord;

/**
 * Represents a terminal node in the CNL grammar. The class only stores the name of the node. It is
 * also subclassed by <code>NonTerminalGrammarNode</code>.
 */
public class GrammarNode
{
    /** The name of the grammar node */
    private String name;

    /**
     * The constructor that creates a <code>GrammarNode</code> object.
     * 
     * @param name The grammar node name.
     */
    public GrammarNode(String name)
    {
        this.name = name;
    }

    /**
     * Get method for the <code>name</code> attribute.
     * 
     * @return The grammar node name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * This method is responsible for gathering all textual nodes that are children of the current
     * node. This method should be rewritten by all sub-classes.
     * 
     * @param gNodes The grammar nodes that were already processed. This set must be kept to avoid
     * infinite loop, that is caused by recursive productions. This way, before calling the
     * <code>getTextualNodesAuxiliar</code> method again, it is necessary to check if the grammar
     * node was already processed.
     * @return The set of <code>TrieWord</code> objects of all grammar terms.
     * @see PartsOfSpeech Grammar term part-of-speech tag.
     */
    protected Set<TrieWord> getTextualNodesAuxiliar(Set<GrammarNode> gNodes)
    {
        gNodes.add(this);
        return new HashSet<TrieWord>();
    }

    /**
     * The method that should be called to collect all grammar terms. The method calls the
     * <code>getTextualNodesAuxiliar</code> method.
     * 
     * @return The set of <code>TrieWord</code> objects of all grammar terms.
     */
    public Set<TrieWord> getTextualNodes()
    {
        return this.getTextualNodesAuxiliar(new HashSet<GrammarNode>());
    }

    /**
     * Equals method for the grammar node. It only compares the grammar node.
     */
    
    public boolean equals(Object obj)
    {
        boolean equals = false;

        if (obj instanceof GrammarNode)
        {
            GrammarNode grammarNode = (GrammarNode) obj;
            equals = this.name == grammarNode.name;
        }

        return equals;
    }
    
    //INSPECT Lais - new method
    
    public String toString()
    {
        return "Grammar Node " + this.getName();
    }
}
