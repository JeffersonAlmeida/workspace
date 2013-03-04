/*
 * @(#)TraceabilityMatrixGenerator.java
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
 * dhq348    Jul 6, 2007    LIBmm76995   Initial creation.
 * cvn768    Mar 2, 2009                 Class adapted for POI 3.2.
 */
package br.ufpe.cin.target.tcg.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.UseCaseUtil;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.tcg.extensions.inputTemplate.InformationTestCase;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * Generic class that is responsible for generating a Traceability Matrix based on some parameters,
 * see method <code>createMatrix(...)</code>. Basically, it creates a spreadsheet with a title and 
 * two columns that links to different types of entities, e.g. Test Cases to Use Cases, 
 * Requirements to Use Cases, etc.
 */
public class TraceabilityMatrixGenerator
{
    /** The single instance */
    private static TraceabilityMatrixGenerator instance;

    /**
     * Returns the single instance.
     * 
     * @return The single instance.
     */
    public static TraceabilityMatrixGenerator getInstance()
    {
        if (instance == null)
        {
            instance = new TraceabilityMatrixGenerator();
        }
        return instance;
    }

    /**
     * Constructs the generator.
     * 
     * @param workBook The workbook used to create the matrix spreasheet.
     * @param testCases The test cases used to fill the matrix.
     */
    private TraceabilityMatrixGenerator()
    {
    }

    /**
     * Creates a spreadsheet with a title and two columns. This method is intended to generate the
     * traceability matrices. The content of the matrix is informed by the paarameter <code>contents</code>, 
     * which is a hash map that links two types entities in the matrix. For instance, if the matrix links
     * use cases to test cases, the hash map contains entries that links a use case Id to a list of 
     * test case Ids.
     * 
     * @param spreadsheetTitle The title of the spreadsheet. This title is placed in the bottom of
     * the spreadsheet.
     * @param subTitle The title that will be displayed in the top (in a cell) of the spreadsheet.
     * @param column1Title The title of the first column of the matrix.
     * @param column2Title The title of the second column of the matrix.
     * @param contents A hash map that links one entity to a list of entities.
     * @param abbreviation1 The abbreviation that will be prepended in every line of the first
     * column.
     * @param abbreviation2 The abbreviation that will be prepended in every line of the second
     * column.
     * @param workbook The object in which the spreadsheet will be written.
     * @return The created spreadsheet.
     */
    public HSSFSheet createMatrix(String spreadsheetTitle, String subTitle, String column1Title,
            String column2Title, Map<String, List<String>> contents, String abbreviation1,
            String abbreviation2, ExcelGenerator excelGenerator)
    {
        HSSFSheet sheet = null;
        HSSFWorkbook workbook = excelGenerator.getWorkbook();
        
        if (workbook != null)
        {
            int rowNumber = 0;

            sheet = workbook.createSheet(spreadsheetTitle);
            sheet.setColumnWidth(0, 12000);
            sheet.setColumnWidth(1, 19000);

            HSSFRow row = sheet.createRow(rowNumber++); // empty row

            /* creates the subtitle row */
            row = sheet.createRow(rowNumber++);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(this.createSubtitleStyle(workbook));
            cell.setCellValue(new HSSFRichTextString(subTitle));

            row = sheet.createRow(rowNumber++); // empty row

            /* creates the columns' title style */
            row = sheet.createRow(rowNumber++);
            HSSFCellStyle style1 = this.getHeaderCellStyle(workbook);

            /* creates the column 1's title */
            cell = row.createCell(0);
            cell.setCellStyle(style1);
            cell.setCellValue(new HSSFRichTextString(column1Title));

            /* creates the column 2's title */
            cell = row.createCell(1);
            cell.setCellStyle(style1);
            cell.setCellValue(new HSSFRichTextString(column2Title));

            /* creates the data's style */
            style1 = this.getTextAreaStyle(workbook);

            List<String> column1Data = TCGUtil.sort(contents.keySet());
            for (String column1Cell : column1Data)
            {
                row = sheet.createRow(rowNumber++);
                cell = row.createCell(0);
                cell.setCellStyle(style1);
                cell.setCellValue(new HSSFRichTextString(abbreviation1 + " - " + column1Cell));

                cell = row.createCell(1);
                cell.setCellStyle(style1);

                String column2Cell = this.mountColumn2Cell(contents.get(column1Cell));
                if (column2Cell.length() > 0)
                {
                    column2Cell = abbreviation2 + " (" + column2Cell + ")";
                }
                cell.setCellValue(new HSSFRichTextString(column2Cell));
            }
        }
        return sheet;
    }

