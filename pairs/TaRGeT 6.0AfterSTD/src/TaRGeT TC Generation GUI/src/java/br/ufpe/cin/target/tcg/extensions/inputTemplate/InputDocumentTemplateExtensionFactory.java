/*
 * @(#)InputDocumentTemplateExtension.java
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
 * fsf2      Mar 09, 2009                Initial creation.
 */
package br.ufpe.cin.target.tcg.extensions.inputTemplate;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

import br.ufpe.cin.target.tcg.TCGActivator;

public class InputDocumentTemplateExtensionFactory
{
	/**
	 * The ID of the Input Template Document extension point.
	 */
	private final static String INPUT_DOCUMENT_TEMPLATE_EXTENSION_POINT_ID = "inputTemplate";

	/**
	 * The configuration element "outputDocumentExtension" in the Output Document extension point
	 * schema.
	 */
	private final static String INPUT_DOCUMENT_TEMPLATE_CONFIGURATION_ELEMENT = "inputDocumentTemplateExtension";

	/**
	 * The attribute "outputDocumentImplementation" in the Output Document extension point schema.
	 * This attribute represents classes that implement the Output Document extension point.
	 */
	private final static String INPUT_DOCUMENT_TEMPLATE_IMPLEMENTATION_ATTRIBUTE = "inputDocumentTemplateImplementation";

	/**
	 * This <code>Collection</code> contains the <code>OutputDocumentData</code> created when this
	 * factory is executed.
	 */
	private static Collection<InputDocumentTemplateExtension> inputDocumentTemplateExtensions;

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
	public static Collection<InputDocumentTemplateExtension> inputDocumentTemplatetExtensions()
	{
		if (inputDocumentTemplateExtensions == null)
		{
			inputDocumentTemplateExtensions = new Vector<InputDocumentTemplateExtension>();

			IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
					TCGActivator.PLUGIN_ID, INPUT_DOCUMENT_TEMPLATE_EXTENSION_POINT_ID).getExtensions();

			for (IExtension extension : extensions)
			{
				IConfigurationElement[] configElements = extension.getConfigurationElements();

				for (IConfigurationElement configElement : configElements)
				{
					if (configElement.getName().equals(INPUT_DOCUMENT_TEMPLATE_CONFIGURATION_ELEMENT))
					{
						InputDocumentTemplateExtension inputDocumentTemplateExtension;

						try
						{
							inputDocumentTemplateExtension = (InputDocumentTemplateExtension) configElement
							.createExecutableExtension(INPUT_DOCUMENT_TEMPLATE_IMPLEMENTATION_ATTRIBUTE);

							inputDocumentTemplateExtensions.add(inputDocumentTemplateExtension);
						}
						catch (CoreException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		return inputDocumentTemplateExtensions;
	}
}
