/*
 * @(#)OnTheFlyTestExclusionPage.java
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
 * wdt022    Apr 7, 2008    LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 * pvcv      Apr 01, 2009				 Internationalization support
 * lmn3      Jul 03, 2009				 Changes to disable the permanent inclusion/exclusion menu when no test case is selected.
 */
package br.ufpe.cin.target.onthefly.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyUtil;
import br.ufpe.cin.target.onthefly.editors.actions.CancelTestCaseExclusionAction;
import br.ufpe.cin.target.onthefly.editors.actions.CancelTestCaseInclusionAction;
import br.ufpe.cin.target.onthefly.editors.actions.ExcludeTestCaseAction;
import br.ufpe.cin.target.onthefly.editors.actions.IncludeTestCaseAction;
import br.ufpe.cin.target.pm.util.GUIUtil;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.util.TCGUtil;


/**
 * <pre>
 * CLASS:
 * This class provides a form page with two sections: 
 *      - Included Test Cases -&gt; Exhibits the test cases that will always be included in the final test suite.
 *      - Excluded Test Cases -&gt; Exhibits the test cases that will always be excluded from the final test suite.
 *      
 * 
 * RESPONSIBILITIES:
 * Exhibits the test cases that will always be included in or removed from the final test suite.
 * 
 * </pre>
 */
public class OnTheFlyTestExclusionPage extends FormPage
{

    /**
     * The unique identifier of this form page.
     */
    public static final String ID = "br.ufpe.cin.target.tcg.editors.OnTheFlyTestExclusionPage";

    /**
     * The table that contains the test cases that will always be included in the final test suite.
     */
    private Table includedTestCaseTable;

    /**
     * The table that contains the test cases that will always be excluded from the final test
     * suite.
     */
    private Table excludedTestCaseTable;

    /**
     * The list with the test cases that will always be included in the final test suite.
     */
    private List<TextualTestCase> includedTestCaseList;

    /**
     * The list with the test cases that will always be excluded from the final test suite.
     */
    private List<TextualTestCase> excludedTestCaseList;

    private Section section;

    /**
     * Creates a included/excluded form page.
     * 
     * @param editor The editor where this page will be added.
     */
    public OnTheFlyTestExclusionPage(FormEditor editor)
    {
        super(editor, ID, Properties.getProperty("exclude_include_specific_test_cases"));
        this.includedTestCaseList = new ArrayList<TextualTestCase>();
        this.excludedTestCaseList = new ArrayList<TextualTestCase>();
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
        form.setText(Properties.getProperty("exclude_include_test_cases"));

        form.getBody().setLayout(new GridLayout());

        this.includedTestCaseTable = this
                .createTestCaseTableSection(
                        form,
                        toolkit,
                        Properties.getProperty("included_test_cases"),
                        Properties
                                .getProperty("the_following_test_cases_will_always_be_included_into_the_final_test_suite"));

        GridData gridData = OnTheFlyUtil.createGridData(GridData.FILL_BOTH, SWT.DEFAULT, 200);
        this.includedTestCaseTable.setLayoutData(gridData);

        this.excludedTestCaseTable = this
                .createTestCaseTableSection(
                        form,
                        toolkit,
                        Properties.getProperty("excluded_test_cases"),
                        Properties
                                .getProperty("the_following_test_cases_will_always_be_excluded_into_the_final_test_suite"));
        gridData = OnTheFlyUtil.createGridData(GridData.FILL_BOTH, SWT.DEFAULT, 200);
        this.excludedTestCaseTable.setLayoutData(gridData);

        this.fillTableFromList();

        this.createContextMenu();

        TCGUtil.reflow(this.includedTestCaseTable);
        TCGUtil.reflow(this.excludedTestCaseTable);

        GUIUtil.addListenerToAllObjects(form, section);
    }

    /**
     * Creates the context menu for <code>includedTestCaseTable</code> and
     * <code>excludedTestCaseTable</code>.
     */
    private void createContextMenu()
    {
        // Creating context menu for includedTestCaseTable
        MenuManager menuManager = new MenuManager(
                "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.IncludedTestCaseTable");
        ISelectionProvider selectionProvider = new TableViewer(this.includedTestCaseTable);

        // Exclude test case action
        ExcludeTestCaseAction excludeTestCaseAction = new ExcludeTestCaseAction(
                "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.ExcludeTestCase",
                selectionProvider, this);
        excludeTestCaseAction.setEnabled(!this.includedTestCaseList.isEmpty());

        menuManager.add(excludeTestCaseAction);

        // Cancel test case inclusion action
        CancelTestCaseInclusionAction cancelTestCaseInclusionAction = new CancelTestCaseInclusionAction(
                "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.CancelTestCaseInclusionAction",
                selectionProvider, this);
        cancelTestCaseInclusionAction.setEnabled(!this.includedTestCaseList.isEmpty());

        menuManager.add(cancelTestCaseInclusionAction);

        // Creating context menu for excludedTestCaseTable
        Menu contextMenu = menuManager.createContextMenu(this.includedTestCaseTable);
        this.includedTestCaseTable.setMenu(contextMenu);
        this
                .getSite()
                .registerContextMenu(
                        "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.IncludedTestCaseTable",
                        menuManager, selectionProvider);

        menuManager = new MenuManager(
                "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.ExcludedTestCaseTable");
        selectionProvider = new TableViewer(this.excludedTestCaseTable);

        // Include test case action
        IncludeTestCaseAction includeTestCaseAction = new IncludeTestCaseAction(
                "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.IncludeTestCase",
                selectionProvider, this);
        includeTestCaseAction.setEnabled(!this.excludedTestCaseList.isEmpty());

        menuManager.add(includeTestCaseAction);

        // Cancel test case exclusion action
        CancelTestCaseExclusionAction cancelTestCaseExclusionAction = new CancelTestCaseExclusionAction(
                "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.CancelTestCaseExclusionAction",
                selectionProvider, this);
        cancelTestCaseExclusionAction.setEnabled(!this.excludedTestCaseList.isEmpty());

        menuManager.add(cancelTestCaseExclusionAction);

        contextMenu = menuManager.createContextMenu(this.excludedTestCaseTable);
        this.excludedTestCaseTable.setMenu(contextMenu);
        this
                .getSite()
                .registerContextMenu(
                        "br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTestExclusionPage.ExcludedTestCaseTable",
                        menuManager, selectionProvider);
    }

