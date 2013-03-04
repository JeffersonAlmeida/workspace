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
 * fsf2      Feb 17, 2008       -        Initial creation.
 * lmn3		 May 14, 2009				 Changes due code inspection.
 * fsf2      Jun 30, 2009                Changed the test case format.
 * fsf2      Sep 15, 2009                Changed the generation to return the result file.
 */
//INSPECT: Felypee - Corrigi o revision history.
package br.ufpe.cin.target.tc4output.excel;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtensionExcel;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.util.ExcelGenerator;


/**
 * This class contains the code for writing the test case excel file.
 * 
 * <pre>
 * CLASS:
 * The class receives a list of test cases and writes a excel file according to the Test Central standard(Version 4).
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
        this.excelGenerator.createCellBlueAreaStyle(0, "Date Last Updated");
        this.excelGenerator.createCellBlueAreaStyle(1, "Modified By");
        this.excelGenerator.createCellBlueAreaStyle(2, "Comments");

        this.excelGenerator.createNextRow();
        this.excelGenerator.createEmptyFields(0, 2);
    }

    /**
     * Creates the Test Suite Keywords sheet.
     */
    private void initTestSuiteKeywords()
    {
        this.excelGenerator.createSheet("Test Suite Keywords");

        generateTestSuiteKeywordsFields("Test Suite Keywords", "", "Keyword Type", "Keyword Value");
        generateTestSuiteKeywordsFields("Test Case Keywords", "Test Case Name", "Keyword Type",
                "Keyword Value");
        generateTestSuiteKeywordsFields("Test Suite Requirements", "", "Requirement Type",
                "Requirement Value");
        generateTestSuiteKeywordsFields("Test Case Requirements", "Test Case Name",
                "Requirement Type", "Requirement Value");

        this.excelGenerator.createNextRow();
        this.excelGenerator.createEmptyFields(0, 2);
    }

    /**
     * Creates the test suite keywords sheet.
     */
    private void generateTestSuiteKeywordsFields(String textTitle, String textCell1,
            String textCell2, String textCell3)
    {
        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(0, "");

        this.excelGenerator.createNextRow();
        this.excelGenerator.createMergedLineWithBlueAreaStyle(textTitle, 0, 2);

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellGreyAreaStyle(0, textCell1);
        this.excelGenerator.createCellGreyAreaStyle(1, textCell2);
        this.excelGenerator.createCellGreyAreaStyle(2, textCell3);
    }

    /**
     * Creates the test case sheet.
     */
    private void initTCSheet()
    {
        this.excelGenerator.createSheet("Test Suite Details");

        this.excelGenerator.setColumnHidden(0, true);

        this.mountTests();

        this.mountAutomationTable();
    }

    /**
     * Creates the automation table
     */
    private void mountAutomationTable()
    {
        this.excelGenerator.createNextRow();
        this.excelGenerator.createNextRow();

        this.excelGenerator.createMergedLineWithBlueAreaStyle("Automation Table:", 1, 3);

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellHeaderCellStyleWithBorders(1, "Case");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(2, "Verified");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(3, "System");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(4, "Script Information");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(5, "Parameters");
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.target.tcg.extensions.output.
     * OutputDocumentExtension#writeTestCaseDataInFile(File file)
     */
    public File writeTestCaseDataInFile(File file) throws IOException
    {
        super.writeTestCaseDataInFile(file);
        
        this.excelGenerator = new ExcelGenerator();
        
        this.excelGenerator.initWorkbookAndStyles();

        this.initTCSheet();
        this.initTestSuiteKeywords();
        this.initRevisionHistory();

        this.createRTMRequirementsSheet();
        this.createRTMSystemTestSheet();
        this.createRTMUseCaseSheet();
        
        File resultFile = this.informationTestCase.getFileName().equals("") ? file : 
            new File(file.getParent() + FileUtil.getSeparator() + this.informationTestCase.getFileName());

        this.excelGenerator.write(resultFile);
        
        return resultFile;
    }

    /**
     * This method mounts the test case info: case number, status, regression level, execution type,
     * test case name, objective and expected results.
     * 
     * @param tcCount The case number inside the generated test suite.
     * @param testCase The test case object where the information will be retrieved.
     */
    protected void mountTestCaseInfo(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();
        
        //Creating hidden field to the test id.
        this.excelGenerator.createCellHiddenCellStyle(8, String.valueOf(testCase.getId()));

        // Case number
        DecimalFormat df = new DecimalFormat("000");

        this.excelGenerator.createCellTextAreaStyle(1, (this.informationTestCase.getSuiteName()
                .equals("") ? testCase.getTcIdHeader() : this.informationTestCase.getSuiteName() + "-" + df.format(testCase.getId())));

        // Status field.
        this.excelGenerator.createCellTextAreaStyle(2, this.informationTestCase.getStatus().equals(
                "") ? testCase.getStatus() : this.informationTestCase.getStatus());

        // Reg Level
        this.excelGenerator.createCellTextAreaStyle(3, this.informationTestCase
                .getRegressionLevel().equals("") ? testCase.getRegressionLevel()
                : this.informationTestCase.getRegressionLevel());

        // Exe Type
        this.excelGenerator.createCellTextAreaStyle(4, this.informationTestCase.getExecutionType()
                .equals("") ? testCase.getExecutionType() : this.informationTestCase
                .getExecutionType());

        // Description
        this.excelGenerator.createCellTextAreaStyle(5, testCase.getDescription());

        // Procedure/Objective
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getObjective());

        // Expected Results
        this.excelGenerator.createCellTextAreaStyle(7, "");
    }

    /**
     * Adds a row with the use case references.
     * 
     * @param testCase The test case from which the use case references will be extracted.
     */
    protected void mountUseCaseReferences(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();

        // The label
        this.excelGenerator.createCellTextAreaStyle(5, "Use Cases: ");

        // The references
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getUseCaseReferences());

        // Empty fields
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(7, 7);
    }

    /**
     * Mounts the test case notes field.
     * 
     * @param testCase The <code>testCase</code> that contains the notes.
     */
    protected void mountTestCaseNotes(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();

        // The label
        this.excelGenerator.createCellTextAreaStyle(5, "Notes:");

        // The notes
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getNote());

        // Empty fields
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(7, 7);
    }

    /**
     * Mounts the test case steps fields.
     * 
     * @param testCase The test case object that contains the step fields.
     */
    protected void mountTestCaseSteps(TextualTestCase testCase)
    {
        this.excelGenerator.createNextRow();

        this.excelGenerator.createCellTextAreaStyle(5, "Test Procedure (Step Number):");

        // Empty fields
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(6, 7);

        int stepNumber = 1;

        for (TextualStep step : testCase.getSteps())
        {
            this.excelGenerator.createNextRow();

            // Step Number
            this.excelGenerator.createCellTextAreaStyle(5, String.valueOf(stepNumber));

            // Step
            this.excelGenerator.createCellTextAreaStyle(6, String.valueOf(step.getAction()));

            // Expected Result
            this.excelGenerator.createCellTextAreaStyle(7, String.valueOf(step.getResponse()));

            // Empty Fields
            this.excelGenerator.createEmptyFields(1, 4);

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
        // Creating the final condition and cleanup rows
        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(5, "Final Conditions:");

        // preparing final conditions text.
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getFinalConditions());

        // Creating empty cells
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(7, 7);

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(5, "Cleanup:");

        // Creating cleanup field.
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getCleanup());

        // Creating empty cells
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(7, 7);
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
        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(5, "Requirements:");
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getRequirements());
        this.excelGenerator.createCellTextAreaStyle(7, "");
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(7, 7);

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(5, "Setup:");
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getSetups());
        this.excelGenerator.createCellTextAreaStyle(7, "");
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(7, 7);

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(5, "Initial Conditions:");
        this.excelGenerator.createCellTextAreaStyle(6, testCase.getInitialConditions());
        this.excelGenerator.createCellTextAreaStyle(7, "");
        this.excelGenerator.createEmptyFields(1, 4);
        this.excelGenerator.createEmptyFields(7, 7);
    }

    /**
     * This method mounts the test suite header.
     */
    protected void mountHeader()
    {
        this.excelGenerator.createNewField("Suite Name:", this.informationTestCase.getSuiteName(),
                true);
        this.excelGenerator.createNewField("Date Exported:", this.informationTestCase
                .getDateExported(), true);
        this.excelGenerator.createNewField("Description:", this.informationTestCase
                .getDescription(), true);
        this.excelGenerator.createNewField("Created By:", this.informationTestCase.getCreatedBy(),
                true);

        this.excelGenerator.createNewField("Last Modified By:", this.informationTestCase
                .getLastModifiedBy(), true);
        this.excelGenerator.createNewField("Suite Status:", this.informationTestCase
                .getSuiteStatus(), true);
        this.excelGenerator.createNewField("Ownership Group:", this.informationTestCase
                .getOwnershipGroup(), true);

        this.excelGenerator.createNewField("Read Permissions:", this.informationTestCase
                .getReadPermissions(), true);
        this.excelGenerator.createNewField("Write Permissions:", this.informationTestCase
                .getWritePermissions(), true);
        this.excelGenerator.createNewField("Attached File Desc:", this.informationTestCase
                .getAttachedFileDesc(), true);
        this.excelGenerator.createNewField("Procedure:", this.informationTestCase.getProcedure(),
                true);
        this.excelGenerator.createNewField("Notes:", this.informationTestCase.getNotes(), true);
        this.excelGenerator.createNewField("", "", false);

        this.excelGenerator.setCellStyleHeader(4);

        this.createColumnTitles();
    }

    /**
     * Creates the column titles cells.
     */
    private void createColumnTitles()
    {
        this.excelGenerator.createNextRow();
        this.excelGenerator.createMergedLineWithBlueAreaStyle("TPS Data:", 1, 3);

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellHeaderCellStyleWithBorders(1, "Test Case Name");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(2, "Status");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(3, "Regression Level");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(4, "Execution Method");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(5, "Case Description");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(6, "Step Description");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(7, "Expected Results");
    }
}
