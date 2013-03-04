/*
 * @(#)DeleteDocumentAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 26, 2007    LIBll12753   Initial creation.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * tnd783   Aug 05, 2008    LIBqq51204   Changes in method deleteFiles.
 */
package com.motorola.btc.research.target.pm.views.artifacts;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.exceptions.TargetProjectLoadingException;
import com.motorola.btc.research.target.pm.util.GUIUtil;

/**
 * Represents the action of deleting a document.
 * 
 * <pre>
 * CLASS:
 * Represents the action of deleting a document. 
 * This action is used by ArtifactsView during the deletion of an object in a tree.
 */
public class DeleteDocumentAction extends Action
{
    /**
     * The parent of this action.
     */
    private Composite parent;

    /**
     * The viewer that is used to get the current selection of a tree.
     */
    private TreeViewer viewer;

    /**
     * Initializes the parent of this action..
     * 
     * @param parent The component that is the parent of this action.
     * @param viewer A treeviewer that is used to get the current selection of a tree.
     */
    public DeleteDocumentAction(Composite parent, TreeViewer viewer)
    {
        this.parent = parent;
        this.viewer = viewer;
        this.setText("Delete");
    }

    /**
     * Implements the behaviour of the action. It deletes the selected documents.
     */
    public void run()
    {
        IStructuredSelection selection = (IStructuredSelection) this.viewer.getSelection();
        Iterator iterator = selection.iterator();
        String[] names = new String[selection.size()];
        int i = 0;
        while (iterator.hasNext())
        {
            names[i++] = ((DocumentTreeObject) iterator.next()).getAbsolutePath();
        }

        String message = "";
        String files = "";

        Collection<String> referredDocuments = ProjectManagerController.getInstance()
                .areFilesBeingReferred(Arrays.asList(names));
        for (String referredDocument : referredDocuments)
        {
            files += ", " + FileUtil.getFileName(referredDocument);
        }

        String end = "the selected documents?";

        if (referredDocuments.size() > 0)
        {
            files = files.substring(2);
            message = "The documents " + files
                    + " refer to at least one of the selected documents.\n";
            end = "them?";
        }

        message += "Do you want to delete " + end;

        try
        {
            if (GUIUtil.openYesOrNoError(parent.getShell(), "Delete", message))
            {
                deleteFiles(names);
            }
        }
        catch (TargetProjectLoadingException e)
        {
            MessageDialog.openError(parent.getShell(), "Error while deleting",
                    "Can not delete the document");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            MessageDialog.openError(parent.getShell(), "Error while deleting",
                    "Can not delete the document");
            e.printStackTrace();
        }
        catch (TargetException e)
        {
            MessageDialog.openError(parent.getShell(), "Error while updating search results",
                    "Can not update search results");
            e.printStackTrace();
        }
    }

    /**
     * Deletes the specified files.
     * 
     * @param names The names of the files to be deleted.
     * @throws IOException Thrown when the file can not be deleted due to operating system problems.
     * @throws TargetProjectLoadingException Thrown when an error occurs during the project
     * reloading.
     */
    
    private void deleteFiles(String[] names) throws TargetProjectLoadingException, IOException,
            TargetException
    {
        if (!FileUtil.deleteFiles(names))
        {
            MessageDialog
                    .openError(
                            parent.getShell(),
                            "Error while deleting",
                            "Could not delete the selected files.\n\nPlease verify the permissions and if it is being used by another application.");
        }
        else
        {
            /* reloads the visual part of the project */
            GUIManager.getInstance().refreshProject(parent.getShell());
        }
    }
}
