/*
 * @(#)ProjectManagerControllerTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Apr 09, 2007   LIBkk22882   Initial creation.
 * wdt022    Apr 28, 2007   LIBkk22882   Updates due to inspection LX168641.
 * dhq348    Jun 19, 2007   LIBmm47221   Modifications according to CR. Unit tests updated to support DuplicatedUseCaseIdErrorBetweenDocuments.
 * dhq348    Jul 11, 2007   LIBmm47221   Rework after inspection LX185000.
 * dhq348    Feb 15, 2008   LIBoo24851   Modifications according to CR.
 * wln013    Mar 04, 2008   LIBpp56482   importDocumentWithInterruption method added.
 */
package com.motorola.btc.research.target.pm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.common.util.WordFileFilter;
import com.motorola.btc.research.target.pm.PMUnitTestUtil;
import com.motorola.btc.research.target.pm.ProjectManagerTests;
import com.motorola.btc.research.target.pm.errors.DifferentMergedFeatureNameError;
import com.motorola.btc.research.target.pm.errors.DuplicatedFeatureIdInsideDocumentError;
import com.motorola.btc.research.target.pm.errors.DuplicatedStepIdError;
import com.motorola.btc.research.target.pm.errors.DuplicatedUseCaseIdError;
import com.motorola.btc.research.target.pm.errors.DuplicatedUseCaseIdErrorBetweenDocuments;
import com.motorola.btc.research.target.pm.errors.EmptyUseCaseFieldError;
import com.motorola.btc.research.target.pm.errors.Error;
import com.motorola.btc.research.target.pm.errors.InvalidStepIdReferenceError;
import com.motorola.btc.research.target.pm.errors.UnableToLoadDocumentError;
import com.motorola.btc.research.target.pm.exceptions.TargetProjectLoadingException;

/**
 * This class comprehends the unit tests for the <code>ProjectManagerController</code> class. The
 * input documents defined in <code>ProjectManagerTests</code> are used to test the
 * <code>ProjectManagerController</code> methods.
 */
