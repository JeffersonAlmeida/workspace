/*
 * @(#)InputDocumentData.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * fsf2      Feb 27, 2009                Initial creation.
 */
package com.motorola.btc.research.target.tcg.extensions.output;

/**
 * <pre>
 * CLASS:
 * Represents an output document data.
 * 
 * RESPONSIBILITIES:
 * This class encapsulates the output document generation. 
 * 
 * </pre>
 */
public class OutputDocumentData
{
	/**
     * A reference to the extension plug-in of this output generator.
     */
    private OutputDocumentExtension outputDocumentExtension;

    /**
     * Constructor of the <code>OutputDocumentData</code> class.
     */
    public OutputDocumentData(OutputDocumentExtension outputDocumentExtension)
    {
        this.outputDocumentExtension = outputDocumentExtension;
    }

    /**
     * Gets the Output Document extension plug-in in which this <code>OutputDocumentExtension</code> is
     * related to.
     * 
     * @return the Output Document extension plug-in in which this <code>OutputDocumentExtension</code>
     * is related to.
     */
    public OutputDocumentExtension getOutputDocumentExtension()
    {
        return this.outputDocumentExtension;
    }
}
