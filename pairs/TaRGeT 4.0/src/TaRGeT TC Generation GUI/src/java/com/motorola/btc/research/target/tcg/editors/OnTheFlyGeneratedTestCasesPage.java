/*
 * @(#)OnTheFlyGeneratedTestCasesPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Mar 19, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 * tnd783    Aug 25, 2008	LIBqq51204   Changes in method createHeaderSection.
 */
package com.motorola.btc.research.target.tcg.editors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.tcg.TCGActivator;
import com.motorola.btc.research.target.tcg.TCGUtil;
import com.motorola.btc.research.target.tcg.controller.TestCaseGeneratorController;
import com.motorola.btc.research.target.tcg.editors.actions.CancelTestCaseExclusionAction;
import com.motorola.btc.research.target.tcg.editors.actions.CancelTestCaseInclusionAction;
import com.motorola.btc.research.target.tcg.editors.actions.ExcludeTestCaseAction;
import com.motorola.btc.research.target.tcg.editors.actions.IncludeTestCaseAction;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.filters.TestSuiteFilterAssembler;

/**
 * <pre>
 * CLASS:
 * This class provides a form page with three sections: 
 *      - Header -> Provides a GUI to set which test cases shall be exhibited (common, added, removed) and
 *                  to select, load, and save a filter configuration.    
 *      - Test Cases Table -> Exhibits the test suite according to some filter configuration
 *      - Test Case -> Exhibits a selected test case
 * 
 * RESPONSIBILITIES:
 * Exhibits the test suite according some filter configuration. Compares two test suites, indicating 
 * which test cases were removed or added. Exhibits a selected test case.
 * 
 * </pre>
 */
public class OnTheFlyGeneratedTestCasesPage extends FormPage
{

    /**
     * The unique identifier of this form page.
     */
    public static final String ID = "com.motorola.btc.research.target.tcg.editors.OnTheFlyGeneratedTestCasesPage";

    /**
     * The table with the test cases generated according to the selected filter configuration.
     */
    private Table generatedTestCasesTable;

    /**
     * The table that exhibits a test case according to a predefined format.
     */
    private TestCaseTable testCaseTable;

    /**
     * The form page that provides filter configuration.
     */
    private OnTheFlyTestSelectionPage selectionPage;

    /**
     * The component created on header section.
     */
    private OnTheFlyHeader header;

    /**
     * The form page that exhibits the list of test cases always excluded or included in the final
     * test suite.
     */
    private OnTheFlyTestExclusionPage exclusionInclusionPage;

    /**
     * Creates a generated test cases form page.
     * 
     * @param editor The editor where this page will be added.
     * @param selectionPage The form page that provides filter configuration.
     * @param exclusionInclusionPage The form page that exhibits the list of test cases always
     * excluded or included in the final test suite.
     */
    public OnTheFlyGeneratedTestCasesPage(FormEditor editor,
            OnTheFlyTestSelectionPage selectionPage,
            OnTheFlyTestExclusionPage exclusionInclusionPage)
    {
        super(editor, ID, "Generated Test Cases");

        this.selectionPage = selectionPage;
        this.exclusionInclusionPage = exclusionInclusionPage;
    }

    /**
     * Retrieves the editor that contains this page.
     * 
     * @return The editor where this page was added.
     */
    public OnTheFlyMultiPageEditor getEditor()
    {
        return (OnTheFlyMultiPageEditor) super.getEditor();
    }

