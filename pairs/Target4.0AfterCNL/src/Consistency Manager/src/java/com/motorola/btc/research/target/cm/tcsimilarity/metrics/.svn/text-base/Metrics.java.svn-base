package com.motorola.btc.research.target.cm.tcsimilarity.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.motorola.btc.research.target.cm.tcsimilarity.logic.StepSimilarity;
import com.motorola.btc.research.target.cm.tcsimilarity.logic.TestCaseSimilarity;

public class Metrics
{

    public static String WORD_BY_WORD = "WORD BY WORD";

    public static String CHAR_BY_CHAR = "CHAR BY CHAR";

    // GAU
    public List<TestCaseSimilarity> getTestCaseSimilarities(List<StepSimilarity[][]> similarity,
            String metric)
    {

        List<TestCaseSimilarity> result = new ArrayList<TestCaseSimilarity>();
        for (StepSimilarity[][] stepSimilarityArray : similarity)
        {
            StepSimilarity temp = stepSimilarityArray[0][0];
            int[][] resultTemp = getPairs(stepSimilarityArray);
            double sim = getResultantSimilarity(stepSimilarityArray, resultTemp, metric);
            result.add(new TestCaseSimilarity(temp.getModifiedId(), temp.getOriginalId(), sim, temp
                    .getUCId()));
        }
        Collections.sort(result);
        return result;
    }

    // resultTemp[i][j]; i = line index in the array and j = index of the lowest StepSimilarity in
    // the array
    private double getResultantSimilarity(StepSimilarity[][] similarity, int[][] resultTemp,
            String metric)
    {
        double result = 0;
        int line = 0;
        int column = 0;
        StepSimilarity temp = null;
        for (int i = 0; i < resultTemp.length; i++)
        {
            line = resultTemp[i][0];
            column = resultTemp[i][1];
            temp = similarity[line][column];
            if (metric.equalsIgnoreCase(WORD_BY_WORD))
            {
                result += temp.getSimilarityByWord();
            }
            else if (metric.equalsIgnoreCase(CHAR_BY_CHAR))
            {
                result += temp.getSimilarityByChar();
            }
        }
        return result / resultTemp.length;
    }

    private int[][] getPairs(StepSimilarity[][] stepSimilarityArray)
    {
        int arraySize = stepSimilarityArray.length;
        int[][] resultTemp = new int[arraySize][2]; // resultTemp[i][j]; i = line index in the array
                                                    // and j = index of the lowest StepSimilarity in
                                                    // the array
        for (int i = 0; i < arraySize; i++)
        {
            StepSimilarity[] stepSimilarityArrayLine = getStepSimilarityArrayLine(
                    stepSimilarityArray, i); // gets one array[][] line
            int lowerSim = getLowestStepSimilarity(stepSimilarityArrayLine); // gets the index of
                                                                                // the lowest
                                                                                // StepSimilarity in
                                                                                // the array
            stepSimilarityArray[i][lowerSim].setAlreadyVisited(true);
            resultTemp[i][0] = i;
            resultTemp[i][1] = lowerSim;
            int checkDupMatch = checkDuplicatedMatch(resultTemp, i); // checks if the resultTemp
                                                                        // already has the lowest
                                                                        // StepSimilarity index
            if (checkDupMatch != -1)
            {
                while (checkDupMatch != -1)
                {
                    int col = resultTemp[checkDupMatch][1];
                    StepSimilarity dup = stepSimilarityArray[checkDupMatch][col];
                    StepSimilarity lowestStepSimilarity = stepSimilarityArrayLine[lowerSim];
                    if (dup.hasLowerSimilarityThan(lowestStepSimilarity))
                    {
                        lowerSim = getLowestStepSimilarity(stepSimilarityArrayLine);
                        stepSimilarityArray[i][lowerSim].setAlreadyVisited(true);
                        resultTemp[i][1] = lowerSim;
                    }
                    else
                    {
                        stepSimilarityArrayLine = getStepSimilarityArrayLine(stepSimilarityArray,
                                checkDupMatch);
                        lowerSim = getLowestStepSimilarity(stepSimilarityArrayLine);
                        stepSimilarityArray[i][lowerSim].setAlreadyVisited(true);
                        resultTemp[checkDupMatch][1] = lowerSim;
                    }
                    checkDupMatch = checkDuplicatedMatch(resultTemp, i);
                }
            }
        }
        return resultTemp;
    }

    // returns the index i in the array[i][y] where the duplicated match is found
    // returns -1 if no duplicated match is found
    private int checkDuplicatedMatch(int[][] resultTemp, int index)
    {
        int result = -1;
        int lowerSim = resultTemp[index][1];
        for (int i = 0; i < index; i++)
        {
            if (resultTemp[i][1] == lowerSim)
            {
                result = i;
                break;
            }
        }
        return result;
    }

    private int getLowestStepSimilarity(StepSimilarity[] stepSimilarityArrayLine)
    {
        int result = -1;
        StepSimilarity resultTemp = null;

        for (int i = 0; i < stepSimilarityArrayLine.length; i++)
        {
            StepSimilarity arrayItem = stepSimilarityArrayLine[i];
            if (!arrayItem.isAlreadyVisited())
            {
                if (resultTemp == null)
                {
                    resultTemp = arrayItem;
                    result = i;
                }
                else
                {
                    if (arrayItem.hasLowerSimilarityThan(resultTemp))
                    {
                        resultTemp = arrayItem;
                        result = i;
                    }
                }
            }
        }

        return result;
    }

    private StepSimilarity[] getStepSimilarityArrayLine(StepSimilarity[][] array, int line)
    {
        StepSimilarity[] stepSimilarityArrayLine = new StepSimilarity[array.length];
        for (int j = 0; j < array.length; j++)
        {
            stepSimilarityArrayLine[j] = array[line][j];
        }
        return stepSimilarityArrayLine;
    }

}