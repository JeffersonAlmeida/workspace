/*
 * @(#)IncludeTestCaseAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Apr 11, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 */
package com.motorola.btc.research.target.tcg.editors.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

import com.motorola.btc.research.target.tcg.actions.AbstractTCGAction;
import com.motorola.btc.research.target.tcg.editors.OnTheFlyTestExclusionPage;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * This action adds the selected test cases in inclusion list.
 * 
 * </pre>
 */
public class IncludeTestCaseAction extends AbstractTCGAction
{
    /**
     * The form page that exhibits the list of test cases always excluded or included in the final
     * test suite.
     */
    private OnTheFlyTestExclusionPage inclusionPage;

    /**
     * Creates an instance of this action.
     * 
     * @param id The action id.
     * @param selectionProvider The selection provider for this action.
     * @param inclusionPage The form page that exhibits the list of test cases always excluded or
     * included in the final test suite.
     */
    public IncludeTestCaseAction(String id, ISelectionProvider selectionProvider,
            OnTheFlyTestExclusionPage inclusionPage)
    {
        super(id, "Include Test Case Permanently",
                "Always include the selected test cases from the final test suite",
                selectionProvider);
        this.inclusionPage = inclusionPage;
    }

    /**
     * @see AbstractTCGAction#run(ISelection) Gets the selected test case and adds it in inclusion
     * list.
     */
    public void run(ISelection selection)
    {
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        if (selection != null && !selection.isEmpty())
        {
            for (Object obj : structuredSelection.toList())
            {
                TextualTestCase testCase = (TextualTestCase) obj;

                if (!this.inclusionPage.isTestCaseIncluded(testCase))
                {
                    this.inclusionPage.addTestCaseToInclusionList(testCase);
                }
            }
        }
        this.inclusionPage.getEditor().setFocus();
        this.selectionProvider.setSelection(StructuredSelection.EMPTY);
    }

    /**
     * @ see {@link ISelectionChangedListener}{@link #selectionChanged(SelectionChangedEvent)}
     * Enables this action according to selection event: this action is enabled if the selection
     * object is a test case and it is not in inclusion or exclusion list.
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();

        if (selection != null && !selection.isEmpty())
        {
            boolean allIncluded = true;
            for (Object obj : selection.toList())
            {
                if (!(obj instanceof TextualTestCase))
                {
                    this.setEnabled(false);
                    break;
                }
                else if (!this.inclusionPage.isTestCaseIncluded((TextualTestCase) obj))
                {
                    allIncluded = false;
                }
            }
            this.setEnabled(!allIncluded);
        }
        else
        {
            this.setEnabled(false);
        }

    }

}
