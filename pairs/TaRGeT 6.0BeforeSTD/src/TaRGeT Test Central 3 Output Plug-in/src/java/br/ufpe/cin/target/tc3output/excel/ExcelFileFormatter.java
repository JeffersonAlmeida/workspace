/*
 * @(#)ExcelFileFormatter.java
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
 * wdt022    Jan 8, 2007    LIBkk11577   Initial creation.
 * wdt022    Jan 18, 2007   LIBkk11577   Rework after inspection (LX135556).
 * wdt022    Feb 23, 2007   LIBkk16317   Fix excel file encoding.
 * dhq348    Jul 05, 2007   LIBmm76986   Modifications according to CR.
 * dhq348    Jul 06, 2007   LIBmm76995   Modifications according to CR.
 * dhq348    Nov 21, 2007   LIBoo10574   It is now filling the blank fields with default values.
 * dhq348    Jan 22, 2008   LIBoo10574   Rework after inspection LX229632.
 * tnd783    Apr 07, 2008   LIBpp71785   Added hidden cell with test information
 * tnd783    Jul 21, 2008   LIBpp71785   Rework after inspection LX285039. 
 * tnd783 	 Sep 08, 2008	LIBqq51204   Changes made to consider the initial ID
 * jrm687    Dec 18, 2008   LIBnn34008	 Modifications to add document revision history.
 * fsf2      Mar 2, 2009                 Class adapted for POI 3.2 and for TaRGeT SPL.
 * lmn3		 May 14, 2009				 Changes due code inspection
 * lmn3      Jun 20, 2009                Inclusion of Revision History Sheet.
 * lmn3		 Jun 26, 2009				 Change of class hierarchy due different output extension implementation.
 * fsf2		 Jun 26, 2009				 Change to print only the test case id header.
 * fsf2      Sep 15, 2009                Changed the generation to return the result file.
 */
package br.ufpe.cin.target.tc3output.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtensionExcel;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties;
import br.ufpe.cin.target.tcg.util.ExcelGenerator;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.UseCaseUtil;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;

/**
 * This class contains the code for writing the test case excel file.
 * 
 * <pre>
 * CLASS:
 * The class receives a list of test cases and writes a excel file according to the Test central standard.
 * 
 * RESPONSIBILITIES:
 * Builds and writes an Excel file from a list of test cases. 
 * 
 * COLABORATORS:
 * It depends on the POI external library.
 * 
 * USAGE:
 * ExcelFileFormatter efof = new ExcelFileFormatter(&lt;test_case_list&gt;);
 * efof.writeTestCaseDataInFile(&lt;file_object&gt;);
 */
public class ExcelFileFormatter extends OutputDocumentExtensionExcel
{

    /**
     * The class constructor. It receives as input the list of test case that will be written in the
     * excel file.
     * 
     * @param testCaseList The list of test cases that will compose the test suite in the excel
     * file.
     */
    public ExcelFileFormatter()
    {
    }

    /**
     * Creates the revision history sheet.
     */
    private void initRevisionHistory()
    {
        this.excelGenerator.createSheet("Revision History");

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(0, "");

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellGreyAreaStyle(0, "Date Last Updated");
        this.excelGenerator.createCellGreyAreaStyle(1, "Modified By");
        this.excelGenerator.createCellGreyAreaStyle(2, "Comments");

        this.excelGenerator.createNextRow();
        this.excelGenerator.createEmptyFields(0, 2);
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.target.tcg.extensions.output.
     * OutputDocumentExtension#writeTestCaseDataInFile(File file)
     */
    public File writeTestCaseDataInFile(File file) throws IOException
    {
        this.excelGenerator = new ExcelGenerator();
        this.excelGenerator.initWorkbookAndStyles();

        this.mountTests();
        this.initRevisionHistory();

        this.createRTMRequirementsSheet();
        this.createRTMSystemTestSheet();
        this.createRTMUseCaseSheet();

        this.excelGenerator.write(file);
        
        return file;
    }

    /**
     * This method mounts the test case info: case number, regression level, execution type, test
     * case name, objective and expected results.
     * 
     * @param tcCount The case number inside the generated test suite.
     * @param testCase The test case object where the information will be retrieved.
     */
    protected void mountTestCaseInfo(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();

        // Creating hidden field to the test id.
        this.excelGenerator.createCellHiddenCellStyle(6, String.valueOf(testCase.getId()));

        // Case number
        this.excelGenerator.createCellTextAreaStyleWithBorders(0, testCase.getTcIdHeader());

        // Reg Level
        this.excelGenerator.createCellTextAreaStyleWithBorders(1, testCase.getRegressionLevel());

        // Exe Type
        this.excelGenerator.createCellTextAreaStyleWithBorders(2, testCase.getExecutionType());

        // Description/Name
        this.excelGenerator.createCellTextAreaStyleWithBorders(3, testCase.getDescription());

        // Procedure/Objective
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getObjective());

        // Expected Results
        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");
    }

    /**
     * Adds a row with the use case references.
     * 
     * @param testCase The test case from which the use case references will be extracted.
     */
    protected void mountUseCaseReferences(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        // The label
        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Use Cases: ");

        // The references
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getUseCaseReferences());

