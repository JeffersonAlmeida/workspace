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
 * fsf2      Aug 24, 2009        -       Initial creation.
 * fsf2      Sep 15, 2009                Changed the generation to return the result file.
 */
package br.ufpe.cin.target.stdoutput.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtensionExcel;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties;
import br.ufpe.cin.target.tcg.util.ExcelExtractor;
import br.ufpe.cin.target.tcg.util.ExcelGenerator;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;

//INSPECT Felype
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
    private ExcelExtractor excelExtractor;

    // Use Case file name used as id.
    private Hashtable<String, List<String>> objectiveAndImportantNotes;

    private Hashtable<String, List<String>> coverageCategories;

    // Case number
    private DecimalFormat dfID = new DecimalFormat("000");

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

        this.generateTestSuiteKeywordsFields("Test Suite Keywords", "", "Keyword Type",
                "Keyword Value");

        this.generateTestSuiteKeywordsFields("Test Case Keywords", "Test Case Name",
                "Keyword Type", "Keyword Value");

        this.generateTestCaseKeywords();

        this.generateTestSuiteKeywordsFields("Test Suite Requirements", "", "Requirement Type",
                "Requirement Value");

        this.generateTestSuiteKeywordsFields("Test Case Requirements", "Test Case Name",
                "Requirement Type", "Requirement Value");

        this.excelGenerator.createNextRow();
        this.excelGenerator.createEmptyFields(0, 2);
    }

    /**
     * Generates the test case keywords fields.
     */
    //INSPECT - Lais. Adicionada categorias Component e valor "NO" para Regression test.
    private void generateTestCaseKeywords()
    {
        for (TextualTestCase testCase : this.testCases)
        {
            if(this.isRegressionTest(testCase))
            {
                this.createTestCaseKeywordLine(testCase, "Category", "ET Charter");
                this.createTestCaseKeywordLine(testCase, "ET Charter Level", "");
                this.createTestCaseKeywordLine(testCase, "Execution Time", this.getExecutionTime(testCase.getSteps().get(0).getAction()));
            }
            else
            {
                this.createTestCaseKeywordLine(testCase, "Category", this
                        .getKeywordCategoryValue(testCase));
                this.createTestCaseKeywordLine(testCase, "Category", "Use Case");
            }
            
            this.createTestCaseKeywordLine(testCase, "Feature ID", testCase.getFeatureId());
            this.createTestCaseKeywordLine(testCase, "Phase", "System");
            this.createTestCaseKeywordLine(testCase, "Platform", "Android");
            this.createTestCaseKeywordLine(testCase, "Regression Test", "NO");
            this.createTestCaseKeywordLine(testCase, "Component", TestCaseProperties.getInstance().getComponentKeywordValue());
        }
    }

    /**
     * Comment for method.
     * @param action
     * @return
     */
    private String getExecutionTime(String action)
    {
        String executionTime = "";
        
        if(action.toLowerCase().contains("30 min"))
        {
            executionTime = "30 Minutes";
        }
        else if(action.toLowerCase().contains("60 min"))
        {
            executionTime = "60 Minutes";
        }
        else if(action.toLowerCase().contains("90 min"))
        {
            executionTime = "90 Minutes";
        }
        else if(action.toLowerCase().contains("120 min"))
        {
            executionTime = "120 Minutes";
        }

        return executionTime;
    }

    /**
     * Comment for method.
     * 
     * @param testCase
     * @param string
     * @param keywordCategoryValue
     */
    private void createTestCaseKeywordLine(TextualTestCase testCase, String keywordType,
            String keywordValue)
    {
        this.excelGenerator.createNextRow();

        this.excelGenerator.setCurrentRowHeight(22.5f);

        this.excelGenerator.createCellTextAreaStyle(0, (this.informationTestCase.getSuiteName()
                .equals("") ? testCase.getTcIdHeader() : this.informationTestCase.getSuiteName()
                + "-" + dfID.format(testCase.getId())));

        this.excelGenerator.createCellTextAreaStyle(1, keywordType);

        this.excelGenerator.createCellTextAreaStyle(2, keywordValue);
    }

    /**
     * Comment for method.
     * 
     * @param testCase
     * @return
     */
    private String getKeywordCategoryValue(TextualTestCase testCase)
    {
        String keywordCategoryValue = "";

        String[] ucReferences = testCase.getUseCaseReferences().split("#");

        List<String> listKeywordCategoryValue = null;

        Collection<UseCaseDocument> useCaseDocuments = ProjectManagerController.getInstance()
                .getCurrentProject().getUseCaseDocuments();

        for (UseCaseDocument useCaseDocument : useCaseDocuments)
        {
            Feature feature = useCaseDocument.getFeature(ucReferences[0]);
            if (feature != null)
            {
                if (feature.getUseCase(ucReferences[1]) != null)
                {
                    listKeywordCategoryValue = this.coverageCategories.get(useCaseDocument
                            .getDocFilePath());
                }
            }
        }

        if (listKeywordCategoryValue != null)
        {
            keywordCategoryValue = listKeywordCategoryValue
                    .get(Integer.parseInt(ucReferences[1]) - 1);
        }

        if (keywordCategoryValue.equals(""))
        {
            keywordCategoryValue = getEmptyField();
        }
        
        else if(keywordCategoryValue.equals("Feature Interaction Tests"))
        {
            keywordCategoryValue = "Feature Interaction Test";
        }
        
        else if(keywordCategoryValue.equals("Feature Tests"))
        {
            keywordCategoryValue = "Feature Test";
        }
        

        return keywordCategoryValue;
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
     * 
     * @throws IOException
     */
    private void initTCSheet() throws IOException
    {
        this.extractObjectiveAndImportantNotes();

        this.excelGenerator.createSheet("Test Suite Details");

        this.excelGenerator.setColumnHidden(0, true);

        this.mountTests();

        this.mountAutomationTable();
    }

    private void extractObjectiveAndImportantNotes() throws IOException
    {
        this.objectiveAndImportantNotes = new Hashtable<String, List<String>>();

        this.coverageCategories = new Hashtable<String, List<String>>();

        this.excelExtractor = new br.ufpe.cin.target.tcg.util.ExcelExtractor();

        Collection<File> useCaseDocuments = ProjectManagerController.getInstance()
                .getUseCaseDocumentFiles();

        for (File file : useCaseDocuments)
        {
            this.extractObjectiveAndImportantNotesFromFile(file);
        }
    }

    private void extractObjectiveAndImportantNotesFromFile(File file) throws FileNotFoundException,
            IOException
    {
        int currentRow = 22;

        this.excelExtractor.inicializeExcelFile(file);

        this.excelExtractor.setSheet(1);

        this.excelExtractor.setCurrentRow(currentRow);

        List<String> listObjectivesAndImportantNotes = new ArrayList<String>();

        List<String> listCoverageCategories = new ArrayList<String>();

        while (!this.excelExtractor.finished() && !isFinished())
        {
            listObjectivesAndImportantNotes.add(this.excelExtractor.getCellString(6));

            listCoverageCategories.add(this.excelExtractor.getCellString(2));

            currentRow = currentRow + 1;
            this.excelExtractor.setCurrentRow(currentRow);
        }

        this.objectiveAndImportantNotes
                .put(file.getAbsolutePath(), listObjectivesAndImportantNotes);

        this.coverageCategories.put(file.getAbsolutePath(), listCoverageCategories);
    }

    /**
     * Comment for method.
     * 
     * @param currentRow
     * @return
     */
    private boolean isFinished()
    {
        return this.excelExtractor.getCellString(1).equals("")
                && this.excelExtractor.getCellString(2).equals("")
                && this.excelExtractor.getCellString(3).equals("")
                && this.excelExtractor.getCellString(4).equals("")
                && this.excelExtractor.getCellString(5).equals("")
                && this.excelExtractor.getCellString(6).equals("");
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

        this.orderByUseCaseID();

        this.excelGenerator = new ExcelGenerator();

        this.excelGenerator.initWorkbookAndStyles();

        this.initTCSheet();
        this.initTestSuiteKeywords();
        this.initRevisionHistory();

        // this.createRTMRequirementsSheet();
        // this.createRTMSystemTestSheet();
        // this.createRTMUseCaseSheet();
        
        File resultFile = this.informationTestCase.getFileName().equals("") ? file
                : new File(file.getParent() + FileUtil.getSeparator()
                        + this.informationTestCase.getFileName());

        this.excelGenerator.write(resultFile);
        
        return resultFile;
    }

    /**
     * Comment for method.
     */
    private void orderByUseCaseID()
    {
        Comparator<TextualTestCase> compator = new Comparator<TextualTestCase>()
        {
            
            public int compare(TextualTestCase test1, TextualTestCase test2)
            {
                String id1 = test1.getUseCaseReferences().substring(
                        test1.getUseCaseReferences().indexOf("#") + 1);
                String id2 = test2.getUseCaseReferences().substring(
                        test2.getUseCaseReferences().indexOf("#") + 1);

                Integer id1Int = Integer.parseInt(id1);
                Integer id2Int = Integer.parseInt(id2);

                return id1Int.compareTo(id2Int);
            }
        };

        Collections.sort(this.testCases, compator);
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

        // Creating hidden field to the test id.
        this.excelGenerator.createCellHiddenCellStyle(8, String.valueOf(testCase.getId()));

        this.excelGenerator.createCellTextAreaStyle(1, (this.informationTestCase.getSuiteName()
                .equals("") ? testCase.getTcIdHeader() : this.informationTestCase.getSuiteName()
                + "-" + dfID.format(testCase.getId())));

        // Status field.
        this.excelGenerator.createCellTextAreaStyle(2, Properties.getProperty("test_status"));

        // Reg Level - Set to "3" according to client's request
        this.excelGenerator.createCellTextAreaStyle(3, "3");

        // Exe Type
        this.excelGenerator.createCellTextAreaStyle(4, this.informationTestCase.getExecutionType()
                .equals("") ? Properties.getProperty("execution_type") : this.informationTestCase
                .getExecutionType());

        // Case Description - Manually filled.
        this.excelGenerator.createCellTextAreaStyle(5, this.getEmptyField());

        // Step Description - Manually filled.
        this.excelGenerator.createCellTextAreaStyle(6, this.getEmptyField());

        // Procedure
        this.excelGenerator.createCellTextAreaStyle(7, this.getObjectiveProcedure(testCase));

        // Expected Results
        this.excelGenerator.createCellTextAreaStyle(8, "");

        // Traceability
        this.excelGenerator.createCellTextAreaStyle(9, "");
    }

    private String getEmptyField()
    {
        return TestCaseProperties.getInstance().getEmptyField();
    }

    private String getObjectiveProcedure(TextualTestCase testCase)
    {
        // Position 0 = Feature ID.
        // Position 1 = Use Case ID.
        String[] ucReferences = testCase.getUseCaseReferences().split("#");

        Collection<UseCaseDocument> useCaseDocuments = ProjectManagerController.getInstance()
                .getCurrentProject().getUseCaseDocuments();

        List<String> objectives = null;

        for (UseCaseDocument useCaseDocument : useCaseDocuments)
        {
            Feature feature = useCaseDocument.getFeature(ucReferences[0]);
            if (feature != null)
            {
                if (feature.getUseCase(ucReferences[1]) != null)
                {
                    objectives = this.objectiveAndImportantNotes
                            .get(useCaseDocument.getDocFilePath());
                }
            }
        }

        String objective = "";

        if (objectives != null)
        {
            String objectiveString = objectives.get(Integer.parseInt(ucReferences[1]) - 1);
            if (objectiveString.contains("Important Notes:"))
            {
                int index = objectiveString.indexOf("Important Notes:");
                objective = objectiveString.substring(0, index);
            }
            else
            {
                objective = objectiveString;
            }
        }

        if (objective.trim().equals(""))
        {
            objective = TestCaseProperties.getInstance().getEmptyField();
        }
        else
        {
            objective = TestCaseProperties.getInstance().getObjectivePrefix() + " " + objective;
        }

        return objective;
    }

    /**
     * Adds a row with the use case references.
     * 
     * @param testCase The test case from which the use case references will be extracted.
     */
    protected void mountUseCaseReferences(TextualTestCase testCase)
    {
    }

    /**
     * Mounts the test case notes field.
     * 
     * @param testCase The <code>testCase</code> that contains the notes.
     */
    protected void mountTestCaseNotes(TextualTestCase testCase)
    {
        if(!this.isRegressionTest(testCase))
        {
            this.excelGenerator.createNextRow();

            // The label
            this.excelGenerator.createCellTextAreaStyle(6, "Important Notes:");

            // The notes
            this.excelGenerator.createCellTextAreaStyle(7, this.getNotes(testCase));

            // Empty fields
            this.excelGenerator.createTextEmptyFieldsGrey(1, 5);
            this.excelGenerator.createEmptyFields(8, 9);
        }
    }

    private String getNotes(TextualTestCase testCase)
    {
        String notes = "";

        String[] ucReferences = testCase.getUseCaseReferences().split("#");

        List<String> objectivesAndNotes = this.getObjectiveAndImportantNotesText(ucReferences);

        if (objectivesAndNotes != null)
        {
            String objectivesAndNotesString = objectivesAndNotes.get(Integer
                    .parseInt(ucReferences[1]) - 1);

            if (objectivesAndNotesString != null)
            {
                if (objectivesAndNotesString.contains("Important Notes:"))
                {
                    String[] parts = objectivesAndNotesString.split("Important Notes:");

                    notes = parts[parts.length - 1].trim();
                }
            }
        }

        if (notes.equals(""))
        {
            notes = getEmptyField();
        }

        return notes;
    }

    /**
     * Comment for method.
     * 
     * @param ucReferences
     * @return
     */
    private List<String> getObjectiveAndImportantNotesText(String[] ucReferences)
    {
        List<String> objectivesAndNotes = null;

        Collection<UseCaseDocument> useCaseDocuments = ProjectManagerController.getInstance()
                .getCurrentProject().getUseCaseDocuments();

        for (UseCaseDocument useCaseDocument : useCaseDocuments)
        {
            Feature feature = useCaseDocument.getFeature(ucReferences[0]);
            if (feature != null)
            {
                if (feature.getUseCase(ucReferences[1]) != null)
                {
                    objectivesAndNotes = this.objectiveAndImportantNotes.get(useCaseDocument
                            .getDocFilePath());
                }
            }
        }

        return objectivesAndNotes;
    }

    /**
     * Mounts the test case steps fields.
     * 
     * @param testCase The test case object that contains the step fields.
     */
    protected void mountTestCaseSteps(TextualTestCase testCase)
    {
        TextualStep step = testCase.getSteps().get(0);

        this.excelGenerator.createNextRow();

        // Use Case label.
        this.excelGenerator.createCellTextAreaStyle(6, (this.isRegressionTest(testCase) ? "Key Test Areas of Concentration:" : "Use Case:"));

        // Empty fields.
        this.excelGenerator.createTextEmptyFieldsGrey(1, 5);

        // Procedure.
        this.excelGenerator.createCellTextAreaStyle(7, (step.getAction().equals("EMPTY") ? this
                .getEmptyField() : step.getAction()));

        // Expected results.
        this.excelGenerator.createCellTextAreaStyle(8, (step.getResponse().equals("EMPTY") ? ""
                : step.getResponse() + "\n\n")
                + ((Properties.getProperty("expected_results_coment") == null || Properties
                        .getProperty("expected_results_coment").trim().equals("")) ? this
                        .getEmptyField() : Properties.getProperty("expected_results_coment")));

        // Traceability.
        this.excelGenerator.createCellTextAreaStyle(9, (this.isRegressionTest(testCase) ? "N/A" : this.getTraceability(testCase)));
    }

    private String getTraceability(TextualTestCase testCase)
    {
        return testCase.getRequirements().replaceAll(Pattern.quote("["), "").replaceAll(
                Pattern.quote("]"), "").replace(",", "\n").replaceAll(" ", "").replace("N/A", "");
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
        this.excelGenerator.createCellTextAreaStyle(6, "Final Condition:");

        // preparing final conditions text.
        this.excelGenerator.createCellTextAreaStyle(7, testCase.getFinalConditions());

        // Creating empty cells
        this.excelGenerator.createTextEmptyFieldsGrey(1, 5);
        this.excelGenerator.createEmptyFields(8, 9);

        this.excelGenerator.createNextRow();
        this.excelGenerator.createCellTextAreaStyle(6, "Cleanup:");

        // Creating cleanup field.
        this.excelGenerator.createCellTextAreaStyle(7, testCase.getCleanup());

        // Creating empty cells
        this.excelGenerator.createTextEmptyFieldsGrey(1, 5);
        this.excelGenerator.createEmptyFields(8, 9);
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
        String text = "";

        this.excelGenerator.createNextRow();

        text = (this.isRegressionTest(testCase) ? "Time Constraint:" : "Initial PUT Conditions:");
        this.excelGenerator.createCellTextAreaStyle(6, text);

        text = (this.isRegressionTest(testCase) ? this.getExecutionTime(testCase.getSteps().get(0).getAction()) : testCase
                .getInitialConditions().replace("1)", "").trim());
        this.excelGenerator.createCellTextAreaStyle(7, text);

        this.excelGenerator.createTextEmptyFieldsGrey(1, 5);
        this.excelGenerator.createEmptyFields(8, 9);

        this.excelGenerator.createNextRow();

        text = (this.isRegressionTest(testCase) ? "Setup:" : "Setup information:");
        this.excelGenerator.createCellTextAreaStyle(6, text);

        this.excelGenerator.createCellTextAreaStyle(7, this.getEmptyField());

        this.excelGenerator.createTextEmptyFieldsGrey(1, 5);
        this.excelGenerator.createEmptyFields(8, 9);

        this.excelGenerator.createNextRow();

        text = (this.isRegressionTest(testCase) ? "Notes:" : "Test Content Location:");
        this.excelGenerator.createCellTextAreaStyle(6, text);

        this.excelGenerator.createCellTextAreaStyle(7, this.getEmptyField());
        this.excelGenerator.createTextEmptyFieldsGrey(1, 5);
        this.excelGenerator.createEmptyFields(8, 9);
    }

    /**
     * Comment for method.
     * 
     * @param testCase
     * @return
     */
    private boolean isRegressionTest(TextualTestCase testCase)
    {
        boolean result = false;

        if (testCase.getSteps() != null && testCase.getSteps().size() > 0
                && testCase.getSteps().get(0).getAction() != null
                && testCase.getSteps().get(0).getAction().toLowerCase().startsWith("et charter"))
        {
            result = true;
        }

        return result;
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
        this.excelGenerator.createCellHeaderCellStyleWithBorders(7, "Procedure");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(8, "Expected Results");
        this.excelGenerator.createCellHeaderCellStyleWithBorders(9, "Traceability");
    }
}
