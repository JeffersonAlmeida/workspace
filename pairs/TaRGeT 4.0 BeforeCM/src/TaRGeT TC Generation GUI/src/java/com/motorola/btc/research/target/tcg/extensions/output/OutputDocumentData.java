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

public class OutputDocumentData
{
    private OutputDocumentExtension outputDocumentExtension;

    public OutputDocumentData(OutputDocumentExtension outputDocumentExtension)
    {
        this.outputDocumentExtension = outputDocumentExtension;
    }

    public OutputDocumentExtension getOutputDocumentExtension()
    {
        return this.outputDocumentExtension;
    }
}
