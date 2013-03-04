/*
 * @(#)RequirementsFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Jun 06, 2007   LIBmm42774   Initial creation.
 * wdt022    Mar 20, 2008   LIBpp56482   getRequirements method created.
 */
package com.motorola.btc.research.target.tcg.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/**
 * This class contains the implementation of a filter based on requirements. This filter selects the
 * test cases that cover at least one of the informed requirements.
 * 
 * <pre>
 * CLASS:
 * It contains the attribute <code>requirements</code> that represent the requirements
 * on which the filtering will be based.
 * </pre>
 */
public class RequirementsFilter implements TestSuiteFilter<FlowStep>
{

    /** The list of requirements that will guide the filtering */
    private Set<String> requirements;

    /**
     * Constructor from which the requirements are informed to the filter.
     * 
     * @param requirements The list of requirements
     */
    public RequirementsFilter(Collection<String> requirements)
    {
        this.requirements = new HashSet<String>(requirements);
    }

    /**
     * This filter selects the test cases that cover at least one of the requirements in
     * <code>requirements</code> attribute.
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        List<TestCase<FlowStep>> result = new ArrayList<TestCase<FlowStep>>();
        for (TestCase<FlowStep> testCase : testSuite.getTestCases())
        {
            boolean found = false;
            for (FlowStep step : testCase.getSteps())
            {
                for (String requirement : this.requirements)
                {
                    if (step.getRelatedRequirements().contains(requirement))
                    {
                        result.add(testCase);
                        found = true;
                        break;
                    }
                }
                if (found)
                {
                    break;
                }
            }
        }

        return new TestSuite<TestCase<FlowStep>>(result, testSuite.getName());
    }

    /**
     * Gets the list of requirements set in the filter.
     * 
     * @return The set of requirements.
     */
    public Set<String> getRequirements()
    {
        return this.requirements;
    }
}
