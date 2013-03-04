/*
 * @(#)ReloadProjectShellListener.java
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
 *  dhq348   Jan 25, 2007   LIBll12753   Initial creation.
 *  dhq348   Feb 08, 2007   LIBll12753   Modification after inspection LX142521.
 *  dhq348   Feb 12, 2007   LIBll27713   CR LIBll27713 improvements.
 *  dhq348   Feb 16, 2007   LIBll27713   Rework of inspection LX144782.
 *  wdt022   Mar 25, 2008   LIBpp56482   Updated due to GUIManager.refreshView method update.
 *  tnd783   Apr 07, 2008   LIBpp71785   Added GUIManager.refreshEditors() method call.
 *  dhq348   Jul 11, 2007   LIBmm47221   Rework after inspection LX185000.
 *  tnd783   Jul 21, 2008   LIBpp71785   Rework after inspection LX285039. 
 *  tnd783   Aug 04, 2008 	LIBqq51204   Changes made to include the refresh automatically functionality. 
 */
package br.ufpe.cin.target.pm;

import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Shell;

import br.ufpe.cin.target.pm.controller.ProjectManagerController;

/**
 * Represents a shell listener related to the main shell of the application.
 * 
 * <pre>
 * CLASS:
 * This class implements the behavior of the listener associated to the main 
 * shell of the environment. Basically, it reloads the current project when the 
 * focus goes back to the main window. This class is instantiated in ArtifactsView.
 * 
 * USAGE:
 * ArtifactsViewShellListener listener = new ArtifactsViewShellListener(shell);
 */
public class ReloadProjectShellListener implements ShellListener
{

    /**
     * The shell to which this listener will be added to.
     */
    private Shell shell;

    /**
     * The constructor that assigns the shell.
     * 
     * @param shell The shell to which this listener will be added to.
     */
    public ReloadProjectShellListener(Shell shell)
    {
        this.shell = shell;
    }

    /**
     * Implements the behavior when the shell is activated (e.g. When the focus goes back to the
     * shell).
     * 
     * @param e The shell event that notifies that the shell has been activated.
     */
    public void shellActivated(ShellEvent e)
    {
        if (ProjectManagerController.getInstance().hasOpenedProject() && GUIManager.getInstance().isRefreshAutomatically())
        {
            GUIManager.getInstance().refreshProject(shell,false);
        }
    }

    /**
     * Implements the behavior when the shell is closed. Empty behavior.
     * 
     * @param e The shell event that notifies that the shell has been closed.
     */
    public void shellClosed(ShellEvent e)
    {
        // empty
    }

    /**
     * Implements the behavior when the shell is deactivated. Empty behavior.
     * 
     * @param e The shell event that notifies that the shell has been deactivated.
     */
    public void shellDeactivated(ShellEvent e)
    {
        // empty
    }

    /**
     * Implements the behavior when the shell is deiconified. Empty behavior.
     * 
     * @param e The shell event that notifies that the shell has been deiconified.
     */
    public void shellDeiconified(ShellEvent e)
    {
        // empty
    }

    /**
     * Implements the behavior when the shell is iconified. Empty behavior.
     * 
     * @param e The shell event that notifies that the shell has been iconified.
     */
    public void shellIconified(ShellEvent e)
    {
        // empty
    }

}
