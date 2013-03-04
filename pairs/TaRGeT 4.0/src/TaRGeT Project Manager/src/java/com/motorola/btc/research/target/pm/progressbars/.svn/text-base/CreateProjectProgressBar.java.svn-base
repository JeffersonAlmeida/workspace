/*
 * @(#)CreateProjectProgress.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Dec 6, 2006    LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007   LIBkk11577   Rework of inspection LX133710.
 * wdt022    Apr 01, 2008   LIBpp56482   Changes due to actions framework refactoring.
 */
package com.motorola.btc.research.target.pm.progressbars;

import org.eclipse.core.runtime.IProgressMonitor;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;

/**
 * Progress bar for project creation.
 * 
 * <pre>
 * CLASS:
 * This class indicates the progress of a new project creation. 
 * Creates a logical project, its folders on disk and the xml 
 * file representing the project data.
 * 
 * USAGE:
 * CreateProjectProgress createProgress = new CreateProjectProgress(projectName, destinationFolder);
 */
public class CreateProjectProgressBar extends TargetProgressBar
{
    /** The project name */
    private String projectName;

    /** The project folder */
    private String projectFolder;

    /**
     * Sets the attributes with the values passed as parameters. It also initializes the subtasks.
     * 
     * @param taskname The name o the task.
     * @param projectName The name of the project to be created.
     * @param projectFolder The folder where the project will be created.
     */
    public CreateProjectProgressBar(String projectName, String projectFolder)
    {
        super("Creating project...");
        this.subtasks = new String[] { "Creating project directories", "Creating project",
                "Reloading existing documents" };
        this.projectFolder = projectFolder;
        this.projectName = projectName;
    }

    @Override
    /**
     * Implements the specific behavior of this progress bar. It first creates the logical project
     * then creates the project directories and finally the xml file representing the project.
     * 
     * @throws TargetException Thrown when an error has occurred during the logical creation of the
     * project.
     * @throws TargetException Thrown when it is not possible to create the project folders or to
     * write in the disk for some reason.
     */
    public void hook(IProgressMonitor monitor) throws TargetException
    {
        this.worked = this.addSubtask(monitor, worked);

        /* creates a new TargetProject */
        ProjectManagerController.getInstance().createProject(this.projectName, this.projectFolder);

        ProjectManagerController.getInstance().createProjectDirectories();

        this.worked = this.addSubtask(monitor, worked);
        ProjectManagerController.getInstance().createXMLFile();

        this.worked = this.addSubtask(monitor, worked);
    }

    @Override
    /**
     * It does not perform any operation on error.
     */
    protected void finishOnError()
    {
        // Nothing to be done
    }
}
