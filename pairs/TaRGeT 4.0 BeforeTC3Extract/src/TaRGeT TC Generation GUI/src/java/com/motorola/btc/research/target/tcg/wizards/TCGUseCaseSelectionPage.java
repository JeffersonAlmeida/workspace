/*
 * @(#)TCGUseCaseSelectionPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Nov 13, 2007   LIBoo10574   Initial creation.
 * wdt022    Mar 17, 2008   LIBpp56482   setSelectedUseCases method added.
 * wln013    Apr 15, 2008   LIBpp56482   getUseCase, isUseCaseSelected, selectUseCase methods added.
 * wln013    May 16, 2008   LIBpp56482   Changes according to meeting 2 of inspection LX263835.
 */
package com.motorola.btc.research.target.tcg.wizards;

import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.common.TreeObject;

/**
 * Class that mounts the use case selection page for test case generation.
 */
public class TCGUseCaseSelectionPage extends WizardPage implements TCGPage
{
    /**
     * Viewer representing the available use cases.
     */
    private FeatureUseCaseViewer viewer;

    /**
     * TableViewer that manages the selected use cases.
     */
    private TableViewer usecasesTableViewer;

    /**
     * Stores the selected use cases.
     */
    private HashMap<Feature, List<UseCase>> selectedUseCases;

    /**
     * Sets the current page title and description.
     */
    public TCGUseCaseSelectionPage()
    {
        super("");
        setTitle("Test Suite Generation Wizard");
        setDescription("Select the use cases to cover or generate all tests.");
        setPageComplete(false);
        this.selectedUseCases = new HashMap<Feature, List<UseCase>>();
    }

    /**
     * Creates the visual components.
     * 
     * @param parent The parent of all components to be created.
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

        createTree(mainContainer);
        createButtons(mainContainer);
        createUseCasesTable(mainContainer);
        setControl(mainContainer);

        return mainContainer;
    }

    /**
     * Creates a use case tree given its <code>parent</code>.
     * 
     * @param parent The parent of the tree to be created.
     */
    private void createTree(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData(GridData.FILL_BOTH);
        group.setLayoutData(gridData);
        group.setText("Available Use Cases");

        viewer = new FeatureUseCaseViewer(group, false);
        viewer.populate();
    }

    /**
     * Creates the selected use cases table.
     * 
     * @param parent The parent of the table to be created.
     */
    private void createUseCasesTable(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData(GridData.FILL_BOTH);
        group.setLayoutData(gridData);
        group.setText("Selected Use Cases");

        this.usecasesTableViewer = new TableViewer(new Table(group, SWT.MULTI | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.FULL_SELECTION));
        this.usecasesTableViewer.setContentProvider(new ArrayContentProvider());
        this.usecasesTableViewer.setLabelProvider(new UseCasesLabelProvider());

        Table table = this.usecasesTableViewer.getTable();
        table.setLinesVisible(true);
        GridData tableGridData = new GridData(GridData.FILL_BOTH);
        tableGridData.horizontalSpan = 3;
        tableGridData.heightHint = 75;
        table.setLayoutData(tableGridData);
    }

    /**
     * Creates the add buttons and remove button. It also creates their listeners.
     * 
     * @param parent The parent of the buttons to be created.
     */
    private void createButtons(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, true);
        composite.setLayout(layout);