    /**
     * Creates content in the form hosted in this page.
     * 
     * @param managedForm The form hosted in this page.
     */
    protected void createFormContent(IManagedForm managedForm)
    {
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("Test Cases Viewer");
        form.getBody().setLayout(new GridLayout());

        try
        {
            this.createHeaderSection(form, toolkit);
            this.createGeneratedTestCasesSection(form, toolkit);
            this.createTestCaseTableSection(form, toolkit);
            this.createContextMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Creates the context menu for <code>generatedTestCasesTable</code>
     */
    private void createContextMenu()
    {
        MenuManager menuManager = new MenuManager(
                "com.motorola.btc.research.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage");
        ISelectionProvider selectionProvider = new TableViewer(this.generatedTestCasesTable);

        menuManager
                .add(new ExcludeTestCaseAction(
                        "com.motorola.btc.research.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.ExcludeTestCase",
                        selectionProvider, this.exclusionInclusionPage));

        menuManager
                .add(new IncludeTestCaseAction(
                        "com.motorola.btc.research.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.IncludeTestCase",
                        selectionProvider, this.exclusionInclusionPage));

        menuManager
                .add(new CancelTestCaseExclusionAction(
                        "com.motorola.btc.research.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.CancelTestCaseExclusionAction",
                        selectionProvider, this.exclusionInclusionPage));

        menuManager
                .add(new CancelTestCaseInclusionAction(
                        "com.motorola.btc.research.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.CancelTestCaseInclusionAction",
                        selectionProvider, this.exclusionInclusionPage));

        Menu contextMenu = menuManager.createContextMenu(this.generatedTestCasesTable);
        this.generatedTestCasesTable.setMenu(contextMenu);
        this
                .getSite()
                .registerContextMenu(
                        "com.motorola.btc.research.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage",
                        menuManager, selectionProvider);
    }

    /**
     * Creates the header section. This section provides a GUI to set which test cases shall be
     * exhibited (common, added, removed) and to select, load, and save a filter configuration.
     * 
     * @param form The form widget managed by this form
     * @param toolkit The toolkit used by this form
     */
    
    private void createHeaderSection(final ScrolledForm form, FormToolkit toolkit)
    {
        Section section = OnTheFlyUtil.createSection(form, toolkit, Section.EXPANDED
                | Section.TITLE_BAR);

        Composite client = OnTheFlyUtil.createComposite(toolkit, section, SWT.NONE, 2);

        header = new OnTheFlyHeader(client, this.getEditor());

        header.addCheckBoxesListener(new SelectionListener()
        {
            @Override 
            /**
             * Sent when default selection occurs in the control.
             * @param e an event containing information about the default selection
             */
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }
            /**
             * Sent when selection occurs in the control.
             * @param e an event containing information about the selection
             */
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                updateTestCaseListTable();
            }

        });

        header.addFormerFilterSelectionListener(new SelectionListener()
        {

            @Override
            /**
             * Sent when default selection occurs in the control.
             * @param e an event containing information about the default selection
             */
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            @Override
            /**
             * Sent when selection occurs in the control.
             * @param e an event containing information about the selection
             */
            public void widgetSelected(SelectionEvent e)
            {
                updateTestCaseListTable();
            }

        });
        header.addLoadFilterButtonListener(new SelectionListener()
        {

            @Override
            /**
             * Sent when default selection occurs in the control.
             * @param e an event containing information about the default selection
             */
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            @Override
            /**
             * Sent when selection occurs in the control.
             * @param e an event containing information about the selection
             */
            public void widgetSelected(SelectionEvent e)
            {

                TestSuiteFilterAssembler assembler = header.getFormerAssembler();
                selectionPage.setFilterAssembler(assembler);
                TestSuite<TestCase<FlowStep>> wholeTestSuite = getEditor().getRawTestSuite();
                TestSuite<TestCase<FlowStep>> testSuite = selectionPage.getCurrentTestSuite();
                updateTestCaseListTable(wholeTestSuite, testSuite, testSuite);
            }

        });

        header.addSaveFilterButtonListener(new SelectionListener()
        {

            @Override
            /**
             * Sent when default selection occurs in the control.
             * @param e an event containing information about the default selection
             */
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            @Override
            /**
             * Sent when selection occurs in the control.
             * @param e an event containing information about the selection
             */
            public void widgetSelected(SelectionEvent e)
            {
                TestSuiteFilterAssembler assembler = selectionPage.getFilterAssembler();
                header.addFilter(assembler);
            }

        });

        section.setClient(client);
        section.setExpanded(true);
        section.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    }

    /**
     * Creates the generated test cases section. This section exhibits the test suite according to
     * some filter configuration.
     * 
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     */
    private void createGeneratedTestCasesSection(final ScrolledForm form, FormToolkit toolkit)
    {
        Section section = OnTheFlyUtil.createSection(form, toolkit, Section.TITLE_BAR
                | Section.TWISTIE | Section.EXPANDED);

        Composite client = OnTheFlyUtil.createComposite(toolkit, section, SWT.NONE, 2);

        this.generatedTestCasesTable = this.createTestCaseListTable(client);
        TestSuite<TestCase<FlowStep>> testSuite = this.getEditor().getRawTestSuite();
        this.updateTestCaseListTable(testSuite, testSuite, testSuite);

        this.createSaveTestSuiteButton(client);

        section.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        section.setText("Generated Test Cases");
        section.setClient(client);
        section.setExpanded(true);
    }

