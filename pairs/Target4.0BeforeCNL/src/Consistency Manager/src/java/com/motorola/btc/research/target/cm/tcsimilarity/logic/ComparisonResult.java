/*
 * @(#)ComparisonResult.java
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

import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * Represents the result of the comparison of some new test case with an old test case.
 */
public class ComparisonResult
{

    /**
     * The old test case.
     */
    private TextualTestCase oldTestCase;

    /**
     * The similarity of the old test case to a new test case.
     */
    private double similarity;

    /**
     * Constructor for the Comparison Result class.
     * 
     * @param oldTestCase The old test case.
     * @param similarity The result of the comparison.
     */
    public ComparisonResult(TextualTestCase oldTestCase, double similarity)
    {
        this.oldTestCase = oldTestCase;
        this.similarity = similarity;
    }

    /**
     * Gets the old test case.
     * 
     * @return The old test case.
     */
    public TextualTestCase getOldTestCase()
    {
        return oldTestCase;
    }

    /**
     * Gets the similarity between test cases.
     * 
     * @return The similarity between the test cases.
     */
    public double getSimilarity()
    {
        return similarity;
    }

}
