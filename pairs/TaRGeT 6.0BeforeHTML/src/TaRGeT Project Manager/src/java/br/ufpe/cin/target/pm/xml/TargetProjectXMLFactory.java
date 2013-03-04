/*
 * @(#)TargetProjectXMLFactory.java
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
 * wra050   Aug 03, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * dhq348   Mar 14, 2007    LIBll66163   Changes according to LIBll66163.
 * dhq348   May 09, 2007    LIBmm23662   Changes according to LIBmm23662.
 */
package br.ufpe.cin.target.pm.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.controller.TargetProject;


/**
 * <pre>
 * CLASS:
 * This class is responsible for dealing with TargetProject 
 * representation in XML. The xml format looks like:
 * 
 *      <target-project name="project-name"/>           
 *
 * RESPONSIBILITIES:
 * Load a TargetProject from xml file
 * Save a TargetProject into xml file  
 *  
 * </pre>
 */
public class TargetProjectXMLFactory
{
    /**
     * Loads a TargetProject from an XML File.
     * 
     * @param file Absolute path from target xml file.
     * @return The target project loaded from xml.
     * @throws TargetException Thrown when the XML file does not exist or is not well formed.
     */
    public static TargetProject loadProject(String file) throws TargetException
    {
        TargetProject project = null;

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(file));
            String projectName = doc.getDocumentElement().getAttribute("name");
            String nextStep = doc.getDocumentElement().getAttribute("nextID");
            
            int nextStepInt = 1;
            if(!nextStep.equals(""))
            {
                nextStepInt = Integer.parseInt(nextStep);
            }
            
            String rootDir = FileUtil.getFilePath(file);
            project = new TargetProject(projectName, rootDir, nextStepInt);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new TargetException("The file " + file + " is not a valid TaRGeT project.");
        }

        return project;
    }

    /**
     * Saves a TargetProject from XML File.
     * 
     * @param project The project that must be saved.
     * @param file The xml file that handle the project information.
     * @throws TargetException Thrown if any problem with file creation occurs.
     */
    public static void saveProject(TargetProject project, String file) throws TargetException
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation impl = builder.getDOMImplementation();

            Document doc = impl.createDocument(null, null, null);
            Element e1 = doc.createElement("target-project");
            e1.setAttribute("name", project.getName());
            e1.setAttribute("nextID", String.valueOf(project.getNextAvailableTestCaseID()));
            doc.appendChild(e1);

            DOMSource domSource = new DOMSource(doc);
            /* the XML will be written in stringWriter */
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.transform(domSource, streamResult);

            FileOutputStream output = new FileOutputStream(new File(file));
            output.write(stringWriter.toString().getBytes());
            output.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new TargetException("Problem while saving TaRGeT project '" + project.getName()
                    + "'.");
        }

    }
}
