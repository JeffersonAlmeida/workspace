/*
 * @(#)FeaturesView.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Aug 04, 2006    LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerController inclusion.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * dhq348   May 23, 2007    LIBmm25975   Removed method  public static UseCase getSelectedUseCase().
 * dhq348   Jun 19, 2007    LIBmm47221   Modifications according to CR.
 * dhq348   Jul 11, 2007    LIBmm47221   Rework after inspection LX185000.
 * dhq348   Oct 01, 2007    LIBnn34008   Added the 'interruptions' folder.
 * dhq348   Jan 14, 2008    LIBnn34008   Rework after inspection LX229625.
 */
package com.motorola.btc.research.target.pm.views.features;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.ui.IViewSite;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.common.TargetTreeView;
import com.motorola.btc.research.target.pm.common.TreeObject;
import com.motorola.btc.research.target.pm.common.TreeViewContentProvider;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.editors.UseCaseHTMLGenerator;

/**
 * <pre>
 * CLASS:
 * This class presents the features and use cases extracted from the documents,
 * they are displayed in the left pane of the tool.
 * 
 * RESPONSABILITIES:
 * Presents the project features and use cases. So, the user can make a 
 * double click in some use case and the view is opened with 
 * its details. 
 * 
 * USAGE:
 * It can be explicitly referred by its ID or referred internally by RCP.
 * </pre>
 */
public class FeaturesView extends TargetTreeView
{
    /**
     * The public ID of the view that is referred in plugin.xml.
     */
    public static final String ID = "com.motorola.btc.research.target.pm.views.features.FeaturesView";

    /**
     * The default action that is responsible for opening an use case inside the tool.
     */
    private Action defaultViewAction;

    /**
     * The action that displays an use case in the default user html browser.
     */
    private Action htmlViewAction;

    /**
     * The root of the features tree.
     */
    private TreeObject featuresRoot;

    /**
     * The content provider of the view.
     */
    private TreeViewContentProvider contentProvider;

    /**
     * Enum that specifies the default view and the html view.
     */
    private enum ViewEnum {
        defaultView, htmlView
    };

    /**
     * Default constructor. Sets the attributes to null.
     */
    public FeaturesView()
    {
        super();
        this.contentProvider = null;
        this.featuresRoot = null;
    }

    /**
     * Implements the hook method.
     */

    protected void hook()
    {
    }

    /**
     * Fill context menu with options
     * 
     * @param The menu manager to which the actions will be added to.
     */
    protected void fillContextMenu(IMenuManager manager)
    {
        IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

        if (selection.size() == 1)
        {
            TreeObject object = (TreeObject) selection.getFirstElement();
            if (object.hasChildren())
            {
                this.defaultViewAction.setEnabled(false);
                this.htmlViewAction.setEnabled(false);
            }
            else if (object.getValue() instanceof UseCase)
            {
                this.defaultViewAction.setEnabled(true);
                this.htmlViewAction.setEnabled(true);
            }
        }
        else
        {
            this.defaultViewAction.setEnabled(false);
            this.htmlViewAction.setEnabled(false);
        }

        manager.add(this.defaultViewAction);
        manager.add(this.htmlViewAction);
    }

    /**
     * Instantiates defaultViewAction and htmlViewAction
     */

    protected void makeActions()
    {
        defaultViewAction = new Action()
        {
            public void run()
            {
                IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
                if (selection.size() == 1)
                {
                    TreeObject object = (TreeObject) selection.getFirstElement();
                    if (!object.hasChildren())
                    {
                        if (object.getValue() instanceof UseCase)
                        {
                            openUseCase(ViewEnum.defaultView);
                        }
                    }
                }
            }
        };
        defaultViewAction.setText("Open in default view...");

        htmlViewAction = new Action()
        {
            public void run()
            {
                IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
                if (selection.size() == 1)
                {
                    TreeObject object = (TreeObject) selection.getFirstElement();
                    if (!object.hasChildren())
                    {
                        if (object.getValue() instanceof UseCase)
                        {
                            openUseCase(ViewEnum.htmlView);
                        }
                    }
                }
            }
        };
        htmlViewAction.setText("Open with default browser...");
    }

    /**
     * Creates the double click action
     */
    private void makeDoubleClickAction()
    {
        doubleClickAction = new Action()
        {
            public void run()
            {
                IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

                if (selection.size() == 1)
                {
                    TreeObject object = (TreeObject) selection.getFirstElement();
                    if (object.getValue() instanceof UseCase)
                    {
                        openUseCase(ViewEnum.defaultView);
                    }

                }
            }
        };
    }

