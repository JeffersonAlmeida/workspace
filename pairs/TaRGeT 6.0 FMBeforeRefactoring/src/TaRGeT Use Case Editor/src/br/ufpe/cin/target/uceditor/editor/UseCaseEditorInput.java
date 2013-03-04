/*
 * @(#)UseCaseEditorInput.java
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
package br.ufpe.cin.target.uceditor.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;

/**
 * Represents an editor input for the Use Case Editor.
 *         
 */
public class UseCaseEditorInput implements IEditorInput
{
    private UseCase useCase;
    
    private Feature feature;
    
    public UseCaseEditorInput(UseCase useCase, Feature feature)
    {
        this.useCase = useCase;
        this.feature = feature;
    }

    /**
     * Gets the useCase value.
     *
     * @return Returns the useCase.
     */
    public UseCase getUseCase()
    {
        return useCase;
    }

    /**
     * Sets the useCase value.
     *
     * @param useCase The useCase to set.
     */
    public void setUseCase(UseCase useCase)
    {
        this.useCase = useCase;
    }

    /**
     * Gets the feature value.
     *
     * @return Returns the feature.
     */
    public Feature getFeature()
    {
        return feature;
    }

    /**
     * Sets the feature value.
     *
     * @param feature The feature to set.
     */
    public void setFeature(Feature feature)
    {
        this.feature = feature;
    }

    
	public boolean exists() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	
	public ImageDescriptor getImageDescriptor() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getName() 
	{
		// TODO Auto-generated method stub
		return "";
	}

	
	public IPersistableElement getPersistable() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getToolTipText() 
	{
		// TODO Auto-generated method stub
		return "";
	}

    
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class adapter)
    {
        // TODO Auto-generated method stub
        return null;
    }

	

}
