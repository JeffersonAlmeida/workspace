/*
 * @(#)TextualTestCaseFactory.java
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
 * tnd783    Jul 10, 2008    LIBpp71785   Initial creation.
 * tnd783    Jul 21, 2008    LIBpp71785   Rework after inspection LX285039.
 * tnd783    Sep 09, 2008	 LIBqq51204   Changes made to support the new properties.
 * dwvm83	 Sep 30, 2008	 LIBqq51204	  Rework after inspection LX302177. 
 * fsf2      Feb 19, 2009                 Adapted to Test Central 4 template (status, regression level).
 * fsf2      Jul 10, 2009                 Added TC ID header to the test case.
 */
package br.ufpe.cin.target.tcg.extractor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties;
import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties.PrintingDescription;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.UseCaseUtil;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;

/**
 * This class represents a factory of Textual Test Cases.
 * 
 * <pre>
 * CLASS:
 * This class represents a factory of Textual Test Cases.
 * 
 * RESPONSIBILITIES:
 * 1) Creates an instance of TextualTestCase, keeping the textual information that is used to generate a test case in an excel sheet with the 
 * Test Central standard for test cases  
 *
 * COLABORATORS:
 * N/A
 *
 * USAGE:
 * TextualTestCase textualTest = TextualTestCaseFactory.newTextualTestCase(<TestCase<FlowStep>>, List<Flow>);
 */
public class TextualTestCaseFactory
{

    /**
     * The keep requirements property. It is read from the properties file.
     */
    private boolean KEEP_REQUIREMENTS = TestCaseProperties.getInstance()
            .isKeepingRequirements();

    /**
     * The test case id. It is read from the properties file.
     */
    private  String TEST_CASE_ID = TestCaseProperties.getInstance().getTestCaseId();
    
    /**
     * The test case initial id. It is read from the properties file.
     */
    
    private  int TEST_CASE_INITIAL_ID = TestCaseProperties.getInstance().getTestCaseInitialId();

    /**
     * The empty field. It is read from the properties file.
     */
    private   String EMPTY_FIELD = TestCaseProperties.getInstance().getEmptyField();

    /**
     * The objective prefix. It is read from the properties file.
     */
    private  String OBJECTIVE_PREFIX = TestCaseProperties.getInstance().getObjectivePrefix();

    /**
     * Creates an instance of TextualTestCase.
     * 
     * @param rawTest The raw test to be mapped into Textual Test Case
     * @param documentsFlows The use case documents flows from which the test cases were extracted
     * @return
     */
    public  TextualTestCase newTextualTestCase(TestCase<FlowStep> rawTest,
            List<Flow> documentsFlows)
    {
        String featureId = getDefiningFeature(rawTest);
        
        /* set the test string id */
        String tcIdHeader = createTCIdHeader(rawTest);

        /* set the test objective */
        String objective = buildObjective(rawTest, documentsFlows);

        /* set the requirements that are covered by the test */
        List<String> requirements = getRequirements(rawTest);

        /* set the test initial conditions */
        String initialConditions = getInitialConditions(rawTest);

        /* set the test steps */
        List<TextualStep> steps = getTextualSteps(rawTest.getSteps());

        /* gets the use case references */
        List<String> usecaseReferences = getUseCaseReferences(rawTest);

        /* gets the setups */
        String setups = getSetups(rawTest, usecaseReferences);

        /* gets the cleanup */
        String cleanup = getCleanup();

        /* gets the final conditions */
        String finalConditions = getFinalConditions();

        /* gets the note */
        String note = getNote();

        /* gets the regression level */
        String regLevel = getRegressionLevel();

        /* gets the execution type */
        String executionType = getExecutionType();

        /* converts requirements into a string */
        String strRequirements = getListAsString(requirements);
        /* converts use case references into a string */
        String strUseCases = getListAsString(usecaseReferences);

        //setting the rawTest id to take into consideration the test case initial id
        rawTest.setId(rawTest.getId()- 1 + TEST_CASE_INITIAL_ID);
        
        String description = checkPrintUseCaseDescriptionProperty(rawTest);
        
        TextualTestCase textualTest = new TextualTestCase(rawTest.getId(), tcIdHeader, steps, executionType,
                regLevel, description, objective, strRequirements, setups, initialConditions, note,
                finalConditions, cleanup, strUseCases, this.getStatus(), featureId);
        return textualTest;
    }

