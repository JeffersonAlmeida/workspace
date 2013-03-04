/*
 * @(#)CompositionTestSuiteFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Aug 08, 2007   LIBmm42774   Initial creation.
 */
package com.motorola.btc.research.target.tcg.filters;

import java.util.ArrayList;
import java.util.List;

import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/**
 * This class represents a filter that is assembled by the composition of other filters. For
 * instance, the composition of two test suites means that the result from one filter is passed as
 * parameter to the filter.
 * <p>
 * It is possible to compose <b>N</b> filters. This means that the result from filter <b>1</b> is
 * passed to filter <b>2</b>, the result of filter <b>2</b> is passed to filter <b>3</b> and so
 * on.
 */
public class CompositionTestSuiteFilter implements TestSuiteFilter<FlowStep>
{
    /** The list of filters that will be composed */
    private List<TestSuiteFilter<FlowStep>> composedFilter;

    /**
     * Constructor that receives the list of filters that will compose a major filter. The
     * composition is performed from the beginning to the end of the filter list.
     * 
     * @param filters The list of filters to be composed.
     */
    public CompositionTestSuiteFilter(List<TestSuiteFilter<FlowStep>> filters)
    {
        this.composedFilter = new ArrayList<TestSuiteFilter<FlowStep>>(filters);
    }

    /**
     * Composes the filters contained in the attribute <code>composedFilter</code>.
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        for (TestSuiteFilter<FlowStep> filter : this.composedFilter)
        {
            testSuite = filter.filter(testSuite);
        }
        return testSuite;
    }
}
