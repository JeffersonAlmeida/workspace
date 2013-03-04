/*
 * @(#)TargetTreeView.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Nov 30, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   Oct 18, 2007    LIBnn34008   Updated method existChild().
 */
package com.motorola.btc.research.target.pm.common;

import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.DrillDownAdapter;

import com.motorola.btc.research.target.pm.controller.ProjectManagerController;

/**
 * An abstract target view that contains a tree viewer.
 * 
 * <pre>
 * CLASS:
 * An abstract target view that contains a tree viewer. 
 * This is the base class for all views that displays trees of objects.
 *
 * COLABORATORS:
 * 1) Uses TreeViewer
 * 2) Uses Action
 *
 * USAGE:
 * This class must be subclassed.
 */
public abstract class TargetTreeView extends TargetView
{
    /**
     * The tree viewer that handles trees of objects.
     */
    protected TreeViewer viewer;

    /**
     * Double click action assigned to documents.
     */
    protected Action doubleClickAction;

    /**
     * Create the GUI components.
     * 
     * @see com.motorola.btc.research.target.pm.common.TargetView#createIndividualControl()
     */
    
    public void createIndividualControl()
    {
        /*
         * This must be before returning to avoid the error: "An error has occurred when activating
         * this view"
         */
        this.viewer = new TreeViewer(this.parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        new DrillDownAdapter(this.viewer);

        /* There is an project open and no feature */
        Collection<String> featureDocuments = ProjectManagerController.getInstance()
                .getCurrentProject().getPhoneDocumentFilePaths();
        if (featureDocuments != null)
        {
            this.makeActions();
            this.hookDoubleClickAction();
            this.hook();
        }

    }

    /**
     * Delegates the focus request to the viewer's control.
     */
    public void setFocus()
    {
        this.viewer.getControl().setFocus();
    }

    /**
     * Hook method. This method when implemented by the subclasses represents the behaviour of the
     * view when it is being created. It may contains visual stuff.
     */
    protected abstract void hook();

    /**
     * Creates the actions related to objects of the tree.
     */
    protected abstract void makeActions();

    /**
     * Cleans all the instantiated objects. These objects should be initialized to null.
     */
    public abstract void clean();

    /**
     * Verifies if a child with name <code>childName</code> already exists in <code>parent</code>.
     * 
     * @param childName The name of the child which existence will be verified.
     * @param parent The tree parent that will be used for searching for the child existence.
     * @return <i>True</i> if exists a child in parent with the same name or <i>false</i>
     * otherwise.
     */
    protected boolean existChild(String childName, TreeObject parent)
    {
        TreeObject[] children = parent.getChildren();
        boolean result = false;
        for (int i = 0; i < children.length; i++)
        {
            if (children[i].getValue().toString().equals(childName))
            {
                result = true;
                break;
            }
        }
        return result;
    }
    
    /**
     * Refreshes the tree viewer.
     */
    public void refresh()
    {
        this.viewer.refresh();
    }




    /**
     * Hook the double click action with the viewer
     */
    private void hookDoubleClickAction()
    {
        this.viewer.addDoubleClickListener(new IDoubleClickListener()
        {
            public void doubleClick(DoubleClickEvent event)
            {
                doubleClickAction.run();
            }
        });
    }

    /**
     * Enables the context menu.
     */
    protected void hookContextMenu()
    {
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener()
        {
            public void menuAboutToShow(IMenuManager manager)
            {
                fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    /**
     * Fills context menu options. Each view has its specific menu items.
     * 
     * @param The menu manager to which the actions will be added to.
     */
    protected abstract void fillContextMenu(IMenuManager manager);
}
