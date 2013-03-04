/*
 * @(#)SearchDialog.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   May 15, 2007    LIBmm25975   Initial creation.
 * dhq348   Aug 20, 2007    LIBmm93130   Modifications according to CR.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 * dhq348   Sep 03, 2007    LIBnn24462   Updated call to SearchController.search()
 */
package com.motorola.btc.research.target.pm.search.gui;

import java.util.List;
import java.util.Stack;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.pm.exceptions.TargetSearchException;
import com.motorola.btc.research.target.pm.search.SearchController;
import com.motorola.btc.research.target.pm.search.TargetIndexDocument;
import com.motorola.btc.research.target.pm.views.SearchResultsView;

/**
 * This dialog is used to search for contents in use cases. The dialog displays a search dialog with
 * two buttons ("Search" and "Cancel"), one combo box ("Queries") and one link to TaRGeT help.
 * 
 * <pre>
 * CLASS:
 * This class creates a search dialog. This class overrides methods from Dialog, and adds procedures to
 * handle search operations.
 * </pre>
 */
public class SearchDialog extends Dialog
{

    /**
     * Input text component. Presents the query history and the current query.
     */
    private Combo text;

    /**
     * The title of the search dialog.
     */
    private String title;

    /**
     * The previous search queries.
     */
    private Stack<String> previousQueries;

    /**
     * Creates a search dialog given its parent, title and default value.
     * 
     * @param parentShell The parent of the search dialog
     * @param title The title of the search dialog.
     * @param previous The previous search values.
     */
    public SearchDialog(Shell parentShell, String title, Stack<String> previous)
    {
        super(parentShell);
        this.title = title;
        this.previousQueries = previous;
        this.createShell();

        this.createButtonBar(parentShell);
    }

    /**
     * Creates the buttons for the search dialog buttons.
     * 
     * @param parent The search button bar.
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent)
    {
        createButton(parent, IDialogConstants.OK_ID, "Search", true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

        /* enables the 'Search' button depending on the value of 'text' */
        boolean enable = false;
        enable = this.text != null && this.text.getText().trim().length() > 0;

        getButton(IDialogConstants.OK_ID).setEnabled(enable);
    }

    /**
     * Creates a help link in the <code>parent</code> component.
     * 
     * @param parent The parent component of the link.
     */
    private void createLink(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        layout.marginHeight = 5;
        layout.marginWidth = 10;
        layout.verticalSpacing = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Link help = new Link(composite, SWT.NONE);
        help.setText("For more information about how to search for use cases see our <a>Help</a>");
        help.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            public void widgetSelected(SelectionEvent e)
            {
                IWorkbench workbench = PlatformUI.getWorkbench();
                workbench.getHelpSystem().displayHelp();
            }
        });
    }

    /**
     * Configures the shell. Sets the title, size and location of the shell.
     * 
     * @param shell The shell to be configured.
     */
    @Override
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);
        if (title != null)
        {
            shell.setText(title);
        }
        shell.setSize(400, 135);
        shell.setLocation(400, 300);
    }

    /**
     * Creates the dialog area.
     * 
     * @param parent The parent component of the dialog area.
     */
    @Override
    protected Control createDialogArea(Composite parent)
    {
        // create composite
        Composite composite = (Composite) super.createDialogArea(parent);

        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 5;
        layout.marginWidth = 10;
        layout.verticalSpacing = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label label = new Label(composite, SWT.WRAP);
        label.setText("Find:");

        text = new Combo(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
        text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

        text.setFocus();
        if (previousQueries != null)
        {
            this.fillItems();
            text.select(0);
        }

        this.createLink(parent);
        this.addTextModifyListener(this.text);

        applyDialogFont(composite);
        return composite;
    }

    /**
     * Validates the text in the combo box.
     * 
     * @param combo The combo whose text will be validated.
     */
    private void addTextModifyListener(final Combo combo)
    {
        combo.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                if (combo.getText().trim().length() == 0)
                {
                    getButton(IDialogConstants.OK_ID).setEnabled(false);
                }
                else
                {
                    getButton(IDialogConstants.OK_ID).setEnabled(true);
                }
            }
        });
    }

    /**
     * Sets the text history.
     */
    private void fillItems()
    {
        String[] items = new String[previousQueries.size()];
        int i = 0;
        while (!previousQueries.empty())
        {
            items[i++] = previousQueries.pop();
        }
        text.setItems(items);
    }

    /**
     * Handles a button pressed event.
     * 
     * @param buttonId The id of the pressed button.
     */
    @Override
    protected void buttonPressed(int buttonId)
    {
        switch (buttonId)
        {
            case IDialogConstants.OK_ID:
                this.search();
                break;

            default:
                break;
        }
        super.buttonPressed(buttonId);
    }

    /**
     * Performs a search.
     */
    private void search()
    {
        String query = this.text.getText().trim();
        if (query.length() > 0)
        {
            try
            {
                List<TargetIndexDocument> documents = SearchController.getInstance().search(query,
                        true);
                documents = SearchController.getInstance().filterSearch(documents);
                GUIManager.getInstance().displaySearchResults(query, documents);
            }
            catch (TargetSearchException e)
            {
                e.printStackTrace();
                MessageDialog.openError(this.getShell(), "Error on searching",
                        "The search could not be performed. " + e.getMessage());
            }
        }
        /* sets the focus into the search results view even if the query is empty */
        GUIManager.getInstance().activateView(
                (SearchResultsView) GUIManager.getInstance().getView(SearchResultsView.ID, true));
    }

}
