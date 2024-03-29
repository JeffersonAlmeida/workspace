/*
 * @(#)SearchResultsView.java
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
 * dhq348    May 22, 2007   LIBmm25975   Initial creation.
 * dhq348    Sep 03, 2007   LIBnn24462   Added the method refreshSearchResults().
 * pvcv      Apr 01, 2009				 Internationalization support
 */
package br.ufpe.cin.target.pm.views;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.common.TargetTableView;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.editors.UseCaseHTMLGenerator;
import br.ufpe.cin.target.pm.exceptions.TargetSearchException;
import br.ufpe.cin.target.pm.search.SearchController;
import br.ufpe.cin.target.pm.search.TargetIndexDocument;


/**
 * This view is responsible for displaying the the search results. It keeps a
 * table with the columns: Use Case Name, Use Case Id, Feature ID and Document
 * Name of the search results.
 * 
 * <pre>
 * CLASS:
 * Keeps a table with the search results. The class adds the capability for double clicking in a
 * search result and for opening the use case inside the tool. It also provides a private class
 * (SearchResultsViewLabelProvider) that handles the labeling of the search result.
 * </pre>
 */
public class SearchResultsView extends TargetTableView {
	/** The view id that is referred in plugin.xml */
	public static final String ID = "br.ufpe.cin.target.pm.views.SearchResultsView";

	/**
	 * Creates the table and the viewer that handles all table's event.
	 */
	
	protected TableViewer createTable() {
		TableViewer result = new TableViewer(new Table(this.group, SWT.BORDER
				| SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION));

		Table table = result.getTable();
		table.setBounds(0, 0, 100, 100);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		/* The layout of the data */
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		table.setLayoutData(gridData);

		this.createColumns(table);

		result.setContentProvider(new ArrayContentProvider());
		result.setLabelProvider(new SearchResultsViewLabelProvider());

		return result;
	}

	/**
	 * Create the table columns and set their names.
	 * 
	 * @param table
	 *            The table where the columns will be created.
	 */
	private void createColumns(Table table) {
		TableColumn[] columns = new TableColumn[] {
				new TableColumn(table, SWT.LEFT),
				new TableColumn(table, SWT.LEFT),
				new TableColumn(table, SWT.LEFT),
				new TableColumn(table, SWT.LEFT) };
		columns[0].setText(Properties.getProperty("use_case_name")); // usecaseNameColumn
		columns[1].setText(Properties.getProperty("use_case_id")); // usecaseIdColumn
		columns[2].setText(Properties.getProperty("feature_id")); // featureIdColumn
		columns[3].setText(Properties.getProperty("document_name")); // documentNameColumn
	}

	/**
	 * Adds the search results to the table by setting the input to the table's
	 * viewer.
	 * 
	 * @param documents
	 *            The documents that represent the search results.
	 * @param query
	 *            The query used in the search.
	 */
	public void addSearchResults(List<TargetIndexDocument> documents,
			String query) {
		this.group.setText(Properties.getProperty("results_for") + " " + query);
		this.viewer.setInput(documents);
	}

	/**
	 * Updates the search results view according to the last query.
	 * 
	 * @throws TargetSearchException
	 *             In case of any error during the search processment.
	 */
	public void refreshSearchResults() throws TargetSearchException {
		if (!SearchController.getInstance().getLastQueries().isEmpty()) {
			String query = SearchController.getInstance().getLastQueries()
					.peek();
			List<TargetIndexDocument> documents = SearchController
					.getInstance().search(query, false);
			documents = SearchController.getInstance().filterSearch(documents);
			this.addSearchResults(documents, query);
		}
	}

	/**
	 * Private class that provides the text to the rows of the table.
	 * 
	 * <pre>
	 * CLASS:
	 * Provides the text to the rows according to the indexed document and the column.
	 * </pre>
	 */
	private class SearchResultsViewLabelProvider extends LabelProvider
			implements ITableLabelProvider {
		/**
		 * Do not return any image.
		 * 
		 * @param element
		 *            The element that represents the indexed document.
		 * @param columnIndex
		 *            The column that will be verified.
		 * @return <code>null</code>
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * Gets the text of the row. Sets the text to their respectively row
		 * according to the column number.
		 * 
		 * <pre>
		 * case 0: The use case name.
		 * case 1: The use case id.
		 * case 2: The feature id.
		 * case 3: The document name.
		 * </pre>
		 * 
		 * @param element
		 *            The element that represents the indexed document.
		 * @param columnIndex
		 *            The column that will be verified.
		 * @return The text to the respectively column.
		 */
		public String getColumnText(Object element, int columnIndex) {
			TargetIndexDocument document = (TargetIndexDocument) element;
			String result = "";
			switch (columnIndex) {
			case 0:
				result = document.getUsecase().getName();
				break;
			case 1:
				result = document.getUsecase().getId();
				break;
			case 2:
				result = document.getFeature().getId();
				break;
			case 3:
				result = document.getDocumentName();
				break;
			default:
				break;
			}
			return result;
		}
	}

	/**
	 * Creates a group and sets its layout (a gridlayout with four columns).
	 */
	
	protected Group createGroup() {
		Group result = new Group(this.parent, SWT.FILL);
		result.setText(Properties.getProperty("results"));

		GridLayout gridLayout = new GridLayout(4, false); // 4 columns and set
		// that the columns
		// must not have equal width
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;

		result.setLayout(gridLayout); // sets the layout of the group

		return result;
	}

	/**
	 * Adds a double click capability to the table.
	 */
	
	protected void performFinalOperations() {
		this.addDoubleClickCapability();
	}

	/**
	 * Adds the double click capability to the search results. By double
	 * clicking it the use case is displayed inside the tool.
	 */
	private void addDoubleClickCapability() {
		this.viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				TargetIndexDocument document = (TargetIndexDocument) selection
						.getFirstElement();
				List<br.ufpe.cin.target.pm.errors.Error> errorList = ProjectManagerController
						.getInstance().getErrorList();
				try {
					UseCaseHTMLGenerator generator = new UseCaseHTMLGenerator();
					generator.openUseCaseInsideTool(document.getUsecase(),
							document.getFeature(), errorList, true);
				} catch (Exception e) {
					MessageDialog.openInformation(viewer.getControl()
							.getShell(), Properties.getProperty("search"),
							Properties.getProperty("error_opening_use_case")
									+ "  " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Encapsulates calls to all update methods of the current view.
	 * 
	 * @throws TargetSearchException
	 *             In case any search error occurs.
	 */
	public void update() throws TargetSearchException {
		refreshSearchResults();
	}
}