    /**
     * Retrieves the execution type of the test case.
     * 
     * @return The execution type of the test case
     */
    private  String getExecutionType()
    {
        return "Man";
    }

	private String getStatus() 
	{
		return "Under Development";
	}
	
    /**
     * Retrieves the regression level of the test case.
     * 
     * @return The regression level of the test case
     */
    private  String getRegressionLevel()
    {
        return "na";
    }

    /**
     * Retrieves the note of the test case.
     * 
     * @return The note of the test case
     */
    private  String getNote()
    {
        return "Test case auto-generated by TaRGeT system.";
    }

    /**
     * Retrieves the final conditions of the test case.
     * 
     * @return The final conditions of the test case
     */
    private  String getFinalConditions()
    {
        return EMPTY_FIELD;
    }

    /**
     * Retrieves the execution type of the test case.
     * 
     * @return The execution type of the test case
     */
    private  String getCleanup()
    {
        return EMPTY_FIELD;
    }

    /**
     * Returns a string representing the test case case ID. The ID is created by modifying the
     * string in the test case properties file.
     * 
     * @param rawTest The test case whose id will be created.
     * @return A string representing the test case case ID
     */
    
    private  String createTCIdHeader(TestCase<FlowStep> rawTest)
    {
        String result = TEST_CASE_ID;
        
        int id = rawTest.getId() - 1 + TEST_CASE_INITIAL_ID ;
        
        DecimalFormat df = new DecimalFormat("000");
        result = result.replaceAll("<tc_id>", df.format(id));
        result = result.replaceAll("<tc_featureid>", getDefiningFeature(rawTest));

        return result;
    }

    /**
     * Checks the print use case description property and adds the use case description to the test
     * case description according to the property.
     * 
     * @param rawTest The test case whose description will be created.
     * @return A string representing the test case description.
     */
    private  String checkPrintUseCaseDescriptionProperty(TestCase<FlowStep> rawTest)
    {
        String result = "";
        PrintingDescription printUseCaseDescription = TestCaseProperties.getInstance()
                .getPrintUseCaseDescription();
        List<FlowStep> steps = rawTest.getSteps();

        switch (printUseCaseDescription)
        {
            case ALL:
                Set<UseCase> useCases = new HashSet<UseCase>();
                for (FlowStep flowStep : steps)
                {
                    String useCaseId = flowStep.getId().getUseCaseId();
                    UseCase useCase = ProjectManagerExternalFacade.getInstance().getUseCasesById(
                            useCaseId);
                    if (!useCases.contains(useCase))
                    {
                        result = result + " " + useCase.getDescription();
                        useCases.add(useCase);
                    }
                }
                break;
            case LAST:
                FlowStep lastStep = steps.get(steps.size() - 1);
                String useCaseId = lastStep.getId().getUseCaseId();
                UseCase useCase = ProjectManagerExternalFacade.getInstance().getUseCasesById(
                        useCaseId);
                result = result + " " + useCase.getDescription();
                break;
            case NONE:
                result = EMPTY_FIELD;
        }
        return result;
    }

    /**
     * Returns the id of the feature of the first flow step in <code>rawTest</code>.
     * 
     * @param The test whose feature id will be returned.
     */
    private  String getDefiningFeature(TestCase<FlowStep> rawTest)
    {
        String result = "";
        if (!rawTest.getSteps().isEmpty())
        {
            result = rawTest.getSteps().iterator().next().getId().getFeatureId();
        }
        return result;
    }

