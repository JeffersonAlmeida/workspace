/*
 * @(#)SimilarTC.java
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
 * faas      08/07/2008                  Initial creation.
 */
package br.ufpe.cin.target.cm.tcsimilarity.logic;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import br.ufpe.cin.target.cm.tcsimilarity.metrics.Metrics;
import br.ufpe.cin.target.cm.tcsimilarity.metrics.Metrics2;
import br.ufpe.cin.target.cm.util.ConsistencyManagerUtil;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * Represents the logic of the similar TC functionality. RESPONSIBILITIES: 1) Provides comparison of
 * test cases.
 */
public class SimilarTC
{

    /**
     * Compares a new test case with a list of old test cases.
     * 
     * @param newTestCase The new test case to compare.
     * @param oldTestCases The list of old test cases to compare.
     * @return A list of comparison results.
     */
    public List<ComparisonResult> compare(TextualTestCase newTestCase,
            List<TextualTestCase> oldTestCases)
    {
        TestCaseMap oldTestCasesTable = new TestCaseMap(oldTestCases);

        //INSPECT Felype Alterei pra remover o estouro do heap.
   //     Metrics2 metrics = new Metrics2();
   //     List<StepSimilarity[][]> similarity = checkSimilarity(newTestCase, oldTestCasesTable);
   //     List<TestCaseSimilarity> list = metrics.getTestCaseSimilarities(similarity,
   //             Metrics.WORD_BY_WORD);
        
        

        List<TestCaseSimilarity> list = checkSimilarity(newTestCase, oldTestCasesTable, Metrics.WORD_BY_WORD);

        return ConsistencyManagerUtil.getComparisonResults(list, oldTestCases);
    }

    /**
     * Checks the similarity of a new test case against an old test case map.
     * 
     * @param newTestCase The new test case to check similarity.
     * @param oldTestCasesTable The old test case map.
     * @return A list of step similarity matrices.
     */
    private List<TestCaseSimilarity> checkSimilarity(TextualTestCase newTestCase,
            TestCaseMap oldTestCasesTable, String method)
            {

        List<TextualTestCase> oldTestCases = oldTestCasesTable.getTestCases(newTestCase
                .getUseCaseReferences());

        Metrics2 metrics = new Metrics2();

        List<TestCaseSimilarity> listTestCaseSimilarity = new Vector<TestCaseSimilarity>();

        if (oldTestCases != null && oldTestCases.size() > 0)
        {
            //INSPECT
            List<TextualStep> newTestCaseSteps = null;
            List<TextualStep> oldTestCaseSteps = null;
            StepSimilarity[][] steps = null;
            int[][] resultTemp = null;

            for (TextualTestCase oldTestCase : oldTestCases)
            {
                newTestCaseSteps = newTestCase.getSteps();
                oldTestCaseSteps = oldTestCase.getSteps();

                int maxStepsSize = Math.max(newTestCaseSteps.size(), oldTestCaseSteps.size());

                steps = checkSteps(newTestCaseSteps, oldTestCaseSteps, maxStepsSize, newTestCase.getId(),
                        oldTestCase.getId(), newTestCase.getUseCaseReferences());

                TestCaseSimilarity result = null;

                StepSimilarity temp = steps[0][0];
                resultTemp = metrics.getPairs(steps);
                double similarity = metrics.getResultantSimilarity(steps, resultTemp, method);
                result = new TestCaseSimilarity(temp.getNewTestCaseId(), temp.getOldTestCaseId(), similarity, temp
                        .getUseCaseId(), resultTemp);

                steps = null;
                temp = null;

                listTestCaseSimilarity.add(result);
            }
        }

        Collections.sort(listTestCaseSimilarity);

        return listTestCaseSimilarity;
            }

    /**
     * Checks the similarity of test steps.
     * 
     * @param newTestCaseSteps A list of old test steps.
     * @param oldTestCaseSteps A list of new test steps.
     * @param maxStepsSize The maximum step size.
     * @param newTestCaseId The id of the new test case.
     * @param oldTestCaseId The id of the old test case.
     * @param useCaseId The use case id.
     * @return A step similarity matrix.
     */
    //INSPECT
    private StepSimilarity[][] checkSteps(List<TextualStep> newTestCaseSteps,
            List<TextualStep> oldTestCaseSteps, int maxStepsSize, int newTestCaseId, int oldTestCaseId,
            String useCaseId)
    {
        int newStepIndex = 0; // iterates over new steps

        int diff = newTestCaseSteps.size() - oldTestCaseSteps.size();

        StepSimilarity[][] steps = null;
        
        //when newTestCaseSteps.size() > oldTestCaseSteps.size()
        if (diff > 0)
        {
            steps = new StepSimilarity[newTestCaseSteps.size()][newTestCaseSteps.size()];
        }
        //when newTestCaseSteps.size() < oldTestCaseSteps.size()
        else
        {
            steps = new StepSimilarity[oldTestCaseSteps.size()][oldTestCaseSteps.size()];
        }

        for (TextualStep newTestCaseStep : newTestCaseSteps)
        {
            int oldStepIndex = 0;// iterates over old steps
            for (TextualStep oldTestCaseStep : oldTestCaseSteps)
            {
                // creates the similarity object
                StepSimilarity sim = new StepSimilarity(newTestCaseStep, oldTestCaseStep, newTestCaseId,
                        oldTestCaseId, useCaseId);
                sim.setOrderDistance(Math.abs(oldStepIndex - newStepIndex)); // order that steps are grouped
                sim.setMaxStepsSize(maxStepsSize);
                steps[newStepIndex][oldStepIndex] = sim;
                oldStepIndex++;
            }
            newStepIndex++;
        }
        
      //when newTestCaseSteps.size() > oldTestCaseSteps.size()
        if (diff > 0)
        {
            for (int i = oldTestCaseSteps.size(); i < oldTestCaseSteps.size() + diff; i++)
            {
                for (int k = 0; k < newTestCaseSteps.size(); k++)
                {
                    StepSimilarity ghost = new StepSimilarity(null, null, k + 1, i + 1, useCaseId);
                    ghost.clearStepSimilarity(diff - i - 1, k);
                    ghost.setNewTestCaseId(k + 1 + "");
                    ghost.setOldTestCaseId(i + 1 + "");
                    ghost.setMaxStepsSize(maxStepsSize);
                    ghost.setOrderDistance(k);
                    steps[k][i] = ghost;
                }
            }
        }
        //when newTestCaseSteps.size() < oldTestCaseSteps.size()
        else if (diff < 0)
        {
            int diffAbs = Math.abs(diff);
            for (int i = newTestCaseSteps.size(); i < newTestCaseSteps.size() + diffAbs; i++)
            {

                for (int k = 0; k < oldTestCaseSteps.size(); k++)
                {
                    StepSimilarity ghost = new StepSimilarity(null, null, k + 1, i + 1, useCaseId);
                    ghost.clearStepSimilarity(diffAbs - i - 1, k);
                    ghost.setOldTestCaseId(k + 1 + "");
                    ghost.setNewTestCaseId(i + 1 + "");
                    ghost.setMaxStepsSize(maxStepsSize);
                    ghost.setOrderDistance(k);
                    steps[i][k] = ghost;
                }

            }
        }

        return steps;

    }
}
