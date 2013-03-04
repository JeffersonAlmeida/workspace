/*
 * @(#)ArtifactsTreeViewLabelProvider.java
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
 * dhq348    Jan 9, 2007    LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007   LIBkk11577   Rework of inspection LX133710.
 * wmr068    Aug 07, 2008   LIBqq64190   Method getImage now loads icons according to the plug-ins that implement the input document infrastructure.
 */
package br.ufpe.cin.target.pm.views.artifacts;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.PMActivator;
import br.ufpe.cin.target.pm.common.TreeObject;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentData;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentExtensionFactory;

/**
 * <pre>
 * CLASS:
 * A simple label provider that displays the text and image of a tree object.
 * 
 * RESPONSIBILITIES:
 * Handle the text and image to be displayed in the TreeView.
 * 
 * USAGE:
 * This class is used only by TreeViewContentProvider.  
 * </pre>
 */
public class ArtifactsTreeViewLabelProvider extends LabelProvider
{
    /**
     * Returns the text that will be displayed in the tree view based on <code>obj</code>.
     * 
     * @param obj The object which text will be returned.
     * @return The text related to the object.
     */
    public String getText(Object obj)
    {
        return obj.toString();
    }

    /**
     * Returns the image that will be displayed in the tree view. If <code>obj</code> is a parent
     * tree the image is a folder. If <code>obj</code> is not a parent then checks if it is a word
     * or excel file. If it is a word file then verifies if it is well formed. The default image is
     * IMG_OBJ_ELEMENT.
     * 
     * @param obj The object which image will be returned.
     * @return The image related to the object.
     */
    public Image getImage(Object obj)
    {
        String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
        Image image = null;
        TreeObject treeObject = (TreeObject) obj;

        if (!(treeObject instanceof DocumentTreeObject))
        {
            imageKey = ISharedImages.IMG_OBJ_FOLDER;
        }
        else
        {
            DocumentTreeObject node = (DocumentTreeObject) obj;
            if (node.getDocumentType() == DocumentType.useCase)
            {
                String fileType = FileUtil.getFileExtension(new File(node.getAbsolutePath()));
                InputDocumentData inputDocumentData = InputDocumentExtensionFactory.getInputDocumentDataByExtension(fileType);

                if (ProjectManagerController.getInstance().isDocumentWellFormed(
                        getText(node.getAbsolutePath())))
                {
                    image = inputDocumentData.getDocumentIconDescriptor().createImage();
                }
                else
                {
                    image = inputDocumentData.getDocumentErrorIconDescriptor().createImage();
                }
            }
            else
            {
#if($xmloutput)
            	image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,"icons/xml_icon_32_32.bmp").createImage();
#elseif($html)
	        	image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,"icons/html_icon_32_32.bmp").createImage();
#else
            	image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,"icons/excel_icon_32_32.bmp").createImage();
#end
            }
        }

        if (image == null)
        {
            image = PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
        }
        return image;
    }
}
