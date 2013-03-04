/*
 * @(#)TargetDocument.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   May 17, 2007    LIBmm25975   Initial creation.
 * dhq348   Jun 06, 2007    LIBmm25975   Rework after first meeting of inspection LX179934.
 * dhq348   Aug 17, 2007    LIBmm93130   Modifications according to CR.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 */
package com.motorola.btc.research.target.pm.search;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;

/**
 * Represents a search result. This class contains all relevant information to identify the searched
 * use case. The information contained here is displayed to the user.
 * 
 * <pre>
 * CLASS:
 * This class  contains the document index number, the score of the search result, 
 * the document name, the feature and the use case that the current result represents.
 * </pre>
 */
public class TargetIndexDocument
{

    /**
     * The number of the result in the index.
     */
    private int indexNumber;

    /**
     * The score of the current result.
     */
    private float score;

    /**
     * The name of the document that the result occurs.
     */
    private String documentName;

    /**
     * The use case that the result occurs.
     */
    private UseCase usecase;

    /**
     * The feature that the result occurs.
     */
    private Feature feature;

    /**
     * The query that generated this result
     */
    private String query;

    /**
     * Creates an indexed document given an <code>usecase</code>, a <code>feature</code> and a
     * <code>documentName</code>.
     * 
     * @param usecase The use case where the result was found.
     * @param feature The feature where the result was found
     * @param documentName The name of the feature where the result was found.
     * @param query The query that generated this result
     * @param indexNumber The number of the result in the index
     * @param score The score of the current result
     */
    public TargetIndexDocument(UseCase usecase, Feature feature, String documentName, String query,
            int indexNumber, float score)
    {
        this.usecase = usecase;
        this.feature = feature;
        this.documentName = documentName;
        this.query = query;
        this.indexNumber = indexNumber;
        this.score = score;
    }

    /**
     * Returns the number of the result in the index.
     * 
     * @return The number of the result in the index.
     */
    public int getIndexNumber()
    {
        return indexNumber;
    }

    /**
     * Returns the score of the current result.
     * 
     * @return The score of the current result.
     */
    public float getScore()
    {
        return score;
    }

    /**
     * Returns the name of the document that the result occurs.
     * 
     * @return The name of the document that the result occurs.
     */
    public String getDocumentName()
    {
        return documentName;
    }

    /**
     * Returns the feature returned in the result.
     * 
     * @return The returned feature.
     */
    public Feature getFeature()
    {
        return feature;
    }

    /**
     * Returns the use case returned in the result.
     * 
     * @return The use case returned.
     */
    public UseCase getUsecase()
    {
        return usecase;
    }

    /**
     * Returns the query that generated this result.
     * 
     * @return The query that generated this result.
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * Verifies if the attributes: usecase.getName(), usecase.getId(), feature.getId() and
     * documentName are the same for the current object and <code>obj</code>.
     * 
     * @param obj The object to be verified.
     */

    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof TargetIndexDocument)
        {
            TargetIndexDocument doc = (TargetIndexDocument) obj;
            if (doc.usecase.getName() == this.usecase.getName()
                    && doc.usecase.getId() == this.usecase.getId()
                    && doc.feature.getId() == this.feature.getId()
                    && doc.documentName == this.documentName)
            {
                result = true;
            }
        }
        return result;
    }

    /**
     * Provides the hashcode based on the sum of the hash code of the attributes: usecase.getName(),
     * usecase.getId(), feature.getId() and documentName.
     * 
     * @return The hash code for the object.
     */

    public int hashCode()
    {
        int result = 0;

        result += this.usecase.getName().hashCode();
        result += this.usecase.getId().hashCode();
        result += this.feature.getId().hashCode();
        result += this.documentName.hashCode();

        return result;
    }
}
