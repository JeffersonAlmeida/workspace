/*
 * @(#)CompositionTestSuiteFilterTest.java
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

import java.util.ArrayList;
import java.util.List;

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
 * Tests the class CompositionTestSuiteFilter. It composes a <code>CompositionTestSuiteFilter</code>
 * with <code>RequirementsFilter</code> and <code>DefaultTestSuiteFilter</code> and then
 * compares the expected number of test cases.
 */
public class CompositionTestSuiteFilterTest
{
    /** A simple raw test suite */
    private static TestSuite<TestCase<FlowStep>> rawTestSuite;

    /**
     * Creates a simple raw test suite from HUGE_USE_CASE_DOCUMENT.
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
     * This method perform the following tests:
     * 
     * <pre>
     * <ol>
     * <li>Creates a CompositionTestSuiteFilter with two RequirementsFilter and checks that they do not generate common test cases.
     * <li>Creates a CompositionTestSuiteFilter with two RequirementsFilter and checks that they generate six common test cases.
     * <li>Adds a DefaultTestSuiteFilter into the previous filtersList and then checks that it does not modify the number of test cases.
     * </ol>
     * </pre>
     */
    @Test
    public void testFilter()
    {
        List<TestSuiteFilter<FlowStep>> filtersList = new ArrayList<TestSuiteFilter<FlowStep>>();

        /* excludent requirements, the test cases final list must be empty */
        List<String> requirements = new ArrayList<String>();
        requirements.add("FR_TARGET_0025");
        RequirementsFilter filter1 = new RequirementsFilter(requirements);
        requirements = new ArrayList<String>();
        requirements.add("FR_TARGET_0040");
        RequirementsFilter filter2 = new RequirementsFilter(requirements);
        filtersList.add(filter1);
        filtersList.add(filter2);
        CompositionTestSuiteFilter filter = new CompositionTestSuiteFilter(filtersList);
        TestSuite<TestCase<FlowStep>> newSuite = filter.filter(rawTestSuite);
        Assert.assertNotNull("The filtered test suite is null.", newSuite);
        Assert.assertTrue("Wrong number of test cases.", newSuite.getTestCases().isEmpty());

        /* requirements that will have 6 common test cases */
        filtersList = new ArrayList<TestSuiteFilter<FlowStep>>();
        requirements = new ArrayList<String>();
        requirements.add("FR_TARGET_0015");
        filter1 = new RequirementsFilter(requirements);
        requirements = new ArrayList<String>();
        requirements.add("FR_TARGET_0100");
        filter2 = new RequirementsFilter(requirements);
        filtersList.add(filter1);
        filtersList.add(filter2);
        filter = new CompositionTestSuiteFilter(filtersList);
        newSuite = filter.filter(rawTestSuite);
        Assert.assertNotNull("The filtered test suite is null.", newSuite);
        Assert.assertEquals("Wrong number of test cases.", 9, newSuite.getTestCases().size());

        /* Adds a DefaultTestSuiteFilter but the result must be the same */
        filtersList.add(new DefaultTestSuiteFilter());
        filter = new CompositionTestSuiteFilter(filtersList);
        newSuite = filter.filter(rawTestSuite);
        Assert.assertNotNull("The filtered test suite is null.", newSuite);
        Assert.assertEquals("Wrong number of test cases.", 9, newSuite.getTestCases().size());
    }
}
