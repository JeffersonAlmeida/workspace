/*
 * @(#)TargetTableView.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    May 22, 2007   LIBmm25975   Initial creation.
 */
package com.motorola.btc.research.target.pm.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.motorola.btc.research.target.pm.common.TargetView;

/**
 * Represents a generic view that contains a table.
 * 
 * <pre>
 * CLASS:
 * This class represents a view that manages a table. The table is managed by a table viewer. All
 * subclasses of this class must implement the create table method. It also adds a resize capability
 * to the table which means that whenever the view is resized the table is resized too.
 * </pre>
 */
public abstract class TargetTableView extends TargetView
{
    /** The group that represents the parent of the table */
    protected Group group;

    /**
     * The viewer that handles table data
     */
    protected TableViewer viewer;

    /**
     * Creates the visual content of the view.
     */
    @Override
    public void createIndividualControl()
    {
        this.group = this.createGroup(); // creates the group

        this.viewer = this.createTable(); // creates the table

        addResizeCapability(this.viewer.getTable().getColumns()); // add resize capability

        this.performFinalOperations(); // executes any extra operation
    }

    /**
     * Adds the resizing capability to the table so that its columns can be automatically resized.
     * The table is resized when the window or view is maximized or minimized.
     * org.eclipse.swt.widgets.Table is not automatically resized in such cases, so a new size and
     * area for the columns are necessary to be set.
     * 
     * @param columns The columns that will receive the capability.
     */
    private void addResizeCapability(final TableColumn[] columns)
    {
        final Table table = this.viewer.getTable();
        /* manages the resize operations in the table and resizes the columns accordingly */
        group.addControlListener(new ControlAdapter()
        {
            public void controlResized(ControlEvent e)
            {
                // the area of the receiver able to display data
                Rectangle clientArea = group.getClientArea();
                Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                // excludes the borders size
                int width = clientArea.width - 2 * table.getBorderWidth();

                /* verifies if the preferredSize height is greater than the available height */
                if (preferredSize.y > clientArea.height + table.getHeaderHeight())
                {
                    // excludes the vertical scrollbar width
                    Point vertical = table.getVerticalBar().getSize();
                    width -= vertical.x;
                }

                Point previousSize = table.getSize(); // the previous size of the table
                if (previousSize.x > clientArea.width)
                {
                    // decreases the table size so decrease the columns size
                    resizeColumnsWidth(columns, width);
                    table.setSize(clientArea.width, clientArea.height); // decreases the
                    // table size
                }
                else
                {
                    // increases the table size so increase the columns size
                    table.setSize(clientArea.width, clientArea.height);
                    resizeColumnsWidth(columns, width);
                }
            }

            /* resizes the columns widths given the specified width */
            private void resizeColumnsWidth(TableColumn[] columns, int width)
            {
                int ratio = width / columns.length;
                for (int i = 0; i < columns.length; i++)
                {
                    columns[i].setWidth(ratio);
                }
            }
        });
    }

    /**
     * Creates a group to the table.
     * 
     * @return The group that will contain the table.
     */
    protected abstract Group createGroup();

    /**
     * Creates the visual table and sets its layout properties.
     */
    protected abstract TableViewer createTable();

    /**
     * Performs any final operation to conclude the creation of individual controls.
     */
    protected abstract void performFinalOperations();
}
