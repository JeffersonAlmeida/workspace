/*
 * @(#)TreeViewContentProvider.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *  dhq348        -          LIBkk11577   Initial creation.
 *  dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 */
package com.motorola.btc.research.target.pm.common;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

/**
 * <pre>
 * CLASS:
 * Abstract class that implements an abstract content provider for a tree view.
 * 
 * RESPONSIBILITIES:
 * Handle contents to a tree view GUI component.
 * 
 * USAGE:
 * Extend this class and implement the <code>initialize()</code> and <code>getVSite()</code> 
 * methods. All public methods must not be directly called. The platform 
 * is responsible to do that.  
 * </pre>
 */
public abstract class TreeViewContentProvider implements IStructuredContentProvider,
        ITreeContentProvider
{

    /**
     * The root of all the tree. It is necessary because it is not possible to know previously how
     * many root the trees that will use this content provider will have. So each root has to be
     * added as a child of this superRoor node.
     */
    protected TreeObject superRoot;

    /**
     * Manages any input change.
     * 
     * @param v The viewer
     * @param oldInput The current input.
     * @param newInput The input that will be created.
     */
    public void inputChanged(Viewer v, Object oldInput, Object newInput)
    {
    }

    /**
     * Disposes the current object.
     */
    public void dispose()
    {
    }

    /**
     * Returns all the children of <code>parent</code>.
     * 
     * @param parent The node which children will be returned.
     * @return The array with the children.
     */
    public Object[] getElements(Object parent)
    {
        Object[] result = new Object[0];
        if (parent.equals(getVSite()))
        {
            if (superRoot == null)
            {
                initialize();
            }
            result = getChildren(superRoot);
        }
        else
        {
            result = getChildren(parent);
        }
        return result;
    }

    /**
     * Returns the parent node of <code>child</code>.
     * 
     * @param The child which parent will be returned.
     * @return The parent of <code>child</code>.
     */

    public Object getParent(Object child)
    {
        return ((TreeObject) child).getParent();
    }

    /**
     * Returns all the children of <code>parent</code>.
     * 
     * @param parent The node which children will be returned.
     * @return The array with the children.
     */
    public Object[] getChildren(Object parent)
    {
        return ((TreeObject) parent).getChildren();
    }

    /**
     * Checks if the node <code>parent</code> has at least one child.
     * 
     * @param parent The node which will be verified.
     * @return <b>true</b> if <code>parent</code> has at least one child or <b>false</b>
     * otherwise.
     */

    public boolean hasChildren(Object parent)
    {
        return ((TreeObject) parent).hasChildren();
    }

    /**
     * Gets the value of <code>superRoot</code> attribute.
     * 
     * @return Returns The superRoot value.
     */
    public TreeObject getSuperRoot()
    {
        return superRoot;
    }

    /**
     * Abstract method that initializes the content provider.
     */
    public abstract void initialize();

    /**
     * Abstract method that returns the specific content provider site.
     * 
     * @return The specific content provider site.
     */
    public abstract IViewSite getVSite();
}
