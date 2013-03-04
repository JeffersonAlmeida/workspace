/*
 * @(#)StartingOperationsExtension.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wmr068    Aug 7, 2008    LIBqq64190   Initial creation.
 */
package com.motorola.btc.research.target.core.extensions;

// INSPECT new interface
/**
 * Before launching TaRGeT, sometimes it is necessary to check if the system has all requirements
 * for launching the application successfully. This interface represents the extension point for
 * plug-ins that need to perform starting operations before initiating the TaRGeT system. This way,
 * clients must implement this interface to provide their concrete starting operations. Notice that
 * such starting operations provided by the clients plug-ins will be called when the TaRGeT is
 * to be launched.
 * 
 * @see com.motorola.btc.research.target.core.TargetApplication
 */
public interface StartingOperationsExtension
{
    /**
     * Performs the starting operations of plug-ins that extends this extension point. Such
     * operations consist of checking dependencies and requirements that guarantees that the extended plug-in
     * is initiated correctly.
     * 
     * @return <code>true</code> if the operations performed by the extended plug-in were
     * correctly initiated. <code>false</code>, otherwise.
     */
    boolean performStartingOperations();
}
