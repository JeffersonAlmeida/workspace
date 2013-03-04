package br.ufpe.cin.target.monitoring;

import java.io.PrintStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import br.ufpe.cin.target.common.tuclientagent.TrackUsageClient;
import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.pm.controller.TargetProject;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;




/*
 * @(#)TestCaseGenerationLogging.aj
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Apr 9, 2007    LIBll91788   Initial creation.
 * dhq348   Apr 18, 2007    LIBll91788   Rework of inspection LX164695.
 */



/**
 * This aspect is responsible for sending log information about test case generation. 
 * 
 * <pre>
 * More specifically, it logs information about: 
 * 
 *      1) during test case generation (TEST_GEN): 
 *          1.1) Number of flows 
 *          1.2) Number of test cases generated
 *           
 *      2) during test case generation (IMPORTED_DOCS): 
 *          2.1) Number of loaded documents 
 *          2.2) Number of features 
 *          2.3) Number of use cases 
 *          2.4) Number of flows 
 *          2.5) Number of requirements
 * </pre>
 */
public aspect TestCaseGenerationLogging
{
   
	after() : execution(void *.start(BundleContext))
	    
	    && !within(HelloAspect) {
	System.out.println("Hi from HelloAspect ;-)");
	}
	
	before() : execution(* *.generateXmlFromWordDocument(.., ..)) &&  !within(HelloAspect) {
	System.out.println("Gerando xml...");
	}
	before() : execution(* *.teste()) &&  !within(HelloAspect) {
	System.out.println("Gerando OPAAAAA...");
	}

/**
* Replaces the "Good bye world!" output with "Bye from HelloAspect ;-)".
*/
	void around() : cflowbelow(execution(void BundleActivator.stop(BundleContext))) 
	    && call(void PrintStream.println(String))
	    && !within(HelloAspect) {
		System.out.println("Bye from HelloAspect ;-)");
	}
	
	TestSuite<TestCase<FlowStep>> around() : execution(TestSuite<TestCase<FlowStep>> *.getCurrentTestSuite()) 
	&& cflowbelow(execution(void createSaveTestSuiteButton(Composite))){
		System.out.println("TesteNovo");
		
		TestSuite<TestCase<FlowStep>> suite =  proceed();
		
		return suite;
	}
	
    pointcut generateTestCase(): 
        execution(* *.generateTests(..,..,..));
    
    /**
     * Returns the number of flows in 'currentProject'.
     * 
     * @param currentProject The project whose number of flows will be counted.
     * @return The number of flows in the 'currentProject'.
     */
    private int getNumberOfFlows(TargetProject currentProject)
    {
        int result = 0;
        for (Feature feature : currentProject.getFeatures())
        {
            result += getNumberOfFlowsFromFeature(feature);
        }
        return result;
    }

    /**
     * Returns the number of flows given a feature
     * 
     * @param feature The feature whose flows will be counted.
     * @return The number of flows given a feature.
     */
    private int getNumberOfFlowsFromFeature(Feature feature)
    {
        int result = 0;
        for (UseCase useCase : feature.getUseCases())
        {
            result += useCase.getFlows().size();
        }
        return result;
    }

    /**
     * Returns the number of documents in 'currentProject'.
     * 
     * @param currentProject The project whose the number of documents will be searched.
     * @return The number of documents in 'currentProject'.
     */
    private int getNumberOfDocuments(TargetProject currentProject)
    {
        return currentProject.getUseCaseDocuments().size();
    }

    /**
     * Gets the number of features in 'currentProject'.
     * 
     * @param currentProject The project whose number of features will be counted.
     * @return The number of features in 'currentProject'.
     */
    private int getNumberOfFeatures(TargetProject currentProject)
    {
        return currentProject.getFeatures().size();
    }

    /**
     * Gets the number of use cases in 'currentProject'.
     * 
     * @param currentProject The project whose number of use cases will be counted.
     * @return The number of use cases in 'currentProject'.
     */
    private int getNumberOfUseCases(TargetProject currentProject)
    {
        int result = 0;
        for (Feature feature : currentProject.getFeatures())
        {
            result += feature.getUseCases().size();
        }
        return result;
    }
    TestSuite<TextualTestCase> around() : generateTestCase() {
        /*
         * this generationType can be 'All' if the option for generating all test cases was selected
         * or 'Requirements' if only some requirements were selected
         */
        String generationType = "";
      
        generationType = thisJoinPoint.getSignature().getName().contains("All") ? "All"
                : "Requirements";

        TestSuite<TextualTestCase> suite = proceed();

        TargetProject currentProject = ProjectManagerExternalFacade.getInstance()
                .getCurrentProject();

        /* only test gen */
        String param1 = "TEST_GEN";
        String param2 = getNumberOfFlows(currentProject) + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR
                + suite.getTestCases().size() + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR + generationType;
        TrackUsageClient.executeAgent(param1, param2);

        /* imported docs */
        int numberOfRequirements = ProjectManagerExternalFacade.getInstance()
                .getAllReferencedRequirementsOrdered().size();
        param1 = "IMPORTED_DOCS";
        param2 = "Number of Documents: "+ getNumberOfDocuments(currentProject) + "\n"
                +"Number of Features: " +  getNumberOfFeatures(currentProject) + "\n"
                +"Number of UseCases: " + getNumberOfUseCases(currentProject) + "\n"
                +"Number of Flows: " + getNumberOfFlows(currentProject) + "\n";
        TrackUsageClient.executeAgent(param1, param2);

        return suite;
    }

    
}
