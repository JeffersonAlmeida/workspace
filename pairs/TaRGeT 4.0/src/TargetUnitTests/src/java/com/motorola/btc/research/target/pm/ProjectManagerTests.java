/*
 * @(#)ProjectManagerTests.java
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
 * dhq348    Jun 29, 2007   LIBmm63120   Added .
 * dhq348    Jun 19, 2007   LIBmm47221   Added WRONG_FEATURE_NAME_WITH_CORRECT_DOC_1.
 * dhq348    Jul 11, 2007   LIBmm47221   Rework after inspection LX185000. Changed WRONG_FEATURE_NAME_WITH_CORRECT_DOC_1 to DUPLICATED_FEATURE_NAME_WITH_CORRECT_DOC.
 * dhq348    Feb 21, 2008   LIBoo89937   Added INTERRUPTION_ERRORS.
 * dhq348    Feb 22, 2008   LIBoo89937   Rework after inspection LX246152.
 */
package com.motorola.btc.research.target.pm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.controller.ProjectManagerControllerTest;
import com.motorola.btc.research.target.pm.controller.TargetProjectRefreshInformationTest;
import com.motorola.btc.research.target.pm.controller.TargetProjectTest;
import com.motorola.btc.research.target.pm.editors.UseCaseHTMLGeneratorTest;
import com.motorola.btc.research.target.pm.search.SearchControllerTests;

/**
 * This class comprehends the unit tests for the Project Manager (PM) Plugin. It aggregates all
 * other PM test suites.
 * <p>
 * This class also defines some constants that are used by the test suites.
 */
@RunWith(Suite.class)
@SuiteClasses( { ProjectManagerControllerTest.class, TargetProjectRefreshInformationTest.class,
        TargetProjectTest.class, UseCaseHTMLGeneratorTest.class, SearchControllerTests.class })
public class ProjectManagerTests
{

    /** This constant stores the folder that is used as temporary folder */
    public static final String OUTPUT_FOLDER = UnitTestUtil.getTestOutputDirectoryPath("PMTests");

    /** This constant stores the path of the folder that contains the unit test inputs */
    public static final String INPUT_FOLDER = UnitTestUtil.getTestInputDirectoryPath("pm");

    /** Path to a document without any error */
    public static final String INPUT_CORRECT_DOC_1 = INPUT_FOLDER + FileUtil.getSeparator()
            + "correct_doc_1.doc";

    /** Path to a document that refers to a step of document <code>INPUT_CORRECT_DOC_1</code> */
    public static final String INPUT_CORRECT_DOC_2 = INPUT_FOLDER + FileUtil.getSeparator()
            + "correct_doc_2.doc";

    /** Path to a document that does not contain any XML content */
    public static final String INPUT_NOXML_DOC = INPUT_FOLDER + FileUtil.getSeparator()
            + "noxml_doc.doc";

    /** Path to a document that contains an invalid XML content */
    public static final String INPUT_WRONGXML_DOC = INPUT_FOLDER + FileUtil.getSeparator()
            + "wrongxml_doc.doc";

    /**
     * Path to a document that contains a feature with the same Id of a feature from document
     * <code>INPUT_CORRECT_DOC_1</code>
     */
    public static final String INPUT_DUP_FEATID_WITH_DOC_1 = INPUT_FOLDER + FileUtil.getSeparator()
            + "dup_featid_with_doc_1.doc";

    /** Path to a document that contains two documents with the same Id */
    public static final String INPUT_DUP_FEATID_INSIDE_DOC = INPUT_FOLDER + FileUtil.getSeparator()
            + "dup_featid_inside_doc.doc";

    /** Path to a document that contains a step without a specified Id */
    public static final String INPUT_MISSING_STEPID_DOC = INPUT_FOLDER + FileUtil.getSeparator()
            + "missing_stepid_doc.doc";

    /** Path to a document that contains two use cases (in the same feature) with equal Ids */
    public static final String INPUT_DUP_UCID_DOC = INPUT_FOLDER + FileUtil.getSeparator()
            + "dup_ucid_doc.doc";

    /** Path to a document that contains a duplicated step Id */
    public static final String INPUT_DUP_STEPID_DOC = INPUT_FOLDER + FileUtil.getSeparator()
            + "dup_stepid_doc.doc";

    /** Path to a correct test suite */
    public static final String INPUT_TEST_SUITE_XLS = INPUT_FOLDER + FileUtil.getSeparator()
            + "testSuite.xls";

    /**
     * Path to a document that contains one use case with a missing action field and another use
     * case with a missing response field
     */
    public static final String INPUT_MISSING_ACTION_RESPONSE_DOC = INPUT_FOLDER
            + FileUtil.getSeparator() + "missing_action_response_doc.doc";

    /**
     * Path to a document that contains a feature with the same id as one present in correct doc 1
     * but with a different name.
     */
    public static final String DUPLICATED_FEATURE_NAME_WITH_CORRECT_DOC = INPUT_FOLDER
            + FileUtil.getSeparator() + "duplicated_feature_name_with_correct_doc1.doc";

    /**
     * Path to a document that contains a duplicated feature as one in doc 1.
     */
    public static final String CORRECT_DUPLICATED_FEATURE_WITH_DOC1 = INPUT_FOLDER
            + FileUtil.getSeparator() + "correct_duplicated_feature_with_doc1.doc";

    
}
