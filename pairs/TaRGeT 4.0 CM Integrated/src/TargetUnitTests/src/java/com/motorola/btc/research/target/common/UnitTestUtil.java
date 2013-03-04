/*
 * @(#)UnitTestUtil.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Apr 10, 2007   LIBkk22882   Initial creation.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * dhq348    Aug 21, 2007   LIBmm42774   Rework after inspection LX201094. Added common method getPhoneDocument().
 */
package com.motorola.btc.research.target.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class encapsulates utility methods for Target´s unit tests.
 */
public class UnitTestUtil
{
    /** The folder that contains the input data for the tests */
    public static final String INPUT_FOLDER = FileUtil.getUserDirectory() + FileUtil.getSeparator()
            + "resources";

    /** The output folder where the test can create temporary files */
    public static final String OUTPUT_FOLDER = FileUtil.TEMP_FOLDER;

    static
    {
        try
        {
            changeUserDirectory();
            WordDocumentProcessing.getInstance().initListener();
        }
        catch (Exception e)
        {
            System.out
                    .println("Error trying to change the user directory or initializing the .NET listener");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Returns the path for a temporary output folder which may be used by the unit tests.
     * 
     * @param outputDirectoryName The name of the directory in which the tests may store the
     * produced outputs.
     * @return The path for the output directory for the unit test.
     */
    public static String getTestOutputDirectoryPath(String outputDirectoryName)
    {

        String path = new File(OUTPUT_FOLDER).getAbsolutePath() + FileUtil.getSeparator()
                + outputDirectoryName;
        File testFolder = new File(path);

        if (!testFolder.exists())
        {
            testFolder.mkdirs();
            System.out.println("Created test temporary folder: " + testFolder.getAbsolutePath());
        }
        return path;
    }

    /**
     * Returns the path for the directory where the unit test inputs are stored.
     * 
     * @param resourcerFolderName The name of the directory whose path will be returned.
     * @return The path for the input resources directory for the unit test.
     */
    public static String getTestInputDirectoryPath(String inputFolderName)
    {
        return new File(INPUT_FOLDER).getAbsolutePath() + FileUtil.getSeparator() + inputFolderName;
    }

    /**
     * Copy the necessary resources to enable the use of the class
     * com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing.
     * <p>
     * This method creates a 'resource' folder in the <code>OUTPUT_FOLDER</code> and copies all
     * the contents from the folder 'target//common//resources' to that folder. Afterwards, It
     * changes the 'user.dir' property to point to the folder <code>OUTPUT_FOLDER</code>.
     * <p>
     * This method is called by a static block, since the above operations MUST be performed BEFORE
     * any calling of the <code>WordDocumentProcessing</code> methods.
     * 
     * @param outputDirectoryName The name of the directory that stores the outputs produced by the
     * test.
     * @return The path for the output directory for the unit test.
     * @throws IOException Thrown in case there is some a problem while copying the necessary
     * resources.
     * @see com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing
     */
    private static void changeUserDirectory() throws IOException
    {

        /*
         * the resources directory where the class
         * com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing looks for the
         * executable 'DocToXmlConverter.exe'
         */
        File resources = new File(OUTPUT_FOLDER + FileUtil.getSeparator() + "resources");
        if (!resources.exists())
        {
            resources.mkdirs();
            System.out.println("Created resources folder: " + resources.getAbsolutePath());
        }

        /* copy the necessary files to run 'DocToXmlConverter.exe'into the resources folders */
        FileUtil.copyFolder(FileUtil.getUserDirectory() + FileUtil.getSeparator()
                + "..//common//resources", resources.getAbsolutePath(), true);

        /*
         * user.dir is set to resources directory in order that the class
         * com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing can run
         * 'DocToXmlConverter.exe'
         */
        System.setProperty("user.dir", OUTPUT_FOLDER);
    }

    /**
     * Loads <code>documentName</code> as a <code>PhoneDocument</code> object.
     * 
     * @param documentName The name of the docuemnt to be loaded.
     * @return A <code>PhoneDocument</code> object loaded from <code>documentName</code>.
     * @throws UseCaseDocumentXMLException Thrown if any error occurs during the extraction of
     * contents from the MS Word document.
     */
    public static PhoneDocument getPhoneDocument(String documentName)
            throws UseCaseDocumentXMLException
    {
        /* Load the XML from the files */
        List<String> docs = new ArrayList<String>();
        docs.add(documentName);

        WordDocumentProcessing wdp = WordDocumentProcessing.getInstance();
        // wdp.initListener();

        System.out.print("[STARTED] Loading document " + documentName + ".");
        Collection<PhoneDocument> userViewDocuments = wdp.createObjectsFromWordDocument(docs, true);
        System.out.println(" [FINISHED]");

        PhoneDocument result = null;
        if (!userViewDocuments.isEmpty())
        {
            /* Set the phone document objects */
            Iterator<PhoneDocument> i = userViewDocuments.iterator();
            result = i.next();
        }

        return result;
    }
}
