//XMLFileFormatter,XMLExtractor

/*
 * @(#)TestLinkAspect.aj
 *
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * fsf2      07/08/2009                  Initial creation.
 * lmn3      13/08/2009                  Changes on the extraction algorithm. 
 * lmn3      21/01/2009                  Inclusion of transformRequirementsFile() method to generate the requirements XML file.
 */
package testlink;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.htmlparser.jericho.Segment;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties;

/**
 * << high level description of the aspect >>
 * 
 * <pre>
 * CLASS:
 * << Short description of the Utility Function or Toolkit >>
 * [ Template guidelines:
 *     When the class is an API UF, describe only WHAT the UF shall
 *     do, when it is an implementation UF, describe HOW the UF will
 *     do what it should, and if it is a tailoring to an already existing
 *     UF, describe what are the differences that made the UF needed.
 * ]
 * RESPONSIBILITIES:
 * <<High level list of things that the class does>>
 * 1) responsibility
 * 2) ...
 * <Example:
 * RESPONSIBILITIES:
 * 1) Provide interface to access navigation related UFs
 * 2) Guarantee easy access to the UFs
 * 3) Provide functional implementation to the ScrollTo UF for P2K.
 * >
 *
 * COLABORATORS:
 * << List of descriptions of relationships with other classes, i.e. uses,
 * contains, creates, calls... >>
 * 1) class relationship
 * 2) ...
 *
 * USAGE:
 * << how to use this class - for UFs show how to use the related 
 * toolkit call, for toolkits show how a test case should access the
 * functions >>
 */
