/*
 * @(#)TargetTests.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Apr 25, 2007   LIBkk22882   Initial creation.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing. 
 * dhq348    Jun 01, 2007   LIBmm25975   Added SearchTestsManager
 * dhq348    Feb 14, 2008   LIBoo24851   Added InterruptionTest.
 */
package com.motorola.btc.research.target;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.motorola.btc.research.target.common.CommonTests;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing;
import com.motorola.btc.research.target.pm.ProjectManagerTests;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;

/**
 * This class encapsulates the test suite for all the unit tests of Target.
 */
@RunWith(Suite.class)
@SuiteClasses( { ProjectManagerTests.class, TestCaseGenerationTests.class,
        CommonTests.class})
public class TargetTests
{

    /**
     * Closes the listener of <code>WordDocumentProcessing</code> instance.
     * 
     * @throws UseCaseDocumentXMLException In case of any error during the listener closing.
     */
    @AfterClass
    public static void afterClass() throws UseCaseDocumentXMLException
    {
        WordDocumentProcessing.getInstance().finishListener();
    }
}
