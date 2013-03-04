/*
 * @(#)RequirementsFilterTest.java
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
import java.util.Collection;

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
 * This class tests the <code>RequirementsFilter</code> class. It checks the number of test cases
 * in a filtered suite.
 */
public class RequirementsFilterTest
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
     * Tests if rawTestSuite was correctly filtered according to some requirements. Tests the number
     * of tests in the filtered test suite.
     * 
     * <pre>
     * <ol>
     * <li>Checks that filtering by requirement FR_TARGET_0025 produces 9 test cases.
     * <li>Checks that filtering by requirements FR_TARGET_0025 or FR_TARGET_0040 produces 15 test cases.
     * </ol>
     * </pre>
     */
    @Test
    public void testFilter()
    {
        Collection<String> requirements = new ArrayList<String>();
        requirements.add("FR_TARGET_0025");
        RequirementsFilter filter = new RequirementsFilter(requirements);
        TestSuite<TestCase<FlowStep>> newSuite = filter.filter(rawTestSuite);
        Assert.assertNotNull("The filtered test suite is null.", newSuite);
        Assert.assertEquals("Wrong number of test cases.", 9, newSuite.getTestCases().size());

        requirements.add("FR_TARGET_0040");
        filter = new RequirementsFilter(requirements);
        newSuite = filter.filter(rawTestSuite);

        Assert.assertEquals("Wrong number of test cases.", 15, newSuite.getTestCases().size());
    }
}
