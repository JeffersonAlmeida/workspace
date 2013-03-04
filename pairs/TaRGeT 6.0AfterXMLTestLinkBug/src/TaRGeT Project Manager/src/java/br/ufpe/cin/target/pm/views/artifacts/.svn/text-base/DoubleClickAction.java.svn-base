/*
 * @(#)DoubleClickAction.java
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
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * wmr068   Aug 07, 2008    LIBqq64190   Method openUseCaseDocument using the input document infrastructure.
 * fsf2     Jun 02, 2009                 Modified after code inspection.
 */
package br.ufpe.cin.target.pm.views.artifacts;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.PartInitException;

import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.common.TreeObject;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentData;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentExtensionFactory;


/**
 * Represents the action of double clicking a document.
 * 
 * <pre>
 * CLASS:
 * Represents the action of double clicking a document.
 * This action is used by <code>ArtifactsView</code> during the selection of an object in a tree.
 * This class uses the Desktop Java API to open the file using a default program. The class OfficeLauncher is not needed anymore.
 */
public class DoubleClickAction extends Action
{
    /**
     * The viewer that is used to get the current selection of a tree.
     */
    private TreeViewer viewer;

    /**
     * Initializes the viewer of this action.
     * 
     * @param viewer A treeviewer that is used to get the current selection of a tree.
     */
    public DoubleClickAction(TreeViewer viewer)
    {
        this.viewer = viewer;
    }

    /**
     * Implements the behaviour of the action. Opens a use case document or a test case depending on
     * the type of the tree object.
     */
    public void run()
    {
        try
        {

            ISelection selection = viewer.getSelection();
            TreeObject treeObject = (TreeObject) ((IStructuredSelection) selection)
                    .getFirstElement();
            if (treeObject instanceof DocumentTreeObject)
            {
                DocumentTreeObject dto = (DocumentTreeObject) treeObject;
                if (dto.getDocumentType().equals(DocumentType.useCase))
                {
                    this.openUseCaseDocument(dto.getAbsolutePath());
                }
                else
                {
                    this.openTestSuiteDocument(dto.getAbsolutePath());
                }
            }
        }
        catch (PartInitException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Used to open the selected use case document.
     * 
     * @param fileName The name fo the file to be opened.
     * @throws IOException Thrown when it is not possible to open the specified file due to OS
     * problems.
     * @throws PartInitException Thrown due to RCP internal exceptions.
     */
    private void openUseCaseDocument(String fileName) throws PartInitException, IOException
    {
        Collection<String> docNames = ProjectManagerController.getInstance().getCurrentProject()
                .getUseCaseDocumentFilePaths();
        for (String docName : docNames)
        {
            File file = new File(docName);
            if (fileName.equals(file.getAbsolutePath()))
            {
                String fileExtension = FileUtil.getFileExtension(file);

                InputDocumentData inputDocumentData = InputDocumentExtensionFactory
                        .getInputDocumentDataByExtension(fileExtension);
                
                inputDocumentData.getInputDocumentExtension().openInputDocument(file);
                break;
            }
        }
    }

    /**
     * Used to open the selected test suite.
     * 
     * @param fileName The name of the file to be opened.
     * @throws IOException Thrown when it is not possible to open the specified file due to OS
     * problems.
     * @throws PartInitException Thrown due to RCP internal exceptions.
     * @throws UnsupportedOperationException Thrown when the Desktop API is not supported 
     * by the current OS.
     */
    private void openTestSuiteDocument(String fileName) throws PartInitException, IOException
    {
        Collection<String> testSuites = ProjectManagerController.getInstance()
                .getTestSuiteDocuments();
        for (String testSuite : testSuites)
        {
            File file = new File(testSuite);
            if (fileName.equals(file.getAbsolutePath()))
            {
                if (Desktop.isDesktopSupported())
                {
                    Desktop.getDesktop().open(file);
                }
                else
                {
                    throw new UnsupportedOperationException(
                            "Desktop API not supported on the current platform");
                }
                break;
            }
        }
    }
}
