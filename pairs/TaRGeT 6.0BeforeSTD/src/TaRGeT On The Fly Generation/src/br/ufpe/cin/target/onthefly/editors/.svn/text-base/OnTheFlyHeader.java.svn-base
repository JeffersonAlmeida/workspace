/*
 * @(#)OnTheFlyHeader.java
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
 * wln013    Apr 03, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 * tnd783    Aug 25, 2008	LIBqq51204	 Changes made to create a header for on the fly pages.
 * dwvm83	 Nov 25, 2008				 Changes in method createCheckButtonsArea to fix check box bug.
 * pvcv      Apr 01, 2009				 Internationalization support
 * lmn3      Jan 11, 2009                Inclusion of a parameter for the name of filter selection
 */
package br.ufpe.cin.target.onthefly.editors;

import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyHeader;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyMultiPageEditor;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyUtil;
import br.ufpe.cin.target.tcg.filters.TestSuiteFilterAssembler;


/**
 * <pre>
 * CLASS:
 * This class provides a GUI to set which test cases shall be exhibited (common, added, removed) and to select, 
 * load, and save a filter configuration
 * 
 * RESPONSIBILITIES:
 * Exhibits the selected filter configuration. Indicates the number of common, new, and removed test cases
 * 
 * </pre>
 */
public class OnTheFlyHeader
{

    /**
     * Represents the label for the empty filter, in other words the filter without settings.
     */
    public static final String DEFAULT_SELECTION = "All Test Cases";

    /**
     * The check box that enable/disable common test cases exhibition on test cases tree.
     */
    private Button commonTestCasesCheckBox;

    /**
     * The check box that enable/disable added test cases exhibition on test cases tree.
     */
    private Button addedTestCasesCheckBox;

    /**
     * The check box that enable/disable removed test cases exhibition on test cases tree.
     */
    private Button removedTestCasesCheckBox;

    /**
     * The button that triggers the action to save a filter configuration.
     */
    private Button saveFilterSelectionButton;

    /**
     * The button that triggers the action to load a filter configuration.
     */
    private Button loadFilterSelectionButton;

    /**
     * The combo with filter selections names
     */
    private Combo formerFilterSelections;

    /**
     * The parent composite.
     */
    private Composite parent;

    /**
     * The map of filter name and filter assembler.
     */
    private HashMap<String, TestSuiteFilterAssembler> formerTestSuiteAssembler;

    /**
     * The list of headers. This list is necessary to maintain the consistency between the combos of
     * filter selections.
     */
    
    private List<OnTheFlyHeader> instances;

    /**
     * Creates a header for on the fly pages.
     * 
     * @param parent The composite parent.
     * @param editor The editor where the header will be created.
     */
    
    public OnTheFlyHeader(Composite parent, OnTheFlyMultiPageEditor editor)
    {
        this.parent = parent;
        this.formerTestSuiteAssembler = new HashMap<String, TestSuiteFilterAssembler>();
        this.createVisualComponents();
        this.instances = ((OnTheFlyMultiPageEditor) editor).getHeaders();
        addInstance(this);
    }

    /**
     * Adds a listener interested on check boxes events.
     * 
     * @param listener The check boxes listener
     */
    public void addCheckBoxesListener(SelectionListener listener)
    {
        this.addedTestCasesCheckBox.addSelectionListener(listener);
        this.commonTestCasesCheckBox.addSelectionListener(listener);
        this.removedTestCasesCheckBox.addSelectionListener(listener);
    }

    /**
     * Verify if the common check box is checked.
     * 
     * @return True if <code>commonTestCasesCheckBox</code> is selected, false otherwise.
     */
    public boolean isCommonTCCheckBoxChecked()
    {
        return this.commonTestCasesCheckBox.getSelection();
    }

    /**
     * Verify if the added check box is checked.
     * 
     * @return True if <code>addedTestCasesCheckBox</code> is selected, false otherwise.
     */
    public boolean isAddedTCCheckBoxChecked()
    {
        return this.addedTestCasesCheckBox.getSelection();
    }

    /**
     * Verify if the removed check box is checked.
     * 
     * @return True if <code>removedTestCasesCheckBox</code> is selected, false otherwise.
     */
    public boolean isRemovedTCCheckBoxChecked()
    {
        return this.removedTestCasesCheckBox.getSelection();
    }

