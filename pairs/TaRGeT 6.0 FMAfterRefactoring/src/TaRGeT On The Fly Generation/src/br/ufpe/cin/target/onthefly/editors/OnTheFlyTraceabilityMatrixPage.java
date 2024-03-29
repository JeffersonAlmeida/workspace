/*
 * @(#)OnTheFlyTraceabilityMatrixPage.java
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
 * wln013    Mar 24, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 * tnd783    Aug 25, 2008	LIBqq51204	 Changes made on pages' header for on the fly.
 * pvcv      Apr 01, 2009				 Internationalization support
 * lmn3      Dec 14, 2009                Added the method verifyExpandTreeItem
 * lmn3      Jan 11, 2010                Changes to include an input dialog for the name of the filter selection
 * lmn3      Jan 18, 2010                Changes on the method updateTestCaseListTable() and inclusion of a reference to the last filter selection from the Test Selection page
 */
package br.ufpe.cin.target.onthefly.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.common.util.StringsUtil;
import br.ufpe.cin.target.common.util.UseCaseUtil;
import br.ufpe.cin.target.onthefly.editors.actions.AddRequirementToFilterAction;
import br.ufpe.cin.target.onthefly.editors.actions.AddUseCaseToFilterAction;
import br.ufpe.cin.target.onthefly.editors.actions.CancelTestCaseExclusionAction;
import br.ufpe.cin.target.onthefly.editors.actions.CancelTestCaseInclusionAction;
import br.ufpe.cin.target.onthefly.editors.actions.ExcludeTestCaseAction;
import br.ufpe.cin.target.onthefly.editors.actions.IncludeTestCaseAction;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.pm.util.GUIUtil;
import br.ufpe.cin.target.tcg.TCGActivator;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.filters.TestSuiteFilter;
import br.ufpe.cin.target.tcg.filters.TestSuiteFilterAssembler;


/**
 * <pre>
 *   CLASS:
 *   This class mounts the traceability matrix page for test case generation.
 * 
 *   RESPONSIBILITIES:
 *   This page allows test case designer to visualize the test cases grouped by requirements or use cases. 
 *   
 *   USAGE:
 *   This class is used in On The Fly editor.
 * 
 * </pre>
 */
public class OnTheFlyTraceabilityMatrixPage extends FormPage {

	/** The form page ID */
	public static final String ID = "br.ufpe.cin.target.tcg.editors.OnTheFlyTraceabilityMatrix";

	/**
	 * Counts the number of common test cases, comparing the current test suite
	 * with the former.
	 */
	private int common;

	/**
	 * Counts the number of new test cases, comparing the current test suite
	 * with the former.
	 */
	private int added;

	/**
	 * Counts the number of removed test cases, comparing the current test suite
	 * with the former.
	 */
	private int removed;

	/**
	 * Exhibits test cases grouped by requirements.
	 */
	private Tree requirementsTree;

	/**
	 * Exhibits test cases grouped by use cases.
	 */
	private Tree useCasesTree;
	
	/**
     * Represents a requirement.
     */
    private Image requirementImage;
	
	/**
	 * Represents a use case.
	 */
	private Image useCaseImage;

	/**
	 * The test suite generated with the current filters configuration.
	 */
	private HashSet<Integer> currentTestCasesIDs;

	/**
	 * The test suite generated with the configuration highlighted on header.
	 */
	private HashSet<Integer> formerTestCasesIDs;

	/**
	 * The test cases grouped by requirements.
	 */
	private HashMap<String, List<TextualTestCase>> requirementsTestCases;

	/**
	 * The test cases grouped by use cases.
	 */
	private HashMap<String, List<TextualTestCase>> useCaseTestCases;

	/**
	 * The whole test suite.
	 */
	private TestSuite<TextualTestCase> wholeTestSuite;

	/**
	 * Exhibits the test case selected on trees.
	 */
	private TestCaseTable tcTable;

	/**
	 * The component created on header section.
	 */
	private OnTheFlyHeader header;

	/**
	 * The form page that provides filter configuration.
	 */
	private OnTheFlyTestSelectionPage selectionPage;

	/**
	 * The form page that exhibits the list of test cases always excluded or
	 * included in the final test suite.
	 */
	private OnTheFlyTestExclusionPage exclusionInclusionPage;

    private Section section;
    
    /**
     * Stores the reference of the last filter selection from the Test Selection Page. 
     */ 
    private TestSuiteFilterAssembler lastFilterSelection;

