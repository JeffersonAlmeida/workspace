/*
 * @(#)RefreshAutomaticallyAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *  tnd783   Aug 05, 2008    LIBqq51204   Initial creation.
 *  dwvm83	 Sep 30, 2008	 LIBqq51204	  Rework after inspection LX302177.
 *  dwvm83   Oct 14, 2008				  Added method selectionChanged
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
 * Action used to switch the refresh automatically preference.
 * 
 * RESPONSIBILITIES:
 * Switches the refresh automatically preference and refreshes the project.
 * 
 * USAGE:
 * One Action can be associated with events in GUI.
 * </pre>
 */

public class RefreshAutomaticallyAction extends TargetAction
{
    

    /**
     * Switches the state of the refresh automatically preference and
     * refreshes the project by reloading the project's progress bar 
     * and refreshing all the views and editors. 
     * 
     */
    protected void hook()
    {
    	if (ProjectManagerController.getInstance().hasOpenedProject()) {
	        GUIManager.getInstance().switchRefreshAutomatically();
	        //If the refresh automatically state changes from true to
	        //false, the project should not be refreshed
	        if (GUIManager.getInstance().isRefreshAutomatically())
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