    /**
     * Creates the test case table section. This section exhibits a selected test case.
     * 
     * @param form The form widget managed by this form
     * @param toolkit The toolkit used by this form
     */
    private void createTestCaseTableSection(final ScrolledForm form, FormToolkit toolkit)
    {
        Section section = OnTheFlyUtil.createSection(form, toolkit, Section.TITLE_BAR
                | Section.TWISTIE | Section.COMPACT);

        Composite client = OnTheFlyUtil.createComposite(toolkit, section, SWT.NONE, 1);

        this.testCaseTable = new TestCaseTable(client);
        this.testCaseTable.setTestCase(null);

        section.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        section.setText("Selected Test Case");
        section.setClient(client);
        section.setExpanded(true);
    }

    /**
     * Creates the save current TestSuite button and sets its listeners.
     * 
     * @param parent The parent composite.
     */
    private void createSaveTestSuiteButton(Composite parent)
    {
        GridData gridData = OnTheFlyUtil.createGridData(SWT.NONE, 150, 25);
        Button saveCurrentTestSuite = new Button(parent, SWT.PUSH);
        saveCurrentTestSuite.setText("Save Test Suite");
        saveCurrentTestSuite
                .setToolTipText("Saves the test suite generated with the filter specified in the Test Selection Page");
        saveCurrentTestSuite.setLayoutData(gridData);
        saveCurrentTestSuite.addSelectionListener(new SelectionListener()
        {

            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            public void widgetSelected(SelectionEvent e)
            {
                TestSuite<TestCase<FlowStep>> testSuite = selectionPage.getCurrentTestSuite();

                List<TextualTestCase> textualTestCases = new ArrayList<TextualTestCase>();
                for (TestCase<FlowStep> testCase : testSuite.getTestCases())
                {
                    textualTestCases.add(getEditor().getTextualTestCase(testCase.getId()));
                }

                try
                {
                    long beforeTime = System.currentTimeMillis();
                    TestCaseGeneratorController.getInstance().writeTestSuiteFile(
                            new TestSuite<TextualTestCase>(textualTestCases, testSuite.getName()));
                    long afterTime = System.currentTimeMillis();
                    TCGUtil.displayTestCaseSummary(getEditor().getSite().getShell(),
                            textualTestCases.size(), afterTime - beforeTime);
                    GUIManager.getInstance().refreshViews();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                    MessageDialog.openError(getEditor().getSite().getShell(), "Error",
                            "Error while writing the excel file. " + e1.getMessage());
                    TCGActivator.logError(0, this.getClass(),
                            "Error while writing the Excel file. " + e1.getMessage(), e1);
                }
                catch (TargetException e2)
                {
                    MessageDialog.openError(getEditor().getSite().getShell(), "Error", e2
                            .getMessage());
                    TCGActivator.logError(0, this.getClass(), "Error while refreshing views. "
                            + e2.getMessage(), e2);
                    e2.printStackTrace();
                }

            }
        });
    }

    /**
     * Creates the visual components to exhibit the list of test cases. The
     * <code>generatedTestCasesTable</code> is filled.
     * 
     * @param The composite parent.
     * @return A table filled with test cases
     */
    private Table createTestCaseListTable(Composite parent)
    {

        Composite mainContainer = new Composite(parent, SWT.WRAP);

        mainContainer.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
        mainContainer.setLayoutData(gridData);
        mainContainer.pack(false);

        Table table = TCGUtil.createTestCaseListTable(mainContainer, SWT.MULTI);
        gridData = OnTheFlyUtil.createGridData(GridData.FILL_BOTH, SWT.DEFAULT, 200);
        table.setLayoutData(gridData);

        table.addSelectionListener(new SelectionListener()
        {

            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            public void widgetSelected(SelectionEvent e)
            {
                TextualTestCase testCase = null;
                if (generatedTestCasesTable.getSelectionCount() > 0)
                {
                    testCase = (TextualTestCase) generatedTestCasesTable.getSelection()[0]
                            .getData();
                }

                testCaseTable.setTestCase(testCase);
                TCGUtil.reflow(testCaseTable.getGrid());
            }

        });

        return table;
    }


