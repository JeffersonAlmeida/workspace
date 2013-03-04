/*
 * @(#)PMActivator.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348         -         LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007   LIBkk11577   Added Javadoc.
 * dhq348    Jan 18, 2007   LIBkk11577   Rework of inspection LX133710.
 */

package com.motorola.btc.research.target.pm;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * <pre>
 * CLASS:
 * This is the main class of the pm plug-in.
 * </pre>
 */
public class PMActivator extends AbstractUIPlugin
{
    /**
     * The plug-in ID. This ID is declared in the plug-in properties.
     */
    public static final String PLUGIN_ID = "com.motorola.btc.research.target.pm";

    /**
     * The shared instance of the plug-in.
     */
    private static PMActivator plugin;

    /**
     * Initializes the shared instance.
     */
    public PMActivator()
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
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static PMActivator getDefault()
    {
        return plugin;
    }

}
