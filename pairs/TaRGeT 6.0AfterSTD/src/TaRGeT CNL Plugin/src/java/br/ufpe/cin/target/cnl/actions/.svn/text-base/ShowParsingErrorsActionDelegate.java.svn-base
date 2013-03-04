package br.ufpe.cin.target.cnl.actions;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import br.ufpe.cin.cnlframework.grammar.ParsingPossibility;
import br.ufpe.cin.cnlframework.postagger.TaggedSentence;
import br.ufpe.cin.cnlframework.tokenizer.TokenizedSentence;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.dialogs.ShowParsingErrorsDialog;


public class ShowParsingErrorsActionDelegate implements IViewActionDelegate{
	
	private Shell shell;
    
    private ShowParsingErrorsDialog parsingErrorsDialog;
	
	
	public void init(IViewPart view) {
		this.shell = view.getViewSite().getShell();
		this.parsingErrorsDialog = new ShowParsingErrorsDialog(this.shell);
		
	}

	
    public void run(IAction action)
    {
        //verifies if the CNL controller has been started properly in order to run the action.
	    if (CNLPluginController.getInstance().isErrorStartingController())
        {
            MessageDialog.openError(null, "Unable to initialize the CNL Plugin",
                    "Error while initializing configuration files. Reload bases and try again.");
        }
        else
        {
            HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> errorSentence = CNLPluginController
                    .getInstance().getFaultList();
            this.parsingErrorsDialog.open(errorSentence);
        }

    }

	
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
