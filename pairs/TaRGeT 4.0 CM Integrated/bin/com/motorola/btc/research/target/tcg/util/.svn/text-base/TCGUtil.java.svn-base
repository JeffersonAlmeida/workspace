/*
 * @(#)TCGUtil.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Mar 25, 2008    LIBpp56482   Initial creation.
 */
package com.motorola.btc.research.target.tcg.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * This class contains some routines that are useful in the Test Case Generation plugin.
 */
public class TCGUtil
{
    /**
     * Represents the gray color.
     */
    public static final RGB GRAY_RGB = new RGB(192, 192, 192);

    /**
     * Represents the color common new test cases.
     */
    public static final RGB COMMON_ITEM_RGB = new RGB(0, 0, 0);

    /**
     * Represents the color for removed test cases.
     */
    public static final RGB REMOVED_ITEM_RGB = new RGB(255, 0, 0);

    /**
     * Represents the color for new test cases.
     */
    public static final RGB ADDED_ITEM_RGB = new RGB(0, 200, 0);

    /**
     * Displays the summary of the test case generation process.
     * 
     * @param shell The shell of the parent window.
     * @param testCasesNumber The number of generated test cases.
     * @param totalTime The generation total time in milliseconds.
     */
    public static void displayTestCaseSummary(Shell shell, int testCasesNumber, long totalTime)
    {
        String message = "Number of generated test cases: " + testCasesNumber + "\n\n";
        long minutes = totalTime / 60000;
        long seconds = (totalTime % 60000) / 1000;
        long milliseconds = totalTime - (minutes * 60000 + seconds * 1000);

        message += "The test cases were generated in ";

        String separator = "";
        if (minutes > 0)
        {
            message += minutes + " minutes";
            separator = ", ";
        }
        if (seconds > 0)
        {
            message += separator + seconds + " seconds";
            separator = ", ";
        }

        if (!separator.equals(""))
        {
            separator = " and ";
        }
        message += separator + milliseconds + " milliseconds.\n";

        MessageDialog.openInformation(shell, "Test Case Generation Summary", message);
    }

    /**
     * Reflows a composite and all the parents up the hierarchy until a ScrolledForm is reached.
     * Thise piece of code was copied from the Section class.
     * 
     * @param s The composite to be reflown.
     */
    public static void reflow(Composite s)
    {
        Composite c = s;
        while (c != null)
        {
            c.setRedraw(false);
            c = c.getParent();
            if (c instanceof ScrolledForm)
            {
                break;
            }
        }
        c = s;
        while (c != null)
        {
            c.layout(true);
            c = c.getParent();
            if (c instanceof ScrolledForm)
            {
                ((ScrolledForm) c).reflow(true);
                break;
            }
        }
        c = s;
        while (c != null)
        {
            c.setRedraw(true);
            c = c.getParent();
            if (c instanceof ScrolledForm)
            {
                break;
            }
        }
    }

    /**
     * This method creates a table that displays a test suite list.
     * 
     * @param parent The composite that will contain the table.
     * @param style The test case style. See <code>Table</code> for details.
     * @return The test case table widget.
     */
    public static Table createTestCaseListTable(Composite parent, int style)
    {
        Table table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL
                | SWT.V_SCROLL | style);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn tc = new TableColumn(table, SWT.LEFT);
        tc.setText("Id");
        tc.setResizable(true);
        tc.setWidth(100);

        tc = new TableColumn(table, SWT.LEFT);
        tc.setText("Description");
        tc.setResizable(true);
        tc.setWidth(400);

        tc = new TableColumn(table, SWT.LEFT);
        tc.setText("Requirements");
        tc.setResizable(true);
        tc.setWidth(200);

        tc = new TableColumn(table, SWT.LEFT);
        tc.setText("UseCases");
        tc.setResizable(true);
        tc.setWidth(200);

        return table;
    }

    /**
     * Adds a new item to a test case table.
     * 
     * @param testCaseTable The test case table to which the item will be added.
     * @param testCase The test case to be added.
     * @return The created table item.
     */
    public static TableItem addTestCaseToTable(Table testCaseTable, TextualTestCase testCase)
    {
        TableItem newItem = new TableItem(testCaseTable, SWT.NONE);
        newItem.setText(new String[] { testCase.getTcIdHeader(), testCase.getObjective(),
                testCase.getRequirements(), testCase.getUseCaseReferences() });

        newItem.setData(testCase);

        return newItem;
    }

    /**
     * Creates a grid layout with default style and a specific number of columns.
     * 
     * @param columns The number of columns.
     * @return A grid layout with a specific number of columns.
     */
    public static GridLayout createGridLayout(int columns)
    {
        GridLayout layout = new GridLayout();
        layout.numColumns = columns;
        return layout;
    }

    /**
     * Creates a composite according to the parameters.
     * 
     * @param toolkit The toolkit used by this form.
     * @param section The composite parent.
     * @param style The composite style.
     * @param columns The number of columns.
     * @return A composite
     */
    public static Composite createComposite(FormToolkit toolkit, Section section, int style,
            int columns)
    {
        Composite client = toolkit.createComposite(section, style);
        client.setLayout(createGridLayout(columns));
        return client;
    }

    /**
     * Creates a Section according to the parameters.
     * 
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     * @param style The section style.
     * @return The section widget.
     */
    public static Section createSection(ScrolledForm form, FormToolkit toolkit, int style)
    {

        Section section = toolkit.createSection(form.getBody(), style);
        section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
        section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

        return section;
    }

    /**
     * Constructs a new instance of GridData according to the parameters.
     * 
     * @param style The GridData style.
     * @param widthHint The preferred width in pixels.
     * @param heightHint The preferred height in pixels.
     * @return
     */
    public static GridData createGridData(int style, int widthHint, int heightHint)
    {
        GridData gd = new GridData(style);
        gd.widthHint = widthHint;
        gd.heightHint = heightHint;
        return gd;
    }
}
