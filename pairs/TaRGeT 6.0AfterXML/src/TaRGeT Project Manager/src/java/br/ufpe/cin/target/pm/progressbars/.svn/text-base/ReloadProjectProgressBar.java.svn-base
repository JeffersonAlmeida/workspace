/*
 * @(#)ReloadProjectProgressBar.java
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
 * dhq348   Jan 18, 2007    LIBkk11577   Initial creation due to rework of inspection LX133710.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   May 18, 2007    LIBmm25975   Modification according to CR.
 * dhq348   Jun 21, 2007    LIBmm25975   Rework after meeting 3 of inspection LX179934.
 */
package br.ufpe.cin.target.pm.progressbars;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.controller.TargetProjectRefreshInformation;
import br.ufpe.cin.target.pm.errors.Error;
import br.ufpe.cin.target.pm.exceptions.TargetProjectLoadingException;


/**
 * Represents the progress bar that is displayed when the project is being reloaded.
 * 
 * <pre>
 * CLASS:
 * Represents the progress bar with the steps of the project being reloaded.
 */
public class ReloadProjectProgressBar implements IRunnableWithProgress
{

    /**
     * Represents an exception found while reloading the project
     */
    private Exception exception;

    /**
     * Initializes <code>exception</code> to null indicating that any exception has ocurred.
     */
    public ReloadProjectProgressBar()
    {
        exception = null;
    }

    /**
     * Starts the task and reloads the project. It also has a subtask indicating the refresh of the
     * views. This refresh operation must be done outside the progress bar because of thread
     * problems.
     * 
     * @param monitor The monitor of the progress bar.
     * @throws InterruptedException Thrown when an error has occurred during the execution of the
     * progress bar.
     * @throws InvocationTargetException Thrown when an error has occurred during the execution of
     * the progress bar.
     */
    public void run(IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException
    {
        try
        {
            exception = null;

            monitor.beginTask("Loading modified documents.", 2);
            monitor.subTask("Loading files");
            monitor.worked(1);
            TargetProjectRefreshInformation refreshInfo = ProjectManagerController.getInstance()
                    .reloadProject();

            monitor.subTask("Updating index");
            ProjectManagerController.getInstance().updateIndex(refreshInfo, false);

            Collection<br.ufpe.cin.target.pm.errors.Error> errors = refreshInfo
                    .getNewDifferentErrors(Error.FATAL_ERROR);

            if (!errors.isEmpty())
            {
                this.exception = new TargetProjectLoadingException(errors);
            }

            monitor.subTask("Refreshing views");
            monitor.worked(2);
        }
        catch (TargetException e)
        {
            exception = e;
        }
    }

    /**
     * Gets the exception value.
     * 
     * @return Returns the exception.
     */
    public Exception getException()
    {
        return exception;
    }

}
