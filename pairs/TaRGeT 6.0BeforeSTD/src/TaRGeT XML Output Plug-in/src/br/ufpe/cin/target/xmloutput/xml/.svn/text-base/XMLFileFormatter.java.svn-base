/*
 * @(#)ExceFileFilter.java
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
 * pvcv      Apr 21, 2009                Initial creation.
 * fsf2      Aug 07, 2009                Changed XML output to support output transformation for TestLink.
 * lmn3      Aug 20, 2009                Inclusion of method to replace non standard Unicode characters in the test case due to issues with text encoding.
 * fsf2      Sep 15, 2009                Changed the generation to return the result file.
 */
package br.ufpe.cin.target.xmloutput.xml;

import java.io.File;
import java.io.IOException;

import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtension;
import br.ufpe.cin.target.tcg.extensions.output.TestSuiteXMLGenerator;

/**
 * This class contains the code for writing the test case excel file.
 * 
 * <pre>
 * CLASS:
 * The class receives a list of test cases and writes a excel file according to the Test central standard.
 * 
 * RESPONSIBILITIES:
 * Builds and writes an Excel file from a list of test cases. 
 * 
 * COLABORATORS:
 * It depends on the POI external library.
 * 
 * USAGE:
 * XMLFileFormatter xmlFof = new XMLFileFormatter(&lt;test_case_list&gt;);
 * xmlFof.writeTestCaseDataInFile(&lt;file_object&gt;);
 */
public class XMLFileFormatter extends OutputDocumentExtension
{

    /**
     * Method that should be used to write the xml file.
     * 
     * @param file The file where the xml information will be written.
     * @throws IOException Exception launched in case of something goes wrong during the file
     * writing.
     */
    //INSPECT - Lais changed remoção da geração do xml para tcg, inclusão de information test case para geração da traceability matrix
    public File writeTestCaseDataInFile(File file) throws IOException
    {
        super.writeTestCaseDataInFile(file);
        
        TestSuiteXMLGenerator generator = new TestSuiteXMLGenerator();
        File testSuiteFile = generator.generateXMLFile(this.testCases, file.getName(), this.informationTestCase);
        
        String fileName = file.getAbsolutePath().replace(FileUtil.getFileExtension(file), "xml");            
        file.delete();
        testSuiteFile.renameTo(new File(fileName));
        
        return file;
    }

    
    public String getExtensionFile()
    {
        return ".xml";
    }
}
