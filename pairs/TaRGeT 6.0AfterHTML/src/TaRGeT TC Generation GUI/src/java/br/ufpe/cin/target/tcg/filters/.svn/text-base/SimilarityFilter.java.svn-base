/*
 * @(#)SimilarityFilter.java
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
 * wfo007    Jun 04, 2007   LIBmm42774   Initial creation.
 * wfo007    Aug 16, 2007   LIBmm42774   Modifications in removeSimilarPaths method.
 * wfo007    Aug 24, 2007   LIBmm42774   Rework after inspection LX201876.
 * wdt022    Mar 20, 2008   LIBpp56482   getSimilarity method created.
 * lmn3      Jan 18, 2009                Inclusion of equals() method and modification on the filter() method.
 */
package br.ufpe.cin.target.tcg.filters;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.tcg.exceptions.InvalidSimilarityException;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;


/**
 * This class is used to filter a Test Suite using the similarity technique to remove the most
 * similar test cases according to a specified percentage
 * 
 * <pre>
 * CLASS:
 * This class is used to filter a Test Suite using the similarity technique. 
 * 
 * RESPONSIBILITIES:
 * 1) Filter a Test Suite using the similarity technique.
 *
 * COLABORATORS:
 * 1) Uses TestSuite, TestCase and FlowStep classes
 *
 * USAGE:
 *  TestSuiteFilter filter = new SimilarityFilter(requiredSimilarity);
 *  TestSuite suite = filter.filter(TestSuite<TestCase<FlowStep>> suite);
 *  </pre>
 */
public class SimilarityFilter implements TestSuiteFilter<FlowStep>
{
    /**
     * The specified similarity percentage, i.e. the percentage of test cases that will remain after
     * the filtering.
     */
    private double requiredSimilarity;

    /**
     * Constructor. Initializes the filter, using a specified similarity.
     * 
     * @param requiredSimilarity The similarity percentage.
     * @throws InvalidSimilarityException Exception thrown if the specified coverage is out of range
     * (the range is between 0 and 100).
     */
    public SimilarityFilter(double requiredSimilarity) throws InvalidSimilarityException
    {
        if (requiredSimilarity < 0 || requiredSimilarity > 100)
        {
            throw new InvalidSimilarityException();
        }
        this.requiredSimilarity = requiredSimilarity;
    }

    /**
     * Filters the specified test suite using the similarity technique.
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> suite)
    {
        // Clone Suite
        List<TestCase<FlowStep>> suiteClone = new ArrayList<TestCase<FlowStep>>(suite
                .getTestCases());

        if (this.requiredSimilarity != 100)
        {

            List<List<Double>> matrix = null;
            
            // Build the similarity matrix according to the test cases extracted from the lts.
            if (suiteClone.size() > 100)
            {
                //calculates the similarity matrix and displays a progress bar indacating a the status of the task to the user
                matrix = getSimilarityMatrixWithProgress(suiteClone);
            }
            
            else
            {
                //calculates the similarity matrix without displaying a progress bar 
                matrix = getSimilarityMatrixWithoutProgress(suiteClone);
            }

            // Remove the most similar test cases using the similarity matrix.
            removeSimilarPaths(suiteClone, matrix, requiredSimilarity);

        }

        return new TestSuite<TestCase<FlowStep>>(suiteClone, suite.getName());
    }

    /**
     * Removes the most similar test cases using the similarity matrix.
     * 
     * @param testCases The list of test cases generated from the lts.
     * @param matrix The similarity matrix.
     * @param required The similarity percentage.
     */
    private void removeSimilarPaths(List<TestCase<FlowStep>> testCases, List<List<Double>> matrix,
            double required)
    {
        // Establish the number of test cases that must remain in the test suite.
        int qtdPaths = (int) Math.round(testCases.size() * (required / 100));
        // While there are test cases to be removed.
        while (testCases.size() > qtdPaths)
        {
            double max = 0;
            int indexLine = 0;
            int indexColumn = 0;
            // matrix.size()-1: since the matrix is symmetric,
            // the last line doesn't need to be verified.
            for (int i = 0; i < matrix.size() - 1; i++)
            {
                List<Double> line = new ArrayList<Double>(matrix.get(i));
                for (int j = i + 1; j < line.size(); j++)
                {
                    double similarity = line.get(j);
                    if (similarity > max)
                    {
                        max = similarity;
                        indexLine = i;
                        indexColumn = j;
                    }
                }
            }
            // Remove the shortest path
            int index = 0;
            if (testCases.get(indexLine).getSteps().size() < testCases.get(indexColumn).getSteps()
                    .size())
            { // The first is the shortest.
                index = indexLine;
            }
            else if (testCases.get(indexColumn).getSteps().size() < testCases.get(indexLine)
                    .getSteps().size())
            { // The second is the shortest.
                index = indexColumn;
            }
            else
            { // Both test case have the same size: random choice
                index = Math.random() < 0.5 ? indexLine : indexColumn;
            }

            // Remove the test case.
            testCases.remove(index);

            for (int i = 0; i < matrix.size(); i++)
            { // The first column only exists logically
                matrix.get(i).remove(index);
            }
            matrix.remove(index);
        }
    }

