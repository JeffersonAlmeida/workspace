package br.ufpe.cin.target.uceditor.editor.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import br.ufpe.cin.target.tcg.actions.AbstractTCGAction;
import br.ufpe.cin.target.uceditor.util.UseCaseEditorUtil;

//INSPECTED mcms 22/01 classe adicionada para criação de uma ação para o menu de contexto 
public class AddStepAboveAction extends AbstractTCGAction{


    protected ISelectionProvider selectionProvider;
	private TableViewer tv;
	private ScrolledForm sf;
	
	public AddStepAboveAction(TableViewer tv, ScrolledForm sf, String id, String text, String tooltipText,
            ISelectionProvider selectionProvider){
		super(id, text, tooltipText, selectionProvider);
		this.tv = tv;
		this.sf = sf;
		this.selectionProvider = selectionProvider;
	}
	
	
	public void run(ISelection selection)
    {
        if (selection != null && !selection.isEmpty())
        {
        	UseCaseEditorUtil.addLineAbove(tv, sf);             	
        }
    }

    /**
     * @ see {@link ISelectionChangedListener}{@link #selectionChanged(SelectionChangedEvent)}
     * Enables this action according to selection event: this action is enabled if the selection
     * object is a test case and it is not in exclusion or inclusion list.
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
    	// Do nothing
    }
	
	
}
