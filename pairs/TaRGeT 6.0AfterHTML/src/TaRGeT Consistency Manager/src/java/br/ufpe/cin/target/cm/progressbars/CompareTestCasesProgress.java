/*
 * @(#)CompareTestCasesProgress.java
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
 * faas      12/08/2008                  Initial creation.
 */
package br.ufpe.cin.target.cm.progressbars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import br.ufpe.cin.target.cm.controller.ConsistencyManagementController;
import br.ufpe.cin.target.cm.tcsimilarity.logic.Comparison;
import br.ufpe.cin.target.cm.tcsimilarity.logic.ComparisonResult;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

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
                monitor.subTask(Properties.getProperty("extracting_test_suite"));
                // get the test cases of the test suite that was selected to be
                // compared with the new test case
                List<TextualTestCase> oldTestSuite = consistencyManagement
                        .extractTestSuite(oldTestSuiteFile);

                if (oldTestSuite.size() > 0)
                {
                    int nextID = oldTestSuite.get(oldTestSuite.size() - 1).getId() + 1;

                    if (ProjectManagerController.getInstance().getCurrentProject()
                            .getNextAvailableTestCaseID() > nextID)
                    {
                        nextID = ProjectManagerController.getInstance().getCurrentProject()
                                .getNextAvailableTestCaseID();
                    }

                    ProjectManagerController.getInstance().getCurrentProject()
                            .setNextAvailableTestCaseID(nextID);
                    ProjectManagerController.getInstance().createXMLFile();
                }

                this.comparison = new Comparison();

                Collections.sort(this.newTestSuite);

                int i = 1;

                for (TextualTestCase newTestCase : this.newTestSuite)
                {
                    monitor
                    .subTask(Properties.getProperty("comparing_test_case") + " " + i + " "
                            + Properties.getProperty("of") + " " + this.newTestSuite.size()
                            + "...");
                    
                    List<ComparisonResult> comparisonResults = consistencyManagement.compare(
                            newTestCase, oldTestSuite);                    

                    this.comparison.addComparisonResults(newTestCase, comparisonResults);

                    i++;

                    monitor.worked(1);

                    if (monitor.isCanceled())
                    {
                        throw new InterruptedException();
                    }
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
        finally
        {
            monitor.done();
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