    /**
     * Opens the use case in a browser or in default view. Depending on the type of the view the
     * method call HTMLGenerator.openUseCaseInsideTool() or HTMLGenerator.openUseCaseInBrowser().
     * Both the selected feature and usecase are retrieved from the selected TreeObject.
     * 
     * @param view The enum used to retrieve the types of views.
     */
    private void openUseCase(ViewEnum view)
    {
        ISelection selection = viewer.getSelection();
        TreeObject treeObject = (TreeObject) ((IStructuredSelection) selection).getFirstElement();
        if (!treeObject.equals(this.featuresRoot) && !treeObject.hasChildren())
        {
            UseCase selectedUseCase = (UseCase) treeObject.getValue();
            Feature selectedFeature = (Feature) treeObject.getParent().getValue();
            try
            {
                if (selectedUseCase != null)
                {
                    List<com.motorola.btc.research.target.pm.errors.Error> errors = ProjectManagerController
                            .getInstance().getErrorList();
                    UseCaseHTMLGenerator generator = new UseCaseHTMLGenerator();
                    if (view.equals(ViewEnum.defaultView))
                    {
                        generator.openUseCaseInsideTool(selectedUseCase, selectedFeature, errors);
                    }
                    else
                    {
                        generator.openUseCaseInBrowser(selectedUseCase, selectedFeature, errors);
                    }
                }
            }
            catch (Exception e)
            {
                showMessage("Error opening the use case.  " + e.getMessage());
            }
        }
    }

    /**
     * Shows the String message in a Information Dialog
     * 
     * @param message The message to be displayed.
     */
    private void showMessage(String message)
    {
        MessageDialog.openInformation(viewer.getControl().getShell(), "Requirements", message);
    }

    /**
     * Sets the attributes to null.
     */

    public void clean()
    {
        this.defaultViewAction = null;
        this.htmlViewAction = null;
        this.featuresRoot = null;
        this.contentProvider = null;
    }

    /**
     * Updates the use cases according to the <code>features</code> passed as parameters.
     */
    private void updateViewContents()
    {
        Collection<Feature> features = ProjectManagerController.getInstance().getAllFeatures();


        if (this.contentProvider == null)
        {
            /* checks if there is at least one feature to be displayed */
            if ((features != null && features.size() > 0))
            {
            	this.contentProvider = this.createContentProvider(features);
                this.viewer.setContentProvider(this.contentProvider);

                this.viewer.setLabelProvider(new FeaturesTreeViewLabelProvider());
                this.viewer.setSorter(new ViewerSorter());
                this.viewer.setInput(this.getViewSite());
                this.makeDoubleClickAction();
                this.hookContextMenu();
            }
        }
        else
        {
            this.fillFeatureRoot(this.featuresRoot, features);
            this.viewer.refresh(); // refreshes the tree !!!
        }
    }

    
    /**
     * Creates the content provider that handles the nodes. It initializes the root nodes.
     * 
     * @param features The features to be added to the trees.
     * @return A content provider.
     */
    private TreeViewContentProvider createContentProvider(final Collection<Feature> features)
    {
        return new TreeViewContentProvider()
        {

            public void initialize()
            {
                featuresRoot = new TreeObject("Features");
                fillFeatureRoot(featuresRoot, features);

                superRoot = new TreeObject("");
                superRoot.addChild(featuresRoot);
            }


            public IViewSite getVSite()
            {
                return getViewSite();
            }
        };
    }


    /**
     * Fills the feature root. This method avoids feature duplication.
     * 
     * @param root The parent root.
     * @param features The features to be added.
     */
    private void fillFeatureRoot(TreeObject root, Collection<Feature> features)
    {
        for (Feature feature : features)
        {
            if (!this.existChild(feature.toString(), root))
            {
                TreeObject parent = new TreeObject(feature);
                for (UseCase useCase : feature.getUseCases())
                {
                    TreeObject to = new TreeObject(useCase);
                    parent.addChild(to);
                }
                root.addChild(parent);
            }
        }
    }


    /**
     * Verifies if there are removed objects in the features tree.
     */
    private void refreshRemovedFeatures()
    {
        if (this.featuresRoot != null)
        {
            Collection<Feature> features = ProjectManagerController.getInstance()
                    .getCurrentProject().getFeatures();
            TreeObject[] featureNodes = this.featuresRoot.getChildren();
            TreeObject[] usecaseNodes = null;
            for (int i = 0; i < featureNodes.length; i++)
            {
                Feature currentFeature = (Feature) featureNodes[i].getValue();
                if (!features.contains(currentFeature))
                {
                    this.featuresRoot.removeChild(featureNodes[i]);
                }

                if (featureNodes[i].hasChildren()) // checks the use cases names
                {
                    usecaseNodes = featureNodes[i].getChildren();
                    for (int j = 0; j < usecaseNodes.length; j++)
                    {
                        UseCase usecase = (UseCase) usecaseNodes[j].getValue();
                        if (currentFeature.getUseCase(usecase.getId()) != null)
                        {
                            this.featuresRoot.removeChild(featureNodes[i]);
                            break;
                        }
                    }
                }
            }
            this.viewer.refresh();
        }
    }


    /**
     * Encapsulates calls to all update methods of the current view.
     */
    public void update()
    {
        refreshRemovedFeatures();
        updateViewContents();
    }
}
