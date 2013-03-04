/*
 * @(#)PMUnitTestUtil.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jul 4, 2007    LIBmm63120   Initial creation.
 */
package com.motorola.btc.research.target.pm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.controller.TargetProject;
import com.motorola.btc.research.target.pm.controller.TargetProjectRefreshInformation;

/**
 * Class that contains methods used by other test classes from PM plugin.
 */
public class PMUnitTestUtil
{

    /**
     * Creates a project structure, and returns the path for the .tgt project file. If the
     * <code>createXML</code> parameter is set to <code>false</code>, the returned string will
     * represent a file that does not exist.
     * 
     * @param createDir Informs if the directories structure must be created.
     * @param createXML Informs if the .tgt file must be created.
     * @return The path for the .tgt project file.
     */
    public static String createProjectStructure(boolean createDir, boolean createXML)
    {

        long time = System.currentTimeMillis();
        String projectName = "ProjectStructure_" + time;

        /* Creates the TaRgeT project in Memory */
        TargetProject tgtProject = ProjectManagerController.getInstance().createProject(
                projectName, ProjectManagerTests.OUTPUT_FOLDER);

        /* Creates the project directories */
        if (createDir)
        {
            ProjectManagerController.getInstance().createProjectDirectories();
        }
        else
        {
            /* At least, the project folder must be created */
            File rootDirectory = new File(tgtProject.getRootDir());
            rootDirectory.mkdir();
        }

        /* Creates the TaRGeT project file (.tgt) */
        if (createXML)
        {
            try
            {
                ProjectManagerController.getInstance().createXMLFile();
            }
            catch (TargetException e)
            {
                throw new RuntimeException("Error creating the XML file!");
            }
        }

        /* Stores the project file path */
        String targetProjectFile = ProjectManagerController.getInstance().getCurrentProject()
                .getRootDir()
                + FileUtil.getSeparator() + TargetProject.PROJECT_FILE_NAME;

        /* Closes the created project */
        ProjectManagerController.getInstance().closeProject();

        return targetProjectFile;
    }

    /**
     * Auxiliary method for creating and opening an empty project.
     * 
     * @param createDir Informs if the directories structure must be created.
     * @param createXML Informs if the .tgt file must be created.
     * @return The refreshing info of the opening activity.
     * @throws TargetException In case of any error during the project creation/opening.
     */
    public static TargetProjectRefreshInformation generalEmptyProjectOpening(boolean createDir,
            boolean createXML) throws TargetException
    {

        /* Creates whole project structure */
        String targetProjectFile = createProjectStructure(createDir, createXML);

        /*
         * Opens the project. It creates the project directories, if they were not created.
         */
        TargetProject tgtProject = ProjectManagerController.getInstance().openProject(
                targetProjectFile);

        /* Verifies if the TargetProject instance is not null */
        Assert.assertNotNull("Opened project must be diferent from null", ProjectManagerController
                .getInstance().getCurrentProject());
        /* Verifies if the use case and test case directories exist */
        File tcDirectory = new File(tgtProject.getTestSuiteDir());
        File ucDirectory = new File(tgtProject.getUseCaseDir());
        Assert.assertTrue("Use Case directory was not created", ucDirectory.exists());
        Assert.assertTrue("Test Case directory was not created", tcDirectory.exists());

        /* Loads project documents */
        TargetProjectRefreshInformation refreshingInfo = ProjectManagerController.getInstance()
                .loadOpenedProject();

        /* An empty project must not contain neither documents, nor errors */
        Assert.assertEquals("Opened project must not contain documents", 0,
                ProjectManagerController.getInstance().getCurrentProject().getPhoneDocuments()
                        .size());
        Assert.assertEquals("Opened project must not contain errors", 0, ProjectManagerController
                .getInstance().getErrorList().size());

        return refreshingInfo;
    }

    /**
     * Auxiliary method for adding documents to the project and refreshing it.
     * 
     * @param docs The documents to be added to the project.
     * @return The refreshing information of the opening task.
     * @throws IOException In case of any error during the copying of files to the use cases
     * directory.
     * @throws TargetException In case of any exception during the project refreshing.
     */
    public static TargetProjectRefreshInformation addAndReloadProject(String[] docs) throws IOException,
            TargetException
    {

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        FileUtil.copyFiles(docs, tgtProject.getUseCaseDir(), true);

        return ProjectManagerController.getInstance().reloadProject();
    }

    /**
     * Auxiliary method for removing documents from the project and refreshing it.
     * 
     * @param docs The documents to be removed from the project.
     * @throws TargetException In case of any error during the project refreshing.
     */
    public static TargetProjectRefreshInformation removeAndReloadProject(String[] docs) throws TargetException
    {
        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        ArrayList<String> filesToDelete = new ArrayList<String>();
        /* Get the complete path of the files to be deleted from the project */
        for (String completePath : docs)
        {

            String fileName = FileUtil.getFileName(completePath);

            filesToDelete.add(tgtProject.getUseCaseDir() + FileUtil.getSeparator() + fileName);
        }

        FileUtil.deleteFiles(filesToDelete.toArray(new String[] {}));

        return ProjectManagerController.getInstance().reloadProject();
    }

}