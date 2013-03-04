package br.ufpe.cin.target.cnl.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.dialogs.EditRemoveLexicalTermDialog;

public class EditRemoveTermsActionDelegate extends Action implements IWorkbenchAction{

    private static final String ID = "br.ufpe.cin.target.cnl.editremovelexicalterm";
    
    private Shell shell;
    
    private EditRemoveLexicalTermDialog editRemoveLexicalTermDialog;
    
    public EditRemoveTermsActionDelegate(){
        setId(ID);
    }

    
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
            this.editRemoveLexicalTermDialog = new EditRemoveLexicalTermDialog(this.shell);
            this.editRemoveLexicalTermDialog.open();
        }
    }

    public void dispose() {}

}
