/*
 * @(#)TargetProjectRefreshInformationTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Apr 25, 2007   LIBkk22882   Initial creation.
 * wdt022    Apr 28, 2007   LIBkk22882   Updates due to inspection LX168641.
 * dhq348    Feb 21, 2008   LIBoo89937   Added interruption errors tests.
 * dhq348    Feb 22, 2008   LIBoo89937   Rework after inspection LX246152.
 * wmr068    Oct 16, 2008   xxx          Added the addProblematicDocumentsAndRemoveThem test.
 */
package com.motorola.btc.research.target.pm.controller;

import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.PMUnitTestUtil;
import com.motorola.btc.research.target.pm.ProjectManagerTests;
import com.motorola.btc.research.target.pm.errors.Error;

/**
 * This class comprehends the unit tests for the <code>TargetProjectRefreshInformation</code>
 * class. The input documents defined in <code>ProjectManagerTests</code> were used to test the
 * <code>TargetProjectRefreshInformationTest</code> methods.
 */
public class TargetProjectRefreshInformationTest
{

    /**
     * This is the setup method, called before each test execution. Since each test case
     * creates/opens a project, this method prepares the TaRGeT project (closing it) and clears the
     * error list.
     */
    @Before
    public void setUp()
    {
        ProjectManagerController.getInstance().closeProject();
    }

    /**
     * This method tests the refresh functionality after adding and removing documents
     * of a TaRGeT project. In addition, it verifies the number of errors introduced by
     * the new document and their respective types. At the end, it removes the problematic
     * document and verifies that no error is present anymore.
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void addProblematicDocumentsAndRemoveThem() throws IOException, TargetException
    {
        TargetProjectRefreshInformation refreshInfo = this
                .openProjectWithDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 });

        Assert.assertEquals(1, refreshInfo.getAddedDocuments().size());

        refreshInfo = this.addDocumentsAndReloadProject(new String[] {
                ProjectManagerTests.INPUT_DUP_STEPID_DOC, ProjectManagerTests.INPUT_WRONGXML_DOC });

        Assert.assertEquals(2, refreshInfo.getAddedDocuments().size());
        Assert.assertEquals(0, refreshInfo.getPreviousErrors().size());

        // Errors
        Assert.assertEquals(2, refreshInfo.getNewErrors().size());
        // Related to INPUT_WRONGXML_DOC
        Assert.assertEquals(1, refreshInfo.getNewErrors(Error.FATAL_ERROR).size());
        // Related to INPUT_DUP_STEPID_DOC
        Assert.assertEquals(1, refreshInfo.getNewErrors(Error.ERROR).size());

        refreshInfo = PMUnitTestUtil.removeAndReloadProject(new String[] {
                ProjectManagerTests.INPUT_DUP_STEPID_DOC, ProjectManagerTests.INPUT_WRONGXML_DOC });
        
        Assert.assertEquals(2, refreshInfo.getRemovedDocuments().size());
        
        Assert.assertEquals(0, refreshInfo.getNewErrors().size());
    }

    /**
     * This method tests the result information of opening and refreshing operations.
     * <p>
     * The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens a project containing the document <code>INPUT_CORRECT_DOC_1</code>.</td>
     * <td>The document is correctly imported. The operation information must indicate an opening
     * operation.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Adds the document <code>INPUT_DUP_STEPID_DOC</code> to the project and refreshs it.</td>
     * <td>The operation information must indicate a refreshing operation. The old project and the
     * old error list variables are verified. There must be one error in the new errors list.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openAndReloadOperation() throws IOException, TargetException
    {
        TargetProjectRefreshInformation refreshInfo = this
                .openProjectWithDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 });

        Assert.assertTrue("It must be a opening operation",
                refreshInfo.getOperation() == TargetProjectRefreshInformation.OPEN);

        // Keeps the information after the opening
        TargetProject oldProject = ProjectManagerController.getInstance().getCurrentProject();
        Collection<com.motorola.btc.research.target.pm.errors.Error> oldErrorList = ProjectManagerController
                .getInstance().getErrorList();

        refreshInfo = this
                .addDocumentsAndReloadProject(new String[] { ProjectManagerTests.INPUT_DUP_STEPID_DOC });

        this.verifyRefreshInfo(TargetProjectRefreshInformation.REFRESH, oldErrorList, oldProject,
                refreshInfo, com.motorola.btc.research.target.pm.errors.Error.ERROR, 1);
    }

    /**
     * This method tests the result information of opening and refreshing operations.
     * <p>
     * The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens a project containing no documents and adds the document
     * <code>INPUT_NOXML_DOC</code> to the project and refreshes it.</td>
     * <td>The project is refreshed.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Imports the document <code>INPUT_DUP_STEPID_DOC</code> to the project.</td>
     * <td>The operation information must indicate a importing operation. The old project and the
     * old error list variables are verified. There must be one error in the new errors list. The
     * number of new different errors must be 1. There is no new different fatal errors.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openAndImportOperation() throws IOException, TargetException
    {

        TargetProjectRefreshInformation refreshInfo = this
                .openProjectWithDocuments(new String[] {});

        refreshInfo = this
                .addDocumentsAndReloadProject(new String[] { ProjectManagerTests.INPUT_NOXML_DOC });

        // Keeps the information after the opening
        TargetProject oldProject = ProjectManagerController.getInstance().getCurrentProject();
        Collection<com.motorola.btc.research.target.pm.errors.Error> oldErrorList = ProjectManagerController
                .getInstance().getErrorList();

        refreshInfo = this
                .importDocumentsToProject(new String[] { ProjectManagerTests.INPUT_DUP_STEPID_DOC });

        int fatalError = com.motorola.btc.research.target.pm.errors.Error.FATAL_ERROR;
        int error = com.motorola.btc.research.target.pm.errors.Error.ERROR;
        this.verifyRefreshInfo(TargetProjectRefreshInformation.IMPORT, oldErrorList, oldProject,
                refreshInfo, error, 2);

        Assert.assertTrue("The number of new different errors must be 1", refreshInfo
                .getNewDifferentErrors(error).size() == 1);

        Assert.assertTrue("There is no new different fatal errors", refreshInfo
                .getNewDifferentErrors(fatalError).size() == 0);
    }


    /**
     * Auxiliary method for verifying the refresh information.
     * 
     * @param operationType The refreshing operation type.
     * @param oldErrorList The error list before the refreshing operation.
     * @param oldProject The project before the refreshing operation.
     * @param refreshInfo The refresh information object.
     * @param errorType The new added error type.
     * @param errorsNumber The number of errors after the operation.
     */
    private void verifyRefreshInfo(int operationType,
            Collection<com.motorola.btc.research.target.pm.errors.Error> oldErrorList,
            TargetProject oldProject, TargetProjectRefreshInformation refreshInfo, int errorType,
            int errorsNumber)
    {
        Assert.assertTrue("It must be a open operation",
                refreshInfo.getOperation() == operationType);

        Assert.assertTrue("The old project variable was not correctly set", refreshInfo
                .getOldProject().getFeatures().size() == oldProject.getFeatures().size()
                && refreshInfo.getOldProject().getPhoneDocuments().size() == oldProject
                        .getPhoneDocuments().size());

        Assert.assertTrue("The old error list variable was not correctly set", refreshInfo
                .getPreviousErrors().size() == oldErrorList.size());

        Assert.assertTrue("There must be one error in the new errors list", refreshInfo
                .hasNewError(errorType)
                && refreshInfo.getNewErrors().size() == errorsNumber);
    }

