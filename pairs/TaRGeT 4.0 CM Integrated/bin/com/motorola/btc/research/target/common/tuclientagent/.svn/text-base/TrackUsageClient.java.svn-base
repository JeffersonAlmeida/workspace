/*
 * @(#)TrackUsageClient.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Apr 10, 2007    LIBll91788   Initial creation.
 * dhq348   Apr 18, 2007    LIBll91788   Rework of inspection LX164695.
 */
package com.motorola.btc.research.target.common.tuclientagent;

import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class represents a call to the application 'TUClientAgent.exe' given a number of parameters
 * that the application uses to send data to a remote server.
 */
public class TrackUsageClient
{

    /**
     * The folder that contains the application
     */
    protected static String folder = FileUtil.getUserDirectory() + FileUtil.getSeparator()
            + "resources" + FileUtil.getSeparator();


    /** URL separator */
    public static String PARAMETER_INTERNAL_SEPARATOR = "$";

    /**
     * Makes an URL to the server. Uses the parameters to compose such string. Later executes the
     * application 'TUClientAgent.exe' given the composed string.
     * 
     * @param param1 The first parameter used to compose the string to the server.
     * @param param2 The second parameter used to compose the string to the server.
     */
    public static void executeAgent(String param1, String param2)
    {
        String toolName = "TARGET";
        String toolVersion = "RSCH-TARGT_N_01.00.01I";
        String command = folder + "TUClientAgent.exe";

        command += " -u ";
        command += "http://www-rd.jag.br.mot.com/ec/app_user_log/log2.cgi?fn=##CMD_ID##.txt&user=##USER##&tool=";
        command += toolName + "&version=" + toolVersion;
        command += "&param1=" + param1 + "&param2=" + param2;
        command += "&delay=##H##&tzs=##TZS##&tzbias=##TZBIAS##";

        Process p = null;

        try
        {
            p = Runtime.getRuntime().exec(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            p.waitFor();
            int exitValue = p.exitValue();
            if (exitValue != 0)
            {
                System.out.println("exitValue != 0");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
