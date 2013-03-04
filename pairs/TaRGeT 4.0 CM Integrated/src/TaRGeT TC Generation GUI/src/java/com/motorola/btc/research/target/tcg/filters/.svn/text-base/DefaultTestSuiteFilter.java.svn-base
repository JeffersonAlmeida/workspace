/*
 * @(#)DefaultTestSuiteFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Jun 05, 2007   LIBmm42774   Initial creation.
 */
package com.motorola.btc.research.target.tcg.filters;

import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/**
 * This class implements a test suite filter that does nothing. It only clones the received test
 * suite.
 */
public class DefaultTestSuiteFilter implements TestSuiteFilter<FlowStep>
{
    /**
     * This method does not change the input test suite. It only clones the input test suite and
     * delivers as output.
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        return new TestSuite<TestCase<FlowStep>>(testSuite.getTestCases(), testSuite.getName());
    }

}
