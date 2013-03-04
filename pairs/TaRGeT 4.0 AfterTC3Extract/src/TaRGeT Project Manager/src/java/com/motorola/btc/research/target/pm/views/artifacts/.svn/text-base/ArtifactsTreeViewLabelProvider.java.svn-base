/*
 * @(#)ArtifactsTreeViewLabelProvider.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jan 9, 2007    LIBkk11577   Initial creation.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 */
package com.motorola.btc.research.target.pm.views.artifacts;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.pm.PMActivator;
import com.motorola.btc.research.target.pm.common.TreeObject;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;

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
                if (ProjectManagerController.getInstance().isDocumentWellFormed(
                        getText(node.getAbsolutePath())))
                {
                    image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                            "icons/word_icon_32_32.bmp").createImage();
                }
                else
                {
                    image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                            "icons/word_icon_error_32_32.bmp").createImage();
                }
            }
            else
            {
                image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                        "icons/excel_icon_32_32.bmp").createImage();
            }
        }

        if (image == null)
        {
            image = PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
        }
        return image;
    }
}
