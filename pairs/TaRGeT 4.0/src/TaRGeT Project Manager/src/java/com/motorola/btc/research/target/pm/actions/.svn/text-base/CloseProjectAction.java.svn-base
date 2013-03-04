/*
 * @(#)CloseProjectAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Jul 21, 2006    LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerController inclusion.
 * dhq348   Dec 20, 2006    LIBkk11577   Inspection (LX124184) faults fixing.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * wln013   Apr 29, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * dwvm83   Oct 14, 2008				 Added method selectionChanged
 */
package com.motorola.btc.research.target.pm.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import com.motorola.btc.research.target.common.actions.TargetAction;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.util.GUIUtil;

/**
 * Action used to close a TargetProject.
 * 
 * <pre>
 * CLASS:
 * Action used to close a TargetProject. 
 * 
 * RESPONSIBILITIES:
 * When the related events occur (e.g., close menu option is clicked), 
 * the opened project is closed.
 * 
 * USAGE:
 * One Action can be associated with events in GUI.
 * </pre>
 */
public class CloseProjectAction extends TargetAction
{
    /**
     * The ID of the action that is declared in plug-in.xml
     */
    public static final String ID = "com.motorola.btc.research.target.pm.actions.CloseProjectAction";

    @Override
    protected void hook()
    {
        if (ProjectManagerController.getInstance().hasOpenedProject())
        {
            if (GUIUtil.openYesOrNoInformation(this.window.getShell(), "Close project",
                    "The current project will be closed. Do you want to proceed?"))
            {
                GUIUtil.closeProject(this.window.getShell());
            }
        }
    }
    
    @Override
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
