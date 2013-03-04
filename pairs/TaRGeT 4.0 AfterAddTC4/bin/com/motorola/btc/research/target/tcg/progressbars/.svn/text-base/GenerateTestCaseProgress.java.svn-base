/*
 * @(#)GenerateTestCaseProgress.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 10, 2007    LIBkk11577   Initial creation.
 * dhq348   Jan 18, 2007    LIBkk11577   Rework of inspection LX135556.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * wdt022   May 31, 2007    LIBmm42774   Modifications due to CR.
 * dhq348   Aug 14, 2007    LIBmm42774   Added the path coverage and test purpose.
 * dhq348   Aug 21, 2007    LIBmm42774   Rework after inspection LX201094.
 * dhq348   Aug 23, 2007    LIBmm42774   Changed the type of purposes list.
 * dhq348   Nov 09, 2007    LIBnn34008   Modifications according to CR.
 * dhq348   Jan 14, 2008    LIBnn34008   Rework after inspection LX229625.
 * dhq348   Jan 21, 2008    LIBoo10574   Rework after inspection LX229631.
 * wdt022   Mar 25, 2008    LIBpp56482   Adapted to handle the write excel file method in the controller.
 */
package com.motorola.btc.research.target.tcg.progressbars;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.controller.TestCaseGeneratorController;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.filters.TestPurpose;
import com.motorola.btc.research.target.tcg.filters.TestSuiteFilterAssembler;

/**
 * Displays a progress bar while the test cases are being generated. The class is also responsible
 * for starting the test case generation process.
 * 
 * <pre>
 * 
 * RESPONSIBILITIES:
 * 1) Displays a progress bar indicating the progress of the generation task.
 * </pre>
 */
public class GenerateTestCaseProgress implements IRunnableWithProgress
{
    /**
     * The name of the task that is displayed by the progress bar.
     */
    private String taskname;

    /**
     * Attribute that represents the path coverage of the tests that will be generated.
     */
    private int pathCoverage;

    /**
     * Represents the test purpose list.
     */
    private List<TestPurpose> testPurposesList;

    /**
     * The list of the requirements that will be used in the generation task.
     */
    private Set<String> requirementsList;

    /**
     * Represents the time stamp as a <code>long</code> of the 'current time' before the beginning
     * of tests generation.
     */
    private long beforeGenerating;

    /**
     * Represents the time stamp as a <code>long</code> of the 'current time' after the finish of
     * tests generation.
     */
    private long afterGenerating;

    /**
     * Represents the number of generated test cases.
     */
    private int numberOfGeneratedTestCases;


    /**
     * The set of selected usecases.
     */
    private HashMap<Feature, List<UseCase>> usecases;

    
    /**
     * Creates a progress bar with main task named 'taskname'.
     * 
     * @param taskname The task name to be displayed.
     * @param requirementsList The list of selected requirements.
     * @param usecasesList The list of selected use cases.
     * @param pathCoverage The path coverage of the tests that will be generated.
     * @param testPurposes The list containing the created test purposes.
     */
    public GenerateTestCaseProgress(String taskname, Set<String> requirementsList,
            HashMap<Feature, List<UseCase>> usecasesList, int pathCoverage,
            List<TestPurpose> testPurposes)
    {
        this.taskname = taskname;
        this.requirementsList = requirementsList;
        this.usecases = usecasesList;
        this.pathCoverage = pathCoverage;
        this.testPurposesList = testPurposes;
        
    }

    /**
     * Displays a progress bar indicating that test cases are being generated.
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
            monitor.beginTask(this.taskname, 4);

            ProjectManagerExternalFacade projectFacade = ProjectManagerExternalFacade.getInstance();

            this.beforeGenerating = System.currentTimeMillis();

            TestCaseGeneratorController controller = TestCaseGeneratorController.getInstance();

            monitor.subTask("Generating testing model from documents.");
            monitor.worked(1);

            Collection<PhoneDocument> documents = projectFacade.getCurrentProject()
                    .getPhoneDocuments();

            
            LTS<FlowStep, Integer> lts = controller.generateLTSModel(documents);

            monitor.subTask("Generating test cases.");
            monitor.worked(2);

            TestSuiteFilterAssembler filterAssembler = new TestSuiteFilterAssembler();

            filterAssembler.setSimilarityFilter((double) this.pathCoverage);
            filterAssembler.setPurposeFilter(this.testPurposesList);

            filterAssembler.setRequirementsFilter(this.requirementsList);
            filterAssembler.setUseCasesFilter(this.usecases);

            TestSuite<TextualTestCase> testSuite = controller.generateTests(filterAssembler, lts,
                    documents);

            this.numberOfGeneratedTestCases = testSuite.getTestCases().size();

            monitor.subTask("Writting test suite excel file.");
            monitor.worked(2);

            controller.writeTestSuiteFile(testSuite);

            this.afterGenerating = System.currentTimeMillis();
        }
        catch (Throwable e)
        {
            throw new InvocationTargetException(e);
        }
    }

    /**
     * Gets the numberOfGeneratedTestCases value.
     * 
     * @return Returns the numberOfGeneratedTestCases.
     */
    public int getNumberOfGeneratedTestCases()
    {
        return numberOfGeneratedTestCases;
    }

    /**
     * Gets the total time of the test case generation process.
     * 
     * @return The amount of time spent during test case generation.
     */
    public long getGenerationTime()
    {
        return this.afterGenerating - this.beforeGenerating;
    }
}