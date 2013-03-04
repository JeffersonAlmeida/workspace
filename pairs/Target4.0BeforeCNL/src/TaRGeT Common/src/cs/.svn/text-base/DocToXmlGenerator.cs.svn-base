/*
 * @(#)DocToXmlGenerator.cs
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *    -           -             -        Initial creation.
 * wdt022    Feb 23, 2007   LIBkk16317   Modification to handle documents without XML content.
 * wdt022    May 15, 2007   LIBmm26220   Modification to handle the new functionality that keeps this program alive during all TaRGeT execution.
 * wdt022    May 22, 2007   LIBmm26220   Rework of inspection LX175105.
 * dhq348    Aug 22, 2007   LIBmm93130   Added method extractXML().
 * dhq348    Sep 03, 2007   LIBnn24462   Fix error in extractXML() method. Error captured by tests.
 * fsf2      Mar 12, 2009                Added support for Office 2007.
 */
using System;
using System.IO;
using System.Collections.Generic;
using Word = Microsoft.Office.Interop.Word;


namespace WordUseCaseTools
{
    /**
     * Class that encapsulates all the necessary code for processing the MS Word 
     * documents and extracting the XML content. It also manages the communication with the 
     * Java process.
     * 
     */
    public class DocToXmlGenerator
    {
        /* The instance that represents the MS Word application */
        private Word.ApplicationClass wordApplication = null;

        /* The Id of a success processing operation */
        public const int OK_COMMAND = 0;

        /* The Id of a failed extracting operation */
        public const int ERROR_EXTRACTING_XML = 1;

        /* Represents the missing object. It is used when no parameter need to be informed. */
        object missing = System.Reflection.Missing.Value;
        object falseObj = false;

        /* Parameters used to open an Microsoft Word document. */
        object confirmConversions = false;
        object readOnly = true;
        object addToRecentFiles = false;
        object passwordDocument = System.Reflection.Missing.Value;
        object passwordTemplate = System.Reflection.Missing.Value;
        object revert = false;
        object writePasswordDocument = System.Reflection.Missing.Value;
        object writePasswordTemplate = System.Reflection.Missing.Value;
        object format = Word.WdOpenFormat.wdOpenFormatDocument;
        object encoding = System.Reflection.Missing.Value;
        object visible = false;
        object openAndRepair = System.Reflection.Missing.Value;
        object documentDirection = System.Reflection.Missing.Value;
        object noEncodingDialog = true;
        object xmlTransform = System.Reflection.Missing.Value;

        /**
         * Class constructor. It instantiates the Word application.
         */
        public DocToXmlGenerator()
        {
            wordApplication = new Word.ApplicationClass();
            wordApplication.Visible = false;
        }

        /**
         * Extracts the XML content from a list of Microsoft Word documents. The XML content is saved 
         * into several files, each XML file for each Microsoft Word document. If any Word document is
         * corrupted or does not contain XML content, only the corrupted XML files will not be generated.
         * 
         * This program writes the processing result into the standard output in order to communicate to
         * the Java process.
         */
        public void extractAllXmlsFromNonCorruptedDocuments(List<string> docFiles, List<string> xmlFiles)
        {
            bool hasXMLExtractionErrors = false;
            // Stores the files that were not processed, when an error ocurred.
            List<string> errorDocumentFiles = new List<string>();

            for (int i = 0; i < docFiles.Count; i++)
            {
                object doc = (object)docFiles[i];
                object xmlFile = (object)xmlFiles[i];

                try
                {
                    this.extractXML(doc,xmlFile);
                }
                catch (Exception e)
                {
                    errorDocumentFiles.Add(docFiles[i]);
                    hasXMLExtractionErrors = true;
                }

                // Verifies if the XML was extracted
     /*           if (!String.IsNullOrEmpty(data))
                {
                    try
                    {
                        // Writes the XML file
                        StreamWriter sr = new StreamWriter(xmlFiles[i]);
                        sr.Write(data);
                        sr.Close();
                    }
                    catch (Exception)
                    {
                        errorDocumentFiles.Add(docFiles[i]);
                        hasXMLExtractionErrors = true;
                    }
                }*/
            }

            if (hasXMLExtractionErrors)
            {
                // Writes the failed Id and the unprocessed document paths into the standard output */
                Console.Out.WriteLine(ERROR_EXTRACTING_XML);
                Console.Out.WriteLine(errorDocumentFiles.Count);
                foreach (String filePath in errorDocumentFiles)
                {
                    Console.Out.WriteLine(filePath);
                }
            }
            else
            {
                Console.Out.WriteLine(OK_COMMAND);
            }

            Console.Out.Flush();
        }

