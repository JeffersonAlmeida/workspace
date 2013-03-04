/*
 * @(#)UseCaseEditorActivator.java
 *
 REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------  ------------    ----------    ----------------------------
 * mcms     07/09/2009                    Initial Creation
 * lmn3     07/10/2009                    Changes due code inspection.
 */
package br.ufpe.cin.target.uceditor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class UseCaseEditorActivator extends AbstractUIPlugin 
{

	// The plug-in ID
	public static final String PLUGIN_ID = "br.ufpe.cin.target.uceditor";

	// The shared instance
	private static UseCaseEditorActivator plugin;
	
	/**
	 * The constructor
	 */
	public UseCaseEditorActivator() 
	{
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception 
	{
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin(org.osgi.framework.BundleContext)
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
	public static UseCaseEditorActivator getDefault() 
	{
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) 
	{
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	 /**
     * This method is used to log any error during the Editor execution.
     * 
     * @param code Integer indicating the error code.
     * @param clazz The class in which the error was raised.
     * @param message The error message.
     * @param exception The error exception object.
     */
    @SuppressWarnings("unchecked")
    public static void logError(int code, Class clazz, String message, Throwable exception)
    {
        UseCaseEditorActivator.getDefault().getLog().log(
                new Status(IStatus.ERROR, UseCaseEditorActivator.PLUGIN_ID, code, clazz.getSimpleName()
                        + ": " + message, exception));
    }

}
