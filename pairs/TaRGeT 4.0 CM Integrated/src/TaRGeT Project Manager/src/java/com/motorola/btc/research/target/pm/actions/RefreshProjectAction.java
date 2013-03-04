/*
 * @(#)RefreshProjectAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783    31/07/2008     LIBqq51204   Initial creation.
 * dwvm83   Oct 14, 2008				 Added method selectionChanged
 */
package com.motorola.btc.research.target.pm.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import com.motorola.btc.research.target.common.actions.TargetAction;
import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;

/**
 * <pre>
 * CLASS:
 * Action used to refresh the project.
 * 
 * RESPONSIBILITIES:
 * Refresh the current project.
 * 
 * USAGE:
 * One Action can be associated with events in GUI.
 * </pre>
 */

public class RefreshProjectAction extends TargetAction
{

    
    protected void hook()
    {
        if (ProjectManagerController.getInstance().hasOpenedProject())
        {
            GUIManager.getInstance().refreshProject(super.window.getShell());
        }
    }
    
    
    /**
     * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged()
     */
    public void selectionChanged(IAction action, ISelection selection) {
    	super.selectionChanged(action, selection);
    	//If the project is not closed
    	if (ProjectManagerController.getInstance().getCurrentProject() != null)
    		action.setEnabled(true);
    }

}
