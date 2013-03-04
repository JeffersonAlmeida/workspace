/*
 * @(#)OpenProjectWizard.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Nov 28, 2006    LIBkk11577   Javadoc comments.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring. 
 */

package com.motorola.btc.research.target.pm.wizards;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.perspectives.RequirementPerspective;
import com.motorola.btc.research.target.pm.progressbars.OpenProjectProgressBar;
import com.motorola.btc.research.target.pm.util.GUIUtil;

/**
 * This class represents the Open Project wizard that is responsible for opening an existing
 * project.
 * 
 * <pre>
 * CLASS:
 * This class represents the Open Project wizard that is responsible for opening an existing project.
 * 
 * RESPONSIBILITIES:
 * 1) Collect the data distributed in the pages
 * 2) Open an existing Target Project
 *
 * COLABORATORS:
 * 1) Uses ProjectOpenPage
 * 2) Uses TargetProject
 *
 * USAGE:
 * OpenProjectWizard wizard = new OpenProjectWizard()
 * </pre>
 */
public class OpenProjectWizard extends TargetWizard
{
    /**
     * The project selection page
     */
    private ProjectSelectionPage projectSelectionPage;

    /**
     * Constructor.
     * 
     * @param parentShell The parent shell of the wizard.
     */
    public OpenProjectWizard(Shell parentShell)
    {
        super(parentShell);
    }

    /**
     * Adds an open page to the wizard.
     */
    public void addPages()
    {
        setWindowTitle("Open Project Wizard");
        projectSelectionPage = new ProjectSelectionPage();
        addPage(projectSelectionPage);
    }

    /**
     * Initializes the wizard. 1) Retrieves the name of the project. 2) Displays a progress bar when
     * opening the project. 3) Shows the correct perspective. 4) Attaches ErrorView as a
     * ProjectManagerController observer. 5) Notifies all observers (recently added and existent).
     * 6) Refreshes the tree views.
     * 
     * @return <i>True</i> if everything goes fine or <i>false</i> otherwise.
     */
    @Override
    protected boolean init() throws Exception
    {
        boolean result = false;

        ProjectManagerController controller = ProjectManagerController.getInstance();

        /* Retrieves the project file from projectOpenPage */
        String projectFile = projectSelectionPage.getProjectFile();

        /* Opens the project */
        try
        {
            controller.openProject(projectFile);

            /* Manages the project opening progress */
            this.progressBar = new OpenProjectProgressBar();
            ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.parentShell);
            dialog.setCancelable(false);
            dialog.run(true, true, this.progressBar);

            /* verifies the exception */
            if (this.progressBar.getException() != null)
            {
                throw this.progressBar.getException();
            }

            GUIUtil.showPerspective(RequirementPerspective.ID);

            /*
             * the code below updates the tree views, the code must be here and not in the
             * respective progress bar because when the progress bar is executed the perspective has
             * not been created yet, so there is no how to update the views
             */
            GUIManager.getInstance().refreshViews();

            GUIManager.getInstance().updateApplicationTitle();

            result = true;

        }
        catch (TargetException te)
        {
            te.printStackTrace();
            MessageDialog
                    .openInformation(null, "Error while trying to open the project.",
                            "The opened file is not a valid project file. \n Please select a valid project and try again.");
        }

        return result;
    }

    /**
     * Enables or disables the close project action depending on the open project process.
     * 
     * @return The previous state of the open project process.
     */
    @Override
    protected boolean finish()
    {
        if (!this.isNotCrashed)
        {
            /* closes any exiting project is an error occurred */
            ProjectManagerController.getInstance().closeProject();
        }
        return this.isNotCrashed;
    }

    /**
     * @return The text of the wizard. Used to refer to external locations. E.g. The titles of error
     * messages.
     */
    @Override
    protected String getLocation()
    {
        return "opening project";
    }
}
