/*
 * @(#)OnTheFlyGeneratedTestCasesPage.java
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
 * wdt022    Mar 19, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 * tnd783    Aug 25, 2008	LIBqq51204   Changes in method createHeaderSection.
 * pvcv      Apr 01, 2009				 Internationalization support.
 * lmn3      Jul 03, 2009                Changes to correct permanent inclusion/exclusion bug.
 * fsf2      Jul 20, 2009                Changes due to consistency checkbox inspected.
 * lmn3      Jan 11, 2010                Changes to include an input dialog for the name of the filter selection
 * lmn3      Jan 18, 2010                Changes on the updateTestCaseListTable() method and inclusion of a reference to the last filter selection o the Test Selection Page.
 */
package br.ufpe.cin.target.onthefly.editors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.onthefly.editors.actions.CancelTestCaseExclusionAction;
import br.ufpe.cin.target.onthefly.editors.actions.CancelTestCaseInclusionAction;
import br.ufpe.cin.target.onthefly.editors.actions.ExcludeTestCaseAction;
import br.ufpe.cin.target.onthefly.editors.actions.IncludeTestCaseAction;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.util.GUIUtil;
import br.ufpe.cin.target.tcg.TCGActivator;
import br.ufpe.cin.target.tcg.controller.TestCaseGeneratorController;
import br.ufpe.cin.target.tcg.extensions.ConsistencyManagerExtension;
import br.ufpe.cin.target.tcg.extensions.ConsistencyManagerExtensionFactory;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.filters.TestSuiteFilterAssembler;
import br.ufpe.cin.target.tcg.util.TCGUtil;


/**
 * <pre>
 * CLASS:
 * This class provides a form page with three sections: 
 *      - Header -&gt; Provides a GUI to set which test cases shall be exhibited (common, added, removed) and
 *                  to select, load, and save a filter configuration.    
 *      - Test Cases Table -&gt; Exhibits the test suite according to some filter configuration
 *      - Test Case -&gt; Exhibits a selected test case
 * 
 * RESPONSIBILITIES:
 * Exhibits the test suite according some filter configuration. Compares two test suites, indicating 
 * which test cases were removed or added. Exhibits a selected test case.
 * 
 * </pre>
 */
public class OnTheFlyGeneratedTestCasesPage extends FormPage {

	/**
	 * The unique identifier of this form page.
	 */
	public static final String ID = "br.ufpe.cin.target.tcg.editors.OnTheFlyGeneratedTestCasesPage";

	/**
	 * The table with the test cases generated according to the selected filter
	 * configuration.
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
	 * The form page that exhibits the list of test cases always excluded or
	 * included in the final test suite.
	 */
	private OnTheFlyTestExclusionPage exclusionInclusionPage;

	/**
	 * Check button that defines whether the consistency management shall be
	 * started or not before saving test cases.
	 */
	private Button consistencyManagementCheckButton;

    private Section section;
    
    /**
     * Stores the reference of the last filter selection from the Test Selection Page. 
     */
    private TestSuiteFilterAssembler lastFilterSelection;

	/**
	 * Creates a generated test cases form page.
	 * 
	 * @param editor
	 *            The editor where this page will be added.
	 * @param selectionPage
	 *            The form page that provides filter configuration.
	 * @param exclusionInclusionPage
	 *            The form page that exhibits the list of test cases always
	 *            excluded or included in the final test suite.
	 */
	public OnTheFlyGeneratedTestCasesPage(FormEditor editor,
			OnTheFlyTestSelectionPage selectionPage,
			OnTheFlyTestExclusionPage exclusionInclusionPage) {
	    
		super(editor, ID, Properties.getProperty("generated_test_cases"));

		this.selectionPage = selectionPage;
		
		this.exclusionInclusionPage = exclusionInclusionPage;
		
		this.lastFilterSelection = this.selectionPage.getFilterAssembler();
	}

	/**
	 * Retrieves the editor that contains this page.
	 * 
	 * @return The editor where this page was added.
	 */
	public OnTheFlyMultiPageEditor getEditor() {
		return (OnTheFlyMultiPageEditor) super.getEditor();
	}

	/**
	 * Creates content in the form hosted in this page.
	 * 
	 * @param managedForm
	 *            The form hosted in this page.
	 */
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(Properties.getProperty("test_cases_viewer"));
		form.getBody().setLayout(new GridLayout());

