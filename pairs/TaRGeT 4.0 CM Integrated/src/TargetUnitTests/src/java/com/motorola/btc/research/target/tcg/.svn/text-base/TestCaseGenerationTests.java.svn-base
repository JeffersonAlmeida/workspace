/*
 * @(#)TestCaseGenerationTests.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Apr 10, 2007   LIBkk22882   Initial creation.
 * dhq348    Aug 09, 2007   LIBmm42774   Added test classes from filters and pruners
 * wfo007    Aug 22, 2007   LIBmm42774   Added a document to the list of documents used to test.
 * dhq348    Feb 15, 2008   LIBoo24851   Added interruption related documents.
 * dwvm83	 Sep 18, 2008	LIBqq51204   Added three documents to test merge feature.
 * dwvm83	 Sep 23, 2008	LIBqq51204	 Added empty flow related document.	
 */
package com.motorola.btc.research.target.tcg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.tcg.controller.TestCaseGeneratorControllerTest;
import com.motorola.btc.research.target.tcg.excel.ExcelFileFormatterTest;
import com.motorola.btc.research.target.tcg.filters.CompositionTestSuiteFilterTest;
import com.motorola.btc.research.target.tcg.filters.DefaultTestSuiteFilterTest;
import com.motorola.btc.research.target.tcg.filters.PurposeFilterTest;
import com.motorola.btc.research.target.tcg.filters.RequirementsFilterTest;
import com.motorola.btc.research.target.tcg.filters.SimilarityFilterTest;
import com.motorola.btc.research.target.tcg.filters.TestSuiteFilterAssemblerTest;

/**
 * This class represents the test suite from TaRGeT Test Case Generation plug-in. It also
 * encapsulates common attributes for the suite's unit tests.
 */
@RunWith(Suite.class)
// INSPECT
@SuiteClasses( { TestCaseGeneratorControllerTest.class, ExcelFileFormatterTest.class,
        CompositionTestSuiteFilterTest.class, DefaultTestSuiteFilterTest.class,
        RequirementsFilterTest.class, TestSuiteFilterAssemblerTest.class,
        SimilarityFilterTest.class, PurposeFilterTest.class })
public class TestCaseGenerationTests
{
    /** Folder that stores the outputs produced by the unit tests */
    public static final String OUTPUT_FOLDER = UnitTestUtil.getTestOutputDirectoryPath("TCGTests");

    /** Folder that stores the inputs used by the unit tests */
    public static final String INPUT_FOLDER = UnitTestUtil.getTestInputDirectoryPath("tcg");

    /**
     * Use case document with 25 use cases. It is a modified version of Target use case document
     * Version 01.03 to force some test generation scenarios.
     */
    public static final String HUGE_USE_CASE_DOCUMENT = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "targetUCDoc_modified.doc";

    /** Use case document whose flows are associated with at least one requirement id */
    public static final String ALL_FLOWS_WITH_REQID_UC_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "phonebook.doc";

    /** Use case document whose flow contains a loop, with the same from and to steps. */
    public static final String FLOW_WITH_LOOP_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "loopdoc.doc";

    /** Use case document that contains a reference to ALL_FLOWS_WITH_REQID_UC_DOC */
    public static final String REFERS_OTHER_DOC_UC_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "messaging.doc";

    /**
     * Use case document that uses multiple step references in 'from' and 'to' fields.
     */
    public static final String MULTIPLE_REFERENCES_UC_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "ucdoc1.doc";

    /**
     * Use case document that is semantically equivalent but syntactically different to
     * MULTIPLE_REFERENCES_UC_DOC.
     */
    public static final String EQUIVALENT_UC_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "ucdoc2.doc";

    /**
     * Use case document whose generated tests are in the inverse order of the tests generated from
     * EQUIVALENT_UC_DOC
     */
    public static final String INVERTED_UC_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "ucdoc3.doc";
    
    /**
     * Use case document with a feature that has use case 1
     * 
     */
    public static final String F_UCA_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "ucdocA.doc";
    
    /**
     * Use case document with a feature (the same feature as F_UCA_DOC)that has use case 2
     * 
     */
    public static final String F_UCB_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "ucdocB.doc";
    
     /** Use case document that has a feature that is the merge of the ones in 
     * F_UCA_DOC and F_UCBS_DOC*/
    public static final String MERGED_FEATURES_UAB_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "ucdocAB.doc";
 
    /** Use case document that contains a duplicated interruption */
    public static final String EMPTY_FLOW_DESC_UC_DOC = TestCaseGenerationTests.INPUT_FOLDER
            + FileUtil.getSeparator() + "empty_flow_desc_ucdoc.doc";
 

}
