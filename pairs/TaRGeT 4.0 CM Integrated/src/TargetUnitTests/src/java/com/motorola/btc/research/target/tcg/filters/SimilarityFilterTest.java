/*
 * @(#)SimilarityFilterTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wfo007    Aug 17, 2007   LIBmm42774   Initial creation.
 * wfo007    Aug 24, 2007   LIBmm42774   Rework after inspection LX201876.
 */
package com.motorola.btc.research.target.tcg.filters;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.common.lts.UserViewLTSGenerator;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;
import com.motorola.btc.research.target.tcg.extractor.BasicPathFactory;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/**
 * This class is used to test the Similarity filter.
 * 
 * <pre>
 * CLASS:
 * This class is used to test the similarity Filter.
 * The Similarity Filter filters the test suite, according to the 
 *  technique created by Emanuela Gadelha, weg016.
 * There are few tests done in this class, because the similarity 
 *  technique uses a random choice, in some cases.
 *  
 * RESPONSIBILITIES:
 * 1) Test the SimilarityFilter using a test suite generated from a document.
 * 2) Test if the Similarity Technique was successfully done, removing the 
 *      most similar test cases according to a specified coverage.
 *
 * COLABORATORS:
 * 1) Uses LTS, FlowStep, TestCase, TestSuite and PhoneDocument.
 * 2) Uses Purpose, UserViewLTSGenerator, StepId, and Transition classes.
 *
 * </pre>
 */
public class SimilarityFilterTest
{
    /** Loaded PhoneDocument for the file TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC */
    private static PhoneDocument allFlowsWithReqDoc;

    /**
     * Load the use case documents (static attributes) that will be used as input for tests.
     * 
     * @throws UseCaseDocumentXMLException Thrown in case of a problem occurs while loading the use
     * case documents.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws UseCaseDocumentXMLException
    {
        allFlowsWithReqDoc = UnitTestUtil
                .getPhoneDocument(TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC);
    }

    /**
     * Test if the similarity filter is reducing the test suite using the similarity strategy from
     * Emanuela Gadelha, weg016.
     */
    @Test
    public void testFilterForAllFlowReqDoc()
    {
        // Retrieve the LTS for the specified document.
        PhoneDocument document = allFlowsWithReqDoc;
        LTS<FlowStep, Integer> lts = getLTS(document);

        try
        {
            // Generate the test suite, from all the paths in the LTS.
            BasicPathFactory extractor = new BasicPathFactory();
            TestSuite<TestCase<FlowStep>> suite = extractor.create(lts);

            // Establish a similarity filter, considering 50% coverage, that means,
            // that only 50% (the specified similarity coverage) of all the test
            // cases will remain in the suite.
            SimilarityFilter filter = new SimilarityFilter(50.00);
            TestSuite<TestCase<FlowStep>> finalSuite = filter.filter(suite);

            // Store the id of the test cases that will remain in the
            // suite, in order to do the assertion.
            List<TestCase<FlowStep>> remainingTCs = new ArrayList<TestCase<FlowStep>>();
            remainingTCs.add(suite.getTestCases().get(0));
            remainingTCs.add(suite.getTestCases().get(2));
            remainingTCs.add(suite.getTestCases().get(4));

            Assert.assertEquals(finalSuite.getTestCases().size(), 3);
            assertTrue(finalSuite.getTestCases().containsAll(remainingTCs));
        }
        catch (TestGenerationException e)
        {
            fail();
        }
    }

    /**
     * Retrieve an LTS from a specified document.
     * 
     * @param document A document used to generate the LTS.
     * @return The LTS for the specified document.
     */
    private LTS<FlowStep, Integer> getLTS(PhoneDocument document)
    {
        List<PhoneDocument> documents = new ArrayList<PhoneDocument>();
        documents.add(document);
        UserViewLTSGenerator ltsGenerator = new UserViewLTSGenerator();
        LTS<FlowStep, Integer> lts = ltsGenerator.generateLTS(documents);
        return lts;
    }

}
