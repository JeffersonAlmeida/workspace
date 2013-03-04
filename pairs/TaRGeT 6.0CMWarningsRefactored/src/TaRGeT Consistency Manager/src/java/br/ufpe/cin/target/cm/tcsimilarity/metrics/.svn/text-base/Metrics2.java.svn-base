/*
 * @(#)Metrics2.java
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
 * fsf2      30/07/2009                  Fixed bug in the similarity method.
 */
package br.ufpe.cin.target.cm.tcsimilarity.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufpe.cin.target.cm.tcsimilarity.logic.StepSimilarity;
import br.ufpe.cin.target.cm.tcsimilarity.logic.TestCaseSimilarity;

public class Metrics2
{

    public static String WORD_BY_WORD = "WORD BY WORD";

    public static String CHAR_BY_CHAR = "CHAR BY CHAR";

    // INSPECT LAIS - nao está sendo usado
    public List<TestCaseSimilarity> getTestCaseSimilarities(List<StepSimilarity[][]> similarity,
            String metric)
    {
        List<TestCaseSimilarity> result = new ArrayList<TestCaseSimilarity>();
        
        int[][] resultTemp = null;
        
        for (StepSimilarity[][] stepSimilarityArray : similarity)
        {
            StepSimilarity temp = stepSimilarityArray[0][0];
            resultTemp = getPairs(stepSimilarityArray);
            double sim = getResultantSimilarity(stepSimilarityArray, resultTemp, metric);
            result.add(new TestCaseSimilarity(temp.getNewTestCaseId(), temp.getOldTestCaseId(), sim, temp
                    .getUseCaseId(), resultTemp));
        }
        
        Collections.sort(result);
        return result;
    }
    