    /**
     * Retrieves the similarity matrix from a specified list of test cases.
     * 
     * @param testCases The list of test cases generated from the lts.
     * @return The similarity matrix for the specified list of test cases.
     */
    private List<List<Double>> getSimilarityMatrixWithProgress(final List<TestCase<FlowStep>> testCases)
    {
        ProgressMonitorDialog progressMonitor = new ProgressMonitorDialog(Display.getCurrent()
                .getActiveShell());
        
        final List<List<Double>> matrix = new ArrayList<List<Double>>(testCases.size());

        try
        {
            IRunnableWithProgress runnable = new IRunnableWithProgress()
            {
                
                public void run(IProgressMonitor monitor) throws InvocationTargetException,
                        InterruptedException
                {
                    monitor.beginTask(Properties.getProperty("applying_similarity_filter"), testCases.size()
                            * testCases.size());
                                        
                    // Iterate from the first to the penultimate row, since the matrix is symmetric.
                    for (int i = 0; i < testCases.size(); i++)
                    {
                        
                        monitor.subTask(Properties.getProperty("analyzing_similarity_of_test_case")
                                + " " + i + " " + Properties.getProperty("of") + " "
                                + testCases.size() + "...");
                        
                        List<Double> line = new ArrayList<Double>(testCases.size());
                        matrix.add(line);
                        for (int j = 0; j < testCases.size(); j++)
                        {
                            if (j <= i)
                            {
                                // Since the matrix is symmetrical, there's no need to retrieve the
                                // similarity for the symmetrical elements of the matrix.
                                line.add(0.00);
                            }
                            else
                            {
                                line.add(getSimilarity(testCases.get(i), testCases.get(j)));
                            }
                            
                            monitor.worked(1);
                        }
                        monitor.worked(1);
                    }                    
                    monitor.done();                    
                }
            };
            
            progressMonitor.run(true, false, runnable);
            
            return matrix;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }        
    }
    
    /**
     * Retrieves the similarity matrix from a specified list of test cases.
     * 
     * @param testCases The list of test cases generated from the lts.
     * @return The similarity matrix for the specified list of test cases.
     */
    private List<List<Double>> getSimilarityMatrixWithoutProgress(final List<TestCase<FlowStep>> testCases)
    {
        List<List<Double>> matrix = new ArrayList<List<Double>>(testCases.size());
        
        // Iterate from the first to the penultimate row, since the matrix is symmetric.
        for (int i = 0; i < testCases.size(); i++)
        {
            
            List<Double> line = new ArrayList<Double>(testCases.size());
            matrix.add(line);
            for (int j = 0; j < testCases.size(); j++)
            {
                if (j <= i)
                {
                    // Since the matrix is symmetrical, there's no need to retrieve the
                    // similarity for the symmetrical elements of the matrix.
                    line.add(0.00);
                }
                else
                {
                    line.add(getSimilarity(testCases.get(i), testCases.get(j)));
                }
            }            
        }
        
        return matrix;
    }

    /**
     * <pre>
     * Retrieve the similarity between two test cases.
     * Given:
     * 	N = Number of matching steps for both test cases;
     * 	S1 = Size of the first test case.
     * 	S2 = Size of the second test case.
     *  
     *  The similarity is established as:
     *   
     *   Similarity = N / ( (S1+S2)/2 )
     * </pre>
     * 
     * @param testCase1 The "first" test case.
     * @param testCase2 The "second" test case.
     * @return The similarity between the specified test cases.
     */
    private Double getSimilarity(TestCase<FlowStep> testCase1, TestCase<FlowStep> testCase2)
    {
        int similarity = 0;
        // Establish the number of matching steps of both test cases.
        for (FlowStep step1 : testCase1.getSteps())
        {
            for (FlowStep step2 : testCase2.getSteps())
            {
                similarity = step1.equals(step2) ? similarity + 1 : similarity;
            }
        }

        // Retrieve each test case size.
        int sizePath1 = testCase1.getSteps().size();
        int sizePath2 = testCase2.getSteps().size();

        // Establish the similarity between both test cases.
        double totalSimilarity = (double) similarity / ((double) (sizePath1 + sizePath2) / 2);
        return totalSimilarity;
    }

    /**
     * Gets the path coverage for the filter.
     * 
     * @return The double value representing the similarity.
     */
    public double getSimilarity()
    {
        return this.requiredSimilarity;
    }

    
    public boolean equals(TestSuiteFilter<FlowStep> filter)
    {
        if (filter instanceof SimilarityFilter)
        {
            return ((SimilarityFilter) filter).getSimilarity() == this.getSimilarity();
        }
        else
        {
            return false;
        }
    }
}
