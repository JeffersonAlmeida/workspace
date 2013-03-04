/*
 * @(#)InputExtensionFactory.java
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
 */
package br.ufpe.cin.target.pm.extensions.input;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import br.ufpe.cin.target.pm.PMActivator;

/**
 * A factory for the Input Document extension. 
 * 
 * CLASS: This class represents the Input Document extension factory. It searches for available
 * Input Document extension implementations and returns a <code>Collection</code> of
 * <code>InputDocumentData</code>. This data object encapsulates information about a determined
 * Input Document extension, such as the document name, the document type, and the Input Document
 * extension object itself.
 * 
 * RESPONSIBILITIES: 1) ???
 * 
 * USAGE: Collection<InputDocumentData> inputExtensionsList = InputDocumentExtensionFactory.inputExtensions();
 */
public class InputDocumentExtensionFactory
{
    /**
     * The ID of the Input Document extension point.
     */
    private final static String INPUT_DOCUMENT_EXTENSION_POINT_ID = "input";

    /**
     * The configuration element "inputDocumentExtension" in the Input Document extension point
     * schema.
     */
    private final static String INPUT_DOCUMENT_CONFIGURATION_ELEMENT = "inputDocumentExtension";

    /**
     * The attribute "inputDocumentImplementation" in the Input Document extension point schema.
     * This attribute represents classes that implement the Input Document extension point, such as
     * <code>br.ufpe.cin.target.mswordinput.controller.MSWordDocumentExtensionImplementation</code>.
     */
    private final static String INPUT_DOCUMENT_IMPLEMENTATION_ATTRIBUTE = "inputDocumentImplementation";

    /**
     * The attribute "name" in the Input Document extension point schema. This attribute represents
     * the name of the documents.
     */
    private final static String INPUT_DOCUMENT_NAME_ATTRIBUTE = "name";

    /**
     * The attribute "inputDocumentType" in the Input Document extension point schema. This
     * attribute represents the document type of documents (i.e. doc, xml etc).
     */
    private final static String INPUT_DOCUMENT_TYPE_ATTRIBUTE = "inputDocumentType";

    /**
     * The attribute "inputDocumentTypeFilter" in the Input Document extension point schema. This
     * attribute represents the document type to be filtered (i.e. *.doc, *.xml etc).
     */
    private final static String INPUT_DOCUMENT_TYPE_FILTER_ATTRIBUTE = "inputDocumentTypeFilter";

    /**
     * The attribute "inputDocumentIcon" in the Input Document extension point schema.
     */
    private final static String INPUT_DOCUMENT_ICON_ATTRIBUTE = "inputDocumentIcon";

    /**
     * The attribute "inputDocumentErrorIcon" in the Input Document extension point schema.
     */
    private final static String INPUT_DOCUMENT_ERROR_ICON_ATTRIBUTE = "inputDocumentErrorIcon";

    /**
     * This <code>Map</code> contains the <code>InputDocumentData</code> created when this
     * factory is executed. Notice that the keys consist of document types (i.e. doc, xml etc).
     */
    private static Map<String, InputDocumentData> inputDocumentDataMap;

    /**
     * Searches for the available Input Document extensions plug-ins. In addition, this method
     * creates a <code>Collection</code> of <code>InputDocumentData</code> and returns it. Such
     * a collection will contain all information about the Input Document extensions plug-ins.
     * 
     * @return a <code>Collection</code> of <code>InputDocumentData</code> which represents all
     * data of the Input Document extensions plug-ins. Notice that each
     * <code>InputDocumentData</code> represents one extension. It contains not only information
     * like the name and document type, but also the the Input Document extension object itself.
     */
    public static Collection<InputDocumentData> inputExtensions()
    {
        if (inputDocumentDataMap == null)
        {
            inputDocumentDataMap = new LinkedHashMap<String, InputDocumentData>();

            IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
                    PMActivator.PLUGIN_ID, INPUT_DOCUMENT_EXTENSION_POINT_ID).getExtensions();

            for (IExtension extension : extensions)
            {
                IConfigurationElement[] configElements = extension.getConfigurationElements();

                for (IConfigurationElement configElement : configElements)
                {
                    if (configElement.getName().equals(INPUT_DOCUMENT_CONFIGURATION_ELEMENT))
                    {
                        InputDocumentExtension inputDocumentExtension;

                        InputDocumentData inputDocumentData;

                        try
                        {
                            inputDocumentExtension = (InputDocumentExtension) configElement
                                    .createExecutableExtension(INPUT_DOCUMENT_IMPLEMENTATION_ATTRIBUTE);
                            String documentName = configElement
                                    .getAttribute(INPUT_DOCUMENT_NAME_ATTRIBUTE);
                            String documentTypeFilter = configElement
                                    .getAttribute(INPUT_DOCUMENT_TYPE_FILTER_ATTRIBUTE);
                            String documentType = configElement
                                    .getAttribute(INPUT_DOCUMENT_TYPE_ATTRIBUTE);

                            String documentIcon = configElement
                                    .getAttribute(INPUT_DOCUMENT_ICON_ATTRIBUTE);
                            ImageDescriptor documentIconDescriptor = AbstractUIPlugin
                                    .imageDescriptorFromPlugin("", documentIcon);

                            String documentErrorIcon = configElement
                                    .getAttribute(INPUT_DOCUMENT_ERROR_ICON_ATTRIBUTE);
                            ImageDescriptor documentErrorIconDescriptor = AbstractUIPlugin
                                    .imageDescriptorFromPlugin(
                                            "", documentErrorIcon);

                            inputDocumentData = new InputDocumentData(inputDocumentExtension,
                                    documentName, documentTypeFilter, documentType,
                                    documentIconDescriptor, documentErrorIconDescriptor);

                            inputDocumentDataMap.put(documentType, inputDocumentData);
                        }
                        catch (CoreException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return inputDocumentDataMap.values();
    }

    /**
     * Returns the corresponding <code>InputDocumentData</code> object given the document type
     * (i.e. doc, xml etc).
     * 
     * @param documentType the document type (i.e. doc, xml etc).
     * @return the <code>InputDocumentData</code> object related to the given document type.
     */
    public static InputDocumentData getInputDocumentDataByExtension(String documentType)
    {
        if(inputDocumentDataMap == null)
        {
            inputExtensions();
        }
        
        return inputDocumentDataMap.get(documentType);
    }

}
