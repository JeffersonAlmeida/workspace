/*
 * @(#)ImportFeaturesProgressBar.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Dec 13, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   May 18, 2007    LIBmm25975   Modification according to CR.
 */
package com.motorola.btc.research.target.pm.progressbars;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.controller.TargetProjectRefreshInformation;
import com.motorola.btc.research.target.pm.exceptions.FileAlreadyExistsException;
import com.motorola.btc.research.target.pm.exceptions.TargetProjectLoadingException;

/**
 * Indicates the progress of the document importing process.
 * 
 * <pre>
 * CLASS:
 * Indicates the progress of the document importing 
 * process. It copies the files passed to the correct project 
 * folder and load them into the project.
 * 
 * USAGE:
 * ImportFeaturesProgressBar progressBar = new ImportFeaturesProgressBar(taskname, features);
 */
public class ImportFeaturesProgressBar extends TargetProgressBar
{
    /**
     * The names of the feature documents.
     */
    private String[] featureDocuments;

    /**
     * The instance of <code>TargetProjectRefreshInformation</code> after the importing.
     */
    private TargetProjectRefreshInformation refreshInfo = null;

    /**
     * Constructor. Initiates the name of the task and the feature documents to be displayed.
     * 
     * @param taskname The name fo task.
     * @param featureDocuments The names of the feature documents.
     */
    public ImportFeaturesProgressBar(String taskname, String[] featureDocuments)
    {
        super(taskname);
        this.featureDocuments = featureDocuments;
        this.subtasks = new String[] { "Copying files", "Importing features",
                "Updating artifacts view", "Updating requirements view", "Updating index" };
    }

    
    /**
     * Implements the specific behavior of this progress bar. It copies all imported documents to
     * the correct project directory. Then it loads all of the copied files into the project.
     * 
     * @param monitor The monitor of the progress bar.
     * @throws TargetException Thrown when a logic error occurs during the importing process.
     * @throws IOException Thrown when it is not possible to copy the features to the project
     * folder.
     */
    public void hook(IProgressMonitor monitor) throws TargetException, IOException
    {
        /* Copy the files */
        worked = this.addSubtask(monitor, worked);

        /* check duplicated files */
        this.checkDuplicatedFiles();

        this.featureDocuments = FileUtil.copyFiles(this.featureDocuments, ProjectManagerController
                .getInstance().getCurrentProject().getUseCaseDir(), false);

        /* gets the previous number of errors */
        worked = this.addSubtask(monitor, worked);

        /* loads the features */
        this.refreshInfo = ProjectManagerController.getInstance().loadImportedDocumentsIntoProject(
                FileUtil.loadFiles(this.featureDocuments));

        worked = this.addSubtask(monitor, worked);
        ProjectManagerController.getInstance().updateIndex(this.refreshInfo, false);

    }

    /**
     * Checks if the file names exist in the project.
     * 
     * @throws TargetProjectLoadingException Thrown when there are duplicated files.
     */
    private void checkDuplicatedFiles() throws TargetProjectLoadingException
    {

        List<String> duplicated = FileUtil.getDuplicatedFiles(ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocumentFilePaths(),
                this.featureDocuments);

        if (duplicated.size() > 0)
        {
            this.featureDocuments = new String[] {}; // avoids existing files deletion
            throw new FileAlreadyExistsException();
        }
    }

    
    /**
     * Performs the deletion of the files if a error has occurred.
     */
    protected void finishOnError()
    {
        /* delete the loaded documents */
        FileUtil.deleteFiles(this.featureDocuments);
    }

    /**
     * The get method for the attribute <code>refreshInfo</code>.
     * 
     * @return Return the value of <code>refreshInfo</code> attribute.
     */
    public TargetProjectRefreshInformation getRefreshInfo()
    {
        return this.refreshInfo;
    }
}