    /**
     * Sets the text of check boxes, informing the number of each kind of test case.
     * 
     * @param addedTestCases The number of added test cases.
     * @param commonTestCases The number of common test cases.
     * @param removedTestCases The number of removed test cases.
     */
    public void setCheckBoxesText(int addedTestCases, int commonTestCases, int removedTestCases)
    {
        this.addedTestCasesCheckBox.setText(Properties.getProperty("new_test_cases")+" (" + addedTestCases + ")");
        this.commonTestCasesCheckBox.setText(Properties.getProperty("common_test_cases")+" (" + commonTestCases + ")");
        this.removedTestCasesCheckBox.setText(Properties.getProperty("removed_test_cases")+" (" + removedTestCases + ")");
    }

    /**
     * Adds a listener for <code>formerFilterSelections</code> events.
     * 
     * @param listener The filter selection combo listener.
     */
    public void addFormerFilterSelectionListener(SelectionListener listener)
    {
        this.formerFilterSelections.addSelectionListener(listener);
    }

    /**
     * Adds a listener for <code>loadFilterSelectionButton</code> events.
     * 
     * @param listener The load filter button listener.
     */
    public void addLoadFilterButtonListener(SelectionListener listener)
    {
        this.loadFilterSelectionButton.addSelectionListener(listener);
    }

    /**
     * Adds a listener interested on <code>saveFilterSelectionButton</code> events.
     * 
     * @param listener The save filter button listener.
     */
    public void addSaveFilterButtonListener(SelectionListener listener)
    {
        this.saveFilterSelectionButton.addSelectionListener(listener);
    }

    /**
     * Adds a <code>TestSuiteFilterAssembler</code> in the list of available filters.
     * 
     * @param filter The test suite filter.
     * @param filterSelectionName the name of the filter selection
     */
    public void addFilter(TestSuiteFilterAssembler filter, String filterSelectionName)
    {
        for (OnTheFlyHeader header : instances)
        {
            if (!(header.parent.isDisposed()))
            {
                header.updateFilterList(filter, filterSelectionName);
            }
            else
            {
                instances.remove(header);
            }
        }
    }

    /**
     * Adds an instance of header on the list of instances. If an instance is created when another
     * one already exists, its map of test suite filters and its combo of test suites are filled
     * according to the map and combo of the existent header.
     * 
     * @param header The <code>OnTheFlyHeader</code> instance that will be catalogued.
     */
    private void addInstance(OnTheFlyHeader header)
    {
        if (instances.size() == 0)
        {
            header.formerTestSuiteAssembler.put(DEFAULT_SELECTION, new TestSuiteFilterAssembler());
            header.formerFilterSelections.add(DEFAULT_SELECTION);
        }
        else
        {
            OnTheFlyHeader firstInstance = instances.get(0);
            for (String filterName : firstInstance.formerTestSuiteAssembler.keySet())
            {
                header.formerTestSuiteAssembler.put(filterName,
                        firstInstance.formerTestSuiteAssembler.get(filterName));
            }
            header.formerFilterSelections.setItems(firstInstance.getFilterSelections());
        }
        header.formerFilterSelections.select(0);
        instances.add(header);
    }

    /**
     * Updates the list of filters.
     * 
     * @param filter The filter that will be added.
     * @param filterSelectionName the name of the filter selection
     */
    private void updateFilterList(TestSuiteFilterAssembler filter, String filterSelectionName)
    {
        String newItem = filterSelectionName;
        
        if(newItem.equals(""))
        {
            newItem = Properties.getProperty("selection")+" " + this.formerFilterSelections.getItemCount();
        }
        this.formerTestSuiteAssembler.put(newItem, filter);
        this.formerFilterSelections.add(newItem);
    }

    /**
     * Gets the list of filters available on <code>formerFilterSelections</code>.
     * 
     * @return The list of filters items.
     */
    private String[] getFilterSelections()
    {
        if (this.formerFilterSelections != null)
        {
            return this.formerFilterSelections.getItems();
        }
        else
            return new String[0];

    }

    /**
     * Gets the test suite filter associated with the selected item on
     * <code>formerFilterSelections</code>.
     * 
     * @return A test suite filter according to combo selection.
     */
    protected TestSuiteFilterAssembler getFormerAssembler()
    {
        String selectedItem = null;
        if (this.formerFilterSelections != null)
        {
            int index = this.formerFilterSelections.getSelectionIndex();
            selectedItem = this.formerFilterSelections.getItem(index);

        }
        else
        {
            selectedItem = DEFAULT_SELECTION;
        }
        return this.formerTestSuiteAssembler.get(selectedItem);

    }

