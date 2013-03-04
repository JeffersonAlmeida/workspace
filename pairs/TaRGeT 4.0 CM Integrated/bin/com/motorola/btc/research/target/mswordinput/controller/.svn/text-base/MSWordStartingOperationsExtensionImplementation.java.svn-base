/*
 * @(#)MSWordStartingOperationsExtensionImplementation.java
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
package com.motorola.btc.research.target.mswordinput.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.core.extensions.StartingOperationsExtension;
import com.motorola.btc.research.target.mswordinput.ucdoc.WordDocumentProcessing;

//INSPECT new class
/**
 * This class implements the Starting Operations extension point. In this particular case,
 * the starting operations performed by this class consist of checking the .NET framework and
 * the existence of a file which is used for verifying the MS Word. 
 */
public class MSWordStartingOperationsExtensionImplementation implements StartingOperationsExtension
{

    /* (non-Javadoc)
     * @see com.motorola.btc.research.target.core.extensions.StartingOperationsExtension#performStartingOperations()
     */
    
    public boolean performStartingOperations()
    {
        boolean result = true;

        String separator = FileUtil.getSeparator();

        String userDir = System.getProperty("user.dir");

        String filename = userDir + separator + "resources" + separator
                + "ucsample01validation.dat";

        if (!checkDatFile(filename))
        {
            MessageDialog
                    .openError(
                            null,
                            "Error while launching!",
                            "The file "
                                    + "\"ucsample01validation.dat\""
                                    + " does not exist. Please download it again and place it in the application directory.");
            result = false;
        }
        else if (!checkDotNet(filename))
        {
            result = MessageDialog
                    .openConfirm(null, "Error while launching!",
                            "The .NET framework or the Office component is not installed. Do you want to proceed?");
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

}
