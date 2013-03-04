/*
 * @(#)GUIUtil.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dqh348   Dec 11, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 18, 2007    LIBkk11577   Rework of inspection LX133710.
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 */
package com.motorola.btc.research.target.pm.util;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.pm.common.TargetTreeView;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.views.artifacts.ArtifactsView;
import com.motorola.btc.research.target.pm.views.features.FeaturesView;

/**
 * Class that provides utility functions to all GUI classes.
 * 
 * <pre>
 * CLASS:
 * Class that provides utility functions to all GUI classes. E.g. closing a project or displaying an specified perspective.
 * 
 * RESPONSIBILITIES:
 * 1) Close a project
 * 2) Display an specified perspective
 
 * USAGE:
 * All methods should be accessed in a static way.
 * E.g. GUIUtil.closeProject(shell);
 */
public class GUIUtil
{
    /**
     * Closes the current opened project. 1) Closes the logical project 2) Cleans the views 3)
     * Closes the perspective of the active page 4) Detach all observers of the error view 5)
     * Disables close project action 6) Clears the error list
     * 
     * @param shell The shell used to retrieve visual components.
     */
    public static void closeProject(Shell shell)
    {
        /* closes the target project */
        ProjectManagerController.getInstance().closeProject();

        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

        /* cleans all TargetTreeView data */
        ((TargetTreeView) GUIManager.getInstance().getView(FeaturesView.ID, false)).clean();
        ((TargetTreeView) GUIManager.getInstance().getView(ArtifactsView.ID, false)).clean();
        
        IWorkbenchPage page = window.getActivePage();
        page.closeEditors(page.getEditorReferences(), false);

        /* closes the active perspective */
        IPerspectiveDescriptor perspective = window.getActivePage().getPerspective();
        window.getActivePage().closePerspective(perspective, false, true);

        GUIUtil.setDefaultTitle();

    }

    /**
     * Displays the perspective specified by its id (perspectiveID).
     * 
     * @param perspectiveID The id of the perspective to be displayed.
     * @throws WorkbenchException Thrown when an error occurs when trying to display a perspective.
     */
    public static void showPerspective(String perspectiveID) throws WorkbenchException
    {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        workbench.showPerspective(perspectiveID, window);
    }

    /**
     * Method that opens a simple YES/NO dialog.
     * 
     * @param parent The parent shell of the dialog
     * @param title The dialog's title
     * @param message The message
     * @param dialogImageType The type of the image to be displayed. (E.g. MessageDialog.QUESTION)
     * @return <code>true</code> if the user presses the YES button or <code>false</code>
     * otherwise.
     */
    public static boolean openYesOrNo(Shell parent, String title, String message,
            int dialogImageType)
    {
        MessageDialog dialog = new MessageDialog(parent, title, null, message, dialogImageType,
                new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 0);
        return dialog.open() == 0;
    }

    /**
     * Method that opens a simple YES/NO dialog.
     * 
     * @param parent The parent shell of the dialog
     * @param title The dialog's title
     * @param message The message
     * @return <code>true</code> if the user presses the YES button or <code>false</code>
     * otherwise.
     */
    public static boolean openYesOrNoInformation(Shell parent, String title, String message)
    {
        return openYesOrNo(parent, title, message, MessageDialog.QUESTION);
    }

    /**
     * Method that opens a simple YES/NO dialog.
     * 
     * @param parent The parent shell of the dialog
     * @param title The dialog's title
     * @param message The message
     * @return <code>true</code> if the user presses the YES button or <code>false</code>
     * otherwise.
     */
    public static boolean openYesOrNoError(Shell parent, String title, String message)
    {
        return openYesOrNo(parent, title, message, MessageDialog.WARNING);
    }

    /**
     * Sets the title to its default value. It can be used to set the title when there is no opened
     * project in TaRGeT.
     */
    public static void setDefaultTitle()
    {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        shell.setText("TaRGeT - Test and Requirements Generation Tool");

    }

    /**
     * Sets the title using the given project name that is current opened in TaRGeT.
     * 
     * @param projectName The name of the opened project used to set the title.
     */
    public static void setCustomTitle(String projectName)
    {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        shell.setText(projectName + " - TaRGeT");
    }
}
