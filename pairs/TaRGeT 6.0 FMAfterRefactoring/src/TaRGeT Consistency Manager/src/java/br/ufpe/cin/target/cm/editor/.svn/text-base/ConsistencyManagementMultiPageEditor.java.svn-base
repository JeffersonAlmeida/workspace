/*
 * @(#)ConsistencyManagementMultiPageEditor.java
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
 * tnd783   07/07/2008    LIBhh00000    Initial creation.
 * fsf2		20/06/2009					Integration
 */
package br.ufpe.cin.target.cm.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.editors.RefreshableEditor;

/**
 * <pre>
 * CLASS:
 * This class is responsible for Consistency Management pages creation.  
 * 
 * RESPONSIBILITIES:
 * Creates form pages, provides comparison results for form pages.
 * 
 * </pre>
 */
public class ConsistencyManagementMultiPageEditor extends FormEditor implements RefreshableEditor
{
    /**
     * The unique identifier of this form editor.
     */
    public static final String ID = "br.ufpe.cin.target.cm.editor.ConsistencyManagementMultiPageEditor";

    /* (non-Javadoc)
     * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
     */
    
    protected void addPages()
    {
        TestComparisonPage testComparisonPage = new TestComparisonPage(this);

        int index;
        
        try
        {
            index = addPage(testComparisonPage);
            setPageText(index, Properties.getProperty("comparison"));
        }
        catch (PartInitException e)
        {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    
    public void doSave(IProgressMonitor monitor)
    {
       
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     */
    
    public void doSaveAs()
    {

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     */
    
    public boolean isSaveAsAllowed()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see br.ufpe.cin.target.pm.editors.RefreshableEditor#refresh()
     */
     //INSPECT: Felype - Atributo adicionado.
    
    public void refresh(boolean refreshAnyway)
    {
        ((TestComparisonPage)this.pages.get(0)).refresh();
    }

}
