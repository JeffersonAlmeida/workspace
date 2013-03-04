/*
 * @(#)TestPurposeCreationPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Aug 01, 2007    LIBmm42774   Initial creation.
 * dhq348    Aug 21, 2007    LIBmm42774   Rework after inspection LX201094.
 * dhq348    Aug 27, 2007    LIBmm42774   Rework after inspection LX201876.
 * dhq348    Sep 21, 2007    LIBnn34865   Modification according to CR.
 * dhq348    Nov 05, 2007    LIBnn41846   Updated title and description.
 * dhq348    Nov 27, 2007    LIBoo10574   Changed TreeViewer by FeatureUseCaseViewer.
 * dhq348    Jan 23, 2008    LIBoo10574   Rework after inspection LX229632.
 * wdt022    Mar 20, 2008    LIBpp56482   Updated widgets and components arrangement in the screen.
 */
package com.motorola.btc.research.target.tcg.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.pm.common.TreeObject;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.filters.TestPurpose;

/**
 * This page is responsible for creating the test purposes and managing path coverage filter. It
 * handles the widgets used to create the string representing the test purposes and a scale object
 * related to the choice of the path coverage percentage.
 */
public class TestPurposeCreationPage extends WizardPage implements TCGPage
{
    /**
     * Viewer representing the available steps.
     */
    private FeatureUseCaseViewer viewer;

    /**
     * Represents the visual component of the current test purpose.
     */
    private TableViewer currentPurposeList;

    /**
     * The list of test purposes.
     */
    private TableViewer purposesList;

    /**
     * The hash map of step IDs to flowsteps
     */
    private HashMap<String, FlowStep> steps;

    /**
     * The object that represents the similarity slider.
     */
    private Scale scale;

    /**
     * Text component that displays the current similarity.
     */
    private Text similarityText;

    public boolean isEditorPage;

    /**
     * Constructor that sets the page's title and description.
     */
    public TestPurposeCreationPage()
    {
        super("");
        setTitle("Test Suite Generation by Purpose");
        setDescription("Create the test purposes.");
        setPageComplete(false);
    }

    public TestPurposeCreationPage(boolean isEditor)
    {
        this();
        this.isEditorPage = isEditor;
    }

    /**
     * Creates the visual components that are responsible for creating the test purposes. It creates
     * 1) a field for selecting steps in order to create a purpose, 2) a field for displaying the
     * current purpose that is being created and 3) a table for displaying the already created
     * purposes.
     * 
     * @param parent The parent of the components to be created.
     * @return The main component of the page. It is used to set the control of the wizard.
     */
    public Composite createVisualComponents(Composite parent)
    {
        /* Creates a main container for the page */
        int columns = 1;
        if (this.isEditorPage)
        {
            columns = 2;
        }
        Composite mainContainer = this.createBasicContainer(parent, columns);

        Composite topContainer = this.createBasicContainer(mainContainer, 2);
        this.createStepsField(topContainer);
        topContainer = this.createBasicContainer(mainContainer, 2);
        this.createCurrentPurpose(topContainer);
        topContainer = this.createBasicContainer(mainContainer, 2);
        this.createPurposesList(topContainer);

        /* the slide bar */
        Composite bottomContainer = this.createBasicContainer(mainContainer, 2);
        this.createSliderBar(bottomContainer);

        /* Fills the necessary data (available steps) */
        this.fillData();

        /* Setting the page control. Otherwise the page will not be displayed */
        setControl(mainContainer);

        return mainContainer;
    }

    /**
     * Hook method that is responsible for creating the visual components of the current page. Its
     * through this method that all the other method of the class are called or activated.
     * 
     * @param parent The parent component of the current page.
     */
    public void createControl(Composite parent)
    {
        createVisualComponents(parent);
    }

