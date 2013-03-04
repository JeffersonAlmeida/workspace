package com.motorola.btc.research.target.cnl.views;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.motorola.btc.research.cnlframework.grammar.ParsingPossibility;
import com.motorola.btc.research.cnlframework.postagger.TaggedSentence;
import com.motorola.btc.research.cnlframework.tokenizer.TokenizedSentence;
import com.motorola.btc.research.target.cnl.controller.CNLPluginController;
import com.motorola.btc.research.target.cnl.dialogs.ShowParsingErrorsDialog;

public class ShowParsingErrorsActionDelegate implements IViewActionDelegate{
	
	private Shell shell;
    
    private ShowParsingErrorsDialog parsingErrorsDialog;
	

	public void init(IViewPart view) {
		this.shell = view.getViewSite().getShell();
		this.parsingErrorsDialog = new ShowParsingErrorsDialog(this.shell);
		
	}


	public void run(IAction action) {
		HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> errorSentence = CNLPluginController.getInstance().getFaultList();
		this.parsingErrorsDialog.open(errorSentence);
		
	}


	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
