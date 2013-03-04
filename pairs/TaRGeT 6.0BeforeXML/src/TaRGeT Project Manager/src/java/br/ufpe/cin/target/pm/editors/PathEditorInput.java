/*
 * @(#)PathEditorInput.java
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
 *    -      Nov 29, 2006    LIBkk11577   Initial creation.
 *  dhq348   Sep 04, 2007    LIBnn24462   Added javadoc comments.
 *  tnd783   Apr 07, 2008    LIBpp71785   Added use case and feature attributes.
 *  tnd783   Jul 21, 2008    LIBpp71785   Rework after inspection LX285039.  
 */
package br.ufpe.cin.target.pm.editors;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;

/**
 * This class represents an input to the editors of the tool. It is used by
 * <code>UseCaseEditor</code> and <code>HTMLGenerator</code> to store the path to the use case
 * being visualized.
 */
public class PathEditorInput implements IPathEditorInput
{
    /**
     * An abstract path that represents the basic input of the class.
     */
    private IPath path;

    /**
     * The use case of the editor input.
     */
    private UseCase useCase;

    /**
     * The feature of the editor input.
     */
    private Feature feature;

    /**
     * Constructor that receives the <code>path</code> of the input.
     * 
     * @param path The path of the input.
     */
    public PathEditorInput(IPath path)
    {
        this.path = path;
    }

    /**
     * @return The path of the input.
     */
    public IPath getPath()
    {
        return this.path;
    }

    public void setPath(IPath path)
    {
        this.path = path;
    }

    /**
     * Verifies if the file specified by <code>path</code> exists.
     * 
     * @return <code>true</code> if the specified file exists or <code>false</code> otherwise.
     */
    public boolean exists()
    {
        return this.path.toFile().exists();
    }

    /**
     * @return The image descriptor associated with <code>path</code>.
     */
    public ImageDescriptor getImageDescriptor()
    {
        return PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(
                this.path.toString());
    }

    /**
     * @return The string representing <code>path</code>.
     */
    public String getName()
    {
        return this.path.toString();
    }

    /**
     * @return <code>null</code> since the current editor input cannot be persisted. It is
     * recommended to return <code>null</code>.
     */
    public IPersistableElement getPersistable()
    {
        return null;
    }

    /**
     * @return The tool tip text.
     */
    public String getToolTipText()
    {
        return this.path.makeRelative().toOSString();
    }

    /**
     * @return <code>null</code> since the current editor input does not have an adapter for the
     * given class. It is recommended to return <code>null</code>.
     */
    public Object getAdapter(Class adapter)
    {
        return null;
    }

    /**
     * Compares <code>obj</code> with the current editor input. If they have the same path then
     * the method returns <code>true</code>. If they do not have the same path then the method
     * returns <code>false</code>.
     * 
     * @param obj The object to be compared to the current editor input.
     * @return <code>true</code> if the given <code>obj</code> has the same path of the current
     * editor input or <code>false</code> otherwise.
     */
    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof PathEditorInput)
        {
            PathEditorInput other = (PathEditorInput) obj;
            result = this.path.equals(other.path);
        }
        return result;
    }

    /**
     * Getter method for the feature attribute.
     * 
     * @return The feature.
     */
    public Feature getFeature()
    {
        return feature;
    }

    /**
     * Setter method for the feature attribute.
     * 
     * @param feature The feature to be displayed.
     */
    public void setFeature(Feature feature)
    {
        this.feature = feature;
    }

    /**
     * Getter method for the use case attribute.
     * 
     * @return The use case.
     */
    public UseCase getUseCase()
    {
        return useCase;
    }

    /**
     * Setter method for the use case attribute.
     * 
     * @param useCase The use case to be displayed.
     */
    public void setUseCase(UseCase useCase)
    {
        this.useCase = useCase;
    }

}
