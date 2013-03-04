/*
 * @(#)TCGRequirementsSelectionPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * WRA050    15/08/2006         -        Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerExternalFacade inclusion.
 * dhq348   Jan 10, 2007    LIBkk11577   Refactoring.
 * dhq348   Jan 18, 2007    LIBkk11577   Rework of inspection LX135556.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * dhq348   Aug 20, 2007    LIBmm93130   Modifications according to CR.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 * dhq348   Nov 27, 2007    LIBoo10574   Modifications according to CR.
 * dhq348   Jan 21, 2008    LIBoo10574   Rework after inspection LX229631.
 * wdt022    Mar 17, 2008   LIBpp56482   setSelectedRequirements method added.
 */
package com.motorola.btc.research.target.tcg.wizards;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;

/**
 * Class that mounts the requirements selection page for test case generation.
 * 
 * <pre>
 *   CLASS:
 *   This class creates the interface used to select the requirements that 
 *   will be present in the tests obtained from the test generation.
 *   
 *   RESPONSIBILITIES:
 *   This page allows test case designer to select the requirements used as input 
 *   to test case generation.
 *  
 *   USAGE:
 *   This class is used in TCG Wizard.
 * </pre>
 */
public class TCGRequirementsSelectionPage extends WizardPage implements TCGPage
{
    /**
     * The table that displays the requirements
     */
    private Table reqTable;

    /**
     * The button that selects all requirements
     */
    private Button selectAllButton;

    /**
     * The button that unchecked all requirements
     */
    private Button deselectAllButton;

    /**
     * Sets the page title, description and indicates that it initially will not have the finish
     * button enabled.
     */
    public TCGRequirementsSelectionPage()
    {
        super(" ");
        setTitle("Test Suite Generation by Requirements");
        setDescription("Select requirements to cover.");
        setPageComplete(true);
    }

    /**
     * Creates all visual components given their <code>parent</code>.
     * 
     * @param parent The parent of the components that will be created.
     * @return The main component of the page. It is used to set the control of the wizard.
     */
    public Composite createVisualComponents(Composite parent)
    {
        Composite mainContainer = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        mainContainer.setLayout(layout);
        GridData data = new GridData(GridData.FILL_BOTH);
        mainContainer.setLayoutData(data);
        mainContainer.pack(false);

        this.createRequirementsContainer(mainContainer);
        this.createButtonsContainer(mainContainer);

        /* Setting the page control. Otherwise the page will not be displayed */
        setControl(mainContainer);

        return mainContainer;
    }

    /**
     * Create all GUI controls displayed in the page.
     * 
     * @param parent The parent component of the page.
     */
    public void createControl(Composite parent)
    {
        createVisualComponents(parent);
    }

    /**
     * Creates the requirements container.
     * 
     * @param parent The parent component of the page.
     */
    private void createRequirementsContainer(Composite parent)
    {
        Label label = new Label(parent, SWT.NULL);
        label.setText("Select the input requirements:");

        this.createReqTable(parent);
        this.addTableContents();
    }

    /**
     * Creates the buttons 'select all' and 'deselect all'.
     * 
     * @param parent The parent component of the page.
     */
    private void createButtonsContainer(Composite parent)
    {
        Composite composite1 = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        composite1.setLayout(layout);

        composite1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        this.selectAllButton = new Button(composite1, SWT.PUSH);
        this.selectAllButton.setText("Select all");
        this.selectAllButton.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
                // not implemented
            }