//INSPECT Lais. Changed the extraction algorithm.
public aspect TestLinkAspect
{
    //TODO extract text messages
    
    private boolean generateRequirements = true;
    
    private static final String IGNORED_TAGS_FILE = FileUtil.getUserDirectory() + FileUtil.getSeparator()
    + "resources" + FileUtil.getSeparator() + "htmlOutputIgnoredTags.txt";
    
    /** The location of the requirements directory */
    private static final String REQUIREMENT_FILE_SOURCE = ProjectManagerController.getInstance()
            .getCurrentProject().getRootDir()
            + FileUtil.getSeparator() + "requirements";
    
    pointcut generateXML(File file) :
        execution(File br.ufpe.cin.target.xmloutput.xml.XMLFileFormatter.writeTestCaseDataInFile(File)) && args(file);
    
    pointcut extractXML(File file) :
        execution(List<TextualTestCase> br.ufpe.cin.target.xmloutput.XMLExtractor.extractTestSuite(File)) && args(file);
    
    List<TextualTestCase> around(File file) throws FileNotFoundException, IOException : extractXML(file)
    {
        return this.extractTestSuite(file);
    }

    File around(File file) throws IOException : generateXML(file)
    {
        File fileNew = proceed(file);
    	
    	try
        {
    	    this.generateRequirements = MessageDialog.openQuestion(null, Properties.getProperty("generate_requirements_document"),  Properties.getProperty("generate_requirements_question"));
    	    
    	    if(generateRequirements)
    	    {
    	        this.transformRequirementsFile(fileNew);
    	    }
    	    
    	    File xmlFile = this.transformTestSuiteFile(fileNew);
    	    
            xmlFile = this.updateRequirementsSpecificationTitle(xmlFile);

            

            return xmlFile;
        }
        catch (IOException e)
        {
            throw new org.aspectj.lang.SoftException(e);
        }        
    }

  
    /**
     * 
     * Updates the test suite xml file with the requirements specification document title.
     * @param xmlFile the test suite file
     * @return the updated test suite file;
     */
    private File updateRequirementsSpecificationTitle(File xmlFile)
    {   
        try
        {
            String requirementsSpecTitle = TestCaseProperties.getInstance().getRequirementsSpecificationTitle();
                        
            if (this.generateRequirements)
            {
                InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(),
                        Properties.getProperty("testlink_requirements_specification"),
                        Properties.getProperty("type_the_name_of_the_requirements_specification_document"),
                        requirementsSpecTitle, null);

               

                if (dlg.open() == org.eclipse.jface.window.Window.OK)
                {
                    requirementsSpecTitle = dlg.getValue().trim();
                    TestCaseProperties.getInstance().setRequirementsSpecificationTitle(
                            requirementsSpecTitle);

                }
            }
            
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document testSuiteDocument = docBuilder.parse(xmlFile);

            Element root = testSuiteDocument.getDocumentElement();
            
            NodeList testCases = root.getElementsByTagName("testcase");

            for (int i = 0; i < testCases.getLength(); i++)
            {

                Element testCase = (Element) testCases.item(i);

                Element requirements = (Element) testCase.getElementsByTagName("requirements").item(0);
                NodeList requirementsList = requirements.getElementsByTagName("requirement");

                for (int j = 0; j < requirementsList.getLength(); j++)
                {
                    Element requirement = (Element) requirementsList.item(j);
                    
                    Element reqSpecTitle = (Element) requirement.getElementsByTagName("req_spec_title").item(0);
                    reqSpecTitle.setTextContent(requirementsSpecTitle);
                    
                }
            }
            
            Source source = new DOMSource(testSuiteDocument); // Prepare the output file 
            
            File file = new File(xmlFile.getAbsolutePath()); 
            FileOutputStream fileOutputStream = new FileOutputStream(file);            
            Result result = new StreamResult(fileOutputStream); 
            
            // Write the DOM document to the file 
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            
#if($qualiti)
			xformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
#else
			xformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
#end
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            xformer.setOutputProperty(OutputKeys.METHOD, "xml");
            xformer.transform(source, result); 
            
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerFactoryConfigurationError e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        
        return xmlFile;
    }
    
    
    /**
     * Transforms the generated XML file to the TestLinkFormat.
     * 
     * @param xmlFile
     * @throws IOException
     */
    private File transformTestSuiteFile(File xmlFile) throws IOException
    {
        
        File xsltFile = new File("resources/TestLinkTransform.xsl");

        if (!xmlFile.isFile())
        {
            throw new IllegalAccessError(Properties.getProperty("the_file") + " " + xmlFile.getName()
                    + " " +Properties.getProperty("does_not_exist_in_the_specified_location") + " " + xmlFile.getAbsolutePath());
        }
        else if (!xsltFile.isFile())
        {
            throw new IllegalAccessError(Properties.getProperty("the_file") + " " + xsltFile.getName()
                    + " " +Properties.getProperty("does_not_exist_in_the_specified_location") + " " + xsltFile.getAbsolutePath());
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
#if($qualiti)
	    	trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
#else
			trans.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
#end
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.transform(xmlSource, new StreamResult(fileOutputStream));

            fileOutputStream.close();

            xmlFile.delete();
            fileTemp.renameTo(xmlFile);
            
        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
        return xmlFile;
    }
    
    /**
     * 
     * Generates a XML file to be imported in TestLink tool.
     * @param xmlFile the test suit XML file to be transformed.
     * @throws IOException
     */
    private void transformRequirementsFile(File xmlFile) throws IOException
    {
        File xsltFile = new File("resources/testLinkRequirementsTransform.xsl");

        if (!xmlFile.isFile())
        {
            throw new IllegalAccessError(Properties.getProperty("the_file") + " " + xmlFile.getName()
                    + " " +Properties.getProperty("does_not_exist_in_the_specified_location") + " " + xmlFile.getAbsolutePath());
        }
        else if (!xsltFile.isFile())
        {
            throw new IllegalAccessError(Properties.getProperty("the_file") + " " + xsltFile.getName()
                    + " " +Properties.getProperty("does_not_exist_in_the_specified_location") + " " + xsltFile.getAbsolutePath());
        }
        
        File outDir = new File(REQUIREMENT_FILE_SOURCE);
        
        if (!outDir.exists())
        {
            outDir.mkdirs();
        }
        
        
        FileInputStream fileInputStream = new FileInputStream(xmlFile);

        Source xmlSource = new StreamSource(fileInputStream);
        
        Source xsltSource = new StreamSource(xsltFile);

        TransformerFactory transFact = TransformerFactory.newInstance();

        File fileTemp = new File(outDir + FileUtil.getSeparator() + "Test_Link_Requirements.xml");

        FileOutputStream fileOutputStream = new FileOutputStream(fileTemp);

        try
        {
            Templates cachedXSLT = transFact.newTemplates(xsltSource);
            Transformer trans = cachedXSLT.newTransformer();
            trans = transFact.newTransformer(xsltSource);
            
#if($qualiti)
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
#else
    		trans.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
#end
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.transform(xmlSource, new StreamResult(fileOutputStream));

            fileOutputStream.close();
            fileInputStream.close();
            
            MessageDialog.openInformation(null, Properties.getProperty("requirements_document_summary"), Properties.getProperty("testlink_requirements_document_saved_in") + " " + fileTemp.getAbsolutePath());
        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method extracts test cases from a given Excel file.
     * 
     * @param file The file to be read
     * @param headers
     * @return The list of TextualTestCases extracted from the file
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File file) throws FileNotFoundException,
    IOException
    {
        try
        {
            List<TextualTestCase> testCases = new ArrayList<TextualTestCase>();
            
            List<String> ignoredTags = this.getIgnoredTags();
            StringBuffer stringBuffer = new StringBuffer();
            
            //reading the content of the xml file with a BufferedReader to avoid problems with charset enconding
            //when creating the DOM Document.
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line = null;
            StringBuilder contents = new StringBuilder();

            while ((line = input.readLine()) != null)
            {
                contents.append(line);
            }
            
            //building the xml document
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(new InputSource(new java.io.StringReader(contents.toString())));
            
            NodeList list = doc.getElementsByTagName("testcase");
            
            for (int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                NodeList children = node.getChildNodes();
                
                String xmlText = "";
                
                //extracting relevant html fields
                for (int l = 0; l < children.getLength();  l++){
                    //retrieving the content of tags <summary>, <steps> and <expectedresults>
                    xmlText += children.item(l).getTextContent();
                
                }
                
                //adding an identification to help the parsing of test cases
                xmlText = xmlText.replaceAll("<br />", "@@@");
                
                //initializing the html parser
                net.htmlparser.jericho.Source source = new net.htmlparser.jericho.Source(xmlText);
                //turning off log messages
                source.setLogger(null);
                
                List<Segment> ignoredSegments = new ArrayList<Segment>();
                
                //checking if the text to be parsed contains tags that should be ignored
                for (String ignoredTag : ignoredTags)
                {
                    if (xmlText.contains(ignoredTag))
                    {
                        for (int j = 0; j < xmlText.length() && xmlText.indexOf(ignoredTag, j) >= 0; j++)
                        {
                            int indexIgnoredTag = xmlText.indexOf(ignoredTag, j);
                            
                            Segment ignoredSegment = new Segment(source, indexIgnoredTag, indexIgnoredTag
                                    + ignoredTag.length());
                            
                            //indicates to the parser which tags should be ignored
                            ignoredSegments.add(ignoredSegment);

                            j = indexIgnoredTag + ignoredTag.length();
                        }
                    }
                }
                
                //ignoring tags when parsing the html code
                source.ignoreWhenParsing(ignoredSegments);
                
                //removing html code
                stringBuffer.append(source.getTextExtractor().toString().trim());
                
            }
            
            input.close();
            
            String htmlText = stringBuffer.toString();
            

            // separating the test cases
            String[] testCasesText = htmlText.split("#testCase#");

            for (int i = 1; i < testCasesText.length; i++)
            {
                testCasesText[i] = testCasesText[i].replaceAll("Test Case ID:", "");
                
                //separating the test cases in lines
                String[] testCaseLines = testCasesText[i].split("@@@");

                String tcIdHeader = testCaseLines[0];
                String regressionLevel = "";
                String executionType = "";
                String description = "";
                String objective = "";
                String useCaseReferences = "";
                String requirements = "";
                String setups = "";
                String initialConditions = "";
                String note = "";
                String finalConditions = "";
                String cleanup = "";
                String status = "";
                String featureId = "";
                String useCaseId = "";
                String tcId = "";
                List<TextualStep> steps = new ArrayList<TextualStep>();
                List<String> responses = new ArrayList<String>();

                // retrieving test case fields
                for (int l = 1; l < testCaseLines.length; l++)
                {
                    if (testCaseLines[l].startsWith("#id#"))
                    {
                        tcId = testCaseLines[l].replaceAll("#id#", "").trim();
                    }

                    if (testCaseLines[l].contains("#regressionLevel#"))
                    {
                        regressionLevel = this.extractField(testCaseLines[l], "#regressionLevel#");
                    }
                    else if (testCaseLines[l].contains("#executionType#"))
                    {
                        executionType = this.extractField(testCaseLines[l], "#executionType#");
                    }
                    else if (testCaseLines[l].contains("#description#"))
                    {
                        description = this.extractField(testCaseLines[l], "#description#");
                    }
                    else if (testCaseLines[l].contains("#objective#"))
                    {
                        objective = this.extractField(testCaseLines[l], "#objective#");
                    }
                    else if (testCaseLines[l].contains("#useCaseReferences#"))
                    {
                        useCaseReferences = this.extractField(testCaseLines[l],
                        "#useCaseReferences#");

                        String[] temp = useCaseReferences.split("#");

                        if (temp.length > 0)
                        {
                            featureId = temp[0];
                            useCaseId = temp[1];
                        }
                    }
                    else if (testCaseLines[l].contains("#requirements#"))
                    {
                        requirements = this.extractField(testCaseLines[l], "#requirements#");
                    }
                    else if (testCaseLines[l].contains("#setups#"))
                    {
                        setups = this.extractField(testCaseLines[l], "#setups#");
                    }
                    else if (testCaseLines[l].contains("#initialConditions#"))
                    {
                        initialConditions = this.extractField(testCaseLines[l],
                        "#initialConditions#");
                    }
                    else if (testCaseLines[l].contains("#note#"))
                    {
                        note = this.extractField(testCaseLines[l], "#note#");
                    }
                    else if (testCaseLines[l].contains("#finalConditions#"))
                    {
                        finalConditions = this.extractField(testCaseLines[l], "#finalConditions#");
                    }
                    else if (testCaseLines[l].contains("#cleanup#"))
                    {
                        cleanup = this.extractField(testCaseLines[l], "#cleanup#");
                    }
                    else if (testCaseLines[l].contains("#stepId#"))
                    {
                        int index = testCaseLines[l].indexOf("#action#");

                        String stepId = testCaseLines[l].substring(0, index);
                        stepId = stepId.replaceAll("#stepId#", "").trim();

                        String action = testCaseLines[l]
                                                      .substring(index, testCaseLines[l].length());
                        action = action.replaceAll("#action#", "").trim();

                        StepId id = new StepId(featureId, useCaseId, stepId);
                        TextualStep step = new TextualStep(action, "", id);
                        steps.add(step);
                    }
                    else if (testCaseLines[l].contains("#response#"))
                    {
                        String response = this.extractField(testCaseLines[l], "#response#");
                        responses.add(response);
                    }

                }

                for (int j = 0; j < responses.size(); j++)
                {
                    if (j < steps.size())
                    {
                        steps.get(j).setResponse(responses.get(j));
                    }
                }

                // mounting the test case
                TextualTestCase tc = new TextualTestCase(Integer.parseInt(tcId.trim()), tcIdHeader, steps,
                        executionType, regressionLevel, description, objective, requirements,
                        setups, initialConditions, note, finalConditions, cleanup,
                        useCaseReferences, status, featureId);

                testCases.add(tc);
            }
            return testCases;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * Extracts a test case field.
     * @param testCaseLine the text to be processed.
     * @param fieldName the name of the test case field.
     * @return the contento of the field.
     */
    private String extractField(String testCaseLine, String fieldName)
    {
        int index = testCaseLine.indexOf(fieldName);
        String field = testCaseLine.substring(index, testCaseLine.length());
        field = field.replaceAll(fieldName, "").trim();

        return field;
    }
    
    /**
     * Returns a list containing html tags defined by the user that should be ignored.
     * These tags are retrieved from a resource file.
     * @return a list containing html tags that will be ignoring by the parser.
     * @throws IOException
     */
    private List<String> getIgnoredTags() throws IOException{
        List<String> ignoredTags = new ArrayList<String>();
        
        BufferedReader in;
        in = new BufferedReader(new FileReader(new File(TestLinkAspect.IGNORED_TAGS_FILE)));
        String line = in.readLine();
        
        while (line != null)
        {
            if(!line.equals("")){
                ignoredTags.add(line);
            }
            
            line = in.readLine();
        }
        
        return ignoredTags;
    }
        
}
