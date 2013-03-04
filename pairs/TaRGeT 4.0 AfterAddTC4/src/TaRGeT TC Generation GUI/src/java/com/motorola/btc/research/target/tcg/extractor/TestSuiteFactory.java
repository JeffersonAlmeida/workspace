/*
 * @(#)TestSuiteFactory.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2006   LIBkk11577   Modifications after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   Modifications according to CR.
 */
package com.motorola.btc.research.target.tcg.extractor;

import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;

/**
 * This interface is implemented by test case generation algorithms.
 * 
 * <pre>
 * INTERFACE:
 * It is an interface that contains only one method. This method receives as input a 
 * LTS model, and delivers as output a test suite.
 * 
 * RESPONSIBILITIES:
 * 1) Provides a unique interface for the test generation algorithm.
 *
 * COLABORATORS:
 * 2) Uses TestSuite
 * <pre>
 */
public interface TestSuiteFactory<STEP>
{

    /**
     * Generates a test suite from an input LTS model.
     * 
     * @param The input LTS model.
     * @return The generated test suite.
     * @throws TestGenerationException Thrown in case of any error during the test generation.
     */
    public TestSuite<TestCase<STEP>> create(LTS<STEP, Integer> model)
            throws TestGenerationException;

}
