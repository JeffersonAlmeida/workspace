/*
 * @(#)WordDocumentProcessing.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *    -            -        LIBkk11577   Added to version control.
 * wdt022    Jan 16, 2007   LIBkk11577   Changes after inspection LX128956.
 * wdt022    Feb 23, 2007   LIBkk16317   Handling error when no xml is generated.
 * wdt022    Mar 08, 2007   LIBll29572   Modification according to CR.
 * wsn013    Apr 24, 2007   LIBkk22882   Insertion of complete path for generated xml files.  
 * wdt022    May 15, 2007   LIBmm26220   New functionality for starting and keep running the .NET process.
 * wdt022    May 25, 2007   LIBmm26220   Rework of inspection LX175105. 
 */
package com.motorola.btc.research.target.mswordinput.ucdoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.xml.UseCaseDocumentXMLParser;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class is used to extract a <code>PhoneDocument</code> Java object from a Word Document.
 * 
 * <pre>
 * CLASS:
 * This class is responsible for starting and keep communicating with a .NET process, which is started by running
 * an executable file. The communication with the process is done by its standard inputs and outputs.
 * <p>
 * The extraction process is done in two steps. The first step comprehends the extraction of the XML file from the Word document. The
 *  .NET process is responsible for reading the Word file, extracting the XML information and writing it into a file. 
 * The second step consists in reading the extracted XML file, parsing it, and mounting the <code>PhoneDocument</code> Java object.   
 *  
 * USAGE:
 * WordDocumentProcessing wdp = WordDocumentProcessing();
 * PhoneDocument pDoc = wdp.createObjectsFromWordDocument(<word_document_file_path>);
 */
public class WordDocumentProcessing
{

    /** Id of a successful command submission to the .NET program. */
    public static final int OK_COMMAND = 0;

    /**
     * Id of a failed command submission to the .NET program. It means that a document may not be
     * correctly processed.
     */
    public static final int ERROR_EXTRACTING_XML = 1;

    /** Location of .NET XML extractor and its necessary dlls */
    private static final String DLL_FOLDER = FileUtil.getUserDirectory() +
     					FileUtil.getSeparator()
     						+ "resources" + FileUtil.getSeparator();

    /**
     * Native converter (.NET compilation for Windows) that extracts the .xml associated with a word
     * document
     */
    public static final String EXE_FILE = "DocToXmlConverter.exe";

    /** .NET standard input channel */
    private OutputStreamWriter dotNetStdin;

    /** .NET standard output channel */
    private BufferedReader dotNetStdout;

    /** Singleton instance */
    private static WordDocumentProcessing instance = null;

    /** Boolean that indicates if the .NET process is being listened */
    private boolean isListening;

    /**
     * Private constructor for singleton design pattern. It does not perform any operation.
     */
    private WordDocumentProcessing()
    {
        this.isListening = false;
    }

    /**
     * Returns the singleton instance of this class.
     * 
     * @return An instance of <code>WordDocumentProcessing</code> class.
     */
    public static WordDocumentProcessing getInstance()
    {
        if (instance == null)
        {
            instance = new WordDocumentProcessing();
        }
        return instance;
    }

    /**
     * Kills the .NET process. It sends an end signal (".\n") to the .NET program, and destroy the
     * process.
     * 
     * @throws UseCaseDocumentException In case of any error during the signal sending.
     */
    public synchronized void finishListener() throws UseCaseDocumentXMLException
    {
        if (this.isListening)
        {
            this.informArgumentsToDotNet(".\n");
            this.isListening = false;
        }
    }

    /**
     * Passes some arguments to the .NET program via the standard input.
     * <p>
     * The .NET program accepts arguments for two purposes: (1) finish the connection with the Java
     * implementation; (2) process some documents and extract their XML contents. For the purpose
     * (2), the arguments must be a sequence of file paths representing, sequentially, the input MS
     * Word files and the output XML files. The file path must be separated by the character "|", as
     * described bellow:
     * 
     * <pre>
     *          _document_1.doc|_document_2.doc|_document_1.xml|_document_2.xml
     * 
     * @param arguments A string containing the arguments to inform to the .NET process.
     * @return The list of documents that were not processed, in case of error.
     * @throws UseCaseDocumentException In case of any error accessing the .NET standard input and output.
     */
    private List<String> informArgumentsToDotNet(String arguments)
            throws UseCaseDocumentXMLException
    {
        List<String> result = null;
        try
        {
            this.dotNetStdin.append(arguments);
            this.dotNetStdin.flush();
            result = this.getResult();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new UseCaseDocumentXMLException(e,
                    "Error conecting to .NET program. The XML extraction result could not be read.");
        }
        return result;
    }

    /**
     * Reads and analyses the responses to the stimuli passed to .NET program.
     * 
     * @return The list of MS Word file paths that were not processed. An empty list is returned in
     * case of success.
     * @throws IOException In case of any error accessing the .NET standard output.
     */
    private List<String> getResult() throws IOException
    {
        int responseId = Integer.parseInt(this.readLine());
        List<String> errorDocuments = new ArrayList<String>();

        switch (responseId)
        {
            case OK_COMMAND:
                break;
            case ERROR_EXTRACTING_XML:
                int numErrorDocs = Integer.parseInt(this.readLine());

                for (int i = 0; i < numErrorDocs; i++)
                {
                    String errorDocPath = this.readLine();
                    errorDocuments.add(errorDocPath);
                }
                break;
        }

        return errorDocuments;
    }