    /**
     * This method removes from the text the requirements between brackets.
     * 
     * @param text The text that contains the requirements to be removed.
     * @return The text without requirements.
     */
    private  String removeRequirements(String text)
    {
        String result = text;
        String patternStr = "[^\\u005B\\u005D]+(\\u005B(.*)\\u005D)";

        Pattern p = Pattern.compile(patternStr);

        Matcher m = p.matcher(text);

        if (m.matches())
        {
            int endIndex = m.start(1);
            result = text.substring(0, endIndex);
        }

        return result;
    }

    /**
     * Gets the setup strings in <code>rawTest</code>. Uses <code>usecaseReferences</code> to
     * get the use cases setups.
     * 
     * @param rawTest The test case in which the setups will be searched.
     * @param usecaseReferences The references used to retrieve setups from use cases.
     * @return A list with the setup strings.
     */
    //INSPECT - Laís Remoção de código para tratamento do setup do flowstep
    private  String getSetups(TestCase<FlowStep> rawTest, List<String> usecaseReferences)
    {
        List<String> result = new ArrayList<String>();

        for (String usecaseString : usecaseReferences)
        {
            String[] reference = UseCaseUtil.splitUseCaseReference(usecaseString);
            for (Feature feature : ProjectManagerExternalFacade.getInstance().getAllFeatures())
            {
                if (feature.getId().equals(reference[0]))
                {
                    UseCase usecase = feature.getUseCase(reference[1]);
                    if (usecase.getSetup() != null && usecase.getSetup().length() > 0)
                    {
                        result.add(usecase.getSetup());
                    }
                }
            }
        }
        
        String setup = getConnectedString(result, false);
        if (setup.length() == 0)
        {
            setup = EMPTY_FIELD;
        }
        return setup;
    }

    /**
     * Returns a list with the use case references of the given flow <code>step</code>.
     * 
     * @param testCase Used to obtain the use case references.
     * @return A list with the use case references.
     */
    private  List<String> getUseCaseReferences(TestCase<FlowStep> testCase)
    {
        Set<String> tmpResult = new LinkedHashSet<String>();

        for (FlowStep flowStep : testCase.getSteps())
        {
            tmpResult.add(UseCaseUtil.getUseCaseReference(flowStep));
        }

        List<String> result = new ArrayList<String>(tmpResult);
        return result;
    }

    /**
     * Maps a list of <code>FlowStep</code> into a list of <code>TextualStep</code>.
     * 
     * @param stepList The list of flow steps.
     * @return The textual steps representation from the given input steps.
     */
    private  List<TextualStep> getTextualSteps(List<FlowStep> stepList)
    {
        List<TextualStep> textualSteps = new ArrayList<TextualStep>();

        // for each step from the list
        for (FlowStep step : stepList)
        {
            String action = step.getUserAction();
            String response = step.getSystemResponse();
            String condition = step.getSystemCondition();
            StepId id = step.getId();

            TextualStep textualStep = null;

            if (!KEEP_REQUIREMENTS)
            {
                action = removeRequirements(action);
                response = removeRequirements(response);
            }

            if (condition != null && !condition.equals(""))
            {
                textualStep = new TextualStep(condition, action, response, id);
            }
            else
            {
                textualStep = new TextualStep(action, response, id);
            }

            textualSteps.add(textualStep);
        }
        return textualSteps;
    }

    /**
     * Retrieves all the requirements from a given raw test case.
     * 
     * @param test The test case to retrieve the requirements.
     * @return The requirements of the test.
     */
    private List<String> getRequirements(TestCase<FlowStep> test)
    {
        Set<String> requirements = new LinkedHashSet<String>();

        for (FlowStep step : test.getSteps())
        {
            requirements.addAll(step.getRelatedRequirements());
        }

        List<String> result = new ArrayList<String>(requirements);
        return result;
    }

