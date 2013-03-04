/*
 * @(#)TestComparisonPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.editor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.motorola.btc.research.target.cm.controller.ConsistencyManagementController;
import com.motorola.btc.research.target.cm.tcsimilarity.logic.Comparison;
import com.motorola.btc.research.target.cm.tcsimilarity.logic.ComparisonResult;
import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.util.TCGUtil;
import com.motorola.btc.research.target.tcg.util.TestCaseGrid;

/**
 * <pre>
 * CLASS:
 * This class provides a form page with two sections. The first one displays the similarity of
 * new and old test cases. The second one displays shows a selected old test case and a selected
 * new test cases so that the user can compare them.
 *    
 * RESPONSIBILITIES:
 * Provides information about consistency management to the GUI.
 * 
 * </pre>
 */
// INSPECT
public class TestComparisonPage extends FormPage
{

    /**
     * The unique identifier of this form editor.
     */
    public static final String ID = "com.motorola.btc.research.target.tcg.editors.TestComparisonPage";

    /**
     * The comparison object, representing all the comparison results.
     */
    private Comparison comparison;

    /**
     * The comparison results section.
     */
    private Section comparisonResultsSection;

    /**
     * The similarity table, which displays the similarity of the selected test case against the
     * selected test case in the new test cases table.
     */
    private Table similarityTable;

    /**
     * The new test cases table, which displays the new test cases to be compared.
     */
    private Table newTestCasesTable;

    /**
     * The compare test cases section.
     */
    private Section compareTestCasesSection;

    /**
     * The new test case table, containing all the information about the new test case.
     */
    private TestCaseGrid newTestCaseTable;

    /**
     * The new test case table, containing all the information about the old test case.
     */
    private TestCaseGrid oldTestCaseTable;

    /**
     * The SWT Button to generate the test cases after consistency management.
     */
    private Button generateButton;

    /**
     * Map representing the new test cases and their comparison result against the selected old test
     * case.
     */
    private HashMap<TextualTestCase, ComparisonResult> checkedOldTestCases;

    /**
     * The SWT shell to which this page will be added to.
     */
    private Shell shell;