    /**
     * Reads a line from the .NET standard output.
     * 
     * @return The line read.
     * @throws IOException In case of any error accessing the .NET standard output.
     */
    private String readLine() throws IOException
    {
        String line = null;

        do
        {
            line = this.dotNetStdout.readLine();

        } while (line == null);

        return line.trim();
    }

    /**
     * Initializes the .NET process. The method executes the <code>EXE_FILE</code>, and keeps the
     * <code>Process</code> instance and its standard input and output.
     * 
     * @throws UseCaseDocumentException In case of error on the .NET program execution.
     */
    public synchronized void initListener() throws UseCaseDocumentXMLException
    {
        if (!this.isListening)
        {
            String command = DLL_FOLDER + EXE_FILE;
            Process p = null;

            try
            {
                p = Runtime.getRuntime().exec(command);
            }
            catch (Exception e)
            {
                String message = "Error while executing \"" + DLL_FOLDER + EXE_FILE + "\".";
                e.printStackTrace();
                throw new UseCaseDocumentXMLException(e, message);
            }
            this.dotNetStdin = new OutputStreamWriter(p.getOutputStream());
            this.dotNetStdout = new BufferedReader(new InputStreamReader(p.getInputStream()));

            this.isListening = true;
        }
    }

    /**
     * This method is responsible for extracting the XML content from each Microsoft Word document
     * of the list <code>docfileNames</code>. The XML content of each Word documet is stored in
     * the files listed in <code>xmlfileNames</code>.
     * 
     * @param docfileNames The list of absolute paths of Word document files.
     * @param xmlfileNames The list of absolute paths of the XML files that will be output.
     * @throws UseCaseDocumentException It is launched in case of any error during the extraction
     * process.
     */
    private List<String> generateXmlFromWordDocument(List<String> docfileNames,
            List<String> xmlfileNames) throws UseCaseDocumentXMLException
    {
        String commandDocFiles = "";
        String commandXMLFiles = "";
        List<String> result = new ArrayList<String>();

        for (int i = 0; i < docfileNames.size(); i++)
        {
            if (i > 0)
            {
                commandDocFiles += "|";
                commandXMLFiles += "|";
            }
            commandDocFiles += docfileNames.get(i);
            commandXMLFiles += xmlfileNames.get(i);
        }
        if (docfileNames.size() > 0)
        {
            String command = commandDocFiles + "|" + commandXMLFiles + "\n";

            result = this.informArgumentsToDotNet(command);
        }

        return result;
    }

    /**
     * Reads each Word document file from the list <code>docFileNames</code>, and extracts a list
     * of <code>PhoneDocument</code> objects. During this process, a XML file is written and
     * removed. If the attribute <code>throwExceptionOnAnyError</code> is set to <code>true</code>,
     * the method throws an exception when the first error is found on the XML extraction. If the
     * attribute is <code>false</code>, no exception is launched to inform errors on the XML
     * extraction phase.
     * 
     * @param docFileNames The list of input Microsoft word file absolute paths.
     * @param throwExceptionOnAnyError If <code>true</code>, an exception is thrown on first XML
     * extraction error.
     * @return A list of <code>PhoneDocument</code> objects.
     * @throws UseCaseDocumentException It may be thrown during the XML extraction from Word
     * document, or during the XML parsing.
     */
    public synchronized List<PhoneDocument> createObjectsFromWordDocument(
            List<String> docFileNames, boolean throwExceptionOnAnyError)
            throws UseCaseDocumentXMLException
    {
        List<PhoneDocument> result = new ArrayList<PhoneDocument>();

        // Define XML file name
        String tempFileName = FileUtil.TEMP_FOLDER + FileUtil.getSeparator()
                + System.currentTimeMillis();
        List<String> xmlFileNames = new ArrayList<String>();
        for (int i = 0; i < docFileNames.size(); i++)
        {
            xmlFileNames.add(tempFileName + "_" + i + ".xml");
        }

        // Create XML file and retrieve Objects
        this.generateXmlFromWordDocument(docFileNames, xmlFileNames);

        for (int i = 0; i < docFileNames.size(); i++)
        {
            PhoneDocument phone = null;
            File xmlFile = new File(xmlFileNames.get(i));
            String errorMessage = null;
            long lastModified = (new File(docFileNames.get(i))).lastModified();

            if (xmlFile.exists())
            {
                try
                {
                    UseCaseDocumentXMLParser ucDocParser = new UseCaseDocumentXMLParser(xmlFile);
                    phone = ucDocParser.buildPhoneDocument();
                    phone.setDocFilePath(docFileNames.get(i));
                    phone.setLastDocumentModification(lastModified);
                }
                catch (UseCaseDocumentXMLException e)
                {
                    phone = new PhoneDocument(docFileNames.get(i), lastModified);
                    errorMessage = "An error occurred while parsing the XML content extracted from the document "
                            + FileUtil.getFileName(docFileNames.get(i))
                            + ". The XML may be malformed.";
                }
            }
            else
            {
                phone = new PhoneDocument(docFileNames.get(i), lastModified);
                errorMessage = "An error occurred while extracting the XML content from the document "
                        + FileUtil.getFileName(docFileNames.get(i))
                        + ". The document may not contain XML content.";
            }

            result.add(phone);

            if (errorMessage != null && throwExceptionOnAnyError)
            {
                FileUtil.deleteFiles(xmlFileNames.toArray(new String[] {}));
                throw new UseCaseDocumentXMLException(errorMessage);
            }
        }

        // Remove XML files
        FileUtil.deleteFiles(xmlFileNames.toArray(new String[] {}));

        return result;
    }

}
