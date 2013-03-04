/*
 * @(#)ExcelExtractor.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.tcg.extensions.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * This class contains the code for extracting a list of test cases from an Excel file according to
 * the Test central standard.
 * 
 * RESPONSIBILITIES:
 * Extracts a list of test cases from an Excel file. 
 *
 * COLABORATORS:
 * It depends on the POI external library.
 *
 * USAGE:
 * ExcelExtractor extractor = new ExcelExtractor();
 * List<TextualTestCase> testCases = extractor.extractTestSuite(<file>);
 */
// INSPECT
public interface ExtractorDocumentExtension
{
    /**
     * This method extracts test cases from a given Excel file.
     * 
     * @param file The file to be read
     * @return The list of TextualTestCases extracted from the file
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File file) throws FileNotFoundException, IOException;
}
