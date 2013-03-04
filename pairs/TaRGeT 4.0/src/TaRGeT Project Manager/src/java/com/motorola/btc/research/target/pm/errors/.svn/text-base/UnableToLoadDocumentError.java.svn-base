/*
 * @(#)UnableToLoadDocumentError.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Jan 29, 2007    LIBll12753   Initial creation.
 * wdt022   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 */
package com.motorola.btc.research.target.pm.errors;

import java.io.File;

/**
 * This class represents an error that appears when the TaRGeT is unable to load a use case
 * document.
 * 
 * <pre>
 * CLASS:
 * The class contains a <code>File</code> attribute that represents the file that was unable to load.
 */
public class UnableToLoadDocumentError implements Error
{
    /** The file that was unable to load */
    private File file;

    /**
     * Basic constructor for the class <code>Error</code>.
     * 
     * @param file The file that was unable to load.
     */
    public UnableToLoadDocumentError(File file)
    {
        this.file = file;
    }

    /**
     * Method used to retrieve the error description.
     * 
     * @return The error description.
     */
    public String getDescription()
    {
        return "The file "
                + this.file.getName()
                + " could not be loaded. The document may not contain XML content or it may be malformed.";
    }

    /**
     * Method used to retrieve the location where the error appeared in the resource. It returns the
     * file name.
     * 
     * @return The file name.
     */
    public String getLocation()
    {
        return this.file.getName();
    }

    /**
     * Method used to retrieve the path of the resource. It is the absolute path of the file.
     * 
     * @return The absolute path of the file.
     */
    public String getPath()
    {
        return this.file.getAbsolutePath();
    }

    /**
     * Method used to retrieve the resource. It is the file name.
     * 
     * @return The file name.
     */
    public String getResource()
    {
        return this.file.getName();
    }

    /**
     * Gets the type of the error.
     * 
     * @return Returns the value <code>Error.FATAL_ERROR</code>.
     */
    public int getType()
    {
        return Error.FATAL_ERROR;
    }

    /**
     * Verifies the equality of the attribute <code>file</code>.
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof UnableToLoadDocumentError)
        {
            UnableToLoadDocumentError error = (UnableToLoadDocumentError) obj;

            result = error.file.getAbsolutePath().equals(this.file.getAbsolutePath());
        }
        return result;
    }
}
