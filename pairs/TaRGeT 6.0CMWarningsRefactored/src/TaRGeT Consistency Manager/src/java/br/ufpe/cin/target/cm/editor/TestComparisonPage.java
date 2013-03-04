/*
 * @(#)TestComparisonPage.java
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
 * tnd783   07/07/2008    LIBhh00000     Initial creation.
 * fsf2     26/06/2009                   Changed colors of the cells.
 * fsf2     03/07/2009                   Added legends.
 */
package br.ufpe.cin.target.cm.editor;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import br.ufpe.cin.target.tcg.controller.TestCaseGeneratorController;
import br.ufpe.cin.target.tcg.extensions.ConsistencyManagerExtension;
import br.ufpe.cin.target.tcg.extensions.ConsistencyManagerExtensionFactory;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.util.TCGUtil;
import br.ufpe.cin.target.tcg.util.TestCaseGrid;


import br.ufpe.cin.target.cm.controller.ConsistencyManagementController;
import br.ufpe.cin.target.cm.tcsimilarity.logic.Comparison;
import br.ufpe.cin.target.cm.tcsimilarity.logic.ComparisonResult;
import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.util.GUIUtil;

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
public class TestComparisonPage extends FormPage
{
    /**
     * The unique identifier of this form editor.
     */
    public static final String ID = "br.ufpe.cin.target.tcg.editors.TestComparisonPage";

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
    private TestCaseGrid newTestCaseGrid;

    /**
     * The new test case table, containing all the information about the old test case.
     */
    private TestCaseGrid oldTestCaseGrid;

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
     * The next new ID.
     */
    private Text textNextID;

    /**
     * Button to change the next new ID.
     */
    private Button buttonChangeNextID;

    /**
     * Percentage of the most similar test case.
     */
    private Spinner firstPercent;

    /**
     * Percentage of the second most similar test case.
     */
    private Spinner secondPercent;

    /**
     * Text with the old test case information.
     */
    private Text textOldTestCase;

    /**
     * Button to change the file to the comparison.
     */
    private Button buttonChangeFile;

    /**
     * The last suite used in the comparison.
     */
    private List<TextualTestCase> lastNewTestSuiteUsedInComparison;

    /**
     * The color to paint new test cases in "New Test Cases" table.
     */
    public static final RGB NEW_TEST_CASE_COLOR = new RGB(255, 209, 179);;

    /**
     * Constructor for the TestComparisonPage.
     * 
     * @param editor The Form Editor where the page is located.
     */
    public TestComparisonPage(FormEditor editor)
    {
        super(editor, ID, Properties.getProperty("test_comparison"));
        this.comparison = ((ConsistencyManagementEditorInput) editor.getEditorInput())
        .getComparison();
        this.checkedOldTestCases = new HashMap<TextualTestCase, ComparisonResult>();
        this.shell = editor.getSite().getShell();
        this.lastNewTestSuiteUsedInComparison = TestCaseGeneratorController.getInstance().getLastSuiteGenerated();
    }

