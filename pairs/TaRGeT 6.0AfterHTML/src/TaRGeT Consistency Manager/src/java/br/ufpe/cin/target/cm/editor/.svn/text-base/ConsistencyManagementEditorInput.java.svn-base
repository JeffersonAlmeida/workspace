/*
 * @(#)ConsistencyManagementEditorInput.java
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
 * tnd783   07/07/2008    	 		     Initial creation.
 * fsf2		20/06/2009					 Integration.
 */
package br.ufpe.cin.target.cm.editor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.cm.progressbars.CompareTestCasesProgress;
import br.ufpe.cin.target.cm.tcsimilarity.logic.Comparison;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.tcg.controller.TestCaseGeneratorController;


/**
 * <pre>
 * CLASS:
 * Represents an editor input for the Consistency Management.
 * 
 * RESPONSIBILITIES:
 * Provides results of comparison for Consistency Management form pages.  
 * 
 * </pre>
 */
public class ConsistencyManagementEditorInput implements IEditorInput
{
    /**
     * The comparison object, representing all the comparison results.
     */
    private Comparison comparison;
    
    private File oldTestCaseFile;

    /**
     * Creates an editor input for the Consistency Management. 
     * 
     * @param comparison
     * @param file 
     */
    public ConsistencyManagementEditorInput(Comparison comparison, File file)
    {
        this.comparison = comparison;
        this.oldTestCaseFile = file;
    }

    /**
     * Gets the comparison object, with all the comparison results.
     * @return
     */
    public Comparison getComparison()
    {
        return comparison;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#exists()
     */
    
    public boolean exists()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     */
    
    public ImageDescriptor getImageDescriptor()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    
    public String getName()
    {
        return "";
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    
    public IPersistableElement getPersistable()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    
    public String getToolTipText()
    {
        return "";
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class adapter)
    {
        return null;
    }

    /**
     * Comment for method.
     * @throws InterruptedException 
     * @throws InvocationTargetException 
     */
    public void refresh() throws InvocationTargetException, InterruptedException
    {
        CompareTestCasesProgress progressBar = new CompareTestCasesProgress(
        		Properties.getProperty("comparing_test_cases"), this.oldTestCaseFile,
                TestCaseGeneratorController.getInstance().getLastSuiteGenerated());
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        
        dialog.run(true, true, progressBar);
        
        this.comparison = progressBar.getComparison();
    }

}
