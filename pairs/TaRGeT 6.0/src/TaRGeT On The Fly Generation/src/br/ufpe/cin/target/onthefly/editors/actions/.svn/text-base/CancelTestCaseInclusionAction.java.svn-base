/*
 * @(#)CancelTestCaseInclusionAction.java
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
 * wdt022    Apr 11, 2008   LIBpp56482   Initial creation.
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
 * This action removes the selected test cases from the inclusion list.
 * 
 * </pre>
 */
public class CancelTestCaseInclusionAction extends AbstractTCGAction
{
    /**
     * The form page that exhibits the list of test cases always excluded or included in the final
     * test suite.
     */
    private OnTheFlyTestExclusionPage inclusionPage;

    /**
     * Creates an instance of this action.
     * 
     * @param id The action id.
     * @param selectionProvider The selection provider for this action.
     * @param exclusionPage The form page that exhibits the list of test cases always excluded or
     * included in the final test suite.
     */
    public CancelTestCaseInclusionAction(String id, ISelectionProvider selectionProvider,
            OnTheFlyTestExclusionPage inclusionPage)
    {
        super(id, Properties.getProperty("cancel_test_case_inclusion"),
                Properties.getProperty("removes_the_selected_test_cases_from_the_inclusion_list"), selectionProvider);
        this.inclusionPage = inclusionPage;
    }

    /**
     * @see AbstractTCGAction#run(ISelection) Gets the selected test case and removes it from the
     * inclusion list.
     */
    public void run(ISelection selection)
    {
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        if (selection != null && !selection.isEmpty())
        {
            for (Object obj : structuredSelection.toList())
            {
                TextualTestCase testCase = (TextualTestCase) obj;

                this.inclusionPage.cancelTestCaseInclusion(testCase);
            }
        }
        this.inclusionPage.getEditor().setFocus();
        this.selectionProvider.setSelection(StructuredSelection.EMPTY);
    }

    /**
     * @ see {@link ISelectionChangedListener}{@link #selectionChanged(SelectionChangedEvent)}
     * Enables this action according to selection event: this action is enabled if the selection
     * object is a test case and it is in inclusion list.
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();

        if (selection != null && !selection.isEmpty())
        {
            boolean atLeastOneIncluded = false;
            for (Object obj : selection.toList())
            {
                if (!(obj instanceof TextualTestCase))
                {
                    this.setEnabled(false);
                    break;
                }
                else if (this.inclusionPage.isTestCaseIncluded((TextualTestCase) obj))
                {
                    atLeastOneIncluded = true;
                }
            }
            this.setEnabled(atLeastOneIncluded);
        }
        else
        {
            this.setEnabled(false);
        }
    }

}
