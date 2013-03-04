/*
 * @(#)ExcelExtractor.java
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
 * tnd783    07/07/2008     LIBhh00000   Initial creation.
 * fsf2      16/03/2009                  Adapted for POI 3.2.
 * fsf2      01/07/2009                  Changed the objective test cell.
 */
package br.ufpe.cin.target.tc4output.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.tcg.extensions.extractor.ExtractorDocumentExtension;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * This class contains the code for extracting a list of test cases from an Excel file according to
 * the Test central standard.
 * 
 * RESPONSIBILITIES:
 * Extracts a list of test cases from an Excel file. 
 *
 * COLABORATORS:
 * It depends on the POI external library.
 *
 * USAGE:
 * ExcelExtractor extractor = new ExcelExtractor();
 * List<TextualTestCase> testCases = extractor.extractTestSuite(<file>);
 */
public class ExcelExtractor implements ExtractorDocumentExtension
{
    private br.ufpe.cin.target.tcg.util.ExcelExtractor excelExtractor;

    /**
     * This method extracts test cases from a given Excel file.
     * 
     * @param file The file to be read
     * @return The list of TextualTestCases extracted from the file
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File file) throws FileNotFoundException,
    IOException
    {
        List<TextualTestCase> result = new ArrayList<TextualTestCase>();

        this.setWorkbook(file);

        for(int i = 0; i < 16; i++)
        {
            this.excelExtractor.nextRow();
        }

        while (!this.excelExtractor.finished()
                && (this.excelExtractor.getCellType(1) == br.ufpe.cin.target.tcg.util.ExcelExtractor.CELL_TYPE_NUMERIC || 
                        (this.excelExtractor.getCellString(1).matches(".*\\d"))))
        {
            int id = getId();
            String header = getIdHeader();
            
            String description = getDescription();
            String status = getStatus();
            String regLevel = getRegressionLevel();
            String execType = getExecutionType();
            String objective = getObjective();
            String usecaseReferences = getUsecaseReferences();
            String requirements = getRequirements();
            String setups = getSetups();
            String initialConditions = getInitialConditions();
            String note = getNote();
            List<TextualStep> steps = getSteps();
            String finalConditions = getFinalConditions();
            String cleanup = getCleanUp();

            TextualTestCase testCase = new TextualTestCase(id, header, steps, execType, regLevel, description,
                    objective, requirements, setups, initialConditions, note, finalConditions,
                    cleanup, usecaseReferences,status,"");

            result.add(testCase);
        }

        return result;
    }

    /**
     * Comment for method.
     * @return
     */
    private String getStatus()
    {
        return this.excelExtractor.getCellString(2);
    }

    /**
     * Gets the original id, without header.
     * @return
     */
    private int getId()
    {
        int retorno = -1;
        
        if (this.excelExtractor.getCellType(8) == br.ufpe.cin.target.tcg.util.ExcelExtractor.CELL_TYPE_STRING)
        {
            try
            {
                retorno = Integer.parseInt(this.excelExtractor.getCellString(8));
            }
            catch (NumberFormatException e)
            {
                retorno = -1;
            }
            
        }
        
        return retorno;
    }

    /**
     * Auxiliary method to retrieve the id of the test case.
     * @param headers 
     * 
     * @return The test case id.
     */
    private String getIdHeader()
    {
        String strId = "";
        
        if (this.excelExtractor.getCellType(1) == br.ufpe.cin.target.tcg.util.ExcelExtractor.CELL_TYPE_STRING)
        {
            strId = this.excelExtractor.getCellString(1);
        }
        else
        {
            strId = String.valueOf(this.excelExtractor.getColumnIndex(1));
        }
        
        return strId;
    }

    /**
     * Auxiliary method to retrieve the regression level of the test case.
     * 
     * @return The test case regression level.
     */
    private String getRegressionLevel()
    {
        return this.excelExtractor.getCellString(3);
    }

