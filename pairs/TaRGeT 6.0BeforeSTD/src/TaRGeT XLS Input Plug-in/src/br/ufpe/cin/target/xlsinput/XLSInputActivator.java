package br.ufpe.cin.target.xlsinput;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class XLSInputActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "br.ufpe.cin.target.xlsinput";

	// The shared instance
	private static XLSInputActivator plugin;
	
	/**
	 * The constructor
	 */
	public XLSInputActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static XLSInputActivator getDefault() {
		return plugin;
	}
}
