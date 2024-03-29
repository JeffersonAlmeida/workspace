/*
 * @(#)Error.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348         -         LIBkk11577   Initial creation.
 * wdt022   Jan 29, 2007    LIBll12753   Rework of inspection LX136878. Transforming the class in interface.
 * wdt022   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 */

package com.motorola.btc.research.target.pm.errors;

/**
 * This interface represents an error. An error is mainly referred by its id.
 * 
 * <pre>
 * CLASS:
 * Represents all the logical errors in the project.
 */
public interface Error
{
    /**
     * The code of the fatal error .
     */
    public static final int FATAL_ERROR = 0;

    /**
     * The code of the error type ERROR.
     */
    public static final int ERROR = 1;

    /**
     * The code of the error type WARNING.
     */
    public static final int WARNING = 2;

    /**
     * Returns the description of the error.
     * 
     * @return The description attribute.
     */
    public String getDescription();

    /**
     * Returns the location inside the resource where is the error.
     * 
     * @return The location of the error.
     */
    public String getLocation();

    /**
     * The path of the resource that contains the error.
     * 
     * @return The absolute path of the resource.
     */
    public String getPath();

    /**
     * The name of the resource.
     * 
     * @return The resource name.
     */
    public String getResource();

    /**
     * Returns the error type (error or warning).
     * 
     * @return The type of the error.
     */
    public int getType();

}
