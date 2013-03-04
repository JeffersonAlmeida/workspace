/*
 * @(#)BasicPathFactory.java
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
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2006   LIBkk11577   Modifications after inspection (LX135035).
 * dhq348    Apr 26, 2007   LIBmm09879   Modifications according to CR.
 * wdt022    May 31, 2007   LIBmm42774   Modifications according to CR.
 * dhq348    Nov 07, 2007   LIBnn34008   Refactored code.
 * dhq348    Jan 14, 2008   LIBnn34008   Rework after inspection LX229625.
 */
package br.ufpe.cin.target.tcg.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import br.ufpe.cin.target.common.lts.LTS;
import br.ufpe.cin.target.common.lts.State;
import br.ufpe.cin.target.common.lts.Transition;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.tcg.exceptions.TestGenerationException;


/**
 * This class implements the Basic Path algorithm to generate a test suite with objects of type
 * TestCase<TVALUE> from an input LTS<TVALUE,SVALUE>.
 * 
 * <pre>
 * CLASS:
 * This class implements the Basic Path algorithm 
 * (https://compass.motorola.com/cgi/go/196963821, see chapter 3) to create 
 * a test suite from a input LTS generated from use case documents (user view).
 * 
 * RESPONSIBILITIES:
 * 1) Run the LTS in depth and store the walked transitions
 * 2) When the algorithm reach the root state or a terminal state, creates a test case 
 * and store it in the test suite that will be returned
 * 3) When there are no more transition to explore in some state, stop running
 *
 * COLABORATORS:
 * 1) Uses LTS
 * 2) Uses TestCase
 * 3) Uses TestSuite
 *
 * USAGE:
 * TestSuiteFactory testFactory = new BasicPathFactory<FlowStep, String>();
 */
public class BasicPathFactory implements TestSuiteFactory<FlowStep>
{

    /** Incremental test case id */
    private int incrementalTestCaseId;

    /**
     * Creates the test suite following the Basic Path algorithm from Emannuela Gadelha.
     */
    public TestSuite<TestCase<FlowStep>> create(LTS<FlowStep, Integer> lts)
            throws TestGenerationException
    {
        /* check if the input LTS is valid */
        if (lts == null)
            throw new TestGenerationException("Test generation error",
                    "LTS must be set before the test suite creation.");

        /* reset the incremental id for test cases */
        incrementalTestCaseId = 1;

        /* the list that will keep the generated test cases */
        List<TestCase<FlowStep>> testCases = new ArrayList<TestCase<FlowStep>>();

        /* calls the method that will walk through the LTS to generate the tests */
        walk(testCases, new Stack<Transition<FlowStep, Integer>>(), lts.getRoot(), lts);

        /* update the test suite with the generated tests */
        return new TestSuite<TestCase<FlowStep>>(new ArrayList<TestCase<FlowStep>>(testCases));
    }

    /**
     * This method walks through the LTS graph in the depth in order to extract different scenarios
     * that will constitute each of the raw test cases of the test suite returned by the method. The
     * tests are denoted as raw because they will be post processed in order to be useful for test
     * execution purposes.
     * 
     * @param testSuite The test suite that contains the generated test cases.
     * @param walkedPath The graph transitions that were still covered by the method.
     * @param currentState The current state being walked by the method.
     * @param lts The LTS model from which the test cases will be extracted.
     */
    protected void walk(List<TestCase<FlowStep>> testSuite,
            Stack<Transition<FlowStep, Integer>> walkedPath, State<Integer, FlowStep> currentState,
            LTS<FlowStep, Integer> lts)
    {
        /* the number of out transitions of the current state */
        int currentStateOutTransitions = currentState.getOutTransitionsSize();

        /* if the method is returned back to the root state */
        boolean reachedRoot = lts.getRoot().getId() == currentState.getId()
                && walkedPath.size() > 0;

        /* current state is terminal state (no transitions and no branches) or reached the root */
        if (currentStateOutTransitions == 0 || reachedRoot)
        {
            /* create a test case with the walkedPath in the test suite */
            TestCase<FlowStep> tc = new TestCase<FlowStep>(getNextTestCaseId(),
                    getTransitionsValues(walkedPath));
            testSuite.add(tc);
        }
        // state has at least one out transition
        else
        {
            /*
             * the transitions that were not walked in the previous time that the method walked
             * through the current branch state
             */
            List<Transition<FlowStep, Integer>> transitionsToWalk = getTransitionsToExplore(
                    currentState, walkedPath);
            walkOutTransitions(testSuite, walkedPath, currentState, transitionsToWalk, lts);
        }// end of if(curOut == 0 || curIsRoot )...
    }

    /**
     * @return The next test case id.
     */
    private int getNextTestCaseId()
    {
        return incrementalTestCaseId++;
    }

    /**
     * For each of the given transitions to walk, calls the walk method passing the incremented
     * path.
     * 
     * @param testSuite The test suite that contains the generated test cases.
     * @param walkedPath The graph transitions that were still covered by the method.
     * @param currentState The current state being walked by the method.
     * @param transitionsToWalk The transitions that will considered in the invocation of walk
     * method.
     * @param lts The LTS model from which the test cases will be extracted.
     */
    protected void walkOutTransitions(List<TestCase<FlowStep>> testSuite,
            Stack<Transition<FlowStep, Integer>> walkedPath, State<Integer, FlowStep> currentState,
            List<Transition<FlowStep, Integer>> transitionsToWalk, LTS<FlowStep, Integer> lts)
    {

        /* for each of the out transitions of the current state */
        for (Transition<FlowStep, Integer> outTrans : transitionsToWalk)
        {

            /* adds the transition to the incremented path */
            // incrementedPath = new Stack<Transition<FlowStep, Integer>>(walkedPath);
            /*
             * calls the method to walk in depth using the destination state of the out transition
             * and the incremented path
             */
            walkedPath.push(outTrans);
            walk(testSuite, walkedPath, outTrans.getTo(), lts);
            walkedPath.pop();
        }
    }

    /**
     * Retrieve the transitions from the given state that were not yet explored.
     * 
     * @param state The state which transitions will be analyzed.
     * @param walkedTransitions The transitions that were yet explored by the test generation.
     * @return The transitions from the state that were not explored.
     */
    private List<Transition<FlowStep, Integer>> getTransitionsToExplore(
            State<Integer, FlowStep> state, List<Transition<FlowStep, Integer>> walkedTransitions)
    {

        ArrayList<Transition<FlowStep, Integer>> toExplore = new ArrayList<Transition<FlowStep, Integer>>();

        for (Transition<FlowStep, Integer> outTrans : state.getOutTransitions())
        {
            if (!walkedTransitions.contains(outTrans))
            {
                toExplore.add(outTrans);
            }
        }

        return toExplore;

    }

    /**
     * Retrieves the values from the given transitions.
     * 
     * @param transitions The transitions from where their values will be retrieved.
     * @return The values from the given transitions.
     */
    private List<FlowStep> getTransitionsValues(List<Transition<FlowStep, Integer>> transitions)
    {
        List<FlowStep> values = new ArrayList<FlowStep>(transitions.size());

        for (Transition<FlowStep, Integer> t : transitions)
        {
            if (!t.getValue().getId().equals(StepId.END))
            {
                values.add(new TCGFlowStep(t));
            }
        }

        return values;
    }
}
