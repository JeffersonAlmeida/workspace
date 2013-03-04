/*
 * @(#)ExcelExtractor.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783    07/07/2008     LIBhh00000   Initial creation.
 * fsf2      16/03/2009                  Adapted for POI 3.2.
 */
package com.motorola.btc.research.target.tc3output.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.tcg.extensions.extractor.ExtractorDocumentExtension;
import com.motorola.btc.research.target.tcg.extractor.TextualStep;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.propertiesreader.TestCaseProperties;

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
// INSPECT Felype
public class ExcelExtractor implements ExtractorDocumentExtension
{
    /** The iterator that contains all rows of the sheet. */
    private static Iterator<HSSFRow> rowIterator;

    /** The row currently being read. */
    private static HSSFRow currentRow;

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

        setWorkbook(file);

        nextRow();
        nextRow();

        while (currentRow != null
                && (currentRow.getCell(0).getCellType() == HSSFCell.CELL_TYPE_NUMERIC || (currentRow
                        .getCell(0).getRichStringCellValue().getString().matches(".*\\d+"))))
        {
            int id = getId();
            String description = getDescription();
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

            ////////////////INSPECT Felype (integração)
            TextualTestCase testCase = new TextualTestCase(id, TestCaseProperties.getInstance().getTestCaseId(), steps, execType, regLevel, description,
                    objective, requirements, setups, initialConditions, note, finalConditions,
                    cleanup, usecaseReferences,"");
            result.add(testCase);

        }
        return result;
    }

    /**
     * Auxiliary method to retrieve the id of the test case.
     * 
     * @return The test case id.
     */
    private static int getId()
    {
        int id;
        
        if (currentRow.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING)
        {
            String strId = currentRow.getCell(0).getRichStringCellValue().getString().replaceAll(TestCaseProperties.getInstance().getTestCaseId(), "");
            id = Integer.parseInt(strId);
        }
        else
        {
            id = currentRow.getCell(0).getColumnIndex();
        }
        
        return id;
    }

    /**
     * Auxiliary method to retrieve the regression level of the test case.
     * 
     * @return The test case regression level.
     */
    private static String getRegressionLevel()
    {
        return currentRow.getCell(1).getRichStringCellValue().getString();

    }

    /**
     * Auxiliary method to retrieve the execution type of the test case.
     * 
     * @return The test case execution type.
     */
    private static String getExecutionType()
    {
        return currentRow.getCell(2).getRichStringCellValue().getString();
    }

    /**
     * Auxiliary method to extract the string id of the test case.
     * 
     * @return The test case string id.
     */
    private static String getDescription()
    {
        return currentRow.getCell(3).getRichStringCellValue().getString();
    }

    /**
     * Auxiliary method to extract the objective of the test case.
     * 
     * @return The test case objective.
     */
    private static String getObjective()
    {
        String objective = currentRow.getCell(4).getRichStringCellValue().getString();
        nextRow();
        return objective;
    }

    /**
     * Auxiliary method to extract the use case references of the test case.
     * 
     * @return The use case references of the test case.
     */
    private static String getUsecaseReferences()
    {
//        List<String> list = new ArrayList<String>();
//        String[] useCases = currentRow.getCell((short) 4).getStringCellValue().trim().split(",");
//        list.addAll(Arrays.asList(useCases));

        String useCaseReferences = currentRow.getCell(4).getRichStringCellValue().getString().trim();
        nextRow();
        return useCaseReferences;
    }

    /**
     * Auxiliary method to extract the requirements of the test case.
     * 
     * @return The requirements of the test case.
     */
    private static String getRequirements()
    {
//        Set<String> set = new HashSet<String>();
//        String[] requirements = currentRow.getCell((short) 4).getStringCellValue().trim()
//                .split(",");
//        set.addAll(Arrays.asList(requirements));
        String requirements = currentRow.getCell(4).getRichStringCellValue().getString().trim();
        nextRow();
        return requirements;
    }

    /**
     * Auxiliary method to extract the setup of the test case.
     * 
     * @return The setup of the test case.
     */
    private static String getSetups()
    {
//        List<String> list = new ArrayList<String>();
//        String setups = currentRow.getCell((short) 4).getStringCellValue().trim();
//        list.add(setups);
        String setups = currentRow.getCell(4).getRichStringCellValue().getString().trim();
        nextRow();
        return setups;
    }

    /**
     * Auxiliary method to extract the initial conditions of the test case.
     * 
     * @return The initial conditions of the test case.
     */
    private static String getInitialConditions()
    {
//        List<String> list = new ArrayList<String>();
//        String initialConditions = currentRow.getCell((short) 4).getStringCellValue().trim();
//        list.add(initialConditions);
        String initialConditions = currentRow.getCell(4).getRichStringCellValue().getString().trim();
        nextRow();
        return initialConditions;
    }

    /**
     * Auxiliary method to extract the note of the test case.
     * 
     * @return The note of the test case.
     */
    private static String getNote()
    {
        String note = currentRow.getCell(4).getRichStringCellValue().getString().trim();
        nextRow();
        return note;
    }

    /**
     * Auxiliary method to extract the steps of the test case.
     * 
     * @return The steps of the test case.
     */
    private static List<TextualStep> getSteps()
    {
        nextRow();

        List<TextualStep> list = new ArrayList<TextualStep>();
        while (currentRow.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC
                || currentRow.getCell(3).getRichStringCellValue().getString().trim().matches("\\d+"))
        {
            String action = currentRow.getCell(4).getRichStringCellValue().getString().trim();
            String response = currentRow.getCell(5).getRichStringCellValue().getString().trim();
            TextualStep step = new TextualStep(action, response, new StepId("", "", ""));
            list.add(step);
            nextRow();
        }

        return list;
    }

    /**
     * Auxiliary method to extract the final conditions of the test case.
     * 
     * @return The final conditions of the test case.
     */
    private static String getFinalConditions()
    {
//        List<String> list = new ArrayList<String>();
//        String finalConditions = currentRow.getCell((short) 4).getStringCellValue().trim();
//        list.add(finalConditions);
        String finalConditions = currentRow.getCell(4).getRichStringCellValue().getString().trim();
        nextRow();
        return finalConditions;
    }

    /**
     * Auxiliary method to extract the cleanup of the test case.
     * 
     * @return The cleanup of the test case.
     */
    private static String getCleanUp()
    {
//        List<String> list = new ArrayList<String>();
//        String cleanup = currentRow.getCell((short) 4).getStringCellValue().trim();
//        list.add(cleanup);
        String cleanup = currentRow.getCell(4).getRichStringCellValue().getString().trim();
        nextRow();
        return cleanup;
    }

    /**
     * Auxiliary method to set the Excel file to be read.
     * 
     * @param file The file to be read
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    private static void setWorkbook(File file) throws IOException, FileNotFoundException
    {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = workbook.getSheetAt(1);
        rowIterator = sheet.rowIterator();
    }

    /**
     * Auxiliary method to get the next row of the file being read.
     * 
     * @return The next row of the test suite file.
     */
    private static HSSFRow nextRow()
    {
        if (rowIterator.hasNext())
        {
            currentRow = (HSSFRow) rowIterator.next();
        }
        else
        {
            currentRow = null;
        }
        return currentRow;
    }

}
