/*
 * @(#)TestCaseGeneratorController.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Nov 27, 2006    LIBkk11577   Initial creation.
 * wsn013   Jan 09, 2007    LIBkk11577   Refactories.
 * wsn013   Jan 18, 2007    LIBkk11577   Modifications after inspection (LX135035).
 * dhq348   Apr 26, 2007    LIBmm09879   Modifications according to CR.
 * dhq348   Jul 05, 2007    LIBmm76986   Modifications according to CR.
 * dhq348   Jul 19, 2007    LIBmm76986   Rework after inspection LX189921.
 * wdt022   May 31, 2007    LIBmm42774   Modifications due to CR. Controller adapted to new architecture.
 * dhq348   Nov 10, 2007    LIBnn34008   Modifications according to CR.
 * dhq348   Jan 18, 2008    LIBnn34008   Rework after inspection LX229628.
 * wdt022   May 31, 2007    LIBmm42774   Modifications due to CR. Controller adapted to new architecture. 
 * dhq348   Nov 21, 2007    LIBoo10574   It is now searching for the properties file.
 * dhq348   Jan 23, 2008    LIBoo10574   Rework after inspection LX229632.
 * dhq348   Feb 21, 2008    LIBoo89937   Modifications according to CR.
 * wdt022   Mar 25, 2008    LIBpp56482   Pruners removed and writeTestSuiteFile method added.
 * tnd783   Apr 07, 2008    LIBpp71785   Changes in mapToTextualTestCases method to call TextualTestCaseFactory
 * wln013   May 12, 2008    LIBpp56482   Rework after meeting 2 of inspection LX263835.
 * tnd783   Jul 21, 2008    LIBpp71785   Rework after inspection LX285039. 
 * jrm687   Dec 18, 2008    LIBnn34008	 Modifications to add document revision history.
 * fsf2     Apr 01, 2009    			 Modifications due new output plugins.
 * fsf2     Jul 03, 2009                 Changed the test suite file generation.
 * fsf2     Sep 15, 2009                 Changed the generation to use the result file name.
 */
package br.ufpe.cin.target.tcg.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.ufpe.cin.target.tcg.exceptions.TestGenerationException;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentData;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtension;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtensionFactory;
import br.ufpe.cin.target.tcg.extractor.BasicPathFactory;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TestSuiteFactory;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.extractor.TextualTestCaseFactory;
import br.ufpe.cin.target.tcg.filters.TestSuiteFilter;
import br.ufpe.cin.target.tcg.filters.TestSuiteFilterAssembler;

import br.ufpe.cin.target.common.lts.LTS;
import br.ufpe.cin.target.common.lts.UserViewLTSGenerator;
import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;

/**
 * This class encapsulates the tool's logic related to test case generation functionalities.
 * 
 * <pre>
 * CLASS:
 * This class encapsulates the tool's logic related to test case generation functionalities.
 * 
 * RESPONSIBILITIES:
 * 1) Provide services to the GUI regarding test cases generation 
 * 
 * COLABORATORS:
 * 1) Uses userViewDocuments
 * 
 * USAGE:
 * TestCaseGeneratorController testGenController = TestCaseGeneratorController.getInstance();
 * testGenController.generateAllTests(lts);
 */
public class TestCaseGeneratorController
{
    /** Unique instance of this class (singleton) */
    private static TestCaseGeneratorController instance;

    /**
     * The algorithm used to generate the test cases from the use case documents
     */
    private TestSuiteFactory<FlowStep> testFactory;

    /**
     * Number that is used to control creation of test suite file´s names
     */
    private int testSuiteFileCounter;

    private OutputDocumentExtension outputDocumentExtension = null;

    /**
     * Reference to the last generated test suite.
     */
    private List<TextualTestCase> lastSuiteGenerated;

    /**
     * Initializes the controller. Initializes the controller.
     */
    private TestCaseGeneratorController()
    {
        this.testFactory = new BasicPathFactory();
        this.testSuiteFileCounter = 1;
    }

    /**
     * Return a reference to the singleton of this class.
     * 
     * @return The singleton.
     */
    public static TestCaseGeneratorController getInstance()
    {
        if (instance == null)
        {
            instance = new TestCaseGeneratorController();
        }
        return instance;
    }

    /**
     * Generates a LTS model from a collection of use case documents.
     * 
     * @param documents The use case documents.
     * @return The generated LTS.
     */
    public LTS<FlowStep, Integer> generateLTSModel(Collection<UseCaseDocument> documents)
    {
        UserViewLTSGenerator ltsGenerator = new UserViewLTSGenerator();
        LTS<FlowStep, Integer> lts = ltsGenerator.generateLTS(documents);

        return lts;
    }

    /**
     * Generates from the input use case documents (user view), all possible test cases according to
     * the implementation of the abstract test factory.
     * 
     * @param filterAssembler The filter assembler.
     * @param lts The LTS from which the test cases will be extracted
     * @param useCaseDocuments The use case documents used to create the LTS.
     * @return The generated test suite.
     * @throws TestGenerationException It is thrown in case of any problem during the test
     * generation.
     */
    public TestSuite<TextualTestCase> generateTests(TestSuiteFilterAssembler filterAssembler,
            LTS<FlowStep, Integer> lts, Collection<UseCaseDocument> useCaseDocuments)
            throws TestGenerationException
    {
        /* Assembling the pruner and pruning the LTS model */
        /* Raw test cases generated from the factory */

        TestSuite<TestCase<FlowStep>> rawTs = this.testFactory.create(lts);

        /* Assembling filter and filtering the generated test suite */
        TestSuiteFilter<FlowStep> filter = filterAssembler.assemblyFilter();
        TestSuite<TestCase<FlowStep>> filteredTests = filter.filter(rawTs);

        return this.mapToTextualTestCases(filteredTests, useCaseDocuments);
    }

