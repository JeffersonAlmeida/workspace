/*
 * @(#)TestCaseGeneratorFacade.java
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
 * wdt022    Mar 14, 2007   LIBpp56482   Added logError method.
 */
package com.motorola.btc.research.target.tcg;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class that controls the plug-in life cycle.
 * 
 * <pre>
 * CLASS:
 * This is the main class of the tcg plug-in.
 * </pre>
 */
public class TCGActivator extends AbstractUIPlugin
{
    /**
     * The plug-in ID. This ID is declared in the plug-in properties.
     */
    public static final String PLUGIN_ID = "com.motorola.btc.research.target.tcg";

    /**
     * The shared instance of the plug-in.
     */
    private static TCGActivator plugin;

    /**
     * Initializes the shared instance.
     */
    public TCGActivator()
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
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
    public static TCGActivator getDefault()
    {
        return plugin;
    }

    /**
     * This method is used to log any error during the TaRGeT execution.
     * 
     * @param code Integer indicating the error code.
     * @param clazz The class in which the error was raised.
     * @param message The error message.
     * @param exception The error exception object.
     */
    @SuppressWarnings("unchecked")
    public static void logError(int code, Class clazz, String message, Throwable exception)
    {
        TCGActivator.getDefault().getLog().log(
                new Status(IStatus.ERROR, TCGActivator.PLUGIN_ID, code, clazz.getSimpleName()
                        + ": " + message, exception));
    }

}
