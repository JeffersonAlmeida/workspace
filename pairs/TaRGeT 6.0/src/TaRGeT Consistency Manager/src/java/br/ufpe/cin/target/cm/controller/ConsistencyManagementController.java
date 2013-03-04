/*
 * @(#)ConsistencyManagementController.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000     Initial creation.
 * fsf2     20/06/2009					 Inclusion of ExtractorDocumentExtension
 * lmn3		20/06/2009					 Inclusion of an attribute to store the test suite file
 * lmn3		20/06/2009					 Changes due code inspection.
 */
package br.ufpe.cin.target.cm.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.ufpe.cin.target.cm.tcsimilarity.logic.ComparisonResult;
import br.ufpe.cin.target.cm.tcsimilarity.logic.SimilarTC;
import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.tcg.controller.TestCaseGeneratorController;
import br.ufpe.cin.target.tcg.extensions.extractor.ExtractorDocumentExtension;
import br.ufpe.cin.target.tcg.extensions.extractor.ExtractorDocumentExtensionFactory;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties;


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
public class ConsistencyManagementController
{
    /** Unique instance of this class (singleton) */
    private static ConsistencyManagementController instance;

    private ExtractorDocumentExtension extractorDocumentExtension;
    
    private File selectedTestSuite;

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
     * @param headers 
     * 
     * @param original The Excel file to be read.
     * @return A list of textual test cases representing the test cases in the file.
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File excelFile) throws FileNotFoundException,
            IOException
    {
    	this.selectedTestSuite = excelFile;
    	
        if(this.extractorDocumentExtension == null)
        {
            this.inicializeExtractorExtensionsList();
        }

        return this.extractorDocumentExtension.extractTestSuite(excelFile);
    }

    private void inicializeExtractorExtensionsList()
    {
        Collection<ExtractorDocumentExtension> extractorExtensionsList = ExtractorDocumentExtensionFactory
                .extractorExtensions();

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
     * @throws TargetException
     */
    public List<TextualTestCase> mergeAndGenerate(
            HashMap<TextualTestCase, TextualTestCase> testCases) throws IOException,
            TargetException
    {
        List<TextualTestCase> result = new ArrayList<TextualTestCase>();

        for (TextualTestCase newTestCase : testCases.keySet())
        {
            TextualTestCase oldTestCase = testCases.get(newTestCase);
            TextualTestCase merged = newTestCase;
            if (oldTestCase != null)
            {
                merged = this.merge(newTestCase, oldTestCase);
                result.add(merged);
            }
        }
        
        DecimalFormat df = new DecimalFormat("0000");
        
        for(TextualTestCase textualTestCase : result)
        {
            if (textualTestCase.getId() == -1)
            {
                int newId = this.getNextAvailableId();

                String newHeaderId = TestCaseProperties.getInstance().getTestCaseId();

                newHeaderId = newHeaderId.replaceAll("<tc_id>", df.format(newId));
                newHeaderId = newHeaderId.replaceAll("<tc_featureid>", textualTestCase
                        .getFeatureId());

                textualTestCase.setId(newId);
                textualTestCase.setTcIdHeader(newHeaderId);

                ProjectManagerController.getInstance().getCurrentProject()
                        .setNextAvailableTestCaseID(newId + 1);
                ProjectManagerController.getInstance().createXMLFile();
            }
        }

        Collections.sort(result);

        generateExcelFile(result);

        return result;
    }

    /**
     * Returns the next available id for a test case from the .tgt project file
     * 
     * @param result
     * @return
     */
    private int getNextAvailableId()
    {
        return ProjectManagerController.getInstance().getCurrentProject()
                .getNextAvailableTestCaseID();
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
        // fields from the new test case
        List<TextualStep> steps = newTestCase.getSteps();
        String requirements = newTestCase.getRequirements();
        String initialConditions = newTestCase.getInitialConditions();
        String useCaseReferences = newTestCase.getUseCaseReferences();
        String setups = newTestCase.getSetups();
        String featureId = newTestCase.getFeatureId();

        // fields from the old test case
        int id = oldTestCase.getId();// keep the old id
        String description = oldTestCase.getDescription();
        String objective = oldTestCase.getObjective();
        String note = oldTestCase.getNote();
        String executionType = oldTestCase.getExecutionType();
        String regressionLevel = oldTestCase.getRegressionLevel();
        String finalConditions = oldTestCase.getFinalConditions();
        String cleanup = oldTestCase.getCleanup();
        String status = oldTestCase.getStatus();
        String tcIdHeader = oldTestCase.getTcIdHeader();

        TextualTestCase merged = new TextualTestCase(id, tcIdHeader, steps, executionType,
                regressionLevel, description, objective, requirements, setups, initialConditions,
                note, finalConditions, cleanup, useCaseReferences, status, featureId);

        return merged;
    }

    /**
     * Auxiliary method that actually generates an excel file with the given test suite.
     * 
     * @param testCases The list of test cases to be written in an excel file.
     * @throws IOException Exception launched in case something goes wrong during the file writing.
     * @throws TargetException
     */
    private void generateExcelFile(List<TextualTestCase> testCases) throws IOException,
            TargetException
    {
        // INSPECT: Felype - Alterei para gerar a saída da mesma forma que On The Fly.
        TestCaseGeneratorController.getInstance().writeTestSuiteFile(testCases);
    }

    /**
     * @return the selectedTestSuite
     */
    public File getSelectedTestSuite()
    {
        return selectedTestSuite;
    }

    /**
     * @param selectedTestSuite the selectedTestSuite to set
     */
    public void setSelectedTestSuite(File selectedTestSuite)
    {
        this.selectedTestSuite = selectedTestSuite;
    }

}
