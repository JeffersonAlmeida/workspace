/*
 * @(#)InstallerHandler.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *  dhq348   Mar 01, 2007   LIBll54505   Initial creation.
 *  dhq348   Mar 14, 2007   LIBll54505   Rework of inspection LX153436.
 */
package com.motorola.btc.research.target.installer;

import java.io.File;

import javax.swing.JOptionPane;

import com.install4j.api.InstallerContext;
import com.install4j.api.StartupHandler;
import com.install4j.api.Util;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class is executed before the beginning of the install process.
 * 
 * <pre>
 * CLASS:
 * It verifies if the resources needed by the TaRGeT are working fine. 
 * It tests if the '.dat' file exists and if the .NET framework is installed.
 * It is executed by the installer of the TaRGeT tool during the verification 
 * of all pre-requisites of the system.
 */
public class InstallerHandler extends StartupHandler
{

    /**
     * Checks if the system contains all necessary installed components for launching the installer.
     * 
     * @param filename The name of the file to be checked.
     * @return If the system configuration is ok.
     */
    private boolean checkDotNet(String filename, String path)
    {
        boolean result = true;
        try
        {
            InstallerWordDocumentProcessing processing = new InstallerWordDocumentProcessing();
            processing.createObjectsFromWordDocument(filename, path);
        }
        catch (UseCaseDocumentXMLException e)
        {
            result = false;
        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
    }

    /**
     * Checks if the '.dat' file exists in the 'resources' directory that comes with the installer.
     * 
     * @param filename The name of the file to be checked.
     * @return <b>true</b> if it exists or <b>false</b> otherwise.
     */
    private boolean checkDatFile(String filename)
    {
        File file = new File(filename);
        return file.exists();
    }

    /**
     * This method is called by the framework before the installer is displayed. If false is
     * returned, the installer is not executed.
     * 
     * @param context The context used by the installer.
     * @return <code>true</code> if the is executed or <code>false</code> otherwise.
     */
    public boolean prepareInstaller(InstallerContext context)
    {
        boolean result = false;

        String path = FileUtil.getFilePath(context.getInstallerFile().getAbsolutePath())
                + FileUtil.getSeparator() + "resources" + FileUtil.getSeparator();
        String filename = path + "ucsample01validation.dat";
        String osName = System.getProperty("os.name");
        if (!osName.equals("Windows XP"))
        {
            if (!context.isUnattended())
            {
                Util.showMessage("The tool can only be installed under MS Windows XP.",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (!checkDatFile(filename))
        {
            if (!context.isUnattended())
            {
                Util
                        .showMessage(
                                "The file \"ucsample01validation.dat\" does not exist. Please download the file again and place it in the installer directory.",
                                JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (!checkDotNet(filename, path))
        {
            if (!context.isUnattended())
            {
                Util.showMessage("The .NET framework or the Office component is not installed.",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            result = true;
        }
        return result;
    }
}