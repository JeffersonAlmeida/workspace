/*
 * @(#)OutputExtensionFactory.java
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
 * fsf2      Feb 27, 2009                Initial creation.
 */
package br.ufpe.cin.target.tcg.extensions.output;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

import br.ufpe.cin.target.tcg.TCGActivator;

public class OutputDocumentExtensionFactory
{
	/**
	 * The ID of the Input Document extension point.
	 */
	private final static String OUTPUT_DOCUMENT_EXTENSION_POINT_ID = "output";

	/**
	 * The configuration element "outputDocumentExtension" in the Output Document extension point
	 * schema.
	 */
	private final static String OUTPUT_DOCUMENT_CONFIGURATION_ELEMENT = "outputDocumentExtension";

	/**
	 * The attribute "outputDocumentImplementation" in the Output Document extension point schema.
	 * This attribute represents classes that implement the Output Document extension point.
	 */
	private final static String OUTPUT_DOCUMENT_IMPLEMENTATION_ATTRIBUTE = "outputDocumentImplementation";

	/**
	 * This <code>Collection</code> contains the <code>OutputDocumentData</code> created when this
	 * factory is executed.
	 */
	private static Collection<OutputDocumentData> outputDocumentDataMap;

	/**
     * Searches for the available Output Document extensions plug-ins. In addition, this method
     * creates a <code>Collection</code> of <code>OutputDocumentData</code> and returns it. Such
     * a collection will contain all information about the Output Document extensions plug-ins.
     * 
     * @return a <code>Collection</code> of <code>OutputDocumentData</code> which represents all
     * data of the Output Document extensions plug-ins. Notice that each
     * <code>OutputDocumentData</code> represents one extension. It contains not only information
     * like the name and document type, but also the the Output Document extension object itself.
     */
	public static Collection<OutputDocumentData> outputExtensions()
	{
		if (outputDocumentDataMap == null)
		{
			outputDocumentDataMap = new Vector<OutputDocumentData>();

			IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
					TCGActivator.PLUGIN_ID, OUTPUT_DOCUMENT_EXTENSION_POINT_ID).getExtensions();

			for (IExtension extension : extensions)
			{
				IConfigurationElement[] configElements = extension.getConfigurationElements();

				for (IConfigurationElement configElement : configElements)
				{
					if (configElement.getName().equals(OUTPUT_DOCUMENT_CONFIGURATION_ELEMENT))
					{
						OutputDocumentExtension outputDocumentExtension;

						OutputDocumentData outputDocumentData;

						try
						{
							outputDocumentExtension = (OutputDocumentExtension) configElement
							.createExecutableExtension(OUTPUT_DOCUMENT_IMPLEMENTATION_ATTRIBUTE);

							outputDocumentData = new OutputDocumentData(outputDocumentExtension);

							outputDocumentDataMap.add(outputDocumentData);
						}
						catch (CoreException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		return outputDocumentDataMap;
	}
}
