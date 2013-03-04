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
 * fsf2      21/04/2009                  Initial creation.
 */
package br.ufpe.cin.target.xmloutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import br.ufpe.cin.target.tcg.extensions.extractor.ExtractorDocumentExtension;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

import com.thoughtworks.xstream.XStream;

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
public class XMLExtractor implements ExtractorDocumentExtension
{
    /**
     * This method extracts test cases from a given Excel file.
     * 
     * @param file The file to be read
     * @return The list of TextualTestCases extracted from the file
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File file) throws FileNotFoundException,
    IOException
    {
        XStream xmlStream = new XStream();

        xmlStream.alias("testCase",
                br.ufpe.cin.target.tcg.extractor.TextualTestCase.class);
        xmlStream.alias("step", br.ufpe.cin.target.tcg.extractor.TextualStep.class);
        xmlStream.alias("testCases", List.class);

        SAXBuilder saxBuilder = new SAXBuilder();

        List<TextualTestCase> testCases = null;

        try
        {
            Document document = saxBuilder.build(file);

            Element testSuite = document.getRootElement();

            Element testCasesElement = testSuite.getChild("testCases");

            Format fmt = Format.getPrettyFormat();

            fmt.setEncoding("ISO-8859-1");

            XMLOutputter xmlOutputter = new XMLOutputter(fmt);

            File fileTemp = new File("fileTemp" + System.currentTimeMillis() + ".xml");
            FileOutputStream fileIOTemp = new FileOutputStream(fileTemp);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter
                    (fileIOTemp,"ISO-8859-1"));
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
            bufferedWriter.newLine();

            xmlOutputter.output(testCasesElement, bufferedWriter);
            bufferedWriter.close();
            fileIOTemp.close();
            
            FileReader fileReader = new FileReader(fileTemp);
           
            testCases = (List<TextualTestCase>) xmlStream.fromXML(fileReader);
            
            fileReader.close();

            fileTemp.delete();
        }
        catch (JDOMException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        return testCases;
    }

    /**
     * Comment for method.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        /*XMLExtractor xmlExtractor = new XMLExtractor();

        try
        {
            List<TextualTestCase> testCases = xmlExtractor.extractTestSuite(new File("a.xml"));

            System.out.println();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
    }
}
