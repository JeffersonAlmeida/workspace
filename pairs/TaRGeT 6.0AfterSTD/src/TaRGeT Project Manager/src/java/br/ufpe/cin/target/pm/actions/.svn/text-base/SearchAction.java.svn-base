/*
 * @(#)SearchAction.java
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
 * dhq348   May 15, 2007    LIBmm25975   Initial creation.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * dhq348   Aug 20, 2007    LIBmm93130   Modifications according to CR.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * wln013   Apr 30, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * dwvm83   Oct 14, 2008				 Added method selectionChanged
 */
package br.ufpe.cin.target.pm.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;

import br.ufpe.cin.target.common.actions.TargetAction;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.search.SearchController;
import br.ufpe.cin.target.pm.search.gui.SearchDialog;


/**
 * This is the action that displays the Search Dialog.
 * 
 * <pre>
 * CLASS:
 *
 * This action can be triggered by the application menu or using the shortcut CTL+F.
 * The search dialog is only displayed if there is a project opened, otherwise it 
 * displays an error message.
 *
 * </pre>
 */
public class SearchAction extends TargetAction
{

    /**
     * Displays the search dialog only if there is an opened project. Otherwise, it displays an
     * error message.
     */
    
    protected void hook()
    {
        if (!ProjectManagerController.getInstance().hasOpenedProject())
        {
            MessageDialog.openInformation(this.window.getShell(), Properties
					.getProperty("error_while_searching"),
					Properties.getProperty("create_open_project_to_search"));
        }
        else if (!this.hasImportedDocumentsInProject()) 
        {
            MessageDialog.openInformation(this.window.getShell(), Properties.getProperty("search_for_ucs"),
            		Properties.getProperty("no_imported_documents"));
        }
        else
        {
            SearchDialog dialog = new SearchDialog(this.window.getShell(), Properties.getProperty("search_for_ucs"),
                    SearchController.getInstance().getLastQueries());
            dialog.open();
        }
    }

    /**
     * Checks if the number of imported documents is greater than 0;
     * 
     * @return <code>true</code> if the number is greater than one, or <code>false</code>
     * otherwise.
     */
    private boolean hasImportedDocumentsInProject()
    {
        return ProjectManagerController.getInstance().getCurrentProject().getUseCaseDocuments()
                .size() > 0;
    }
    
    
    /**
     * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged()
     */
    public void selectionChanged(IAction action, ISelection selection) {
    	super.selectionChanged(action, selection);
    	//If the project is not closed
    	if (ProjectManagerController.getInstance().getCurrentProject() != null)
    		action.setEnabled(true);
    }
}
