/*
 * @(#)MSWordInputActivator.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wmr068    Jul 31, 2008   LIBqq64190   Initial creation.
 * fsf2		 Jun 20, 2009				 Modification on stop method.
 */
package br.ufpe.cin.target.mswordinput;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import br.ufpe.cin.target.common.exceptions.UseCaseDocumentXMLException;
import br.ufpe.cin.target.mswordinput.ucdoc.WordDocumentProcessing;


/**
 * The activator class controls the plug-in life cycle
 */
public class MSWordInputActivator extends Plugin
{

    /**
     * The ID of the plugin.
     */
    public static final String PLUGIN_ID = "br.ufpe.cin.target.mswordinput";

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
     * @see org.eclipse.core.runtime.Plugin(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception
    {
        plugin = null;
        super.stop(context);
        
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
