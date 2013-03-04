/*
 * @(#)OpenUseCaseEditorActionDelegate.java
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
 * -------  ------------    ----------    ----------------------------
 * mcms     07/09/2009                    Initial Creation
 * lmn3     07/10/2009                    Changes due code inspection.
 */
package br.ufpe.cin.target.uceditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.actions.TargetAction;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.uceditor.UseCaseEditorActivator;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditor;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditorInput;


/**
 * Activates the Use Case Editor. It verifies if there is no errors, and then 
 * invokes the Use Case Editor.
 * 
 * RESPONSIBILITIES:
 * 1) Invokes the Use Case Editor.
 * 
 * USAGE:
 * It is referred by its ID.
 */
public class OpenUseCaseEditorActionDelegate extends TargetAction
{

    /**
     * The ID of the action that is declared in plugin.xml.
     */
    public static final String ID = "br.ufpe.cin.target.xmleditor.actions.EditorXMLDelegate";

    /**
     * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged()
     */
    
    public void selectionChanged(IAction action, ISelection selection)
    {
        super.selectionChanged(action, selection);
        action.setEnabled(true);
    }

    /**
     * It implements abstract method from TargetAction. It opens a XML Editor with empty values.
     */
    
    protected void hook()
    {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        
        try
        {
            UseCaseEditorInput input = new UseCaseEditorInput(null, null);
            page.openEditor(input, UseCaseEditor.ID);
        }
        
        catch (PartInitException e)
        {
            e.printStackTrace();
            
            UseCaseEditorActivator.logError(0, this.getClass(), Properties.getProperty("error_opening ") + UseCaseEditor.ID
                    + ": " + e.getMessage(), e);
            
            MessageDialog.openError(this.window.getShell(), "grafical_error",
                    "grafical_interface_error");
        }
    }
}
