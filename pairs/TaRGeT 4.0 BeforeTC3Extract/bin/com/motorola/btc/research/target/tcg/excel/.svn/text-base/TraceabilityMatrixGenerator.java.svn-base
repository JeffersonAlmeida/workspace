/*
 * @(#)TraceabilityMatrixGenerator.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jul 6, 2007    LIBmm76995   Initial creation.
 */
package com.motorola.btc.research.target.tcg.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

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
            String abbreviation2, HSSFWorkbook workbook)
    {
        HSSFSheet sheet = null;
        if (workbook != null)
        {
            int rowNumber = 0;

            sheet = workbook.createSheet(spreadsheetTitle);
            sheet.setColumnWidth((short) 0, (short) 12000);
            sheet.setColumnWidth((short) 1, (short) 19000);

            HSSFRow row = sheet.createRow(rowNumber++); // empty row

            /* creates the subtitle row */
            row = sheet.createRow(rowNumber++);
            HSSFCell cell = row.createCell((short) 0);
            cell.setCellStyle(this.createSubtitleStyle(workbook));
            cell.setCellValue(subTitle);

            row = sheet.createRow(rowNumber++); // empty row

            /* creates the columns' title style */
            row = sheet.createRow(rowNumber++);
            HSSFCellStyle style1 = this.getHeaderCellStyle(workbook);

            /* creates the column 1's title */
            cell = row.createCell((short) 0);
            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellStyle(style1);
            cell.setCellValue(column1Title);

            /* creates the column 2's title */
            cell = row.createCell((short) 1);
            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell.setCellStyle(style1);
            cell.setCellValue(column2Title);

            /* creates the data's style */
            style1 = this.getTextAreaStyle(workbook);

            List<String> column1Data = this.sort(contents.keySet());
            for (String column1Cell : column1Data)
            {
                row = sheet.createRow(rowNumber++);
                cell = row.createCell((short) 0);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellStyle(style1);
                cell.setCellValue(abbreviation1 + " - " + column1Cell);

                cell = row.createCell((short) 1);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellStyle(style1);

                String column2Cell = this.mountColumn2Cell(contents.get(column1Cell));
                if (column2Cell.length() > 0)
                {
                    column2Cell = abbreviation2 + " (" + column2Cell + ")";
                }
                cell.setCellValue(column2Cell);
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

    /**
     * Sorts a collection of strings.
     * 
     * @param original The list to be sorted.
     * @return the sorted list.
     */
    private List<String> sort(Collection<String> original)
    {
        String[] sorted = original.toArray(new String[] {});
        Arrays.sort(sorted);

        return new ArrayList<String>(Arrays.asList(sorted));
    }
}
