/*
 * @(#)SearchDialog.java
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
 * dhq348   May 15, 2007    LIBmm25975   Initial creation.
 * dhq348   Aug 20, 2007    LIBmm93130   Modifications according to CR.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 * dhq348   Sep 03, 2007    LIBnn24462   Updated call to SearchController.search()
 * pvcv     Apr 01, 2009				 Internationalization support
 * lmn3     Apr 01, 2009				 Modifications according to tickets 29, 30 and 31.
 */
package br.ufpe.cin.target.pm.search.gui;

import java.util.ArrayList;
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

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.exceptions.TargetSearchException;
import br.ufpe.cin.target.pm.search.SearchController;
import br.ufpe.cin.target.pm.search.TargetIndexDocument;
import br.ufpe.cin.target.pm.views.SearchResultsView;


/**
 * This dialog is used to search for contents in use cases. The dialog displays
 * a search dialog with two buttons ("Search" and "Cancel"), one combo box
 * ("Queries") and one link to TaRGeT help.
 * 
 * <pre>
 * CLASS:
 * This class creates a search dialog. This class overrides methods from Dialog, and adds procedures to
 * handle search operations.
 * </pre>
 */
public class SearchDialog extends Dialog {

	/**
	 * Input text component. Presents the query history and the current query.
	 */
	private Combo textCombo;
	
	/**
	 * Label to display the message "No results available"
	 */
	private Label noResultsLabel;
	
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
	 * @param parentShell
	 *            The parent of the search dialog
	 * @param title
	 *            The title of the search dialog.
	 * @param previous
	 *            The previous search values.
	 */
	public SearchDialog(Shell parentShell, String title, Stack<String> previous) {
		super(parentShell);
		this.title = title;
		this.previousQueries = previous;
		this.createShell();

		this.createButtonBar(parentShell);
	}
	
	
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font
		// size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());
		
		this.noResultsLabel = new Label(composite, SWT.WRAP | SWT.LEFT);
		this.noResultsLabel.setText(Properties.getProperty("no_results_available"));
		this.noResultsLabel.setVisible(false);
		
		// Add the buttons to the button bar.
		createButtonsForButtonBar(composite);
		return composite;
	}
	
	/**
	 * Creates the buttons for the search dialog buttons.
	 * 
	 * @param parent
	 *            The search button bar.
	 */
	
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, Properties
				.getProperty("search"), true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

		/* enables the 'Search' button depending on the value of 'text' */
		boolean enable = false;
		enable = this.textCombo != null && this.textCombo.getText().trim().length() > 0;

		getButton(IDialogConstants.OK_ID).setEnabled(enable);
	}

	/**
	 * Creates a help link in the <code>parent</code> component.
	 * 
	 * @param parent
	 *            The parent component of the link.
	 */
	private void createLink(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 5;
		layout.marginWidth = 10;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Link help = new Link(composite, SWT.NONE);
		help.setText(Properties.getProperty("help_advice"));
		help.setEnabled(true);
		help.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				IWorkbench workbench = PlatformUI.getWorkbench();
				workbench.getHelpSystem().displayHelp();
			}
		});
	}

	/**
	 * Configures the shell. Sets the title, size and location of the shell.
	 * 
	 * @param shell
	 *            The shell to be configured.
	 */
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
		shell.setSize(460, 135);
		shell.setLocation(400, 300);
	}

	/**
	 * Creates the dialog area.
	 * 
	 * @param parent
	 *            The parent component of the dialog area.
	 */
	
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite composite = (Composite) super.createDialogArea(parent);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 5;
		layout.marginWidth = 10;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label = new Label(composite, SWT.WRAP);
		label.setText(Properties.getProperty("find"));

		textCombo = new Combo(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		textCombo.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));

		textCombo.setFocus();
		if (previousQueries != null) {
			this.fillItems();
			textCombo.select(0);
		}

		this.createLink(parent);
		
		this.addTextModifyListener(this.textCombo);

		applyDialogFont(composite);
		
		return composite;
	}

	/**
	 * Validates the text in the combo box.
	 * 
	 * @param combo
	 *            The combo whose text will be validated.
	 */
	private void addTextModifyListener(final Combo combo) {
		combo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (combo.getText().trim().length() == 0) {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				} else {
					getButton(IDialogConstants.OK_ID).setEnabled(true);
				}
			}
		});
	}

	/**
	 * Sets the text history.
	 */
	private void fillItems() {
		List<String> items = new ArrayList<String>();
		
		while (!previousQueries.empty()) {			
			String item = previousQueries.pop();
			if(!items.contains(item)){
				items.add(item);
			}			
		}
		
		String[] itemsArray = items.toArray(new String[0]);
		textCombo.setItems(itemsArray);
	}

	/**
	 * Handles a button pressed event.
	 * 
	 * @param buttonId
	 *            The id of the pressed button.
	 */
	
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case IDialogConstants.OK_ID:
			this.okPressed();
			break;
		default:
			super.cancelPressed();
			break;
		}
	}
	
	/**
	 * Performs the searching.
	 */
	
	protected void okPressed() {
		this.search();
		this.previousQueries = SearchController.getInstance().getLastQueries();
		this.fillItems();
		this.textCombo.setFocus();
		setReturnCode(OK);
	}

	/**
	 * Performs a search.
	 */
	private void search() {
		String query = this.textCombo.getText().trim();
		if (query.length() > 0) {
			try {
				List<TargetIndexDocument> documents = SearchController
						.getInstance().search(query, true);
				documents = SearchController.getInstance().filterSearch(
						documents);
				if(documents.isEmpty()){
					this.noResultsLabel.setVisible(true);
				}
				else{
					this.noResultsLabel.setVisible(false);
				}				
				GUIManager.getInstance().displaySearchResults(query, documents);
			} catch (TargetSearchException e) {
				e.printStackTrace();
				MessageDialog.openError(this.getShell(), Properties.getProperty("error_on_searching"),
						Properties.getProperty("search_could_not_be_performed") + e.getMessage());
			}
		}
		/*
		 * sets the focus into the search results view even if the query is
		 * empty
		 */
		GUIManager.getInstance().activateView(
				(SearchResultsView) GUIManager.getInstance().getView(
						SearchResultsView.ID, true));
	}

}
