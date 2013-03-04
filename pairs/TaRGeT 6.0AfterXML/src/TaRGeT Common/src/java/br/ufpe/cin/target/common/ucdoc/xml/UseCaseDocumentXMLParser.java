/*
 * @(#)UseCaseDocumentXMLParser.java
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
 * wdt022   Nov 25, 2006    LIBkk11577   Initial creation.
 * wdt022   Jan 10, 2007    LIBkk11577   Javadoc modifications.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   Apr 26, 2007    LIBmm09879   Changing the message that replaces an exception in retrieveRequirements() method.
 * dhq348   Aug 17, 2007    LIBmm93130   Modifications according to CR.
 * dhq348   Oct 11, 2007    LIBnn34008   Modifications according to CR.
 * dhq348   Jan 16, 2008    LIBnn34008   Rework after inspection LX229627.
 * dhq348   Nov 26, 2007    LIBoo10574   The class now suports the processing of the setup elements.
 * dhq348   Jan 23, 2008    LIBoo10574   Rework after inspection LX229632.
 * tnd783	Aug 26, 2008	LIBqq51204   Changes in method retrieveRequirements
 * jrm687   Dec 18, 2008    LIBqq51204	 Modifications to add document revision history.
 * lmn3		Jul 29, 2009				 Modifications on method retrieveRequirements.
 */
package br.ufpe.cin.target.common.ucdoc.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.ufpe.cin.target.common.exceptions.UseCaseDocumentXMLException;
import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.StringsUtil;

/**
 * <pre> 
 * CLASS:
 * Retrieves Java objects from a XML file that represents a use case document.
 *
 * RESPONSIBILITIES:
 * -) Opens a XML file passed as parameter.
 * -) Parses the XML using DOM API.
 * -) Mounts a Java objects structure representing the use case document.
 * -) Launches exception in case of invalid XML structure.  
 * 
 * USAGE:
 *  UseCaseDocumentXMLParser xmlParser = new UseCaseDocumentXMLParser(fileName)
 *  UseCaseDocument useCaseDocument = xmlParser.parseXMLFile();
 * </pre>
 */
public class UseCaseDocumentXMLParser
{
	/** The feature xml tag */
    private static final String ELEMENT_FEATURE = "feature";

    /** The feature id xml tag */
    private static final String ELEMENT_FEATURE_ID = "featureId";

    /** The feature name xml tag */
    private static final String ELEMENT_FEATURE_NAME = "name";

    /** The use case xml tag */
    private static final String ELEMENT_USE_CASE = "useCase";

    /** The use case id xml tag */
    private static final String ELEMENT_USE_CASE_ID = "id";

    /** The use case name xml tag */
    private static final String ELEMENT_USE_CASE_NAME = "name";

    /** The use case description xml tag */
    private static final String ELEMENT_USE_CASE_DESCRIPTION = "description";

    /** The use case setup xml tag */
    private static final String ELEMENT_USE_CASE_SETUP = "setup";

    /** The flow xml tag */
    private static final String ELEMENT_FLOW = "flow";

    /** The flow description xml tag */
    private static final String ELEMENT_FLOW_DESCRIPTION = "description";

    /** The from steps xml tag */
    private static final String ELEMENT_FLOW_FROM_STEPS = "fromSteps";

    /** The to steps xml tag */
    private static final String ELEMENT_FLOW_TO_STEPS = "toSteps";

    /** The flow step xml tag */
    private static final String ELEMENT_FLOW_STEP = "step";

    /** The flow step id xml tag */
    private static final String ELEMENT_FLOW_STEP_ID = "stepId";

    /** The flow step action xml tag */
    private static final String ELEMENT_FLOW_STEP_ACTION = "action";

    /** The flow step condition xml tag */
    private static final String ELEMENT_FLOW_STEP_CONDITION = "condition";

    /** The flow step response xml tag */
    private static final String ELEMENT_FLOW_STEP_RESPONSE = "response";

