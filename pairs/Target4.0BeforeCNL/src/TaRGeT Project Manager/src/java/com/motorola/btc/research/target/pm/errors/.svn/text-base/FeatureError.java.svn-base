/*
 * @(#)FeatureError.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jun 5, 2007    LIBmm47221   Initial creation.
 * dhq348    Jul 11, 2007   LIBmm47221   Rework after inspection LX185000.
 */
package com.motorola.btc.research.target.pm.errors;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This abstract class represents a generic feature error.
 * 
 * <pre>
 * CLASS:
 * It keeps a reference to the feature and the name of the document 
 * where the error appeared.
 * </pre>
 */
public abstract class FeatureError implements Error
{
    /** The name of the document that contains the feature */
    protected String documentName;

    /** The feature with error */
    protected Feature feature;

    /**
     * Constructor that receives a feature and the document name.
     * 
     * @param feature The feature where the error occurred.
     * @param documentName The name of the document where the error occurred.
     */
    public FeatureError(Feature feature, String documentName)
    {
        this.documentName = documentName;
        this.feature = feature;
    }

    /**
     * The get method for <code>documentName</code> attribute.
     * 
     * @return Returns the documentName.
     */
    public String getDocumentName()
    {
        return documentName;
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
     * Method used to retrieve the resource. It returns the name of the document where the
     * duplicated ID appeared.
     * 
     * @return The document name.
     */
    public String getResource()
    {
        return FileUtil.getFileName(this.documentName);
    }
}
