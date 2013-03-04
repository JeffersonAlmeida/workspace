/*
 * @(#)ConsistencyManagerExtensionFactory.java
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
 * faas      08/07/2008                  Initial creation.
 * fsf2		20/06/2009					 Integration.
 */
package br.ufpe.cin.target.tcg.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

import br.ufpe.cin.target.tcg.TCGActivator;


/**
 * A factory for the Consistency Management extension.
 * CLASS:
 * This class represents the Consistency Management extension factory. 
 * It searches for a Consistency Management extension implementation and returns it. 
 * 
 * RESPONSIBILITIES:
 * 1) Open the Consistency Management user interface
 *
 * USAGE: 
 * ConsistencyManagerExtension cmExtension = ConsistencyManagerExtensionFactory.newConsistencyManagerExtension();
 */
public class ConsistencyManagerExtensionFactory
{

    /**
     * The ID of the consistency manager extension point.
     */
    private final static String CM_EXTENSION_POINT_ID = "cm";

    /**
     * The configuration element "cmExtension" in the consistency manager extension point schema.
     */
    private final static String CM_CONFIGURATION_ELEMENT_NAME = "cmExtension";

    /**
     * The attribute "cmImplementation" in the consistency manager extension point schema.
     */
    private final static String CM_IMPLEMENTATION_ATTRIBUTE_NAME = "cmImplementation";

    /**
     * The singleton instance of the Consistency Management extension.
     */
    private static ConsistencyManagerExtension cmExtensionInstance;

    /**
     * Searches for the first consistency manager extension plug-in and returns it. If an instance
     * of the consistency manager has already been found by a previous method execution, this same
     * instance is returned.
     * 
     * @return The consistency manager extension.
     */
    public static ConsistencyManagerExtension newConsistencyManagerExtension()
    {

        if (cmExtensionInstance == null)
        {
            IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
                    TCGActivator.PLUGIN_ID, CM_EXTENSION_POINT_ID).getExtensions();

            for (IExtension extension : extensions)
            {
                IConfigurationElement[] configElements = extension.getConfigurationElements();

                for (IConfigurationElement configElement : configElements)
                {
                    if (configElement.getName().equals(CM_CONFIGURATION_ELEMENT_NAME))
                    {
                        try
                        {
                            cmExtensionInstance = (ConsistencyManagerExtension) configElement
                                    .createExecutableExtension(CM_IMPLEMENTATION_ATTRIBUTE_NAME);
                        }
                        catch (CoreException e)
                        {
                            e.printStackTrace();
                        }

                        break;
                    }
                }
            }
        }
        return cmExtensionInstance;
    }

}
