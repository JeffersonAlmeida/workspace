/*
 * @(#)TargetProjectLoadingException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Jan 6, 2007    LIBkk11577   Initial creation.
 */
package com.motorola.btc.research.target.pm.exceptions;

import java.util.ArrayList;
import java.util.Collection;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.errors.Error;

/**
 * Represents one or more errors found during the project loading.
 * 
 * <pre>
 * CLASS:
 * Comprises the list of errors found during the loading.
 * RESPONSIBILITIES:
 * It works as a message between the business layer and the GUI layer. 
 * It is used to inform the GUI layer the errors found during the project loading.
 *
 * COLABORATORS:
 * Extends the TargetException.
 * 
 * USAGE:
 * throw new TargetProjectLoadingException(errorList);
 */
public class TargetProjectLoadingException extends TargetException
{

    /** Default serial ID. */
    private static final long serialVersionUID = 1L;
    
    /** The errors found during loading. */
    private Collection<Error> errors;

    /**
     * Constructor. It builds an exception without error.
     */
    public TargetProjectLoadingException()
    {
        this.errors = new ArrayList<Error>();
    }

    /**
     * Constructor. It builds an object with the errors passed as parameter.
     * 
     * @param errorList The list with errors.
     */
    public TargetProjectLoadingException(Collection<Error> errorList)
    {
        this.errors = new ArrayList<Error>(errorList);
    }

    /**
     *  Returns the exception message, which is mounted with each error message. 
     */
    
    public String getMessage()
    {
        String message = "The following errors were found during the project loading:\n\n";

        for (Error error : this.errors)
        {

            message += "\t" + error.getDescription() + "\n";
        }
        return message;
    }

    /**
     * The Get method for errors attribute.
     * 
     * @return The list of errors.
     */
    public Collection<Error> getErrors()
    {
        return new ArrayList<Error>(this.errors);
    }

}