    /**
     * Auxiliary method for creating and opening a project with some documents. This method calls the
     * method <code>openGeneralProjectWithDocuments</code> from the
     * <code>ProjectManagerControllerTest</code> class.
     * 
     * @param documents The documents that will be contained in the project.
     * @return The refreshing information of the opening task.
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    public TargetProjectRefreshInformation openProjectWithDocuments(String[] documents)
            throws IOException, TargetException
    {
        return (new ProjectManagerControllerTest()).openGeneralProjectWithDocuments(documents);
    }

    /**
     * Auxiliary method for adding documents to the project and refreshing it. This method calls the
     * method <code>addAndReloadProject</code> from the <code>ProjectManagerControllerTest</code>
     * class.
     * 
     * @param docs The documents to be added to the project.
     * @return The refreshing information of the opening task.
     * @throws IOException In case of any error during the copying of files to the use cases
     * directory.
     * @throws TargetException In case of any exception during the project refreshing.
     */
    public TargetProjectRefreshInformation addDocumentsAndReloadProject(String[] documents)
            throws IOException, TargetException
    {
        return PMUnitTestUtil.addAndReloadProject(documents);
    }

    /**
     * This method is called by some test cases and is responsible for importing documents to the
     * current opened project. This method calls the method <code>importDocuments</code> from the
     * <code>ProjectManagerControllerTest</code> class.
     * 
     * @param docs The documents to be imported.
     * @return The refreshing information of the opening task.
     * @throws TargetException In case of any error during the importing, or the file copying.
     * @throws IOException In case of any error during the importing, or the file copying.
     */
    public TargetProjectRefreshInformation importDocumentsToProject(String[] documents)
            throws IOException, TargetException
    {
        return (new ProjectManagerControllerTest()).importDocuments(documents);
    }

    
}
