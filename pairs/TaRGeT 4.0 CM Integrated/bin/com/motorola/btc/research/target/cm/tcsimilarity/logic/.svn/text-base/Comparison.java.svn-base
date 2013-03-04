/*
 * @(#)Comparison.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.tcsimilarity.logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * Represents the comparison of all new test cases will all old test cases.
 * RESPONSIBILITIES:
 * 1) Store all the comparison results of each new test case.
 */
public class Comparison
{

    /**
     * A map containing all the comparison results related to each new test case.
     */
    private LinkedHashMap<TextualTestCase, List<ComparisonResult>> comparisonResults;

    /**
     * Constructs an instance of Comparison.
     */
    public Comparison()
    {
        this.comparisonResults = new LinkedHashMap<TextualTestCase, List<ComparisonResult>>();
    }

    /**
     * Adds a list of comparison results to a new test case.
     * 
     * @param newTestCase The new test case.
     * @param comparisonResults The list of comparison results.
     */
    public void addComparisonResults(TextualTestCase newTestCase,
            List<ComparisonResult> comparisonResults)
    {
        if (newTestCase != null && comparisonResults != null)
        {
            List<ComparisonResult> list = this.comparisonResults.get(newTestCase);
            if (list != null)
            {
                list.addAll(comparisonResults);
            }
            else
            {
                this.comparisonResults.put(newTestCase, comparisonResults);
            }

        }
    }

    /**
     * Gets the comparison results associated to the given test case.
     * 
     * @param testCase The new test case
     * @return A list of comparison results associated to the given new test case. Returns null if
     * the there is no comparison results associated to the given test case.
     */
    public List<ComparisonResult> getComparisonResultsByTestCase(TextualTestCase testCase)
    {
        return comparisonResults.get(testCase);
    }

    /**
     * Gets all the new test cases which have comparison results associated to them.
     * 
     * @return A list with all the new test cases which have comparison results associated to them.
     */
    public List<TextualTestCase> getNewTestCases()
    {
        return new ArrayList<TextualTestCase>(comparisonResults.keySet());
    }

}
