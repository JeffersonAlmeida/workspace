/*
 * @(#)InvalidStepIdReferenceError.java
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
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * dhq348   Feb 15, 2007    LIBll27713   Rework of inspection LX144782.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   Jul 03, 2007    LIBmm63120   Modification according to CR.
 */
package com.motorola.btc.research.target.pm.errors;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.util.FileUtil;

/**
 * This class represents an error of invalid step ID reference.
 * 
 * <pre>
 * CLASS:
 * The class contains the attribute <code>documentName</code>, which represents the name of the 
 * document where the error is located, and the attribute <code>feature</code>, which represents the 
 * feature that contains the use case with invalid step ID reference. The use case is represented by the attribute <code>useCase</code>,
 * and the invalid step ID reference is represented by the attribute <code>stepId</code>.
 */
public class InvalidStepIdReferenceError extends UseCaseError
{
    /** The invalid step ID reference */
    private StepId stepId;

    /**
     * Builds the invalid step Id reference error. It receives the feature and the use case with
     * invalid step Id reference. It also receives the name of the document where the error is
     * located.
     * 
     * @param feature The feature that contains the use case.
     * @param useCase The use case with invalid step Id reference.
     * @param stepId The invalid step Id reference.
     * @param documentName The name of the document.
     */
    public InvalidStepIdReferenceError(Feature feature, UseCase useCase, StepId stepId,
            String documentName)
    {
        super(feature, useCase, documentName);
        this.stepId = stepId;
    }

    /**
     * Method used to retrieve the error description.
     * 
     * @return The error description..
     */
    public String getDescription()
    {
        return "The use case \"" + this.useCase.getId() + "\" - \"" + this.useCase.getName()
                + "\", in the document \"" + FileUtil.getFileName(this.documentName)
                + "\", contains a reference to the step \"" + stepId
                + "\" that does not exist. See the feature \"" + this.feature.getId() + "\" - "
                + "\"" + this.feature.getName() + "\".";
    }

    /**
     * Method used to retrieve the location where the error appeared in the resource. It returns the
     * name and id of the use case.
     * 
     * @return The id and the name of the use case.
     */
    public String getLocation()
    {
        return this.useCase.getId() + " - " + this.useCase.getName();
    }

    /**
     * Method used to retrieve the path of the resource. It is the absolute path of the document.
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
     * @return Returns the value <code>Error.ERROR</code>.
     */
    public int getType()
    {
        return Error.ERROR;
    }

    /**
     * Gets the step id.
     * 
     * @return The value of the step id attribute.
     */
    public StepId getStepId()
    {
        return stepId;
    }

    /**
     * Verifies the equality of the attributes <code>documentName</code>, <code>stepId</code>,
     * <code>feature</code> and <code>useCase</code>.
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof InvalidStepIdReferenceError)
        {
            InvalidStepIdReferenceError error = (InvalidStepIdReferenceError) obj;

            result = error.documentName.equals(this.documentName)
                    && error.feature.getId().equals(this.feature.getId())
                    && error.stepId.equals(this.stepId)
                    && error.useCase.getId().equals(this.useCase.getId());
        }
        return result;
    }
}
