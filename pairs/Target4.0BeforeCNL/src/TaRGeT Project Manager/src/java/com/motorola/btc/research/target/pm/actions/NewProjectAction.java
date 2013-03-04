/*
 * @(#)NewProjectAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050    -    			LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerController inclusion.
 * dhq348   Dec 22, 2006    LIBkk11577   Inspection (LX124184) faults fixing.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * wln013   Apr 30, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 */
package com.motorola.btc.research.target.pm.actions;

import org.eclipse.jface.wizard.WizardDialog;

import com.motorola.btc.research.target.common.actions.TargetAction;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.util.GUIUtil;
import com.motorola.btc.research.target.pm.wizards.NewProjectWizard;
import com.motorola.btc.research.target.pm.wizards.TargetWizard;

/**
 * Action used to create a new project.
 * 
 * <pre>
 * CLASS:
 * Action used to create a new project.
 * 
 * RESPONSIBILITIES:
 * When the related events occur (e.g, new project menu option is clicked), 
 * the wizard used to create a new project is opened.
 * 
 * USAGE:
 * One Action can be associated with events in GUI.
 * </pre>
 */
public class NewProjectAction extends TargetAction
{
    /**
     * The ID of the action that is declared in plugin.xml
     */
    public static final String ID = "com.motorola.btc.research.target.pm.actions.NewProjectAction";


    protected void hook()
    {
        if (ProjectManagerController.getInstance().hasOpenedProject())
        {
            if (GUIUtil.openYesOrNoInformation(this.window.getShell(), "Close project",
                    "The current project will be closed. Do you want to proceed?"))
            {
                GUIUtil.closeProject(this.window.getShell());
                this.openNewProjectWizard();
            }
        }
        else
        {
            this.openNewProjectWizard();
        }
    }

    /**
     * Opens the new project wizard
     */
    private void openNewProjectWizard()
    {
        TargetWizard wizard = new NewProjectWizard(this.window.getShell());
        WizardDialog dialog = new WizardDialog(this.window.getShell(), wizard);
        dialog.open();
    }
}
