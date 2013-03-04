/*
 * @(#)InstallerLogging.aj
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Apr 10, 2007   LIBll91788   Initial creation.
 * dhq348    Apr 18, 2007    LIBll91788   Rework of inspection LX164695.
 */
package com.motorola.btc.research.target.installer;

import java.util.TimeZone;

import com.install4j.api.InstallerContext;
import com.motorola.btc.research.target.common.tuclientagent.TrackUsageClient;
import com.motorola.btc.research.target.common.util.FileUtil;


/**
 * This aspect is responsible for sending log information about the tool installation.
 * 
 * <pre>
 * More specifically, it logs information about: 
 *      1) the time spent to read the ucsample01validation.dat file during the installation
 * </pre>
 */
public aspect InstallerLogging
{

    /**
     * The time before launching
     */
    private long beforeTime;

    /**
     * The time after launching
     */
    private long afterTime;

    /**
     * The installer folder
     */
    private String installFolder;

    /**
     * Pointcut for preparing the installer
     */
    pointcut appInstall(InstallerContext context) : 
        execution(boolean InstallerHandler.prepareInstaller(InstallerContext))&&
        args(context);

    /**
     * Gets the elapsed time during launching
     * 
     * @return The elapsed time during launching
     */
    private long elapsedTime()
    {
        return afterTime - beforeTime;
    }

    /**
     * Advice before launching. Gets the time before the pointcut appInstall(InstallerContext
     * context).
     */
    before(InstallerContext context) : appInstall(context) {
        installFolder = FileUtil.getFilePath(context.getInstallerFile().getAbsolutePath())
                + FileUtil.getSeparator() + "resources" + FileUtil.getSeparator();
        beforeTime = System.currentTimeMillis();
    }

    /**
     * Advice after launching. Gets the elapsed time and call the method TUClientAgent.executeAgent.
     */
    after(InstallerContext context) : appInstall(context) {
        afterTime = System.currentTimeMillis();
        String param1 = "APP_INSTALL";
        String param2 = elapsedTime() + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR
                + TimeZone.getDefault().getDisplayName();
        InstallerTrackUsageAgent.executeAgent(installFolder, param1, param2);
    }

}
