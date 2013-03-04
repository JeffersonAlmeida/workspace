/*
 * @(#)InstallerTrackUsageAgent.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Apr 12, 2007    LIBll91788   Initial creation.
 * dhq348   Apr 18, 2007    LIBll91788   Rework of inspection LX164695.
 */
package com.motorola.btc.research.target.installer;


/**
 * This class represents a call to the aplication 'TUClientAgent.exe' given a number of parameters
 * that the application uses to send data to a remote server.
 */
public class InstallerTrackUsageAgent extends
        com.motorola.btc.research.target.common.tuclientagent.TrackUsageClient
{

    /**
     * Changes the folder attribute and calls the method TUClientAgent.executeAgent passing the
     * informed parameters.
     * 
     * @param _folder The _folder of the application
     * @param param1 The first parameter used to compose the string to the server.
     * @param param2 The second parameter used to compose the string to the server.
     */
    public static void executeAgent(String _folder, String param1, String param2)
    {
        folder = _folder;
        com.motorola.btc.research.target.common.tuclientagent.TrackUsageClient.executeAgent(param1,
                param2);
    }
}
