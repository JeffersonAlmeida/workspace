/*
 * @(#)TestCaseSimilarity.java
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

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *  Represents the similarity between two test cases.
 */
public class TestCaseSimilarity implements Comparable<TestCaseSimilarity>
{

    /**
     * The use case id.
     */
    //INSPECT
    private String useCaseId;

    /**
     * The id of the new test case.
     */
    //INSPECT
    private String newTestCaseId;

    /**
     * The id of the old test case.
     */
    //INSPECT
    private String oldTestCaseId;

    /**
     * The value of the similarity.
     */
    private double similarity;
    
    //INSPECT Lais added
    /**
     * Contais a pair new-old test case step based on the levenstein distance (groups most similar steps)
     */
    private int[][] stepPairs;
    
    /**
     * Constructor for the test case similarity object.
     * 
     * @param updatedTestCaseId The id of the new test case.
     * @param originalTestCaseId The id of the old test case.
     * @param sim The value of the similarity.
     * @param UCId The use case id.
     */
    //INSPECT - Lais addition of stepPairs field
    public TestCaseSimilarity(String updatedTestCaseId, String originalTestCaseId, double sim,
            String UCId, int[][] stepPairs)
    {
        this.newTestCaseId = updatedTestCaseId;
        this.oldTestCaseId = originalTestCaseId;
        this.similarity = sim;
        this.useCaseId = UCId;
        this.stepPairs = stepPairs;
    }

    /**
     * Gets the id of the new test case.
     * 
     * @return The id of the new test case.
     */
    public String getUpdatedId()
    {
        return newTestCaseId;
    }

    /**
     * Gets the id of the old test case.
     * 
     * @return The id of the old test case.
     */
    public String getOriginalId()
    {
        return oldTestCaseId;
    }

    /**
     * Gets the value of the similarity.
     * 
     * @return The value of the similarity.
     */
    public double getSimilarity()
    {
        return similarity;
    }

    /**
     * Gets the use case id.
     * 
     * @return The use case id.
     */
    public String getUcId()
    {
        return useCaseId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    
    public int compareTo(TestCaseSimilarity o)
    {
        if (this.similarity < o.getSimilarity())
        {
            return -1;
        }
        else if (this.similarity > o.getSimilarity())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Computes the similarity percentage.
     * 
     * @return The similarity percentage.
     */
    public double getRealSimilarity()
    {
        double result = (1 - this.similarity) * 100;
        BigDecimal big = new BigDecimal(result).setScale(2, RoundingMode.UP);
        return big.doubleValue();
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "(TC " + newTestCaseId + ", TC " + oldTestCaseId + ") = " + getRealSimilarity() + "%";
    }

}
