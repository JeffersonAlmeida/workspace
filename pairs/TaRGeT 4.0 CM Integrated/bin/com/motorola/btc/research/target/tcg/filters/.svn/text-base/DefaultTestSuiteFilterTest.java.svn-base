/*
 * @(#)DefaultTestSuiteFilterTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Aug 9,  2007   LIBmm42774   Initial creation.
 * dhq348    Aug 16, 2007   LIBmm42774   Rework after inspection LX199806.    
 */
package com.motorola.btc.research.target.tcg.filters;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/**
 * This class tests the DefaultTestSuiteFilter. It basically compares the tests in a simple raw test
 * suite with the test that were filtered by <code>DefaultTestSuiteFilter</code>.
 */
public class DefaultTestSuiteFilterTest
{

    /** A raw test suite */
    private static TestSuite<TestCase<FlowStep>> rawTestSuite;

    /**
     * Creates a raw test suite from HUGE_USE_CASE_DOCUMENT.
     * 
     * @throws TestGenerationException Thrown in case of any error during the test generation.
     * @throws UseCaseDocumentXMLException Thrown in case of any error during the XML extraction or
     * during the XML parsing.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws TestGenerationException,
            UseCaseDocumentXMLException
    {
        rawTestSuite = TestFiltersUtil
                .generateTestSuite(TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT);
    }

    /**
     * Compares the tests from the raw test suite and the tests filtered by DefaultTestSuiteFilter.
     * Tests the number of tests in the suite and checks if all of them are contained in the
     * filtered test suite.
     */
    @Test
    public void testFilter()
    {
        DefaultTestSuiteFilter filter = new DefaultTestSuiteFilter();
        TestSuite<TestCase<FlowStep>> newSuite = filter.filter(rawTestSuite);
        Assert.assertNotNull("The filtered test suite is null.", newSuite);
        Assert.assertEquals("Wrong number of test cases.", newSuite.getTestCases().size(),
                rawTestSuite.getTestCases().size());

        Set<TestCase<FlowStep>> originalTestCaseSet = new HashSet<TestCase<FlowStep>>(rawTestSuite
                .getTestCases());
        for (TestCase<FlowStep> testCase : newSuite.getTestCases())
        {
            Assert.assertTrue("The expected test case was not generated.", originalTestCaseSet
                    .contains(testCase));
        }
    }
}
