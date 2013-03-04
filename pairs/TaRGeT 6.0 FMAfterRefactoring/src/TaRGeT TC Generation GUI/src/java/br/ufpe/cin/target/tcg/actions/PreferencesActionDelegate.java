package br.ufpe.cin.target.tcg.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.tcg.preferences.PreferencesDialog;

import br.ufpe.cin.target.pm.controller.ProjectManagerController;

/*
 * @(#)PreferencesAction.java
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
 * tnd783   Sep 11, 2008    LIBqq51204	 Initial creation.
 * dwvm83   Oct 14, 2008	LIBqq51204	 Added method selectionChanged	
 */


public class PreferencesActionDelegate extends AbstractTCGActionDelegate 
{

    
    /**
     * Creates and opens the preferences dialog.
     * 
     */
    protected void hookGeneration()
    {

        PreferencesDialog dialog = new PreferencesDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell());
        dialog.open();
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
