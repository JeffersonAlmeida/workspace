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
 * wmr068    Aug 5, 2008    LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.pm.extensions.input;

import org.eclipse.jface.resource.ImageDescriptor;

// INSPECT new class
/**
 * This class encapsulates the input documents data. Examples of data are document name, document
 * type, the image icon of the document, and the Input Document extension plug-in.
 */
public class InputDocumentData
{
    /**
     * A reference to the extension plug-in of this input document.
     */
    private InputDocumentExtension inputDocumentExtension;

    /**
     * A reference to the name of this input document.
     */
    private String documentName;

    /**
     * A reference to the extension (i.e. *.doc, *.xml etc) of this input document.
     */
    private String documentTypeFilter;

    /**
     * A reference to the extension (i.e. doc, xml etc) of this input document.
     */
    private String documentType;

    /**
     * A reference to the icon of this input document.
     */
    private ImageDescriptor documentIconDescriptor;

    /**
     * A reference to the error icon of this input document.
     */
    private ImageDescriptor documentErrorIconDescriptor;

    /**
     * Constructor of the <code>InputDocumentData</code> class.
     * 
     * @param inputDocumentExtension the extension plug-in of this input document.
     * @param documentName the input document name.
     * @param documentTypeFilter the extension (i.e. *.doc, *.xml etc) of this input document to be
     * filtered.
     * @param documentType the extension (i.e. doc, xml etc) of this input document.
     * @param documentIconDescriptor the <code>ImageDescriptor</code> representing the icon of
     * this input document.
     * @param documentErrorIconDescriptor the <code>ImageDescriptor</code> representing the error
     * icon of this input.
     */
    public InputDocumentData(InputDocumentExtension inputDocumentExtension, String documentName,
            String documentTypeFilter, String documentType, ImageDescriptor documentIconDescriptor,
            ImageDescriptor documentErrorIconDescriptor)
    {
        this.inputDocumentExtension = inputDocumentExtension;
        this.documentName = documentName;
        this.documentTypeFilter = documentTypeFilter;
        this.documentType = documentType;
        this.documentIconDescriptor = documentIconDescriptor;
        this.documentErrorIconDescriptor = documentErrorIconDescriptor;
    }

    /**
     * Gets the Input Document extension plug-in in which this <code>InputDocumentData</code> is
     * related to.
     * 
     * @return the Input Document extension plug-in in which this <code>InputDocumentData</code>
     * is related to.
     */
    public InputDocumentExtension getInputDocumentExtension()
    {
        return inputDocumentExtension;
    }

    /**
     * Gets the name of this input document.
     * 
     * @return the name of this input document.
     */
    public String getDocumentName()
    {
        return documentName;
    }

    /**
     * Gets the extension (i.e. *.doc, *.xml etc) of this input document to be filtered.
     * 
     * @return the extension of this input document to be filtered.
     */
    public String getDocumentTypeFilter()
    {
        return documentTypeFilter;
    }

    /**
     * Gets the extension (i.e. doc, xml etc) of this input document.
     * 
     * @return the extension of this input document.
     */
    public String getDocumentType()
    {
        return documentType;
    }

    /**
     * Gets the <code>ImageDescriptor</code> representing the icon of this input document.
     * 
     * @return the <code>ImageDescriptor</code> representing the icon of this input document.
     */
    public ImageDescriptor getDocumentIconDescriptor()
    {
        return documentIconDescriptor;
    }

    /**
     * Gets the <code>ImageDescriptor</code> representing the error icon of this input document.
     * 
     * @return the <code>ImageDescriptor</code> representing the error icon of this input
     * document.
     */
    public ImageDescriptor getDocumentErrorIconDescriptor()
    {
        return documentErrorIconDescriptor;
    }

}
