/*
 * @(#)ComparisonResult.java
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

import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * Represents the result of the comparison of some new test case with an old test case.
 */
public class ComparisonResult
{

    /**
     * The old test case.
     */
    private TextualTestCase oldTestCase;

    /**
     * The similarity of the old test case to a new test case.
     */
    private double similarity;
    
    //INSPECT Lais added
    /**
     * Contais a pair new-old test case step based on the levenstein distance (groups most similar steps)
     */
    private int[][] stepPairs;

    /**
     * Constructor for the Comparison Result class.
     * 
     * @param oldTestCase The old test case.
     * @param similarity The result of the comparison.
     */
    //INSPECT - Lais addition of stepPairs field
    public ComparisonResult(TextualTestCase oldTestCase, double similarity, int[][] stepPairs)
    {
        this.oldTestCase = oldTestCase;
        this.similarity = similarity;
        this.stepPairs = stepPairs;
    }

    /**
     * Gets the old test case.
     * 
     * @return The old test case.
     */
    public TextualTestCase getOldTestCase()
    {
        return oldTestCase;
    }

    /**
     * Gets the similarity between test cases.
     * 
     * @return The similarity between the test cases.
     */
    public double getSimilarity()
    {
        return similarity;
    }

    /**
     * Gets the stepPairs value.
     *
     * @return Returns the stepPairs.
     */
    public int[][] getStepPairs()
    {
        return stepPairs;
    }

    /**
     * Sets the stepPairs value.
     *
     * @param stepPairs The stepPairs to set.
     */
    public void setStepPairs(int[][] stepPairs)
    {
        this.stepPairs = stepPairs;
    }

}
