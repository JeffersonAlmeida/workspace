/*
 * @(#)TargetProgressBar.java
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
 * dhq348    Dec 14, 2006   LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007   LIBkk11577   Rework of inspection LX133710.
 */
package br.ufpe.cin.target.pm.progressbars;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

/**
 * This is an abstract progress bar. Use for all progress bars of the PM plug-in.
 * 
 * <pre>
 * CLASS:
 * Represents the basic behavior of a progress bar of the PM plug-in. It also 
 * has some hook methods for all subclasses implement their specific behavior.
 * It has the name of the task, the name of the subtasks and a counter for 
 * counting the progress of the bar.
 */
public abstract class TargetProgressBar implements IRunnableWithProgress
{
    /**
     * The name of the task associated with this progress bar.
     */
    protected String taskname;

    /**
     * The array with the name of the subtasks.
     */
    protected String[] subtasks;

    /**
     * Indicates if an exception has occurred during the execution.
     */
    protected Exception exception;

    /**
     * A counter that indicates the current step of the progress.
     */
    protected int worked;

    /**
     * Constructor that receives the name of the task.
     * 
     * @param taskname The name of the task.
     */
    public TargetProgressBar(String taskname)
    {
        this.taskname = taskname;
        this.worked = 0;
        this.exception = null;
    }

    /**
     * Adds a subtask to the current progress bar.
     * 
     * @param monitor The monitor which keeps track of the progress.
     * @param worked The value of the current step of the progress.
     * @return The number of the next step.
     */
    protected int addSubtask(IProgressMonitor monitor, int worked)
    {
        monitor.subTask(this.subtasks[worked]);
        monitor.worked(++worked);
        return worked;
    }

    /**
     * Starts the task and delegate the operation to the hook method that is implemented by
     * subclasses. It also checks if there are opened views and update them.
     * 
     * @param monitor The monitor of the progress bar
     */
    public void run(IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException
    {
        try
        {
            /* creates the main task */
            monitor.beginTask(this.taskname, this.subtasks.length);

            /* call the hook method implemented by the subclasses */
            hook(monitor);
        }
        catch (Exception e)
        {
            this.exception = e;
            e.printStackTrace();
        }
        finally
        {
            if (this.exception != null)
            {
                /* performs all the operations necessary to finalize the processing */
                this.finishOnError();
            }
        }
    }

    /**
     * The get method for <code>exception</code>.
     * 
     * @return Returns the exception.
     */
    public Exception getException()
    {
        return exception;
    }

    /**
     * The subclasses implement this method to perform specific operations.
     * 
     * @param monitor The monitor that will accessed by all subclasses.
     * @throws Exception Any exception that can be thrown.
     */
    public abstract void hook(IProgressMonitor monitor) throws Exception;

    /**
     * Performs all the operations (such as deleting some files) necessary to finalize the
     * processing.
     */
    protected abstract void finishOnError();
}
