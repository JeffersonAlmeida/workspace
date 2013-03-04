/*
 * @(#)ReloadBesesProgressBar.java
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
 * lmn3      04/09/2009                  Initial creation.
 */
package br.ufpe.cin.target.cnl.progressbars;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.views.CNLView;
import br.ufpe.cin.target.pm.progressbars.TargetProgressBar;

public class ReloadBasesProgressBar  extends TargetProgressBar
{
    private CNLView cnlView;
    
    public ReloadBasesProgressBar(CNLView view){
        super("Reloading CNL. Please wait...");
        this.subtasks = new String[] { "Processing sentences...", "Updating error messages..."};
        this.cnlView = view;
    }


    
    protected void finishOnError()
    {
        // TODO Auto-generated method stub
        
    }

    
    public void hook(IProgressMonitor monitor) throws Exception
    {
        try
        {
            CNLPluginController.getInstance().startController();
            worked = this.addSubtask(monitor, worked);
            Display display = cnlView.getSite().getShell().getDisplay();
            display.syncExec(new Runnable()
            {
                
                
                public void run()
                {
                    cnlView.update(true);
                    
                }
            });
            
            worked = this.addSubtask(monitor, worked);
            Thread.sleep(1000);   
        }
        catch (final CNLException e)
        {
            Display display = cnlView.getSite().getShell().getDisplay();
            display.syncExec(new Runnable()
            {

                
                public void run()
                {
                    //show error message when reloading Cnl files
                    MessageDialog.openError(cnlView.getSite().getShell(),
                            "Unable to initialize the CNL Plugin", e.getMessage());
                }
            });
        }
    }

}
