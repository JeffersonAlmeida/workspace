/*
 * @(#)ReloadBasesActionDelegate.java
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
 * wxx###   Apr 15, 2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import br.ufpe.cin.target.cnl.progressbars.ReloadBasesProgressBar;
import br.ufpe.cin.target.cnl.views.CNLView;

public class ReloadBasesActionDelegate implements IViewActionDelegate
{
    private IViewPart parentView;

    
    public void init(IViewPart view)
    {
        this.parentView = view;
    }

    
    public void run(IAction action)
    {
        this.hook();
    }
    
    public void hook(){
        try
        {
            ReloadBasesProgressBar progressBar = new ReloadBasesProgressBar((CNLView) parentView);
            ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.parentView.getSite().getShell());
            dialog.setCancelable(false);
            dialog.run(true, false, progressBar);
        }
        catch (InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    public void selectionChanged(IAction action, ISelection selection)
    {

    }

}
