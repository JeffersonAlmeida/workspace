/*
 * @(#)UseCaseError.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Feb 15, 2007    LIBll27713   Initial creation. Created after LX144782 inspection.
 */
package com.motorola.btc.research.target.pm.errors;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;


/**
 * This abstract class encapsulates code that are common to all use case errors.
 * 
 * <pre>
 * CLASS:
 * Besides the use case with error, the class stores the name of the document 
 * that contains the feature in which the use case is included.
 *   
 */
public abstract class UseCaseError implements Error
{
    /** The name of the document that contains the feature */
    protected String documentName;

    /** The feature that contains the use case with duplicated ID */
    protected Feature feature;

    /** The use case with duplicated ID */
    protected UseCase useCase;

    /**
     * Receives the document name, feature and use case.
     * 
     * @param feature The feature where the error occurred.
     * @param useCase The use case where the error occurred.
     * @param documentName The name of the document where the error occurred.
     */
    public UseCaseError(Feature feature, UseCase useCase, String documentName)
    {
        this.documentName = documentName;
        this.feature = feature;
        this.useCase = useCase;
    }


    /**
     * The get method for <code>feature</code> attribute.
     * 
     * @return Returns the feature.
     */
    public Feature getFeature()
    {
        return feature;
    }

    /**
     * The get method for <code>useCase</code> attribute.
     * 
     * @return Returns the useCase.
     */
    public UseCase getUseCase()
    {
        return useCase;
    }
}
