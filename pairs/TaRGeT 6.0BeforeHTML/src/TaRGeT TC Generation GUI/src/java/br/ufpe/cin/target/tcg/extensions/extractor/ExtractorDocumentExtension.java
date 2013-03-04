/*
 * @(#)ExcelExtractor.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000   	Initial creation.
 * fsf2		20/06/2009					Integration.
 */
package br.ufpe.cin.target.tcg.extensions.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

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
public interface ExtractorDocumentExtension
{
    /**
     * This method extracts test cases from a given Excel file.
     * 
     * @param file The file to be read
     * @param headers 
     * @return The list of TextualTestCases extracted from the file
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File file) throws FileNotFoundException, IOException;
}
