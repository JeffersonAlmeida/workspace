/*
 * @(#)RefreshAutomaticallyAction.java
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
 *  tnd783   Aug 05, 2008    LIBqq51204   Initial creation.
 *  dwvm83	 Sep 30, 2008	 LIBqq51204	  Rework after inspection LX302177.
 *  dwvm83   Oct 14, 2008				  Added method selectionChanged
 */
package br.ufpe.cin.target.pm.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import br.ufpe.cin.target.common.actions.TargetAction;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;


/**
 * <pre>
 * CLASS:
 * Action used to switch the refresh automatically preference.
 * 
 * RESPONSIBILITIES:
 * Switches the refresh automatically preference and refreshes the project.
 * 
 * USAGE:
 * One Action can be associated with events in GUI.
 * </pre>
 */

public class RefreshAutomaticallyAction extends TargetAction
{
    
    
    /**
     * Switches the state of the refresh automatically preference and
     * refreshes the project by reloading the project's progress bar 
     * and refreshing all the views and editors. 
     * 
     */
    protected void hook()
    {
    	if (ProjectManagerController.getInstance().hasOpenedProject()) {
	        GUIManager.getInstance().switchRefreshAutomatically();
	        //If the refresh automatically state changes from true to
	        //false, the project should not be refreshed
	        if (GUIManager.getInstance().isRefreshAutomatically())
	        	GUIManager.getInstance().refreshProject(super.window.getShell(),false);
    	}
    
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
