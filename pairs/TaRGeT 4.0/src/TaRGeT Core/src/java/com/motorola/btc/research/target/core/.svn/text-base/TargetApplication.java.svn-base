/*
 * @(#)TargetApplication.java
 *
 * (c) COPYRIGHT 2006 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348         -         LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007   LIBkk11577   Rework of inspection LX133710.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * dwvm83	 Nov 04, 2008				 The interface IPlatformRunnable has been deprecated and the IApplication interface is used instead.
 */
package com.motorola.btc.research.target.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class controls all aspects of the application's execution.
 * 
 * <pre>
 * CLASS:
 * This class controls all aspects of the application's execution. 
 * It is responsible for running all the application.
 */
public class TargetApplication implements IApplication
{
    /**
     * The system file separator.
     */
    private static final String SEPARATOR = FileUtil.getSeparator();

    /**
     * The directory in which the application has been launched from.
     */
    private String userDir;

    /**
     * Instantiates the SEPARATOR with the system SEPARATOR string and gets the system user
     * directory in which the application has been launched from.
     */
    public TargetApplication()
    {
        this.userDir = System.getProperty("user.dir");
    }

    /**
     * Starts this application with the given context and returns a result.  This 
	 * method must not exit until the application is finished and is ready to exit.
	 * The content of the context is unchecked and should conform to the expectations of
	 * the application being invoked.
     * 
	 * @param context the application context to pass to the application
	 * @return the return value of the application
	 * @exception Exception if there is a problem running this application.
     */
 
    public Object start(org.eclipse.equinox.app.IApplicationContext context) throws Exception 
    {
        Display display = PlatformUI.createDisplay();
        Object result = null;
        try
        {
            String filename = this.userDir + SEPARATOR + "resources" + SEPARATOR
                    + "ucsample01validation.dat";
              if (!this.checkDatFile(filename))
            {
                MessageDialog
                        .openError(
                                null,
                                "Error while launching!",
                                "The file "
                                        + "\"ucsample01validation.dat\""
                                        + " does not exist. Please download it again and place it in the application directory.");
            }
            else if (!checkDotNet(filename))
            {
                MessageDialog.openError(null, "Error while launching!",
                        "The .NET framework or the Office component is not installed.");
            }
            else
            {
                int returnCode = PlatformUI.createAndRunWorkbench(display,
                        new ApplicationWorkbenchAdvisor());
                if (returnCode == PlatformUI.RETURN_RESTART)
                {
                	result = IApplication.EXIT_RESTART;
                }
                else
                {
                    result = IApplication.EXIT_OK;
                }
            }
        }
        finally
        {
            display.dispose();
        }
        return result;
    }

    /**
     * Checks if the system contains all necessary installed components for launching the
     * application.
     * 
     * @param filename The name of the file to be checked.
     * @return If the system configuration is ok.
     */
    private boolean checkDotNet(String filename)
    {
        boolean result = true;
        try
        {
            WordDocumentProcessing processing = WordDocumentProcessing.getInstance();
            
            processing.initListener();

            List<String> docFileNames = new ArrayList<String>();
            docFileNames.add(filename);
            processing.createObjectsFromWordDocument(docFileNames, true);
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
     * Checks if the '.dat' file exists in the directory of the application.
     * 
     * @param filename The name of the file to be checked.
     * @return <b>true</b> if it exists or <b>false</b> otherwise.
     */
    private boolean checkDatFile(String filename)
    {
        File file = new File(filename);
        return file.exists();
    }
    
    @Override
    public void stop() 
    {
    	// TODO Auto-generated method stub
    	
    }
}
