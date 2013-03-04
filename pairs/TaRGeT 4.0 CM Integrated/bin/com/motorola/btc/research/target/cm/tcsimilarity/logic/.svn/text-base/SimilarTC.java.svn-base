/*
 * @(#)SimilarTC.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   08/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.tcsimilarity.logic;

import java.util.ArrayList;
import java.util.List;

import com.motorola.btc.research.target.cm.tcsimilarity.metrics.Metrics;
import com.motorola.btc.research.target.cm.tcsimilarity.metrics.Metrics2;
import com.motorola.btc.research.target.cm.util.ConsistencyManagerUtil;
import com.motorola.btc.research.target.tcg.extractor.TextualStep;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

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

        Metrics2 metrics = new Metrics2();
        List<StepSimilarity[][]> similarity = checkSimilarity(newTestCase, oldTestCasesTable);
        List<TestCaseSimilarity> list = metrics.getTestCaseSimilarities(similarity,
                Metrics.WORD_BY_WORD);

        return ConsistencyManagerUtil.getComparisonResults(list, oldTestCases);
    }

    /**
     * Checks the similarity of a new test case against an old test case map.
     * 
     * @param newTestCase The new test case to check similarity.
     * @param oldTestCasesTable The old test case map.
     * @return A list of step similarity matrices.
     */
    private List<StepSimilarity[][]> checkSimilarity(TextualTestCase newTestCase,
            TestCaseMap oldTestCasesTable)
    {

        List<TextualTestCase> oldTestCases = oldTestCasesTable.getTestCases(newTestCase
                .getUseCaseReferences());

        List<StepSimilarity[][]> matrixList = new ArrayList<StepSimilarity[][]>();

        if (oldTestCases != null && oldTestCases.size() > 0)
        {
            for (TextualTestCase originalTest : oldTestCases)
            {
                List<TextualStep> modifSteps = newTestCase.getSteps();
                List<TextualStep> origSteps = originalTest.getSteps();

                int maxStepsSize = Math.max(modifSteps.size(), origSteps.size());

                StepSimilarity[][] steps;
                steps = checkSteps(modifSteps, origSteps, maxStepsSize, newTestCase.getId(),
                        originalTest.getId(), newTestCase.getUseCaseReferences());

                matrixList.add(steps);
            }
        }

        return matrixList;
    }

    /**
     * Checks the similarity of test steps.
     * 
     * @param modifSteps A list of old test steps.
     * @param origSteps A list of new test steps.
     * @param maxStepsSize The maximum step size.
     * @param modifiedId The id of the new test case.
     * @param originalId The id of the old test case.
     * @param UCId The use case id.
     * @return A step similarity matrix.
     */
    private StepSimilarity[][] checkSteps(List<TextualStep> modifSteps,
            List<TextualStep> origSteps, int maxStepsSize, int modifiedId, int originalId,
            String UCId)
    {

        int j = 0;

        int diff = modifSteps.size() - origSteps.size();

        StepSimilarity[][] steps = null;
        if (diff > 0)
        {
            steps = new StepSimilarity[modifSteps.size()][modifSteps.size()];
        }
        else
        {
            steps = new StepSimilarity[origSteps.size()][origSteps.size()];
        }

        for (TextualStep modifFlowStep : modifSteps)
        {
            int i = 0;
            for (TextualStep origFlowStep : origSteps)
            {
                // cria o objeto similaridade
                StepSimilarity sim = new StepSimilarity(modifFlowStep, origFlowStep, modifiedId,
                        originalId, UCId);
                sim.setOrderDistance(Math.abs(i - j)); // ordem em que os steps
                // estão emparelhados
                sim.setMaxStepsSize(maxStepsSize);
                steps[j][i] = sim;
                i++;
            }
            j++;
        }

        if (diff > 0)
        {
            for (int i = origSteps.size(); i < origSteps.size() + diff; i++)
            {

                for (int k = 0; k < modifSteps.size(); k++)
                {
                    StepSimilarity ghost = new StepSimilarity(null, null, k + 1, i + 1, UCId);
                    ghost.clearStepSimilarity(diff - i - 1, k);
                    ghost.setModifiedId(k + 1 + "");
                    ghost.setOriginalId(i + 1 + "");
                    ghost.setMaxStepsSize(maxStepsSize);
                    ghost.setOrderDistance(k);
                    steps[k][i] = ghost;
                }

            }
        }
        else if (diff < 0)
        {
            int diffAbs = Math.abs(diff);
            for (int i = modifSteps.size(); i < modifSteps.size() + diffAbs; i++)
            {

                for (int k = 0; k < origSteps.size(); k++)
                {
                    StepSimilarity ghost = new StepSimilarity(null, null, k + 1, i + 1, UCId);
                    ghost.clearStepSimilarity(diffAbs - i - 1, k);
                    ghost.setOriginalId(k + 1 + "");
                    ghost.setModifiedId(i + 1 + "");
                    ghost.setMaxStepsSize(maxStepsSize);
                    ghost.setOrderDistance(k);
                    steps[i][k] = ghost;
                }

            }
        }

        return steps;

    }
}