        /**
         * Extracts the xml content from the ms word document.
         */
        private void extractXML(object doc, object xmlFile)
        {
            /* represents a single word document */
            Word.Document wordDocument = null;

            try
            {
                // Open the Microsoft Word document.
                wordDocument = wordApplication.Documents.Open(ref doc,
                    ref confirmConversions, ref readOnly, ref addToRecentFiles,
                    ref passwordDocument, ref passwordTemplate, ref revert, ref writePasswordDocument,
                    ref writePasswordTemplate, ref format, ref encoding, ref visible,
                    ref openAndRepair, ref documentDirection, ref noEncodingDialog, ref xmlTransform);
            }
            catch (Exception)
            {
                // An exception occurred. Maybe the MS Word is closed
                wordApplication = new Word.ApplicationClass();
                wordApplication.Visible = false;
                // Open the Microsoft Word document.
                wordDocument = wordApplication.Documents.Open(ref doc,
                    ref confirmConversions, ref readOnly, ref addToRecentFiles,
                    ref passwordDocument, ref passwordTemplate, ref revert, ref writePasswordDocument,
                    ref writePasswordTemplate, ref format, ref encoding, ref visible,
                    ref openAndRepair, ref documentDirection, ref noEncodingDialog, ref xmlTransform);
            }

            try
            {
                //Get xml data from Word document
                object filePath = (object)xmlFile;
                object format = Word.WdSaveFormat.wdFormatXML;

                wordDocument.SaveAs(ref filePath, ref format, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing, ref missing);
            }
            catch (Exception e)
            {
                // Closes the opened Word Document
                wordDocument.Close(ref falseObj, ref missing, ref missing);
                throw e;
            }
            
            // Closes the opened Word Document
            wordDocument.Close(ref falseObj, ref missing, ref missing);
        }

        /**
         * Closes the Microsoft Word application.
         */
        private void closeWordApplication()
        {
            this.wordApplication.Quit(ref falseObj, ref missing, ref missing);
        }

        /**
         * This method keeps listening to the standard input, waiting requests from 
         * the Java process.
         * 
         */
        private void listenConsole()
        {
            string readLine = Console.ReadLine();

            // While the line is not null and is not nthe char "."
            while (readLine != null && readLine.CompareTo(".") != 0)
            {
                String[] args = readLine.Split(new char[] { '|' });

                List<string> docFiles = new List<string>();
                List<string> xmlFiles = new List<string>();
                
                int halfSize = args.Length / 2;
                for (int i = 0; i < halfSize; i++)
                {
                    docFiles.Add(args[i]);
                    xmlFiles.Add(args[i + halfSize]);
                }

                this.extractAllXmlsFromNonCorruptedDocuments(docFiles, xmlFiles);
                readLine = Console.ReadLine();
            }
            // Answer the Java process indicating that the .NET process exit was successful.
            Console.Out.WriteLine(OK_COMMAND);
            Console.Out.Flush();
        }

        /**
         * Main function. It calls the method listenConsole in order to wait the Java process requests.
         * When the communication with the Java process ends, the MS Word application is closed.
         */
        static void Main(string[] args)
        {
            DocToXmlGenerator dtxg = new DocToXmlGenerator();

            dtxg.listenConsole();

            dtxg.closeWordApplication();
        }
    }
}
