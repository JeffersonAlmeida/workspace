/*
 * @(#)AbstractTCGAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Apr 10, 2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.tcg.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * 
 * <pre>
 * CLASS:
 * An abstract action that contains a selection provider 
 * This is the base class for all actions with selection provider.  
 * 
 * </pre>
 *
 */
public abstract class AbstractTCGAction extends Action implements ISelectionChangedListener
{

    /**
     * The selection provider of this action
     */
    protected ISelectionProvider selectionProvider;
    
    /**
     * Creates an action without a selection provider.
     *  
     * @param id The action id.
     * @param text The string used as the text for the action 
     * @param tooltipText The tool tip text for this action.
     */
    protected AbstractTCGAction(String id, String text, String tooltipText)
    {
        this(id, text, tooltipText, null);
    }

    /**
     * 
     * Creates an action that contains a selection provider.
     * 
     * @param id The action id.
     * @param text The string used as the text for the action 
     * @param tooltipText The tool tip text for this action.
     * @param selectionProvider The selection provider for this action.
     */
    protected AbstractTCGAction(String id, String text, String tooltipText,
            ISelectionProvider selectionProvider)
    {
        super(text);
        this.setToolTipText(tooltipText);
        this.setId(id);
        this.selectionProvider = selectionProvider;
        
        if(this.selectionProvider != null)
        {
            this.selectionProvider.addSelectionChangedListener(this);
        }
    }

    /**
     * Runs this action.
     */
    public final void run()
    {
        ISelection selection = StructuredSelection.EMPTY;
    
        if(this.selectionProvider != null)
        {
           selection = this.selectionProvider.getSelection();
        }
        this.run(selection);
    }
    
    /**
     * Runs this action.
     * Each action implementation must define the steps needed to carry out this action.
     * @param selection The current selection.
     */
    public abstract void run(ISelection selection);
}


