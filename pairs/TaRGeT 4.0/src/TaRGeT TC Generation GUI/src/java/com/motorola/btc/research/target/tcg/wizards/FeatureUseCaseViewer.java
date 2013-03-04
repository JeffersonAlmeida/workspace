/*
 * @(#)FeatureUseCaseViewer.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Nov 27, 2007   LIBoo10574   Initial creation.
 */
package com.motorola.btc.research.target.tcg.wizards;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.part.DrillDownAdapter;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.common.TreeObject;
import com.motorola.btc.research.target.pm.common.TreeViewContentProvider;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;

/**
 * Viewer that displays a tree of Feature/UseCases/FlowSteps. It can be configured to display only
 * use cases level or the full flow steps level. It will only render the tree after the call of
 * method populate.
 */
public class FeatureUseCaseViewer
{
    /**
     * The root of the tree.
     */
    private TreeObject root;

    /**
     * The viewer that manages tree operations.
     */
    private TreeViewer treeViewer;

    /**
     * Indicates if the steps level will be displayed. If <code>false</code> then only the use case
     * level will be displayed.
     */
    private boolean displaySteps;

    /**
     * Creates the viewer given its <code>parent</code> and a boolean indicating if the steps
     * level will be displayed.
     * 
     * @param parent The parent of the components that will be created.
     * @param displaySteps Indicates if the steps level will be displayed.
     */
    public FeatureUseCaseViewer(Composite parent, boolean displaySteps)
    {
        this.displaySteps = displaySteps;

        root = new TreeObject("");
        fillRoot();

        treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        new DrillDownAdapter(treeViewer);
        treeViewer.setSorter(new ViewerSorter());
        treeViewer.setContentProvider(this.createContentProvider());
        treeViewer.setLabelProvider(new FeatureUseCaseLabelProvider());

        GridData treeData = new GridData(GridData.FILL_BOTH);
        treeData.horizontalSpan = 3;
        treeData.heightHint = 75;
        treeViewer.getTree().setLayoutData(treeData);
    }

    /**
     * Fills the root node with the features/usecases/flowsteps.
     */
    private void fillRoot()
    {
        Collection<Feature> features = ProjectManagerExternalFacade.getInstance().getAllFeatures();
        for (Feature feature : features)
        {
            TreeObject featureNode = new TreeObject(feature);
            for (UseCase useCase : feature.getUseCases())
            {
                TreeObject useCaseNode = new TreeObject(useCase);
                featureNode.addChild(useCaseNode);

                if (displaySteps)
                {
                    for (StepId stepId : useCase.getActualStepIds())
                    {
                        TreeObject flowStepNode = new TreeObject(useCase.getFlowStepById(stepId));
                        useCaseNode.addChild(flowStepNode);
                    }
                }
            }
            root.addChild(featureNode);
        }
    }

    /**
     * Creates an empty content provider.
     */
    private TreeViewContentProvider createContentProvider()
    {
        return new TreeViewContentProvider()
        {
            @Override
            public void initialize()
            {
            }

            @Override
            public IViewSite getVSite()
            {
                return null;
            }

        };
    }

    /**
     * @return The current selection.
     */
    public ISelection getSelection()
    {
        return treeViewer.getSelection();
    }

    /**
     * @return The tree managed by the current viewer.
     */
    public Tree getTree()
    {
        return treeViewer.getTree();
    }


    /**
     * @return The root node of the tree.
     */
    public TreeObject getRoot()
    {
        return root;
    }

    /**
     * Displays the tree nodes. Only after a call to this method the tree will be displayed.
     */
    public void populate()
    {
        treeViewer.setInput(root);
    }
}
