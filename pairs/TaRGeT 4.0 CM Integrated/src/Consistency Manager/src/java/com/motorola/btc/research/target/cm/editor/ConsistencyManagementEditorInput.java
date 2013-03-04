/*
 * @(#)ConsistencyManagementEditorInput.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.motorola.btc.research.target.cm.tcsimilarity.logic.Comparison;

/**
 * <pre>
 * CLASS:
 * Represents an editor input for the Consistency Management.
 * 
 * RESPONSIBILITIES:
 * Provides results of comparison for Consistency Management form pages.  
 * 
 * </pre>
 */
//INSPECT
public class ConsistencyManagementEditorInput implements IEditorInput
{

    /**
     * The comparison object, representing all the comparison results.
     */
    private Comparison comparison;

    /**
     * Creates an editor input for the Consistency Management. 
     * 
     * @param comparison
     */
    public ConsistencyManagementEditorInput(Comparison comparison)
    {
        this.comparison = comparison;
    }

    /**
     * Gets the comparison object, with all the comparison results.
     * @return
     */
    public Comparison getComparison()
    {
        return comparison;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#exists()
     */
    
    public boolean exists()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     */
    
    public ImageDescriptor getImageDescriptor()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    
    public String getName()
    {
        return "";
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    
    public IPersistableElement getPersistable()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    
    public String getToolTipText()
    {
        return "";
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class adapter)
    {
        return null;
    }

}
