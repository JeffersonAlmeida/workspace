/*
 * @(#)XMLFileFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wmr068    Aug 07, 2008   LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.xmlinput.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * Implements a FileFilter to XML files.
 * 
 * <pre>
 * CLASS:
 * Implements a <code>FileFilter</code> to XML files. This filter accepts only files that end with '.xml'.
 */
// INSPECT identical to WordFileFilter
public class XMLFileFilter implements FileFilter
{
    /**
     * This method accepts file names that ends with '.xml'.
     * 
     * @param file The file which extension will be checked.
     * @return <i>True</i> if the file ends with '.xml' or <i>false</i> otherwise.
     */
    public boolean accept(File file)
    {
        return !file.isDirectory() && file.getAbsolutePath().endsWith(".xml");
    }

}