            public void widgetSelected(SelectionEvent e)
            {
                enableAllRequirementsItems(true);
            }
        });

        this.deselectAllButton = new Button(composite1, SWT.PUSH);
        this.deselectAllButton.setText("Deselect all");
        this.deselectAllButton.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
                // not implemented
            }

            public void widgetSelected(SelectionEvent e)
            {
                enableAllRequirementsItems(false);
            }
        });

        Composite composite2 = new Composite(parent, SWT.NONE);
        layout = new GridLayout();
        layout.numColumns = 1;
        composite2.setLayout(layout);
        composite2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        if (this.reqTable.getItems().length == 0)
        {
            this.enableAllComponents(false);
        }
    }

    /**
     * Enables or disables the components.
     * 
     * @param flag Indicates if the components have to be enabled or disabled.
     */
    private void enableAllComponents(boolean flag)
    {
        this.deselectAllButton.setEnabled(flag);
        this.selectAllButton.setEnabled(flag);
        this.reqTable.setEnabled(flag);
    }

    /**
     * Enables or disables all requirements items.
     * 
     * @param flag Indicates if all requirements have to be enabled or disabled.
     */
    private void enableAllRequirementsItems(boolean flag)
    {
        TableItem[] items = this.reqTable.getItems();
        for (int i = 0; i < items.length; i++)
        {
            items[i].setChecked(flag);
        }
    }

    /**
     * This method is used to abstract over reqTableCreation
     * 
     * @param composite The parent component of the requirements table.
     */
    private void createReqTable(Composite composite)
    {
        reqTable = new Table(composite, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.H_SCROLL
                | SWT.V_SCROLL);
        reqTable.setHeaderVisible(true);
        reqTable.setLinesVisible(true);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 3;
        gridData.heightHint = 150;
        reqTable.setLayoutData(gridData);

        reqTable.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            public void widgetSelected(SelectionEvent e)
            {
                TableItem[] items = reqTable.getItems();
                for (int i = 0; i < items.length; i++)
                {
                    if (items[i].getChecked())
                    {
                        break;
                    }
                }
            }
        });

        createTableColumn(reqTable, SWT.LEFT, "Requirement Code", 300);
    }

    /**
     * This method is used to abstract over table column creation.
     * 
     * @param table The table in which the column will be created.
     * @param style The style of the column.
     * @param title The title of the column.
     * @param width The initial width of the column.
     */
    private void createTableColumn(Table table, int style, String title, int width)
    {
        TableColumn tc = new TableColumn(table, style);
        tc.setText(title);
        tc.setResizable(true);
        tc.setWidth(width);
    }

    /**
     * Add the <code>reqTable</code> contents.
     */
    private void addTableContents()
    {
        for (String req : ProjectManagerExternalFacade.getInstance()
                .getAllReferencedRequirementsOrdered())
        {
            TableItem item = new TableItem(reqTable, SWT.NONE);
            item.setText(new String[] { req });
        }
    }

    /**
     * Returns true if it is necessary to generate all test cases.
     * 
     * @return <b>true</b> if it is necessary to generate all usecases.
     */
    public boolean anyRequirementSelected()
    {
        return getSelectedRequirements().size() > 0 || hasSelectedAllRequirements();
    }

    /**
     * Checks if all requirements were selected. Checks if the number of selected requirements is
     * the same of the requirements listed in <code>reqTable</code>.
     * 
     * @return <code>true</code> if all requirements were selected or <code>false</code>
     * otherwise.
     */
    public boolean hasSelectedAllRequirements()
    {
        /* represents all distinct items */
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (TableItem item : this.reqTable.getItems())
        {
            if (map.get(item.getText()) == null)
            {
                map.put(item.getText(), 1);
            }
            else
            {
                map.put(item.getText(), map.remove(item.getText()) + 1);
            }
        }
        return (map.size() == this.getSelectedRequirements().size()) && (map.size() != 0);
    }

    /**
     * Returns the list of the selected requirements.
     * 
     * @return The list of the selected requirements.
     */
    public Set<String> getSelectedRequirements()
    {
        Set<String> selectedRequirements = new HashSet<String>();
        TableItem[] items = this.reqTable.getItems();
        for (int i = 0; i < items.length; i++)
        {
            if (items[i].getChecked())
            {
                selectedRequirements.add(items[i].getText());
            }
        }
        return selectedRequirements;
    }

    /**
     * Sets as selected all the requirements passed as parameter.
     * 
     * @param The list of requirements that will be selected.
     */
    public void setSelectedRequirements(Set<String> requirements)
    {
        TableItem[] items = this.reqTable.getItems();
        for (int i = 0; i < items.length; i++)
        {
            items[i].setChecked(requirements.contains(items[i].getText()));
        }
    }

}
