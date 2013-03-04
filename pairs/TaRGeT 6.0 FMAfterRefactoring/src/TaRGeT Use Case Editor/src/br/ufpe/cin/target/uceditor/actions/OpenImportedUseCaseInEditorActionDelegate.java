/*
 * @(#)OpenImportedUseCaseInEditorActionDelegate.java
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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.uceditor.UseCaseEditorActivator;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditor;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditorInput;

import br.ufpe.cin.target.common.actions.TargetAction;
import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.common.TreeObject;

/**
 * Activates Use Case Editor to open a selected use case. It verifies if there is no errors, and then it 
 * invokes the editor.
 * 
 * RESPONSIBILITIES:
 * 1) Invokes the Use Case Editor.
 * 
 * USAGE:
 * It is referred by its ID.
 */
public class OpenImportedUseCaseInEditorActionDelegate extends TargetAction
{

    public static final String ID = "br.ufpe.cin.target.xmleditor.ucimported.OpenImportedUseCase";

    private UseCase useCase;

    private Feature feature;

    /**
     * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged()
     */
    
    public void selectionChanged(IAction action, ISelection selection)
    {
        super.selectionChanged(action, selection);
        IStructuredSelection select = (IStructuredSelection) selection;

        // Getting TreeObject corresponding to the selected Use Case on user interface
        TreeObject treeChild = (TreeObject) select.getFirstElement();
        
        if (treeChild != null)
        {
            // Getting TreeObject corresponding to Feature that had an use case selected
            TreeObject treeParent = (TreeObject) treeChild.getParent();
            
            if (treeChild.getValue() instanceof UseCase)
            {
                this.useCase = (UseCase) treeChild.getValue();
                this.feature = (Feature) treeParent.getValue();
            }
        }
    }

    /**
     * Returns an array of references of all use case editor opened
     * 
     * @param page The active page
     * @return an array of IEditorReference
     */
    public IEditorReference[] getUseCaseEditorReferences(IWorkbenchPage page)
    {
        IEditorReference[] editors = page.getEditorReferences();
        IEditorReference[] ref = null;
        if (editors.length > 0)
        {
            ref = editors;
        }
        return ref;
    }

    /**
     * Implements the abstract method from TargetAction. Opens a XML Editor with the values of the selected use
     * case.
     */
    
    protected void hook()
    {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        
        // Getting the Use Case Editor reference
        IEditorReference[] editorReference = getUseCaseEditorReferences(page);        

        try
        {
            UseCaseEditorInput editorInput = new UseCaseEditorInput(this.useCase, this.feature);

            boolean hasFound = false;
            if (editorReference != null)
            {
                for (int i = 0; i < editorReference.length; i++)
                {
                	if (editorReference[i].getPartName().equals(useCase.getName()))
                    {	
                        if (editorReference[i].getEditorInput() instanceof UseCaseEditorInput)
                        {
                            hasFound = true;
                        }
                    }
                }
                if (!hasFound)
                    page.openEditor(editorInput, UseCaseEditor.ID);
            }
            else
            {
                page.openEditor(editorInput, UseCaseEditor.ID);
            }
        }
        catch (PartInitException e)
        {
            e.printStackTrace();
            
            UseCaseEditorActivator.logError(0, this.getClass(), Properties.getProperty("error_opening ") + UseCaseEditor.ID
                    + ": " + e.getMessage(), e);
            
            MessageDialog.openError(this.window.getShell(), Properties.getProperty("grafical_error"),
            		Properties.getProperty("grafical_interface_error"));
        }
    }
}
