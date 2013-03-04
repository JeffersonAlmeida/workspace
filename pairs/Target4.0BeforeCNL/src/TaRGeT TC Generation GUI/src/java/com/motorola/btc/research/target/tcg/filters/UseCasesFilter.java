/*
 * @(#)UseCasesFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Nov 19, 2007   LIBoo10574   Initial creation.
 * dhq348    Jan 23, 2008   LIBoo10574   Rework after inspection LX229632.
 * wdt022    Mar 20, 2008   LIBpp56482   getUsecases method added. Constructor updated.
 */
package com.motorola.btc.research.target.tcg.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/**
 * This class is used to filter a Test Suite using the a set of user selected use cases.
 */
public class UseCasesFilter implements TestSuiteFilter<FlowStep>
{

    /**
     * A map with the selected use case.
     */
    private HashMap<Feature, List<UseCase>> useCases;

    /**
     * Creates an UseCasesFilter given a map with the user selected <code>usecases</code>.
     */
    public UseCasesFilter(HashMap<Feature, List<UseCase>> usecases)
    {
        this.useCases = new HashMap<Feature, List<UseCase>>();
        for (Feature f : usecases.keySet())
        {
            this.useCases.put(f, new ArrayList<UseCase>(usecases.get(f)));
        }
    }

    /**
     * Filter the specified test suite using the given use cases.
     * 
     * @see TestSuiteFilter.filter()
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        List<TestCase<FlowStep>> result = new ArrayList<TestCase<FlowStep>>();

        for (TestCase<FlowStep> testCase : testSuite.getTestCases())
        {
            if (containsUseCase(testCase))
            {
                result.add(testCase);
            }
        }

        return new TestSuite<TestCase<FlowStep>>(result, testSuite.getName());
    }

    /**
     * Checks if the given <code>testCase</code> is covered by at least one of the selected use
     * cases.
     * 
     * @param testCase The test case to be checked.
     * @return <code>true</code> if the test case is covered or <code>false</code> otherwise.
     */
    private boolean containsUseCase(TestCase<FlowStep> testCase)
    {
        boolean result = false;

        /* iterates over the test case flow steps */
        for (FlowStep flowStep : testCase.getSteps())
        {
            /* iterates over the keys (features) of the user selected use cases */
            for (Feature feature : useCases.keySet())
            {
                StepId stepId = flowStep.getId();
                /* if the feature IDs are the same then iterates over the use cases list */
                if (stepId.getFeatureId().equals(feature.getId()))
                {
                    for (UseCase usecase : useCases.get(feature))
                    {
                        /*
                         * if the use case id and the step use case id are the same then the given
                         * testCase is covered by one of the user selected use cases
                         */
                        if (stepId.getUseCaseId().equals(usecase.getId()))
                        {
                            result = true;
                            break;
                        }
                    }
                    if (result)
                    {
                        break;
                    }
                }
            }
            if (result)
            {
                break;
            }
        }

        return result;
    }

    /**
     * The set of use cases that were selected into this filter.
     * 
     * @return A hashmap relating a feature to one or more use cases.
     */
    public HashMap<Feature, List<UseCase>> getUsecases()
    {
        return this.useCases;
    }

}
