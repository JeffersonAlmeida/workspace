/*
 * @(#)ExcelFileFormatterTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Apr 10, 2007   LIBkk22882   Initial creation.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * dhq348    Jul 17, 2007   LIBmm76995   Added the tests testGetUseCasesAsStringFromRequirement and testGetTestCasesFromRequirement.
 * wdt022    Jun 18, 2007   LIBmm42774   Modifications due to CR.
 * dhq348    Aug 16, 2007   LIBmm42774   Rework after inspection LX199806.
 * dhq348    Aug 21, 2007   LIBmm42774   Rework after inspection LX201094. Removed method getPhoneDocumentByDocName() now the class uses UnitTestUtil.getPhoneDocument().
 * wln013    Mar 25, 2008   LIBpp56482   Removed the pruner assembler from the method buildExcelFileFormatter.
 * dwvm83    Oct 09, 2008	LIBqq51204	 Added project creation in BeforeClass 
 * dwvm83	 Oct 17, 2008				 Fixed method testGetUseCasesFromRequirement	
 */
package com.motorola.btc.research.target.tcg.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.common.lts.UserViewLTSGenerator;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.pm.ProjectManagerTests;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;
import com.motorola.btc.research.target.tcg.controller.TestCaseGeneratorController;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.filters.TestSuiteFilterAssembler;


/**
 * This class encapsulates the unit tests for ExcelFileFormatter class.
 */
public class ExcelFileFormatterTest
{
    /** Loaded PhoneDocument from HUGE_USE_CASE_DOCUMENT */
    private static PhoneDocument targetUCDoc;

    /** A default formatter */
    private static ExcelFileFormatter formatter;

    /**
     * Load the use case documents (static attributes) that will be used as input for test
     * generation tests.
     * 
     * @throws UseCaseDocumentXMLException Thrown in case of a problem occurs while loading the use
     * case documents.
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws UseCaseDocumentXMLException,
            TestGenerationException
    {
    	//INSPECT code added to create the project in memory
        long time = System.currentTimeMillis();
        String projectName = "TestCreate_" + time;
        
        /* Creates the TaRgeT project in Memory */
        ProjectManagerController.getInstance().createProject(projectName,ProjectManagerTests.OUTPUT_FOLDER);

        targetUCDoc = UnitTestUtil.getPhoneDocument(TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT);

        PhoneDocument document = UnitTestUtil
                .getPhoneDocument(TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC);
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(document);
        formatter = buildExcelFileFormatter(docs);
        
   }

    /**
     * Tests the generation of test suite excel files from the document
     * <code>TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT</code>.
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     * @throws IOException Thrown if there is a problem while writing the excel test suite file.
     */
    @Test
    public void generateTestSuiteFileForTargetDocument() throws TestGenerationException,
            IOException
    {

        /* Sets the input use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(targetUCDoc);

        /* Write the output test suite excel file */
        File testSuiteFile = TestCaseGeneratorController.getInstance().generateTestSuiteFile(
                TestCaseGenerationTests.OUTPUT_FOLDER);
        buildExcelFileFormatter(docs).writeTestCaseDataInFile(testSuiteFile);

        Assert.assertTrue("The test cases file was not generated.", new File(testSuiteFile
                .getAbsolutePath()).exists());
    }

    /**
     * Tests the generation of an excel test suite file from an empty list of test cases.
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     * @throws IOException Thrown if there is a problem while writing the excel test suite file.
     */
    @Test
    public void generateTestSuiteFileFromEmptyTestCaseList() throws TestGenerationException,
            IOException
    {

        /* Initializes the excel file formatter with an empty list of tests */
        ExcelFileFormatter eff = new ExcelFileFormatter(new ArrayList<TextualTestCase>());

        /* Write the output test suite excel file */
        TestCaseGeneratorController tcc = TestCaseGeneratorController.getInstance();
        File testSuiteFile = tcc.generateTestSuiteFile(TestCaseGenerationTests.OUTPUT_FOLDER);
        eff.writeTestCaseDataInFile(testSuiteFile);
    }

    /**
     * Tests the method getUseCasesFromRequirement(). Tests if the method is correctly retrieving
     * the use cases.
     * 
     * @throws UseCaseDocumentXMLException Thrown in case of a problem occurs while loading the use
     * case documents.
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void testGetUseCasesFromRequirement() throws UseCaseDocumentXMLException, IOException, 
     TestGenerationException, TargetException
    {
    	//Code added to associate the document HUGE_USE_CASE_DOCUMENT to the Target Project by calling the 
    	//loadImportedDocumentsIntoProject method
    	Collection<File> files = new ArrayList<File>();
        files.add(new File(TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT));
      	ProjectManagerController.getInstance().loadImportedDocumentsIntoProject(files);
       
        List<String> usecases = formatter.getUseCasesFromRequirement("FR_TARGET_0220");
        
        Assert.assertTrue("The specified use case is in the list.", usecases.contains("UC_01"));
        Assert.assertFalse("The specified use case should not be in the list.", usecases
                .contains("UC_05"));
    }

    /**
     * Tests the method getTestCasesFromRequirement(). Checks if the method returns at least one
     * test case.
     * 
     * @throws UseCaseDocumentXMLException Thrown in case of a problem occurs while loading the use
     * case documents.
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void testGetTestCasesFromRequirement() throws UseCaseDocumentXMLException,
            TestGenerationException
    {
        Assert.assertFalse("There must be at least one test case.", formatter
                .getTestCasesFromRequirement("TRS_11111_101").isEmpty());
    }

    /**
     * Tests the method getTestCaseReferences() from ExcelFileFormatter.
     */
    @Test
    public void testGetTestCaseReferences()
    {
        List<String> list = formatter.getTestCaseReferences("11111#UC_01");
        Assert
                .assertFalse("The use case 11111#UC_01 must be in the returned list.", list
                        .isEmpty());
        Assert.assertEquals("Wrong number of test cases.", 4, list.size());
    }

    /**
     * Builds an ExcelFileFormatter object given a list of <code>documents</code>.
     * 
     * @param documents The documents that will be used to build the ExcelFileFormatter.
     * @return A new ExcelFileFormatter object.
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    private static ExcelFileFormatter buildExcelFileFormatter(ArrayList<PhoneDocument> documents)
            throws TestGenerationException
    {
        /* Sets the LTS model for the input document */
        LTS<FlowStep, Integer> lts = new UserViewLTSGenerator().generateLTS(documents);
        TestCaseGeneratorController tcc = TestCaseGeneratorController.getInstance();

        /* Generates tests for the input document */
        TestSuiteFilterAssembler tsfa = new TestSuiteFilterAssembler();
        

        
        TestSuite<TextualTestCase> ts = tcc.generateTests(tsfa, lts, documents);
        return new ExcelFileFormatter(ts.getTestCases());
    }

}
