/*
 * @(#)ArtifactsView.java
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
 * dhq348   Dec 07, 2007    LIBkk11577   Added deleteDocumentAction and renameDocumentAction.
 * dhq348   Jan 25, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   Jan 14, 2008    LIBnn34008   Rework after inspection LX229625.
 */
package com.motorola.btc.research.target.pm.views.artifacts;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewSite;

import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.ReloadProjectShellListener;
import com.motorola.btc.research.target.pm.common.TargetTreeView;
import com.motorola.btc.research.target.pm.common.TreeObject;
import com.motorola.btc.research.target.pm.common.TreeViewContentProvider;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;

/**
 * Represents the view that displays the documents tree and the test suites tree.
 * 
 * <pre>
 * CLASS:
 * This view displays the documents and test suites trees. It is also responsible 
 * for adding a shell listener that works for the whole environment and that reloads 
 * the project when the focus goes back to it. This class also creates the actions
 * (e.g. double click and right click) assigned to the objects of the trees.
 * 
 * RESPONSABILITIES:
 * 1) Displays documents and test suites.
 * 2) Add a shell listener.
 * 3) Creates the actions assigned to the objects.
 * 
 * USAGE:
 * It can be explicitly referred by its ID or referred internally by RCP.
 * </pre>
 */
public class ArtifactsView extends TargetTreeView
{
    /**
     * The public ID of the view that is referred in plugin.xml.
     */
    public static final String ID = "com.motorola.btc.research.target.pm.views.artifacts.ArtifactsView";

    /**
     * The tree node that is the parent of all documents.
     */
    private TreeObject featureNode;

    /**
     * The tree node that is the parent of all test suites.
     */
    private TreeObject testCaseNode;

    /**
     * The action responsible for deleting an object in the tree.
     */
    private Action deleteDocumentAction;

    /**
     * The action responsible for renaming an object in the tree.
     */
    private Action renameDocumentAction;

    /**
     * The shell listener. It is added to the main shell of the environment.
     */
    private ShellListener shellListener;

    /**
     * The content provider of the view.
     */
    private TreeViewContentProvider contentProvider;

    /**
     * Default constructor. Initializes the parent nodes and the double click action to null.
     */
    public ArtifactsView()
    {
        super();
        this.featureNode = new TreeObject("Features");
        this.testCaseNode = new TreeObject("TestCases");
        this.doubleClickAction = null;
    }


    /**
     * Implements the hook method from the superclass. In this class it adds a shell listnener to
     * the main shell.
     */
    
    protected void hook()
    {
        Shell shell = this.parent.getShell();

        if (shell != null)
        {
            if (shellListener == null)
            {
                shellListener = new ReloadProjectShellListener(shell);
                shell.addShellListener(shellListener); // adds the listener to the parent shell.
            }
        }
    }

    /**
     * Creates the content provider that handles the nodes. It initializes the root nodes.
     * 
     * @param labels The labels to be added to the trees.
     * @param type The type of the labels.
     * @return The content provider
     */
    private TreeViewContentProvider createContentProvider(final Collection<String> labels,
            final DocumentType type)
    {
        return new TreeViewContentProvider()
        {
            
            public void initialize()
            {
                if (type == DocumentType.useCase)
                {
                    // add the feature documents
                    fillFeatureNode(featureNode, labels);

                    // add the test suites
                    Collection<String> testSuites = ProjectManagerController.getInstance()
                            .getTestSuiteDocuments();
                    fillTestCaseNode(testCaseNode, testSuites);
                }
                else if (type == DocumentType.testCase)
                {
                    fillTestCaseNode(testCaseNode, labels);
                }

                superRoot = new TreeObject("");
                superRoot.addChild(featureNode);
                superRoot.addChild(testCaseNode);
            }

            
            public IViewSite getVSite()
            {
                return getViewSite();
            }
        };
    }

    /**
     * Fills the <code>featureNode</code> with the features. Iterates over the features and adds
     * them as children of the feature node.
     * 
     * @param featureNode The node to be filled.
     * @param features The features that will fill the node.
     */
    private void fillFeatureNode(TreeObject featureNode, Collection<String> features)
    {
        for (String feature : features)
        {
            String featureName = FileUtil.getFileName(feature);
            if (!this.existChild(featureName, featureNode))
            {
                DocumentTreeObject tdo = new DocumentTreeObject(featureName, feature,
                        DocumentType.useCase);
                featureNode.addChild(tdo);
            }
        }
    }

    /**
     * Fills the test case node. Iterates over the test suites to add them as children of the test
     * case node.
     * 
     * @param testCaseNode The node to be filled with the test cases.
     * @param testSuites The test suites that will fill the node.
     */
    private void fillTestCaseNode(TreeObject testCaseNode, Collection<String> testSuites)
    {
        for (String testSuite : testSuites)
        {
            String suiteName = FileUtil.getFileName(testSuite);
            if (!this.existChild(suiteName, testCaseNode))
            {
                DocumentTreeObject tdo = new DocumentTreeObject(suiteName, testSuite,
                        DocumentType.testCase);
                testCaseNode.addChild(tdo);
            }
        }
    }

