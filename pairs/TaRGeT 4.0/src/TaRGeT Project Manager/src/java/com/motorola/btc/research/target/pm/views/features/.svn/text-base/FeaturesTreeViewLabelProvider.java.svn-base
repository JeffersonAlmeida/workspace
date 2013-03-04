/*
 * @(#FeaturesTreeViewLabelProvider.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jul 12, 2006    LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 * wdt022    Feb 06, 2007    LIBll12753   Renamed from RequirementsTreeViewLabelProvider to FeaturesTreeViewLabelProvider.
 * dhq348    Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * dhq348    Jun 19, 2007    LIBmm47221   Modifications according to CR.
 * dhq348    Jul 11, 2007    LIBmm47221   Rework after inspection LX185000.
 * dhq348    Nov 09, 2007    LIBnn34008   Added the interruption support (interruption.gif)
 * dhq348    Jan 14, 2008    LIBnn34008   Rework after inspection LX229625.
 * wdt022    Mar 05, 2008    LIBoo89937   Modification to deal with interruption errors. 
 */
package com.motorola.btc.research.target.pm.views.features;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.PMActivator;
import com.motorola.btc.research.target.pm.common.TreeObject;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;

/**
 * <pre>
 * CLASS:
 * A simple label provider that displays the text and image for a requirements view.
 * 
 * RESPONSIBILITIES:
 * Handle the text and image to be displayed in a requirement view.
 * </pre>
 */
public class FeaturesTreeViewLabelProvider extends LabelProvider
{
    /**
     * The text displayed by the node.
     * 
     * @param The object which text will be returned.
     * @return The text to be displayed.
     */
    public String getText(Object obj)
    {
        return obj.toString();
    }

    /**
     * Returns the image that will be associated to the <code>obj</code> according to its type.
     * 
     * @param obj The object that will have an associated image.
     * @return The image that is associated to <code>obj</code> type.
     */
    public Image getImage(Object obj)
    {
        String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
        Image image = null;
        TreeObject treeObject = (TreeObject) obj;
        ProjectManagerController pmController = ProjectManagerController.getInstance();

        if (treeObject.getParent().getParent() == null)
        {
            imageKey = ISharedImages.IMG_OBJ_FOLDER;
        }
        else if (treeObject.hasChildren())
        {
            if (pmController.getCurrentProject().isFeatureMerged(
                    ((Feature) treeObject.getValue())))
            {
                image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                        "icons/merged_feature_icon.bmp").createImage();
            }
            else
            {
                image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                        "icons/feature_icon_2.bmp").createImage();
            }

        }
        else
        {
            if (pmController.hasUseCaseError(
                    (UseCase) treeObject.getValue(), (Feature) treeObject.getParent().getValue()))
            {
                image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                        "icons/uc_icon_warning.gif").createImage();
            }
            else
            {
                image = PMActivator.imageDescriptorFromPlugin(PMActivator.PLUGIN_ID,
                        "icons/uc_icon.gif").createImage();
            }
        }

        if (image == null)
        {
            image = PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
        }
        return image;
    }
}
