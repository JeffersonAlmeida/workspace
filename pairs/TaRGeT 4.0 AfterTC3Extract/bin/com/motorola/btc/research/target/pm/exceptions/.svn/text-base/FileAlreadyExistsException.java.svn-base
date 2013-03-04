/*
 * @(#)FileAlredyExistsException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 30, 2007    LIBll12753   Initial creation.
 */
package com.motorola.btc.research.target.pm.exceptions;

/**
 * Represents an exception indicating that a duplicated file exists.
 * 
 * <pre>
 * CLASS:
 * Represents an exception indicating that a duplicated file exists.
 */
public class FileAlreadyExistsException extends TargetProjectLoadingException
{

    /** Default serial ID. */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public FileAlreadyExistsException()
    {
    }

    /**
     * Returns the exception message, which is mounted with each error message.
     */

    public String getMessage()
    {
        String message = "The following errors were found during the project loading:\n\n";

        message += "A document with the same name already exists.";

        return message;
    }

}