    /**
     * Retrieves all the initial conditions from a given raw test case.
     * 
     * @param test The test case to retrieve the initial conditions.
     * @return The initial conditions of the test.
     */
    private  String getInitialConditions(TestCase<FlowStep> test)
    {
        List<String> initialConditions = new ArrayList<String>();

        for (FlowStep step : test.getSteps())
        {
            String condition = step.getSystemCondition();

            if (condition != null && !condition.equals(""))
            {
                initialConditions.add(condition);
            }
        }

        String result = getConnectedString(initialConditions, true);
        if (result.length() == 0)
        {
            result = EMPTY_FIELD;
        }

        return result;
    }

    /**
     * Builds and returns the objective of the given raw test case by concatenating the description
     * of the flows which the test covers.
     * 
     * @param test The raw test case.
     * @param documentsFlows The flows of all the input documents.
     * @return The objective of the test.
     */
    
    private  String buildObjective(TestCase<FlowStep> test, List<Flow> documentsFlows)
    {
        String objective = "";
        PrintingDescription printFlowDescription = TestCaseProperties.getInstance()
                .getPrintFlowDescription();
        
        List<FlowStep> steps = test.getSteps();

        switch (printFlowDescription)
        {
            case ALL:
                objective = getFlowDescriptions(steps, documentsFlows);
                break;
            case LAST:
                int lastIndex = steps.size() - 1;
                FlowStep lastFlow = steps.get(lastIndex);
                List<FlowStep> newList = new ArrayList<FlowStep>();
                newList.add(lastFlow);
                
                objective = getFlowDescriptions(newList, documentsFlows);
                break;
        }

        if (objective.equals("")){
            objective = EMPTY_FIELD;
        }else{
            objective = OBJECTIVE_PREFIX + " " + objective;
        }
        
        return objective;
    }

    /**
     * Returns all the documents flow descriptions concatenated to a given list of flow steps.
     * 
     * @param documentsFlows The flows of all the input documents.
     * @param flowSteps The list of flow steps of the raw test case.
     * @return A String representing all the flow descriptions concatenated.
     */
    
    private  String getFlowDescriptions(List<FlowStep> flowSteps, List<Flow> documentsFlows)
    {
        List<String> descriptions = new ArrayList<String>();

        /* for each step of the test */
        for (FlowStep step : flowSteps)
        {
            /* for each flow */
            for (Flow flow : documentsFlows)
            {
                /* if the test step is contained in the current flow store the flow description*/
                if (flow.getFlowStepById(step.getId()) != null){
                    String description = flow.getDescription();

                    if (description != null && !description.equals("") && !descriptions.contains(description))
                    {
                        descriptions.add(description);
                    }

                    break;
                }
            }
            
        }

        String result = getConnectedString(descriptions, false);
        return result;
    }

    /**
     * This method returns a single string from a list of strings. It concatenates the list of
     * strings and adds the periods and line break after each string in the list.
     * 
     * @param strList The list of strings.
     * @param doAsList True if each item in the connected string should be numbered.
     * @return The concatenated string.
     */
    private  String getConnectedString(List<String> strList, boolean doAsList)
    {
        String connectedString = "";
        int i = 1;
        for (String item : strList)
        {
            String str = "";
            if (item.length() > 0)
            {
                item = capitalizeString(item);
                item = item.trim();
                if (!item.endsWith("."))
                {
                    str = item + ".";
                }
                str = item + " ";
                if (doAsList)
                {
                    String index = i + ") ";
                    str = index + str;
                }
            }
            connectedString = connectedString + str;
            i = i + 1;
        }

        return connectedString;
    }

    /**
     * This method capitalizes the first letter of a string.
     * 
     * @param str The String to be capitalized.
     * @return The capitalized string.
     */
    private  String capitalizeString(String str)
    {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Converts a list of strings into a single string concatenated by commas.
     * 
     * @param collection The list to be converted.
     * @return The string which is the result of the conversion.
     */
    private  String getListAsString(List<String> list)
    {
        String str = "";

        for (String item : list)
        {
            if (!str.equals(""))
            {
                str = str + ", ";
            }
            str = str + item;
        }

        if (str.equals(""))
        {
            str = EMPTY_FIELD;
        }

        return str;
    }

}
