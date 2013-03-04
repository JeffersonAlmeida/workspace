/*
 * @(#)ErrorView.java
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
 * dhq348   Dec 17, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 25, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   May 22, 2007   LIBmm25975    The class now extends TargetTableView.
 * pvcv     Apr 01, 2009				 Internationalization support.
 * fsf2     Jul 12, 2009                 Added error message while refreshing the project.     
 */

package br.ufpe.cin.target.pm.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
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
import br.ufpe.cin.target.pm.PMActivator;
import br.ufpe.cin.target.pm.common.TargetTableView;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.controller.TargetProjectRefreshInformation;
import br.ufpe.cin.target.pm.errors.Error;


/**
 * This class is responsible for displaying the errors to the user. It keeps a
 * table with the columns: Description, Resource, Path and Location of the
 * errors.
 * 
 * <pre>
 * CLASS:
 * This view maintains and manages the component that displays the errors to the user. It extends
 * TargetTableView and implements createIndividualControl and createTable methods. It also provides
 * a private class (ErrorViewLabelProvider) that handles the labeling of the items in the errors
 * table.
 * 
 * <pre>
 */
public class ErrorView extends TargetTableView {

	/** The view id that is referred in plugin.xml */
	public static final String ID = "br.ufpe.cin.target.pm.views.ErrorView";

	/**
	 * Creates a table and a viewer that handles all table's event. It also
	 * attaches to the viewer a content provider and a label provider. Also
	 * updates the error counter.
	 */
	
	protected TableViewer createTable() {
		TableViewer result = new TableViewer(new Table(this.group, SWT.BORDER
				| SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION));

		Table table = result.getTable();
		table.setBounds(0, 0, 100, 100);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		/*
		 * The layout of the data. It is set to fill the horizontal alignment,
		 * fill the vertical alignment. The Grid will also occupy all view
		 * space.
		 */
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		table.setLayoutData(gridData);

		createColumns(table); // creates the table columns

		/* sets a viewer content provider */
		result.setContentProvider(new ArrayContentProvider());
		/* sets a viewer label provider */
		result.setLabelProvider(new ErrorViewLabelProvider());

		return result;
	}

	/**
	 * Create the table columns and set their names.
	 * 
	 * @param table
	 *            The table where the columns will be created.
	 */
	private void createColumns(Table table) {
		TableColumn[] columns = new TableColumn[4];
		columns[0] = new TableColumn(table, SWT.NONE);
		columns[1] = new TableColumn(table, SWT.NONE);
		columns[2] = new TableColumn(table, SWT.NONE);
		columns[3] = new TableColumn(table, SWT.NONE);

		columns[0].setText(Properties.getProperty("description"));
		columns[1].setText(Properties.getProperty("resource"));
		columns[2].setText(Properties.getProperty("path"));
		columns[3].setText(Properties.getProperty("location"));
	}

	/**
	 * Updates the error counter
	 */
	private void updateErrorCounter() {
		group.setText(this.viewer.getTable().getItems().length + " "
				+ Properties.getProperty("errors"));
	}

	/**
	 * Updates the list of errors.
	 */
	public void update() {
	    ArrayList<Error> oldErrors = null;
	    boolean displayWarnig = false;
	    
	    if (this.viewer.getInput() instanceof ArrayList)
        {
	        oldErrors = (ArrayList<Error>) this.viewer.getInput();
        }
	   
		ArrayList<Error> errorList = new ArrayList<Error>(
				ProjectManagerController.getInstance().getErrorList());
		
		if(oldErrors != null && !oldErrors.containsAll(errorList))
		{
		    displayWarnig = true;
		}
		
		this.viewer.setInput(errorList);
		this.updateErrorCounter();
		
		if(displayWarnig)
		{
		    this.displayWarnings(errorList);
		}
	}
	
	   /**
     * Displays a message dialog containing any step id warning that exists.
     * 
     * @param refreshInfo
     *            The information about the importing process.
     */
    private void displayWarnings(Collection<Error> stepIdErrors) {
        if (stepIdErrors != null && stepIdErrors.size() > 0) {
            String message = Properties.getProperty("documents_step_id_errors")
                    + "\n\n";
            Set<String> errors = new HashSet<String>();
            for (Error error : stepIdErrors) {
                if (!errors.contains(error.getResource())) {
                    errors.add(error.getResource());
                    message += "\t" + error.getResource() + "\n\n";
                }
            }

            message += Properties.getProperty("see_errors_tab_for_details");

            MessageDialog.openWarning(null, "Warning", message);
        }

    }

	/**
	 * Private class that provides the text and the images to the rows of the
	 * table.
	 * 
	 * <pre>
	 * CLASS:
	 * Provides the text to the rows according to the error and the column. It also provides an icon
	 * to the row according to the error type.
	 * </pre>
	 */
	private class ErrorViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		/**
		 * Provides an icon to the table row according to the type of the error.
		 * 
		 * @param element
		 *            The element that represents the error
		 * @param columnIndex
		 *            The column that will be verified. If it is the first one
		 *            (0) then the icon will be set to this column.
		 * @return The icon image or <code>null</code> if the column is not the
		 *         first one.
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			Image image = null;
			if (columnIndex == 0) {
				Error error = (Error) element;
				if (error.getType() != Error.WARNING) {
					image = PMActivator.imageDescriptorFromPlugin(
							PMActivator.PLUGIN_ID, "icons/error.gif")
							.createImage();
				} else {
					image = PMActivator.imageDescriptorFromPlugin(
							PMActivator.PLUGIN_ID, "icons/warning.gif")
							.createImage();
				}

				image = new Image(image.getDevice(), image.getImageData()
						.scaledTo(15, 15));
			}
			return image;
		}

		/**
		 * Gets the text of the row. Sets the text to their respectively row
		 * according to the column number.
		 * 
		 * <pre>
		 * case 0: The description of the error.
		 * case 1: The resource where the error occurred.
		 * case 2: The path where the error occurred.
		 * case 3: The location where the error occurred.
		 * </pre>
		 * 
		 * @param element
		 *            The element that represents the error
		 * @param columnIndex
		 *            The column that will be verified.
		 * @return The text to the respectively column.
		 */
		public String getColumnText(Object element, int columnIndex) {
			Error error = (Error) element;
			String result = "";
			switch (columnIndex) {
			case 0:
				result = error.getDescription();
				break;
			case 1:
				result = error.getResource();
				break;
			case 2:
				result = error.getPath();
				break;
			case 3:
				result = error.getLocation();
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

		/*
		 * 4 columns and set that the columns must not have equal width
		 */
		GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;

		result.setLayout(gridLayout); // sets the layout of the group

		return result;
	}

	/**
	 * Sets the text of the group.
	 */
	
	protected void performFinalOperations() {
		this.updateErrorCounter();
	}
}