    /**
     * Constructor for the TestComparisonPage.
     * 
     * @param editor The Form Editor where the page is located.
     */
    public TestComparisonPage(FormEditor editor)
    {
        super(editor, ID, "Test Comparison");
        this.comparison = ((ConsistencyManagementEditorInput) editor.getEditorInput())
                .getComparison();
        this.checkedOldTestCases = new HashMap<TextualTestCase, ComparisonResult>();
        this.shell = editor.getSite().getShell();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
     */

    protected void createFormContent(IManagedForm managedForm)
    {
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("Test Comparison");

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        form.getBody().setLayout(layout);

        this.createComparisonResultsSection(form, toolkit);
        this.createCompareTestCasesSection(form, toolkit);
    }

    /**
     * Creates the Comparison Results Section, with two tables. The first represents all the new
     * test cases. The second represents all the old test cases and their similarity to the selected
     * test case in the previous table.
     * 
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     */
    private void createComparisonResultsSection(final ScrolledForm form, FormToolkit toolkit)
    {
        this.comparisonResultsSection = TCGUtil.createSection(form, toolkit, Section.DESCRIPTION
                | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);

        Composite client = TCGUtil.createComposite(toolkit, this.comparisonResultsSection, SWT.WRAP, 3);

        GridLayout layout = (GridLayout) client.getLayout();
        layout.horizontalSpacing = 40;

        this.comparisonResultsSection.setText("Comparison Results");
        this.comparisonResultsSection.setClient(client);
        this.comparisonResultsSection.setExpanded(true);

        Label newTestCasesLabel = new Label(client, SWT.NULL);
        newTestCasesLabel.setText("New Test Cases");
        Label similarityLabel = new Label(client, SWT.NULL);
        similarityLabel.setText("Old Test Cases");

        GridData gridData = new GridData(GridData.VERTICAL_ALIGN_END);
        gridData.horizontalSpan = 2;
        gridData.horizontalAlignment = GridData.FILL;
        similarityLabel.setLayoutData(gridData);

        createNewTestCasesTable(client);
        createSimilarityTable(client);
        createGenerateButton(client);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = 150;
        gd.horizontalSpan = 2;
        this.comparisonResultsSection.setLayoutData(gd);
    }

    /**
     * Creates the SWT button which will be responsible for test suite generation.
     * 
     * @param client The SWT composite in which the button will be created.
     */
    private void createGenerateButton(Composite client)
    {
        generateButton = new Button(client, SWT.NONE);
        generateButton.setText("Generate Test Suite");
        generateButton.addSelectionListener(new SelectionListener()
        {


            public void widgetDefaultSelected(SelectionEvent e)
            {
            }


            public void widgetSelected(SelectionEvent e)
            {
                HashMap<TextualTestCase, TextualTestCase> testCases = new HashMap<TextualTestCase, TextualTestCase>();
                for (TextualTestCase newTestCase : checkedOldTestCases.keySet())
                {
                    ComparisonResult result = checkedOldTestCases.get(newTestCase);
                    TextualTestCase oldTestCase = null;
                    if (result != null)
                    {
                        oldTestCase = checkedOldTestCases.get(newTestCase).getOldTestCase();
                    }
                    testCases.put(newTestCase, oldTestCase);
                }
                try
                {
                    ConsistencyManagementController.getInstance().mergeAndGenerate(testCases);
                    MessageDialog.openInformation(shell, "Consistency Management",
                            "The test cases were merged and generated successfully.");
                    GUIManager.getInstance().refreshViews();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                catch (TargetException e2)
                {
                    e2.printStackTrace();
                }
            }

        });
    }

    /**
     * Creates the Compare Test Cases Section, with two tables. The first represents a new test case
     * selected by the user and the second represents an old test case selected by the user. Both
     * selections are made in the Comparison Results Section.
     * 
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     */
    private void createCompareTestCasesSection(final ScrolledForm form, FormToolkit toolkit)
    {
        this.compareTestCasesSection = TCGUtil.createSection(form, toolkit, Section.DESCRIPTION
                | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);

        Composite client = TCGUtil.createComposite(toolkit, this.compareTestCasesSection, SWT.WRAP, 1);

        this.compareTestCasesSection.setText("Compare Test Cases");
        this.compareTestCasesSection.setClient(client);
        this.compareTestCasesSection.setExpanded(false);

        Label newTestCaseLabel = new Label(client, SWT.NULL);
        newTestCaseLabel.setText("New Test Case");

        this.newTestCaseTable = new TestCaseGrid(client);

        Label formerTestCaseLabel = new Label(client, SWT.NULL);
        formerTestCaseLabel.setText("\nOld Test Case");

        this.oldTestCaseTable = new TestCaseGrid(client);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = 150;
        this.compareTestCasesSection.setLayoutData(gd);
    }

    /**
     * Creates a table with all the new test cases.
     * 
     * @param client The SWT composite in which the button will be created.
     */
    private void createNewTestCasesTable(Composite client)
    {
        this.newTestCasesTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.MULTI);
        this.newTestCasesTable.setHeaderVisible(true);
        this.newTestCasesTable.setLinesVisible(true);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = 100;
        gd.widthHint = 150;
        gd.grabExcessHorizontalSpace = false;
        this.newTestCasesTable.setLayoutData(gd);

        TableColumn tc = new TableColumn(this.newTestCasesTable, SWT.LEFT);
        tc.setText("ID");
        tc.setResizable(true);
        tc.setWidth(150);

        showNewTestCases();

        this.newTestCasesTable.addSelectionListener(new SelectionListener()
        {

            public void widgetDefaultSelected(SelectionEvent e)
            {
            }


            public void widgetSelected(SelectionEvent e)
            {
                showComparison();
                compareTestCasesSection.setExpanded(true);
                showNewTestCase();
            }
        });

    }

    /**
     * Creates a table with all the old test cases and their similarity against the selected test
     * case in the new test cases table.
     * 
     * @param client The SWT composite in which the button will be created.
     */
    private void createSimilarityTable(Composite client)
    {
        this.similarityTable = new Table(client, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.MULTI | SWT.CHECK);
        this.similarityTable.setHeaderVisible(true);
        this.similarityTable.setLinesVisible(true);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = 100;
        gd.widthHint = 220;
        gd.grabExcessHorizontalSpace = false;
        this.similarityTable.setLayoutData(gd);

        TableColumn idColumn = new TableColumn(this.similarityTable, SWT.LEFT);
        idColumn.setText("ID");
        idColumn.setResizable(true);
        idColumn.setWidth(150);

        TableColumn similarityColumn = new TableColumn(this.similarityTable, SWT.LEFT);
        similarityColumn.setText("Similarity");
        similarityColumn.setResizable(true);
        similarityColumn.setWidth(70);

        this.similarityTable.addSelectionListener(new SelectionListener()
        {

            public void widgetDefaultSelected(SelectionEvent e)
            {
            }


            public void widgetSelected(SelectionEvent e)
            {
                boolean checked = ((TableItem) e.item).getChecked();
                if (checked)
                {
                    checkOldTestCase(((TableItem) e.item));
                }
                else
                {
                    unCheckOldTestCase(((TableItem) e.item));
                }
                showOldTestCase();
            }
        });
    }

    /**
     * Unchecks a given table item from the similarity table.
     * 
     * @param unCheckedItem The table item to be unchecked.
     */
    private void unCheckOldTestCase(TableItem unCheckedItem)
    {
        TextualTestCase newTestCase = (TextualTestCase) this.newTestCasesTable.getSelection()[0]
                .getData();
        ComparisonResult checked = this.checkedOldTestCases.get(newTestCase);
        ComparisonResult unChecked = (ComparisonResult) unCheckedItem.getData();
        if (unChecked == checked)
        {
            unCheckedItem.setChecked(true);
        }

    }

    /**
     * Checks a given table item from the similarity table.
     * 
     * @param unCheckedItem The table item to be unchecked.
     */
    private void checkOldTestCase(TableItem checkedItem)
    {
        TableItem[] items = this.similarityTable.getItems();
        for (TableItem i : items)
        {
            if (!i.equals(checkedItem))
            {
                i.setChecked(false);
            }
        }
        TextualTestCase newTestCase = (TextualTestCase) this.newTestCasesTable.getSelection()[0]
                .getData();
        this.checkedOldTestCases.put(newTestCase, (ComparisonResult) checkedItem.getData());
    }

    /**
     * Mounts the New Test Cases Table.
     */
    private void showNewTestCases()
    {
        List<TextualTestCase> newTestCases = comparison.getNewTestCases();

        for (TextualTestCase newTestCase : newTestCases)
        {
            TableItem newItem = new TableItem(this.newTestCasesTable, SWT.NONE);
            String[] text = new String[] { newTestCase.getTcIdHeader() + newTestCase.getId() };
            newItem.setText(text);
            newItem.setData(newTestCase);

            this.checkedOldTestCases.put(newTestCase, null);
        }
    }

    /**
     * Mounts the Similarity Table.
     */
    private void showComparison()
    {
        this.similarityTable.removeAll();

        TextualTestCase newTestCase = (TextualTestCase) this.newTestCasesTable.getSelection()[0]
                .getData();
        List<ComparisonResult> comparisonResults = this.comparison
                .getComparisonResultsByTestCase(newTestCase);
        ComparisonResult selectedItem = this.checkedOldTestCases.get(newTestCase);

        TableItem newItem = new TableItem(this.similarityTable, SWT.NONE);
        newItem.setText(0, "None");

        if (selectedItem == null)
        {
            newItem.setChecked(true);
        }

        for (ComparisonResult comparisonResult : comparisonResults)
        {
            newItem = new TableItem(this.similarityTable, SWT.NONE);

            TextualTestCase oldTestCase = comparisonResult.getOldTestCase();
            double similarity = comparisonResult.getSimilarity();

            newItem.setText(0, oldTestCase.getTcIdHeader() + oldTestCase.getId());
            newItem.setText(1, String.valueOf(similarity) + "%");
            newItem.setData(comparisonResult);

            if (comparisonResult.equals(selectedItem))
            {
                newItem.setChecked(true);
            }

        }
    }

    /**
     * Mounts the New Test Case Table.
     */
    private void showNewTestCase()
    {
        TextualTestCase testCase = (TextualTestCase) this.newTestCasesTable.getSelection()[0]
                .getData();
        this.newTestCaseTable.setTestCase(testCase);
    }

    /**
     * Mounts the Old Test Case Table.
     */
    private void showOldTestCase()
    {
        ComparisonResult comparisonResult = (ComparisonResult) this.similarityTable.getSelection()[0]
                .getData();
        TextualTestCase testCase = comparisonResult.getOldTestCase();
        this.oldTestCaseTable.setTestCase(testCase);
    }

}
