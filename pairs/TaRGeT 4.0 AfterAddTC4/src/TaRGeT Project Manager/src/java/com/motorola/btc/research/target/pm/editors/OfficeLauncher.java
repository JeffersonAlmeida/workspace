/*
 * @(#)OfficeLauncher.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Aug 11, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 */

package com.motorola.btc.research.target.pm.editors;

import java.io.IOException;

import org.eclipse.ui.PartInitException;

/**
 * <pre>
 * CLASS:
 * This class abstracts the process used to open 
 * an office file (word document or excel spreadsheet) in word or excel.
 * 
 * RESPONSABILITIES:
 * Open an office document in MS Word or Excel 
 * </pre>
 */
public class OfficeLauncher
{

    /**
     * The single instance of OfficeLauncher.
     */
    private static OfficeLauncher instance;

    /**
     * The private constructor.
     */
    private OfficeLauncher()
    {
    }

    /**
     * Returns the single instance of <code>OfficeLauncher</code>.
     * 
     * @return The single instance of <code>OfficeLauncher</code>.
     */
    public static OfficeLauncher getInstance()
    {
        if (instance == null)
        {
            instance = new OfficeLauncher();
        }
        return instance;
    }

    /**
     * Opens the MS Word document specified by <code>file</code>.
     * 
     * @param file The name of the document to be opened.
     * @throws PartInitException Thrown when it is not possible to display the document.
     * @throws IOException Thrown when it is not possible to read the document.
     */
    public void openWordDocument(String file) throws PartInitException, IOException
    {
        this.openDocument(file, "winword");
    }

    /**
     * Opens the MS Excel document specified by <code>file</code>.
     * 
     * @param file The name of the document to be opened.
     * @throws PartInitException Thrown when it is not possible to display the document.
     * @throws IOException Thrown when it is not possible to read the document.
     */
    public void openExcelDocument(String file) throws PartInitException, IOException
    {
        this.openDocument(file, "excel");
    }

    /**
     * Opens <code>file</code> passed as parameter to <code>program</code>.
     * 
     * @param file The file to be opened.
     * @param program The name of the application to be launched.
     * @throws IOException Thrown when it is not possible to read the file or to launch the
     * application.
     */
    private void openDocument(String file, String program) throws IOException
    {
        String plainFile = "\"" + file + "\"";
        String osName = System.getProperty("os.name");
        osName.toUpperCase();
        String cmdLine = null;

        // The command line is SO dependent
        if ((osName.indexOf("NT") != -1) || (osName.indexOf("XP") != -1)
                || (osName.indexOf("2000") != -1))
        {
            cmdLine = "cmd /c start";
        }
        else
        {
            cmdLine = "start";
        }
        cmdLine += " ";
        cmdLine += program;
        cmdLine += " ";
        cmdLine += plainFile;

        Runtime.getRuntime().exec(cmdLine);
    }
}
