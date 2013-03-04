/*
 * @(#)ParserTreeNode.java
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

import java.util.List;

import br.ufpe.cin.cnlframework.postagger.TaggedTerm;

/**
 * This class represents an node of a syntactic tree generated by the parser. It stores the start
 * and end character in the sentence, indicating the limits of the node in the processed sentence.
 * It also keeps the grammar node that is related to the parser tree node, the list of tagged terms
 * that are represented by the parser node, and, obviously, the list of children parser tree nodes.
 */
public class ParserTreeNode
{

    /** The sentence index in which this parser node starts */
    private int start;

    /** The sentence index in which this parser node ends */
    private int end;

    /** The grammar node instance related to this parser tree node */
    private GrammarNode grammarNode;

    /** The list of tagged terms that represents this parser tree node */
    private List<TaggedTerm> parsedTerms;

    /** The list of all children parser tree nodes */
    private List<ParserTreeNode> childrenNodes;

    /**
     * Build a parser tree node. All class attributes are initialized by this constructor.
     * 
     * @param start The start index of the parser tree node in the processed sentence.
     * @param end The end index of the parser tree node in the processed sentence.
     * @param grammarNode The grammar node related to this parser tree node.
     * @param terms The tagged terms that compose the parser tree node.
     * @param childrenNodes The list of children nodes.
     */
    public ParserTreeNode(int start, int end, GrammarNode grammarNode, List<TaggedTerm> terms,
            List<ParserTreeNode> childrenNodes)
    {
        this.start = start;
        this.end = end;

        this.parsedTerms = terms;
        this.childrenNodes = childrenNodes;
        this.grammarNode = grammarNode;
    }

    /**
     * Get method for the <code>end</code> attribute.
     * 
     * @return The <code>end</code> attribute value.
     */
    public int getEnd()
    {
        return this.end;
    }

    /**
     * Get method for the <code>parsedTerms</code> attribute. Since the attribute is a list, if
     * the returned list is changed, the <code>parsedTerms</code> attribute is also changed.
     * 
     * @return The <code>parsedTerms</code> attribute value.
     */
    public List<TaggedTerm> getParsedTerms()
    {
        return this.parsedTerms;
    }

    /**
     * Get method for the <code>start</code> attribute.
     * 
     * @return The <code>start</code> attribute value.
     */
    public int getStart()
    {
        return this.start;
    }

    /**
     * Get method for the <code>childrenNodes</code> attribute. Since the attribute is a list, if
     * the returned list is changed, the <code>childrenNodes</code> attribute is also changed.
     * 
     * @return The <code>childrenNodes</code> attribute value.
     */
    public List<ParserTreeNode> getChildrenNodes()
    {
        return this.childrenNodes;
    }

    /**
     * Verifies if the current node has no children.
     * 
     * @return <i>true</i> if the <code>childrenNodes</code> attribute is null, <i>false</i>
     * otherwise.
     */
    public boolean isLeaf()
    {
        return this.childrenNodes == null;
    }

    /**
     * In case the parser node is related to a non-terminal node, this method returns the related
     * grammar node name concatenated with the start and end index in the processed sentence. If the
     * related grammar node is a terminal node, it returns the related tagged term.
     */
    
    public String toString()
    {
        String result = "";
        if (this.grammarNode instanceof NonTerminalGrammarNode)
        {
            result = this.grammarNode.getName() + "{" + start + "," + end + "}";
            //INSPECT Lais - added
            for (ParserTreeNode treeNode : this.childrenNodes)
            {
                result += " "+treeNode.toString();
            }
        }
        else
        {
            result = this.getParsedTerms().get(0).toString();
        }
        return result;
    }

    /**
     * Get method for the <code>grammarNode</code> attribute.
     * 
     * @return The <code>grammarNode</code> attribute value.
     */
    public GrammarNode getGrammarNode()
    {
        return this.grammarNode;
    }

}