    /**
     * Generates all raw test cases from a LTS model.
     * 
     * @param lts The LTS model.
     * @return All generated raw test cases.
     * @throws TestGenerationException
     */
    public TestSuite<TestCase<FlowStep>> generateRawTests(LTS<FlowStep, Integer> lts)
            throws TestGenerationException
    {
        /* raw test cases generated from the factory */
        return this.testFactory.create(lts);
    }

    /**
     * Method that transforms raw test cases into textual test cases. Method that transforms raw
     * test cases into textual test cases.
     * 
     * @param rawTestCases Raw test cases that will be mapped into textual test cases.
     * @param useCaseDocuments The use case documents from which the test cases were extracted.
     * @return The textual test suite.
     */
    public TestSuite<TextualTestCase> mapToTextualTestCases(
            TestSuite<TestCase<FlowStep>> rawTestCases, Collection<UseCaseDocument> useCaseDocuments)
    {
        /* the test suite that will be returned */
        List<TextualTestCase> textualTcs = new ArrayList<TextualTestCase>();

        /* list of all documents flows */
        List<Flow> documentsFlows = this.getDocumentsFlows(useCaseDocuments);

        /*
         * for each raw test creates a textual test case and includes it in the test suite that will
         * be returned
         */
        for (TestCase<FlowStep> rawTest : rawTestCases.getTestCases())
        {
            /* insert the created test into the test suite */
            TextualTestCaseFactory textualTCF = new TextualTestCaseFactory();
            textualTcs.add(textualTCF.newTextualTestCase(rawTest, documentsFlows));
        }

        return new TestSuite<TextualTestCase>(textualTcs, rawTestCases.getName());
    }

    /**
     * Returns all the flows from the input documents.
     * 
     * @param userViewDocuments The use case documents.
     * @return The flows from the documents.
     */
    private List<Flow> getDocumentsFlows(Collection<UseCaseDocument> userViewDocuments)
    {
        List<Flow> documentsFlows = new ArrayList<Flow>();

        // for each document
        for (UseCaseDocument phoneDoc : userViewDocuments)
        {
            // for each feature of a document
            for (Feature feature : phoneDoc.getFeatures())
            {
                // for each use case of a feature
                for (UseCase useCase : feature.getUseCases())

                    // for each flow of a use case
                    for (Flow flow : useCase.getFlows())
                    {
                        documentsFlows.add(flow);
                    }
            }
        }

        return documentsFlows;
    }

    /**
     * Creates a new test suite file into the test suites' directory and writes the test suite data.
     * 
     * @param testSuite The test suite whose data will be written into the file.
     * @return The create file instance.
     * @throws IOException In case of any error writing the file.
     */
    // INSPECT Felype - Alterei para receber tests diretemante.
    public File writeTestSuiteFile(List<TextualTestCase> tests) throws IOException
    {
        if (this.outputDocumentExtension == null)
        {
            this.initOutputExtensionsList();
        }

        this.outputDocumentExtension.setTestCases(tests);

        String testSuiteDir = ProjectManagerExternalFacade.getInstance().getCurrentProject()
                .getTestSuiteDir();

        File result = this.generateTestSuiteFile(testSuiteDir);

        result = this.outputDocumentExtension.writeTestCaseDataInFile(result);

        return result;
    }

    /**
     * Retrieves the available output extensions.
     */
    public void initOutputExtensionsList()
    {
        Collection<OutputDocumentData> outputExtensionsList = OutputDocumentExtensionFactory
                .outputExtensions();

        for (OutputDocumentData outputDocumentData : outputExtensionsList)
        {
            outputDocumentExtension = outputDocumentData.getOutputDocumentExtension();
        }
    }

    /**
     * Generates a valid test suite file. The name of the test is generated according to the current
     * value of <code>testSuiteFileCounter</code> attribute.
     * 
     * @param testSuiteDir The directory where the test suite will be created.
     * @return A valid test suite file.
     */
    public File generateTestSuiteFile(String testSuiteDir)
    {
        DecimalFormat df = new DecimalFormat("0000");

        testSuiteDir += FileUtil.getSeparator();

        if (this.outputDocumentExtension == null)
        {
            this.initOutputExtensionsList();
        }

        File file = new File(testSuiteDir + "NewTestSuite" + df.format(this.testSuiteFileCounter)
                + this.outputDocumentExtension.getExtensionFile());

        // look for a test suite name that is different from the existent ones
        while (file.exists())
        {
            file = new File(testSuiteDir + "NewTestSuite" + df.format(++this.testSuiteFileCounter)
                    + this.outputDocumentExtension.getExtensionFile());
        }

        return file;
    }

    public OutputDocumentExtension getOutputDocumentExtension()
    {
        return outputDocumentExtension;
    }

    public void setOutputDocumentExtension(OutputDocumentExtension outputDocumentExtension)
    {
        this.outputDocumentExtension = outputDocumentExtension;
    }

    /**
     * Comment for method.
     * 
     * @param textualTestCases
     */
    public void setLastSuiteGenerated(List<TextualTestCase> textualTestCases)
    {
        this.lastSuiteGenerated = textualTestCases;
    }

    /**
     * Gets the lastSuiteGenerated value.
     * 
     * @return Returns the lastSuiteGenerated.
     */
    public List<TextualTestCase> getLastSuiteGenerated()
    {
        return lastSuiteGenerated;
    }
}
