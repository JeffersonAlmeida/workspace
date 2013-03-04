/*
 * @(#)ExcelFileFormatter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
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
 * fsf2      Feb 17, 2008       -        Changed the xls generation.
 */
package com.motorola.btc.research.target.tc4output.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

import com.motorola.btc.research.target.tcg.extensions.output.OutputDocumentExtension;
import com.motorola.btc.research.target.tcg.extractor.TextualStep;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.propertiesreader.TestCaseProperties;

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
 * 
 */
public class ExcelFileFormatter extends OutputDocumentExtension{

	public ExcelFileFormatter() 
	{
	}
	
	/**
	 * Creates the revision history sheet.
	 */
	private void initRevisionHistory() 
	{
		this.tcSheet = this.createSheet("Revision History");

		int nextLine = 0;

		HSSFRow row = this.tcSheet.createRow(nextLine++);
		this.createCell(row, 0, "", this.textAreaStyle);

		row = this.tcSheet.createRow(nextLine++);
		this.createCell(row, 0, "Date Last Updated", this.blueAreaStyle);
		this.createCell(row, 1, "Modified By", this.blueAreaStyle);
		this.createCell(row, 2, "Comments", this.blueAreaStyle);

		row = this.tcSheet.createRow(nextLine++);
		this.createEmptyFields(row, 0, 2);
	}

	/**
	 * Creates the Test Suite Keywords sheet.
	 */
	private void initTestSuiteKeywords() 
	{
		this.tcSheet = this.createSheet("Test Suite Keywords");

		int nextLine = 0;

		nextLine = generateTestSuiteKeywordsFields(nextLine,"Test Suite Keywords","","Keyword Type","Keyword Value");
		nextLine = generateTestSuiteKeywordsFields(nextLine,"Test Case Keywords","Test Case Name","Keyword Type","Keyword Value");
		nextLine = generateTestSuiteKeywordsFields(nextLine,"Test Suite Requirements","","Requirement Type","Requirement Value");
		nextLine = generateTestSuiteKeywordsFields(nextLine,"Test Case Requirements","Test Case Name","Requirement Type","Requirement Value");

		HSSFRow row = this.tcSheet.createRow(nextLine++);
		this.createEmptyFields(row, 0, 2);
	}

	/**
	 * Create a sheet with a given title.
	 * @param title
	 * @return A sheet with the given title.
	 */
	private HSSFSheet createSheet(String title)
	{
		HSSFSheet sheet = this.workBook.createSheet(title);

		sheet.setDisplayGridlines(false);
		sheet.setDefaultColumnWidth(27);

		return sheet;
	}

	/**
	 * Creates the test suite keywords sheet.
	 */
	private int generateTestSuiteKeywordsFields(int nextLine, String textTitle, String textCell1, String textCell2, String textCell3) 
	{
		HSSFRow row = this.tcSheet.createRow(nextLine++);
		this.createCell(row, 0, "", this.textAreaStyle);

		row = this.tcSheet.createRow(nextLine++);
		this.createMergedLine(row, textTitle, nextLine-1, 0, 2, this.blueAreaStyle);

		row = this.tcSheet.createRow(nextLine++);
		this.createCell(row, 0, textCell1, this.greyAreaStyle);
		this.createCell(row, 1, textCell2, this.greyAreaStyle);
		this.createCell(row, 2, textCell3, this.greyAreaStyle);

		return nextLine;
	}

	/**
	 * Creates the test case sheet.
	 */
	private void initTCSheet() 
	{
		this.tcSheet = this.createSheet("Test Suite Details");
		this.tcSheet.setColumnHidden(0, true);//hidden the column A

		this.mountHeader();

		int tcCount = TestCaseProperties.getInstance().getTestCaseInitialId();

		for (TextualTestCase testCase : this.testCases) 
		{
			this.mountTestCaseInfo(tcCount++, testCase);
			this.mountUseCaseReferences(testCase);
			this.mountTestCaseInitialFields(testCase);
			this.mountTestCaseNotes(testCase);
			this.mountTestCaseSteps(testCase);
			this.mountTestCaseFinalFields(testCase);
		}

		this.mountAutomationTable();
	}

	/**
	 * Creates the automation table
	 */
	private void mountAutomationTable() 
	{
		this.nextLine = this.nextLine + 1;

		HSSFRow row = this.tcSheet.createRow(this.nextLine++);

		this.createMergedLine(row, "Automation Table:", this.nextLine-1, 1, 3, this.blueAreaStyle);

		row = this.tcSheet.createRow(this.nextLine++);
		this.createCell(row, 1, "Case", this.headerCellStyleWithBorders);
		this.createCell(row, 2, "Verified", this.headerCellStyleWithBorders);
		this.createCell(row, 3, "System", this.headerCellStyleWithBorders);
		this.createCell(row, 4, "Script Information", this.headerCellStyleWithBorders);
		this.createCell(row, 5, "Parameters", this.headerCellStyleWithBorders);
	}

