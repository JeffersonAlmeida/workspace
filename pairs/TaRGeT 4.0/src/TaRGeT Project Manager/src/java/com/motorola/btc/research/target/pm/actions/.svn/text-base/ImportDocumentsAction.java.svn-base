/*
 * @(#)ImportDocumentsAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Dec 13, 2006    LIBkk11577   Initial creation.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * dhq348   Feb 14, 2007    LIBll27713   Rework of inspection LX144782.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * wln013   Apr 29, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * dwvm83   Oct 14, 2008				 Added method selectionChanged
 */
package com.motorola.btc.research.target.pm.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;

import com.motorola.btc.research.target.common.actions.TargetAction;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.wizards.ImportFeaturesWizard;

/**
 * Action responsible for importing documents into a TaRGeT project.
 * 
 * <pre>
 * CLASS:
 * Action responsible for importing documents into a TaRGeT project.
 * 
 * RESPONSIBILITIES:
 * 1) Import documents into a project.
 *
 * USAGE:
 * One Action can be associated with events in GUI.
 */
public class ImportDocumentsAction extends TargetAction
{
    /**
     * The ID of the action that is declared in plugin.xml
     */
    public static final String ID = "com.motorola.btc.research.target.pm.actions.ImportDocumentsAction";

    @Override
    protected void hook()
    {
        if (ProjectManagerController.getInstance().hasOpenedProject())
        {
            this.startImportWizard();
        }
        else
        {
            MessageDialog.openInformation(this.window.getShell(), "Error while importing.",
                    "Please create or open a project to import documents.");
        }
    }

    /**
     * Displays the import wizard dialog.
     */
    private void startImportWizard()
    {
        ImportFeaturesWizard wizard = new ImportFeaturesWizard(this.window.getShell());
        WizardDialog dialog = new WizardDialog(this.window.getShell(), wizard);
        dialog.open();
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
