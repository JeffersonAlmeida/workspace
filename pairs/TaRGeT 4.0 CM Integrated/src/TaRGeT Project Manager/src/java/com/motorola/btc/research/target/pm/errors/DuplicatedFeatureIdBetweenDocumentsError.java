/*
 * @(#)DuplicatedFeatureIdBetweenDocumentsError.java
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
 */
package com.motorola.btc.research.target.pm.errors;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class represents an error of feature ID duplication between two documents.
 * 
 * <pre>
 * CLASS:
 * The class contains the attribute <code>documentName</code>, which represents the name of the 
 * document where the error is located, and the attribute <code>feature</code>, which represents the 
 * feature with duplicated ID.
 */
public class DuplicatedFeatureIdBetweenDocumentsError implements Error
{

    /** The name of one of the documents that contains the feature with duplicated ID */
    private String document1Name;

    /** The name of the other document */
    private String document2Name;

    /** The feature that has the duplicated ID */
    private Feature feature;


    /**
     * Method used to retrieve the error description.
     * 
     * @return The error description.
     */
    public String getDescription()
    {
        return "The documents \"" + FileUtil.getFileName(this.document1Name) + "\" and \""
                + FileUtil.getFileName(this.document2Name)
                + "\" contain a feature with same Id \"" + this.feature.getId() + "\".";
    }

    /**
     * Method used to retrieve the location where the error appears in the resource. It returns the
     * name and id of the feature.
     * 
     * @return The id and the name of the feature.
     */
    public String getLocation()
    {
        return "Feature " + feature.getId() + " - " + feature.getName();
    }

    /**
     * Method used to retrieve the path of the resource. It is the absolute path of the document.
     * 
     * @return The absolute path of the document.
     */
    public String getPath()
    {
        return this.document1Name;
    }

    /**
     * Method used to retrieve the resource. It returns the name of the document where the
     * duplicated ID appeared.
     * 
     * @return The document name.
     */
    public String getResource()
    {
        return FileUtil.getFileName(this.document1Name);
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
     * Verifies the equality of the attributes <code>document1Name</code>,
     * <code>document2Name</code> and <code>feature</code>.
     */
    
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof DuplicatedFeatureIdBetweenDocumentsError)
        {
            DuplicatedFeatureIdBetweenDocumentsError error = (DuplicatedFeatureIdBetweenDocumentsError) obj;

            result = error.document1Name.equals(this.document1Name)
                    && error.document2Name.equals(this.document2Name)
                    && error.feature.getId().equals(this.feature.getId());
        }

        return result;
    }
}