		try {
			this.createHeaderSection(form, toolkit);
			this.createGeneratedTestCasesSection(form, toolkit);
			this.createTestCaseTableSection(form, toolkit);
			this.createContextMenu();
			
			GUIUtil.addListenerToAllObjects(form, section);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the context menu for <code>generatedTestCasesTable</code>
	 */
	private void createContextMenu() {
		MenuManager menuManager = new MenuManager(
				"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage");
		ISelectionProvider selectionProvider = new TableViewer(
				this.generatedTestCasesTable);

		menuManager
				.add(new ExcludeTestCaseAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.ExcludeTestCase",
						selectionProvider, this.exclusionInclusionPage));

		menuManager
				.add(new IncludeTestCaseAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.IncludeTestCase",
						selectionProvider, this.exclusionInclusionPage));

		menuManager
				.add(new CancelTestCaseExclusionAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.CancelTestCaseExclusionAction",
						selectionProvider, this.exclusionInclusionPage));

		menuManager
				.add(new CancelTestCaseInclusionAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage.CancelTestCaseInclusionAction",
						selectionProvider, this.exclusionInclusionPage));

		Menu contextMenu = menuManager
				.createContextMenu(this.generatedTestCasesTable);
		this.generatedTestCasesTable.setMenu(contextMenu);
		this
				.getSite()
				.registerContextMenu(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyGeneratedTestCasesPage",
						menuManager, selectionProvider);
	}

	/**
	 * Creates the header section. This section provides a GUI to set which test
	 * cases shall be exhibited (common, added, removed) and to select, load,
	 * and save a filter configuration.
	 * 
	 * @param form
	 *            The form widget managed by this form
	 * @param toolkit
	 *            The toolkit used by this form
	 */

	private void createHeaderSection(final ScrolledForm form,
			FormToolkit toolkit) {
		this.section = OnTheFlyUtil.createSection(form, toolkit,
				Section.EXPANDED | Section.TITLE_BAR);

		Composite client = OnTheFlyUtil.createComposite(toolkit, section,
				SWT.NONE, 2);

		header = new OnTheFlyHeader(client, this.getEditor());

		header.addCheckBoxesListener(new SelectionListener() {
			
			/*
			 * * Sent when default selection occurs in the control.
			 * 
			 * @param e an event containing information about the default
			 * selection
			 */
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			/**
			 * Sent when selection occurs in the control.
			 * 
			 * @param e
			 *            an event containing information about the selection
			 */
			
			public void widgetSelected(SelectionEvent e) {
			    updateTestCaseListTable();
			}

		});
		
		//adds a listener to the filter combo
		header.addFormerFilterSelectionListener(new SelectionListener() {

			
			/*
			 * * Sent when default selection occurs in the control.
			 * 
			 * @param e an event containing information about the default
			 * selection
			 */
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			
			/*
			 * * Sent when selection occurs in the control.
			 * 
			 * @param e an event containing information about the selection
			 */
			public void widgetSelected(SelectionEvent e) {
			    updateTestCaseListTable();
			}

		});
		
		//adds a listener to the "Load Former Filter" button
		header.addLoadFilterButtonListener(new SelectionListener() {

			
			/*
			 * * Sent when default selection occurs in the control.
			 * 
			 * @param e an event containing information about the default
			 * selection
			 */
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			
			/*
			 * * Sent when selection occurs in the control.
			 * 
			 * @param e an event containing information about the selection
			 */
			public void widgetSelected(SelectionEvent e) {

				TestSuiteFilterAssembler assembler = header
						.getFormerAssembler();
				selectionPage.setFilterAssembler(assembler);
				TestSuite<TestCase<FlowStep>> wholeTestSuite = getEditor()
						.getRawTestSuite();
				TestSuite<TestCase<FlowStep>> testSuite = selectionPage
						.getCurrentTestSuite();
				updateTestCaseListTable(wholeTestSuite, testSuite, testSuite);
			}

		});
		
		//adds a filter to the "Save Current Filter Selection" button. 
		header.addSaveFilterButtonListener(new SelectionListener() {

			
			/*
			 * * Sent when default selection occurs in the control.
			 * 
			 * @param e an event containing information about the default
			 * selection
			 */
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			
			/*
			 * * Sent when selection occurs in the control.
			 * 
			 * @param e an event containing information about the selection
			 */
			public void widgetSelected(SelectionEvent e) {
			    String filterSelectionName = "";
			    
                InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), Properties
                        .getProperty("filter_selection_dialog_title"), Properties
                        .getProperty("filter_selection_dialog_message"), "", null);
                if (dlg.open() == Window.OK)
                {
                    filterSelectionName = dlg.getValue().trim();
                    TestSuiteFilterAssembler assembler = selectionPage
                    .getFilterAssembler();
                    header.addFilter(assembler, filterSelectionName);
                }		    
				
			}

		});

		section.setClient(client);
		section.setExpanded(true);
		section
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));
	}

	/**
	 * Creates the generated test cases section. This section exhibits the test
	 * suite according to some filter configuration.
	 * 
	 * @param form
	 *            The form widget managed by this form.
	 * @param toolkit
	 *            The toolkit used by this form.
	 */
	private void createGeneratedTestCasesSection(final ScrolledForm form,
			FormToolkit toolkit) {
		Section section = OnTheFlyUtil.createSection(form, toolkit,
				Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);

		Composite client = OnTheFlyUtil.createComposite(toolkit, section,
				SWT.NONE, 2);

		this.generatedTestCasesTable = this.createTestCaseListTable(client);
		TestSuite<TestCase<FlowStep>> testSuite = this.getEditor()
				.getRawTestSuite();
		this.updateTestCaseListTable(testSuite, testSuite, testSuite);

		this.createSaveTestSuiteButton(client);

		ConsistencyManagerExtension cm = ConsistencyManagerExtensionFactory
				.newConsistencyManagerExtension();

		if (cm != null) {
			consistencyManagementCheckButton = new Button(client, SWT.CHECK);
			consistencyManagementCheckButton.setText(Properties
					.getProperty("start_consistency_management_before_saving"));
		}

		section
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));
		section.setText(Properties.getProperty("generated_test_cases"));
		section.setClient(client);
		section.setExpanded(true);
	}

	/**
	 * Creates the test case table section. This section exhibits a selected
	 * test case.
	 * 
	 * @param form
	 *            The form widget managed by this form
	 * @param toolkit
	 *            The toolkit used by this form
	 */
	private void createTestCaseTableSection(final ScrolledForm form,
			FormToolkit toolkit) {
		Section section = OnTheFlyUtil.createSection(form, toolkit,
				Section.TITLE_BAR | Section.TWISTIE | Section.COMPACT);

		Composite client = OnTheFlyUtil.createComposite(toolkit, section,
				SWT.NONE, 1);

		this.testCaseTable = new TestCaseTable(client);
		this.testCaseTable.setTestCase(null);

		section
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));
		section.setText(Properties.getProperty("selected_test_case"));
		section.setClient(client);
		section.setExpanded(true);
	}
	
	/**
	 * Creates the save current TestSuite button and sets its listeners.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	private void createSaveTestSuiteButton(Composite parent) {
		GridData gridData = OnTheFlyUtil.createGridData(SWT.NONE, 150, 25);
		Button saveCurrentTestSuite = new Button(parent, SWT.PUSH);
		saveCurrentTestSuite.setText(Properties
				.getProperty("save_test_suite"));
		saveCurrentTestSuite
				.setToolTipText(Properties
						.getProperty("saves_the_test_suite_generated_with_the_filter_specified_in_the_test_selection_page"));
		saveCurrentTestSuite.setLayoutData(gridData);
		saveCurrentTestSuite.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				TestSuite<TestCase<FlowStep>> testSuite = selectionPage
						.getCurrentTestSuite();
				
				HashSet<TestCase<FlowStep>> currentMap = new HashSet<TestCase<FlowStep>>(
				        testSuite.getTestCases());
		        
		        verifyExcludedIncludedTestCases(currentMap);
		        
		        //adds only test cases that are not permanently excluded
                List<TextualTestCase> textualTestCases = new ArrayList<TextualTestCase>();
                for (TestCase<FlowStep> testCase : testSuite.getTestCases())
                {
                    if (currentMap.contains(testCase))
                    {
                        textualTestCases.add(getEditor().getTextualTestCase(testCase.getId()));
                        currentMap.remove(testCase);
                    }
                }
                
                //checks if currentMap contains permanently included test cases
                for (TestCase<FlowStep> testCase : currentMap)
                {
                    if (!textualTestCases.contains(testCase))
                    {
                        textualTestCases.add(getEditor().getTextualTestCase(testCase.getId()));
                    }
                }
                
               Collections.sort(textualTestCases);
               
               TestCaseGeneratorController.getInstance().setLastSuiteGenerated(textualTestCases);
                
				try {
					ConsistencyManagerExtension cmExtension = ConsistencyManagerExtensionFactory
							.newConsistencyManagerExtension();
					if (cmExtension != null
							&& consistencyManagementCheckButton.getSelection()) {
						cmExtension.openConsistencyManager(getEditor()
								.getSite().getShell(), textualTestCases);
					} else {
						long beforeTime = System.currentTimeMillis();
						File testSuiteFile = TestCaseGeneratorController.getInstance()
						//INSPECT Felype Alterei para que CM chamasse o mesmo método de geração que On The Fly.
								.writeTestSuiteFile(textualTestCases);
						long afterTime = System.currentTimeMillis();
						
						//INSPECT - Lais Recebe agora o arquivo da suite de testes.
						TCGUtil.displayTestCaseSummary(getEditor().getSite()
								.getShell(), textualTestCases.size(), afterTime
								- beforeTime, testSuiteFile);
						GUIManager.getInstance().refreshViews();
					}

				} catch (IOException e1) {
					e1.printStackTrace();
					MessageDialog
							.openError(
									getEditor().getSite().getShell(),
									Properties.getProperty("error"),
									Properties
											.getProperty("error_while_writing_the_excel_file")
											+ e1.getMessage());
					TCGActivator.logError(0, this.getClass(), Properties
							.getProperty("error_while_writing_the_excel_file")
							+ e1.getMessage(), e1);
				} catch (TargetException e2) {
					MessageDialog.openError(getEditor().getSite().getShell(),
							Properties.getProperty("error"), e2.getMessage());
					TCGActivator.logError(0, this.getClass(), Properties
							.getProperty("error_while_refreshing_views")
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
	 * @param The
	 *            composite parent.
	 * @return A table filled with test cases
	 */
	private Table createTestCaseListTable(Composite parent) {
		Composite mainContainer = new Composite(parent, SWT.WRAP);

		mainContainer.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		mainContainer.setLayoutData(gridData);
		mainContainer.pack(false);

		Table table = TCGUtil.createTestCaseListTable(mainContainer, SWT.MULTI);
		gridData = OnTheFlyUtil.createGridData(GridData.FILL_BOTH, SWT.DEFAULT,
				200);
		table.setLayoutData(gridData);

		table.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				TextualTestCase testCase = null;
				if (generatedTestCasesTable.getSelectionCount() > 0) {
					testCase = (TextualTestCase) generatedTestCasesTable
							.getSelection()[0].getData();
				}

				testCaseTable.setTestCase(testCase);
				TCGUtil.reflow(testCaseTable.getGrid());
			}

		});

		return table;
	}

	/**
	 * Removes a test case from the table <code>generatedTestCasesTable</code>.
	 * 
	 * @param testCase
	 *            The test case that will be removed.
	 */
	public void removeTestCases(TextualTestCase testCase) {
		for (int i = 0; i < this.generatedTestCasesTable.getItemCount(); i++) {
			if (this.generatedTestCasesTable.getItem(i).getData().equals(
					testCase)) {
				this.generatedTestCasesTable.remove(i);
				break;
			}
		}
	}

	/**
	 * Verifies if the test case table needs to be updated.
	 */
	public void verifyUpdateTestCaseListTable() {
		//INSPECT
		TestSuiteFilterAssembler currentFilterSelection = selectionPage.getFilterAssembler();
		
        if (!currentFilterSelection.equals(this.lastFilterSelection))
        {
            this.lastFilterSelection = currentFilterSelection;
            
            this.updateTestCaseListTable();
        }
	}
	
	/**
	 * 
	 * Updates the table with the test cases generated under filter settings.
	 */
	public void updateTestCaseListTable()
	{
	    TestSuite<TestCase<FlowStep>> wholeTestSuite = this.getEditor().getRawTestSuite();

        TestSuiteFilterAssembler formerAssembler = header.getFormerAssembler();
        
        TestSuite<TestCase<FlowStep>> formerTestSuite = formerAssembler.assemblyFilter()
                .filter(this.getEditor().getRawTestSuite());
        
        TestSuite<TestCase<FlowStep>> currentTestSuite = selectionPage.getCurrentTestSuite();
        
        this.updateTestCaseListTable(wholeTestSuite, currentTestSuite, formerTestSuite);
	}

	/**
	 * Updates the table with the generated test cases. The color of each row
	 * changes according to the test case status: added, common or removed.
	 * 
	 * @param completeTestSuite
	 *            The whole test suite.
	 * @param currentTestSuite
	 *            The test suite generated according to the current filter
	 *            settings.
	 * @param formerTestSuite
	 *            The test suite generated according to the selected filter
	 *            configuration.
	 */
	private void updateTestCaseListTable(
			TestSuite<TestCase<FlowStep>> completeTestSuite,
			TestSuite<TestCase<FlowStep>> currentTestSuite,
			TestSuite<TestCase<FlowStep>> formerTestSuite) {

		HashSet<TestCase<FlowStep>> currentMap = new HashSet<TestCase<FlowStep>>(
				currentTestSuite.getTestCases());
		HashSet<TestCase<FlowStep>> formerMap = new HashSet<TestCase<FlowStep>>(
				formerTestSuite.getTestCases());

		this.verifyExcludedIncludedTestCases(currentMap);
		this.generatedTestCasesTable.setItemCount(0);

		int common = 0;
		int added = 0;
		int removed = 0;
		for (TestCase<FlowStep> testCase : completeTestSuite.getTestCases()) {
			RGB itemRGB = null;
			if (currentMap.contains(testCase) && formerMap.contains(testCase)) {
				common++;
				if (header.isCommonTCCheckBoxChecked()) {
					itemRGB = OnTheFlyUtil.COMMON_ITEM_RGB;
				}
			} else if (currentMap.contains(testCase)
					&& !formerMap.contains(testCase)) {
				added++;
				if (header.isAddedTCCheckBoxChecked()) {
					itemRGB = OnTheFlyUtil.ADDED_ITEM_RGB;
				}
			} else if (!currentMap.contains(testCase)
					&& formerMap.contains(testCase)) {
				removed++;
				if (header.isRemovedTCCheckBoxChecked()) {
					itemRGB = OnTheFlyUtil.REMOVED_ITEM_RGB;
				}
			}

			if (itemRGB != null) {
				TableItem item = new TableItem(this.generatedTestCasesTable,
						SWT.NONE);
				item.setForeground(new Color(item.getDisplay(), itemRGB));
				TextualTestCase textualTC = this.getEditor()
						.getTextualTestCase(testCase.getId());
				item.setText(0, textualTC.getTcIdHeader()/* + textualTC.getId()*/);
				item.setText(1, textualTC.getObjective());
				item.setText(2, textualTC.getRequirements());
				item.setText(3, textualTC.getUseCaseReferences());
				item.setData(textualTC);
			}
		}

		header.setCheckBoxesText(added, common, removed);
	}

	/**
	 * Filters the <code>testSuite</code> removing or adding test cases
	 * according to the lists of excluded and included test cases.
	 * 
	 * @param testSuite
	 *            The test suite that will be filtered.
	 */
	private void verifyExcludedIncludedTestCases(
			Set<TestCase<FlowStep>> testSuite) {
		for (TextualTestCase testCase : this.exclusionInclusionPage
				.getExcludedList()) {
			TestCase<FlowStep> rawTestCase = this.getEditor().getRawTestCase(
					testCase.getId());
			testSuite.remove(rawTestCase);
		}

		for (TextualTestCase testCase : this.exclusionInclusionPage
				.getIncludedList()) {
			if (!testSuite.contains(testCase)) {
				testSuite
						.add(this.getEditor().getRawTestCase(testCase.getId()));
			}
		}
	}

	/**
	 * 
	 * @see IWorkbenchPart#setFocus()
	 */
	public void setFocus() {
		super.setFocus();
		this.verifyUpdateTestCaseListTable();
	}

	/**
	 * Gets the <code>generatedTestCasesTable</code> field.
	 * 
	 * @return The table of generated test cases.
	 */
	public Table getGeneratedTestCasesTable() {
		return this.generatedTestCasesTable;
	}
}