public class ProjectManagerControllerTest
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
     * <pre>
     * ############################################ 
     * ############# PROJECT CREATION #############
     * ############################################
     */

    /**
     * Tests the creation of a TaRGeT project. The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates the project in memory.</td>
     * <td>The current project is different of null and the project name and root folder is
     * correctly set.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Creates the project directories (root, use case and test case directories) in the file
     * system.</td>
     * <td>The root folder is created, and the test case and use case folders are created.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>Creates the .tgt TaRGeT project file.</td>
     * <td>The .tgt project file is created.</td>
     * </tr>
     * </table>
     * <p>
     * The root directory is created using the system time in milliseconds.
     * 
     * @throws TargetException In case of any error on writing the TaRGeT project file.
     */
    @Test
    public void createProject() throws TargetException
    {
        long time = System.currentTimeMillis();
        String projectName = "TestCreate_" + time;

        /* Creates the TaRgeT project in Memory */
        ProjectManagerController.getInstance().createProject(projectName,
                ProjectManagerTests.OUTPUT_FOLDER);
        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();
        /* Tests if the project was correct created in memory */
        Assert.assertNotNull("TaRGeT project must not be null", tgtProject);
        Assert.assertEquals("Project name testing", tgtProject.getName(), projectName);
        Assert.assertEquals("TaRGeT project root directory", tgtProject.getRootDir(),
                ProjectManagerTests.OUTPUT_FOLDER + FileUtil.getSeparator() + projectName);

        /* Creates the project directories */
        ProjectManagerController.getInstance().createProjectDirectories();
        /* Tests if the directories were correct created */
        File rootDirectory = new File(tgtProject.getRootDir());
        File tcDirectory = new File(tgtProject.getTestSuiteDir());
        File ucDirectory = new File(tgtProject.getUseCaseDir());
        Assert.assertTrue("TaRGeT root directory was created", rootDirectory.exists());
        Assert.assertTrue("Use Case directory was created", ucDirectory.exists());
        Assert.assertTrue("Test Case directory was created", tcDirectory.exists());

        /* Creates the TaRGeT project file (.tgt) */
        ProjectManagerController.getInstance().createXMLFile();

        /* Tests if the tgt file was created */
        File tgtProjectFile = new File(tgtProject.getRootDir() + FileUtil.getSeparator()
                + TargetProject.PROJECT_FILE_NAME);
        Assert.assertTrue("TaRGeT Project file was created", tgtProjectFile.exists());
    }

    /**
     * Tests the method <code>existsProjectInDirectory</code> from the
     * <code>ProjectManagerController</code>. This method verifies if there is information of
     * previously created projects in a specified folder. The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates the project directories (root, use case and test case directories) and the .tgt
     * project file.</td>
     * <td>The method <code>existsProjectInDirectory</code> call must return true.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Deletes the test case and use case folders, and creates files with the same names.</td>
     * <td>The method <code>existsProjectInDirectory</code> call must return true.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>Deletes the .tgt project file, and creates a directory with the same name.</td>
     * <td>The method <code>existsProjectInDirectory</code> call must return false.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws IOException In case of any problem during the file creations and deletions.
     */
    @Test
    public void existsProjectInDirectory() throws IOException
    {
        String tgtFilePath = PMUnitTestUtil.createProjectStructure(true, true);
        String projectFolderPath = FileUtil.getFilePath(tgtFilePath);
        String tcDirPath = projectFolderPath + FileUtil.getSeparator() + TargetProject.TC_DIR;
        String ucDirPath = projectFolderPath + FileUtil.getSeparator() + TargetProject.UC_DIR;

        Assert.assertTrue("Not all expected project information is placed in the folder",
                ProjectManagerController.getInstance().existsProjectInDirectory(projectFolderPath));

        /* Deleting the folders */
        if (!(new File(tcDirPath).delete()) || !(new File(ucDirPath).delete()))
        {
            throw new IOException("The folders could not be deleted");
        }
        /* Creating new FILES with the deleted folder names */
        new File(tcDirPath).createNewFile();
        new File(ucDirPath).createNewFile();

        Assert.assertTrue("The .tgt project file is not placed in the folder",
                ProjectManagerController.getInstance().existsProjectInDirectory(projectFolderPath));

        /* Deleting the .tgt project file */
        if (!new File(tgtFilePath).delete())
        {

            throw new IOException("The target project file could not be deleted");
        }
        /* Creating a folder with the project file */
        new File(tgtFilePath).mkdir();

        Assert.assertFalse("There is no project information in the folder",
                ProjectManagerController.getInstance().existsProjectInDirectory(projectFolderPath));

        Assert
                .assertFalse(
                        "There must not be project information in the folder. The informed path is invalid",
                        ProjectManagerController.getInstance().existsProjectInDirectory(
                                projectFolderPath + FileUtil.getSeparator()
                                        + " ... invalid path ..."));

    }

    /**
     * <pre>
     * ###############################################
     * ############# IMPORTING DOCUMENTS ############# 
     * ###############################################
     */

    /**
     * This methods tests the importing of documents to an empty project. The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * and imports the document <code>INPUT_CORRECT_DOC_1</code> and
     * <code>INPUT_CORRECT_DOC_2</code>.</td>
     * <td>Two documents are imported to the current project and there are no errors in the
     * project.</td>
     * </tr>
     * </table>
     * 
     * @throws TargetException In case of any error during the project creation/opening/importing,
     * or during the importing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void importDocumentsToEmptyProject() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        this.importDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        int importedDocNum = ProjectManagerController.getInstance().getCurrentProject()
                .getPhoneDocuments().size();

        Assert.assertEquals("There must be two imported documents.", 2, importedDocNum);

        Assert.assertTrue("There must not be errors in the project", ProjectManagerController
                .getInstance().getErrorList().size() == 0);
    }

    /**
     * This test tries to import a document without XML content. An exception must be launched. The
     * test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * and imports the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_NOXML_DOC</code>.</td>
     * <td>A <code>UseCaseDocumentXMLException</code> exception is launched.</td>
     * </tr>
     * </table>
     * <p>
     * The exception is thrown since no XML is extracted from the document. So, the document can not
     * be imported.
     * 
     * @throws TargetException In case of any error during the project creation/opening/importing,
     * or during the importing.
     * @throws IOException In case of any error during file handling.
     */
    @Test(expected = UseCaseDocumentXMLException.class)
    public void importDocumentWithoutXMLToEmptyProject() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        this.importDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_NOXML_DOC });
    }

    /**
     * This test tries to import a document with an invalid XML content. An exception must be
     * launched. The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * and imports the documents <code>INPUT_CORRECT_DOC_1</code> and
     * <code>INPUT_WRONGXML_DOC</code>.</td>
     * <td>A <code>UseCaseDocumentXMLException</code> exception is launched.</td>
     * </tr>
     * </table>
     * <p>
     * The exception is thrown since no XML is extracted from the document. So, the document can not
     * be imported.
     * 
     * @throws TargetException In case of any error during the project creation/opening, or during
     * the importing.
     * @throws IOException In case of any error during file handling.
     */
    @Test(expected = UseCaseDocumentXMLException.class)
    public void importWrongXMLDocumentToEmptyProject() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        this.importDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_WRONGXML_DOC });
    }

    /**
     * This test tries to import a document with a feature whose Id is already in use, which launchs
     * an exception. The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * and imports the documents <code>INPUT_CORRECT_DOC_1</code> and
     * <code>INPUT_CORRECT_DOC_2</code>.</td>
     * <td>The documents are correctly imported.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Imports the document <code>INPUT_DUP_FEATID_WITH_DOC_1</code>, that contains a
     * feature with Id that is already in use.</td>
     * <td>An <code>TargetProjectLoadingException</code> exception is launched and caught. The
     * exception error list contains only one error of type
     * <code>DuplicatedUseCaseIdErrorBetweenDocuments</code>.</td>
     * </tr>
     * </table>
     * <p>
     * The exception is thrown since no XML is extracted from the document. So, the document can not
     * be imported.
     * 
     * @throws TargetException In case of any error during the project creation/opening, or during
     * the importing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void importDocumentsWithDuplicatedFeatureIdBetweenDocuments() throws TargetException,
            IOException
    {
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        this.importDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        try
        {
            this.importDocuments(new String[] { ProjectManagerTests.INPUT_DUP_FEATID_WITH_DOC_1 });
            /* The previous statement must launch an exception */
            Assert.fail("This statement must not be reached.");

        }
        catch (TargetProjectLoadingException e)
        {
            Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = e.getErrors();

            Assert.assertEquals("There must only be one error", 1, errorList.size());
            Assert.assertTrue("There must be a duplicated feature ID error", this.hasError(
                    DuplicatedUseCaseIdErrorBetweenDocuments.class, errorList));
        }
    }

    /**
     * Verifies if an error is identified when importing a document containing two features with
     * same ID (document <code>INPUT_DUP_FEATID_INSIDE_DOC</code>). It calls the method
     * <code>importDocumentsLaunchingException</code>. <br>
     * See method <code>importDocumentsLaunchingException</code> for further information about
     * this test case.
     * 
     * @throws TargetException In case of any exception launched by the method
     * <code>importDocumentsLaunchingException</code>.
     * @throws IOException In case of any exception launched by the method
     * <code>importDocumentsLaunchingException</code>.
     */
    @Test
    public void importDocumentWithDuplicatedFeatureIdInsideDocument() throws TargetException,
            IOException
    {
        this.importDocumentsLaunchingException(
                new String[] { ProjectManagerTests.INPUT_DUP_FEATID_INSIDE_DOC },
                "There must only be a duplicated feature ID inside document error",
                DuplicatedFeatureIdInsideDocumentError.class);
    }

    /**
     * Verifies if an error is identified when importing a document containing a feature with two
     * use cases with same Id (document <code>INPUT_DUP_UCID_DOC</code>). It calls the method
     * <code>importDocumentsLaunchingException</code>. <br>
     * See method <code>importDocumentsLaunchingException</code> for further information about
     * this test case.
     * 
     * @throws TargetException In case of any exception launched by the method
     * <code>importDocumentsLaunchingException</code>.
     * @throws IOException In case of any exception launched by the method
     * <code>importDocumentsLaunchingException</code>.
     */
    @Test
    public void importDocumentWithDuplicatedUseCaseIdError() throws TargetException, IOException
    {
        this
                .importDocumentsLaunchingException(
                        new String[] { ProjectManagerTests.INPUT_DUP_UCID_DOC },
                        "There must only be a duplicated use case ID error",
                        DuplicatedUseCaseIdError.class);
    }

    /**
     * This test case imports, sequentially, two different documents with errors, and another
     * correct document, that does not cause any effect. The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * and imports the document <code>INPUT_DUP_STEPID_DOC</code>, that contains a use case with
     * two step with same id.</td>
     * <td>The documents are correctly imported, and the error list contains only one error of type
     * <code>DuplicatedStepIdError</code>.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Imports the document <code>INPUT_MISSING_STEPID_DOC</code>, that contains a step
     * without Id.</td>
     * <td>The document is correctly imported, and the error list contains two errors, one of type
     * <code>DuplicatedStepIdError</code>, and another of type
     * <code>EmptyUseCaseFieldError</code>.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>Imports the document <code>INPUT_CORRECT_DOC_1</code>.</td>
     * <td>The document is correctly imported, and the error list keeps unchanged.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening, or during
     * the importing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void importDocumentsWithDuplicatedAndMissingStepId() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Imports the document with duplicated step id error */
        this.importDocuments(new String[] { ProjectManagerTests.INPUT_DUP_STEPID_DOC });

        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        Assert.assertEquals("There must only be one error", 1, errorList.size());
        Assert.assertTrue("There must be a duplicated step ID error", this.hasError(
                DuplicatedStepIdError.class, errorList));

        /* Imports the document with missing step id error */
        this.importDocuments(new String[] { ProjectManagerTests.INPUT_MISSING_STEPID_DOC });
        errorList = ProjectManagerController.getInstance().getErrorList();

        Assert.assertEquals("There must be two errors", 2, errorList.size());
        Assert.assertTrue("There must be a duplicated step ID", this.hasError(
                DuplicatedStepIdError.class, errorList));
        Assert.assertTrue("There must be a missing step ID", this.hasError(
                EmptyUseCaseFieldError.class, errorList));

        /* Imports a correct document. It must not cause any effect. */
        this.importDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 });
        errorList = ProjectManagerController.getInstance().getErrorList();

        Assert.assertEquals("There must be two errors", 2, errorList.size());
        Assert.assertTrue("There must be a duplicated step ID", this.hasError(
                DuplicatedStepIdError.class, errorList));
        Assert.assertTrue("There must be a missing step ID", this.hasError(
                EmptyUseCaseFieldError.class, errorList));

    }

    /**
     * This is a generic method that is called by the test cases. It tests the importing of
     * documents, considering that one of these documents launchs an
     * <code>TargetProjectLoadingException</code> exception. It is verified if the arisen
     * exception contains one (and only one) error of the type indicated by the parameter
     * <code>errorClass</code>. <br>
     * This method assumes that a project was already created.
     * 
     * @param docs The documents to be imported.
     * @param assertMessage The message passed to the assert method that verifies the error type in
     * the launched exception.
     * @param errorClass The type of the error that must be contained in the exception error list.
     * @throws TargetException In case of any error during the project creation/opening, or during
     * the importing.
     * @throws IOException In case of any error during file handling.
     */
    public void importDocumentsLaunchingException(String[] docs, String assertMessage,
            Class errorClass) throws TargetException, IOException
    {
        try
        {
            /* Creates a new project and gets the project instance */
            PMUnitTestUtil.generalEmptyProjectOpening(true, true);

            this.importDocuments(docs);
            /* The previous statement must launch an exception */
            Assert.fail("This statement must not be reached.");

        }
        catch (TargetProjectLoadingException e)
        {
            Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = e.getErrors();

            Assert.assertEquals("There must be only one error", 1, errorList.size());
            Assert.assertTrue(assertMessage, this.hasError(errorClass, errorList));
        }
    }

    /**
     * This test case imports a document with an invalid step id reference error, and after imports
     * another document that fix the error. The test steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * and adds the document <code>INPUT_DUP_UCID_DOC</code> to the use cases' folder, and reload
     * it. (This adding and reloading step was added in order to verify the importing when there is
     * an invalid document in the project).</td>
     * <td>The documents are correctly imported, and the error list contains only one error.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Imports the document <code>INPUT_CORRECT_DOC_2</code>, that contains a reference to a
     * step of another document. </td>
     * <td>The document is correctly imported, and the error list must contain two errors.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>Imports the document <code>INPUT_CORRECT_DOC_1</code>.</td>
     * <td>The document is correctly imported, and the error list contains only a duplicated use
     * case Id error.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening, or during
     * the importing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void importDocumentToFixInvalidStepIdReference() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /*
         * Adding an invalid document to the project, in order to test the importing process when
         * there is an invalid document in the project
         */
        PMUnitTestUtil.addAndReloadProject(new String[] { ProjectManagerTests.INPUT_DUP_UCID_DOC });

        Assert.assertEquals("There must only be one error", 1, ProjectManagerController
                .getInstance().getErrorList().size());

        /* Importing a document with an step Id error */
        this.importDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        Assert.assertEquals("There must be two imported documents", 2, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());

        Assert.assertEquals("There must be two errors in the project", 2, ProjectManagerController
                .getInstance().getErrorList().size());

        /* Importing the second document. It must fix the project error */
        this.importDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 });

        Assert.assertEquals("There must be three imported documents", 3, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());

        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();
        Assert.assertEquals("There must only be one error", 1, errorList.size());
        Assert.assertTrue("There is not a duplicated use case Id error in the project", this
                .hasError(DuplicatedUseCaseIdError.class, errorList));

    }

    /**
     * This test case imports a document with an missing action field and a missing response field.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * containing the document <code>INPUT_MISSING_ACTION_RESPONSE_DOC</code>.</td>
     * <td>The document is correctly imported, and the error list contains only two errors. There
     * must be one missing action field error and one missing response field error.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening, or during
     * the importing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void importDocumentWithMissingResponseAndAction() throws TargetException, IOException
    {
        /* Creates and opens a new project */
        this
                .openGeneralProjectWithDocuments(new String[] { ProjectManagerTests.INPUT_MISSING_ACTION_RESPONSE_DOC });

        Assert.assertEquals("There must only be two missing field errors", 2,
                ProjectManagerController.getInstance().getErrorList().size());

        // This iterator will count how many missing action and response field error exists in the
        // error list
        Iterator<com.motorola.btc.research.target.pm.errors.Error> iterator = ProjectManagerController
                .getInstance().getErrorList().iterator();
        int action = 0;
        int response = 0;
        while (iterator.hasNext())
        {
            EmptyUseCaseFieldError error = (EmptyUseCaseFieldError) iterator.next();

            if (error.getFieldName().equals(EmptyUseCaseFieldError.STEP_ACTION))
            {
                action++;
            }
            if (error.getFieldName().equals(EmptyUseCaseFieldError.STEP_RESPONSE))
            {
                response++;
            }
        }
        Assert.assertEquals("There must be only one action missing field", 1, action);
        Assert.assertEquals("There must be only one response missing field", 1, response);
    }

    /**
     * This method is called by some test cases and is responsible for importing documents to the
     * current opened project.
     * 
     * @param docs The documents to be imported.
     * @return The refreshing information of the opening task.
     * @throws TargetException In case of any error during the importing, or the file copying.
     * @throws IOException In case of any error during file handling.
     */
    public TargetProjectRefreshInformation importDocuments(String[] docs) throws IOException,
            TargetException
    {
        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        docs = FileUtil.copyFiles(docs, tgtProject.getUseCaseDir(), true);

        return ProjectManagerController.getInstance().loadImportedDocumentsIntoProject(
                FileUtil.loadFiles(docs));
    }
    
    
    
    /**
     * <pre>
     * ############################################## 
     * ############# PROJECT REFRESHING #############
     * ##############################################
     */

    /**
     * This methods tests if the tool notices a fatal error when a document is inserted in the
     * project use cases' directory and the project is refreshed.
     * <p>
     * The tests steps are:
     * <p>
     * <table border="1" cellpadding="5" width="610">
     * <tr>
     * <th width="6%">Step</th>
     * <th width="47%">Action</th>
     * <th width="47%">Response</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * copies the document <code>INPUT_CORRECT_DOC_1</code> to the use cases directory, and
     * refreshes the project.</td>
     * <td>The document is correctly imported. No error is generated.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Copies the documents <code>INPUT_DUP_UCID_DOC</code> (duplicated use case Id), and the
     * document <code>INPUT_DUP_FEATID_WITH_DOC_1</code> (duplicated feature Id with document 1),
     * and refreshes the project.</td>
     * <td>There must be three imported documents, and there must be two errors: duplicated use
     * case id error and duplicated feature id error. The document with duplicated feature id error
     * must be the last one added.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void refreshProjectAndGeneratingFatalErrors() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Importing a new document to the project and reloading */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 });

        /*
         * Importing a document with duplicated use case and feature id error to the project and
         * reloading
         */
        PMUnitTestUtil.addAndReloadProject(new String[] { ProjectManagerTests.INPUT_DUP_UCID_DOC,
                ProjectManagerTests.INPUT_DUP_FEATID_WITH_DOC_1 });

        Assert.assertEquals("There must be three imported documents", 3, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());

        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();
        /* Verify the two types of error in the project */
        Assert.assertEquals("There must be two errors", 2, errorList.size());
        Assert.assertTrue("There should be a duplicated use case id error", this.hasError(
                DuplicatedUseCaseIdError.class, errorList));

        Assert.assertTrue("There should be a duplicated feature id error", this.hasError(
                DuplicatedUseCaseIdErrorBetweenDocuments.class, errorList));
        for (com.motorola.btc.research.target.pm.errors.Error error : errorList)
        {

            if (error instanceof DuplicatedUseCaseIdErrorBetweenDocuments)
            {
                DuplicatedUseCaseIdErrorBetweenDocuments dfError = (DuplicatedUseCaseIdErrorBetweenDocuments) error;

                String errorFileName = FileUtil.getFileName(dfError.getSecondDocumentName());
                String secondFileName = FileUtil
                        .getFileName(ProjectManagerTests.INPUT_DUP_FEATID_WITH_DOC_1);

                Assert.assertEquals("The second filename is wrong", secondFileName, errorFileName);

                break;
            }
        }

    }

    /**
     * This test verifies if an invalid step Id error is removed when adding a document.
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
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * copies the document <code>INPUT_CORRECT_DOC_2</code> to the use case directory, and refresh
     * the project.</td>
     * <td>There must be one imported document. There must be an invalid step id reference error in
     * the project.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Copies the document <code>INPUT_CORRECT_DOC_1</code> to the use cases directory, and
     * refreshes the project.</td>
     * <td>There must be two imported documents. There must not be errors in the project.</td>
     * </tr>
     * </table>
     * <p>
     * It also verifies if a merge of features was correctly done.
     * 
     * @throws TargetException In case of any error during the refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void refreshProjectAddTwoDocumentsAndRemovingError() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Importing a new document to the project and reloading */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        Assert.assertEquals("There must be one imported document", 1, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());

        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        Assert.assertEquals("There must be one error in the project", 1, errorList.size());
        Assert.assertTrue("There must be an invalid step id reference error in the project", this
                .hasError(InvalidStepIdReferenceError.class, errorList));
        /* Importing a new document to the project and reloading */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 });

        Assert.assertEquals("There must be two imported documents", 2, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());
        Assert.assertEquals("There must not be errors in the project", 0, ProjectManagerController
                .getInstance().getErrorList().size());

        /* testing merge with no warnings */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.CORRECT_DUPLICATED_FEATURE_WITH_DOC1 });
        Assert.assertEquals("There must be three imported documents", 3, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());
        Assert.assertTrue("There must not be errors in the project", ProjectManagerController
                .getInstance().getErrorList().isEmpty());
        Collection<Feature> merged = ProjectManagerController.getInstance().getAllFeatures();
        Assert.assertEquals("Wrong number of merged features", 2, merged.size());
        for (Feature feature : merged)
        {
            if (feature.getId().equals("11111"))
            {
                Assert.assertEquals("Wrong number of usecases in the merged feature", 4, feature
                        .getUseCases().size());
            }
        }
    }

    /**
     * This test verifies if an invalid step Id error is inserted in the project when removing a
     * document.
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
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * copies the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_NOXML_DOC</code> to
     * the use case directory, and refresh the project.</td>
     * <td>There must be two imported document. There must be only one error in the project.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Copies the document <code>INPUT_CORRECT_DOC_2</code> to the use cases directory, and
     * refreshes the project.</td>
     * <td>There must be three imported documents. There must be only one error in the project.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>Removes the document <code>INPUT_CORRECT_DOC_1</code> from the use cases directory,
     * and refreshes the project.</td>
     * <td>There must be two imported document. There must be an invalid step id reference error
     * and a unable to load document error in the project.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void refreshProjectAddTwoAndRemoveOneDocument() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Importing new documents to the project and reloading */
        PMUnitTestUtil.addAndReloadProject(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_NOXML_DOC });

        Assert.assertEquals("There must be two imported document", 2, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());
        Assert.assertEquals("There must be only one error in the project", 1,
                ProjectManagerController.getInstance().getErrorList().size());

        /* Importing a new document that is referred by the already imported doc and reloading */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        Assert.assertEquals("There must be three imported documents", 3, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());
        Assert.assertEquals("There must be only one error in the project", 1,
                ProjectManagerController.getInstance().getErrorList().size());

        /* Removing an existing document from the project and reloading */
        PMUnitTestUtil.removeAndReloadProject(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 });
        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        int docsNum = ProjectManagerController.getInstance().getCurrentProject()
                .getPhoneDocuments().size();
        Assert.assertEquals("There must be two imported documents.", 2, docsNum);

        Assert.assertEquals("There must be two errors in the project.", 2, errorList.size());
        Assert.assertTrue("There must be an invalid step id reference error", this.hasError(
                InvalidStepIdReferenceError.class, errorList));
        Assert.assertTrue("There must be a unable to load document error in the project", this
                .hasError(UnableToLoadDocumentError.class, errorList));
    }

    /**
     * This test verifies the project refreshing operation, when changing a document.
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
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>),
     * copies the document <code>INPUT_NOXML_DOC</code> to the use case directory, and refresh the
     * project.</td>
     * <td>There must be one imported document. There must be only one error in the project.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Simulates a document changing, by deleting the document <code>INPUT_NOXML_DOC</code>
     * and copying the document <code>INPUT_CORRECT_DOC_1</code> to the use cases directory and
     * renaming it. The project is refreshed.</td>
     * <td>There must be one imported document. There must be no errors in the project.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void refreshProjectChangingDocument() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Importing a document without XML content to the project and reloading */
        PMUnitTestUtil.addAndReloadProject(new String[] { ProjectManagerTests.INPUT_NOXML_DOC });

        Assert.assertEquals("There must be one imported document", 1, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());
        Assert.assertEquals("There must be only one error in the project", 1,
                ProjectManagerController.getInstance().getErrorList().size());

        /* Simulating a document changing */
        this.simulateDocumentUpdating(ProjectManagerTests.INPUT_NOXML_DOC,
                ProjectManagerTests.INPUT_CORRECT_DOC_1);
        ProjectManagerController.getInstance().reloadProject();

        Assert.assertEquals("There must be one imported document", 1, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());
        Assert.assertEquals("There must be no errors in the project", 0, ProjectManagerController
                .getInstance().getErrorList().size());
    }

    /**
     * <pre>
     * ########################################### 
     * ############# PROJECT OPENING #############
     * ###########################################
     */

    /**
     * This test verifies if a project with two documents is correctly opened.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_CORRECT_DOC_2</code>.</td>
     * <td>The opened project must contain two documents. The opened project must not contain
     * errors.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openProjectWithTwoDocuments() throws TargetException, IOException
    {
        this.openGeneralProjectWithDocuments(new String[] {
                ProjectManagerTests.INPUT_CORRECT_DOC_1, ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        Assert.assertTrue("A project is opened", ProjectManagerController.getInstance()
                .hasOpenedProject());

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        Assert.assertEquals("Opened project must contain two documents", 2, tgtProject
                .getPhoneDocuments().size());
        Assert.assertEquals("Opened project must not contain errors", 0, ProjectManagerController
                .getInstance().getErrorList().size());

    }

    /**
     * This test verifies if a project containing a document with invalid step Id error is correctly
     * opened, and if the error is identified.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the document <code>INPUT_CORRECT_DOC_2</code>.</td>
     * <td>The opened project must contain one document. There must be only one error in the
     * project, which must be an invalid step Id reference error, in the use case "UC_1", of the
     * feature "22222".</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openProjectWithInvalidStepIdError() throws TargetException, IOException
    {
        String docName = FileUtil.getFileName(ProjectManagerTests.INPUT_CORRECT_DOC_2);
        this
                .openGeneralProjectWithDocuments(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        Assert.assertTrue("A project is opened", ProjectManagerController.getInstance()
                .hasOpenedProject());

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        /* An empty project must not contain neither documents, nor errors */
        Assert.assertEquals("Opened project must contain one document", 1, tgtProject
                .getPhoneDocuments().size());

        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        /* Gets the feature and the use case that contains the error */
        Feature f = tgtProject.getPhoneDocumentFromFilePath(
                tgtProject.getUseCaseDir() + FileUtil.getSeparator() + docName).getFeature("22222");
        UseCase uc = f.getUseCase("UC_01");

        Assert.assertEquals("There must be only one error", 1, errorList.size());

        Assert.assertTrue("There must be a use case error", ProjectManagerController.getInstance()
                .hasUseCaseError(uc, f));
        Assert.assertTrue("There must be an invalid step Id reference error", this.hasError(
                InvalidStepIdReferenceError.class, errorList));
    }

    /**
     * This test verifies if a <code>UseCaseDocumentXMLException</code> exception is launched
     * during the opening of project containg a document with wrong XML structure.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_WRONGXML_DOC</code>.</td>
     * <td>A <code>UseCaseDocumentXMLException</code> exception must be launched.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test(expected = UseCaseDocumentXMLException.class)
    public void openProjectWithInavlidXMLDocument() throws TargetException, IOException
    {
        this.openGeneralProjectWithDocuments(new String[] {
                ProjectManagerTests.INPUT_CORRECT_DOC_1, ProjectManagerTests.INPUT_WRONGXML_DOC });
    }

    /**
     * This test verifies if a <code>UseCaseDocumentXMLException</code> exception is launched
     * during the opening of project containg a document without XML structure.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_NOXML_DOC</code>.</td>
     * <td>A <code>UseCaseDocumentXMLException</code> exception must be launched.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test(expected = UseCaseDocumentXMLException.class)
    public void openProjectWithNoXMLDocument() throws TargetException, IOException
    {
        this.openGeneralProjectWithDocuments(new String[] {
                ProjectManagerTests.INPUT_CORRECT_DOC_1, ProjectManagerTests.INPUT_NOXML_DOC });
    }

    /**
     * This test verifies if a <code>TargetProjectLoadingException</code> exception is launched
     * during the opening of project containg documents that has duplicated feature Id.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code>, <code>INPUT_CORRECT_DOC_2</code>
     * and <code>INPUT_DUP_FEATID_WITH_DOC_1</code>.</td>
     * <td>A <code>TargetProjectLoadingException</code> exception must be launched. The exception
     * error list must only contain a duplicated feature ID error.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openProjectWithDuplicatedUseCaseIdErrorBetweenDocuments() throws TargetException,
            IOException
    {
        try
        {
            this.openGeneralProjectWithDocuments(new String[] {
                    ProjectManagerTests.INPUT_CORRECT_DOC_1,
                    ProjectManagerTests.INPUT_CORRECT_DOC_2,
                    ProjectManagerTests.INPUT_DUP_FEATID_WITH_DOC_1 });
            /* The previous statement must launch an exception */
            Assert.fail("This statement must not be reached.");

        }
        catch (TargetProjectLoadingException e)
        {
            Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = e.getErrors();

            Assert.assertEquals("There must only be one error", 1, errorList.size());
            Assert.assertTrue("There must be a duplicated feature ID error", this.hasError(
                    DuplicatedUseCaseIdErrorBetweenDocuments.class, errorList));
        }
    }

    /**
     * Tests if a project loading two documents with a common feature (with the same id) and
     * different feature names have a warning.
     */
    @Test
    public void testFeaturesWithSameIdButDifferentNames()
    {
        try
        {
            this.openGeneralProjectWithDocuments(new String[] {
                    ProjectManagerTests.INPUT_CORRECT_DOC_1,
                    ProjectManagerTests.DUPLICATED_FEATURE_NAME_WITH_CORRECT_DOC });
            Collection<com.motorola.btc.research.target.pm.errors.Error> errors = ProjectManagerController
                    .getInstance().getErrorList();
            Assert.assertEquals("Unexpected number of errors", 1, errors.size());
            com.motorola.btc.research.target.pm.errors.Error error = errors.iterator().next();
            Assert.assertEquals("Different classes", DifferentMergedFeatureNameError.class, error
                    .getClass());
            Assert.assertEquals("Different kind of error", Error.WARNING, error.getType());
        }
        catch (IOException e)
        {
            Assert.fail("This statement must not be reached.");
            e.printStackTrace();
        }
        catch (TargetException e)
        {
            Assert.fail("This statement must not be reached.");
            e.printStackTrace();
        }
    }

    /**
     * This test verifies if a <code>TargetProjectLoadingException</code> exception is launched
     * during the opening of project containg a document that has duplicated use case Id.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the document <code>INPUT_DUP_UCID_DOC</code>.</td>
     * <td>A <code>TargetProjectLoadingException</code> exception must be launched. The exception
     * error list must only contain a duplicated use case ID error.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openProjectWithDuplicatedUseCaseIdError() throws TargetException, IOException
    {
        try
        {
            this
                    .openGeneralProjectWithDocuments(new String[] { ProjectManagerTests.INPUT_DUP_UCID_DOC });

            /* The previous statement must launch an exception */
            Assert.fail("This statement must not be reached.");

        }
        catch (TargetProjectLoadingException e)
        {
            Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = e.getErrors();

            Assert.assertEquals("There must only be one error", 1, errorList.size());
            Assert.assertTrue("There must be a duplicated use case ID error", this.hasError(
                    DuplicatedUseCaseIdError.class, errorList));
        }
    }

    /**
     * This test verifies if a project is opened containing an empty use case field error.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code> and
     * <code>INPUT_MISSING_STEPID_DOC</code>.</td>
     * <td>The project is opened. It must only contain an empty field error.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openProjectWithMissingStepIdError() throws TargetException, IOException
    {
        this.openGeneralProjectWithDocuments(new String[] {
                ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_MISSING_STEPID_DOC });

        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        Assert.assertEquals("There must only be one error", 1, errorList.size());
        Assert.assertTrue("There must be an empty field error", this.hasError(
                EmptyUseCaseFieldError.class, errorList));
    }

    /**
     * This test verifies if a <code>TargetProjectLoadingException</code> exception is launched
     * during the opening of a project containing a document that has duplicated feature Id inside a
     * document.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code> and
     * <code>INPUT_DUP_FEATID_INSIDE_DOC</code>.</td>
     * <td>A <code>TargetProjectLoadingException</code> exception must be launched. The exception
     * error list must only contain a duplicated feature ID inside document error.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openProjectWithDuplicatedFeatureInsideDocumentError() throws TargetException,
            IOException
    {
        try
        {
            this.openGeneralProjectWithDocuments(new String[] {
                    ProjectManagerTests.INPUT_CORRECT_DOC_1,
                    ProjectManagerTests.INPUT_DUP_FEATID_INSIDE_DOC });
            /* The previous statement must launch an exception */
            Assert.fail("This statement must not be reached.");
        }
        catch (TargetProjectLoadingException e)
        {
            Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = e.getErrors();

            Assert.assertEquals("There must only be one error", 1, errorList.size());
            Assert.assertTrue("There must be a duplicated feature ID inside document error", this
                    .hasError(DuplicatedFeatureIdInsideDocumentError.class, errorList));
        }
    }

    /**
     * This test verifies if a project is opened containing a duplicated step Id error.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_DUP_STEPID_DOC</code>.</td>
     * <td>The opened project must contain one document. It must only contain a duplicated step Id
     * error.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void openProjectWithDuplicatedStepIdError() throws TargetException, IOException
    {
        this
                .openGeneralProjectWithDocuments(new String[] { ProjectManagerTests.INPUT_DUP_STEPID_DOC });
        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        Assert.assertEquals("Opened project must contain one document", 1, tgtProject
                .getPhoneDocuments().size());

        Collection<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        Assert.assertEquals("There must only be one error", 1, errorList.size());
        Assert.assertTrue("There must be a duplicated step Id error", this.hasError(
                DuplicatedStepIdError.class, errorList));
    }

    /**
     * Auxiliary method that verifies if an error list contains at least one error of a specified
     * type.
     * 
     * @param errorClass The error class to be verified.
     * @param errorList The error list in which the error will be searched.
     * @return <code>True</code> if the error exists in the list. <code>False</code> otherwise.
     */
    private boolean hasError(Class errorClass,
            Collection<com.motorola.btc.research.target.pm.errors.Error> errorList)
    {
        boolean hasDuplicatedFeatureId = false;
        for (com.motorola.btc.research.target.pm.errors.Error error : errorList)
        {

            if (errorClass.equals(error.getClass()))
            {
                hasDuplicatedFeatureId = true;
                break;
            }
        }

        return hasDuplicatedFeatureId;
    }

    /**
     * Auxiliary method for creating and opening a project with some documents.
     * 
     * @param documents The documents that will be in the project.
     * @return The refreshing information of the opening task.
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    public TargetProjectRefreshInformation openGeneralProjectWithDocuments(String[] documents)
            throws IOException, TargetException
    {
        String tgtFile = PMUnitTestUtil.createProjectStructure(true, true);
        String projectPath = FileUtil.getFilePath(tgtFile);

        /* Copies the documents to the use case directory */
        String useCaseDir = projectPath + FileUtil.getSeparator() + TargetProject.UC_DIR;
        FileUtil.copyFiles(documents, useCaseDir, true);
        /* Verifies if the documents were copied */
        Assert.assertEquals("The Use Case directory must contains only " + documents.length
                + " Word documents", documents.length, FileUtil.loadFilesFromDirectory(useCaseDir,
                new WordFileFilter()).size());

        /* Opens the project */
        ProjectManagerController.getInstance().openProject(tgtFile);

        /* Loads project documents */
        TargetProjectRefreshInformation refreshingInfo = ProjectManagerController.getInstance()
                .loadOpenedProject();

        return refreshingInfo;
    }

    /**
     * Creates and opens an empty project. See method <code>generalEmptyProjectOpening</code>.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     */
    @Test
    public void openEmptyProject() throws TargetException
    {
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);
    }

    /**
     * This test creates an empty project with no directory structure and opens it. The open
     * activity must verify that there are no directory structure and creates it. See method
     * <code>generalEmptyProjectOpening</code>.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     */
    @Test
    public void openEmptyProjectWithoutProjectDirectories() throws TargetException
    {
        PMUnitTestUtil.generalEmptyProjectOpening(false, true);
    }

    /**
     * This test creates an empty project with directory structure, but without the .tgt file. It
     * tries to open the project but a <code>TargetException</code> is launched. See method
     * <code>generalEmptyProjectOpening</code>.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     */
    @Test(expected = TargetException.class)
    public void openEmptyProjectWithoutProjectFile() throws TargetException
    {
        PMUnitTestUtil.generalEmptyProjectOpening(true, false);
    }

    /**
     * <pre>
     * ########################################### 
     * ############## OTHER METHODS ##############
     * ###########################################
     */

    /**
     * This test verifies the behavior of the method <code>areFilesBeingReferred</code> of
     * <code>ProjectManagerController</code> class.
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
     * <td>Creates and opens an empty project (see method <code>generalEmptyProjectOpening</code>).
     * Import the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_CORRECT_DOC_2</code>
     * to the project. </td>
     * <td>The opened project must contain two documents. It must not contain errors.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>Calls the <code>ProjectManagerController.areFilesBeingReferred</code> in order to
     * verify if the document <code>INPUT_CORRECT_DOC_1</code> and a document with invalid name
     * are being referred.</td>
     * <td>The method must return a list only containing the document
     * <code>INPUT_CORRECT_DOC_2</code>.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>The documents <code>INPUT_DUP_UCID_DOC</code> and <code>INPUT_WRONGXML_DOC</code>
     * are added to the use cases folder and the project is refreshed.</td>
     * <td>The method must return an empty list.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void areFilesBeingReferred() throws TargetException, IOException
    {
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        String[] firstDocsImporting = new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_CORRECT_DOC_2 };

        this.importDocuments(firstDocsImporting);

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        /* The list of file to be verified if they are referred */
        List<String> verifyReferredDocs = new ArrayList<String>(tgtProject
                .getPhoneDocumentFilePaths());

        String ucDir = tgtProject.getUseCaseDir();
        String doc2Name = FileUtil.getFileName(ProjectManagerTests.INPUT_CORRECT_DOC_2);
        verifyReferredDocs.remove(ucDir + FileUtil.getSeparator() + doc2Name);

        /* Adding an invalid file name to be verified whether it is being referred */
        verifyReferredDocs.add("!!!! invalid file name !!!!");

        Collection<String> referredDocuments = ProjectManagerController.getInstance()
                .areFilesBeingReferred(verifyReferredDocs);

        /* The only document in the project that refers to another document */
        String refDoc = ucDir + FileUtil.getSeparator() + doc2Name;

        Assert.assertEquals("There must be only one document", 1, referredDocuments.size());
        Assert.assertEquals("There must be the document " + FileUtil.getFileName(refDoc)
                + "in the list", refDoc, referredDocuments.iterator().next());

        /* Adding a document that is not well formed and other with wrong xml */
        PMUnitTestUtil.addAndReloadProject(new String[] { ProjectManagerTests.INPUT_DUP_UCID_DOC,
                ProjectManagerTests.INPUT_WRONGXML_DOC });

        verifyReferredDocs = new ArrayList<String>(ProjectManagerController.getInstance()
                .getCurrentProject().getPhoneDocumentFilePaths());

        referredDocuments = ProjectManagerController.getInstance().areFilesBeingReferred(
                verifyReferredDocs);

        Assert.assertEquals("No document must be returned", 0, referredDocuments.size());
    }

    /**
     * Tests the existence of the use case setup.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void testUseCaseSetupExistence() throws TargetException, IOException
    {
        UseCase usecase = getUseCaseFromDocument("11111", "UC_01",
                ProjectManagerTests.INPUT_CORRECT_DOC_1);
        Assert.assertNotNull("The setup information was not found!", usecase.getSetup());
        Assert.assertEquals("The setup data was not correctly loaded", "Test Setup.", usecase
                .getSetup());
    }

    /**
     * Tests the existence of a flow step setup.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void testFlowStepSetup() throws TargetException, IOException
    {
        UseCase usecase = getUseCaseFromDocument("11111", "UC_01",
                ProjectManagerTests.INPUT_CORRECT_DOC_1);
        FlowStep flowStep = usecase.getFlowStepById(new StepId("11111", "UC_01", "1M"));
        Assert.assertNotNull("The setup information was not found!", flowStep.getSetup());
        Assert.assertEquals("The setup data was not corrwctly loaded", "Step Setup.", flowStep
                .getSetup());
    }

    /**
     * Returns the first use case of document INPUT_CORRECT_DOC_1.
     * 
     * @param docName The document where the use case id will be searched.
     * @param featureId The feature id.
     * @param usecaseId The use case id.
     * @return The use case given by its id.
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during file handling.
     */
    private UseCase getUseCaseFromDocument(String featureId, String usecaseId, String docName)
            throws TargetException, IOException
    {
        UseCase usecase = null;
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);
        String[] firstDocsImporting = new String[] { docName };
        this.importDocuments(firstDocsImporting);

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();
        for (Feature feature : tgtProject.getFeatures())
        {
            if (feature.getId().equals(featureId))
            {
                usecase = feature.getUseCase(usecaseId);
                if (usecase != null)
                {
                    break;
                }
            }
        }
        return usecase;
    }

    /**
     * This test verifies the behavior of the method <code>hasDocumentModification</code> of
     * <code>ProjectManagerController</code> class.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_CORRECT_DOC_2</code>.
     * </td>
     * <td>The method <code>hasDocumentModification</code> must not indicate document
     * modification.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>The document <code>INPUT_DUP_UCID_DOC</code> is added to the use cases folder.</td>
     * <td>The method <code>hasDocumentModification</code> must indicate document modification.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>The document <code>INPUT_CORRECT_DOC_1</code> is removed from the use cases folder and
     * a different document is added with the same name of the document
     * <code>INPUT_CORRECT_DOC_1</code>. The project is refreshed.</td>
     * <td>The method <code>hasDocumentModification</code> must indicate document modification.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void hasDocumentModification() throws IOException, TargetException
    {
        String[] documents = new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_CORRECT_DOC_2 };

        this.openGeneralProjectWithDocuments(documents);

        Assert.assertFalse("There is no document modification", ProjectManagerController
                .getInstance().hasDocumentModification());

        /* Copying a new document */
        FileUtil.copyFiles(new String[] { ProjectManagerTests.INPUT_DUP_UCID_DOC },
                ProjectManagerController.getInstance().getCurrentProject().getUseCaseDir(), true);

        Assert.assertTrue("There is a document modification. New file added",
                ProjectManagerController.getInstance().hasDocumentModification());

        ProjectManagerController.getInstance().reloadProject();

        this.simulateDocumentUpdating(ProjectManagerTests.INPUT_CORRECT_DOC_1,
                ProjectManagerTests.INPUT_DUP_FEATID_WITH_DOC_1);

        Assert.assertTrue("There is a document modification. File must have been updated",
                ProjectManagerController.getInstance().hasDocumentModification());
    }

    /**
     * This method simulates a document updating. It deletes a document from a folder, copies
     * another document to the folder and renames it.
     * 
     * @param documentToBeDeleted The path of the document to be deleted (the document that will be
     * renamed). However the whole path may be informed, only the document name is considered by the
     * method.
     * @param documentToBeCopied The document that will substitute the deleted document.
     * @throws IOException In case of any error during the files copying/deleting/renaming.
     */
    private void simulateDocumentUpdating(String documentToBeDeleted, String documentToBeCopied)
            throws IOException
    {
        /* Preparing the document to deletion */
        String fileName = FileUtil.getFileName(documentToBeDeleted);
        String useCaseDir = ProjectManagerController.getInstance().getCurrentProject()
                .getUseCaseDir()
                + FileUtil.getSeparator();
        String docToDelete = useCaseDir + fileName;

        FileUtil.deleteFiles(new String[] { docToDelete });

        /* Copying a document and renaming it to the previously deleted doc. */
        FileUtil.copyFiles(new String[] { documentToBeCopied }, ProjectManagerController
                .getInstance().getCurrentProject().getUseCaseDir(), true);
        FileUtil.renameFile(useCaseDir + FileUtil.getFileName(documentToBeCopied), docToDelete);
    }

    /**
     * This test verifies the behavior of the method <code>renameDocument</code> of
     * <code>ProjectManagerController</code> class.
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
     * <td>Creates and opens a project (see method <code>openGeneralProjectWithDocuments</code>)
     * with the documents <code>INPUT_CORRECT_DOC_1</code> and <code>INPUT_CORRECT_DOC_2</code>.
     * </td>
     * <td>The project is opened with the two documents.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>The document <code>INPUT_CORRECT_DOC_1</code> is renamed with the method
     * <code>renameDocument</code>.</td>
     * <td>The document is renamed. There is no document in the project with the name
     * <code>INPUT_CORRECT_DOC_1</code>.</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>It is tried to rename the document <code>INPUT_CORRECT_DOC_1</code> (it does not exist
     * anymore).</td>
     * <td>The method <code>renameDocument</code> must return <code>false</code>.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void renameWordDocument() throws IOException, TargetException
    {
        String docToBeRenamed = FileUtil.getFileName(ProjectManagerTests.INPUT_CORRECT_DOC_1);
        String[] documents = new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_2,
                ProjectManagerTests.INPUT_CORRECT_DOC_1 };

        this.openGeneralProjectWithDocuments(documents);

        String newDocName = "newName.doc";
        boolean result = ProjectManagerController.getInstance().renameDocument(docToBeRenamed,
                newDocName);

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        String ucDir = tgtProject.getUseCaseDir() + FileUtil.getSeparator();

        Assert.assertTrue("The rename operation must return true", result);

        Assert.assertNull("The document must not exist in the project", tgtProject
                .getPhoneDocumentFromFilePath(ucDir + docToBeRenamed));

        Assert.assertNotNull("The document must exist in the project", tgtProject
                .getPhoneDocumentFromFilePath(ucDir + newDocName));

        /* Try to rename again. It will return false since the doc was already renamed. */
        result = ProjectManagerController.getInstance().renameDocument(docToBeRenamed, newDocName);

        Assert.assertFalse("The document must have already been renamed", result);
    }

    /**
     * This test verifies the behavior of the method <code>renameDocument</code> of
     * <code>ProjectManagerController</code> class. It tests the renaming of an excel test suite.
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
     * <td>Creates an empty project and tries to rename an excel document that does not exist.</td>
     * <td>The method <code>renameDocument</code> must return <code>false</code>.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>The document <code>INPUT_TEST_SUITE_XLS</code> is copied to the test suites' folder
     * and renamed.</td>
     * <td>The document is renamed, and the method <code>renameDocument</code> must return
     * <code>false</code>.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void renameExcelDocument() throws IOException, TargetException
    {
        String xlsToBeRenamed = FileUtil.getFileName(ProjectManagerTests.INPUT_TEST_SUITE_XLS);
        String newXLSName = "newName.xls";

        this.openEmptyProject();

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        /* Tries to rename, however, there is no file to be renamed */
        boolean result = ProjectManagerController.getInstance().renameDocument(xlsToBeRenamed,
                newXLSName);

        Assert.assertTrue("There is no file to be renamed. No file must be renamed", !result);

        FileUtil.copyFiles(new String[] { ProjectManagerTests.INPUT_TEST_SUITE_XLS }, tgtProject
                .getTestSuiteDir(), true);

        result = ProjectManagerController.getInstance().renameDocument(xlsToBeRenamed, newXLSName);

        Assert.assertTrue("The test suite file could not be renamed", result);
    }

    /**
     * This test verifies the behavior of the method <code>renameDocument</code> of
     * <code>ProjectManagerController</code> class. It tests the renaming of a file with invalid
     * extension, i.e., the file is not a MS Excel file neither a MS Word file. The method
     * <code>renameDocument</code> is called and must return <code>false</code>.
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void renameOtherDocument() throws IOException, TargetException
    {
        String xlsToBeRenamed = "fileToBeRenamed.txt";
        String newXLSName = "newName.txt";

        this.openEmptyProject();

        boolean result = ProjectManagerController.getInstance().renameDocument(xlsToBeRenamed,
                newXLSName);

        Assert.assertTrue("The file must not be renamed, since it is invalid", !result);
    }

    /**
     * This test verifies the behavior of the method <code>getTestSuiteDocuments</code> of
     * <code>ProjectManagerController</code> class.
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
     * <td>Creates an empty project.</td>
     * <td>There must not be any test suite in the project. The method
     * <code>getTestSuiteDocuments</code> must return an empty collection.</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>The document <code>INPUT_TEST_SUITE_XLS</code> is copied to the test suites' folder.</td>
     * <td>There must be one test suite in the project. The method
     * <code>getTestSuiteDocuments</code> must return a collection containing the copied test
     * suite.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void getTestSuiteDocuments() throws IOException, TargetException
    {
        String testSuiteName = FileUtil.getFileName(ProjectManagerTests.INPUT_TEST_SUITE_XLS);
        this.openEmptyProject();
        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        Collection<String> testSuites = ProjectManagerController.getInstance()
                .getTestSuiteDocuments();

        Assert.assertEquals("There should not be any test suite in the project", 0, testSuites
                .size());

        FileUtil.copyFiles(new String[] { ProjectManagerTests.INPUT_TEST_SUITE_XLS }, tgtProject
                .getTestSuiteDir(), true);

        testSuites = ProjectManagerController.getInstance().getTestSuiteDocuments();

        Assert.assertEquals("There must be only one test suite in the project", 1, testSuites
                .size());
        Assert.assertTrue("There should be only one test suite in the project, which name is "
                + testSuiteName, testSuites.contains(tgtProject.getTestSuiteDir()
                + FileUtil.getSeparator() + testSuiteName));
    }


    
    /**
     * This test verifies the behavior of the method
     * <code>getAllReferencedRequirementsOrdered</code> of <code>ProjectManagerController</code>
     * class.
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
     * <td>Creates and opens a project containing the document <code>INPUT_CORRECT_DOC_1</code>.
     * The method <code>getAllReferencedRequirementsOrdered</code> is called. </td>
     * <td>The method <code>getAllReferencedRequirementsOrdered</code> must return a set of
     * requirements (i.e., no repetition of elements). There must be 6 elements in the set.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void getAllReferencedRequirementsOrdered() throws IOException, TargetException
    {
        String[] documents = new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 };

        this.openGeneralProjectWithDocuments(documents);

        Collection<String> requirements = ProjectManagerController.getInstance()
                .getAllReferencedRequirementsOrdered();

        boolean noRepetition = true;
        HashSet<String> set = new HashSet<String>();

        for (String req : requirements)
        {
            if (set.contains(req))
            {
                noRepetition = false;
                break;
            }
            else
            {
                set.add(req);
            }
        }
        Assert.assertTrue("The requirements list contains repeated requirements", noRepetition);

        Assert.assertEquals("The requirements list should have 6 elements. It has "
                + requirements.size(), 6, requirements.size());
    }

    /**
     * This test verifies the behavior of the method <code>isDocumentWellFormed</code> of
     * <code>ProjectManagerController</code> class.
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
     * <td>Creates and opens a project containing the document <code>INPUT_CORRECT_DOC_1</code>.
     * The document <code>INPUT_DUP_FEATID_WITH_DOC_1</code> is added to the project, and it is
     * refreshed. </td>
     * <td>The document <code>INPUT_CORRECT_DOC_1</code> must be considered well formed, and the
     * document <code>INPUT_DUP_FEATID_WITH_DOC_1</code> must not be well formed.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void isDocumentWellFormed() throws IOException, TargetException
    {
        String[] documents = new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_1 };

        this.openGeneralProjectWithDocuments(documents);

        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_DUP_FEATID_WITH_DOC_1 });

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();
        String doc1 = tgtProject.getUseCaseDir() + FileUtil.getSeparator()
                + FileUtil.getFileName(ProjectManagerTests.INPUT_CORRECT_DOC_1);
        String dupFeatureId = tgtProject.getUseCaseDir() + FileUtil.getSeparator()
                + FileUtil.getFileName(ProjectManagerTests.INPUT_DUP_FEATID_WITH_DOC_1);

        Assert.assertTrue("The document should be considered well formed", ProjectManagerController
                .getInstance().isDocumentWellFormed(doc1));

        Assert.assertTrue("The document should not be considered well formed",
                !ProjectManagerController.getInstance().isDocumentWellFormed(dupFeatureId));
    }

    /**
     * This test verifies the behavior of the method <code>hasUseCaseError</code> of
     * <code>ProjectManagerController</code> class.
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
     * <td>Creates an empty project. The document <code>INPUT_DUP_STEPID_DOC</code>,
     * <code>INPUT_DUP_UCID_DOC</code> and <code>INPUT_CORRECT_DOC_1</code> are added to the
     * project, and it is refreshed. </td>
     * <td>The use case UC_01 from the feature 11115 and the use case from the feature 11114 must
     * contain errors.</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @throws TargetException In case of any error during the project creation/opening/refreshing.
     * @throws IOException In case of any error during file handling.
     */
    @Test
    public void hasUseCaseError() throws IOException, TargetException
    {
        String[] documents = new String[] { ProjectManagerTests.INPUT_DUP_STEPID_DOC,
                ProjectManagerTests.INPUT_CORRECT_DOC_2 };

        this.openEmptyProject();
        PMUnitTestUtil.addAndReloadProject(documents);

        TargetProject tgtProject = ProjectManagerController.getInstance().getCurrentProject();

        /* Gets the feature and the use case that contains the error */
        Feature f1 = tgtProject.getPhoneDocumentFromFilePath(
                tgtProject.getUseCaseDir() + FileUtil.getSeparator()
                        + FileUtil.getFileName(ProjectManagerTests.INPUT_DUP_STEPID_DOC))
                .getFeature("11115");
        UseCase uc1 = f1.getUseCase("UC_01");

        Assert.assertTrue("The use case 11115#UC_01 should contain errors",
                ProjectManagerController.getInstance().hasUseCaseError(uc1, f1));

        Feature f2 = tgtProject.getPhoneDocumentFromFilePath(
                tgtProject.getUseCaseDir() + FileUtil.getSeparator()
                        + FileUtil.getFileName(ProjectManagerTests.INPUT_CORRECT_DOC_2))
                .getFeature("22222");
        UseCase uc2 = f2.getUseCase("UC_01");

        Assert.assertTrue("The use case 22222#UC_01 should contain errors",
                ProjectManagerController.getInstance().hasUseCaseError(uc2, f2));
    }

}