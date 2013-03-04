/*
 * @(#)AbstractTCGActionDelegate.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jun 26, 2007   LIBmm25975   Initial creation.
 * wdt022    Mar 14, 2008   LIBpp56482   Added verification before perform generation.
 * wdt022    Apr 01, 2008   LIBpp56482   Changes due to actions framework refactoring.
 * wln013    Apr 29, 2008   LIBpp56482   Rework after meeting 1 of inspection LX263835. 
 */
package com.motorola.btc.research.target.tcg.actions;

import org.eclipse.jface.dialogs.MessageDialog;

import com.motorola.btc.research.target.common.actions.TargetAction;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;

/**
 * This class represents an abstract TCG action which means that all actions in TCG plug-in must
 * extends this class. The class registers an action that initializes the current TargetAction and
 * also removes any existing PropertyChangeListener.
 */
public abstract class AbstractTCGActionDelegate extends TargetAction
{
    
    /**
     * Performs the basic verifications before generating test cases.
     */
    protected final void hook()
    {
        if (!ProjectManagerExternalFacade.getInstance().hasOpenedProject())
        {
            MessageDialog.openInformation(this.window.getShell(), "Test Case generation.",
                    "There is no opened project.");
        }
        else if (!this.hasImportedDocumentsInProject())
        {
            MessageDialog.openInformation(this.window.getShell(), "Test Case generation.",
                    "There are no imported documents.");
        }
        else if (!this.hasImportedFeatures())
        {
            MessageDialog.openInformation(this.window.getShell(), "Test Case generation.",
                    "There are no features in the current project.");
        }
        else if (this.hasErrorsInProject())
        {
            MessageDialog.openInformation(this.window.getShell(), "Test Case generation.",
                    "There are errors in the project. It is not possible to generate tests or execute related actions.");
        }
        else
        {
            this.hookGeneration();
        }
    }
    
    /**
     * 
     * Invokes the test case generation wizard.
     */
    protected abstract void hookGeneration();
    
    
    /**
     * Checks if the number of imported documents is greater than 0;
     * 
     * @return <code>true</code> if the number is greater than one, or <code>false</code>
     * otherwise.
     */
    private boolean hasImportedDocumentsInProject()
    {
        return ProjectManagerExternalFacade.getInstance().getCurrentProject().getPhoneDocuments()
                .size() > 0;
    }

    /**
     * Checks if there is at least one imported feature in the project.
     * 
     * @return <code>true</code> if there is at least one imported feature in the project, or
     * <code>false</code> otherwise.
     */
    private boolean hasImportedFeatures()
    {
        return (ProjectManagerExternalFacade.getInstance().getCurrentProject().getFeatures() != null)
                && (!ProjectManagerExternalFacade.getInstance().getCurrentProject().getFeatures()
                        .isEmpty());
    }

    /**
     * Verifies if there is at least one error in the project.
     * 
     * @return <code>True</code> if there is at least one error, or <code>false</code> otherwise.
     */
    private boolean hasErrorsInProject()
    {
        return ProjectManagerExternalFacade.getInstance().hasErrorsInProject();
    }
}