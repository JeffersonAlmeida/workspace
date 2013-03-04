/*
 * @(#)TestSuite.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modifications after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   Modifications according to CR.
 */
package com.motorola.btc.research.target.tcg.extractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a suite of tests of type <T extends TestCase>.
 * It contains a list of revisions containing the revisions of all phone documents in the test suite.
 * 
 * <pre>
 * CLASS:
 * This class represents a suite of tests of type <T extends TestCase>.
 * 
 * RESPONSIBILITIES:
 * 1) Provide a way to organize the test cases.
 * 
 * COLABORATORS:
 * 1) Uses TestCase
 *
 * USAGE:
 *  TestSuite<TextualTestCase> textualTs = new TestSuite<TextualTestCase>(new ArrayList<TextualTestCase>());
 *  textualTs.insertTest(textualTest);
 */
public class TestSuite<T extends TestCase>
{

    /** Name of the test suite */
    private String name;

    /** Test cases of the suite */
    private List<T> testCases;
    
    /**
     * Constructs a test suite using the given test cases.
     * 
     * @param testCases The test cases of the suite
     */
    public TestSuite(List<T> testCases)
    {
        this.testCases = new ArrayList<T>(testCases);
    }
    
    /** 
     * Constructs a test suite using the given test cases and its name.
     * 
     * @param testCases The test cases of the suite
     * @param name The name of the test suite
     */
    public TestSuite(List<T> testCases, String name)
    {
        this.testCases = new ArrayList<T>(testCases);
        this.name = name;
    }

    /**
     * Retrieves the test cases of the suite.
     * 
     * @return The test cases.
     */
    public List<T> getTestCases()
    {
        return this.testCases;
    }

    /**
     * Insert a test into the suite.
     * 
     * @param test The test to be inserted.
     */
    public void insertTest(T test)
    {
        this.testCases.add(test);
    }

    /**
     * Returns a string representing each of the test cases of the test suite.
     */

    public String toString()
    {
        StringBuffer sb = new StringBuffer("");
        Iterator<T> i = testCases.iterator();
        while (i.hasNext())
        {
            T tc = i.next();
            sb.append(tc.toString());
            sb.append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves the name of the suite.
     * 
     * @return The suite name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the suite.
     * 
     * @param name The new test suite name.
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