        Button addUseCaseButton = new Button(composite, SWT.PUSH);
        addUseCaseButton.setText("Add Use Case");
        addUseCaseButton.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                ISelection selection = viewer.getSelection();
                if (!selection.isEmpty())
                {
                    TreeSelection treeSelection = (TreeSelection) selection;
                    Iterator iterator = treeSelection.iterator();
                    while (iterator.hasNext())
                    {
                        Object object = iterator.next();
                        if (object instanceof TreeObject)
                        {
                            TreeObject treeObject = (TreeObject) object;
                            if (treeObject.getValue() instanceof UseCase)
                            {
                                UseCase useCase = (UseCase) treeObject.getValue();
                                Feature parentFeature = (Feature) treeObject.getParent().getValue();
                                List<UseCase> usecases = selectedUseCases.get(parentFeature);
                                if (usecases == null)
                                {
                                    usecases = new ArrayList<UseCase>();
                                }
                                if (!usecases.contains(useCase))
                                {
                                    usecases.add(useCase);
                                    selectedUseCases.remove(parentFeature);
                                    selectedUseCases.put(parentFeature, usecases);
                                    usecasesTableViewer.insert(useCase, -1);
                                }
                            }
                        }
                    }
                }
            }
        });

        Button removeUseCaseButton = new Button(composite, SWT.PUSH);
        removeUseCaseButton.setText("Remove Use Case");
        removeUseCaseButton.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            @SuppressWarnings("unchecked")
            public void widgetSelected(SelectionEvent e)
            {
                ISelection selection = usecasesTableViewer.getSelection();
                if (!selection.isEmpty())
                {
                    StructuredSelection structuredSelection = (StructuredSelection) selection;
                    Iterator iterator = structuredSelection.iterator();
                    while (iterator.hasNext())
                    {
                        Object object = iterator.next();
                        if (object instanceof UseCase)
                        {
                            UseCase useCase = (UseCase) object;
                            removeUseCase(useCase);
                        }
                    }
                }
            }
        });

    }

    /**
     * Removes <code>useCase</code> from the selected ones.
     * 
     * @param useCase The use case that will be removed.
     */
    private void removeUseCase(UseCase useCase)
    {
        for (Feature feature : selectedUseCases.keySet())
        {
            if (selectedUseCases.get(feature).contains(useCase))
            {

                selectedUseCases.get(feature).remove(useCase);

                if (selectedUseCases.get(feature).isEmpty())
                {
                    selectedUseCases.remove(feature);
                }
                usecasesTableViewer.remove(useCase);
            }
        }
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
     * A label provider to the available use cases tree.
     */
    private class UseCasesLabelProvider extends LabelProvider implements ITableLabelProvider
    {
        public Image getColumnImage(Object element, int columnIndex)
        {
            return null;
        }

        /**
         * @return FEATURE_ID#USECASE_ID
         */
        public String getColumnText(Object element, int columnIndex)
        {
            String result = "";
            if (element instanceof UseCase)
            {
                Feature feature = getFeature((UseCase) element);
                if (feature != null)
                {
                    result = feature.getId() + "#" + element.toString();
                }
            }
            return result;
        }
    }

    /**
     * Returns the feature from the given <code>usecase</code> in the set of selected use cases.
     * 
     * @param usecase The selected use case.
     * @return The feature from the given <code>usecase</code> or null if the use case does not
     * exist.
     */
    private Feature getFeature(UseCase usecase)
    {
        Feature result = null;
        for (Feature feature : selectedUseCases.keySet())
        {
            if (selectedUseCases.get(feature).contains(usecase))
            {
                result = feature;
                break;
            }
        }
        return result;
    }

    /**
     * Return objects representing the feature and the use case given as parameters.
     * 
     * @param feat The identifier for the feature of the use case.
     * @param uc The identifier for the use case that will be selected.
     * @return An array with the feature and use case objects.
     */
    private Object[] getUseCase(String feat, String uc)
    {
        TreeObject root = this.viewer.getRoot();
        Feature feature = null;
        UseCase useCase = null;

        for (TreeObject obj : root.getChildren())
        {
            if (obj.getValue() instanceof Feature
                    && (((Feature) obj.getValue()).getId()).equals(feat))
            {
                feature = (Feature) obj.getValue();
                for (TreeObject obj2 : obj.getChildren())
                {
                    if (obj2.getValue() instanceof UseCase
                            && (((UseCase) obj2.getValue()).getId()).equals(uc))
                    {
                        useCase = (UseCase) obj2.getValue();
                        break;
                    }
                }
                if (useCase != null)
                {
                    break;
                }

            }
        }
        
        Object[] result = new Object[2];
        result[0] = feature;
        result[1] = useCase;
        return result;
    }

    /**
     * Returns the selected use cases.
     * 
     * @return A map with the features and its selected use cases.
     */
    public HashMap<Feature, List<UseCase>> getSelectedUseCases()
    {
        return selectedUseCases;
    }

    /**
     * Verifies if the use case is selected.
     * 
     * @param feat The feature of the use case.
     * @param uc The use case that will be verified.
     * @return True if the use case is selected, false otherwise.
     */
    public boolean isUseCaseSelected(String feat, String uc)
    {
        Object[] featureUseCase = getUseCase(feat, uc);
        if (featureUseCase[1] != null)
        {
            List<UseCase> usecases = selectedUseCases.get(featureUseCase[0]);

            return (usecases != null && usecases.contains(featureUseCase[1]));
        }

        return false;
    }

    /**
     * Select a use case.
     * 
     * @param feat The feature of the use case.
     * @param uc The use case that will be selected.
     */
    public void selectUseCase(String feat, String uc)
    {
        Object[] featureUseCase = getUseCase(feat, uc);

        // verifies if the use case exist
        if (featureUseCase[1] != null)
        {
            Feature feature = (Feature) featureUseCase[0];
            UseCase useCase = (UseCase) featureUseCase[1];
            List<UseCase> usecases = selectedUseCases.get(feature);
            if (usecases == null)
            {
                usecases = new ArrayList<UseCase>();
            }
            if (!usecases.contains(useCase))
            {
                usecases.add(useCase);
                selectedUseCases.remove(feature);
                selectedUseCases.put(feature, usecases);
                usecasesTableViewer.insert(useCase, -1);
            }
        }

    }

    /**
     * Sets the use cases selection according to the list of use cases. These use cases will be in
     * the use cases filter.
     * 
     * @param useCases A map with features and the use cases that will be selected.
     */
    public void setSelectedUseCases(HashMap<Feature, List<UseCase>> useCases)
    {
        this.selectedUseCases = new HashMap<Feature, List<UseCase>>(useCases);
        List<UseCase> newList = new ArrayList<UseCase>();
        for (List<UseCase> ucList : this.selectedUseCases.values())
        {
            newList.addAll(ucList);
        }
        this.usecasesTableViewer.setInput(newList);
    }
}
