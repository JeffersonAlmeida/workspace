/*
 * @(#)HTMLGeneratorTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jun 29, 2007    LIBmm63120   Initial creation.
 * dhq348   Jul 04, 2007    LIBmm63120   Rework due to inspection LX188519.
 * dhq348   Jan 15, 2008    LIBnn34008   Changed class to UseCaseHTMLGeneratorTest.
 */
package com.motorola.btc.research.target.pm.editors;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.pm.PMUnitTestUtil;
import com.motorola.btc.research.target.pm.ProjectManagerTests;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.errors.EmptyUseCaseFieldError;

/**
 * This class comprehends the unit tests for the <code>HTMLGenerator</code> class. It tests the
 * methods <code>HTMLGenerator.hasFromOrToStepError</code>,
 * <code>HTMLGenerator.hasDuplicatedStepIdError</code> and
 * <code>HTMLGenerator.hasEmptyUseCaseFieldError</code>.
 */
public class UseCaseHTMLGeneratorTest
{
    /**
     * Tests if the current project has an Invalid Step Id Reference Error. It creates an empty
     * project, loads the document INPUT_CORRECT_DOC_2 and verifies the errors using the step id
     * 11111#UC_02#4M.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during the copying of files to the use cases
     * directory.
     */
    @Test
    public void testHasFromOrToStepError() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Importing a new document to the project and reloading */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_CORRECT_DOC_2 });

        Assert.assertEquals("There must be one imported document", 1, ProjectManagerController
                .getInstance().getCurrentProject().getPhoneDocuments().size());

        List<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        StepId stepId = new StepId("11111", "UC_02", "4M");

        UseCaseHTMLGenerator generator = new UseCaseHTMLGenerator();
        Assert.assertTrue("There is no InvalidStepIdReferenceError.", generator
                .hasFromOrToStepError(stepId, errorList));

    }

    /**
     * Tests if the current project has an Duplicated Step Id Error. It creates an empty project,
     * loads the document INPUT_DUP_STEPID_DOC and verifies the errors using the step id
     * 11115#UC_01#1M.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during the copying of files to the use cases
     * directory.
     */
    @Test
    public void testHasDuplicatedStepIdError() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Imports the document with duplicated step id error */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_DUP_STEPID_DOC });

        List<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        StepId stepId = new StepId("11115", "UC_01", "1M");

        UseCaseHTMLGenerator generator = new UseCaseHTMLGenerator();
        Assert.assertTrue("There is no InvalidStepIdReferenceError.", generator
                .hasDuplicatedStepIdError(stepId, errorList));

    }

    /**
     * Tests if the current project has an Empty Use Case Field Error. It creates an empty project,
     * loads the document INPUT_MISSING_STEPID_DOC and verifies the errors using the step id
     * 11115#UC_01# and verifying the field EmptyUseCaseFieldError.STEP_ID.
     * 
     * @throws TargetException In case of any error during the project creation/opening.
     * @throws IOException In case of any error during the copying of files to the use cases
     * directory.
     */
    @Test
    public void testHasEmptyUseCaseFieldError() throws TargetException, IOException
    {
        /* Creates a new project and gets the project instance */
        PMUnitTestUtil.generalEmptyProjectOpening(true, true);

        /* Imports the document with missing step id error */
        PMUnitTestUtil
                .addAndReloadProject(new String[] { ProjectManagerTests.INPUT_MISSING_STEPID_DOC });

        List<com.motorola.btc.research.target.pm.errors.Error> errorList = ProjectManagerController
                .getInstance().getErrorList();

        StepId stepId = new StepId("11113", "UC_01", "");

        UseCaseHTMLGenerator generator = new UseCaseHTMLGenerator();
        Assert
                .assertTrue("There is no InvalidStepIdReferenceError.", generator
                        .hasEmptyUseCaseFieldError(stepId, errorList,
                                EmptyUseCaseFieldError.STEP_ID, 1, 0));

    }
}
