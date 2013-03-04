/*
 * @(#)ConsistencyManagementController.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.motorola.btc.research.target.cm.tcsimilarity.logic.ComparisonResult;
import com.motorola.btc.research.target.cm.tcsimilarity.logic.SimilarTC;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.controller.TestCaseGeneratorController;
import com.motorola.btc.research.target.tcg.extensions.extractor.ExtractorDocumentExtension;
import com.motorola.btc.research.target.tcg.extensions.extractor.ExtractorDocumentExtensionFactory;
import com.motorola.btc.research.target.tcg.extensions.output.OutputDocumentData;
import com.motorola.btc.research.target.tcg.extensions.output.OutputDocumentExtensionFactory;
import com.motorola.btc.research.target.tcg.extractor.TextualStep;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * This class encapsulates the tool's logic related to consistency management functionalities.
 * 
 * RESPONSIBILITIES:
 * 1) Provide services to the GUI regarding consistency management.
 *
 * COLABORATORS:
 * 1) Uses SimilarTC to compare test cases. 
 *
 * USAGE:
 * ConsistencyManagementController controller = ConsistencyManagementController.getInstance();
 * controller.compareTestSuites(<File>, <List<TextualTestCase>>);
 */
//INSPECT Felype
public class ConsistencyManagementController
{
    /** Unique instance of this class (singleton) */
    private static ConsistencyManagementController instance;

    //INSPECT Felype
    private ExtractorDocumentExtension extractorDocumentExtension;

    /**
     * Return a reference to the singleton of this class.
     * 
     * @return The singleton.
     */
    public static ConsistencyManagementController getInstance()
    {
        if (instance == null)
        {
            instance = new ConsistencyManagementController();
        }
        return instance;
    }

    /**
     * Initializes the controller.
     */
    private ConsistencyManagementController()
    {
    }

    /**
     * Reads a excel file and extracts a list of textual test cases.
     * 
     * @param original The Excel file to be read.
     * @return A list of textual test cases representing the test cases in the file.
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File excelFile) throws FileNotFoundException,
    IOException
    {
        //INSPECT Felype
        if(this.extractorDocumentExtension == null)
        {
            this.inicializeExtractorExtensionsList();
        }

        return this.extractorDocumentExtension.extractTestSuite(excelFile);
    }

    private void inicializeExtractorExtensionsList()
    {
        Collection<ExtractorDocumentExtension> extractorExtensionsList = ExtractorDocumentExtensionFactory.extractorExtensions();

        for (ExtractorDocumentExtension extractorDocumentExtension : extractorExtensionsList)
        {
            this.extractorDocumentExtension = extractorDocumentExtension;
        }
    }

    /**
     * Compares a new test case with all test cases of an old test suite.
     * 
     * @param newTestCase The new test case.
     * @param oldTestCases A list of old Textual Test Cases.
     * @return A list of comparison results.
     */
    public List<ComparisonResult> compare(TextualTestCase newTestCase,
            List<TextualTestCase> oldTestCases)
            {
        SimilarTC similarTC = new SimilarTC();

        return similarTC.compare(newTestCase, oldTestCases);
            }

    /**
     * Merges every pair of test cases in the given map and generates a new excel file containing a
     * test suite with the result of the merging.
     * 
     * @param testCases A map from new test cases to old test cases to be merged.
     * @throws IOException Exception launched in case something goes wrong during the file writing.
     */
    public void mergeAndGenerate(HashMap<TextualTestCase, TextualTestCase> testCases)
    throws IOException
    {
        List<TextualTestCase> result = new ArrayList<TextualTestCase>();

        for (TextualTestCase newTestCase : testCases.keySet())
        {
            TextualTestCase oldTestCase = testCases.get(newTestCase);
            TextualTestCase merged = newTestCase;
            if (oldTestCase != null)
            {
                merged = this.merge(newTestCase, oldTestCase);
            }
            result.add(merged);
        }

        Collections.sort(result);

        generateExcelFile(result);
    }

    /**
     * Auxiliary method that actually performs the merging of two test cases.
     * 
     * @param newTestCase The new test case to be merged.
     * @param oldTestCase The old test case to be merged.
     * @return The test case which is the result of the merging.
     */
    private TextualTestCase merge(TextualTestCase newTestCase, TextualTestCase oldTestCase)
    {
        List<TextualStep> steps = newTestCase.getSteps();
        String requirements = newTestCase.getRequirements();
        String initialConditions = newTestCase.getInitialConditions();
        String useCaseReferences = newTestCase.getUseCaseReferences();
        String setups = newTestCase.getSetups();

        int id = oldTestCase.getId();//keep the old id
        String description = oldTestCase.getDescription();
        String objective = oldTestCase.getObjective();
        String note = oldTestCase.getNote();
        String executionType = oldTestCase.getExecutionType();
        String regressionLevel = oldTestCase.getRegressionLevel();
        String finalConditions = oldTestCase.getFinalConditions();
        String cleanup = oldTestCase.getCleanup();


        ///////////////////////////////////////////////////INSPECT Felype
        String status = oldTestCase.getStatus();
        String tcIdHeader = oldTestCase.getTcIdHeader();

        TextualTestCase merged = new TextualTestCase(id, tcIdHeader, steps, executionType, regressionLevel,
                description, objective, requirements, setups, initialConditions, note, finalConditions,
                cleanup, useCaseReferences, status);



        return merged;
    }

    /**
     * Auxiliary method that actually generates an excel file with the given test suite.
     * 
     * @param testCases The list of test cases to be written in an excel file.
     * @throws IOException Exception launched in case something goes wrong during the file writing.
     */
    private void generateExcelFile(List<TextualTestCase> testCases) throws IOException
    {
        if(TestCaseGeneratorController.getInstance().getOutputDocumentExtension() == null)
        {
            TestCaseGeneratorController.getInstance().inicializeOutputExtensionsList();
        }

        TestCaseGeneratorController.getInstance().getOutputDocumentExtension().setTestCases(testCases);
        String testSuiteDir = ProjectManagerExternalFacade.getInstance().getCurrentProject()
        .getTestSuiteDir();

        TestCaseGeneratorController.getInstance().getOutputDocumentExtension().writeTestCaseDataInFile(TestCaseGeneratorController.getInstance()
                .generateTestSuiteFile(testSuiteDir));
    }

}
