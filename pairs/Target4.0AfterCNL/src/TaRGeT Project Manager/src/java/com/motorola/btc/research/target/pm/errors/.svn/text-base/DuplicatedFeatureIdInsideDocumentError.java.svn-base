/*
 * @(#)DuplicatedFeatureIdError.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Jan 29, 2007    LIBll12753   Initial creation.
 * wdt022   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   Jun 5, 2007     LIBmm47221   Modification according to CR. Noew the class extends from 
 */
package com.motorola.btc.research.target.pm.errors;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class represents an error of feature ID duplication inside a single document.
 * 
 * <pre>
 * CLASS:
 * The class contains the attribute <code>documentName</code>, which represents the name of the 
 * document where the error is located, and the attribute <code>feature</code>, which represents the 
 * feature with duplicated ID.
 */
public class DuplicatedFeatureIdInsideDocumentError extends FeatureError
{
    /**
     * Builds the duplicated feature ID error. It receives the feature with error and the name of
     * the document where the feature is located.
     * 
     * @param feature The feature with the duplicated ID.
     * @param documentName The name of the document.
     */
    public DuplicatedFeatureIdInsideDocumentError(Feature feature, String documentName)
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
        return "There is one or more features with Id \"" + this.feature.getId()
                + "\" in the document \"" + FileUtil.getFileName(this.documentName) + "\".";
    }

    /**
     * Method used to retrieve the location where the error appeared in the resource. It returns the
     * name and id of the feature.
     * 
     * @return The id and the name of the feature.
     */
    public String getLocation()
    {
        return "Feature " + feature.getId() + " - " + feature.getName();
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
     * Method used to retrieve the resource. It returns the name of the document where the
     * duplicated ID appeared.
     * 
     * @return The document name.
     */
    public String getResource()
    {
        return FileUtil.getFileName(this.documentName);
    }

    /**
     * Gets the type of the error.
     * 
     * @return Returns the value <code>Error.FATAL_ERROR</code>.
     */
    public int getType()
    {
        return Error.FATAL_ERROR;
    }

    /**
     * Verifies the equality of the attributes <code>documentName</code> and <code>feature</code>.
     */

    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof DuplicatedFeatureIdInsideDocumentError)
        {
            DuplicatedFeatureIdInsideDocumentError error = (DuplicatedFeatureIdInsideDocumentError) obj;

            result = error.documentName.equals(this.documentName)
                    && error.feature.getId().equals(this.feature.getId());
        }

        return result;
    }
}
