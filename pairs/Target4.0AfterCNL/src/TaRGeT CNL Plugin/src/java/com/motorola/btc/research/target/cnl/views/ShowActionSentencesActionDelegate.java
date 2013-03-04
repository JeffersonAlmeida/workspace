package com.motorola.btc.research.target.cnl.views;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.motorola.btc.research.target.cnl.controller.CNLPluginController;
import com.motorola.btc.research.target.common.exceptions.TargetException;

/*
 * @(#)ShowActionSentencesActionDelegate.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   May 26, 2008    LIBhh00000   Initial creation.
 */

public class ShowActionSentencesActionDelegate implements IViewActionDelegate
{
    private IViewPart parentView;


    public void init(IViewPart view)
    {
        this.parentView = view;
    }


    public void run(IAction action)
    {
        try
        {
            System.out.println(action.getId() + " - " + action.isChecked());
            CNLPluginController.getInstance().setShowActions(action.isChecked());
            CNLView.getView().update(true);
        }
        catch (TargetException e)
        {
            MessageDialog.openError(parentView.getSite().getShell(), "Error Refreshing Results", e.getMessage());
            e.printStackTrace();
        }
    }


    public void selectionChanged(IAction action, ISelection selection)
    {
        // TODO Auto-generated method stub

    }

}
