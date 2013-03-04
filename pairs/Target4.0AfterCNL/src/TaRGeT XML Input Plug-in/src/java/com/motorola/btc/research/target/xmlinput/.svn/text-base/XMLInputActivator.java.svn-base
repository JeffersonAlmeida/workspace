/*
 * @(#)XMLInputActivator.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wmr068    Aug 07, 2008    LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.xmlinput;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

//INSPECT new class
/**
 * The activator class controls the plug-in life cycle
 */
public class XMLInputActivator extends Plugin {

    /**
     * The ID of the plugin.
     */
	public static final String PLUGIN_ID = "com.motorola.btc.research.target.xmlinput";

    /**
     * The shared instance of the plugin.
     */
	private static XMLInputActivator plugin;
	
	/**
	 * The constructor.
	 */
	public XMLInputActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance.
	 */
	public static XMLInputActivator getDefault() {
		return plugin;
	}

}
