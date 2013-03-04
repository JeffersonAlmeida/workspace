/*
 * @(#)CNLException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package com.motorola.btc.research.cnlframework.exceptions;

/**
 * This class represents a general exception thrown by the CNL framework. All other CNL exceptions
 * should extend the <code>CNLException</code>.
 */
@SuppressWarnings("serial")
public class CNLException extends Exception
{
    /**
     * 
     * Constructor that sets a general error message.
     *
     */
    public CNLException()
    {
        super("Error processing the Controlled Natural Language");
    }

    /**
     * 
     * Constructor that allows setting a specific error message.
     * 
     * @param message The error message.
     */
    public CNLException(String message)
    {
        super(message);
    }
}
