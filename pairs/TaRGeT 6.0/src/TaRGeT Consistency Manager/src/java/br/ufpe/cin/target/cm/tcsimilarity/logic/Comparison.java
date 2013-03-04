/*
 * @(#)Comparison.java
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
 * tnd783   07/07/2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cm.tcsimilarity.logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * Represents the comparison of all new test cases will all old test cases.
 * RESPONSIBILITIES:
 * 1) Store all the comparison results of each new test case.
 */
public class Comparison
{

    /**
     * A map containing all the comparison results related to each new test case.
     */
    private LinkedHashMap<TextualTestCase, List<ComparisonResult>> comparisonResults;

    /**
     * Constructs an instance of Comparison.
     */
    public Comparison()
    {
        this.comparisonResults = new LinkedHashMap<TextualTestCase, List<ComparisonResult>>();
    }

    /**
     * Adds a list of comparison results to a new test case.
     * 
     * @param newTestCase The new test case.
     * @param comparisonResults The list of comparison results.
     */
    public void addComparisonResults(TextualTestCase newTestCase,
            List<ComparisonResult> comparisonResults)
    {
        if (newTestCase != null && comparisonResults != null)
        {
            List<ComparisonResult> list = this.comparisonResults.get(newTestCase);
            if (list != null)
            {
                list.addAll(comparisonResults);
            }
            else
            {
                this.comparisonResults.put(newTestCase, comparisonResults);
            }

        }
    }

    /**
     * Gets the comparison results associated to the given test case.
     * 
     * @param testCase The new test case
     * @return A list of comparison results associated to the given new test case. Returns null if
     * the there is no comparison results associated to the given test case.
     */
    public List<ComparisonResult> getComparisonResultsByTestCase(TextualTestCase testCase)
    {
        return comparisonResults.get(testCase);
    }

    /**
     * Gets all the new test cases which have comparison results associated to them.
     * 
     * @return A list with all the new test cases which have comparison results associated to them.
     */
    public List<TextualTestCase> getNewTestCases()
    {
        return new ArrayList<TextualTestCase>(comparisonResults.keySet());
    }

}