	/**
	 * A constructor that creates the page and initializes it with the editor.
	 * 
	 * @param editor
	 *            The editor where this page will be added.
	 * @param selectionPage
	 *            The form page that provides filter configuration.
	 * @param exclusionInclusionPage
	 *            The form page that exhibits the list of test cases always
	 *            excluded or included in the final test suite.
	 */
	public OnTheFlyTraceabilityMatrixPage(FormEditor editor,
			OnTheFlyTestSelectionPage selectionPage,
			OnTheFlyTestExclusionPage exclusionInclusionPage) {
		super(editor, ID, Properties.getProperty("traceability_matrix"));
		this.selectionPage = selectionPage;
		this.exclusionInclusionPage = exclusionInclusionPage;
		wholeTestSuite = getEditor().getTextualTestSuite();
		currentTestCasesIDs = fillSetWithTestCasesIDs(selectionPage
				.getCurrentTestSuite().getTestCases());
		formerTestCasesIDs = fillSetWithTestCasesIDs(getEditor()
				.getRawTestSuite().getTestCases());
		groupTestCases();
		
		this.lastFilterSelection = this.selectionPage.getFilterAssembler();
	}

	/**
	 * Creates content in the form hosted in this page. Four sections are
	 * created: header, requirements X test cases, use cases X test cases, and
	 * test case.
	 * 
	 * @param managedForm
	 *            The form hosted in this page.
	 */
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(Properties.getProperty("traceability_matrix"));
		form.getBody().setLayout(new GridLayout());

		ImageDescriptor descriptor = TCGActivator.imageDescriptorFromPlugin(
				TCGActivator.PLUGIN_ID, "icons/usecase.gif");
		useCaseImage = descriptor.createImage();
		useCaseImage = new Image(useCaseImage.getDevice(), useCaseImage
				.getImageData().scaledTo(16, 16));
		
		descriptor = TCGActivator.imageDescriptorFromPlugin(
                TCGActivator.PLUGIN_ID, "icons/tick_icon.PNG");
        requirementImage = descriptor.createImage();
        requirementImage = new Image(requirementImage.getDevice(), requirementImage
                .getImageData().scaledTo(16, 16));

		createHeaderSection(form, toolkit);
		createRequirementsTestCasesSection(form, toolkit);
		createUseCasesTestCasesSection(form, toolkit);
		createTestCaseSection(form, toolkit);
		createContextMenus();
		
