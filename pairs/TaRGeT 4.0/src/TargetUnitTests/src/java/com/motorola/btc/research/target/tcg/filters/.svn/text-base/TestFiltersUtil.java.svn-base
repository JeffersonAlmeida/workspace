/*
 * @(#)TestFiltersUtil.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Aug 9, 2007    LIBmm42774   Initial creation.
 * dhq348    Aug 21, 2007   LIBmm42774   Rework after inspection LX201094. Removed method getPhoneDocument(). The class is now using UnitTestUtil.getPhoneDocument().
 */
package com.motorola.btc.research.target.tcg.filters;

import java.util.ArrayList;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.common.lts.UserViewLTSGenerator;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;
import com.motorola.btc.research.target.tcg.extractor.BasicPathFactory;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TestSuiteFactory;

/**
 * Class that contains some static methods used several times in test classes from filters and
 * pruners packages.
 */
public class TestFiltersUtil
{

    /**
     * Generates a <code>TestSuite</code> object from a given <code>documentName</code>.
     * 
     * @return A test suite generated from the given document.
     * @throws TestGenerationException Thrown in case of any error during the test generation.
     * @throws UseCaseDocumentXMLException Thrown in case of any error during the XML extraction or
     * during the XML parsing.
     */
    public static TestSuite<TestCase<FlowStep>> generateTestSuite(String documentName)
            throws TestGenerationException, UseCaseDocumentXMLException
    {
        /* The input use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(UnitTestUtil.getPhoneDocument(documentName)); 

        /* Sets the LTS and generate the tests */
        UserViewLTSGenerator ltsGenerator = new UserViewLTSGenerator();
        LTS<FlowStep, Integer> lts = ltsGenerator.generateLTS(docs);

        TestSuiteFactory<FlowStep> testFactory = new BasicPathFactory();
        return testFactory.create(lts);
    }
}
