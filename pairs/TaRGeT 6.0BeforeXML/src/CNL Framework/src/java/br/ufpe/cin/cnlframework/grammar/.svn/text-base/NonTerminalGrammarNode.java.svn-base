/*
 * @(#)NonTerminalGrammarNode.java
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
import java.util.List;
import java.util.Set;

import br.ufpe.cin.cnlframework.vocabulary.TrieWord;

/**
 * This class represents a non terminal node of the grammar. It extends the <code>GrammarNode</code>
 * class by adding the attribute <code>productions</code>, which stores all available productions
 * for that non-terminal node. Each grammar production is represented by a sequence of grammar
 * nodes.
 */
public class NonTerminalGrammarNode extends GrammarNode
{

    /** The list of available productions */
    private List<List<GrammarNode>> productions;

    /**
     * The constructor that builds a non-terminal grammar node. It requires the node name and the
     * list of available productions.
     * 
     * @param name The grammar node name.
     * @param children The list of available productions.
     */
    public NonTerminalGrammarNode(String name, List<List<GrammarNode>> children)
    {
        super(name);

        this.productions = children;
    }

    /**
     * Get method for the <code>productions</code> attribute. It must return the list itself, not
     * a copy.
     * 
     * @return The productions list.
     */
    public List<List<GrammarNode>> getProductions()
    {
        return this.productions;
    }

    /**
     * The method runs all productions and calls the method <code>getTextualNodesAuxiliar</code>
     * for each child node.
     */
    
    protected Set<TrieWord> getTextualNodesAuxiliar(Set<GrammarNode> gNodes)
    {
        Set<TrieWord> result = new HashSet<TrieWord>();
        gNodes.add(this);

        for (List<GrammarNode> production : this.productions)
        {
            for (GrammarNode gNode : production)
            {
                if (!gNodes.contains(gNode))
                {
                    result.addAll(gNode.getTextualNodesAuxiliar(gNodes));
                }
            }
        }

        return result;
    }
    
    //INSPECT - Lais New Method
    
    public String toString()
    {
        return "Non Terminal Grammar Node " + this.getName();
    }

}