    /**
     * Creates the visual components that are responsible for creating the basic test purposes. The
     * method basically creates two containers: one for the text component containing the string
     * representing the current test purpose and another for the buttons that select and discard the
     * purpose.
     * 
     * @param parent The parent of the components being created.
     */
    private void createCurrentPurpose(Composite parent)
    {
        Composite leftContainer = this.createBasicGroup(parent, "Current Test Purpose", 1);
        this.currentPurposeList = this.createTableViewer(leftContainer);
        this.currentPurposeList.setInput(new ArrayList<String>());

        Composite rightContainer = this.createBasicContainer(parent, 2);
        GridData gd = new GridData();
        gd.grabExcessVerticalSpace = true;
        gd.minimumWidth = 120;
        rightContainer.setLayoutData(gd);

        Button moveUpButton = this.createButton(rightContainer, "up", 40, 25);
        Button selectCurrentPurpose = this.createButton(rightContainer, "OK");
        Button moveDownButton = this.createButton(rightContainer, "down", 40, 25);
        Button discardCurrentPurpose = this.createButton(rightContainer, "Clean");

        this.createSelectCurrentPurposeListener(selectCurrentPurpose);
        this.createDiscardCurrentPurposeListener(discardCurrentPurpose);
        this.createMoveUpStepListener(moveUpButton);
        this.createMoveDownStepListener(moveDownButton);
    }

    /**
     * Creates the visual components that manage the list of flow steps.
     * 
     * @param parent The parent of the visual components being created.
     */
    private void createStepsField(Composite parent)
    {
        Composite leftContainer = this.createBasicGroup(parent, "Steps", 1);

        viewer = new FeatureUseCaseViewer(leftContainer, true);

        Composite rightContainer = this.createBasicContainer(parent, 1);
        GridData gd = new GridData();
        gd.grabExcessVerticalSpace = true;
        rightContainer.setLayoutData(gd);
        Button selectStepsButton = this.createButton(rightContainer, "Select");
        this.createSelectStepsListener(selectStepsButton);
    }

    /**
     * Creates the component responsible for displaying the list of already created test purposes.
     * 
     * @param parent The parent component of the list being created.
     */
    private void createPurposesList(Composite parent)
    {
        Composite leftContainer = this.createBasicGroup(parent, "Created Test Purposes", 1);

        this.purposesList = this.createTableViewer(leftContainer);
        this.purposesList.setInput(new ArrayList<String>());

        Composite rightContainer = this.createBasicContainer(parent, 2);
        GridData gd = new GridData();
        // gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        rightContainer.setLayoutData(gd);
        Button cleanSelectedEntry = this.createButton(rightContainer, "Clean");
        Button cleanAllEntries = this.createButton(rightContainer, "Clean all");
        this.createCleanSelectedEntryListener(cleanSelectedEntry);
        this.createCleanAllEntriesListener(cleanAllEntries);
    }

    private TableViewer createTableViewer(Composite container)
    {
        TableViewer result = new TableViewer(new Table(container, SWT.MULTI | SWT.V_SCROLL
                | SWT.FULL_SELECTION));
        Table table = result.getTable();
        table.setBounds(0, 0, 600, 100);
        table.setLinesVisible(true);
        table.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        this.setTableProviders(result);

        return result;
    }