    // resultTemp[i][j]; i = line index in the array and j = index of the lowest StepSimilarity in
    // the similarity matrix
    /**
     * Calculates the resultant similarity of a test case based on the similarities  of each step. 
     * @param similarity the step similarity matrix
     * @param resultTemp the steps pairs array (resultTemp[][])
     * @param metric the metric thet was used by the Levenstein algorithm (WORD_BY_WORD or CHAR_BY_CHAR)
     * @return the resultant similarity of a test case
     */
    public double getResultantSimilarity(StepSimilarity[][] similarity, int[][] resultTemp,
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
                //INSPECT - Lais inclusão de order distance na soma similaridade
                
                double orderDistance = temp.getOrderDistance();
            	double percentualOrderDistance = orderDistance / 20;
            	result += temp.getSimilarityByWord() + percentualOrderDistance;
            }
            else if (metric.equalsIgnoreCase(CHAR_BY_CHAR))
            {
                //INSPECT - Lais inclusão da order distance na soma da similaridade
                double orderDistance = temp.getOrderDistance();
                double percentualOrderDistance = orderDistance / 20;
            	result += temp.getSimilarityByChar() + percentualOrderDistance;
            }
        }
        
        return result / resultTemp.length;
    }
    
    /**
     * Returns a matrix containing the pair [new step index] - [index of the lowest StepSimilarity in the stepSimilarityArray]
     * @param stepSimilarityArray the step similarity matrix
     * @return a matrix containing the pair [new step index] - [index of the lowest StepSimilarity in the stepSimilarityArray]
     */
    public int[][] getPairs(StepSimilarity[][] stepSimilarityArray)
    {
        int arraySize = stepSimilarityArray.length; //number of columns
        
        int[][] resultTemp = new int[arraySize][2]; // resultTemp[i][j]; i = line index in the matrix
                                                    // and j = index of the lowest StepSimilarity in
                                                    // the matrix
        //INSPECT Lais inicialização do array com -1
        for (int i = 0; i < resultTemp.length; i++)
        {
            resultTemp[i][0] = -1;
            resultTemp[i][1] = -1;
        }
        
        StepSimilarity[] stepSimilarityArrayLine = null;
        
        for (int i = 0; i < arraySize; i++)
        {
            stepSimilarityArrayLine = getStepSimilarityArrayLine(
                    stepSimilarityArray, i); // gets one array[][] line
            int lowerSimIndex = getLowestStepSimilarity(stepSimilarityArrayLine);   // gets the index of
                                                                                    // the lowest
                                                                                    // StepSimilarity in
                                                                                    // the line
            stepSimilarityArray[i][lowerSimIndex].setAlreadyVisited(true);
            resultTemp[i][0] = i;
            resultTemp[i][1] = lowerSimIndex;
            
            //INSPECT - Laís inserted code
            int duplicatedPairs [][] = this.newCheckDupMatch(resultTemp);
            
            if(duplicatedPairs[0][0] != -1){
                this.resolveConflicts(stepSimilarityArray, duplicatedPairs, resultTemp);
            }
            
            //INSPECT Lais - end of inserted code
            
            
//            int checkDupMatch = checkDuplicatedMatch(resultTemp, i); // checks if the lowest
//                                                                    // StepSimilarity index has
//                                                                    // been attributed to another
//                                                                    // position in resultTemp.
//                                                                    // Returns the index i in resultTemp[i][j] 
//                                                                    // where the duplicated match is found.
//                                                                    // Otherwise returns -1.
//            
//            //resolves duplicated match problem
//            if (checkDupMatch != -1)
//            {
//                while (checkDupMatch != -1)
//                {
//                    int col = resultTemp[checkDupMatch][1];
//                    StepSimilarity dup = stepSimilarityArray[checkDupMatch][col];
//                    StepSimilarity lowestStepSimilarity = stepSimilarityArrayLine[lowerSimIndex];
//                    if (dup.hasLowerSimilarityThan(lowestStepSimilarity))
//                    {
//                        lowerSimIndex = getLowestStepSimilarity(stepSimilarityArrayLine);
//                        stepSimilarityArray[i][lowerSimIndex].setAlreadyVisited(true);
//                        resultTemp[i][1] = lowerSimIndex;
//                    }
//                    else
//                    {
//                        stepSimilarityArrayLine = getStepSimilarityArrayLine(stepSimilarityArray,
//                                checkDupMatch);
//                        lowerSimIndex = getLowestStepSimilarity(stepSimilarityArrayLine);
//                        stepSimilarityArray[i][lowerSimIndex].setAlreadyVisited(true);
//                        resultTemp[checkDupMatch][1] = lowerSimIndex;
//                    }
//                    checkDupMatch = checkDuplicatedMatch(resultTemp, i);
//                }
//            }
            
            
            
        }
        return resultTemp;
    }

    //INSPECT LAIS - new methods
        
    /**
     * Resolves conflicts in the steps pairs array (resultTemp[][]).
     * @param stepSimilarityMatrix the step similarity matrix
     * @param duplicatedPairs the duplicated pairs in the steps pairs array (resultTemp[][]).
     * @param resultTemp the steps pairs array (resultTemp[][]) without conflicts.
     */
    private void resolveConflicts(StepSimilarity[][] stepSimilarityMatrix, int[][] duplicatedPairs,
            int[][] resultTemp)
    {
        while (duplicatedPairs[0][0] != -1)
        {
            int duplicatedStep1Line = duplicatedPairs[0][0];
            int duplicatedStep1Column = duplicatedPairs[0][1];
            StepSimilarity duplicatedStep1 = stepSimilarityMatrix[duplicatedStep1Line][duplicatedStep1Column];

            int duplicatedStep2Line = duplicatedPairs[1][0];
            int duplicatedStep2Column = duplicatedPairs[1][1];
            StepSimilarity duplicatedStep2 = stepSimilarityMatrix[duplicatedStep2Line][duplicatedStep2Column];
            
            int lowerSimilarityIndex = -1;
            
            if (duplicatedStep1.hasLowerSimilarityThan(duplicatedStep2))
            {
                lowerSimilarityIndex = this.getLowestStepSimilarity(this.getStepSimilarityArrayLine(stepSimilarityMatrix, duplicatedStep2Line));
                stepSimilarityMatrix[duplicatedStep2Line][lowerSimilarityIndex].setAlreadyVisited(true);
                resultTemp[duplicatedStep2Line][1] = lowerSimilarityIndex;
            }
            else
            {
                lowerSimilarityIndex = this.getLowestStepSimilarity(this.getStepSimilarityArrayLine(stepSimilarityMatrix, duplicatedStep1Line));
                stepSimilarityMatrix[duplicatedStep1Line][lowerSimilarityIndex].setAlreadyVisited(true);
                resultTemp[duplicatedStep1Line][1] = lowerSimilarityIndex;
            }
            
            
            duplicatedPairs = this.newCheckDupMatch(resultTemp);

        }
    }
    
    /**
     * Checks if  the steps pairs array (resultTemp[][]) contains duplicated entries.
     * @param resultTemp the steps pairs array (resultTemp[][])
     * @return the duplicated pairs in the steps pairs array (resultTemp[][]) if they exists. 
     * Otherwise returns an array with -1 values in all positions.
     */
    private int[][] newCheckDupMatch(int[][] resultTemp){
        int[][] duplicatedPairs = new int[][] {new int[]{-1, -1}, new int[]{-1, -1}};
        
        for(int i = 0; i < resultTemp.length && i != -1; i++){
            for (int j = 0; j < resultTemp.length; j++) {
                if((resultTemp[j][1] != -1) && (resultTemp[j][1] == resultTemp[i][1]) && (i != j)){
                    duplicatedPairs[0][0] = i;
                    duplicatedPairs[0][1] = resultTemp[i][1];
                    duplicatedPairs[1][0] = j;
                    duplicatedPairs[1][1] = resultTemp[j][1];
                    
                    j = resultTemp.length;
                    i = resultTemp.length;
                }
            }
        }
        
        return duplicatedPairs;
    }
    
    //INSPECT Lais - End of inserted code
    
    
    /**
     * Returns the index of the position in the stepSimilarityArrayLine that has the lowest step similarity.
     * @param stepSimilarityArrayLine the array that will be verified.
     * @return the index of the position in the stepSimilarityArrayLine that has the lowest step similarity
     */
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
        
        //INSPECT Felype - Estava faltando marcar o StepSimilarity como visitado.
        resultTemp.setAlreadyVisited(true);

        return result;
    }
    
    /**
     * 
     * Returns a line of the similarity matrix according to the given index.
     * @param array the similarity matrix.
     * @param line index of the line to be returned. 
     * @return a line of the similarity matrix according to the given index.
     */
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
