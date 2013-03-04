/*
 * @(#)XMLFileGenerator.java
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
 * fsf2      08/10/2009                  Initial creation.
 */
package br.ufpe.cin.target.common.ucdoc.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;

/**
 * This class is responsible to create a XML file representing the use case document. 
 */
public class XMLFileGenerator
{
    private UseCaseDocument useCaseDocument;

    private Document xmlDocument;

    private Element itemFeature, itemUseCase, itemFlow, itemStep;

    private Feature feature;

    /**
     * Constructor.
     * 
     * @param useCaseDocument2
     */
    public XMLFileGenerator(UseCaseDocument useCaseDocument)
    {
        this.useCaseDocument = useCaseDocument;
        
        this.xmlDocument = this.createEmptyDocument();
    }
    
    /**
     * 
     * Creates an empty instance of a DOM document.
     * @return an empty instance of a DOM document
     */
    private Document createEmptyDocument()
    {
        Document result = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            
            // Create a new empty document
            result = builder.newDocument();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * Creates the document XML content.
     */
    public void createXMLSchema()
    {
        // Phone
        Element root = this.xmlDocument.createElement("phone");
        root.setAttribute("xmlns", "user-view.target.v20071129");

        // Feature
        Feature feature = null;
        List<Feature> featureList = this.useCaseDocument.getFeatures();
        Iterator<Feature> it = featureList.iterator();

        while (it.hasNext())
        {
            feature = it.next();
            this.generateTagFeature(feature);
            root.appendChild(this.itemFeature);

            List<UseCase> ucList = feature.getUseCases();

            Iterator<UseCase> itUC = ucList.iterator();

            while (itUC.hasNext())
            {
                UseCase uc = itUC.next();

                this.generateTagUseCase(uc);
                this.itemFeature.appendChild(this.itemUseCase);

                List<Flow> flowList = uc.getFlows();
                Iterator<Flow> itFlow = flowList.iterator();

                while (itFlow.hasNext())
                {
                    Flow flow = itFlow.next();

                    this.generateTagFlow(flow, uc.getId(), feature.getId());
                    this.itemUseCase.appendChild(this.itemFlow);

                    List<FlowStep> stepsList = flow.getSteps();
                    Iterator<FlowStep> itSteps = stepsList.iterator();

                    while (itSteps.hasNext())
                    {
                        FlowStep step = itSteps.next();

                        this.generateTagStep(step);
                        this.itemFlow.appendChild(this.itemStep);
                    }
                }
            }
        }

        this.xmlDocument.appendChild(root);
        
        this.feature = feature;
    }
    
    /**
     * 
     * Generates the <feature> tag.
     * @param feature the feature object
     */
    private void generateTagFeature(Feature feature)
    {
        this.itemFeature = xmlDocument.createElement("feature");
        Element featureId = xmlDocument.createElement("featureId");
        featureId.appendChild(xmlDocument.createTextNode(feature.getId()));
        Element featureName = xmlDocument.createElement("name");
        featureName.appendChild(xmlDocument.createTextNode(feature.getName()));

        this.itemFeature.appendChild(featureId);
        this.itemFeature.appendChild(featureName);
    }
    
    /**
     * 
     * Generates the <useCase> tag.
     * @param uc the use case object
     */
    private void generateTagUseCase(UseCase uc)
    {
        this.itemUseCase = xmlDocument.createElement("useCase");
        Element ucId = xmlDocument.createElement("id");
        ucId.appendChild(xmlDocument.createTextNode(uc.getId()));
        Element ucName = xmlDocument.createElement("name");
        ucName.appendChild(xmlDocument.createTextNode(uc.getName()));
        Element ucDescription = xmlDocument.createElement("description");
        ucDescription.appendChild(xmlDocument.createTextNode(uc.getDescription()));
        Element ucSetup = xmlDocument.createElement("setup");
        ucSetup.appendChild(xmlDocument.createTextNode(uc.getSetup()));

        this.itemUseCase.appendChild(ucId);
        this.itemUseCase.appendChild(ucName);
        this.itemUseCase.appendChild(ucDescription);
        this.itemUseCase.appendChild(ucSetup);
    }
    
    /**
     * 
     * Generates the <useCase> tag.
     * @param uc the use case object
     */
    private void generateTagFlow(Flow flow, String ucId, String featureId)
    {
        this.itemFlow = xmlDocument.createElement("flow");
        Element flowDescription = xmlDocument.createElement("description");
        flowDescription.appendChild(xmlDocument.createTextNode(flow.getDescription()));

        // From Steps
        Element flowFrom = xmlDocument.createElement("fromSteps");
        Set<StepId> from = flow.getFromSteps();
        flowFrom.appendChild(xmlDocument.createTextNode(this.getStepsText(from, ucId, featureId)));

        // To Steps
        Element flowTo = xmlDocument.createElement("toSteps");
        Set<StepId> to = flow.getToSteps();
        flowTo.appendChild(xmlDocument.createTextNode(this.getStepsText(to, ucId, featureId)));

        this.itemFlow.appendChild(flowDescription);
        this.itemFlow.appendChild(flowFrom);
        this.itemFlow.appendChild(flowTo);
    }
    
    /**
     * 
     * Returns a String representation of the use cases steps.
     * @param stepSet the set of steps
     * @param ucId the use case id
     * @param featureId the feature Id
     * @return a String representation of the use cases steps
     */
    private String getStepsText(Set<StepId> stepSet, String ucId, String featureId)
    {
        Iterator<StepId> itStep = stepSet.iterator();

        String step = "";

        while (itStep.hasNext())
        {
            StepId id = itStep.next();
            if ((id.getStepId().equals("START")) || (id.getStepId().equals("END")))
                step = step + id.getStepId() + ",";
            else if (id.getFeatureId().equals(featureId))
            {
                if (id.getUseCaseId().equals(ucId))
                {
                    step = step + id.getStepId() + ",";
                }
                else
                {
                    step = step + id.getUseCaseId() + "#" + id.getStepId() + ",";
                }
            }
            else
            {
                step = step + id.getFeatureId() + "#" + id.getUseCaseId() + "#" + id.getStepId()
                        + ",";
            }
        }
        if (step != "")
        {
            step = step.substring(0, step.length() - 1);
        }

        return step;
    }
    
    /**
     * 
     * Generates the <step> tags.
     * @param step
     */
    private void generateTagStep(FlowStep step)
    {
        this.itemStep = xmlDocument.createElement("step");
        Element stepId = xmlDocument.createElement("stepId");

        stepId.appendChild(xmlDocument.createTextNode(step.getId().getStepId()));
        Element stepAction = xmlDocument.createElement("action");

        stepAction.appendChild(xmlDocument.createTextNode(step.getUserAction()));
        Element stepCondition = xmlDocument.createElement("condition");

        stepCondition.appendChild(xmlDocument.createTextNode(step.getSystemCondition()));
        Element stepResponse = xmlDocument.createElement("response");

        stepResponse.appendChild(xmlDocument.createTextNode(step.getSystemResponse()));

        this.itemStep.appendChild(stepId);
        this.itemStep.appendChild(stepAction);
        this.itemStep.appendChild(stepCondition);
        this.itemStep.appendChild(stepResponse);
    }

    /**
     * Returns the useCaseDocument attribute.
     * @return the useCaseDocument attribute
     */
    public UseCaseDocument getUseCaseDocument()
    {
        return this.useCaseDocument;
    }

    /**
     * Saves the XML file.
     * @param file the XML file to be saved
     */
    //INSPECT mcms adding an output format to permit using accents and special characters in schema XML (26/10/2009)
    public void saveXMLFile(File file)
    {
        try
        {
        	OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
            XMLSerializer serializer = new XMLSerializer(of);
            
            FileWriter fileWriter = new java.io.FileWriter(file.getPath());
            
            serializer.setOutputCharStream(fileWriter);
            serializer.serialize(this.xmlDocument);
            
            fileWriter.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Returns the feature attribute.
     * @return the feature attribute
     */
    public Feature getFeature()
    {
        return this.feature;
    }
}
