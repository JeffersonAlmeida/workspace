/*
 * @(#)ReloadBasesActionDelegate.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   Apr 15, 2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cnl.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.motorola.btc.research.target.cnl.controller.CNLPluginController;

public class ReloadBasesActionDelegate implements IViewActionDelegate
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
            CNLPluginController.getInstance().startController();
            CNLView.getView().update(true);
            System.out.println("Knowledge bases updated!");
        }
        catch (Exception e)
        {
            MessageDialog.openError(parentView.getSite().getShell(), "Error reloading bases", e.getMessage());
            e.printStackTrace();
        }
    }


    public void selectionChanged(IAction action, ISelection selection)
    {
        // TODO Auto-generated method stub

    }

}