    /**
     * Make the double click action. When a user make a double-click in feature document or test
     * suite document, an appropriate editor (MSWord or Excel) is opened. Note, a new implementation
     * of Action is developed.
     */
    
    protected void makeActions()
    {
        this.doubleClickAction = new DoubleClickAction(this.viewer);

        this.renameDocumentAction = new RenameDocumentAction(this.parent, this.viewer,
                this.featureNode, this.testCaseNode);

        this.deleteDocumentAction = new DeleteDocumentAction(this.parent, this.viewer);
    }

    /**
     * Fills context menu options.
     * 
     * @param manager The menu manager to which the actions will be added to.
     */
    protected void fillContextMenu(IMenuManager manager)
    {
        IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
        Iterator iterator = selection.iterator();
        boolean hasparent = false;
        while (iterator.hasNext())
        {
            TreeObject object = (TreeObject) iterator.next();
            if (!(object instanceof DocumentTreeObject))
            {
                this.renameDocumentAction.setEnabled(false);
                this.deleteDocumentAction.setEnabled(false);
                hasparent = true;
            }
        }

        if (!hasparent)
        {
            this.renameDocumentAction.setEnabled(selection.size() == 1);
            this.deleteDocumentAction.setEnabled(true);
        }

        manager.add(this.renameDocumentAction);
        manager.add(this.deleteDocumentAction);
    }

    /**
     * Sets the attributes to null.
     */
    
    public void clean()
    {
        this.featureNode = null;
        this.testCaseNode = null;
        this.deleteDocumentAction = null;
        this.renameDocumentAction = null;
        this.doubleClickAction = null;
    }

    /**
     * This method is used to update the tree viewer with feature documents and test suites. It
     * avoids duplications in the tree. It first checks if exists a featureNode. If it exists then
     * it verifies if the features are not null and if exists at least one of them. If the last
     * condition is verified the it instantiates a new content provider.
     */
    private void updateDocuments()
    {
        Collection<String> files = ProjectManagerController.getInstance().getCurrentProject()
                .getPhoneDocumentFilePaths();
        this.remove(this.featureNode, files);
        if (this.contentProvider == null)
        {
            this.initializeContentProvider(files, DocumentType.useCase);
        }
        else
        {
            this.fillFeatureNode(this.featureNode, files);
            this.viewer.refresh(); // refreshes the tree !!!
        }
    }

    /**
     * Updates the testsuites.
     */
    private void updateTestSuites()
    {
        Collection<String> names = ProjectManagerController.getInstance().getTestSuiteDocuments();
        this.remove(this.testCaseNode, names);

        if (this.contentProvider == null)
        {
            this.initializeContentProvider(names, DocumentType.testCase);
        }
        else
        {
            this.fillTestCaseNode(this.testCaseNode, names);
            this.viewer.refresh();
        }
    }

    /**
     * Initializes the content provider case any label exist.
     * 
     * @param labels The labels to be added to the content provider.
     * @param type The type of the labels.
     */
    private void initializeContentProvider(Collection<String> labels, DocumentType type)
    {
        /* checks if there is at least one feature to be displayed */
        if (labels != null && labels.size() > 0)
        {
            contentProvider = createContentProvider(labels, type);
            this.viewer.setContentProvider(contentProvider);

            this.viewer.setLabelProvider(new ArtifactsTreeViewLabelProvider());

            this.viewer.setSorter(new ViewerSorter());
            this.viewer.setInput(this.getViewSite());

            this.hookContextMenu();
        }
    }

    /**
     * Remove the children from root that do not exist anymore.
     * 
     * @param root The root in which the children will be searched.
     * @param names The existing names.
     */
    private void remove(TreeObject root, Collection<String> names)
    {
        if (root != null)
        {
            TreeObject[] array = root.getChildren();
            for (int i = 0; i < array.length; i++)
            {
                if (!this.hasElements(array[i].getValue().toString(), names))
                {
                    root.removeChild(array[i]);
                }
            }
            this.viewer.refresh();
        }
    }

    /**
     * Verifies if there is a file name in a list of absolute file names.
     * 
     * @param name The name to be verified.
     * @param names The names where <code>name</code> will be searched.
     * @return <i>True</i> if the name is in <code>names</code> or <i>false</i> otherwise.
     */
    private boolean hasElements(String name, Collection<String> names)
    {
        boolean result = false;

        for (String string : names)
        {
            if (name.equals(FileUtil.getFileName(string)))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Encapsulates calls to all update methods of the current view.
     */
    public void update()
    {
        updateDocuments();
        updateTestSuites();
    }
}
