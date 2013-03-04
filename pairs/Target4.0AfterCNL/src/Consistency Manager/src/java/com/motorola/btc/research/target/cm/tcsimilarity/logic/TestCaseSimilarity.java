package com.motorola.btc.research.target.cm.tcsimilarity.logic;

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
    private String ucId;

    /**
     * The id of the new test case.
     */
    private String updatedId;

    /**
     * The id of the old test case.
     */
    private String originalId;

    /**
     * The value of the similarity.
     */
    private double similarity;

    /**
     * Constructor for the test case similarity object.
     * 
     * @param updatedTestCaseId The id of the new test case.
     * @param originalTestCaseId The id of the old test case.
     * @param sim The value of the similarity.
     * @param UCId The use case id.
     */
    public TestCaseSimilarity(String updatedTestCaseId, String originalTestCaseId, double sim,
            String UCId)
    {
        updatedId = updatedTestCaseId;
        originalId = originalTestCaseId;
        similarity = sim;
        ucId = UCId;
    }

    /**
     * Gets the id of the new test case.
     * 
     * @return The id of the new test case.
     */
    public String getUpdatedId()
    {
        return updatedId;
    }

    /**
     * Gets the id of the old test case.
     * 
     * @return The id of the old test case.
     */
    public String getOriginalId()
    {
        return originalId;
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
        return ucId;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "(TC " + updatedId + ", TC " + originalId + ") = " + getRealSimilarity() + "%";
    }

}
