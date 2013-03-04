/*
 * @(#)TestCaseGeneratorControllerTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Apr 10, 2007   LIBkk22882   Initial creation.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * wdt022    Jun 18, 2007   LIBmm42774   Modifications due to CR.
 * dhq348    Aug 16, 2007   LIBmm42774   Rework after inspection LX199806.
 * dhq348    Feb 15, 2008   LIBoo24851   Added interruption tests.
 * wln013    Mar 25, 2008   LIBpp56482   Update methods generateAndVerifyTestsForTuple, testTestCasesGenerationWithInterruptions and testTestCasesGenerationWithUseCasesCovering.
 * dwvm83    Set 25, 2008	LIBqq51204	 Added project creation in BeforeClass, fixed methods hasCommonTest, generateAllTestsForTwoDocuments and generateAndVerifyTestsForTuple  
 */
package com.motorola.btc.research.target.tcg.controller;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.common.lts.UserViewLTSGenerator;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing;
import com.motorola.btc.research.target.pm.ProjectManagerTests;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TextualStep;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.filters.TestSuiteFilterAssembler;

/**    
 * This class encapsulates the unit tests for TestCaseGeneratorController class.
 */
public class TestCaseGeneratorControllerTest
{

    /** Loaded PhoneDocument for the file TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT */
    private static PhoneDocument bigUseCaseDocument;

    /** Loaded PhoneDocument for the file TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC */
    private static PhoneDocument allFlowsWithReqDoc;

    /** Loaded PhoneDocument for the file TestCaseGenerationTests.REFERS_OTHER_DOC_UC_DOC */
    private static PhoneDocument refersOtherDocument;

    /** Loaded PhoneDocument for the file TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC */
    private static PhoneDocument multipleReferencesDocument;

    /** Loaded PhoneDocument for the file TestCaseGenerationTests.EQUIVALENT_UC_DOC */
    private static PhoneDocument equivalentUseCaseDocument;

    /** Loaded PhoneDocument for the file TestCaseGenerationTests.INVERTED_UC_DOC */
    private static PhoneDocument invertedUseCaseDoc;

    /** Loaded PhoneDocument for the file TestCaseGenerationTests.EMPTY_FLOW_DESC_UC_DOC */
    private static PhoneDocument emptyFlowsDescriptionDoc;

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
        docs.add(TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT);
        docs.add(TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC);
        docs.add(TestCaseGenerationTests.REFERS_OTHER_DOC_UC_DOC);
        docs.add(TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC);
        docs.add(TestCaseGenerationTests.EQUIVALENT_UC_DOC);
        docs.add(TestCaseGenerationTests.INVERTED_UC_DOC);
        docs.add(TestCaseGenerationTests.EMPTY_FLOW_DESC_UC_DOC);

        WordDocumentProcessing wdp = WordDocumentProcessing.getInstance();

        Collection<PhoneDocument> userViewDocuments;

        System.out.println(TestCaseGeneratorControllerTest.class.getName());
        System.out.print("[STARTED] Loading documents...");
        userViewDocuments = wdp.createObjectsFromWordDocument(docs, true);
        System.out.println(" [FINISHED]");

        /* Set the phone document objects */
        Iterator<PhoneDocument> i = userViewDocuments.iterator();
        bigUseCaseDocument = i.next();
        allFlowsWithReqDoc = i.next();
        refersOtherDocument = i.next();
        multipleReferencesDocument = i.next();
        equivalentUseCaseDocument = i.next();
        invertedUseCaseDoc = i.next();
        emptyFlowsDescriptionDoc = i.next();

         long time = System.currentTimeMillis();
        String projectName = "TestCreate_" + time;

