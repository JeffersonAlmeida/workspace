/*
 * @(#)ConsistencyManagerUtil.java
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
 * faas      15/08/2008                  Initial creation.
 * fsf2      15/08/2008                  Changed the real similarity criteria to
 *                                       show the result.
 */

package br.ufpe.cin.target.cm.util;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.target.cm.tcsimilarity.logic.ComparisonResult;
import br.ufpe.cin.target.cm.tcsimilarity.logic.TestCaseSimilarity;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * This class provides utility functions to the Consistency Management plug-in.
 */
public class ConsistencyManagerUtil
{
    /**
     * Converts a list of test similarity into a list of comparison results.
     * 
     * @param similarityList The similarity list to be converted.
     * @param oldTestCases The list of old test cases related to the similarity list.
     * @return A list of comparison results.
     */
    public static List<ComparisonResult> getComparisonResults(
            List<TestCaseSimilarity> similarityList, List<TextualTestCase> oldTestCases)
    {
        List<ComparisonResult> comparisonResults = new ArrayList<ComparisonResult>();
        for (TestCaseSimilarity similarity : similarityList)
        {
            String oldTestCaseId = similarity.getOriginalId();
            TextualTestCase oldTestCase = getTestCase(oldTestCases, oldTestCaseId);
            double realSimilarity = similarity.getRealSimilarity();
            int[][] stepPairs = similarity.getStepPairs();
            
            /*if (realSimilarity < 100)
            {*/
                ComparisonResult comparisonResult = new ComparisonResult(oldTestCase,
                        realSimilarity, stepPairs);
                comparisonResults.add(comparisonResult);
            /*}*/
        }

        return comparisonResults;
    }

    /**
     * Searches for a textual test case in the given list which has the given id.
     * 
     * @param list The list to perform the search.
     * @param id The id of the test case which is being searched.
     * @return The test case in case it is found. If it is not found, the return value is null.
     */
    private static TextualTestCase getTestCase(List<TextualTestCase> list, String id)
    {
        TextualTestCase testCase = null;

        for (TextualTestCase current : list)
        {
            if (current.getId() == Integer.parseInt(id))
            {
                testCase = current;
                break;
            }
        }

        return testCase;
    }
}
