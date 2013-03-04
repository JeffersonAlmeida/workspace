/*
 * @(#)OpenProjectProgressBar.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Dec 11, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 * dhq348   May 18, 2007    LIBmm25975   Modification according to CR.
 */
package com.motorola.btc.research.target.pm.progressbars;

import org.eclipse.core.runtime.IProgressMonitor;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.controller.TargetProjectRefreshInformation;

/**
 * The progress bar that is displayed when a project is being opened.
 * 
 * <pre>
 * CLASS:
 * The progress bar representing the steps during the opening process of a project.
 * 
 * RESPONSIBILITIES:
 * 1) Displays a progress bar

 * USAGE:
 * OpenProjectProgressBar bar = new OpenProjectProgressBar();
 */
public class OpenProjectProgressBar extends TargetProgressBar
{
    /**
     * Receives the name of the task and sets the names of the subtasks.
     * 
     * @param taskname The task name to be displayed.
     */
    public OpenProjectProgressBar()
    {
        super("Opening Target project. Please, wait...");
        this.subtasks = new String[] { "Loading features and updating views", "Updating index" };
    }

    @Override
    /**
     * Implements the specific behavior of this progress bar. Adds a subtask and loads the project.
     * 
     * @param monitor The monitor of the progress bar.
     * @throws TargetException In case of any error during the project loading.
     */
    public void hook(IProgressMonitor monitor) throws TargetException
    {
        /* Loads the existing features and sets them to the the current open project */
        worked = this.addSubtask(monitor, worked);

        /* loads the features */
        TargetProjectRefreshInformation refreshInfo = ProjectManagerController.getInstance()
                .loadOpenedProject();

        worked = this.addSubtask(monitor, worked);
        ProjectManagerController.getInstance().updateIndex(refreshInfo, true);
    }

    @Override
    /**
     * Nothing to do on error.
     */
    protected void finishOnError()
    {
    }
}