    /**
     * Refreshes the Consistency Manager editor.
     */
    public void refresh()
    {
        try
        {
            if(!this.lastNewTestSuiteUsedInComparison.equals(TestCaseGeneratorController.getInstance().getLastSuiteGenerated()))
            {
                ((ConsistencyManagementEditorInput) this.getEditorInput()).refresh();

                this.comparison = ((ConsistencyManagementEditorInput) this.getEditorInput()).getComparison();

                this.checkedOldTestCases = new HashMap<TextualTestCase, ComparisonResult>();

                this.showNewTestCases();

                this.similarityTable.removeAll();

                this.oldTestCaseGrid.setTestCase(null, null, false, null);
                this.newTestCaseGrid.setTestCase(null, null, false, null);

                this.lastNewTestSuiteUsedInComparison = TestCaseGeneratorController.getInstance().getLastSuiteGenerated();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            MessageDialog.openError(this.shell, Properties.getProperty("error_while_comparing_test_cases"),
                    Properties.getProperty("test_comparison"));
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
     */
    
    protected void createFormContent(IManagedForm managedForm)
    {
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText(Properties.getProperty("test_comparison"));

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        form.getBody().setLayout(layout);

        this.createComparisonResultsSection(form, toolkit);
        this.createCompareTestCasesSection(form, toolkit);

        GUIUtil.addListenerToAllObjects(form, this.compareTestCasesSection);
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

        Composite client = TCGUtil.createComposite(toolkit, this.comparisonResultsSection,
                SWT.NULL, 1);
        client.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

        GridLayout layout = (GridLayout) client.getLayout();
        layout.horizontalSpacing = 5;
        layout.verticalSpacing = 0;
        layout.marginLeft = 0;

        this.comparisonResultsSection.setText(Properties.getProperty("comparison_results"));
        this.comparisonResultsSection.setClient(client);
        this.comparisonResultsSection.setExpanded(true);

        Composite compositeSection = this.createComposite(client, 4, false);
        GridLayout layoutTable = (GridLayout) compositeSection.getLayout();
        layoutTable.horizontalSpacing = 10;
        layoutTable.verticalSpacing = 0;
        layoutTable.marginWidth = 0;

        Label newTestCasesLabel = new Label(compositeSection, SWT.NULL);
        newTestCasesLabel.setText(Properties.getProperty("new_test_cases"));

        this.createTitleOldTestCase(compositeSection);

        Composite compositePreferencedAndAutomaticAssociation = this.createComposite(compositeSection, 1, false);
        GridData gd2 = new GridData(SWT.FILL, SWT.TOP, false, false);
        gd2.verticalSpan = 2;
        compositePreferencedAndAutomaticAssociation.setLayoutData(gd2);

        GridLayout layoutCompositeBotaoLegendas = (GridLayout) compositePreferencedAndAutomaticAssociation.getLayout();
        layoutCompositeBotaoLegendas.horizontalSpacing = 0;
        layoutCompositeBotaoLegendas.marginWidth = 0;
        layoutCompositeBotaoLegendas.marginHeight = 0;

        this.createLegendAndButton(compositePreferencedAndAutomaticAssociation);
        this.preferencesAndAutomaticallyCheck(compositeSection);

        this.createNewTestCasesTable(compositeSection);
        this.createSimilarityTable(compositeSection);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, false, false);
        gd.horizontalSpan = 2;
        this.comparisonResultsSection.setLayoutData(gd);
    }

    /**
     * Comment for method.
     * @param compositeSection 
     */
    private void createTitleOldTestCase(Composite compositeSection)
    {
        Composite compositeTitleOldTestCase = this.createComposite(compositeSection, 3, false);

        Label similarityLabel = new Label(compositeTitleOldTestCase, SWT.NULL);
        similarityLabel.setText(Properties.getProperty("old_test_cases_from"));

        this.textOldTestCase = new Text(compositeTitleOldTestCase, SWT.BORDER);
        this.textOldTestCase.setText(ConsistencyManagementController.getInstance().getSelectedTestSuite().getName());
        this.textOldTestCase.setEditable(false);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, false, false);
        gd.widthHint = 100;
        gd.verticalIndent = 2;
        this.textOldTestCase.setLayoutData(gd);

        this.buttonChangeFile = new Button(compositeTitleOldTestCase, SWT.NULL);
        this.buttonChangeFile.setText(Properties.getProperty("change"));
        this.buttonChangeFile.addSelectionListener(new SelectionListener()
        {
            
            public void widgetSelected(SelectionEvent e)
            {
                ConsistencyManagerExtension cmExtension = ConsistencyManagerExtensionFactory
                .newConsistencyManagerExtension();

                if (cmExtension != null) 
                {
                    List<TextualTestCase> newTests = new Vector<TextualTestCase>();

                    for(TextualTestCase textualTestCase : checkedOldTestCases.keySet())
                    {
                        newTests.add(textualTestCase);
                    }

                    cmExtension.openConsistencyManager(shell, newTests);
                }
            }

            
            public void widgetDefaultSelected(SelectionEvent e)
            {

            }
        });
    }

    /**
     * Comment for method.
     * @param compositeSection
     */
    private void preferencesAndAutomaticallyCheck(Composite compositeSection)
    {
        Composite composite = this.createComposite(compositeSection, 1, true);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, false, false);
        gd.verticalSpan = 2;
        composite.setLayoutData(gd);

        GridLayout layoutTable = (GridLayout) composite.getLayout();
        layoutTable.marginWidth = 0;
        layoutTable.marginTop = 0;

        this.createNextStepGroup(composite);

        Group compositeFirstAndSecondOldTest = this.createGroup(composite, 3, false, Properties.getProperty("automatic_choice"));

        this.createLabel(compositeFirstAndSecondOldTest, Properties.getProperty("first_old_test"));
        this.firstPercent = new Spinner(compositeFirstAndSecondOldTest, SWT.BORDER);
        this.firstPercent.setMinimum(0);
        this.firstPercent.setMaximum(100);
        this.firstPercent.setSelection(90);
        this.createLabel(compositeFirstAndSecondOldTest, " %");

        this.createLabel(compositeFirstAndSecondOldTest, Properties.getProperty("second_old_test"));
        this.secondPercent = new Spinner(compositeFirstAndSecondOldTest, SWT.BORDER);
        this.secondPercent.setMinimum(0);
        this.secondPercent.setMaximum(100);
        this.secondPercent.setSelection(80);

        this.createLabel(compositeFirstAndSecondOldTest, " %");

        Composite compositeButton = this.createComposite(compositeFirstAndSecondOldTest, 1, true);

        GridLayout layoutCompositeButton = (GridLayout) compositeButton.getLayout();
        layoutCompositeButton.marginTop = 8;

        GridData gdCompositeButton = new GridData(SWT.FILL, SWT.TOP, false, false);
        gdCompositeButton.horizontalAlignment = SWT.CENTER;
        gdCompositeButton.horizontalSpan = 3;
        compositeButton.setLayoutData(gdCompositeButton);

        Button buttonRefresh = new Button(compositeButton, SWT.NULL);
        buttonRefresh.setText(Properties.getProperty("refresh"));

        buttonRefresh.addSelectionListener(new SelectionListener()
        {
            
            public void widgetSelected(SelectionEvent e)
            {
                int indexSelected = newTestCasesTable.getSelectionIndex();

                showNewTestCases();

                newTestCasesTable.setSelection(indexSelected);
                oldTestCaseGrid.setTestCase(null, null, false, null);

                showComparison();
                compareTestCasesSection.setExpanded(true);
                showNewTestCase();
            }

            
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }
        });
    }

    private Composite createComposite(Composite parent, int quantidadeDeColunas,
            boolean colunasDeMesmoTamanho)
    {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout(quantidadeDeColunas, colunasDeMesmoTamanho);

        composite.setLayout(layout);
        layout.verticalSpacing = 5;
        composite.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));

        return composite;
    }

    /**
     * Creates the SWT button which will be responsible for test suite generation.
     * 
     * @param client The SWT composite in which the button will be created.
     */
    private void createLegendAndButton(Composite client)
    {
        Composite compositeAll = this.createComposite(client, 1, false);
        compositeAll.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));

        GridLayout layoutCompositeBotaoLegendas = (GridLayout) compositeAll.getLayout();
        layoutCompositeBotaoLegendas.horizontalSpacing = 0;
        layoutCompositeBotaoLegendas.marginLeft = 0;

        this.createAllLegend(compositeAll);

        this.createButton(compositeAll);
    }

    /**
     * Comment for method.
     */
    private void createButton(Composite composite)
    {
        this.generateButton = new Button(composite, SWT.NONE);
        this.generateButton.setText(Properties.getProperty("generate_test_suite"));
        this.generateButton.addSelectionListener(new SelectionListener()
        {
            
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            
            public void widgetSelected(SelectionEvent e)
            {
                HashMap<TextualTestCase, TextualTestCase> testCases = new HashMap<TextualTestCase, TextualTestCase>();
                Vector<TextualTestCase> excludedTestCases = new Vector<TextualTestCase>();
                String excludedTestCasesString = " ";

                for (TextualTestCase newTestCase : checkedOldTestCases.keySet())
                {
                    ComparisonResult result = checkedOldTestCases.get(newTestCase);
                    TextualTestCase oldTestCase = null;

                    if (result != null)
                    {
                        oldTestCase = checkedOldTestCases.get(newTestCase).getOldTestCase();
                    }
                    else
                    {
                        excludedTestCases.add(newTestCase);
                    }
                    testCases.put(newTestCase, oldTestCase);
                }

                Collections.sort(excludedTestCases);

                DecimalFormat df = new DecimalFormat("0000");
                String testCaseId = "";

                for (int i = 0; i < excludedTestCases.size(); i++)
                {
                    testCaseId = Properties.getProperty("test_case") + df.format(excludedTestCases.get(i).getId());

                    if (i == 0)
                    {
                        excludedTestCasesString = excludedTestCasesString
                        + testCaseId;
                    }
                    else
                    {
                        excludedTestCasesString = excludedTestCasesString + ", "
                        + testCaseId;
                    }
                }

                excludedTestCasesString = excludedTestCasesString + ".";

                try
                {
                    boolean hasExludedTestCases = checkedOldTestCases.values().contains(null);

                    if (!hasExludedTestCases
                            || MessageDialog.openQuestion(shell, Properties.getProperty("warning"),
                                    Properties.getProperty("test_cases_excluded_warning")
                                    + excludedTestCasesString + "\n"
                                    + Properties.getProperty("do_you_want_to_proceed")))
                    {
                        ConsistencyManagementController.getInstance().mergeAndGenerate(testCases);

                        textNextID.setText(String.valueOf(ProjectManagerController.getInstance().getCurrentProject().getNextAvailableTestCaseID()));

                        MessageDialog
                        .openInformation(
                                shell,
                                Properties.getProperty("consistency_management"),
                                Properties
                                .getProperty("the_test_cases_were_merged_and_generated_successfully"));
                        GUIManager.getInstance().refreshViews();
                    }
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

        this.generateButton.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));

        GridData gd = (GridData)this.generateButton.getLayoutData();
        gd.verticalIndent = 20;
    }

    protected Group createGroup(Composite parent, int quantidadeDeColunas, boolean colunasDeMesmoComprimento, String text) {
        GridLayout groupLayout = new GridLayout(quantidadeDeColunas,colunasDeMesmoComprimento);
        groupLayout.verticalSpacing = 1;

        Group group = new Group(parent, SWT.NULL);
        group.setLayout(groupLayout);
        group.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
        group.setText(text);

        return group;
    }

    /**
     * Comment for method.
     * 
     * @param composite
     */
    private void createNextStepGroup(Composite composite)
    {
        int nextID = ProjectManagerController.getInstance().getCurrentProject()
        .getNextAvailableTestCaseID();

        Group compositeNextStepSection = this.createGroup(composite, 3, false, Properties.getProperty("preferences"));

        Label label = new Label(compositeNextStepSection, SWT.NULL);
        label.setText(Properties.getProperty("next_new_id"));
        label.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));

        this.textNextID = new Text(compositeNextStepSection, SWT.BORDER);
        this.textNextID.setText(String.valueOf(nextID));
        this.textNextID.setTextLimit(4);
        this.textNextID.setOrientation(SWT.RIGHT_TO_LEFT);
        this.textNextID.setEditable(false);

        GridData gridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
        gridData.widthHint = 50;
        this.textNextID.setLayoutData(gridData);

        this.buttonChangeNextID = new Button(compositeNextStepSection, SWT.NONE);
        this.buttonChangeNextID.setText(Properties.getProperty("change"));
        this.buttonChangeNextID.addSelectionListener(new SelectionListener()
        {
            
            public void widgetDefaultSelected(SelectionEvent e)
            {
                
            }

            
            public void widgetSelected(SelectionEvent e)
            {
                if (buttonChangeNextID.getText().equals(Properties.getProperty("change")))
                {
                    textNextID.setEditable(true);
                    buttonChangeNextID.setText(Properties.getProperty("save"));
                }
                else
                {
                    try
                    {
                        int newNextID = Integer.valueOf(textNextID.getText());

                        ProjectManagerController.getInstance().getCurrentProject()
                        .setNextAvailableTestCaseID(newNextID);
                        ProjectManagerController.getInstance().createXMLFile();

                        textNextID.setEditable(false);
                        buttonChangeNextID.setText(Properties.getProperty("change"));
                    }
                    catch (NumberFormatException e2)
                    {
                        MessageDialog.openError(shell, Properties.getProperty("error"), Properties
                                .getProperty("error_next_id"));
                    }
                    catch (TargetException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }

        });

        this.buttonChangeNextID.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true,
                false));
    }

    /**
     * Creates all legends of the page.
     * 
     * @param composite The composite to add legends.
     */
    private void createAllLegend(Composite composite)
    {
        Group compositeLegends = this.createGroup(composite, 1, true, Properties.getProperty("legend"));

        this.createLegend(compositeLegends, Properties.getProperty("new_test_case"),
                NEW_TEST_CASE_COLOR);
        this.createLegend(compositeLegends, Properties.getProperty("extra_step"),
                TestCaseGrid.TEST_CASE_EXTRA_STEP_COLOR);
        this.createLegend(compositeLegends, Properties.getProperty("diferente_field"),
                TestCaseGrid.TEST_CASE_DIFERENCE_COLOR);
    }

    private void createLegend(Composite composite, String text, RGB rgb)
    {
        Composite compositeColorAndLegend = new Composite(composite, SWT.NULL);
        GridLayout layout = new GridLayout(2, false);

        compositeColorAndLegend.setLayout(layout);
        layout.verticalSpacing = 1;
        compositeColorAndLegend.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true,
                false));

        Label label = new Label(compositeColorAndLegend, SWT.NULL);
        label.setText("     ");
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
        label.setBackground(new Color(label.getDisplay(), rgb));

        label = new Label(compositeColorAndLegend, SWT.NULL);
        label.setText(text);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
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

        Composite client = TCGUtil.createComposite(toolkit, this.compareTestCasesSection, SWT.WRAP,
                1);

        this.compareTestCasesSection.setText(Properties.getProperty("compare_test_case"));
        this.compareTestCasesSection.setClient(client);
        this.compareTestCasesSection.setExpanded(false);

        Label newTestCaseLabel = new Label(client, SWT.NULL);
        newTestCaseLabel.setText(Properties.getProperty("new_test"));

        this.newTestCaseGrid = new TestCaseGrid(client);

        Label formerTestCaseLabel = new Label(client, SWT.NULL);
        formerTestCaseLabel.setText("\n"+Properties.getProperty("old_test"));

        this.oldTestCaseGrid = new TestCaseGrid(client);

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
        gd.heightHint = 150;
        gd.widthHint = 150;
        gd.grabExcessHorizontalSpace = false;
        this.newTestCasesTable.setLayoutData(gd);

        TableColumn tc = new TableColumn(this.newTestCasesTable, SWT.LEFT);
        tc.setText(Properties.getProperty("id"));
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
                oldTestCaseGrid.setTestCase(null, null, false, null);

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
        gd.heightHint = 150;
        gd.widthHint = 220;
        gd.grabExcessHorizontalSpace = false;
        this.similarityTable.setLayoutData(gd);

        TableColumn idColumn = new TableColumn(this.similarityTable, SWT.LEFT);
        idColumn.setText(Properties.getProperty("id"));
        idColumn.setResizable(true);
        idColumn.setWidth(150);

        TableColumn similarityColumn = new TableColumn(this.similarityTable, SWT.LEFT);
        similarityColumn.setText(Properties.getProperty("similarity"));
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

        if (checkedItem.getText().equals(Properties.getProperty("new_id")))
        {
            TextualTestCase textualTestCase = new TextualTestCase(-1, newTestCase.getTcIdHeader(),
                    newTestCase.getSteps(), newTestCase.getExecutionType(), newTestCase
                    .getRegressionLevel(), newTestCase.getDescription(), newTestCase
                    .getObjective(), newTestCase.getRequirements(),
                    newTestCase.getSetups(), newTestCase.getInitialConditions(), newTestCase
                    .getNote(), newTestCase.getFinalConditions(), newTestCase.getCleanup(),
                    newTestCase.getUseCaseReferences(), newTestCase.getStatus(), newTestCase
                    .getFeatureId());

            ComparisonResult comparisonResult = new ComparisonResult(textualTestCase, 100.0, null);
            this.checkedOldTestCases.put(newTestCase, comparisonResult);
        }
        else if (checkedItem.getText().equals(Properties.getProperty("delete")))
        {
            this.checkedOldTestCases.put(newTestCase, null);
        }
        else
        {
            TextualTestCase oldTestCase = ((ComparisonResult) checkedItem.getData())
            .getOldTestCase();

            Collection<TextualTestCase> newTests = this.checkedOldTestCases.keySet();

            boolean hasNewTest = false;
            Vector<TextualTestCase> newTestAlreadySelected = new Vector<TextualTestCase>();

            for (TextualTestCase newTest : newTests)
            {
                if (this.checkedOldTestCases.get(newTest)!= null && this.checkedOldTestCases.get(newTest).getOldTestCase().equals(oldTestCase))
                {
                    newTestAlreadySelected.add(newTest);

                    hasNewTest = true;
                }
            }

            Collections.sort(newTestAlreadySelected);

            String oldTestsAlreadyAssociated = " ";
            DecimalFormat df = new DecimalFormat("0000");
            String testCaseId = "";

            for (int i = 0; i < newTestAlreadySelected.size(); i++)
            {
                testCaseId = Properties.getProperty("test_case") + df.format(newTestAlreadySelected.get(i).getId());

                if (i == 0)
                {
                    oldTestsAlreadyAssociated = oldTestsAlreadyAssociated
                    + testCaseId;
                }
                else
                {
                    oldTestsAlreadyAssociated = oldTestsAlreadyAssociated + ", "
                    + testCaseId;
                }
            }

            oldTestsAlreadyAssociated = oldTestsAlreadyAssociated + ".";

            ComparisonResult comparisonResult = this.checkedOldTestCases.get(newTestCase);

            if (hasNewTest && !((ComparisonResult) checkedItem.getData()).equals(comparisonResult))
            {
                MessageDialog.openWarning(shell, Properties.getProperty("warning"), Properties
                        .getProperty("old_test_already_associated_warning")
                        + " " + oldTestCase.getTcIdHeader() + ":" + oldTestsAlreadyAssociated);
            }

            this.checkedOldTestCases.put(newTestCase, (ComparisonResult) checkedItem.getData());
        }
    }

    /**
     * Mounts the New Test Cases Table.
     */
    private void showNewTestCases()
    {
        this.newTestCasesTable.removeAll();

        List<TextualTestCase> newTestCases = this.comparison.getNewTestCases();

        DecimalFormat df = new DecimalFormat("0000");

        for (TextualTestCase newTestCase : newTestCases)
        {

            TableItem newItem = new TableItem(this.newTestCasesTable, SWT.NONE);
            String[] text = new String[] { Properties.getProperty("test_case") + df.format(newTestCase.getId()) };
            newItem.setText(text);
            newItem.setData(newTestCase);

            ComparisonResult comparisonResultSelected = null;

            List<ComparisonResult> comparisonResults = this.comparison
            .getComparisonResultsByTestCase(newTestCase);

            // If some old test has similarity equals than 100% or more than 90% with the second
            // result with less than 80%, check this test.
            if ((comparisonResults.size() > 0 && comparisonResults.get(0).getSimilarity() == 100.0)
                    || (comparisonResults.size() > 1
                            && comparisonResults.get(0).getSimilarity() > this.firstPercent.getSelection() && comparisonResults
                            .get(1).getSimilarity() < this.secondPercent.getSelection()))
            {
                comparisonResultSelected = comparisonResults.get(0);
            }
            else
            {
                // If there is no test case with high similarity, the "New ID" option is selected.
                TextualTestCase textualTestCase = new TextualTestCase(-1, newTestCase
                        .getTcIdHeader(), newTestCase.getSteps(), newTestCase.getExecutionType(),
                        newTestCase.getRegressionLevel(), newTestCase.getDescription(), newTestCase
                        .getObjective(), newTestCase.getRequirements(), newTestCase
                        .getSetups(), newTestCase.getInitialConditions(), newTestCase
                        .getNote(), newTestCase.getFinalConditions(), newTestCase
                        .getCleanup(), newTestCase.getUseCaseReferences(), newTestCase
                        .getStatus(), newTestCase.getFeatureId());

                comparisonResultSelected = new ComparisonResult(textualTestCase, 100.0, null);

                newItem.setBackground(0, new Color(newItem.getDisplay(), NEW_TEST_CASE_COLOR));
            }

            this.checkedOldTestCases.put(newTestCase, comparisonResultSelected);
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
        newItem.setText(0, Properties.getProperty("new_id"));

        // If "New ID" is selected.
        if (selectedItem != null && selectedItem.getOldTestCase() != null
                && selectedItem.getOldTestCase().getId() == -1)
        {
            newItem.setChecked(true);
        }

        newItem = new TableItem(this.similarityTable, SWT.NONE);
        newItem.setText(0, Properties.getProperty("delete"));

        if (selectedItem == null)
        {
            newItem.setChecked(true);
        }

        for (ComparisonResult comparisonResult : comparisonResults)
        {
            newItem = new TableItem(this.similarityTable, SWT.NONE);

            TextualTestCase oldTestCase = comparisonResult.getOldTestCase();
            double similarity = comparisonResult.getSimilarity();

            newItem.setText(0, oldTestCase.getTcIdHeader());

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
        TextualTestCase newTestCase = (TextualTestCase) this.newTestCasesTable.getSelection()[0]
                                                                                              .getData();
        TextualTestCase oldTestCase = null;
        
        ComparisonResult comparisonResult = null;

        if (this.similarityTable.getSelection().length > 0)
        {
            comparisonResult = (ComparisonResult) this.similarityTable
            .getSelection()[0].getData();

            oldTestCase = comparisonResult.getOldTestCase();
        }
        //INSPECT - Lais        
        this.newTestCaseGrid.setTestCase(newTestCase, oldTestCase, true, comparisonResult.getStepPairs());
    }

    /**
     * Mounts the Old Test Case Table.
     */
    private void showOldTestCase()
    {
        ComparisonResult comparisonResult = (ComparisonResult) this.similarityTable.getSelection()[0]
                                                                                                   .getData();

        TextualTestCase newTestCase = (TextualTestCase) this.newTestCasesTable.getSelection()[0]
                                                                                              .getData();
        TextualTestCase oldTestCase = comparisonResult.getOldTestCase();
        
        //INSPECT - Lais
        this.oldTestCaseGrid.setTestCase(oldTestCase, newTestCase, false, this.revertStepPairsMatrix(comparisonResult.getStepPairs()));
        this.showNewTestCase();
    }

    private Label createLabel(Composite parent, String text) 
    {
        Label label = new Label(parent, SWT.NULL);
        label.setText(text);
        label.setLayoutData(new GridData(GridData.END, GridData.CENTER,
                false, false));

        return label;
    }
    
    /**
     *  Reverts the steps pairs matrix (resultTemp[][])
     * @param stepPairs the steps pairs matrix
     * @return the reverted steps pairs matrix (resultTemp[][])
     */
    //INSPECT Lais - new method
    private int[][] revertStepPairsMatrix(int[][] stepPairs){
        int[][] result = new int[stepPairs.length][2];
        for (int i = 0; i < stepPairs.length; i++)
        {
            result[i][0] = stepPairs[i][1];
            result[i][1] = stepPairs[i][0];
        }
        
        return result;
    }
}