    /**
     * Creates a basic container for other visual components. Sets a GridLayout and a GridData for
     * the layout data.
     * 
     * @param parent The parent component of the container being created.
     * @param numberOfColumns The number of the columns that the component will support.
     * @return A basic container component.
     */
    private Composite createBasicContainer(Composite parent, int numberOfColumns)
    {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(numberOfColumns, false));
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return composite;
    }

    /**
     * Method that creates a basic Group component given the <code>parent</code> of the component,
     * its <code>title</code> and <code>numberOfColumns</code>. The method set a GridLayout and
     * a GridData as the layout data of the group.
     * 
     * @param parent The parent of the group being created.
     * @param title The title of the group being created.
     * @param numberOfColumns The number of columns in the layout attached to the group being
     * created.
     * @return A new Group component.
     */
    private Composite createBasicGroup(Composite parent, String title, int numberOfColumns)
    {
        Group group = new Group(parent, SWT.NULL);
        group.setText(title);
        group.setLayout(new GridLayout(numberOfColumns, false));
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return group;
    }

    /**
     * Creates a generic button given its <code>parent</code> and <code>label</code>. The
     * method also sets the default button size.
     * 
     * @param parent The parent of the button being created.
     * @param label The label set into the button.
     * @return A new button.
     */
    private Button createButton(Composite parent, String label)
    {
        return this.createButton(parent, label, 60, 25);
    }

    private Button createButton(Composite parent, String label, int width, int length)
    {

        Button button = new Button(parent, SWT.PUSH);
        button.setText(label);
        GridData gridData = new GridData();
        gridData.widthHint = width;
        gridData.heightHint = length;
        gridData.horizontalAlignment = SWT.CENTER;
        gridData.verticalAlignment = SWT.CENTER;
        button.setLayoutData(gridData);

        return button;
    }

    /**
     * Creates a slide bar. Actually it creates a Scale object with range from 0 to 100.
     * 
     * @param parent The parent of the bar being created.
     */
    private void createSliderBar(Composite parent)
    {
        Composite container = this.createBasicContainer(parent, 2);
        int initialValue = 100;

        String similarityStr = "Path Coverage: ";
        similarityText = new Text(container, SWT.READ_ONLY);
        similarityText.setBackground(parent.getBackground());
        similarityText.setText(similarityStr + initialValue + "%");

        scale = new Scale(container, SWT.HORIZONTAL);
        scale.setBounds(0, 0, 40, 200);
        scale.setMinimum(0);
        scale.setMaximum(100);
        scale.setIncrement(1);
        scale.setSelection(initialValue);

        scale.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                updateSimilarity();
            }
        });

        scale.addListener(SWT.MouseWheel, new Listener()
        {
            public void handleEvent(Event event)
            {
                updateSimilarity();
            }
        });
    }

    /**
     * Updates the similarity text component.
     */
    private void updateSimilarity()
    {
        String similarityStr = "Path Coverage: ";
        int perspectiveValue = scale.getSelection();
        this.similarityText.setText(similarityStr + perspectiveValue + "%");
    }

    /**
     * Fills the available steps with the steps and actions retrieved from the actual step ids of
     * the features.
     */
    private void fillData()
    {
        Collection<Feature> features = ProjectManagerExternalFacade.getInstance()
                .getCurrentProject().getFeatures();

        this.steps = new HashMap<String, FlowStep>();
        this.steps.put(TestPurpose.STAR_STEP.getId().getStepId(), TestPurpose.STAR_STEP);

        /* adds the start node */
        TreeObject starNode = new TreeObject(TestPurpose.STAR_STEP);
        viewer.getRoot().addChild(starNode);
        viewer.populate();

        for (Feature feature : features)
        {
            for (StepId stepId : feature.getActualStepIds())
            {
                FlowStep flowStep = feature.getUseCase(stepId.getUseCaseId()).getFlowStepById(
                        stepId);
                if (flowStep != null)
                {
                    this.steps.put(flowStep.toString(), flowStep);
                }
            }
        }
    }

    private void createMoveUpStepListener(Button button)
    {
        button.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            /**
             * Gets the current items in the list of test purposes, attaches the value of the
             * current purpose and sets a new value for the list of test purposes.
             */
            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                if (currentPurposeList.getTable().getSelectionCount() == 1)
                {
                    List<Object> list = (List<Object>) currentPurposeList.getInput();
                    int index = currentPurposeList.getTable().getSelectionIndex();

                    if (index > 0)
                    {
                        Object obj = list.remove(index);
                        list.add(index - 1, obj);
                        currentPurposeList.setInput(list);
                    }
                }
            }
        });
    }

    private void createMoveDownStepListener(Button button)
    {
        button.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            /**
             * Gets the current items in the list of test purposes, attaches the value of the
             * current purpose and sets a new value for the list of test purposes.
             */
            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                if (currentPurposeList.getTable().getSelectionCount() == 1)
                {
                    List<Object> list = (List<Object>) currentPurposeList.getInput();
                    int index = currentPurposeList.getTable().getSelectionIndex();

                    if (index < list.size() - 1)
                    {
                        Object obj = list.remove(index);
                        list.add(index + 1, obj);
                        currentPurposeList.setInput(list);
                    }
                }
            }
        });
    }

    /**
     * Creates a listener for the button responsible for selecting the current test purpose. The
     * method manages the insertion of new purposes in the list of purposes.
     * 
     * @param button The button in which the listener being created will be attached.
     */
    private void createSelectCurrentPurposeListener(Button button)
    {
        button.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            /**
             * Gets the current items in the list of test purposes, attaches the value of the
             * current purpose and sets a new value for the list of test purposes.
             */
            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                if (((List) currentPurposeList.getInput()).size() > 0)
                {
                    List purposes = (List) purposesList.getInput();
                    List<String> purposesString = new ArrayList<String>();
                    if (purposes != null)
                    {
                        for (Object object : purposes)
                        {
                            purposesString.add((String) object);
                        }
                    }
                    purposesString.add(this.getListAsString((List) currentPurposeList.getInput()));
                    purposesList.setInput(purposesString);
                }
                currentPurposeList.setInput(new ArrayList<String>()); // cleans the current text
            }

            @SuppressWarnings("unchecked")
            private String getListAsString(List list)
            {
                String result = "";
                for (Object obj : list)
                {
                    result += "; " + obj;
                }
                return result.substring(2).trim();
            }
        });
    }

    /**
     * Creates a listener for the button responsible for discarding the current test purpose.
     * 
     * @param button The button in which the listener being created will be attached.
     */
    private void createDiscardCurrentPurposeListener(Button button)
    {
        button.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            /**
             * Simply sets the <code>purpose</code> to an empty string.
             */
            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                ISelection selection = currentPurposeList.getSelection();
                if (selection instanceof StructuredSelection)
                {
                    StructuredSelection structuredSelection = (StructuredSelection) selection;
                    if (!structuredSelection.isEmpty())
                    {
                        for (Iterator iterator = structuredSelection.iterator(); iterator.hasNext();)
                        {
                            Object nextElement = iterator.next();
                            currentPurposeList.remove(nextElement);
                            ((ArrayList) currentPurposeList.getInput()).remove(nextElement);
                        }

                    }
                }
            }
        });
    }

    /**
     * Creates a listener for the button responsible for selecting steps and inserting them in a
     * test purpose being created.
     * 
     * @param button The button in which the listener being created will be attached.
     */
    private void createSelectStepsListener(Button button)
    {
        button.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            /**
             * Sets the value of the text component <code>purpose</code>. If necessary
             * concatenates any existing string.
             */
            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                ISelection selection = viewer.getSelection();
                if (!selection.isEmpty() && (selection instanceof TreeSelection))
                {
                    TreeSelection treeSelection = (TreeSelection) selection;
                    Iterator iterator = treeSelection.iterator();
                    while (iterator.hasNext())
                    {
                        Object object = iterator.next();
                        if (object instanceof TreeObject)
                        {
                            TreeObject treeObject = (TreeObject) object;
                            if (treeObject.getValue() instanceof FlowStep)
                            {
                                FlowStep flowStep = (FlowStep) treeObject.getValue();

                                List<String> purposeSteps = (List<String>) currentPurposeList
                                        .getInput();
                                String tmp = flowStep.getId().toString();
                                if (flowStep.getId().getStepId().equals("*"))
                                {
                                    tmp = flowStep.getId().getStepId();
                                }
                                purposeSteps.add(tmp);
                                currentPurposeList.setInput(purposeSteps);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Creates a listener for the button responsible for cleaning the selected entries in the table
     * of already created test purposes.
     * 
     * @param button The button in which the listener being created will be attached.
     */
    private void createCleanSelectedEntryListener(Button button)
    {
        button.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            /**
             * Gets the selection of the already created test purposes and removes them from the
             * list.
             */
            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                ISelection selection = purposesList.getSelection();
                if (selection instanceof StructuredSelection)
                {
                    StructuredSelection structuredSelection = (StructuredSelection) selection;
                    if (!structuredSelection.isEmpty())
                    {
                        for (Iterator iterator = structuredSelection.iterator(); iterator.hasNext();)
                        {
                            Object nextElement = iterator.next();
                            purposesList.remove(nextElement);
                            ((ArrayList) purposesList.getInput()).remove(nextElement);
                        }

                    }
                }
            }
        });
    }

    /**
     * Creates a listener for the button responsible for cleaning all entries in the table of
     * already created test purposes.
     * 
     * @param button The button in which the listener being created will be attached.
     */
    private void createCleanAllEntriesListener(Button button)
    {
        button.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            public void widgetSelected(SelectionEvent e)
            {
                purposesList.setInput(new ArrayList<String>());
            }
        });
    }

    /**
     * Sets the content provider and label provider for the specified <code>viewer</code>.
     * 
     * @param viewer The viewer in which the providers will be set.
     */
    private void setTableProviders(TableViewer viewer)
    {
        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new TableLabelProvider());
    }

    /**
     * Provides the text to a given tree viewer in which the current provider is attached.
     */
    private class TableLabelProvider extends LabelProvider implements ITableLabelProvider
    {
        public String getColumnText(Object element, int columnIndex)
        {
            String result = "";
            switch (columnIndex)
            {
                case 0:
                    result = (String) element;
                    break;
                default:
                    break;
            }
            return result;
        }

        public Image getColumnImage(Object element, int columnIndex)
        {
            return null;
        }
    }

    /**
     * Returns the user selected path coverage percentage.
     * 
     * @return The user selected path coverage percentage
     */
    public int getPathCoverage()
    {
        return this.scale.getSelection();
    }

    /**
     * Sets the path coverage percentage.
     * 
     * @param The path coverage percentage to be selected.
     */
    public void setPathCoverage(int pathCoverage)
    {
        this.scale.setSelection(pathCoverage);
        this.updateSimilarity();
    }

    /**
     * Returns the list of created purposes.
     * 
     * @return The list of the purposes created by the users.
     */
    @SuppressWarnings("unchecked")
    public List<TestPurpose> getPurposesList()
    {
        List<TestPurpose> result = new ArrayList<TestPurpose>();
        if (this.steps != null)
        {
            ArrayList pList = (ArrayList) this.purposesList.getInput();
            for (Object object : pList)
            {
                List<FlowStep> currentStepsRow = new ArrayList<FlowStep>();
                String currentPurpose = (String) object;
                String[] splitPurpose = currentPurpose.split(";");
                for (int i = 0; i < splitPurpose.length; i++)
                {
                    FlowStep currentFlowStep = this.steps.get(splitPurpose[i].trim());
                    currentStepsRow.add(currentFlowStep);
                }
                result.add(new TestPurpose(currentStepsRow));
            }
        }
        return result;
    }

    /**
     * Sets the list of purpose into the purpose table.
     * 
     * @param purposes The purposes to be set.
     */
    public void setPurposeList(Collection<TestPurpose> purposes)
    {
        List<String> newList = new ArrayList<String>();

        for (TestPurpose p : purposes)
        {
            newList.add(p.toString());
        }
        purposesList.setInput(newList);
    }

    @Override
    public boolean canFlipToNextPage()
    {
        return true;
    }
}
