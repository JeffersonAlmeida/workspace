/*
 * @(#)UseCaseDocumentXMLException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Jan 05, 2007    LIBkk11577   Initial creation.
 */
package com.motorola.btc.research.target.common.exceptions;

/**
 * Represents an error during the reading of XML files.
 * 
 * <pre>
 * CLASS:
 * It contains the exception attribute that stores the exception that was previously launched. 
 * It also contains an attribute that stores the exception message. 
 *
 * COLABORATORS:
 * It extends the <code>TargetException</code>
 *
 */
public class UseCaseDocumentXMLException extends TargetException
{
    /** Auto generated serial ID */
    private static final long serialVersionUID = 1115582278709573100L;

    /** The exception that had been thrown before the <code>UseCaseDocumentXMLException</code> */
    protected Exception exception;

    /** The exception message */
    protected String message;

    /**
     * Constructor that sets the thrown exception.
     * 
     * @param e The exception that had been thrown previously.
     */
    public UseCaseDocumentXMLException(Exception e)
    {
        this(e, null);
    }

    /**
     * Constructor that sets the message exception.
     * 
     * @param msg The exception message.
     */
    public UseCaseDocumentXMLException(String msg)
    {
        this(null, msg);
    }

    /**
     * Constructor that sets the thrown exception and the message.
     * 
     * @param e The exception that had been thrown previously.
     * @param message The exception message.
     */
    public UseCaseDocumentXMLException(Exception e, String message)
    {
        this.exception = e;
        this.message = message;
    }

    /**
     * Returns the exception message. If the exception message is <code>null</code>, the message
     * of the previous thrown exception is returned.
     * 
     * @return The message of the exception that had been thrown
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage()
    {
        String result = message;
        if (result == null)
        {
            result = this.exception.getMessage();
        }
        return result;
    }
}