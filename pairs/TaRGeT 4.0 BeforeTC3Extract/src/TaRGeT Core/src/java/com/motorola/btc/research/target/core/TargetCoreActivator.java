/*
 * @(#)TargetCoreActivator.java
 *
 * (c) COPYRIGHT 2006 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348         -         LIBkk11577   Initial creation.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 */
package com.motorola.btc.research.target.core;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * <pre>
 * CLASS:
 * The activator class controls the plug-in life cycle.
 * This class represents the whole plug-in.
 */
public class TargetCoreActivator extends AbstractUIPlugin
{
    /**
     * The ID of the plug-in.
     */
    public static final String PLUGIN_ID = "com.motorola.btc.research.target.core";

    /**
     * The shared instance of the plug-in.
     */
    private static TargetCoreActivator plugin;

    /**
     * Initializes the shared instance.
     */
    public TargetCoreActivator()
    {
        plugin = this;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin - stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception
    {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance of the plug-in.
     * 
     * @return the shared instance
     */
    public static TargetCoreActivator getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path.
     * 
     * @param path The path that will fill the descriptor.
     * @return The image descriptor that describes the an object referenced by <code>path</code>.
     */
    public static ImageDescriptor getImageDescriptor(String path)
    {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}
