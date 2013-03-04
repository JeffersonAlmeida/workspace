/*
 * @(#)HTMLFileFormatter.java
 *
 * Copyright (c) <2007-2009> <Research Project Team – CIn-UFPE>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE
 *
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   17/11/2009      LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.htmloutput.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtension;
import br.ufpe.cin.target.tcg.extensions.output.TestSuiteXMLGenerator;

public class HTMLFileFormatter extends OutputDocumentExtension
{

    /**
     * Method that should be used to write the output file.
     * 
     * @param file The file where the information will be written.
     * @return 
     * @throws IOException Exception launched in case of something goes wrong during the file
     * writing
     */
    //INSPECT - Lais Modificação do método para transformar um arquivo xml em html, inclusão de information test case para geração da traceability matrix
    public File writeTestCaseDataInFile(File file) throws IOException
    {
        super.writeTestCaseDataInFile(file);
        
        TestSuiteXMLGenerator generator = new TestSuiteXMLGenerator();
        File xmlFile = generator.generateXMLFile(this.testCases, file.getName(), this.informationTestCase);

        File xsltFile = new File("resources/HTMLTransform.xsl");

        if (!xmlFile.isFile())
        {
            throw new IllegalAccessError("O arquivo " + xmlFile.getName()
                    + " não existente no local especificado. " + xmlFile.getAbsolutePath());
        }
        else if (!xsltFile.isFile())
        {
            throw new IllegalAccessError("O arquivo " + xsltFile.getName()
                    + " não existente no local especificado. " + xsltFile.getAbsolutePath());
        }
        
        
        FileInputStream fileInputStream = new FileInputStream(xmlFile);

        Source xmlSource = new StreamSource(fileInputStream);
        
        Source xsltSource = new StreamSource(xsltFile);

        TransformerFactory transFact = TransformerFactory.newInstance();

        File fileTemp = new File(xmlFile.getAbsolutePath() + ".temp");
        
        FileOutputStream fileOutputStream = new FileOutputStream(fileTemp);

        try
        {
            Templates cachedXSLT = transFact.newTemplates(xsltSource);
            Transformer trans = cachedXSLT.newTransformer();
            trans = transFact.newTransformer(xsltSource);
            trans.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.METHOD, "html");
            trans.transform(xmlSource, new StreamResult(fileOutputStream));

            fileOutputStream.close();

            xmlFile.delete();
            file.delete();
            fileTemp.renameTo(file);
        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
        
        return file;
    }

    /**
     * Gets the file extension of the plugin output format
     * 
     * @return the file extension
     */
    public String getExtensionFile()
    {
        return ".html";
    }

}
