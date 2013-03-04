/*
 * @(#)AddNewLexicalTermActionDelegate.java
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
 * wxx###   May 14, 2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.actions;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.dialogs.AddLexicalTermDialog;


//INSPECT: Felype - Agora herda de Action.
public class AddNewLexicalTermActionDelegate extends Action implements IWorkbenchAction
{
    private static final String ID = "br.ufpe.cin.target.cnl.addlexicalterm";
    
    private Shell shell;
    
    private AddLexicalTermDialog addNewLexicalTermDialog;
    
    public AddNewLexicalTermActionDelegate(){
        setId(ID);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     */
    
    public void run()
    {
        //verifies if the CNL controller has been started properly in order to run the action.
        if(CNLPluginController.getInstance().isErrorStartingController())
        {
            MessageDialog.openError(null, "Unable to initialize the CNL Plugin",
                    "Error while initializing configuration files. Reload bases and try again.");
        }
        else
        {
            this.shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
            this.addNewLexicalTermDialog = new AddLexicalTermDialog(this.shell);
            
            HashMap<TestCaseTextType, Collection<String>> list = CNLPluginController.getInstance().getOrderedUnkownWords();
            this.addNewLexicalTermDialog.open(list);
        }        
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.actions.ActionFactory.IWorkbenchAction#dispose()
     */
    
    public void dispose()
    {
        // TODO Auto-generated method stub
        
    }
}