	/**
	 * Creates a cell style and set the cell borders according to the given parameters.
	 * This method is used to create the template information area in the intro sheet.
	 * @param borderBottom if the cell style will have a bottom border
	 * @param borderTop if the cell style will have a top border
	 * @param borderLeft if the cell style will have a left border
	 * @param borderRight if the cell style will have a right border
	 * @return a cell style with the desired borders
	 */
	public HSSFCellStyle createTemplateInfoCellStyle(boolean borderBottom, boolean borderTop, 
			boolean borderLeft, boolean borderRight)
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();
		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		if(borderBottom){
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		}
		if(borderTop){
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		if(borderLeft){
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		}
		if(borderRight){
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}
		return cellStyle;
	}

	/**
	 * Method that should be used to write the excel file.
	 * 
	 * @param file
	 *            The file where the excel information will be written.
	 * @throws IOException
	 *             Exception launched in case of something goes wrong during the
	 *             file writing
	 */
	public void writeTestCaseDataInFile(File file) throws IOException
	{
		this.nextLine = 0;

		this.initWorkbookAndStyles();
		this.initTCSheet();
		this.initTestSuiteKeywords();
		this.initRevisionHistory();

		FileOutputStream fos = new FileOutputStream(file);
		this.workBook.write(fos);
		fos.close();
	}

	private void initWorkbookAndStyles() {
		this.workBook = new HSSFWorkbook();

		this.blueAreaStyle = this.getBlueAreaStyle();
		this.greyAreaStyle = this.getGreyAreaStyle();
		this.headerCellStyle = this.getHeaderCellStyle();
		this.headerCellStyleWithBorders = this.getHeaderCellStyleWithBorders();
		this.textAreaStyle = this.getTextAreaStyle();
	}

