/*
 * @(#)StartingOperationsExtensionFactory.java
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
 * wmr068    Aug 7, 2008    LIBqq64190   Initial creation.
 */
package br.ufpe.cin.target.core.extensions;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

import br.ufpe.cin.target.core.TargetCoreActivator;

/**
 * A factory for the Starting Operations extension.
 * 
 * CLASS: This class represents the Starting Operations extension factory. It searches for available
 * Starting Operations extension implementations and returns a <code>Collection</code> of
 * these Starting Operations (<code>StartingOperationsExtension</code>).
 * 
 * RESPONSIBILITIES: 1) ???
 * 
 * USAGE: Collection<StartingOperationsExtension> startingOperationsExtensions = StartingOperationsExtensionFactory
                    .startingOperationsExtensions();
 */
public class StartingOperationsExtensionFactory
{
    /**
     * The ID of the Starting Operations extension point.
     */
    private final static String STARTING_OPERATIONS_EXTENSION_POINT_ID = "startingOperations";

    /**
     * The configuration element "startingOperationsExtension" in the Starting Operations extension point
     * schema.
     */
    private final static String STARTING_OPERATIONS_CONFIGURATION_ELEMENT = "startingOperationsExtension";

    /**
     * The attribute "startingOperationsImplementation" in the Starting Operations extension point schema.
     * This attribute represents classes that implement the Starting Operations extension point, such as
     * <code>br.ufpe.cin.target.mswordinput.controller.MSWordStartingOperationsExtensionImplementation</code>.
     */
    private final static String STARTING_OPERATIONS_IMPLEMENTATION_ATTRIBUTE = "startingOperationsImplementation";

    /**
     * The <code>Collection</code> of available Starting Operations extensions.
     */
    private static Collection<StartingOperationsExtension> startingOperationsExtensionList;

    /**
     * Searches for the available Starting Operations extensions plug-ins. In addition, this method
     * creates a <code>Collection</code> of <code>StartingOperationsExtension</code> and returns it. Such
     * a collection will contain all information about the Starting Operations extensions plug-ins.
     * 
     * @return a <code>Collection</code> of <code>StartingOperationsExtension</code> which represents all
     * data of the Starting Operations extensions plug-ins.
     */
    public static Collection<StartingOperationsExtension> startingOperationsExtensions()
    {
        if (startingOperationsExtensionList == null)
        {
            startingOperationsExtensionList = new ArrayList<StartingOperationsExtension>();
            
            IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
                    TargetCoreActivator.PLUGIN_ID, STARTING_OPERATIONS_EXTENSION_POINT_ID)
                    .getExtensions();
            
            for (IExtension extension : extensions)
            {
                IConfigurationElement[] configElements = extension.getConfigurationElements();

                for (IConfigurationElement configElement : configElements)
                {
                    if (configElement.getName().equals(STARTING_OPERATIONS_CONFIGURATION_ELEMENT))
                    {
                        StartingOperationsExtension startingOperationsExtension;

                        try
                        {
                            startingOperationsExtension = (StartingOperationsExtension) configElement
                                    .createExecutableExtension(STARTING_OPERATIONS_IMPLEMENTATION_ATTRIBUTE);

                            startingOperationsExtensionList.add(startingOperationsExtension);
                        }
                        catch (CoreException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return startingOperationsExtensionList;
    }
}
