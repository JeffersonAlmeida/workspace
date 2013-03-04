/*
 * @(#)TargetView.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 8, 2007    LIBkk11577   Initial creation.
 * dhq348   Jan 29, 2007   LIBll12753   Rework of inspection LX136878.
 */
package com.motorola.btc.research.target.pm.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.ViewPart;

/**
 * Generic view. This is the base class for all views in the project.
 * 
 * <pre>
 * CLASS:
 * This is the base class for all views in the project.
 */
public abstract class TargetView extends ViewPart
{
    /**
     * The parent component of the view.
     */
    protected Composite parent;

    /**
     * Creates the view visual components.
     * 
     * @param parent The parent component of the view.
     */
    @Override
    public final void createPartControl(Composite parent)
    {
        this.parent = parent;
        this.createIndividualControl();
        this.setActionEnablementCapability();
    }

    /**
     * Creates each individual view part control.
     */
    public abstract void createIndividualControl();

    /**
     * This is the default implementation of setFocus to all views.
     */
    @Override
    public void setFocus()
    {
    }

    /**
     * Sets the initial action selection, so the actions are initially unmarked (disabled). It is
     * implemented a basic StructuredViewer that only returns a list with a single item in
     * getSelectionFromWidget and a simple label as a control in getControl. This viewer is used
     * mainly because it was difficult to disable the actions at the tool startup, so the action now
     * in their xml declarations have the enablesfor attribute set to '+' which means that it begins
     * disabled and when any selection is activated their status is changed.
     */
    protected void setActionEnablementCapability()
    {
        StructuredViewer tmpViewer = new StructuredViewer()
        {
            @Override
            protected Widget doFindInputItem(Object element)
            {
                return null;
            }

            @Override
            protected Widget doFindItem(Object element)
            {
                return null;
            }

            @Override
            protected void doUpdateItem(Widget item, Object element, boolean fullMap)
            {
            }

            @Override
            protected List getSelectionFromWidget()
            {
                ArrayList<String> list = new ArrayList<String>();
                list.add("InitialActionSelection");
                return list;
            }

            @Override
            protected void internalRefresh(Object element)
            {
            }

            @Override
            public void reveal(Object element)
            {
            }

            @Override
            protected void setSelectionToWidget(List l, boolean reveal)
            {
            }

            /**
             * Gets a simple label as a control.
             */
            @Override
            public Control getControl()
            {
                Label label = new Label(parent.getShell(), SWT.NONE);
                return label;
            }
        };
        /* sets the new viewer as a selection provider of the current view */
        this.getSite().setSelectionProvider(tmpViewer);
        /* sets a single empty selection that enables any action waiting for a selection */
        tmpViewer.setSelection(new StructuredSelection());
    }

}
