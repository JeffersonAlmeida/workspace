/*
 * @(#)TestSuiteFilter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 29, 2007   LIBmm42774   Initial creation.
 * wdt022    Aug 14, 2007   LIBmm42774   Modification due to inspection LX198758. Renamed from AbstractTestSuiteFilter. 
 */
package com.motorola.btc.research.target.tcg.filters;

import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/** 
 *
 * This interface represents an abstract test suite filter. Its main purpose is provide
 * an interface for a filter implementation that receives as input a test suite
 * and delivers as output a new test suite with fewer test cases.
 * <p>
 * The interface contains a method that must be implemented with the desired filter algorithm.
 * 
 */
public interface TestSuiteFilter<STEP>
{
    /**
     * This method represents any filter that may be performed on a test suite.
     * The filter consists in removing some test cases from the input test suite
     * and delivering a new test suite. 
     * 
     * @param testSuite The test suite that will be filtered.
     * @return The filtered test suite.
     */
    public TestSuite<TestCase<STEP>> filter(TestSuite<TestCase<STEP>> testSuite);
}
