/*
 * @(#)SearchAction.java
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
 * mcms     13/11/2009                    Correction of class structure
 */
package br.ufpe.cin.target.uceditor.editor.actions;

import java.util.HashMap;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.editor.FormEditor;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.uceditor.dialogs.SearchDialog;


/**
 * <pre>
 * CLASS:
 * This action calls a search dialog.  
 * 
 * </pre>
 */
//INSPECT mcms class has his extension modified, now it extends from Action
public class SearchAction extends Action
{
    //INSPECT mcms attribute "manager" was deleted because it became unnecessary
	
    private HashMap<String, Object> components; 
	
    private Shell shell;
    
    private FormEditor editor;
	
	/**
     * Initializes attributes of this action and calls hook method.
     * 
     * @param manager The tool bar manager.
	 * @param components The list of components used at user interface
	 * @param shell The Shell to create the dialog .
     */
    //INSPECT mcms constructor received an ImageDescriptor parameter  
	public SearchAction(IToolBarManager manager, HashMap<String, Object> components, Shell shell, 
	                    ImageDescriptor searchImage, FormEditor editor)
	{	
	    super("&Search", searchImage);
		this.components = components;
		this.shell = shell;
		this.setToolTipText(Properties.getProperty("Search"));
		this.editor = editor;
        manager.add(this);	
	}
	
	/**
     * Runs the action.
     */
    //INSPECT mcms method hook() was deleted and method run() was created
    public void run()
    {
        SearchDialog dialog = new SearchDialog(shell, Properties.getProperty("Search"), components, this.editor);
        dialog.open();
    }

}
