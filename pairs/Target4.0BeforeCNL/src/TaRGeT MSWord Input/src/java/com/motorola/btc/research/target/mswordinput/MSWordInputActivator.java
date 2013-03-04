/*
 * @(#)MSWordInputActivator.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wmr068    Jul 31, 2008   LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.mswordinput;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.mswordinput.ucdoc.WordDocumentProcessing;

//INSPECT new class
/**
 * The activator class controls the plug-in life cycle
 */
public class MSWordInputActivator extends Plugin
{

    /**
     * The ID of the plugin.
     */
    public static final String PLUGIN_ID = "com.motorola.btc.research.target.mswordinput";

    /**
     * The shared instance of the plugin.
     */
    private static MSWordInputActivator plugin;

    /**
     * The constructor.
     */
    public MSWordInputActivator()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        plugin = this;

        WordDocumentProcessing.getInstance().initListener();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception
    {
        plugin = null;
        super.stop(context);
        
        //INSPECT this code was moved from the TargetApplication class.
        try
        {
            WordDocumentProcessing.getInstance().finishListener();
        }
        catch (UseCaseDocumentXMLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static MSWordInputActivator getDefault()
    {
        return plugin;
    }

}