    /** The schema language property */
    protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    /** The schema language definition */
    protected static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    /** The schema source property */
    protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    /** The schema path */
    private static final String SCHEMA_SOURCE = FileUtil.getUserDirectory()
            + FileUtil.getSeparator() + "resources" + FileUtil.getSeparator()
            + "schema_use_cases-user_view.xsd";

    /** The XML DOM tree of the use case document. */
    protected Document xmlUseCaseDocument;
    
    /** The XML file. */
    private File xmlFile;

    /**
     * Default Constructor.
     */
    public UseCaseDocumentXMLParser()
    {
    }

    /**
     * Constructs a <code>UseCaseDocumentXMLParser</code> object. It sets the DOM document and
     * keeps the XML file.
     * 
     * @param xmlFile The XML file.
     * @throws UseCaseDocumentXMLException It is launched in case of any error on the configuration
     * of <code>DocumentBuilderFactory</code> or during the XML parsing.
     */
    public UseCaseDocumentXMLParser(File xmlFile) throws UseCaseDocumentXMLException
    {
        XMLErrorHandler handler = new XMLErrorHandler();
        try
        {
            DocumentBuilder docBuilder = this.getConfiguredDocumentBuilder();
            docBuilder.setErrorHandler(handler);
            this.xmlUseCaseDocument = docBuilder.parse(xmlFile);
            this.xmlFile = xmlFile;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new UseCaseDocumentXMLException(e);
        }
        this.handleErrors(handler);
    }

