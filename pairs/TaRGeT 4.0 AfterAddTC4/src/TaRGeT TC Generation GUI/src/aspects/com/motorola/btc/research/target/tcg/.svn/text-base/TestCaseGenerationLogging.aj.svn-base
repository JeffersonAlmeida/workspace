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
package com.motorola.btc.research.target.tcg;

import com.motorola.btc.research.target.common.tuclientagent.TrackUsageClient;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.controller.TargetProject;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.controller.TestCaseGeneratorController;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;


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
    /**
     * Pointcut for test case generation. 
     */
    pointcut generateTestCase(): 
        execution(TestSuite<TextualTestCase> TestCaseGeneratorController.generate*Tests(..));

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
        return currentProject.getPhoneDocuments().size();
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

    /**
     * Advice for the 'generateTestCase' pointcut. It logs information about the generated test
     * cases and information about the imported documents.
     */
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
        param2 = getNumberOfDocuments(currentProject) + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR
                + getNumberOfFeatures(currentProject) + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR
                + getNumberOfUseCases(currentProject) + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR
                + getNumberOfFlows(currentProject) + TrackUsageClient.PARAMETER_INTERNAL_SEPARATOR + numberOfRequirements;
        TrackUsageClient.executeAgent(param1, param2);

        return suite;
    }
}
