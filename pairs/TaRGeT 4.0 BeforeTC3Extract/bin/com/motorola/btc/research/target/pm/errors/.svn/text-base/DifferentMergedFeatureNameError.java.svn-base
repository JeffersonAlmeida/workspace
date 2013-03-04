/*
 * @(#)DifferentMergedFeatureNameError.java
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

/**
 * This class represents a warning of duplicated feature name within features that have the same ID.
 * 
 * <pre>
 * CLASS:
 * This class keeps the feature that the different name appeared.
 * The getType() implementation returns Error.WARNING
 * </pre>
 */
public class DifferentMergedFeatureNameError extends FeatureError
{
    /**
     * Constructor that receives a feature and the document name.
     * 
     * @param feature The feature where the error occurred.
     * @param documentName The name of the document where the error occurred.
     */
    public DifferentMergedFeatureNameError(Feature feature, String documentName)
    {
        super(feature, documentName);
    }

    /**
     * Method used to retrieve the error description.
     * 
     * @return The error description..
     */
    public String getDescription()
    {
        return "The feature \"" + this.feature.getName() + "\" in file " + this.getResource()
                + " has the same id of a previously loaded feature but has a different name.";
    }

    /**
     * Method used to retrieve the location where the error appeared in the resource. It returns the
     * name and id of the feature.
     * 
     * @return The id and the name of the feature.
     */
    public String getLocation()
    {
        return this.feature.getId() + " - " + this.feature.getName();
    }

    /**
     * Method used to retrieve the path of the resource, which is the absolute path of the document.
     * 
     * @return The absolute path of the document.
     */
    public String getPath()
    {
        return this.documentName;
    }

    /**
     * Gets the type of the error.
     * 
     * @return Returns the value <code>Error.WARNING</code>.
     */
    public int getType()
    {
        return Error.WARNING;
    }

}