    /**
     * Mount the second cell of a row.
     * 
     * @param content The content of the cell.
     * @return A string representing the second cell.
     */
    private String mountColumn2Cell(List<String> content)
    {
        String result = "";

        for (String string : content)
        {
            result += string + ", ";
        }
        if (result.length() > 0)
        {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    /**
     * Creates the style used in the spreadsheet's subtitle.
     * 
     * @param workbook The object used to create a basic cell style.
     * @return A cell style to the subtitle.
     */
    private HSSFCellStyle createSubtitleStyle(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        font.setFontName("Arial");
        style.setFont(font);
        style.setWrapText(false);

        return style;
    }

    /**
     * Constructs the HSSFCellStyle object of a header cell.
     * 
     * @return The style of a header area.
     */
    private HSSFCellStyle getHeaderCellStyle(HSSFWorkbook workbook)
    {
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        return cellStyle;
    }

    /**
     * Constructs the HSSFCellStyle object of a text cell.
     * 
     * @return The style of a text area.
     */
    private HSSFCellStyle getTextAreaStyle(HSSFWorkbook workbook)
    {
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Courier New");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        return cellStyle;
    }
    
    //INSPECT - Lais new methods
    
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
    //INSPECT - Laís inclusão de information test case para geração da traceability matrix
    public List<String> getTestCasesFromRequirement(String requirement, List<TextualTestCase> testCases, InformationTestCase informationTestCase)
    {
        List<String> result = new ArrayList<String>();
        
        DecimalFormat df = new DecimalFormat("000");

        for (TextualTestCase testCase : testCases)
        {
            if (testCase.coversRequirement(requirement))
            {
                if(informationTestCase != null && !informationTestCase.getSuiteName().equals(""))
                {
                    result.add(informationTestCase.getSuiteName() + "-" + df.format(testCase.getId()));
                }
                else
                {
                    result.add(testCase.getTcIdHeader());
                }
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
    //INSPECT - Laís inclusão de information test case para geração da traceability matrix
    public List<String> getTestCaseReferences(String usecaseId, List<TextualTestCase> testCases, InformationTestCase informationTestCase)
    {
        List<String> result = new ArrayList<String>();
        
        DecimalFormat df = new DecimalFormat("000");

        for (TextualTestCase testCase : testCases)
        {
            if (testCase.coversUseCase(usecaseId))
            {
                if(informationTestCase != null && !informationTestCase.getSuiteName().equals(""))
                {
                    result.add(informationTestCase.getSuiteName() + "-" + df.format(testCase.getId()));
                }
                else
                {
                    result.add(testCase.getTcIdHeader());
                }
            }
        }

        return result;
    }

    /**
     * Returns a list of lexicographically ordered strings of use cases.
     * 
     * @return A list with the ordered use cases as strings.
     */
    public List<String> getOrderedUseCases()
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

    /**
     * Creates a traceability matrix that links requirements to use cases.
     */
    public HashMap<String, List<String>> createRTMRequirements()
    {
        HashMap<String, List<String>> columns = new HashMap<String, List<String>>();

        for (String requirement : ProjectManagerExternalFacade.getInstance()
                .getAllReferencedRequirementsOrdered())
        {
            columns.put(requirement, this.getUseCasesFromRequirement(requirement));
        }

        return columns;
    }

    /**
     * Creates a traceability matrix that links requirements to system test cases.
     */
    //INSPECT - Laís
    public HashMap<String, List<String>> createRTMSystemTest(List<TextualTestCase> testCases, InformationTestCase informationTestCase)
    {
        HashMap<String, List<String>> columns = new HashMap<String, List<String>>();

        for (String requirement : ProjectManagerExternalFacade.getInstance()
                .getAllReferencedRequirementsOrdered())
        {
            columns.put(requirement, this.getTestCasesFromRequirement(requirement, testCases, informationTestCase));
        }

        return columns;
    }

    /**
     * Creates a traceability matrix that links use cases to the system test cases.
     */
    //INSPECT - Laís
    public HashMap<String, List<String>> createRTMUseCase(List<TextualTestCase> testCases, InformationTestCase informationTestCase)
    {
        List<String> tmpUseCases = this.getOrderedUseCases();
        HashMap<String, List<String>> columns = new HashMap<String, List<String>>();

        for (String usecaseId : tmpUseCases)
        {
            columns.put(usecaseId, this.getTestCaseReferences(usecaseId, testCases, informationTestCase));
        }

        return columns;
    }

    

    
}
