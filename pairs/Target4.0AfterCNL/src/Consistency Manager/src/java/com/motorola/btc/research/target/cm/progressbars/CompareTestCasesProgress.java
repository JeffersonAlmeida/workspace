/*
 * @(#)CompareTestCasesProgress.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   12/08/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.progressbars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.motorola.btc.research.target.cm.controller.ConsistencyManagementController;
import com.motorola.btc.research.target.cm.tcsimilarity.logic.Comparison;
import com.motorola.btc.research.target.cm.tcsimilarity.logic.ComparisonResult;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * Displays a progress bar while the test cases are being compared. The class is also responsible
 * for starting the test case comparison process.
 * 
 * <pre>
 * 
 * RESPONSIBILITIES:
 * 1) Displays a progress bar indicating the progress of the test case comparison task.
 * </pre>
 */
public class CompareTestCasesProgress implements IRunnableWithProgress
{
    /**
     * The name of the task that is displayed by the progress bar.
     */
    private String taskName;

    /**
     * The list of new test cases to be compared.
     */
    private List<TextualTestCase> newTestSuite;

    /**
     * The old test suite file.
     */
    private File oldTestSuiteFile;

    /**
     * The result of the comparison.
     */
    private Comparison comparison;

    /**
     * Creates a progress bar to start test case comparison.
     * 
     * @param taskName The task to be performed.
     * @param oldTestSuiteFile The old test suite file to compare.
     * @param newTestSuite The new test case to compare;
     */
    public CompareTestCasesProgress(String taskName, File oldTestSuiteFile,
            List<TextualTestCase> newTestSuite)
    {
        this.taskName = taskName;
        this.oldTestSuiteFile = oldTestSuiteFile;
        this.newTestSuite = newTestSuite;
    }

    /**
     * Displays a progress bar indicating that test cases are being compared.
     * 
     * @param monitor The monitor of the progress bar
     * @throws InvocationTargetException Thrown when an error during the launching of the progress
     * bar occurs.
     * @throws InterruptedException It is thrown when an error during the launching of the progress
     * bar occurs.
     */
    public void run(IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException
    {
        try
        {
            int totalWork = this.newTestSuite.size();
            monitor.beginTask(this.taskName, totalWork);

            ConsistencyManagementController consistencyManagement = ConsistencyManagementController
                    .getInstance();
            try
            {
                //get the test cases of the test suite that was selected to be compared with the new test case
                List<TextualTestCase> oldTestSuite = consistencyManagement
                        .extractTestSuite(oldTestSuiteFile);

                this.comparison = new Comparison();

                for (TextualTestCase newTestCase : this.newTestSuite)
                {
                    List<ComparisonResult> comparisonResults = consistencyManagement.compare(
                            newTestCase, oldTestSuite);
                    this.comparison.addComparisonResults(newTestCase, comparisonResults);
                    monitor.worked(1);
                }

            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (Throwable e)
        {
            throw new InvocationTargetException(e);
        }
    }

    /**
     * Gets the Comparison of new and old test suites.
     * 
     * @return The result of the comparison of new and old test suites.
     */
    public Comparison getComparison()
    {
        return this.comparison;
    }

}
