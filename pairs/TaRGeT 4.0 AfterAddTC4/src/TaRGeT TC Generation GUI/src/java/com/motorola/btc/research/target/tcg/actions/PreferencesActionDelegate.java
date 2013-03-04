package com.motorola.btc.research.target.tcg.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.tcg.preferences.PreferencesDialog;

/*
 * @(#)PreferencesAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   Sep 11, 2008    LIBqq51204	 Initial creation.
 * dwvm83   Oct 14, 2008	LIBqq51204	 Added method selectionChanged	
 */


public class PreferencesActionDelegate extends AbstractTCGActionDelegate 
{


    /**
     * Creates and opens the preferences dialog.
     * 
     */
    protected void hookGeneration()
    {

        PreferencesDialog dialog = new PreferencesDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell());
        dialog.open();
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
