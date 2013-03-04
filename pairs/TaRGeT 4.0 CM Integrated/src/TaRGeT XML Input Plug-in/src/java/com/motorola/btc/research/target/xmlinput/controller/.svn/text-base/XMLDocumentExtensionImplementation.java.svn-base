/*
 * @(#)XMLDocumentExtensionImplementation.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wmr068    Aug 7, 2008    LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.xmlinput.controller;

import java.io.File;
import java.io.FileFilter;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.xml.UseCaseDocumentXMLParser;
import com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension;
import com.motorola.btc.research.target.xmlinput.filefilter.XMLFileFilter;

//INSPECT new class
/**
 * This class implements the Input Document extension point for XML files.
 */
public class XMLDocumentExtensionImplementation extends InputDocumentExtension 
{

    /* (non-Javadoc)
     * @see com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension#getFileFilter()
     */
    
    public FileFilter getFileFilter()
    {
        return new XMLFileFilter();
    }

    /* (non-Javadoc)
     * @see com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension#loadDocument(java.io.File)
     */
    
    public PhoneDocument loadDocument(File file) throws TargetException
    {
        UseCaseDocumentXMLParser xmlParser = new UseCaseDocumentXMLParser(file);
        
        PhoneDocument phoneDoc = xmlParser.buildPhoneDocument();
        
        return phoneDoc;
    }
    
    /* (non-Javadoc)
     * @see com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension#saveDocument(com.motorola.btc.research.target.common.ucdoc.PhoneDocument)
     */
    
    public void saveDocument(PhoneDocument phoneDocument) throws TargetException
    {
        throw new UnsupportedOperationException("Save document operation is not implemented");
    }

}
