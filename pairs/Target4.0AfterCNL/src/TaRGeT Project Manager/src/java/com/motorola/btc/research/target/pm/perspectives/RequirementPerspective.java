/*
 * @(#)RequirementPerspective.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Jul 12, 2006    LIBkk11577   Initial creation.
 * dhq348   May 22, 2007    LIBmm25975   Added the view SearchResultsView.
 * dhq348   Jun 21, 2007    LIBmm25975   Added support to search interface.
 */
package com.motorola.btc.research.target.pm.perspectives;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.motorola.btc.research.target.pm.PMActivator;
import com.motorola.btc.research.target.pm.views.ErrorView;
import com.motorola.btc.research.target.pm.views.SearchResultsView;
import com.motorola.btc.research.target.pm.views.artifacts.ArtifactsView;
import com.motorola.btc.research.target.pm.views.features.FeaturesView;

/**
 * <pre>
 *  CLASS:
 *  This class offer a placeholder to requirements related views.
 *  
 *  RESPONSABILITIES:
 *  Define the position of requirement views in workbench. 
 *  
 *  USAGE: 
 *  This class is used internally to the platform. 
 * </pre>
 */
public class RequirementPerspective implements IPerspectiveFactory
{
    /**
     * The perspective ID declared in plugin.xml
     */
    public static final String ID = "com.motorola.btc.research.target.pm.ui.RequirementPerspective";

    /**
     * Set of all visible views Ids in the perspective.
     */
    private static Set<String> visibleViewIDs = new HashSet<String>();

    /**
     * <i>ProjectManagerView</i> extension point id.
     */
    private static final String VIEW_EXTENSION_POINT_ID = "views";

    /**
     * Name of configuration element from <i>ProjectManagerView</i> extension point.
     */
    private static final String VIEW_CONFIGURATION_ELEMENT_NAME = "viewExtension";

    /**
     * Attribute name from configuration element viewId from <i>ProjectManagerView</i> extension
     * point.
     */
    private static final String VIEW_ID_ATTRIBUTE_NAME = "viewId";

    /**
     * Create the initial layout. In this method, the position of all views can be defined.
     * 
     * @param layout Layout used to organize the views.
     */
    public void createInitialLayout(IPageLayout layout)
    {
        layout.setEditorAreaVisible(true);
        layout.addStandaloneView(FeaturesView.ID, true, IPageLayout.LEFT, 0.3f, layout
                .getEditorArea());

        IFolderLayout folderLayout = layout.createFolder("other_views", IPageLayout.BOTTOM, 0.6f,
                layout.getEditorArea());
        folderLayout.addPlaceholder(ArtifactsView.ID + ":*");
        folderLayout.addView(ArtifactsView.ID);
        folderLayout.addView(ErrorView.ID);
        folderLayout.addView(SearchResultsView.ID);

        layout.getViewLayout(FeaturesView.ID).setCloseable(false);
        layout.getViewLayout(ArtifactsView.ID).setCloseable(false);
        layout.getViewLayout(ErrorView.ID).setCloseable(false);
        layout.getViewLayout(SearchResultsView.ID).setCloseable(false);
        visibleViewIDs.add(FeaturesView.ID);
        visibleViewIDs.add(ArtifactsView.ID);
        visibleViewIDs.add(ErrorView.ID);
        visibleViewIDs.add(SearchResultsView.ID);

        for (String viewId : this.getExtensionViews())
        {
            visibleViewIDs.add(viewId);
            folderLayout.addView(viewId);
            layout.getViewLayout(viewId).setCloseable(false);
        }
    }

    /**
     * Process all the extensions for the <i>ProjectManagerView</i> extension point.
     * 
     * @return All the view IDs of those extending plugins.
     */
    private Set<String> getExtensionViews()
    {
        Set<String> result = new HashSet<String>();
        IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
                PMActivator.PLUGIN_ID, VIEW_EXTENSION_POINT_ID).getExtensions();

        for (IExtension extension : extensions)
        {
            IConfigurationElement[] configElements = extension.getConfigurationElements();

            for (IConfigurationElement configElement : configElements)
            {
                if (configElement.getName().equals(VIEW_CONFIGURATION_ELEMENT_NAME))
                {
                    String viewIdElement = configElement.getAttribute(VIEW_ID_ATTRIBUTE_NAME);

                    if (viewIdElement != null)
                    {
                        result.add(viewIdElement);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 
     * Returns the ids of all visible views.
     *  
     * @return A set of strings representing the view ids.
     */
    public static Set<String> getVisibleViewsIds()
    {
       return new HashSet<String>(visibleViewIDs);
    }
}
