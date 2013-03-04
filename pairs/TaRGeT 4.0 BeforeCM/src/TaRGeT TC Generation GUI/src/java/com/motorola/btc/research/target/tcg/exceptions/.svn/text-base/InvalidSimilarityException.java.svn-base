/*
 * @(#)InvalidSimilarityException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wfo007    Jun 04, 2007   LIBmm42774   Initial creation.
 * wfo007    Aug 24, 2007   LIBmm42774   Rework after inspection LX201876.
 */
package com.motorola.btc.research.target.tcg.exceptions;

import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;

/**
 * This class is used to specify an exception when an invalid required similarity is specified to
 * the similarity filter.
 * 
 * <pre>
 * 
 * RESPONSIBILITIES:
 * 1) Launch an exception whenever an invalid required similarity is specified.
 *
 * COLABORATORS:
 * 1) Uses TargetException class.
 *
 */
public class InvalidSimilarityException extends TestGenerationException
{
    /**
     * Default Serial Id.
     */
    private static final long serialVersionUID = 1L;


    /**
     * Constructor. Initialize the exception. A similarity exception is thrown after an invalid
     * required similarity in the similarity filter is specified.
     */
    public InvalidSimilarityException()
    {
        super("Similarity Exception", "The required similarity must be a value between 0 and 100.");
    }
}
