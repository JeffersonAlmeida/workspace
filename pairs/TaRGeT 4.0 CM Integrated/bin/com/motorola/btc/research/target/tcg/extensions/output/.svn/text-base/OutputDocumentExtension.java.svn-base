/*
 * @(#)InputExtension.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * fsf2      Feb 27, 2009                Initial creation.
 */
package com.motorola.btc.research.target.tcg.extensions.output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.util.UseCaseUtil;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * This class represents the extension point for different kinds of generation output documents. This way, 
 * this class must be extended by clients that need to implement a new output document extension to the
 * TaRGeT system.
 * 
 * USAGE:
 * Generates the xls file. 
 * </pre>
 */
public abstract class OutputDocumentExtension
{
	/** The work book that contains all sheets. Represents the excel file */
	protected HSSFWorkbook workBook;

	/** The sheet that contains the test cases */
	protected HSSFSheet tcSheet;

	/** The style of the header cells */
	protected HSSFCellStyle headerCellStyle;

	/** The style of the header cells with borders*/
	protected HSSFCellStyle headerCellStyleWithBorders;

	/** The style of the blue cells */
	protected HSSFCellStyle blueAreaStyle;

	/** The style of the grey cells */
	protected HSSFCellStyle greyAreaStyle;

	/** The style of the text cells */
	protected HSSFCellStyle textAreaStyle;

	/** The number of the next sheet row */
	protected int nextLine;

	/** The test case list */
	protected List<TextualTestCase> testCases;

	/**
	 * Method that should be used to write the excel file.
	 * 
	 * @param file
	 *            The file where the excel information will be written.
	 * @throws IOException
	 *             Exception launched in case of something goes wrong during the
	 *             file writing
	 */
	public abstract void writeTestCaseDataInFile(File file) throws IOException;
	
	/**
	 * This method mounts the test case info: case number, status, regression level,
	 * execution type, test case name, objective and expected results.
	 * 
	 * @param tcCount
	 *            The case number inside the generated test suite.
	 * @param testCase
	 *            The test case object where the information will be retrieved.
	 */
	protected abstract void mountTestCaseInfo(int tcCount, TextualTestCase testCase);

	/**
	 * Adds a row with the use case references.
	 * 
	 * @param testCase
	 *            The test case from which the use case references will be
	 *            extracted.
	 */
	protected abstract void mountUseCaseReferences(TextualTestCase testCase);

	/**
	 * Mounts the test case notes field.
	 * 
	 * @param testCase
	 *            The <code>testCase</code> that contains the notes.
	 */
	protected abstract void mountTestCaseNotes(TextualTestCase testCase);

	/**
	 * Mounts the test case steps fields.
	 * 
	 * @param testCase
	 *            The test case object that contains the step fields.
	 */
	protected abstract void mountTestCaseSteps(TextualTestCase testCase);

	/**
	 * This method mounts the test case final conditions and cleanup
	 * information.
	 * 
	 * @param testCase
	 *            The test case that contains the final conditions and cleanup
	 *            information
	 */
	protected abstract void mountTestCaseFinalFields(TextualTestCase testCase);

	/**
	 * This method mounts the test case initial conditions, setup information
	 * and related requirements.
	 * 
	 * @param testCase
	 *            The test case that contains the initial conditions, setup
	 *            information and related requirements.
	 */
	protected abstract void mountTestCaseInitialFields(TextualTestCase testCase);

	/**
	 * This method mounts the test suite header.
	 */
	protected abstract void mountHeader();

	/**
	 * Gets a list of use cases from the specified <code>requirement</code>.
	 * 
	 * @param requirement
	 *            The requirement whose use cases will be returned.
	 * @return A list of use cases.
	 */
	protected List<String> getUseCasesFromRequirement(String requirement) 
	{
		List<String> result = new ArrayList<String>();

		for (UseCase usecase : ProjectManagerExternalFacade.getInstance()
				.getUseCasesByRequirement(requirement)) 
		{
			result.add(usecase.getId());
		}

		String[] orderedUseCases = result.toArray(new String[] {});
		Arrays.sort(orderedUseCases);

		return new ArrayList<String>(Arrays.asList(orderedUseCases));
	}

	/**
	 * Returns a list of test case ids related to the given
	 * <code>requirement</code>.
	 * 
	 * @param requirement
	 *            The requirement whose test cases will be returned.
	 * @return A list of test cases.
	 */
	protected List<String> getTestCasesFromRequirement(String requirement) 
	{
		List<String> result = new ArrayList<String>();

		for (TextualTestCase testcase : this.testCases) 
		{
			if (testcase.coversRequirement(requirement)) 
			{
				result.add(testcase.getTcIdHeader());
			}
		}

		return result;
	}

	/**
	 * Returns a list of test case ids related to the given
	 * <code>usecaseId</code>.
	 * 
	 * @param usecaseId
	 *            The id of the use case whose related test cases will be
	 *            returned.
	 * @return A list of test cases.
	 */
	protected List<String> getTestCaseReferences(String usecaseId) 
	{
		List<String> result = new ArrayList<String>();

		for (TextualTestCase testcase : this.testCases) 
		{
			if (testcase.coversUseCase(usecaseId))
			{
				result.add(testcase.getTcIdHeader());
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

		for (Feature feature : ProjectManagerExternalFacade.getInstance()
				.getCurrentProject().getFeatures()) 
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

	public void setTestCases(List<TextualTestCase> testCases)
	{
		this.testCases = testCases;
	}
	
	public List<TextualTestCase> getTestCases()
	{
		return this.testCases;
	}
}
