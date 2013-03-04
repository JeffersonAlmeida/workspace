/*
 * @(#)ConsistencyManagementWizard.java
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
 * tnd783   07/07/2008    LIBhh00000    Initial creation.
 * fsf2		20/06/2009					Integration.
 */
package br.ufpe.cin.target.cm.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


import br.ufpe.cin.target.cm.editor.ConsistencyManagementEditorInput;
import br.ufpe.cin.target.cm.editor.ConsistencyManagementMultiPageEditor;
import br.ufpe.cin.target.cm.progressbars.CompareTestCasesProgress;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;

/**
 * <pre>
 * CLASS:
 * This class represents the Consistency Management wizard that is responsible for selecting an old test suite to compare.
 * 
 * RESPONSIBILITIES:
 * 1) Compare new test suite with selected old test suite
 * 2) Open the Test Comparison Page
 *
 * COLABORATORS:
 * 1) Uses OldTestSuitesPage
 * 2) Uses ProjectManagerExternalFacade
 *
 * USAGE:
 * ConsistencyManagementWizard wizard = new ConsistencyManagementWizard(<Shell>, <List<TextualTestCase>>)
 * </pre>
 */

public class ConsistencyManagementWizard extends Wizard
{
    /**
     * The test suites selection page.
     */
    private OldTestSuitesPage oldTestSuitesPage;

    /**
     * The list of new test cases to be compared.
     */
    private List<TextualTestCase> newTestSuite;

    /**
     * The parent component of this wizard.
     */
    private Shell parentShell;

    /**
     * Constructor of the Consistency Management wizard. Receives the parent shell of the current
     * wizard and a list of test cases to be compared.
     * 
     * @param parentShell The wizard's parent component.
     * @param newTestSuite The list of new test cases to be compared.
     */
    public ConsistencyManagementWizard(Shell parentShell, List<TextualTestCase> newTestSuite)
    {
        try
        {
            this.parentShell = parentShell;
            this.setWindowTitle(Properties.getProperty("consistency_management"));
            this.newTestSuite = newTestSuite;

            ProjectManagerExternalFacade pm = ProjectManagerExternalFacade.getInstance();
            String oldTestSuiteDir = pm.getCurrentProject().getTestSuiteDir();
            this.oldTestSuitesPage = new OldTestSuitesPage(new File(oldTestSuiteDir));

            this.addPage(this.oldTestSuitesPage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    
    public boolean performFinish()
    {
        try
        {
            CompareTestCasesProgress progressBar = new CompareTestCasesProgress(
            		Properties.getProperty("comparing_test_cases"), oldTestSuitesPage.getSelectedTestSuiteFile(),
                    newTestSuite);
            ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.parentShell);
            
            dialog.run(true, true, progressBar);

            IEditorInput input = new ConsistencyManagementEditorInput(progressBar.getComparison(),oldTestSuitesPage.getSelectedTestSuiteFile());
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage();
            page.openEditor(input, ConsistencyManagementMultiPageEditor.ID);
        }
        catch(InvocationTargetException ie)
        {
            if ((ie.getTargetException() instanceof OutOfMemoryError)) 
        	{
            	MessageDialog.openError(this.parentShell, Properties.getProperty("test_comparison"),
                        Properties.getProperty("too_large_suite_error"));
			}
            else if(!(ie.getTargetException() instanceof InterruptedException))
            {
                MessageDialog.openError(this.parentShell, Properties.getProperty("error_while_comparing_test_cases"),
                        Properties.getProperty("error_while_comparing_test_cases"));
                ie.printStackTrace();
            }
            
            return true;
        }
        
        catch (Exception e)
        {
            MessageDialog.openError(this.parentShell, Properties.getProperty("error_while_comparing_test_cases"),
            		Properties.getProperty("error_while_comparing_test_cases"));
            e.printStackTrace();
        }

        return true;
    }

}
