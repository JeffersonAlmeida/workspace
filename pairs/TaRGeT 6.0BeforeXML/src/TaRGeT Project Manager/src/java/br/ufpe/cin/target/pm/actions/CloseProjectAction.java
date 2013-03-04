/*
 * @(#)CloseProjectAction.java
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
 * wra050   Jul 21, 2006    LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerController inclusion.
 * dhq348   Dec 20, 2006    LIBkk11577   Inspection (LX124184) faults fixing.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * wln013   Apr 29, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * dwvm83   Oct 14, 2008				 Added method selectionChanged
 */
package br.ufpe.cin.target.pm.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import br.ufpe.cin.target.common.actions.TargetAction;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.util.GUIUtil;


/**
 * Action used to close a TargetProject.
 * 
 * <pre>
 * CLASS:
 * Action used to close a TargetProject. 
 * 
 * RESPONSIBILITIES:
 * When the related events occur (e.g., close menu option is clicked), 
 * the opened project is closed.
 * 
 * USAGE:
 * One Action can be associated with events in GUI.
 * </pre>
 */
public class CloseProjectAction extends TargetAction
{
    /**
     * The ID of the action that is declared in plug-in.xml
     */
    public static final String ID = "br.ufpe.cin.target.pm.actions.CloseProjectAction";

    
    protected void hook()
    {
        if (ProjectManagerController.getInstance().hasOpenedProject())
        {
            if (GUIUtil.openYesOrNoInformation(this.window.getShell(), "Close project",
                    "The current project will be closed. Do you want to proceed?"))
            {
                //INSPECT - Lais inclusão de try catch para pegar excecao ao fechar view de cnl
            	try{
                	GUIUtil.closeProject(this.window.getShell());
                }
                catch(Exception e){
                	e.printStackTrace();
                }
            }
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
