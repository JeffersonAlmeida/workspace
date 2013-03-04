/*
 * @(#)PurposeFilterTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wfo007    Aug 22, 2007   LIBmm42774   Initial creation.
 * wfo007    Aug 24, 2007   LIBmm42774   Rework after inspection.
 * wln013    Mar 25, 2008   LIBpp56482   Removed the pruner assembler from the method testFilterForLoopDoc.
 */
package com.motorola.btc.research.target.tcg.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.common.lts.UserViewLTSGenerator;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;
import com.motorola.btc.research.target.tcg.extractor.BasicPathFactory;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;

/**
 * This class is used to test the Purpose filter.
 * 
 * <pre>
 * CLASS:
 * This class is used to test the purpose Filter.
 * The Purpose Filter filters the test suite, according to the specified 
 * 	purpose to assure that the test suite satisfies the purpose.
 *  
 * RESPONSIBILITIES:
 * 1) Test the PurposeFilter using a test suite generated from a document.
 *
 * COLABORATORS:
 * 1) Uses LTS, FlowStep, TestCase, TestSuite and PhoneDocument.
 * 2) Uses Purpose and UserViewLTSGenerator classes.
 *
 * </pre>
 */
public class PurposeFilterTest
{
    /** Loaded PhoneDocument for the file TestCaseGenerationTests.FLOW_WITH_LOOP_DOC */
    private static PhoneDocument flowWithLoopDoc;

    /**
     * Initializes the instance.
     */
    public PurposeFilterTest()
    {
    }

    /**
     * Load the use case documents (static attributes) that will be used as input for tests.
     * 
     * @throws UseCaseDocumentXMLException Thrown in case of a problem occurs while loading the use
     * case documents.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws UseCaseDocumentXMLException
    {

        /* Load the XML from the files */
        List<String> docs = new ArrayList<String>();
        docs.add(TestCaseGenerationTests.FLOW_WITH_LOOP_DOC);

        WordDocumentProcessing wdp = WordDocumentProcessing.getInstance();
        Collection<PhoneDocument> userViewDocuments;

        System.out.print("[STARTED] Loading documents...");
        userViewDocuments = wdp.createObjectsFromWordDocument(docs, true);
        System.out.println(" [FINISHED]");

        /* Set the phone document objects */
        Iterator<PhoneDocument> i = userViewDocuments.iterator();
        flowWithLoopDoc = i.next();
    }

    /**
     * INSPECT
     * 
     * Test if the purpose filter is assuring that the purpose is satisfied. First, the LTS is
     * pruned, but after the pruning a test case that does not match the purpose is generated, then,
     * the purpose filter filters the suite, providing a test suite with all of its test cases
     * matching the specified purpose.
     */
    @Test
    public void testFilterForLoopDoc()
    {
        // Retrieve the LTS for the specified document.
        PhoneDocument document = flowWithLoopDoc;
        LTS<FlowStep, Integer> lts = getLTS(document);

        try
        {
            // Steps from the document, that will be used to match the purpose.
            FlowStep stepA = new FlowStep(new StepId("33333", "UC_01", "2A"),
                    "Fill some of the extended information fields.", "",
                    "Some of the extended information form is filled.", new HashSet<String>());

            FlowStep stepB = new FlowStep(new StepId("33333", "UC_01", "4M"),
                    "Confirm the contact creation.",
                    "There is enough phone memory to insert a new contact.",
                    "A new contact is created in My Phonebook application.", new HashSet<String>());

            List<FlowStep> steps = new ArrayList<FlowStep>();
            steps.add(stepA);
            steps.add(stepB);

            	
            	
            // Generate the test suite, from all the paths in the LTS.
            BasicPathFactory extractor = new BasicPathFactory();
            TestSuite<TestCase<FlowStep>> suite = extractor.create(lts);

            // Verify that more than one test case was generated, and only one
            // of them should be generated, since it is the only one that
            // satisfies the test purpose.
            assertEquals(suite.getTestCases().size(), 2);
            assertFalse(suite.getTestCases().get(0).getSteps().containsAll(steps));
            assertTrue(suite.getTestCases().get(1).getSteps().containsAll(steps));

            // Filter the suite, and verify that only one test case remains,
            // and this test case is the one that satisfies the purpose.
           
            // Retrieve a *;stepA;*;stepB;* test purpose.
            
            TestPurpose purpose = getPurpose(stepA, stepB); 
            List<TestPurpose> testPurposes = new ArrayList<TestPurpose>();
            testPurposes.add(purpose);
            
            PurposeFilter purposeFilter = new PurposeFilter(testPurposes);
            TestSuite<TestCase<FlowStep>> filteredSuite = purposeFilter.filter(suite);
            assertEquals(filteredSuite.getTestCases().size(), 1);
            assertTrue(filteredSuite.getTestCases().get(0).getSteps().containsAll(steps));
        }
        catch (TestGenerationException e)
        {
            fail();
        }
    }

    /**
     * Retrieve a test purpose of the type: <b>*;stepA;*;stepB;*"</b>
     */
    private TestPurpose getPurpose(FlowStep stepA, FlowStep stepB)
    {
        List<FlowStep> steps = new ArrayList<FlowStep>();
        steps.add(TestPurpose.STAR_STEP);
        steps.add(stepA);
        steps.add(TestPurpose.STAR_STEP);
        steps.add(stepB);
        steps.add(TestPurpose.STAR_STEP);
        return new TestPurpose(steps);
    }

    /**
     * Retrieve an LTs from a specified document.
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
