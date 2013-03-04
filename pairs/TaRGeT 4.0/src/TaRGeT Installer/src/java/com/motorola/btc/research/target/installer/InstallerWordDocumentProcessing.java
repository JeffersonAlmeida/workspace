/*
 * @(#)InstallerWordDocumentProcessing.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *  dhq348   Mar 1, 2007    LIBll54505   Initial creation.
 *  wdt022   May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 */
package com.motorola.btc.research.target.installer;

import java.io.File;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing;

/** 
 * <pre>
 * CLASS:
 * This class is a subclass of WordDocumentProcessing because 
 * it was necessary to customize the path used by the EXE file.
 * </pre>
 */
public class InstallerWordDocumentProcessing
{
    /**
     * This method is responsible for extracting the XML information from the Microsoft Word
     * document and writes this information into a file. This method depends on a dll and executable
     * file that are used to read the Word document and to extract the XML.
     * 
     * @param docfileName The absolute path of the Word document file to be processed.
     * @param xmlfileName The absolute path of the XML file that will be outputed.
     * @param path The path to the executable file.
     * @throws UseCaseDocumentXMLException It is thrown in case of any error during the installation
     * process.
     */
    private void generateXmlFromWordDocument(String docfileName, String xmlfileName, String path)
            throws UseCaseDocumentXMLException
    {
        // Try to open Word document.
        File file = new File(docfileName);
        if (!file.canRead())
        {
            String message = "The file \"" + docfileName + "\" can not be read.";
            System.err.println(message);
            throw new UseCaseDocumentXMLException(message);
        }

        // Generate the XML file
        String command = path + WordDocumentProcessing.EXE_FILE;
        command += " \"" + docfileName + "\" \"" + xmlfileName + "\"";

        Process p = null;
        String message = "Error while executing \"" + command + "\".";
        try
        {
            p = Runtime.getRuntime().exec(command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(message);
            throw new UseCaseDocumentXMLException(e, message);
        }

        try
        {
            // Verify result.
            p.waitFor();

            int exitValue = p.exitValue();
            if (exitValue != 0)
            {
                throw new UseCaseDocumentXMLException(message);
            }
        }
        catch (InterruptedException ie)
        {
            System.err.println(message);
            throw new UseCaseDocumentXMLException(ie, message);
        }
        catch (Exception e)
        {
            System.err.println(message);
            throw new UseCaseDocumentXMLException(e, message);
        }
    }

    /**
     * Reads a Word document file and extracts a <code>PhoneDocument</code> object. During this
     * process, a XML file is written and removed.
     * 
     * @param docFileName The input Microsoft word file absolute path.
     * @param path The path used to retrieve the executable in createObjectsFromWordDocument().
     * @return PhoneDocument A instace of <code>PhoneDocument</code> class.
     * @throws UseCaseDocumentXMLException It may be thrown during the XML extraction from Word
     * document, or during the XML parsing.
     */
    public PhoneDocument createObjectsFromWordDocument(String docFileName, String path)
            throws UseCaseDocumentXMLException
    {
        PhoneDocument phone = null;

        // Define XML file name
        String tempFileName = System.currentTimeMillis() + ".xml";
        String xmlFileName = path + tempFileName;

        try
        {
            // Create XML file and retrieve Objects
            this.generateXmlFromWordDocument(docFileName, xmlFileName, path);
            File xmlFile = new File(xmlFileName);

            InstallerUseCaseDocumentXMLParser ucDocParser = new InstallerUseCaseDocumentXMLParser(
                    xmlFile, path);
            phone = ucDocParser.buildPhoneDocument();
            phone.setDocFilePath(docFileName);
            phone.setLastDocumentModification((new File(docFileName)).lastModified());

        }
        finally
        {
            // Remove XML file
            File tempFile = new File(xmlFileName);
            tempFile.delete();
        }
        return phone;
    }
}
