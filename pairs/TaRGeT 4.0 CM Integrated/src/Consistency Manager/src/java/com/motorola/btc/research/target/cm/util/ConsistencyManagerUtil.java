/*
 * @(#)ConsistencyManagerUtil.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   15/08/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.util;

import java.util.ArrayList;
import java.util.List;

import com.motorola.btc.research.target.cm.tcsimilarity.logic.ComparisonResult;
import com.motorola.btc.research.target.cm.tcsimilarity.logic.TestCaseSimilarity;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * This class provides utility functions to the Consistency Management plug-in.
 */
public class ConsistencyManagerUtil
{
    /**
     * Converts a list of test similarity into a list of comparison results.
     * 
     * @param similarityList The similarity list to be converted.
     * @param oldTestCases The list of old test cases related to the similarity list.
     * @return A list of comparison results.
     */
    public static List<ComparisonResult> getComparisonResults(
            List<TestCaseSimilarity> similarityList, List<TextualTestCase> oldTestCases)
    {
        List<ComparisonResult> comparisonResults = new ArrayList<ComparisonResult>();
        for (TestCaseSimilarity similarity : similarityList)
        {
            String oldTestCaseId = similarity.getOriginalId();
            TextualTestCase oldTestCase = getTestCase(oldTestCases, oldTestCaseId);
            double realSimilarity = similarity.getRealSimilarity();
            /*if (realSimilarity < 100)
            {*/
                ComparisonResult comparisonResult = new ComparisonResult(oldTestCase,
                        realSimilarity);
                comparisonResults.add(comparisonResult);
            /*}*/
        }

        return comparisonResults;
    }

    /**
     * Searches for a textual test case in the given list which has the given id.
     * 
     * @param list The list to perform the search.
     * @param id The id of the test case which is being searched.
     * @return The test case in case it is found. If it is not found, the return value is null.
     */
    private static TextualTestCase getTestCase(List<TextualTestCase> list, String id)
    {
        TextualTestCase testCase = null;

        for (TextualTestCase current : list)
        {
            if (current.getId() == Integer.parseInt(id))
            {
                testCase = current;
                break;
            }
        }

        return testCase;
    }
}
