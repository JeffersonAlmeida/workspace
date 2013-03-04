/*
 * @(#)WordFileFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 26, 2007    LIBll12753   Initial creation.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 */
package com.motorola.btc.research.target.mswordinput.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * Implements a FileFilter to Microsoft Word files.
 * 
 * <pre>
 * CLASS:
 * Implements a <code>FileFilter</code> to Microsoft Word files. This filter accepts only files that end with '.doc'.
 */
public class WordFileFilter implements FileFilter
{
    /**
     * This method accepts file names that ends with '.doc'.
     * 
     * @param file The file which extension will be checked.
     * @return <i>True</i> if the file ends with '.doc' or <i>false</i> otherwise.
     */
    public boolean accept(File file)
    {
        return !file.isDirectory() && file.getAbsolutePath().endsWith(".doc");
    }

}
