/*
 * @(#)TreeObject.java
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
 * wra050   Jul 12, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 */
package br.ufpe.cin.target.pm.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

/**
 * <pre>
 * CLASS:
 * Class used to represent a child node, used to display items in a tree.
 * 
 * RESPONSIBILITIES:
 * Implements a child item to be used in tree views.
 * </pre>
 */
public class TreeObject implements IAdaptable
{
    /**
     * The object that this tree object represents. (E.g. a feature or a use case)
     */
    private Object value;

    /**
     * The parent of this tree object
     */
    private TreeObject parent;

    /**
     * The children of this node.
     */
    private List<TreeObject> children;

    /**
     * Initializes the value of the tree object with <code>value</code> and also initializes the
     * children collection.
     * 
     * @param value The object that this tree object represents.
     */
    public TreeObject(Object value)
    {
        this.value = value;
        this.children = new ArrayList<TreeObject>();
    }

    /**
     * Add a TreeObject as a child node.
     * 
     * @param child The child node to be added as a child of the current node.
     */
    public void addChild(TreeObject child)
    {
        child.parent = this;
        children.add(child);
    }

    /**
     * Remove a child object from this node.
     * 
     * @param child The child node to be removed.
     */
    public void removeChild(TreeObject child)
    {
        children.remove(child);
    }

    /**
     * Returns the children of the current node.
     * 
     * @return The children nodes.
     */
    public TreeObject[] getChildren()
    {
        return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
    }

    /**
     * Checks if the current node has any child.
     * 
     * @return <b>True</b> if the node has children or <b>false</b> if there are no children.
     */
    public boolean hasChildren()
    {
        return children.size() > 0;
    }

    /**
     * Returns a null adapter.
     * 
     * @param adapter The null adapter.
     */
    public Object getAdapter(Class adapter)
    {
        return null;
    }

    /**
     * Get method for <code>value</code> attribute.
     * 
     * @return The node value.
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Get method for <code>parent</code> attribute.
     * 
     * @return The parent value.
     */
    public TreeObject getParent()
    {
        return parent;
    }

    /**
     * Return the value toString.
     * 
     * @see java.lang.Object#toString()
     */
    
    public String toString()
    {
        return this.value.toString();
    }

}
