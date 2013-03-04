/*
 * @(#)AddRequirementToFilterAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Apr 10, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 */
package com.motorola.btc.research.target.tcg.editors.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.motorola.btc.research.target.tcg.actions.AbstractTCGAction;
import com.motorola.btc.research.target.tcg.editors.OnTheFlyTestSelectionPage;

/**
 * <pre>
 * CLASS:
 * This action adds an specific requirement to the requirements filter.  
 * 
 * </pre>
 */
public class AddRequirementToFilterAction extends AbstractTCGAction
{
    /**
     * The form page that provides filter configuration.
     */
    private OnTheFlyTestSelectionPage selectionPage;

    /**
     * Creates an instance of this action.
     * 
     * @param id The action id.
     * @param selectionProvider The selection provider for this action.
     * @param selectionPage The form page that provides filter configuration.
     */
    public AddRequirementToFilterAction(String id, ISelectionProvider selectionProvider,
            OnTheFlyTestSelectionPage selectionPage)
    {
        super(id, "Add to requirement filter", "Add the selected requirement in the requirements filter list.", selectionProvider);
        this.selectionPage = selectionPage;

    }

    /**
     * @see AbstractTCGAction#run(ISelection) Gets the selected requirement and adds it in the
     * requirements filter list.
     */
    public void run(ISelection selection)
    {
        IStructuredSelection sel = (IStructuredSelection) selection;
        if (sel != null && !sel.isEmpty())
        {
            Object selectedObj = sel.getFirstElement();
            if (selectedObj instanceof String)
            {
                this.selectionPage.selectRequirement((String) selectedObj);
                this.selectionPage.getEditor().setFocus();
            }
        }
    }

    /**
     * @ see {@link ISelectionChangedListener}{@link #selectionChanged(SelectionChangedEvent)}
     * Enables this action according to selection event: this action is enable if the selection
     * object is a requirement and it is not in requirements filter list.
     */
    public void selectionChanged(SelectionChangedEvent event)
    {

        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        this.setEnabled(false);
        if (selection != null && !selection.isEmpty())
        {
            Object selectedObj = selection.getFirstElement();
            if (selectedObj instanceof String
                    && !(this.selectionPage.isRequirementSelected((String) selectedObj)))
            {
                this.setEnabled(true);
            }
        }
    }

}