	/**
	 * Constructs the HSSFCellStyle object of a header cell.
	 * 
	 * @return The style of a header area.
	 */
	private HSSFCellStyle getHeaderCellStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index); // the default color is black.
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a cell with borders.
	 * 
	 * @return The style of a grey area.
	 */
	private HSSFCellStyle getHeaderCellStyleWithBorders()
	{
		HSSFCellStyle cellStyle = this.getHeaderCellStyle();

		return this.setBorders(cellStyle);
	}

	/**
	 * Constructs the HSSFCellStyle object of a blue cell.
	 * 
	 * @return The style of a blue area.
	 */
	private HSSFCellStyle getBlueAreaStyle() 
	{
		HSSFCellStyle cellStyle = getHeaderCellStyleWithBorders();

		cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);


		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a grey cell.
	 * 
	 * @return The style of a grey area.
	 */
	private HSSFCellStyle getGreyAreaStyle() 
	{
		HSSFCellStyle cellStyle = getHeaderCellStyleWithBorders();

		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);


		return cellStyle;
	}


	/**
	 * Constructs the HSSFCellStyle object of a text cell.
	 * 
	 * @return The style of a text area.
	 */
	private HSSFCellStyle getTextAreaStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();

		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		this.setBorders(cellStyle);

		return cellStyle;
	}

	/**
	 * Sets the borders of a given cell.
	 * @param cellStyle
	 * @return The given cell with borders.
	 */
	private HSSFCellStyle setBorders(HSSFCellStyle cellStyle) 
	{
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

	/**
	 * This method mounts the test case info: case number, status, regression level,
	 * execution type, test case name, objective and expected results.
	 * 
	 * @param tcCount
	 *            The case number inside the generated test suite.
	 * @param testCase
	 *            The test case object where the information will be retrieved.
	 */
	protected void mountTestCaseInfo(int tcCount, TextualTestCase testCase)
	{
		HSSFRow row = this.tcSheet.createRow(this.nextLine++);

		// Case number
		this.createCell(row, 1, testCase.getTcIdHeader() + testCase.getId(), this.textAreaStyle);

		// Status field.
		this.createCell(row, 2, testCase.getStatus(), this.textAreaStyle);

		// Reg Level
		this.createCell(row, 3, testCase.getRegressionLevel(), this.textAreaStyle);

		// Exe Type
		this.createCell(row, 4, testCase.getExecutionType(), this.textAreaStyle);

		// Description
		this.createCell(row, 5, testCase.getDescription(), this.textAreaStyle);

		// Procedure/Objective
		this.createCell(row, 6, testCase.getObjective(), this.textAreaStyle);

		// Expected Results
		this.createCell(row, 7, "", this.textAreaStyle);
	}

	/**
	 * Adds a row with the use case references.
	 * 
	 * @param testCase
	 *            The test case from which the use case references will be
	 *            extracted.
	 */
	protected void mountUseCaseReferences(TextualTestCase testCase) 
	{
		HSSFRow row = this.tcSheet.createRow(this.nextLine++);

		// The label
		this.createCell(row, 5, "Use Cases: ", this.textAreaStyle);

		// The references
		this.createCell(row, 6, testCase.getUseCaseReferences(), this.textAreaStyle);

		// Empty fields
		this.createEmptyFields(row, 1, 4);
		this.createEmptyFields(row, 7, 7);
	}

	/**
	 * Creates empty fields.
	 * @param row
	 * @param inicialCell
	 * @param finalCell
	 */
	private void createEmptyFields(HSSFRow row, int inicialCell, int finalCell) 
	{
		for(int i = inicialCell; i <= finalCell; i++)
		{
			this.createCell(row, i, "", this.textAreaStyle);
		}
	}

	/**
	 * Mounts the test case notes field.
	 * 
	 * @param testCase
	 *            The <code>testCase</code> that contains the notes.
	 */
	protected void mountTestCaseNotes(TextualTestCase testCase) 
	{
		HSSFRow notesArea = this.tcSheet.createRow(this.nextLine++);

		// The label
		this.createCell(notesArea, 5, "Notes:", this.textAreaStyle);

		// The notes
		this.createCell(notesArea, 6, testCase.getNote(), this.textAreaStyle);

		// Empty fields
		this.createEmptyFields(notesArea, 1, 4);
		this.createEmptyFields(notesArea, 7, 7);
	}

	/**
	 * Mounts the test case steps fields.
	 * 
	 * @param testCase
	 *            The test case object that contains the step fields.
	 */
	protected void mountTestCaseSteps(TextualTestCase testCase) 
	{
		HSSFRow tcStepHeader = this.tcSheet.createRow(this.nextLine++);

		this.createCell(tcStepHeader, 5, "Test Procedure (Step Number):", this.textAreaStyle);

		// Empty fields
		this.createEmptyFields(tcStepHeader, 1, 4);
		this.createEmptyFields(tcStepHeader, 6, 7);

		int stepNumber = 1;

		for (TextualStep step : testCase.getSteps()) 
		{
			HSSFRow row = this.tcSheet.createRow(this.nextLine++);

			// Step Number
			this.createCell(row, 5, String.valueOf(stepNumber), this.textAreaStyle);

			// Step
			this.createCell(row, 6, String.valueOf(step.getAction()), this.textAreaStyle);

			// Expected Result
			this.createCell(row, 7, String.valueOf(step.getResponse()), this.textAreaStyle);

			//Empty Fields
			this.createEmptyFields(row, 1, 4);

			stepNumber++;
		}
	}

	/**
	 * This method mounts the test case final conditions and cleanup
	 * information.
	 * 
	 * @param testCase
	 *            The test case that contains the final conditions and cleanup
	 *            information
	 */
	protected void mountTestCaseFinalFields(TextualTestCase testCase) 
	{
		// Creating the final condition and cleanup rows
		HSSFRow finalCondRow = this.tcSheet.createRow(this.nextLine++);
		HSSFRow cleanupRow = this.tcSheet.createRow(this.nextLine++);

		// Setting the header
		this.createCell(finalCondRow, 5, "Final Conditions:", this.textAreaStyle);
		this.createCell(cleanupRow, 5, "Cleanup:", this.textAreaStyle);

		// preparing final conditions text.
		this.createCell(finalCondRow, 6, testCase.getFinalConditions(), this.textAreaStyle);

		// Creating cleanup field.
		this.createCell(cleanupRow, 6, testCase.getCleanup(), this.textAreaStyle);

		// Creating empty cells
		this.createEmptyFields(finalCondRow, 1, 4);
		this.createEmptyFields(finalCondRow, 7, 7);

		this.createEmptyFields(cleanupRow, 1, 4);
		this.createEmptyFields(cleanupRow, 7, 7);
	}

	/**
	 * This method mounts the test case initial conditions, setup information
	 * and related requirements.
	 * 
	 * @param testCase
	 *            The test case that contains the initial conditions, setup
	 *            information and related requirements.
	 */
	protected void mountTestCaseInitialFields(TextualTestCase testCase) 
	{
		// Creating initial condition and setup rows
		HSSFRow requirementsRow = this.tcSheet.createRow(this.nextLine++);
		HSSFRow setupRow = this.tcSheet.createRow(this.nextLine++);
		HSSFRow iniCondRow = this.tcSheet.createRow(this.nextLine++);

		// Preparing the header
		this.createCell(requirementsRow, 5, "Requirements:", this.textAreaStyle);
		this.createCell(setupRow, 5, "Setup:", this.textAreaStyle);
		this.createCell(iniCondRow, 5, "Initial Conditions:", this.textAreaStyle);

		// Preparing Requirements Information
		this.createCell(requirementsRow, 6, testCase.getRequirements(), this.textAreaStyle);

		// Preparing setup information
		this.createCell(setupRow, 6, testCase.getSetups(), this.textAreaStyle);

		// Preparing initial conditions text.
		this.createCell(iniCondRow, 6, testCase.getInitialConditions(), this.textAreaStyle);

		// Creating empty cells
		this.createCell(requirementsRow, 7, "", this.textAreaStyle);
		this.createCell(setupRow, 7, "", this.textAreaStyle);
		this.createCell(iniCondRow, 7, "", this.textAreaStyle);

		this.createEmptyFields(requirementsRow, 1, 4);
		this.createEmptyFields(requirementsRow, 7, 7);

		this.createEmptyFields(setupRow, 1, 4);
		this.createEmptyFields(setupRow, 7, 7);

		this.createEmptyFields(iniCondRow, 1, 4);
		this.createEmptyFields(iniCondRow, 7, 7);
	}

	/**
	 * This method mounts the test suite header.
	 */
	protected void mountHeader() 
	{
		this.createNewField("Suite Name:",true);
		this.createNewField("Exported:",true);
		this.createNewField("Description:",true);
		this.createNewField("Created By:",true);

		this.createNewField("Last Modified By:",true);
		this.createNewField("Suite Status:",true);
		this.createNewField("Ownership Group:",true);

		this.createNewField("Read Permissions:",true);
		this.createNewField("Write Permissions:",true);
		this.createNewField("Attached File Desc:",true);
		this.createNewField("Procedure:",true);
		this.createNewField("Notes:",true);
		HSSFRow lastRow = this.createNewField("",false);

		lastRow.getCell(4).setCellStyle(this.headerCellStyle);

		this.createColumnTitles();
	}

	/**
	 * Creates the column titles cells.
	 */
	private void createColumnTitles() 
	{
		HSSFRow row = this.tcSheet.createRow(this.nextLine++);

		this.createMergedLine(row, "TPS Data:", this.nextLine-1, 1, 3, this.blueAreaStyle);

		row = this.tcSheet.createRow(this.nextLine++);

		this.createCell(row, 1, "Test Case Name", this.headerCellStyleWithBorders);
		this.createCell(row, 2, "Status", this.headerCellStyleWithBorders);
		this.createCell(row, 3, "Regression Level", this.headerCellStyleWithBorders);
		this.createCell(row, 4, "Execution Method", this.headerCellStyleWithBorders);
		this.createCell(row, 5, "Case Description", this.headerCellStyleWithBorders);
		this.createCell(row, 6, "Step Description", this.headerCellStyleWithBorders);
		this.createCell(row, 7, "Expected Results", this.headerCellStyleWithBorders);
	}

	/**
	 * Creates a merged line with the given text.
	 * @param row
	 * @param text
	 * @param line
	 * @param startCell
	 * @param endCell
	 * @param cellStyle
	 */
	private void createMergedLine(HSSFRow row, String text, int line, int startCell, int endCell, HSSFCellStyle cellStyle)
	{
		this.tcSheet.addMergedRegion(new CellRangeAddress(line, line, startCell, endCell));
		this.createCell(row, startCell, text, cellStyle);

		for(int i = startCell+1; i <= endCell; i++)
		{
			this.createCell(row, i, "", cellStyle);
		}
	}

	/**
	 * Creates a cell in the given row with the given text and style.
	 * @param row
	 * @param cellNumber
	 * @param text
	 * @param style
	 * @return The created cell.
	 */
	private HSSFCell createCell(HSSFRow row, int cellNumber, String text, HSSFCellStyle style)
	{
		HSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new HSSFRichTextString(text));
		cell.setCellStyle(style);

		return cell;
	}

	/**
	 * Creates a field with the given text.
	 * @param cellText
	 * @param merge
	 * @return The new line.
	 */
	private HSSFRow createNewField(String cellText, boolean merge)
	{
		HSSFRow row = this.tcSheet.createRow(this.nextLine++);

		this.createMergedLine(row, cellText, this.nextLine-1, 1, 3, this.headerCellStyle);
		createCell(row,4,"",this.textAreaStyle);

		return row;
	}
}