    /**
     * This method returns the <code>DocumentBuilder</code> instance, configured to the document
     * schema.
     * 
     * @return The configured DocumentBuilder
     * @throws ParserConfigurationException This exception is thrown in case of any error during the
     * configuration.
     */
    private DocumentBuilder getConfiguredDocumentBuilder() throws ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        File file = new File(SCHEMA_SOURCE);
        factory.setAttribute(JAXP_SCHEMA_SOURCE, file);
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder;
    }

    /**
     * This method is used together with the <code>XMLErrorHandler</code> class. It is responsible
     * for launching the <code>UseCaseDocumentXMLException</code> exceptions.
     * 
     * @param handler The <code>XMLErrorHandler</code> that is responsible for transforming the
     * JAXP parser exceptions into <code>UseCaseDocumentXMLException</code> exceptions.
     * @throws UseCaseDocumentXMLException The <code>UseCaseDocumentXMLException</code>
     * exceptions.
     */
    protected void handleErrors(XMLErrorHandler handler) throws UseCaseDocumentXMLException
    {
        /* First check if there were fatal errors */
        if (handler.isFatalError())
        {
            throw handler.getFatalErrors().iterator().next();
        }

        /* Second check if there were errors */
        if (handler.isError())
        {
            throw handler.getErrors().iterator().next();
        }

        /* Third check if there were warnings */
        if (handler.isWarning())
        {
            throw handler.getWarnings().iterator().next();
        }
    }

    /**
     * It reads a XML DOM tree and generates a <code>UseCaseDocument</code> instance.
     * 
     * @return A <code>UseCaseDocument</code> instance.
     */
    public UseCaseDocument buildUseCaseDocument()
    {
        UseCaseDocument phoneDoc = null;

        Element root = this.xmlUseCaseDocument.getDocumentElement();

        List<Feature> features = this.buildFeatureList(root);

        phoneDoc = new UseCaseDocument(this.xmlFile.getAbsolutePath(), this.xmlFile.lastModified(), features);

        return phoneDoc;
    }
    
    /**
     * Retrieves a list of <code>Feature</code> objects from the root element.
     * 
     * @param root The root element of the XML document.
     * @return A list of <code>Feature</code> objects
     */
    private List<Feature> buildFeatureList(Element root)
    {

        List<Feature> featureList = new ArrayList<Feature>();

        NodeList featureElements = root.getElementsByTagName(ELEMENT_FEATURE);

        // parses each feature XML element
        for (int i = 0; i < featureElements.getLength(); i++)
        {
            Element featureElement = (Element) featureElements.item(i);
            Feature feature = this.buildFeature(featureElement);
            featureList.add(feature);
        }

        return featureList;
    }

    /**
     * Receives as parameter a XML element that represents a feature and returns the
     * <code>Feature</code> object.
     * 
     * @param featureElement The XML element.
     * @return The <code>Feature</code> object.
     */
    private Feature buildFeature(Element featureElement)
    {
        Feature feature = null;

        List<UseCase> useCaseList = new ArrayList<UseCase>();

        String featureId = this.getSingleElement(featureElement, ELEMENT_FEATURE_ID)
        		.getTextContent().trim();
        String featureName = this.getSingleElement(featureElement, ELEMENT_FEATURE_NAME)
                .getTextContent().trim();

        NodeList useCaseElements = featureElement.getElementsByTagName(ELEMENT_USE_CASE);

        // parses each use case XML element
        for (int i = 0; i < useCaseElements.getLength(); i++)
        {
            Element useCaseElement = (Element) useCaseElements.item(i);
            UseCase useCase = this.buildUseCase(useCaseElement, featureId);
            useCaseList.add(useCase);
        }

        feature = new Feature(featureId, featureName, useCaseList);

        return feature;
    }

    /**
     * It parses a use case XML element and returns the <code>UseCase</code> object.
     * 
     * @param useCaseElement The XML feature
     * @param featureId The id of the feature that contains the use case
     * @return The built use case
     */
    private UseCase buildUseCase(Element useCaseElement, String featureId)
    {
        UseCase useCase = null;

        List<Flow> flowList = new ArrayList<Flow>();

        String useCaseId = this.getSingleElement(useCaseElement, ELEMENT_USE_CASE_ID)
                .getTextContent().trim();
        String useCaseName = this.getSingleElement(useCaseElement, ELEMENT_USE_CASE_NAME)
                .getTextContent().trim();
        String useCaseDescription = this.getSingleElement(useCaseElement,
                ELEMENT_USE_CASE_DESCRIPTION).getTextContent().trim();

        String useCaseSetup = this.getSingleElement(useCaseElement, ELEMENT_USE_CASE_SETUP)
                .getTextContent().trim();

        NodeList flowElements = useCaseElement.getElementsByTagName(ELEMENT_FLOW);
        // parses each flow XML element
        for (int i = 0; i < flowElements.getLength(); i++)
        {
            Element flowElement = (Element) flowElements.item(i);
            Flow flow = this.buildFlow(flowElement, useCaseId, featureId);
            flowList.add(flow);
        }

        useCase = new UseCase(useCaseId, useCaseName, useCaseDescription, flowList, useCaseSetup);

        return useCase;
    }

    /**
     * Receives a XML element representing a use case flow and returns a <code>Flow</code> object.
     * 
     * @param flowElement The XML element.
     * @param useCaseId The id of the use case that contains the flow.
     * @param featureId The id of the feature that contains the flow.
     * @return The Flow object
     */
    private Flow buildFlow(Element flowElement, String useCaseId, String featureId)
    {
        Flow flow = null;

        List<FlowStep> flowStepList = new ArrayList<FlowStep>();

        String flowDescription = this.getSingleElement(flowElement, ELEMENT_FLOW_DESCRIPTION)
                .getTextContent().trim();
        String toSteps = this.getSingleElement(flowElement, ELEMENT_FLOW_TO_STEPS).getTextContent()
                .trim();
        String fromSteps = this.getSingleElement(flowElement, ELEMENT_FLOW_FROM_STEPS)
                .getTextContent().trim();

        NodeList stepElements = flowElement.getElementsByTagName(ELEMENT_FLOW_STEP);

        // parses each step XML element
        for (int i = 0; i < stepElements.getLength(); i++)
        {
            Element stepElement = (Element) stepElements.item(i);
            FlowStep flowStep = this.buildFlowStep(stepElement, useCaseId, featureId);
            flowStepList.add(flowStep);
        }

        flow = new Flow(flowStepList, this.getStepSet(fromSteps, useCaseId, featureId), this
                .getStepSet(toSteps, useCaseId, featureId), flowDescription);

        return flow;
    }

    /**
     * Receives as input a string containing a list of step ids separated by comma, and returns a
     * <code>Set</code> object containing all split step ids.
     * 
     * @param stepList The step list in String format
     * @param useCaseId The id of the use case that contains the flow
     * @param featureId The id of the feature that contains the flow
     * @return The set of strings, each string representing a step id.
     */
    private Set<StepId> getStepSet(String stepList, String useCaseId, String featureId)
    {

        Set<StepId> result = new LinkedHashSet<StepId>();

        String[] stepArray = stepList.split(",");

        for (String step : stepArray)
        {
            if (step.trim().equals("START"))
            {
                result.add(StepId.START);
            }
            else if (step.trim().equals("END"))
            {
                result.add(StepId.END);
            }
            else if (!step.trim().equals(""))
            {
                step = step.trim();
                StepId stepId = null;

                String[] array = step.split("#");
                if (array.length == 1)
                {
                    stepId = new StepId(featureId, useCaseId, array[0].trim());
                }
                else if (array.length == 2)
                {
                    stepId = new StepId(featureId, array[0].trim(), array[1].trim());
                }
                else if (array.length == 3)
                {
                    stepId = new StepId(array[0].trim(), array[1].trim(), array[2].trim());
                } // TODO Launch exception in case array.length > 3 or == 0
                result.add(stepId);
            }
        }

        return result;
    }

    /**
     * Receives as input a XML element representing a flow step and returns a <code>FlowStep</code>
     * object.
     * 
     * @param flowStepElement The XML element
     * @param useCaseId The id of the use case that contains the flow step
     * @param featureId The id of the feature that contains the flow step
     * @return The <code>FlowStep</code> object
     */
    private FlowStep buildFlowStep(Element flowStepElement, String useCaseId, String featureId)
    {
        FlowStep flowStep = null;

        String flowStepId = this.getSingleElement(flowStepElement, ELEMENT_FLOW_STEP_ID)
                .getTextContent().trim();
        String flowStepAction = this.getSingleElement(flowStepElement, ELEMENT_FLOW_STEP_ACTION)
                .getTextContent().trim();
        String flowStepCondition = this.getSingleElement(flowStepElement,
                ELEMENT_FLOW_STEP_CONDITION).getTextContent().trim();
        
        String flowStepResponse = this
                .getSingleElement(flowStepElement, ELEMENT_FLOW_STEP_RESPONSE).getTextContent()
                .trim();

        Set<String> allRequirements = new LinkedHashSet<String>();
        allRequirements.addAll(this.retrieveRequirements(flowStepAction));
        allRequirements.addAll(this.retrieveRequirements(flowStepCondition));
        allRequirements.addAll(this.retrieveRequirements(flowStepResponse));

        StepId id = new StepId(featureId, useCaseId, flowStepId);

        flowStep = new FlowStep(id, flowStepAction, flowStepCondition, flowStepResponse, allRequirements);

        return flowStep;
    }

    //INSPECT mcms The body of this method was changed to allow its access from class SaveAction
    // at XML Editor plug-in. Now, it calls the method implemented in StringsUtil.
    /**
     * Receives a step field (action, condition or response) and extracts the related requirements.
     * A single requirement in the specifications may be any set of characters except brackets and
     * comma. A list of single requirements starts with a "[" and may contain one ore more single
     * requirements, separated by comma, and finishes with a "]". For instance, the processing of
     * the step field "Test the step field [req1, req2]" would return a set containing the strings
     * "req1" and "req2".
     * 
     * @param stepField The step field
     * @return A set containing all related requirements.
     * @throws UseCaseDocumentXMLException 
     */
    
    private Set<String> retrieveRequirements(String stepField) 
    {
              return StringsUtil.retrieveRequirements(stepField);
    }

    /**
     * Auxiliary method that returns the first XML element which has a specific name.
     * 
     * @param element The super-element that must contain the required XML element
     * @param elementName The name of required element.
     * @return The required element.
     */
    private Element getSingleElement(Element element, String elementName)
    {
        return ((Element) element.getElementsByTagName(elementName).item(0));
    }
}
