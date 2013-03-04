/*
 * @(#)AddNewLexicalTermActionDelegate.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   May 14, 2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cnl.views;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.motorola.btc.research.target.cnl.controller.CNLPluginController;
import com.motorola.btc.research.target.cnl.dialogs.AddLexicalTermDialog;

public class AddNewLexicalTermActionDelegate implements IViewActionDelegate
{
    private Shell shell;
    
    private AddLexicalTermDialog addNewLexicalTermDialog;
    
    

    public void init(IViewPart view)
    {
        this.shell = view.getViewSite().getShell();
        this.addNewLexicalTermDialog = new AddLexicalTermDialog(this.shell);
    }


    public void run(IAction action)
    {
        List<String> list = CNLPluginController.getInstance().getOrderedUnkownWords();
        this.addNewLexicalTermDialog.open(list);

    }


    public void selectionChanged(IAction action, ISelection selection)
    {
        // TODO Auto-generated method stub

    }

}
