/*
 * @(#)OnTheFlyMultiPageEditor.java
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
 * wdt022   Mar 14, 2008    LIBpp56482   Initial creation.
 * tnd783   Aug 25, 2008	LIBqq51204	 Changes made on pages' header for on the fly.
 * pvcv     Apr 01, 2009				 Internationalization support.
 * lmn3		May 14, 2009				 Changes due code inspection.
 * fsf2     Jun 29, 2009                 Added refresh method.
 * lmn3		Jul 14, 2009				 Modifications to close CM editor when closing OnTheFly editor.
 */
package br.ufpe.cin.target.onthefly.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.onthefly.actions.OnTheFlyTCGActionDelegate;
import br.ufpe.cin.target.pm.editors.RefreshableEditor;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.tcg.TCGActivator;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * <pre>
 * CLASS:
 * This class is responsible for On The Fly form pages creation.  
 * 
 * RESPONSIBILITIES:
 * Creates form pages, provides whole test suites for form pages.
 * 
 * </pre>
 */
public class OnTheFlyMultiPageEditor extends FormEditor implements RefreshableEditor
{
    /**
     * The unique identifier of this form editor.
     */
    public static final String ID = "br.ufpe.cin.target.onthefly.editors.OnTheFlyMultiPageEditor";
    
    /**
     * The list of headers. This list is necessary to maintain the consistency between the combos of
     * filter selections.
     */

    private List<OnTheFlyHeader> headers = new ArrayList<OnTheFlyHeader>();
    
    private static int activePage = 0;
    
    public OnTheFlyMultiPageEditor() {
		// TODO Auto-generated constructor stub
	}

    /**
     * Creates and adds pages to this editor.
     */
    protected void createPages()
    {
        try
        {
            OnTheFlyTestSelectionPage testSelection = new OnTheFlyTestSelectionPage(this);

            OnTheFlyTestExclusionPage testInclusionExclusionPage = new OnTheFlyTestExclusionPage(
                    this);

            OnTheFlyGeneratedTestCasesPage generatedTestCasesPage = new OnTheFlyGeneratedTestCasesPage(
                    this, testSelection, testInclusionExclusionPage);

            OnTheFlyTraceabilityMatrixPage traceabilityMatrix = new OnTheFlyTraceabilityMatrixPage(
                    this, testSelection, testInclusionExclusionPage);

            addPage(testSelection);
            setPageText(this.getPageCount() - 1, Properties.getProperty("selection"));

            addPage(generatedTestCasesPage);
            setPageText(this.getPageCount() - 1, Properties.getProperty("test_cases"));

            addPage(traceabilityMatrix);
            setPageText(this.getPageCount() - 1, Properties.getProperty("traceability_matrix"));

            addPage(testInclusionExclusionPage);
            setPageText(this.getPageCount() - 1, Properties.getProperty("inclusion_exclusion"));
            
            this.setActivePage(0);
            this.setActivePage(activePage);
            
        }
        catch (PartInitException e)
        {
            TCGActivator.logError(0, this.getClass(), Properties
                    .getProperty("error_creating_pages"), e);
            e.printStackTrace();
        }
    }
    
    /**
     * @see FormEditor#addPages()
     */
    
    protected void addPages()
    {
    }

    /**
     * @see FormEditor#doSave(IProgressMonitor)
     */
    
    public void doSave(IProgressMonitor monitor)
    {
    }

    /**
     * @see FormEditor#doSaveAs()
     */
    
    public void doSaveAs()
    {
    }

    /**
     * @see FormEditor#isSaveAsAllowed()
     */
    
    public boolean isSaveAsAllowed()
    {
        return false;
    }

    /**
     * @see FormEditor#setFocus()
     */
    
    public void setFocus()
    {
        super.setFocus();
        if (this.getActivePage() >= 0)
        {
            ((FormPage) this.pages.get(this.getActivePage())).setFocus();
        }
    }

    /**
     * Gets the whole test suite with test cases in raw format.
     * 
     * @return A test suite with raw test cases.
     */
    public TestSuite<TestCase<FlowStep>> getRawTestSuite()
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getRawTestSuite();
    }

    /**
     * Gets the raw test case associated with the specified id.
     * 
     * @param id The test case id.
     * @return The test case to which the specified id is associated, or null if there is no test
     * case associated with the id.
     */
    public TestCase<FlowStep> getRawTestCase(int id)
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getRawTestCase(id);
    }

    /**
     * Gets the whole test suite with test cases in textual format.
     * 
     * @return A test suite with textual test cases.
     */
    public TestSuite<TextualTestCase> getTextualTestSuite()
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getTextualTestSuite();
    }

    /**
     * Gets the textual test case associated with the specified id.
     * 
     * @param id The test case id.
     * @return The test case to which the specified id is associated, or null if there is no test
     * case associated with the id.
     */
    public TextualTestCase getTextualTestCase(int id)
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getTextualTestCase(id);
    }

    /**
     * Gets the list of headers of the current editor.
     * 
     * @return The list of headers.
     */

    public List<OnTheFlyHeader> getHeaders()
    {
        return headers;
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.target.pm.editors.RefreshableEditor#refresh()
     */
    
    public void refresh(boolean refreshAnyway)
    {
        activePage = this.getActivePage();
        
        OnTheFlyTCGActionDelegate onTheFlyTCGActionDelegate = new OnTheFlyTCGActionDelegate();

        if(this.validateRefresh(onTheFlyTCGActionDelegate) || refreshAnyway)
        {
            onTheFlyTCGActionDelegate.generateTestCases();
        }
    }
    
    /**
     * 
     * Performs the basic verifications before generating test cases.
     * @param tcgActionDelegate
     * @return
     */
    private boolean validateRefresh(OnTheFlyTCGActionDelegate tcgActionDelegate)
    {
        boolean validate = true;
        
        if (!ProjectManagerExternalFacade.getInstance().hasOpenedProject())
        {
            validate = false;
        }
        else if (!tcgActionDelegate.hasImportedDocumentsInProject() || !tcgActionDelegate.hasImportedFeatures())
        {
            tcgActionDelegate.selectionChanged();
            
            validate = false;
            
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
        }
        else if (tcgActionDelegate.hasErrorsInProject())
        {
            
        	validate = false;
            
            tcgActionDelegate.selectionChanged();
            
            this.close(false);
            
        	MessageDialog.openInformation(this.getContainer().getShell(), Properties.getProperty("test_case_generation"),
                    Properties.getProperty("there_are_errors_in_the_project_impossible_generate_tests"));
        }
        
        return validate;
    }

}
