/*
 * @(#)ErrorView.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Dec 17, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 25, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348    May 22, 2007   LIBmm25975   The class now extends TargetTableView.
 */

package com.motorola.btc.research.target.pm.views;

import java.util.ArrayList;

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

import com.motorola.btc.research.target.pm.PMActivator;
import com.motorola.btc.research.target.pm.common.TargetTableView;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.errors.Error;

/**
 * This class is responsible for displaying the errors to the user. It keeps a table with the
 * columns: Description, Resource, Path and Location of the errors.
 * 
 * <pre>
 * CLASS:
 * This view maintains and manages the component that displays the errors to the user. It extends
 * TargetTableView and implements createIndividualControl and createTable methods. It also provides
 * a private class (ErrorViewLabelProvider) that handles the labeling of the items in the errors
 * table.
 * <pre>
 */
public class ErrorView extends TargetTableView
{

    /** The view id that is referred in plugin.xml */
    public static final String ID = "com.motorola.btc.research.target.pm.views.ErrorView";

    /**
     * Creates a table and a viewer that handles all table's event. It also attaches to the viewer a
     * content provider and a label provider. Also updates the error counter.
     */

    protected TableViewer createTable()
    {
        TableViewer result = new TableViewer(new Table(this.group, SWT.BORDER | SWT.MULTI
                | SWT.V_SCROLL | SWT.FULL_SELECTION));

        Table table = result.getTable();
        table.setBounds(0, 0, 100, 100);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        /*
         * The layout of the data. It is set to fill the horizontal alignment, fill the vertical
         * alignment. The Grid will also occupy all view space.
         */
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
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
     * @param table The table where the columns will be created.
     */
    private void createColumns(Table table)
    {
        TableColumn[] columns = new TableColumn[4];
        columns[0] = new TableColumn(table, SWT.NONE);
        columns[1] = new TableColumn(table, SWT.NONE);
        columns[2] = new TableColumn(table, SWT.NONE);
        columns[3] = new TableColumn(table, SWT.NONE);

        columns[0].setText("Description");
        columns[1].setText("Resource");
        columns[2].setText("Path");
        columns[3].setText("Location");
    }

    /**
     * Updates the error counter
     */
    private void updateErrorCounter()
    {
        group.setText(this.viewer.getTable().getItems().length + " errors.");
    }

    /**
     * Updates the list of errors.
     */
    public void update()
    {
        ArrayList<Error> errorList = new ArrayList<Error>(ProjectManagerController.getInstance()
                .getErrorList());
        this.viewer.setInput(errorList);
        this.updateErrorCounter();
    }

    /**
     * Private class that provides the text and the images to the rows of the table.
     * 
     * <pre>
     * CLASS:
     * Provides the text to the rows according to the error and the column. It also provides an icon
     * to the row according to the error type.
     * </pre>
     */
    private class ErrorViewLabelProvider extends LabelProvider implements ITableLabelProvider
    {
        /**
         * Provides an icon to the table row according to the type of the error.
         * 
         * @param element The element that represents the error
         * @param columnIndex The column that will be verified. If it is the first one (0) then the
         * icon will be set to this column.
         * @return The icon image or <code>null</code> if the column is not the first one.
         */
        public Image getColumnImage(Object element, int columnIndex)
        {
            Image image = null;
            if (columnIndex == 0)
            {
                Error error = (Error) element;
                if (error.getType() != Error.WARNING)
                {
                    image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                            "icons/error.gif").createImage();
                }
                else
                {
                    image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                            "icons/warning.gif").createImage();
                }

                image = new Image(image.getDevice(), image.getImageData().scaledTo(15, 15));
            }
            return image;
        }

        /**
         * Gets the text of the row. Sets the text to their respectively row according to the column
         * number.
         * 
         * <pre>
         * case 0: The description of the error.
         * case 1: The resource where the error occurred.
         * case 2: The path where the error occurred.
         * case 3: The location where the error occurred.
         * </pre>
         * 
         * @param element The element that represents the error
         * @param columnIndex The column that will be verified.
         * @return The text to the respectively column.
         */
        public String getColumnText(Object element, int columnIndex)
        {
            Error error = (Error) element;
            String result = "";
            switch (columnIndex)
            {
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

    protected Group createGroup()
    {
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

    protected void performFinalOperations()
    {
        this.updateErrorCounter();
    }
}