        /* Creates the TaRgeT project in Memory */
        ProjectManagerController.getInstance().createProject(projectName,
                ProjectManagerTests.OUTPUT_FOLDER);
 
    }

    /**
     * Tests the creation of test suite files and verifies if the the names of the created files are
     * not duplicated.
     * 
     * @throws IOException Thrown if there is any problem in the file creation.
     * @throws FileNotFoundException Thrown if the file to be written cannot be found.
     */
    @Test
    public void generateTestSuiteFile() throws FileNotFoundException, IOException
    {

        TestCaseGeneratorController tcController = TestCaseGeneratorController.getInstance();

        /* Creates multiples empty files */
        for (int i = 0; i < 30; i++)
        {
            File file = tcController.generateTestSuiteFile(TestCaseGenerationTests.OUTPUT_FOLDER);

            if (file.exists())
                Assert.fail("Duplicate test suite file name '" + file.getAbsolutePath() + "'");

            file.createNewFile();

            /* Verifies if file could be created */
            Assert.assertTrue("File " + file.getAbsolutePath() + "could not be created.", file
                    .exists());

        }
    }

    /**
     * Tests the generation of test cases for all scenarios from a document with a huge number of
     * use cases (<code>TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT</code>).
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void generateAllTestsForHugeDocument() throws TestGenerationException
    {

        /* The input use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(bigUseCaseDocument);
        TestSuite<TextualTestCase> ts = getTestSuite(docs);
        /*
         * Assert if the number of generated tests it the expected number.
         */
        Assert.assertEquals(
                "Number of generated test cases is not the expected for the input document '"
                        + TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT + "'", 104, new Integer(ts
                        .getTestCases().size()));

        /* Verifies if all test case fields are correct initialized */
        for (Iterator<TextualTestCase> iter = ts.getTestCases().iterator(); iter.hasNext();)
        {
            TextualTestCase tc = (TextualTestCase) iter.next();
            verifyTestFields(true, tc);
        }

    }

    /**
     * Tests the generation of test cases for all scenarios from the use case documents
     * <code>TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC</code> and
     * <code>TestCaseGenerationTests.REFERS_OTHER_DOC_UC_DOC</code>. The first document has all
     * the flows associated with at least one requirement. The second document refers to the first
     * document and its last flow is not associated with any requirement (so the last test of the
     * test suite does not have any associated requirement).
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void generateAllTestsForTwoDocuments() throws TestGenerationException
    {
        /* The input use case documents */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs = new ArrayList<PhoneDocument>();
        docs.add(allFlowsWithReqDoc);
        docs.add(refersOtherDocument);

        TestSuite<TextualTestCase> ts = getTestSuite(docs);

        /* Verifies if the the number of generated tests it the expected number. */
        Assert.assertEquals(
                "Number of generated test cases is not the expected for the documents '"
                        + TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC + "' and '"
                        + TestCaseGenerationTests.REFERS_OTHER_DOC_UC_DOC + "'", new Integer(7),
                new Integer(ts.getTestCases().size()));

        /*
         * Verifies that all tests but one have related requirements.
         */
        int missingRequirements = 0;
        for (TextualTestCase tc : ts.getTestCases())
        {
        	
            if (!tc.getRequirements().equals("None."))
            {
                verifyTestFields(true, tc);
            }
            else
            {
                missingRequirements++;
            }
        }
        Assert.assertEquals("The number of test cases with empty requirements is wrong.", 1,
                missingRequirements);
    }

    /**
     * Tests the test generation for all scenarios from the document
     * <code>TestCaseGenerationTests.EMPTY_FLOW_DESC_UC_DOC</code> that does not have flow
     * descriptions. As a consequence, all the generated tests will have an empty objective field.
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void generateTestForEmptyFlowDescDocument() throws TestGenerationException
    {

        /* The input use case documents */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(emptyFlowsDescriptionDoc);

        TestSuite<TextualTestCase> ts = getTestSuite(docs);

        /* Verifies if the the number of generated tests it the expected number */
        Assert.assertEquals("Number of generated test cases is not the expected for the document '"
                + TestCaseGenerationTests.EMPTY_FLOW_DESC_UC_DOC + "'", new Integer(5),
                new Integer(ts.getTestCases().size()));

        /*
         * Verifies if the generated tests have empty objectives and all other fields are correctly
         * filled
         */
        for (TextualTestCase tc : ts.getTestCases())
        {
            Assert.assertTrue("The test objective field must not be empty.", tc.getObjective()
                    .length() > 0);
            verifyTestFields(false, tc);
        }

    }

    /**
     * Verifies the generation of test cases for scenarios related to specific requirements from a
     * use case document with a huge number of use cases (<code>TestCaseGenerationTests.HUGE_USE_CASE_DOCUMENT</code>).
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void generateTestsFromRequirementsForHugeDocument() throws TestGenerationException
    {

        /* List of tuples to be tested */
        List<ReqTestsTuple> reqTests = new ArrayList<ReqTestsTuple>();

        String[] s1 = { "FR_TARGET_0015", "FR_TARGET_0110", "FR_TARGET_0100", "FR_TARGET_0010" };
        ReqTestsTuple rtt = new ReqTestsTuple(s1, 38);
        reqTests.add(rtt);

        String[] s2 = { "FR_TARGET_0100" };
        rtt = new ReqTestsTuple(s2, 23);
        reqTests.add(rtt);

        String[] s3 = { "FR_TARGET_0205" };
        rtt = new ReqTestsTuple(s3, 1);
        reqTests.add(rtt);

        /*
         * Requirement ids that are not present in the input use case document. No generated tests
         * are supposed to be associated with them.
         */
        String[] s4 = { "FR", "TARGET", "0015", "FR_TARGET_ZZZZ", "ANYTHING" };
        rtt = new ReqTestsTuple(s4, 0);
        reqTests.add(rtt);

        /* Sets the input use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(bigUseCaseDocument);

        /* Set the LTS model */
        LTS<FlowStep, Integer> lts = generateLTSFromDocuments(docs);

        /* Generates and verifies the expected number of test for each tuple */
        for (ReqTestsTuple tuple : reqTests)
        {
            generateAndVerifyTestsForTuple(docs, tuple, lts);
        }

    }

    /**
     * Tests the test case generation from two use case documents (<code>TestCaseGenerationTests.ALL_FLOWS_WITH_REQID_UC_DOC</code>
     * and <code>TestCaseGenerationTests.REFERS_OTHER_DOC_UC_DOC</code>) for scenarios related to
     * specific requirements.
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void generateTestsFromRequirementsForTwoDocuments() throws TestGenerationException
    {

        /* List of tuples to be tested */
        List<ReqTestsTuple> reqTests = new ArrayList<ReqTestsTuple>();

        String[] s1 = { "TRS_111166_102", "TRS_111166_103", "TRS_11111_101", "TRS_11111_104" };
        ReqTestsTuple rtt = new ReqTestsTuple(s1, 6);
        reqTests.add(rtt);

        String[] s2 = { "TRS_111166_103" };
        rtt = new ReqTestsTuple(s2, 2);
        reqTests.add(rtt);

        String[] s3 = { "TRS_111166_102" };
        rtt = new ReqTestsTuple(s3, 2);
        reqTests.add(rtt);

        String[] s4 = { "TRS_11111_104" };
        rtt = new ReqTestsTuple(s4, 2);
        reqTests.add(rtt);

        String[] s5 = { "TRS_11111_101" };
        rtt = new ReqTestsTuple(s5, 4);
        reqTests.add(rtt);

        String[] s6 = { "TRS_22222_002" };
        rtt = new ReqTestsTuple(s6, 1);
        reqTests.add(rtt);

        /*
         * Requirement ids that are not present in the input use case document. No generated tests
         * are supposed to be associated with them.
         */
        String[] s7 = { "FR", "TARGET", "102", "FR_TARGET_ZZZZ", "ANYTHING" };
        rtt = new ReqTestsTuple(s7, 0);
        reqTests.add(rtt);

        String[] s8 = { "TRS_111166_102", "TRS_111166_103", "TRS_11111_101", "TRS_11111_104",
                "TRS_22222_002" };
        rtt = new ReqTestsTuple(s8, 6);
        reqTests.add(rtt);

        /* Set the input use case documents used for test generation */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(allFlowsWithReqDoc);
        docs.add(refersOtherDocument);

        /* Sets the LTS model for test generation */
        LTS<FlowStep, Integer> lts = generateLTSFromDocuments(docs);

        /* Generates and verifies the expected number of test for each tuple */
        for (ReqTestsTuple tuple : reqTests)
        {
            generateAndVerifyTestsForTuple(docs, tuple, lts);
        }

    }

    /**
     * Compares two identical test suites that are generated from syntactically different documents
     * that are semantically equivalent (<code>TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC</code>
     * and <code>TestCaseGenerationTests.EQUIVALENT_UC_DOC</code>).
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void generateIdenticalTestSuitesFromSyntaticDifferentDocs()
            throws TestGenerationException
    {
        /* Sets the first input use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(multipleReferencesDocument);

        TestSuite<TextualTestCase> ts1 = getTestSuite(docs);

        /* Verifies the expected number of tests for the first document */
        Assert.assertEquals("Number of generated test cases is not the expected for the document '"
                + TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC + "'", new Integer(5),
                new Integer(ts1.getTestCases().size()));

        /* Sets the second input use case document */
        ArrayList<PhoneDocument> docs2 = new ArrayList<PhoneDocument>();
        docs2 = new ArrayList<PhoneDocument>();
        docs2.add(equivalentUseCaseDocument);

        TestSuite<TextualTestCase> ts2 = getTestSuite(docs2);

        /* Verify if the two test suites are identical */
        compareTestSuites(ts1, ts2);
    }

    /**
     * Compares the test suites generated from the documents
     * <code>TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC</code> and
     * <code>TestCaseGenerationTests.EQUIVALENT_UC_DOC</code>. The second document generates the
     * same tests from the first document but in the inverse order.
     * 
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    @Test
    public void generateTestsInInvertedOrder() throws TestGenerationException
    {

        /* Sets the first use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(equivalentUseCaseDocument);

        TestSuite<TextualTestCase> ts1 = getTestSuite(docs);

        Assert.assertEquals("Number of generated test cases is not the expected for the document '"
                + TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC + "'", new Integer(5),
                new Integer(ts1.getTestCases().size()));

        /* Sets the second use case document */
        docs = new ArrayList<PhoneDocument>();
        docs.add(invertedUseCaseDoc);

        TestSuite<TextualTestCase> ts2 = getTestSuite(docs);

        /* Inverted test case list from the second generated test suite */
        List<TextualTestCase> invertedList = new ArrayList<TextualTestCase>();

        /* Initializes the inverted list */
        for (int i = ts2.getTestCases().size() - 1; i >= 0; i--)
        {
            invertedList.add(ts2.getTestCases().get(i));
        }

        /* Test suite generated from the inverted list of tests */
        TestSuite<TextualTestCase> invertedTs2 = new TestSuite<TextualTestCase>(invertedList);

        /* Compares the reference test suite with the inverted list from the second document */
        this.compareTestSuites(ts1, invertedTs2);

    }

    /**
     * Generate a test suite using <code>LTSModelPrunerAssembler</code> and
     * <code>TestSuiteFilterAssembler</code> given some documents.
     * 
     * @param docs The test suite will be generated from these documents.
     * @return A test suite.
     * @throws TestGenerationException It is thrown in case of any problem during the test
     * generation.
     */
    public static TestSuite<TextualTestCase> getTestSuite(ArrayList<PhoneDocument> docs)
            throws TestGenerationException
    {
        LTS<FlowStep, Integer> lts = generateLTSFromDocuments(docs);
        
        TestSuiteFilterAssembler testSuiteFilterAssembler = new TestSuiteFilterAssembler();
        // INSPECT
        return TestCaseGeneratorController.getInstance().generateTests(testSuiteFilterAssembler,
                lts, docs);
    }

    /**
     * Generates an LTS model from the given use case documents and returns it.
     * 
     * @param docs The input use case documents.
     * @return The generated LTS Model
     */
    public static LTS<FlowStep, Integer> generateLTSFromDocuments(ArrayList<PhoneDocument> docs)
    {
        UserViewLTSGenerator ltsGenerator = new UserViewLTSGenerator();
        return ltsGenerator.generateLTS(docs);
    }

    /**
     * Asserts if the test cases fields are according to the following constraints:
     * <p>
     * <ul>
     * <li> All fields are different from null
     * <li> Test string Id is not empty and contains the substring "_"
     * <li> The test objective is not empty, contains the substring "." and does not contain the
     * substring ".."
     * <li> Requirements and initial conditions fields's sets that have at least one element
     * </ul>
     * 
     * @param tc The test case to be analyzed.
     * @param verifyObjective When its value is true, does verify the objective field. Otherwise,
     * ignores its value.
     */
    private void verifyTestFields(boolean verifyObjective, TextualTestCase tc)
    {
        /* Verifies if the objects are initialized */
        Assert.assertNotNull("Test case object is null", tc);

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' STR_ID field is not correct initialized", tc.getStrId());

        if (verifyObjective)
        {
            Assert.assertNotNull("Test case '" + tc.getStrId()
                    + "' OBJECTIVE is not correct initialized", tc.getObjective());
        }

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' REQUIREMENTS field is not correct initialized", tc.getRequirements());

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' SETUP field is not correct initialized", tc.getSetups());

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' INITIAL CONDITIONS field is not correct initialized", tc
                .getInitialConditions());

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' NOTE field is not correct initialized", tc.getNote());

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' STEPS field is not correct initialized", tc.getSteps());

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' FINAL CONDITIONS field is not correct initialized", tc.getFinalConditions());

        Assert.assertNotNull("Test case '" + tc.getStrId()
                + "' CLEANUP field is not correct initialized", tc.getCleanup());

        /* Verifies if the fields have valid values */
        if (tc.getStrId().length() == 0 || !tc.getStrId().contains("_"))
        {
            Assert.fail("Test case ID is invalid: + '" + tc.getStrId() + "'");
        }

        if (verifyObjective
                && (tc.getObjective().length() == 0 || !tc.getObjective().contains(".") || tc
                        .getObjective().contains("..")))
        {

            Assert.fail("Test case '" + tc.getStrId() + "' OBJECTIVE is invalid: '"
                    + tc.getObjective() + "'");
        }

        Assert.assertFalse("Test case '" + tc.getStrId() + "' REQUIREMENTS is invalid: '"
                + tc.getRequirements() + "'", tc.getRequirements().isEmpty());

        Assert.assertFalse("Test case '" + tc.getStrId() + "' INITIAL CONDITIONS is invalid: '"
                + tc.getInitialConditions() + "'", tc.getInitialConditions().isEmpty());

    }

    /**
     * Private class that encapsulates a tuple. This tuple keeps an array of requirements as the
     * first value and the number of tests related to the requirements as the second value.
     */
    private class ReqTestsTuple
    {

        /** Array of requirements ids */
        String[] reqs = null;

        /** Number of test cases associated with the requirements */
        int relatedTests = 0;

        /**
         * Constructor that initializes the tuple.
         * 
         * @param reqs The requirements array.
         * @param relatedTests The number of related tests.
         */
        public ReqTestsTuple(String[] reqs, int relatedTests)
        {
            this.reqs = reqs;
            this.relatedTests = relatedTests;
        }

        /**
         * Returns the set of requirements from the tuple requirements' array.
         * 
         * @return The set of the requirements from the tuple.
         */
        public Set<String> getReqsSet()
        {
            Set<String> reqSet = new HashSet<String>();
            for (int i = 0; i < this.reqs.length; i++)
            {
                reqSet.add(this.reqs[i]);
            }
            return reqSet;
        }

        /**
         * Returns the string representation of the tuple.
         * 
         * @return The string representation.
         */
        public String toString()
        {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < reqs.length; i++)
            {
                sb.append(reqs[i]);
                sb.append(", ");
            }
            return sb.toString();
        }

    }

    /**
     * Generates and verifies the expected number of tests for the requirements of the given tuple.
     * 
     * @param docs The input documents for test generation.
     * @param tuple The tuple that specifies the requirements and the number of associated tests.
     * @param lts The input model from which the tests will be extracted.
     * @throws TestGenerationException Thrown if a problem occurs in the test generation.
     */
    private void generateAndVerifyTestsForTuple(ArrayList<PhoneDocument> docs, ReqTestsTuple tuple,
            LTS<FlowStep, Integer> lts) throws TestGenerationException
    {
        /* Generates tests for the set of requirements */
        Set<String> tupleReqSet = tuple.getReqsSet();
        TestSuiteFilterAssembler testSuiteFilterAssembler = new TestSuiteFilterAssembler();
        testSuiteFilterAssembler.setRequirementsFilter(tupleReqSet);
        
        TestSuite<TextualTestCase> ts = TestCaseGeneratorController.getInstance().generateTests(
                testSuiteFilterAssembler, lts, docs);

        /* Verifies the number of tests generated for the set of requirements */
        Assert.assertEquals(
                "Number of generated test cases is not the expected for the requirements '"
                        + tuple.toString() + "'", new Integer(tuple.relatedTests), new Integer(ts
                        .getTestCases().size()));

        /* Verifies if the generated tests cover at least one of the requirements from the tuple */
        for (TextualTestCase tc : ts.getTestCases())
        {
            boolean found = false;
            /*
             * It is not the most efficient way to verify it, but it covers the call to
             * hasRequirement method
             */
            for (Iterator<String> iter = tupleReqSet.iterator(); iter.hasNext() && !found;)
            {
                String req = (String) iter.next();
               //INSPECT
                if (tc.coversRequirement(req))
                {
                    found = true;
                }
            }

            Assert.assertTrue("The test case '" + tc.getStrId()
                    + "' does not refer to a requirement from '" + tupleReqSet + "'", found);
        }

    }

    /**
     * Compares each test from the first test suite against each test from the second test suite.
     * 
     * @param ts1 Test suite whose tests are the reference for the comparison with the tests from
     * the other test suite.
     * @param ts2 The other test suite to be compared with the reference one.
     */
    private void compareTestSuites(TestSuite<TextualTestCase> ts1, TestSuite<TextualTestCase> ts2)
    {
        /* Verifies if the number of tests is the same for both test suites */
        Assert.assertEquals("Test suites have different number of tests.", new Integer(ts1
                .getTestCases().size()), new Integer(ts2.getTestCases().size()));

        Iterator<TextualTestCase> iter1 = ts1.getTestCases().iterator();

        while (iter1.hasNext())
        {
            TextualTestCase tc1 = (TextualTestCase) iter1.next();
            Assert.assertTrue("Test suites are different.", hasCommonTest(ts2.getTestCases()
                    .iterator(), tc1));
        }
    }

    /**
     * Compares if there is at least one test in <code>iter2</code> that matches <code>tc1</code>.
     * 
     * @param iter2 Iterator over tests.
     * @param tc1 The test to be compared.
     * @return <code>true</code> if there is at least one test in <code>iter2</code> that
     * matches <code>tc1</code> or <code>false</code> otherwise.
     */
    private boolean hasCommonTest(Iterator<TextualTestCase> iter2, TextualTestCase tc1)
    {
        boolean result = false;
        while (iter2.hasNext() && !result)
        {
            boolean equals = true;
            TextualTestCase tc2 = (TextualTestCase) iter2.next();

            //INSPECT 
            equals &= tc1.getExecutionType().equals(tc2.getExecutionType());
            equals &= (tc1.getRegressionLevel().equals(tc2.getRegressionLevel()));
            
            equals &= tc1.getObjective().equals(tc2.getObjective());
            equals &= tc1.getRequirements().equals(tc2.getRequirements());
            equals &= tc1.getSetups().equals(tc2.getSetups());
            equals &= tc1.getInitialConditions().equals(tc2.getInitialConditions());
            equals &= tc1.getNote().equals(tc2.getNote());

            List<TextualStep> l1 = tc1.getSteps();
            List<TextualStep> l2 = tc2.getSteps();

            equals &= (l1.size() == new Integer(l2.size()));
            for (int index = 0; index < l1.size() && index < l2.size(); index++)
            {
                TextualStep s1 = l1.get(index);
                TextualStep s2 = l2.get(index);

                equals &= s1.getAction().equals(s2.getAction());
                equals &= s1.getSystemState().equals(s2.getSystemState());
                equals &= s1.getResponse().equals(s2.getResponse());
            }

            equals &= tc1.getFinalConditions().equals(tc2.getFinalConditions());

            if (equals)
            {
                result = equals;
                break;
            }
        }
        return result;
    }


    /**
     * INSPECT
     * Tests if the generation of test cases by setting UseCasesFilter is correctly.
     */
    @Test
    public void testTestCasesGenerationWithUseCasesCovering()
    {
        try
        {
        	PhoneDocument document = allFlowsWithReqDoc;
        	TestCaseGeneratorController controller = TestCaseGeneratorController.getInstance();
            Collection<PhoneDocument> docs = new ArrayList<PhoneDocument>();
            docs.add(document);
            

            /* the feature and use case of the filter */
            Feature feature = null;
            UseCase usecase = null;
            for (Feature f : document.getFeatures())
            {
                usecase = f.getUseCase("UC_01");
                if (usecase != null)
                {
                    feature = f;
                    break;
                }
            }

            Assert.assertNotNull("Could not load the feature.", feature);
            Assert.assertNotNull("Could not load the usecase.", usecase);

            LTS<FlowStep, Integer> lts = controller.generateLTSModel(docs);


            Assert.assertNotNull("It was not possible to generate the LTS model.", lts);

            TestSuiteFilterAssembler filterAssembler = new TestSuiteFilterAssembler();

            HashMap<Feature, List<UseCase>> mapping = new HashMap<Feature, List<UseCase>>();
            List<UseCase> usecases = new ArrayList<UseCase>();
            usecases.add(usecase);
            mapping.put(feature, usecases);
            filterAssembler.setUseCasesFilter(mapping);

            TestSuite<TextualTestCase> testSuite = controller.generateTests(filterAssembler,
                   lts, docs);

            Assert.assertEquals("Wrong number of test cases.", 4, testSuite.getTestCases().size());

            lts = controller.generateLTSModel(docs);

            testSuite = controller.generateTests(new TestSuiteFilterAssembler(),
                    lts, docs);
            Assert.assertEquals("Wrong number of test cases.", 5, testSuite.getTestCases().size());
        }
        catch (TestGenerationException e)
        {
            e.printStackTrace();
            Assert.fail("It was not possible to generate tests.");
        }
    }
}
