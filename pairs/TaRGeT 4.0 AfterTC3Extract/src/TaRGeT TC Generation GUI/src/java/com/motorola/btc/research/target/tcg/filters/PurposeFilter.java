/*
 * @(#)PurposeFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wfo007    Jun 04, 2007   LIBmm42774   Initial creation.
 * wfo007    Aug 24, 2007   LIBmm42774   Rework after inspection LX201876.
 * wdt022    Mar 20, 2008   LIBpp56482   Change filter approach. The filter is not a prunner, now it just filters the test cases.
 * wdt022    Mar 25, 2008   LIBpp56482   getPurposes method created.
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
 * This class is used to filter a Test Suite using a list of test purposes.
 * 
 * <pre>
 * CLASS:
 * This class is used to filter a Test Suite using a list of test purposes.
 * 
 * RESPONSIBILITIES:
 * 1) Filter a Test Suite using a list of test purposes, verifying if each purpose was satisfied.
 *
 * COLABORATORS:
 * 1) Uses TestSuite, TestCase, and FlowStep classes
 *
 * USAGE:
 *  PurposeFilter filter = new PurposeFilter(Collection<TestPurpose> purposes);
 *  TestSuite suite = filter.filter(TestSuite<TestCase<FlowStep>> suite);
 *  </pre>
 */

public class PurposeFilter implements TestSuiteFilter<FlowStep>
{

    /**
     * The set of test purposes that will be satisfied.
     */
    private Set<TestPurpose> purposes;

    /**
     * Create a purpose filter according to the test purposes.
     * 
     * @param purposes The list of test purposes
     */
    public PurposeFilter(Collection<TestPurpose> purposes)
    {
        this.purposes = new HashSet<TestPurpose>(purposes);
    }

    /**
     * @see com.motorola.btc.research.target.tcg.filters.TestSuiteFilter#filter(com.motorola.btc.research.target.tcg.extractor.TestSuite)
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        List<TestCase<FlowStep>> result = new ArrayList<TestCase<FlowStep>>();

        for (TestCase<FlowStep> testCase : testSuite.getTestCases())
        {

            if (testCase.getSteps().size() > 0)
            {
                for (TestPurpose purpose : this.purposes)
                {
                    // If the test case matches with at least one test purpose
                    if (matchPurpose(testCase.getSteps(), 0, purpose.getSteps(), 0))
                    {
                        result.add(testCase);
                        break;
                    }
                }
            }
        }
        return new TestSuite<TestCase<FlowStep>>(result, testSuite.getName());
    }

    /**
     * This recursive method checks if a test case satisfies a test purpose.
     * 
     * @param testCase The test case that will be verified.
     * @param i The test case steps counter.
     * @param purpose The test purpose that will be satisfied.
     * @param j The test purpose steps counter.
     * @return True if the test case matches with test purpose, false otherwise.
     */
    private boolean matchPurpose(List<FlowStep> testCase, int i, List<FlowStep> purpose, int j)
    {
        boolean result = true;
        
        if (i < testCase.size() && j < purpose.size())
        {
            // if the steps are equal, the pointers go forward.
            if (((FlowStep) purpose.get(j)).equals((FlowStep) testCase.get(i)))
            {
                result = matchPurpose(testCase, i + 1, purpose, j + 1);
            }
            else
            {
                // if the steps are not equal and the purpose step is the star step, the method is
                // called with three possibilities: just the test case pointer goes forward,
                // just the purpose pointer goes forward or both pointers go forward.
                if (((FlowStep) purpose.get(j)).equals(TestPurpose.STAR_STEP))
                {
                    result = ((matchPurpose(testCase, i + 1, purpose, j))
                            || (matchPurpose(testCase, i, purpose, j + 1)) || (matchPurpose(
                            testCase, i + 1, purpose, j + 1)));
                }
                else
                {
                    result = false;
                }
            }
        }
        else
        {
            result = (purpose.size() == j && testCase.size() == i || ((purpose.size() - 1 == j && testCase
                    .size() == i) && (purpose.get(j).equals(TestPurpose.STAR_STEP))));
        }
        return result;
    }

    /**
     * Gets the set of purposes for this filter.
     * 
     * @return The list of purposes.
     */
    public Set<TestPurpose> getPurposes()
    {
        return this.purposes;
    }
}