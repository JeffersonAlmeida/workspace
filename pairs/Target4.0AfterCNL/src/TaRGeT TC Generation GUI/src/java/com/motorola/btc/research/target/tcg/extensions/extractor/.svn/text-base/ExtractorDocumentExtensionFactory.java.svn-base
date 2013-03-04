/*
 * @(#)OutputExtensionFactory.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * fsf2      Mar 16, 2009                Initial creation.
 */
package com.motorola.btc.research.target.tcg.extensions.extractor;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

import com.motorola.btc.research.target.tcg.TCGActivator;

public class ExtractorDocumentExtensionFactory
{
	/**
	 * The ID of the Extractor Document Extension point.
	 */
	private final static String EXTRACTOR_DOCUMENT_EXTENSION_POINT_ID = "extractor";

	/**
	 * The configuration element "extractorDocumentExtension" in the Extractor Document extension point
	 * schema.
	 */
	private final static String EXTRACTOR_DOCUMENT_CONFIGURATION_ELEMENT = "extractorDocumentExtension";

	/**
	 * The attribute "extractorDocumentImplementation" in the Extractor Document extension point schema.
	 * This attribute represents classes that implement the Output Document extension point.
	 */
	private final static String EXTRACTOR_DOCUMENT_IMPLEMENTATION_ATTRIBUTE = "extractorDocumentImplementation";

	/**
	 * This <code>Collection</code> contains the <code>OutputDocumentData</code> created when this
	 * factory is executed.
	 */
	private static Collection<ExtractorDocumentExtension> outputDocumentDataMap;

	/**
     * Searches for the available extractor document extensions plug-ins. In addition, this method
     * creates a <code>Collection</code> of <code>OutputDocumentData</code> and returns it. Such
     * a collection will contain all information about the Output Document extensions plug-ins.
     * 
     * @return a <code>Collection</code> of <code>ExtractorDocumentExtension</code> which represents all
     * data of the Output Document extensions plug-ins. Notice that each
     * <code>OutputDocumentData</code> represents one extension. It contains not only information
     * like the name and document type, but also the the Output Document extension object itself.
     */
	public static Collection<ExtractorDocumentExtension> extractorExtensions()
	{
		if (outputDocumentDataMap == null)
		{
			outputDocumentDataMap = new Vector<ExtractorDocumentExtension>();

			IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
					TCGActivator.PLUGIN_ID, EXTRACTOR_DOCUMENT_EXTENSION_POINT_ID).getExtensions();

			for (IExtension extension : extensions)
			{
				IConfigurationElement[] configElements = extension.getConfigurationElements();

				for (IConfigurationElement configElement : configElements)
				{
					if (configElement.getName().equals(EXTRACTOR_DOCUMENT_CONFIGURATION_ELEMENT))
					{
					    ExtractorDocumentExtension outputDocumentExtension;

						try
						{
							outputDocumentExtension = (ExtractorDocumentExtension) configElement
							.createExecutableExtension(EXTRACTOR_DOCUMENT_IMPLEMENTATION_ATTRIBUTE);

							outputDocumentDataMap.add(outputDocumentExtension);
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
