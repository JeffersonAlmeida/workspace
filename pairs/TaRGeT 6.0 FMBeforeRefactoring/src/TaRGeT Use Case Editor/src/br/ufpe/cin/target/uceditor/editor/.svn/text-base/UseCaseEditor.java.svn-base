/*
 * @(#)UseCaseEditor.java
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
 * mcms     06/11/2009                    Add method to update editor title
 */
package br.ufpe.cin.target.uceditor.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.editors.RefreshableEditor;
import br.ufpe.cin.target.uceditor.UseCaseEditorActivator;

/**
 * This class represents an Use Case editor.  
 * 
 */
public class UseCaseEditor extends FormEditor implements RefreshableEditor
{
    /**
     * Use Case Editor Id
     */
	public static final String ID = "br.ufpe.cin.target.uceditor.editor.UseCaseEditor";
	protected boolean dirty;
	
	public UseCaseEditor() {
		// TODO Auto-generated constructor stub
	}
	
	
    protected void createPages()
    {
        UseCaseEditorPage editor = null;
        dirty = false;
        UseCaseEditorInput input = (UseCaseEditorInput) this.getEditorInput();

        try
        {
        	editor = new UseCaseEditorPage(this, input.getUseCase(), input.getFeature());
            if (input.getFeature() != null)
            {
                if (input.getUseCase() != null)
                {
                    this.setPartName(input.getUseCase().getName());
                   
                }
            }
        
            int index = addPage(editor);           
            this.setPageText(index, Properties.getProperty("use_cases"));
        }
        catch (PartInitException e)
        {
            UseCaseEditorActivator.logError(0, this.getClass(), Properties.getProperty("error_pages"), e);
            e.printStackTrace();
        }

    }

	
	protected void addPages() 
	{
		// TODO Auto-generated method stub
		
	}

	
	public void doSave(IProgressMonitor monitor) 
	{
		setDirty(false);
		UseCaseEditorPage page = (UseCaseEditorPage)this.pages.get(this.getActivePage());
		page.save();
		// TODO Auto-generated method stub
		
	}
	
	public void doSave(){
		setDirty(false);
		UseCaseEditorPage page = (UseCaseEditorPage)this.pages.get(this.getActivePage());
		page.save();
	}

	
	public void doSaveAs() 
	{
		doSave(null);
		// TODO Auto-generated method stub
		
	}

	
	public boolean isSaveAsAllowed() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	//INSPECT mcms add method to update editor title
	public void updateTitle(String name) {
		this.setPartName(name);
		
	}
	
	public boolean isDirty(){
		return dirty;
	}
	
	public void setDirty(boolean value){
		if(value!=dirty){
			dirty = value;
			firePropertyChange(PROP_DIRTY);			
		}
	}
	
    /**
     * Refreshes the editor, updating use case, features and errors information.
     */
    public void refresh(boolean refreshAnyway)
    {
        IEditorInput input = getEditorInput();

        if (input instanceof UseCaseEditorInput)
        {
        	UseCaseEditorInput pathInput = (UseCaseEditorInput) input;

            UseCase useCase = pathInput.getUseCase();
            Feature feature = pathInput.getFeature();

            if(useCase != null && feature != null)
            {
            	UseCase newUseCase = null;
                Feature newFeature = null;
                
                for (Feature f : ProjectManagerController.getInstance().getAllFeatures())
                {
                    if (feature.equals(f))
                    {
                        newUseCase = f.getUseCase(useCase.getId());
                        newFeature = f;
                        break;
                    }
                }
                if (newUseCase != null)
                {
                	pathInput.setUseCase(newUseCase);
                	pathInput.setFeature(newFeature);
                	setPartName(newUseCase.getName());
                }
                else
                {
                    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
                    page.closeEditor(this, false);
                }
            }
        }
    }

}
