/*
 * @(#)AddRequirementToFilterAction.java
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
 * lmn3      May, 14, 2009				 Changes due code inspection 
 */
package br.ufpe.cin.target.onthefly.editors.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyTestSelectionPage;
import br.ufpe.cin.target.tcg.actions.AbstractTCGAction;


/**
 * <pre>
 * CLASS:
 * This action adds an specific requirement to the requirements filter.  
 * 
 * </pre>
 */
public class AddRequirementToFilterAction extends AbstractTCGAction
{
    /**
     * The form page that provides filter configuration.
     */
    private OnTheFlyTestSelectionPage selectionPage;

    /**
     * Creates an instance of this action.
     * 
     * @param id The action id.
     * @param selectionProvider The selection provider for this action.
     * @param selectionPage The form page that provides filter configuration.
     */
    public AddRequirementToFilterAction(String id, ISelectionProvider selectionProvider,
            OnTheFlyTestSelectionPage selectionPage)
    {
        super(id, Properties.getProperty("add_to_requirement_filter"), Properties.getProperty("add_the_selected_requirement_in_the_requirements_filter_list"), selectionProvider);
        this.selectionPage = selectionPage;

    }

    /**
     * @see AbstractTCGAction#run(ISelection) Gets the selected requirement and adds it in the
     * requirements filter list.
     */
    public void run(ISelection selection)
    {
        IStructuredSelection sel = (IStructuredSelection) selection;
        if (sel != null && !sel.isEmpty())
        {
            Object selectedObj = sel.getFirstElement();
            if (selectedObj instanceof String)
            {
                this.selectionPage.selectRequirement((String) selectedObj);
                this.selectionPage.getEditor().setFocus();
            }
        }
    }

    /**
     * @ see {@link ISelectionChangedListener}{@link #selectionChanged(SelectionChangedEvent)}
     * Enables this action according to selection event: this action is enable if the selection
     * object is a requirement and it is not in requirements filter list.
     */
    public void selectionChanged(SelectionChangedEvent event)
    {

        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        this.setEnabled(false);
        if (selection != null && !selection.isEmpty())
        {
            Object selectedObj = selection.getFirstElement();
            if (selectedObj instanceof String
                    && !(this.selectionPage.isRequirementSelected((String) selectedObj)))
            {
                this.setEnabled(true);
            }
        }
    }

}
