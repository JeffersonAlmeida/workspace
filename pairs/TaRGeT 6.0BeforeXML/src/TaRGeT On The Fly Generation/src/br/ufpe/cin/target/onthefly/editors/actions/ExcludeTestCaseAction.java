/*
 * @(#)ExcludeTestCaseAction.java
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
 * wdt022    Apr 10, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 * pvcv      Apr 01, 2009				 Internationalization support
 */
package br.ufpe.cin.target.onthefly.editors.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyTestExclusionPage;
import br.ufpe.cin.target.tcg.actions.AbstractTCGAction;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * <pre>
 * CLASS:
 * This action adds the selected test cases in exclusion list.
 * 
 * </pre>
 */
public class ExcludeTestCaseAction extends AbstractTCGAction
{
    /**
     * The form page that exhibits the list of test cases always excluded or included in the final
     * test suite.
     */
    private OnTheFlyTestExclusionPage exclusionPage;

    /**
     * Creates an instance of this action.
     * 
     * @param id The action id.
     * @param selectionProvider The selection provider for this action.
     * @param exclusionPage The form page that exhibits the list of test cases always excluded or
     * included in the final test suite.
     */
    public ExcludeTestCaseAction(String id, ISelectionProvider selectionProvider,
            OnTheFlyTestExclusionPage exclusionPage)
    {
        super(id, Properties.getProperty("exclude_test_case_permanently"), 
        		Properties.getProperty("remove_the_selected_test_cases_from_the_final_test_suite"),
                selectionProvider);
        this.exclusionPage = exclusionPage;
    }

    /**
     * @see AbstractTCGAction#run(ISelection) Gets the selected test case and adds it in exclusion
     * list.
     */
    public void run(ISelection selection)
    {
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        if (selection != null && !selection.isEmpty())
        {
            for (Object obj : structuredSelection.toList())
            {
                TextualTestCase testCase = (TextualTestCase) obj;

                if (!this.exclusionPage.isTestCaseExcluded(testCase))
                {
                    this.exclusionPage.addTestCaseToExclusionList(testCase);
                }
            }
        }
        this.exclusionPage.getEditor().setFocus();
        this.selectionProvider.setSelection(StructuredSelection.EMPTY);
    }

    /**
     * @ see {@link ISelectionChangedListener}{@link #selectionChanged(SelectionChangedEvent)}
     * Enables this action according to selection event: this action is enabled if the selection
     * object is a test case and it is not in exclusion or inclusion list.
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();

        if (selection != null && !selection.isEmpty())
        {
            boolean allExcluded = true;
            for (Object obj : selection.toList())
            {
                if (!(obj instanceof TextualTestCase))
                {
                    this.setEnabled(false);
                    break;
                }
                else if (!this.exclusionPage.isTestCaseExcluded((TextualTestCase) obj))
                {
                    allExcluded = false;
                }
            }
            this.setEnabled(!allExcluded);
        }
        else
        {
            this.setEnabled(false);
        }
    }
}
