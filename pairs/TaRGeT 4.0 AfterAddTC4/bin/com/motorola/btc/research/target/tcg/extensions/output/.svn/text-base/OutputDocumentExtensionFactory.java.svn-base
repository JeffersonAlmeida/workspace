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
 * fsf2    Jul 31, 2008   LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.tcg.extensions.output;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

import com.motorola.btc.research.target.tcg.TCGActivator;

public class OutputDocumentExtensionFactory
{
	private final static String OUTPUT_DOCUMENT_EXTENSION_POINT_ID = "output";

	private final static String OUTPUT_DOCUMENT_CONFIGURATION_ELEMENT = "outputDocumentExtension";

	private final static String OUTPUT_DOCUMENT_IMPLEMENTATION_ATTRIBUTE = "outputDocumentImplementation";

	private static Vector<OutputDocumentData> outputDocumentDataMap;

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
