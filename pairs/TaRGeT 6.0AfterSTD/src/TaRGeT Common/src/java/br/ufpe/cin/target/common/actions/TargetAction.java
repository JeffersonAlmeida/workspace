/*
 * @(#)TargetAction.java
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
 * dhq348   Jan 6, 2007     LIBkk11577   Initial creation.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * wln013   Apr 29, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * tnd783   Aug 05, 2008    LIBqq51204  
 */
package br.ufpe.cin.target.common.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;

/**
 * This is the super class for all action classes in the project.
 * 
 * <pre>
 * CLASS:
 * This is class must be extended by all triggered actions.
 * 
 * RESPONSIBILITIES:
 * 1) Provides default implementation for all action classes in the project.
 * 
 * COLABORATORS:
 * 1) Uses IWorkbenchWindow
 *
 * USAGE:
 * This class is extended (since it is an abstract class) by all action classes in the project.
 */
public abstract class TargetAction extends ActionDelegate implements IWorkbenchWindowActionDelegate
{

    /**
     * The workbench window.
     */
    protected IWorkbenchWindow window;
    
    /**
     * The action.
     */
    protected IAction action;

    
    /**
     * The window object is cached in order to provide any need parent shell. dialog.
     * 
     * @param window The workbench window
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init(IWorkbenchWindow window)
    {
        this.window = window;
    }

    /**
     * The action has been activated. The argument of the method represents the 'real' action
     * sitting in the workbench UI. This method is a template method.
     * 
     * @param action An action.
     * @see IWorkbenchWindowActionDelegate#run
     */
  
    public final void run(IAction action)
    {
        this.action = action;
        this.hook();
    }

    /**
     * A hook method that must be implemented by the subclasses in order to contribute the run
     * method.
     */
    protected abstract void hook();
    
   

}