    /**
     * Auxiliary method to retrieve the execution type of the test case.
     * 
     * @return The test case execution type.
     */
    private String getExecutionType()
    {
        return this.excelExtractor.getCellString(4);
    }

    /**
     * Auxiliary method to extract the string id of the test case.
     * 
     * @return The test case string id.
     */
    private String getDescription()
    {
        return this.excelExtractor.getCellString(5);
    }

    /**
     * Auxiliary method to extract the objective of the test case.
     * 
     * @return The test case objective.
     */
    private String getObjective()
    {
        String objective = this.excelExtractor.getCellString(6);

        this.excelExtractor.nextRow();

        return objective;
    }

    /**
     * Auxiliary method to extract the use case references of the test case.
     * 
     * @return The use case references of the test case.
     */
    private String getUsecaseReferences()
    {
        String useCaseReferences = this.excelExtractor.getCellString(6).trim();

        this.excelExtractor.nextRow();

        return useCaseReferences;
    }

    /**
     * Auxiliary method to extract the requirements of the test case.
     * 
     * @return The requirements of the test case.
     */
    private String getRequirements()
    {
        String requirements = this.excelExtractor.getCellString(6).trim();

        this.excelExtractor.nextRow();

        return requirements;
    }

    /**
     * Auxiliary method to extract the setup of the test case.
     * 
     * @return The setup of the test case.
     */
    private String getSetups()
    {
        String setups = this.excelExtractor.getCellString(6).trim();

        this.excelExtractor.nextRow();

        return setups;
    }

    /**
     * Auxiliary method to extract the initial conditions of the test case.
     * 
     * @return The initial conditions of the test case.
     */
    private String getInitialConditions()
    {
        String initialConditions = this.excelExtractor.getCellString(6).trim();

        this.excelExtractor.nextRow();

        return initialConditions;
    }

    /**
     * Auxiliary method to extract the note of the test case.
     * 
     * @return The note of the test case.
     */
    private String getNote()
    {
        String note = this.excelExtractor.getCellString(6).trim();

        this.excelExtractor.nextRow();

        return note;
    }

    /**
     * Auxiliary method to extract the steps of the test case.
     * 
     * @return The steps of the test case.
     */
    private List<TextualStep> getSteps()
    {
        this.excelExtractor.nextRow();

        List<TextualStep> list = new ArrayList<TextualStep>();

        while ((this.excelExtractor.getCellType(5) == br.ufpe.cin.target.tcg.util.ExcelExtractor.CELL_TYPE_NUMERIC || 
                (this.excelExtractor.getCellString(5).matches("\\d+"))))
        {
            String action = this.excelExtractor.getCellString(6).trim();
            String response = this.excelExtractor.getCellString(7).trim();
            TextualStep step = new TextualStep(action, response, new StepId("", "", ""));

            list.add(step);

            this.excelExtractor.nextRow();
        }

        return list;
    }

    /**
     * Auxiliary method to extract the final conditions of the test case.
     * 
     * @return The final conditions of the test case.
     */
    private String getFinalConditions()
    {
        String finalConditions = this.excelExtractor.getCellString(6).trim();

        this.excelExtractor.nextRow();

        return finalConditions;
    }

    /**
     * Auxiliary method to extract the cleanup of the test case.
     * 
     * @return The cleanup of the test case.
     */
    private String getCleanUp()
    {
        String cleanup = this.excelExtractor.getCellString(6).trim();

        this.excelExtractor.nextRow();

        return cleanup;
    }

    /**
     * Auxiliary method to set the Excel file to be read.
     * 
     * @param file The file to be read
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    private void setWorkbook(File file) throws IOException, FileNotFoundException
    {
        this.excelExtractor = new br.ufpe.cin.target.tcg.util.ExcelExtractor();
        this.excelExtractor.inicializeExcelFile(file);

        this.excelExtractor.setSheet(0);
    }
}
