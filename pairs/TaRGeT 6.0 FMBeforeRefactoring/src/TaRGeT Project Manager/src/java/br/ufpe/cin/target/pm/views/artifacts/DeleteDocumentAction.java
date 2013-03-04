/*
 * @(#)DeleteDocumentAction.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 26, 2007    LIBll12753   Initial creation.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * tnd783   Aug 05, 2008    LIBqq51204   Changes in method deleteFiles.
 * pvcv     Apr 01, 2009				 Internationalization support
 */
package br.ufpe.cin.target.pm.views.artifacts;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.exceptions.TargetProjectLoadingException;
import br.ufpe.cin.target.pm.util.GUIUtil;

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
        this.setText(Properties.getProperty("delete"));
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

        String end = Properties.getProperty("the_selected_documents");

        if (referredDocuments.size() > 0)
        {
            files = files.substring(2);
            message = Properties.getProperty("the_documents") + " " + files + " "
                    + Properties.getProperty("refer_to_documents") + "\n";
            end = Properties.getProperty("them");
        }

        message += Properties.getProperty("want_to_delete") + " " + end;

        try
        {
            if (GUIUtil.openYesOrNoError(parent.getShell(), Properties.getProperty("delete"),
                    message))
            {
                deleteFiles(names);
            }
        }
        catch (TargetProjectLoadingException e)
        {
            MessageDialog.openError(parent.getShell(), Properties
                    .getProperty("error_while_deleting"), Properties.getProperty("can_not_delete"));
            e.printStackTrace();
        }
        catch (IOException e)
        {
            MessageDialog.openError(parent.getShell(), Properties
                    .getProperty("error_while_deleting"), Properties.getProperty("can_not_delete"));
            e.printStackTrace();
        }
        catch (TargetException e)
        {
            MessageDialog.openError(parent.getShell(), Properties
                    .getProperty("error_while_updating"), Properties.getProperty("can_not_update"));
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
            MessageDialog.openError(parent.getShell(), Properties
                    .getProperty("error_while_deleting"), Properties
                    .getProperty("can_not_delete_plus_permissions"));
        }
        else
        {
            /* reloads the visual part of the project */
            GUIManager.getInstance().refreshProject(parent.getShell(),false);
        }
    }
}