    /**
     * Updates the table with the test cases generated under filter settings.
     */
    public void updateTestCaseListTable()
    {
        TestSuite<TestCase<FlowStep>> wholeTestSuite = this.getEditor().getRawTestSuite();

        TestSuiteFilterAssembler formerAssembler = header.getFormerAssembler();
        TestSuite<TestCase<FlowStep>> formerTestSuite = formerAssembler.assemblyFilter().filter(
                this.getEditor().getRawTestSuite());

        TestSuite<TestCase<FlowStep>> currentTestSuite = selectionPage.getCurrentTestSuite();
        this.updateTestCaseListTable(wholeTestSuite, currentTestSuite, formerTestSuite);
    }

    /**
     * Updates the table with the generated test cases. The color of each row changes according to
     * the test case status: added, common or removed.
     * 
     * @param completeTestSuite The whole test suite.
     * @param currentTestSuite The test suite generated according to the current filter settings.
     * @param formerTestSuite The test suite generated according to the selected filter
     * configuration.
     */
    private void updateTestCaseListTable(TestSuite<TestCase<FlowStep>> completeTestSuite,
            TestSuite<TestCase<FlowStep>> currentTestSuite,
            TestSuite<TestCase<FlowStep>> formerTestSuite)
    {

        HashSet<TestCase<FlowStep>> currentMap = new HashSet<TestCase<FlowStep>>(currentTestSuite
                .getTestCases());
        HashSet<TestCase<FlowStep>> formerMap = new HashSet<TestCase<FlowStep>>(formerTestSuite
                .getTestCases());

        this.verifyExcludedIncludedTestCases(currentMap);
        this.generatedTestCasesTable.setItemCount(0);

        int common = 0;
        int added = 0;
        int removed = 0;
        for (TestCase<FlowStep> testCase : completeTestSuite.getTestCases())
        {
            RGB itemRGB = null;
            if (currentMap.contains(testCase) && formerMap.contains(testCase))
            {
                common++;
                if (header.isCommonTCCheckBoxChecked())
                {
                    itemRGB = OnTheFlyUtil.COMMON_ITEM_RGB;
                }
            }
            else if (currentMap.contains(testCase) && !formerMap.contains(testCase))
            {
                added++;
                if (header.isAddedTCCheckBoxChecked())
                {
                    itemRGB = OnTheFlyUtil.ADDED_ITEM_RGB;
                }
            }
            else if (!currentMap.contains(testCase) && formerMap.contains(testCase))
            {
                removed++;
                if (header.isRemovedTCCheckBoxChecked())
                {
                    itemRGB = OnTheFlyUtil.REMOVED_ITEM_RGB;
                }
            }

            if (itemRGB != null)
            {
                TableItem item = new TableItem(this.generatedTestCasesTable, SWT.NONE);
                item.setForeground(new Color(item.getDisplay(), itemRGB));
                TextualTestCase textualTC = this.getEditor().getTextualTestCase(testCase.getId());
                item.setText(0, textualTC.getStrId());
                item.setText(1, textualTC.getObjective());
                item.setText(2, textualTC.getRequirements());
                item.setText(3, textualTC.getUseCaseReferences());
                item.setData(textualTC);
            }
        }

        header.setCheckBoxesText(added, common, removed);
    }

    /**
     * Filters the <code>testSuite</code> removing or adding test cases according to the lists of
     * excluded and included test cases.
     * 
     * @param testSuite The test suite that will be filtered.
     */
    private void verifyExcludedIncludedTestCases(Set<TestCase<FlowStep>> testSuite)
    {
        for (TextualTestCase testCase : this.exclusionInclusionPage.getExcludedList())
        {
            TestCase<FlowStep> rawTestCase = this.getEditor().getRawTestCase(testCase.getId());
            testSuite.remove(rawTestCase);
        }

        for (TextualTestCase testCase : this.exclusionInclusionPage.getIncludedList())
        {
            if (!testSuite.contains(testCase))
            {
                testSuite.add(this.getEditor().getRawTestCase(testCase.getId()));
            }
        }
    }

    /**
     * @Override
     * @see IWorkbenchPart#setFocus()
     */
    public void setFocus()
    {
        super.setFocus();
        this.updateTestCaseListTable();
    }

    /**
     * Gets the <code>generatedTestCasesTable</code> field.
     * 
     * @return The table of generated test cases.
     */
    public Table getGeneratedTestCasesTable()
    {
        return this.generatedTestCasesTable;
    }
}
