/*
 * @(#)ImportFeaturesWizard.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Nov 30, 2006    LIBkk11577   Initial creation.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 */
package com.motorola.btc.research.target.pm.wizards;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.controller.TargetProjectRefreshInformation;
import com.motorola.btc.research.target.pm.errors.Error;
import com.motorola.btc.research.target.pm.perspectives.RequirementPerspective;
import com.motorola.btc.research.target.pm.progressbars.ImportFeaturesProgressBar;
import com.motorola.btc.research.target.pm.util.GUIUtil;

/**
 * The wizard responsible for importing documents into the project.
 * 
 * <pre>
 * CLASS:
 * The wizard responsible for importing documents into the project.
 * 
 * RESPONSIBILITIES:
 * 1) Import new documents into the project.
 *
 * USAGE:
 * ImportFeaturesWizard wizard = new ImportFeaturesWizard(shell);
 */
public class ImportFeaturesWizard extends TargetWizard
{
    /** Indicates that there is an opened project */
    private boolean opened;

    /**
     * This is the page responsible for setting the project requirements
     */
    private DocumentsSelectionPage documentsSelectionPage;

    /**
     * Constructor.
     * 
     * @param parentShell The parent shell.
     */
    public ImportFeaturesWizard(Shell parentShell)
    {
        super(parentShell);
    }

    /**
     * Add the wizard pages. In this case, only one page is added (RequirementSelPage)
     */
    public void addPages()
    {
        setWindowTitle("Import Documents Wizard");
        documentsSelectionPage = new DocumentsSelectionPage();
        addPage(documentsSelectionPage);
    }

    /**
     * @see com.motorola.btc.research.target.pm.wizards.TargetWizard#init()
     */
    @Override
    protected boolean init() throws Exception
    {
        this.opened = ProjectManagerController.getInstance().hasOpenedProject();
        if (this.opened)
        {
            /* Retrieves the features from requirementSelPage */
            String[] featureDocumentName = documentsSelectionPage.getDocuments();

            ImportFeaturesProgressBar importBar = new ImportFeaturesProgressBar(
                    "Importing features", featureDocumentName);
            this.progressBar = importBar;
            ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.parentShell);
            dialog.setCancelable(false);
            dialog.run(false, true, this.progressBar);

            /* verifies the exception */
            if (this.progressBar.getException() != null)
            {
                throw this.progressBar.getException();
            }

            this.displayWarnings(importBar.getRefreshInfo());

            GUIUtil.showPerspective(RequirementPerspective.ID);

            GUIManager.getInstance().refreshViews();
        }
        return this.opened;
    }

    /**
     * Displays a message dialog containing any step id warning that exists.
     * 
     * @param refreshInfo The information about the importing process.
     */
    private void displayWarnings(TargetProjectRefreshInformation refreshInfo)
    {
        Collection<Error> stepIdErrors = refreshInfo.getNewDifferentErrors(Error.ERROR);

        if (stepIdErrors != null && stepIdErrors.size() > 0)
        {
            String message = "The following documents have step id errors:\n\n";
            Set<String> errors = new HashSet<String>();
            for (Error error : stepIdErrors)
            {
                if (!errors.contains(error.getResource()))
                {
                    errors.add(error.getResource());
                    message += "\t" + error.getResource() + "\n\n";
                }
            }

            message += "See the \"Errors\" tab for a detailed description.";

            MessageDialog.openWarning(null, "Warning", message);
        }

    }

    @Override
    protected boolean finish()
    {
        return this.isNotCrashed;
    }

    @Override
    protected String getLocation()
    {
        return "importing documents";
    }
}