		GUIUtil.addListenerToAllObjects(form,section);
	}

	/**
	 * Creates the context menu for <code>requirementsTree</code> and
	 * <code>useCasesTree</code>
	 */
	private void createContextMenus() {
		ISelectionProvider reqSelectionProvider = new TreeViewer(
				requirementsTree);
		MenuManager menuManager = new MenuManager(
				"br.ufpe.cin.target.tcg.editor.OnTheFlyReqContextMenu");
		menuManager
				.add(new AddRequirementToFilterAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.AddRequirementToFilter",
						reqSelectionProvider, selectionPage));

		menuManager
				.add(new ExcludeTestCaseAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.ExcludeTestCase",
						reqSelectionProvider, exclusionInclusionPage));

		menuManager
				.add(new IncludeTestCaseAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.IncludeTestCase",
						reqSelectionProvider, exclusionInclusionPage));

		menuManager
				.add(new CancelTestCaseExclusionAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.CancelTestCaseExclusionAction",
						reqSelectionProvider, exclusionInclusionPage));

		menuManager
				.add(new CancelTestCaseInclusionAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.CancelTestCaseInclusionAction",
						reqSelectionProvider, exclusionInclusionPage));

		Menu contextMenu = menuManager.createContextMenu(requirementsTree);
		requirementsTree.setMenu(contextMenu);

		getSite()
				.registerContextMenu(
						"br.ufpe.cin.target.tcg.editor.OnTheFlyReqContextMenu",
						menuManager, reqSelectionProvider);

		ISelectionProvider ucSelectionProvider = new TreeViewer(useCasesTree);
		menuManager = new MenuManager(
				"br.ufpe.cin.target.tcg.editor.OnTheFlyUCContextMenu");
		menuManager
				.add(new AddUseCaseToFilterAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.AddUseCaseToFilter",
						ucSelectionProvider, selectionPage));
		menuManager
				.add(new ExcludeTestCaseAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.ExcludeTestCase",
						ucSelectionProvider, exclusionInclusionPage));

		menuManager
				.add(new IncludeTestCaseAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.IncludeTestCase",
						ucSelectionProvider, exclusionInclusionPage));

		menuManager
				.add(new CancelTestCaseExclusionAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.CancelTestCaseExclusionAction",
						ucSelectionProvider, exclusionInclusionPage));

		menuManager
				.add(new CancelTestCaseInclusionAction(
						"br.ufpe.cin.target.tcg.editor.contextmenu.OnTheFlyTraceabilityMatrixPage.CancelTestCaseInclusionAction",
						ucSelectionProvider, exclusionInclusionPage));

		contextMenu = menuManager.createContextMenu(useCasesTree);
		useCasesTree.setMenu(contextMenu);

		getSite()
				.registerContextMenu(
						"br.ufpe.cin.target.tcg.editor.OnTheFlyUCContextMenu",
						menuManager, ucSelectionProvider);
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
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetDefaultSelected()
			 */
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetSelected()
			 */
			public void widgetSelected(SelectionEvent e) {
				fillRequirementsTree();
				fillUseCasesTree();
			}

		});
		
		//adds a listener to the filter combo
		header.addFormerFilterSelectionListener(new SelectionListener() {

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetDefaultSelected()
			 */
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetSelected()
			 */
			public void widgetSelected(SelectionEvent e) {
			    
			    currentTestCasesIDs = fillSetWithTestCasesIDs(selectionPage
						.getCurrentTestSuite().getTestCases());
				TestSuiteFilter<FlowStep> formerFilter = header
						.getFormerAssembler().assemblyFilter();
				formerTestCasesIDs = fillSetWithTestCasesIDs((formerFilter
						.filter(getEditor().getRawTestSuite())).getTestCases());
				fillRequirementsTree();
				fillUseCasesTree();
			}

		});
		
		//adds a listener to the "Load Former Filter" button
		header.addLoadFilterButtonListener(new SelectionListener() {

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetDefaultSelected()
			 */
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetSelected()
			 */
			public void widgetSelected(SelectionEvent e) {
				TestSuiteFilterAssembler assembler = header
						.getFormerAssembler();
				selectionPage.setFilterAssembler(assembler);
				currentTestCasesIDs = fillSetWithTestCasesIDs(selectionPage
						.getCurrentTestSuite().getTestCases());
				fillRequirementsTree();
				fillUseCasesTree();
			}

		});
		
		//adds a filter to the "Save Current Filter Selection" button. 
		header.addSaveFilterButtonListener(new SelectionListener() {

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetDefaultSelected()
			 */
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetSelected()
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
						false));//
	}

	/**
	 * Create the requirements X test cases section. This section provides a
	 * tree viewer that exhibits the test cases grouped by requirements.
	 * 
	 * @param form
	 *            The form widget managed by this form
	 * @param toolkit
	 *            The toolkit used by this form
	 */
	private void createRequirementsTestCasesSection(final ScrolledForm form,
			FormToolkit toolkit) {
		Section section = OnTheFlyUtil.createSection(form, toolkit,
				Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE
						| Section.EXPANDED);
		Composite client = OnTheFlyUtil.createComposite(toolkit, section,
				SWT.NONE, 1);

		createRequirementsTestCasesTree(client);

		section.setText(Properties.getProperty("requirements_x_test_cases"));
		section.setClient(client);
		section.setExpanded(true);
		section
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));

	}

	/**
	 * Create the use cases X test cases section. This section provides a tree
	 * viewer that exhibits the test cases grouped by use cases.
	 * 
	 * @param form
	 *            The form widget managed by this form
	 * @param toolkit
	 *            The toolkit used by this form
	 */
	private void createUseCasesTestCasesSection(final ScrolledForm form,
			FormToolkit toolkit) {

		Section section = OnTheFlyUtil.createSection(form, toolkit,
				Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE
						| Section.EXPANDED);
		Composite client = OnTheFlyUtil.createComposite(toolkit, section,
				SWT.NONE, 2);

		createUseCasesTestCasesTree(client);

		section.setText(Properties.getProperty("use_cases_x_test_cases"));
		section.setClient(client);
		section.setExpanded(true);
		section
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));

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
	private void createTestCaseSection(final ScrolledForm form,
			FormToolkit toolkit) {
		Section section = OnTheFlyUtil.createSection(form, toolkit,
				Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE
						| Section.EXPANDED);
		Composite client = OnTheFlyUtil.createComposite(toolkit, section,
				SWT.NONE, 2);

		tcTable = new TestCaseTable(client);

		section.setText(Properties.getProperty("selected_test_case"));
		section.setClient(client);
		section.setExpanded(true);
		section
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));

	}

	/**
	 * Creates and fills in the tree that exhibits the test cases grouped by
	 * requirements.
	 * 
	 * @param parent
	 *            The composite control which will be the parent of the tree
	 *            (cannot be null).
	 */
	private void createRequirementsTestCasesTree(Composite parent) {
		requirementsTree = new Tree(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		requirementsTree.setLayoutData(OnTheFlyUtil.createGridData(
				GridData.FILL_BOTH, SWT.DEFAULT, 200));
		requirementsTree.addSelectionListener(new SelectionListener() {

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetDefaultSelected()
			 */
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetSelected()
			 */
			public void widgetSelected(SelectionEvent e) {
				updateTestCase((Tree) e.getSource());
			}

		});

	}

	/**
	 * Creates and fills in the tree that exhibits the test cases grouped by use
	 * cases.
	 * 
	 * @param parent
	 *            The composite control which will be the parent of the tree
	 *            (cannot be null).
	 */
	private void createUseCasesTestCasesTree(Composite parent) {
		useCasesTree = new Tree(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		useCasesTree.setLayoutData(OnTheFlyUtil.createGridData(
				GridData.FILL_BOTH, SWT.DEFAULT, 200));
		useCasesTree.addSelectionListener(new SelectionListener() {

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetDefaultSelected()
			 */
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			
			/*
			 * *
			 * 
			 * 
			 * 
			 * @see SelectionListener#widgetSelected()
			 */
			public void widgetSelected(SelectionEvent e) {
				updateTestCase((Tree) e.getSource());
			}

		});
	}
	
	/**
	 * 
	 * Verifies if a tree item in the Traceability Matrix tree should be displayed expanded or not.
	 * @param item the tree item to be verified
	 */
	private void verifyExpandTreeItem(TreeItem item)
	{
	    boolean hasRemovedTestCase = false;
        
        for (int i = 0; i < item.getItems().length && !hasRemovedTestCase; i++)
        {
            //checks if the tree item contais removed test cases
            if (header.isRemovedTCCheckBoxChecked()
                    && item.getItems()[i].getForeground().equals(
                            new Color(item.getDisplay(), OnTheFlyUtil.REMOVED_ITEM_RGB)))
            {
                hasRemovedTestCase = true;
            }
        }
        //verifies if the tree item was selected in the selection tab
        //if the foreground color is gray the item was not selected
        if (item.getForeground().equals(new Color(item.getDisplay(), OnTheFlyUtil.GRAY_RGB)))
        {
            if(hasRemovedTestCase)
            {
                item.setExpanded(true);
            }
            else
            {
                item.setExpanded(false);
            }            
        }
        //the item was selected and should be expanded
        else
        {
            item.setExpanded(true);
        }
	}

	/**
	 * Fills in the requirements tree.
	 */
	private void fillRequirementsTree() {

		requirementsTree.removeAll();
		List<String> requirementsList = new ArrayList<String>(requirementsTestCases.keySet());
		Collections.sort(requirementsList, StringsUtil.getNaturalComparator());
		
		for (String requirement : requirementsList) {
			TreeItem reqItem = new TreeItem(requirementsTree, SWT.SINGLE
					| SWT.Expand | SWT.H_SCROLL | SWT.V_SCROLL);

			reqItem.setText(requirement);
			reqItem.setData(requirement);
			reqItem.setImage(requirementImage);

			if (!selectionPage.isRequirementSelected(requirement)) {
				reqItem.setForeground(new Color(reqItem.getDisplay(),
						OnTheFlyUtil.GRAY_RGB));
			}
			
            List<TextualTestCase> testCasesFromRequirement = getTestCasesFromRequirement(requirement,
                    wholeTestSuite);
			
			for (TextualTestCase testCase : testCasesFromRequirement) {
				createTestCaseTreeItem(reqItem, testCase,
						new HashSet<TextualTestCase>());
			}
		}
        for (TreeItem item : requirementsTree.getItems())
        {
            verifyExpandTreeItem(item);
        }
	}

	/**
	 * Fills in the use cases tree.
	 */
	private void fillUseCasesTree() {
		useCasesTree.removeAll();
		common = 0;
		added = 0;
		removed = 0;

		Set<TextualTestCase> testCases = new HashSet<TextualTestCase>();
		
		List<String> useCasesList = new ArrayList<String>(useCaseTestCases.keySet());
        Collections.sort(useCasesList, StringsUtil.getNaturalComparator());

		for (String useCase : useCasesList) {
			TreeItem ucItem = new TreeItem(useCasesTree, SWT.SINGLE
					| SWT.H_SCROLL | SWT.V_SCROLL);

			ucItem.setText(useCase);
			ucItem.setData(useCase);
			ucItem.setImage(useCaseImage);

			StringTokenizer token = new StringTokenizer(useCase, "#");
			if (token.countTokens() >= 2) {
				if (!this.selectionPage.isUseCaseSelected(token.nextToken(),
						token.nextToken())) {
					ucItem.setForeground(new Color(ucItem.getDisplay(),
							OnTheFlyUtil.GRAY_RGB));
				}
			}
			
			List<TextualTestCase> testCasesFromUseCase = getTestCasesFromUseCase(useCase,
                    wholeTestSuite);
			
			for (TextualTestCase testCase : testCasesFromUseCase) {
				createTestCaseTreeItem(ucItem, testCase, testCases);
			}

		}

		header.setCheckBoxesText(added, common, removed);

        for (TreeItem item : useCasesTree.getItems())
        {
            verifyExpandTreeItem(item);
        }

	}

	/**
	 * Create a test case tree item. It is painted according its status: common,
	 * new or removed.
	 * 
	 * @param parent
	 *            The tree node which will be the parent of the new tree item.
	 * @param testCase
	 *            The test case which will be added in the tree.
	 * @param testCases
	 *            The set of test cases in which the <code>testCase</code>
	 *            instance will be verified whether it is contained or not.
	 */
	private void createTestCaseTreeItem(TreeItem parent,
			TextualTestCase testCase, Set<TextualTestCase> testCases) {

		RGB itemRGB = null;

		if (currentTestCasesIDs.contains(testCase.getId())
				&& formerTestCasesIDs.contains(testCase.getId())) {
			if (!testCases.contains(testCase)) {
				common++;
				testCases.add(testCase);
			}

			if (header.isCommonTCCheckBoxChecked()) {
				itemRGB = OnTheFlyUtil.COMMON_ITEM_RGB;
			}
		} else if (currentTestCasesIDs.contains(testCase.getId())
				&& !formerTestCasesIDs.contains(testCase.getId())) {
			if (!testCases.contains(testCase)) {
				added++;
				testCases.add(testCase);
			}
			if (header.isAddedTCCheckBoxChecked()) {
				itemRGB = OnTheFlyUtil.ADDED_ITEM_RGB;
			}
		} else if (!currentTestCasesIDs.contains(testCase.getId())
				&& formerTestCasesIDs.contains(testCase.getId())) {
			if (!testCases.contains(testCase)) {
				removed++;
				testCases.add(testCase);
			}
			if (header.isRemovedTCCheckBoxChecked()) {
				itemRGB = OnTheFlyUtil.REMOVED_ITEM_RGB;
			}
		}

		if (itemRGB != null) {
			TreeItem tcItem = new TreeItem(parent, SWT.SINGLE | SWT.H_SCROLL
					| SWT.V_SCROLL);
			tcItem.setForeground(new Color(tcItem.getDisplay(), itemRGB));
			tcItem.setData(testCase);
			tcItem.setText(testCase.getTcIdHeader() + " - "
					+ testCase.getObjective());
		}

	}

	/**
	 * Updates the test case which will be exhibited on test case table.
	 * 
	 * @param item
	 *            The <code>TreeItem</code> instance whose test case object will
	 *            be displayed on the test case table.
	 */
	private void updateTestCase(Tree item) {
		TreeItem treeItem = item.getSelection()[0];
		if (treeItem.getData() instanceof TextualTestCase) {
			tcTable.setTestCase((TextualTestCase) treeItem.getData());
		}
	}

	/**
	 * Group the test cases by requirements and by use cases.
	 */
	private void groupTestCases() {
		this.requirementsTestCases = new HashMap<String, List<TextualTestCase>>();
		this.useCaseTestCases = new HashMap<String, List<TextualTestCase>>();

		// Get all requirements and use cases
		Set<String> allReq = new HashSet<String>();
		Set<String> allUC = new HashSet<String>();

		ProjectManagerExternalFacade projectManagerFacade = ProjectManagerExternalFacade
				.getInstance();

		allReq.addAll(projectManagerFacade
				.getAllReferencedRequirementsOrdered());

		for (Feature feature : projectManagerFacade.getAllFeatures()) {
			for (UseCase useCase : feature.getUseCases()) {
				String reference = UseCaseUtil.getUseCaseReference(feature,
						useCase);
				allUC.add(reference);

			}
		}

		for (String requirement : allReq) {
			requirementsTestCases.put(requirement, getTestCasesFromRequirement(
					requirement, wholeTestSuite));
		}

		for (String useCase : allUC) {
			useCaseTestCases.put(useCase, getTestCasesFromUseCase(useCase,
					wholeTestSuite));
		}
	}

	/**
	 * Gets the test cases that contains a specific requirement.
	 * 
	 * @param requirement
	 *            The specific requirement.
	 * @param testSuite
	 *            The test suite with the test cases.
	 * @return A list of test cases that contains the specified requirement
	 */
	private List<TextualTestCase> getTestCasesFromRequirement(
			String requirement, TestSuite<TextualTestCase> testSuite) {
		List<TextualTestCase> result = new ArrayList<TextualTestCase>();

		for (TextualTestCase testcase : testSuite.getTestCases()) {
			if (testcase.getRequirements().contains(requirement)) {
				result.add(testcase);
			}
		}
		return result;
	}

	/**
	 * Gets the test cases that is generated from a specific use case.
	 * 
	 * @param useCase
	 *            The specific use case.
	 * @param testSuite
	 *            The test suite with the test cases.
	 * @return A list of test cases that contains the specified use case.
	 */
	private List<TextualTestCase> getTestCasesFromUseCase(String useCase,
			TestSuite<TextualTestCase> testSuite) {
		List<TextualTestCase> result = new ArrayList<TextualTestCase>();

		for (TextualTestCase testcase : testSuite.getTestCases()) {
			if (testcase.coversUseCase(useCase)) {
				result.add(testcase);
			}
		}
		return result;
	}

	/**
	 * Fills in a set with a list of test cases IDs.
	 * 
	 * @param testCases
	 *            The test cases
	 * @return A hashset with the test cases IDs.
	 */
	private HashSet<Integer> fillSetWithTestCasesIDs(
			List<TestCase<FlowStep>> testCases) {
		HashSet<Integer> set = new HashSet<Integer>();

		for (TestCase<FlowStep> testCase : testCases) {
			set.add(testCase.getId());
		}
		return set;
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
		for (TextualTestCase testCase : exclusionInclusionPage
				.getExcludedList()) {
			TestCase<FlowStep> rawTestCase = getEditor().getRawTestCase(
					testCase.getId());
			testSuite.remove(rawTestCase);
		}

		for (TextualTestCase testCase : exclusionInclusionPage
				.getIncludedList()) {
			if (!testSuite.contains(testCase)) {
				testSuite.add(getEditor().getRawTestCase(testCase.getId()));
			}
		}
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
	 * Updates the test suites and the trees, according to the filters settings
	 * and the list of included and excluded test cases
	 */
	public void updateTestCasesTrees() {
		
	    TestSuiteFilterAssembler currentFilterSelection = selectionPage.getFilterAssembler();
        
        if(!currentFilterSelection.equals(this.lastFilterSelection))
        {
            this.lastFilterSelection = currentFilterSelection;
            Set<TestCase<FlowStep>> currentTests = new HashSet<TestCase<FlowStep>>(
                    selectionPage.getCurrentTestSuite().getTestCases());
            
            verifyExcludedIncludedTestCases(currentTests);
            
            currentTestCasesIDs = fillSetWithTestCasesIDs(new ArrayList<TestCase<FlowStep>>(
                    currentTests));
            
            TestSuiteFilterAssembler formerAssembler = header.getFormerAssembler();
            
            TestSuite<TestCase<FlowStep>> formerTestSuite = formerAssembler.assemblyFilter()
                    .filter(this.getEditor().getRawTestSuite());
            
            formerTestCasesIDs = fillSetWithTestCasesIDs(formerTestSuite.getTestCases());

            fillRequirementsTree();
            fillUseCasesTree();
        }
	}

	/**
	 * 
	 * @see IWorkbenchPart#setFocus()
	 */
	public void setFocus() {
		super.setFocus();
		updateTestCasesTrees();
	}
}
