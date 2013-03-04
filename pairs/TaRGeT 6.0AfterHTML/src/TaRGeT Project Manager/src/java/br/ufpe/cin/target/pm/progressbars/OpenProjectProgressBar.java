/*
 * @(#)OpenProjectProgressBar.java
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
 * dhq348   Dec 11, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 * dhq348   May 18, 2007    LIBmm25975   Modification according to CR.
 */
package br.ufpe.cin.target.pm.progressbars;

import org.eclipse.core.runtime.IProgressMonitor;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.controller.TargetProjectRefreshInformation;


/**
 * The progress bar that is displayed when a project is being opened.
 * 
 * <pre>
 * CLASS:
 * The progress bar representing the steps during the opening process of a project.
 * 
 * RESPONSIBILITIES:
 * 1) Displays a progress bar

 * USAGE:
 * OpenProjectProgressBar bar = new OpenProjectProgressBar();
 */
public class OpenProjectProgressBar extends TargetProgressBar
{
    /**
     * Receives the name of the task and sets the names of the subtasks.
     * 
     * @param taskname The task name to be displayed.
     */
    public OpenProjectProgressBar()
    {
        super(Properties.getProperty("opening_target_project_wait"));
        this.subtasks = new String[] { Properties.getProperty("loading_features_update_views"), Properties.getProperty("updating_index") };
    }

    
    /**
     * Implements the specific behavior of this progress bar. Adds a subtask and loads the project.
     * 
     * @param monitor The monitor of the progress bar.
     * @throws TargetException In case of any error during the project loading.
     */
    public void hook(IProgressMonitor monitor) throws TargetException
    {
        /* Loads the existing features and sets them to the the current open project */
        worked = this.addSubtask(monitor, worked);

        /* loads the features */
        TargetProjectRefreshInformation refreshInfo = ProjectManagerController.getInstance()
                .loadOpenedProject();

        worked = this.addSubtask(monitor, worked);
        ProjectManagerController.getInstance().updateIndex(refreshInfo, true);
    }

    
    /**
     * Nothing to do on error.
     */
    protected void finishOnError()
    {
    }
}
