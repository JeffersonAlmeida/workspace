/*
 * @(#)TestGenerationException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modifications after inspection (LX135035).
 */
package com.motorola.btc.research.target.tcg.exceptions;

import com.motorola.btc.research.target.common.exceptions.TargetException;

/**
 * Exception that indicates a problem while generating test cases.
 * 
 * <pre>
 * CLASS:
 * Exception that indicates a problem while generating test cases.
 * 
 * RESPONSIBILITIES:
 * N/A
 *
 * COLABORATORS:
 * N/A
 *
 * USAGE:
 * N/A
 */
public class TestGenerationException extends TargetException
{
    /** Default serial Id */ 
    private static final long serialVersionUID = 0L;

    /** The title of the exception */
    private String title;

    /**
     * Initializes the Exception.
     * 
     * @param title The title of the exception.
     * @param message The message of the cause of the exception.
     */
    public TestGenerationException(String title, String message)
    {
        super(message);
        this.title = title;
    }

    /**
     * Retrieved the title of the exception.
     * 
     * @return Exception title.
     */
    public String getTitle()
    {
        return this.title;
    }

}
