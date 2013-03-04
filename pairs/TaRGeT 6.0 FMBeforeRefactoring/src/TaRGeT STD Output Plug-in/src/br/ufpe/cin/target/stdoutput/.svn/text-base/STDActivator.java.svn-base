package br.ufpe.cin.target.stdoutput;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class STDActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "br.ufpe.cin.target.stdoutput";

	// The shared instance
	private static STDActivator plugin;
	
	/**
	 * The constructor
	 */
	public STDActivator() {
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
	public static STDActivator getDefault() {
		return plugin;
	}
}
