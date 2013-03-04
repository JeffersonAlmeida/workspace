/*
 * @(#)InputDocumentExtensionImplementation.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wmr068    Jul 31, 2008    LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.mswordinput.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.mswordinput.filefilter.WordFileFilter;
import com.motorola.btc.research.target.mswordinput.ucdoc.WordDocumentProcessing;
import com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension;

//INSPECT new class
/**
 * This class implements the Input Document extension point for MS Word files.
 */
public class MSWordDocumentExtensionImplementation extends InputDocumentExtension
{

    /* (non-Javadoc)
     * @see com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension#loadDocument(java.io.File)
     */

    public PhoneDocument loadDocument(File docToBeLoaded) throws TargetException
    {
        WordDocumentProcessing wordProcessing = WordDocumentProcessing.getInstance();

        List<String> docNames = new ArrayList<String>();
        docNames.add(docToBeLoaded.getAbsolutePath());

        PhoneDocument loadedDocument = wordProcessing.createObjectsFromWordDocument(docNames, true)
                .get(0);

        return loadedDocument;
    }

    /* (non-Javadoc)
     * @see com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension#getFileFilter()
     */

    public FileFilter getFileFilter()
    {
        return new WordFileFilter();
    }

    /* (non-Javadoc)
     * @see com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension#saveDocument(com.motorola.btc.research.target.common.ucdoc.PhoneDocument)
     */

    public void saveDocument(PhoneDocument phoneDocument) throws TargetException
    {
        throw new UnsupportedOperationException("Save document operation is not implemented");
    }

}