    /**
     * Creates a section that exhibits a test case table.
     * 
     * @param form The form widget managed by this form
     * @param toolkit The toolkit used by this form
     * @param text The section title.
     * @param description The section description.
     * @return A <code>Table</code> filled with test cases.
     */
    private Table createTestCaseTableSection(final ScrolledForm form, FormToolkit toolkit,
            String text, String description)
    {
        Table result = null;
        this.section = OnTheFlyUtil.createSection(form, toolkit, Section.DESCRIPTION
                | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);

        Composite client = OnTheFlyUtil.createComposite(toolkit, section, SWT.NONE, 1);

        section.setText(text);
        section.setDescription(description);
        section.setClient(client);
        section.setExpanded(true);

        GridData gd = new GridData(GridData.FILL_BOTH);

        result = TCGUtil.createTestCaseListTable(client, SWT.MULTI);
        result.setLayoutData(gd);

        gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        section.setLayoutData(gd);

        return result;
    }

    /**
     * Fills in included and exclude test cases tables according to included and excluded test cases
     * lists.
     */
    private void fillTableFromList()
    {
        for (TextualTestCase tc : this.includedTestCaseList)
        {
            TCGUtil.addTestCaseToTable(this.includedTestCaseTable, tc);
        }
        for (TextualTestCase tc : this.excludedTestCaseList)
        {
            TCGUtil.addTestCaseToTable(this.excludedTestCaseTable, tc);
        }
    }

    /**
     * Adds a test case in the excluded test cases list.
     * 
     * @param testCase The test case that will be added.
     */
    public void addTestCaseToExclusionList(TextualTestCase testCase)
    {
        if (this.excludedTestCaseTable != null)
        {
            TCGUtil.addTestCaseToTable(this.excludedTestCaseTable, testCase);
        }
        this.excludedTestCaseList.add(testCase);
        this.cancelTestCaseInclusion(testCase);
    }

    /**
     * Adds a test case in the included test cases list.
     * 
     * @param testCase The test case that will be added.
     */
    public void addTestCaseToInclusionList(TextualTestCase testCase)
    {
        if (this.includedTestCaseTable != null)
        {
            TCGUtil.addTestCaseToTable(this.includedTestCaseTable, testCase);
        }
        this.includedTestCaseList.add(testCase);
        this.cancelTestCaseExclusion(testCase);
    }

    /**
     * Verifies if a test case is in the included test cases list.
     * 
     * @param testCase The test case that will be verified.
     * @return True if the test case is in the included test cases list, false otherwise.
     */
    public boolean isTestCaseIncluded(TextualTestCase testCase)
    {
        return this.includedTestCaseList.indexOf(testCase) >= 0;
    }

    /**
     * Verifies if a test case is in the excluded test cases list.
     * 
     * @param testCase The test case that will be verified.
     * @return True if the test case is in the excluded test cases list, false otherwise.
     */
    public boolean isTestCaseExcluded(TextualTestCase testCase)
    {
        return this.excludedTestCaseList.indexOf(testCase) >= 0;
    }

    /**
     * Removes a test case from the included test cases list.
     * 
     * @param testCase The test case that will be removed.
     */
    public void cancelTestCaseInclusion(TextualTestCase testCase)
    {
        int index = this.includedTestCaseList.indexOf(testCase);
        if (index >= 0)
        {
            if (includedTestCaseTable != null)
            {
                this.includedTestCaseTable.remove(index);
            }
            this.includedTestCaseList.remove(index);
        }
    }

    /**
     * Removes a test case from the excluded test cases list.
     * 
     * @param testCase The test case that will be removed.
     */
    public void cancelTestCaseExclusion(TextualTestCase testCase)
    {
        int index = this.excludedTestCaseList.indexOf(testCase);
        if (index >= 0)
        {
            if (excludedTestCaseTable != null)
            {
                this.excludedTestCaseTable.remove(index);
            }
            this.excludedTestCaseList.remove(index);

        }
    }

    /**
     * Gets the list of test cases that will always be included in the test suite.
     * 
     * @return The list of included test cases.
     */
    public List<TextualTestCase> getIncludedList()
    {
        return this.includedTestCaseList;
    }

    /**
     * Gets the list of test cases that will always be excluded from the test suite.
     * 
     * @return The list of excluded test cases.
     */
    public List<TextualTestCase> getExcludedList()
    {
        return this.excludedTestCaseList;
    }
}