    /**
     * Creates the visual components of the header: the check buttons and the filters area.
     * 
     * @param parent The composite parent.
     */
    private void createVisualComponents()
    {
        Composite mainContainer = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;

        mainContainer.setLayout(layout);
        GridData data = new GridData(GridData.FILL_BOTH);
        mainContainer.setLayoutData(data);
        mainContainer.pack(false);

        this.createCheckButtonsArea(mainContainer);
        this.createHistoryArea(mainContainer);
    }

    /**
     * Creates the check buttons area. It is composed of three buttons:
     * <code>commonTestCasesCheckBox</code>, <code>addedTestCasesCheckBox</code>, and
     * <code>removedTestCasesCheckBox</code>.
     * 
     * @param parent The composite parent.
     */
    private void createCheckButtonsArea(Composite parent)
    {

        Composite mainContainer = new Composite(parent, SWT.NONE);
        mainContainer.setLayout(new GridLayout());
        mainContainer.setLayoutData(new GridData(GridData.FILL_BOTH));

        createCheckButtons(mainContainer, SWT.CHECK);
        setCheckBoxesText(0, 0, 0);

        this.commonTestCasesCheckBox
                .setToolTipText(Properties.getProperty("displays_test_cases_that_are_common_to_the_current_and_the_former_test_suites"));
        this.commonTestCasesCheckBox.setSelection(true);
        this.commonTestCasesCheckBox.setLayoutData(OnTheFlyUtil.createGridData(GridData.FILL_VERTICAL,
                200, SWT.DEFAULT));

        this.addedTestCasesCheckBox
                .setToolTipText(Properties.getProperty("displays_test_cases_that_are_not_contained_in_the_former_test_suite_but_are_available_now"));
        this.addedTestCasesCheckBox.setSelection(true);
        this.addedTestCasesCheckBox.setLayoutData(OnTheFlyUtil.createGridData(GridData.FILL_VERTICAL,
                200, SWT.DEFAULT));

        this.removedTestCasesCheckBox
                .setToolTipText(Properties.getProperty("Displays_test_cases_that_are_contained_in_the_former_test_suite_but_are_unavailable_now"));
        this.removedTestCasesCheckBox.setSelection(false);
        this.removedTestCasesCheckBox.setLayoutData(OnTheFlyUtil.createGridData(GridData.FILL_VERTICAL,
                200, SWT.DEFAULT));

    }

    /**
     * Creates a check button according to an specific style.
     * 
     * @param parent The composite parent.
     * @param style The style that will be applied.
     */
    private void createCheckButtons(Composite parent, int style)
    {
        this.commonTestCasesCheckBox = new Button(parent, style);
        this.addedTestCasesCheckBox = new Button(parent, style);
        this.removedTestCasesCheckBox = new Button(parent, style);
    }

    /**
     * Creates the history area. It is composed of a combo: <code>formerFilterSelections</code>
     * and two buttons: <code>loadFilterSelectionButton</code> and
     * <code>saveFilterSelectionButton</code>.
     * 
     * @param parent The parent composite.
     */
    private void createHistoryArea(Composite parent)
    {
        Composite mainContainer = new Composite(parent, SWT.WRAP);

        mainContainer.setLayout(new GridLayout(1, false));
        mainContainer.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));

        GridData gridData = OnTheFlyUtil.createGridData(GridData.FILL_BOTH, 150, 25);

        this.formerFilterSelections = new Combo(mainContainer, SWT.DROP_DOWN | SWT.READ_ONLY);
        this.formerFilterSelections.setLayoutData(gridData);
        this.formerFilterSelections.setToolTipText(Properties.getProperty("saved_test_suite_filter_configurations"));
        this.formerFilterSelections.select(0);

        gridData = OnTheFlyUtil.createGridData(GridData.FILL_BOTH, 150, 25);
        this.loadFilterSelectionButton = new Button(mainContainer, SWT.PUSH);
        this.loadFilterSelectionButton.setText(Properties.getProperty("load_former_filter_selection"));
        this.loadFilterSelectionButton
                .setToolTipText(Properties.getProperty("Loads_a_saved_test_suite_filter_configuration_into_test_selection_page"));
        this.loadFilterSelectionButton.setLayoutData(gridData);

        gridData = OnTheFlyUtil.createGridData(GridData.FILL_BOTH, 150, 25);
        this.saveFilterSelectionButton = new Button(mainContainer, SWT.PUSH);
        this.saveFilterSelectionButton.setLayoutData(gridData);
        this.saveFilterSelectionButton.setText(Properties.getProperty("save_current_filter_selection"));
        this.saveFilterSelectionButton
                .setToolTipText(Properties.getProperty("saves_the_test_suite_filter_configuration_from_test_selection_page"));
    }

}