        // An empty cell
        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");
    }

    /**
     * Mounts the test case notes field.
     * 
     * @param testCase The <code>testCase</code> that contains the notes.
     */
    protected void mountTestCaseNotes(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        // Creating notes header
        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Notes:");

        // String notes text
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getNote());

        // Creating empty cells
        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");
    }

    /**
     * Mounts the test case steps fields.
     * 
     * @param testCase The test case object that contains the step fields.
     */
    protected void mountTestCaseSteps(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Test Procedure (Step Number):");
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, "");
        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");

        int stepNumber = 1;

        for (TextualStep step : testCase.getSteps())
        {
            this.excelGenerator.createNextRow();
            this.excelGenerator.mountGreyArea();

            // Step Number
            this.excelGenerator.createCellTextAreaStyleWithBorders(3, stepNumber + "");

            // Step
            this.excelGenerator.createCellTextAreaStyleWithBorders(4, step.getAction());

            // Expected Result
            this.excelGenerator.createCellTextAreaStyleWithBorders(5, step.getResponse());

            if (TestCaseProperties.getInstance().isPrintingHiddenTestCaseId())
            {
                // Hidden Id
                this.excelGenerator.createCellHiddenCellStyle(6, step.getId().toString());
            }

            stepNumber++;
        }
    }

    /**
     * This method mounts the test case final conditions and cleanup information.
     * 
     * @param testCase The test case that contains the final conditions and cleanup information
     */
    protected void mountTestCaseFinalFields(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        // Setting the header
        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Final Conditions:");

        // preparing final conditions text.
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getFinalConditions());

        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");

        // Creating the cleanup rows
        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        // Setting the header
        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Cleanup:");

        // Creating an empty cell for cleanup field
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getCleanup());

        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");
    }

    /**
     * This method mounts the test case initial conditions, setup information and related
     * requirements.
     * 
     * @param testCase The test case that contains the initial conditions, setup information and
     * related requirements.
     */
    protected void mountTestCaseInitialFields(TextualTestCase testCase)
    {
        // Creating initial condition
        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        // Preparing the header
        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Requirements:");

        // Preparing Requirements Information
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getRequirements());

        // Creating empty cells
        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");

        // Creating setup rows
        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Setup:");

        // Preparing setup information
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getSetups());

        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");

        this.excelGenerator.createNextRow();
        this.excelGenerator.mountGreyArea();

        this.excelGenerator.createCellTextAreaStyleWithBorders(3, "Initial Conditions:");

        // Preparing initial conditions text.
        this.excelGenerator.createCellTextAreaStyleWithBorders(4, testCase.getInitialConditions());

        this.excelGenerator.createCellTextAreaStyleWithBorders(5, "");
    }

    /**
     * This method mounts the test suite header.
     */
    protected void mountHeader()
    {
        this.excelGenerator.createSheet("Test Suite");
        this.excelGenerator.createNextRow();

        this.excelGenerator.setHeightCurrentRow((short) 700);
        this.excelGenerator.setColumnWidth(0, 1600);

        this.excelGenerator.setColumnWidth(1, 1800);
        this.excelGenerator.setColumnWidth(2, 1800);
        this.excelGenerator.setColumnWidth(3, 6000);
        this.excelGenerator.setColumnWidth(4, 6000);
        this.excelGenerator.setColumnWidth(5, 6000);

        this.excelGenerator.createCellHeaderCellStyleWithBordersGrey(0, "Case");
        this.excelGenerator.createCellHeaderCellStyleWithBordersGrey(1, "Reg. Level");
        this.excelGenerator.createCellHeaderCellStyleWithBordersGrey(2, "Exe. Type");
        this.excelGenerator.createCellHeaderCellStyleWithBordersGrey(3, "Case Description");
        this.excelGenerator.createCellHeaderCellStyleWithBordersGrey(4, "Procedure");
        this.excelGenerator.createCellHeaderCellStyleWithBordersGrey(5, "Expected Results");
    }

    /**
     * Gets a list of use cases from the specified <code>requirement</code>.
     * 
     * @param requirement The requirement whose use cases will be returned.
     * @return A list of use cases.
     */
    public List<String> getUseCasesFromRequirement(String requirement)
    {
        List<String> result = new ArrayList<String>();

        for (UseCase usecase : ProjectManagerExternalFacade.getInstance().getUseCasesByRequirement(
                requirement))
        {
            result.add(usecase.getId());
        }

        String[] orderedUseCases = result.toArray(new String[] {});
        Arrays.sort(orderedUseCases);

        return new ArrayList<String>(Arrays.asList(orderedUseCases));
    }

    /**
     * Returns a list of test case ids related to the given <code>requirement</code>.
     * 
     * @param requirement The requirement whose test cases will be returned.
     * @return A list of test cases.
     */
    public List<String> getTestCasesFromRequirement(String requirement)
    {
        List<String> result = new ArrayList<String>();

        for (TextualTestCase testcase : this.testCases)
        {
            if (testcase.coversRequirement(requirement))
            {
                result.add(testcase.getDescription());
            }
        }

        return result;
    }

    /**
     * Returns a list of test case ids related to the given <code>usecaseId</code>.
     * 
     * @param usecaseId The id of the use case whose related test cases will be returned.
     * @return A list of test cases.
     */
    public List<String> getTestCaseReferences(String usecaseId)
    {
        List<String> result = new ArrayList<String>();

        for (TextualTestCase testcase : this.testCases)
        {
            if (testcase.coversUseCase(usecaseId))
            {
                result.add(testcase.getDescription());
            }
        }

        return result;
    }

    /**
     * Returns a list of lexicographically ordered strings of use cases.
     * 
     * @return A list with the ordered use cases as strings.
     */
    protected List<String> getOrderedUseCases()
    {
        Set<String> set = new HashSet<String>();

        for (Feature feature : ProjectManagerExternalFacade.getInstance().getCurrentProject()
                .getFeatures())
        {
            for (UseCase usecase : feature.getUseCases())
            {
                set.add(UseCaseUtil.getUseCaseReference(feature, usecase));
            }
        }

        String[] sorted = set.toArray(new String[] {});
        Arrays.sort(sorted);

        return new ArrayList<String>(Arrays.asList(sorted));
    }
}
