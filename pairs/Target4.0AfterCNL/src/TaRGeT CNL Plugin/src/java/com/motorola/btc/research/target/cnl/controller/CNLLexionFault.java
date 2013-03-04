/*
 * @(#)CNLLexionFault.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   Mar 12, 2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cnl.controller;

import com.motorola.btc.research.target.common.ucdoc.StepId;

/**
 * This method represents a CNL lexion fault.
 * @author
 *
 */
public class CNLLexionFault extends CNLFault
{
    private String details;
    
    /**
     * Class constructor.
     * @param sentence the sentence that contains the fault
     * @param step the step where the fault occurred.
     * @param resource the document that contains the fault
     * @param field the test case text type
     * @param details some details about the fault
     */
    protected CNLLexionFault(String sentence, StepId step, String resource, TestCaseTextType field, String details)
    {
        super(sentence, step, resource, field);
        
        this.details = details;  
    }

    /**
     * This method returns the fault details.
     * @return the fault details
     */
    public String getDetails()
    {
        return this.details;
    }

}
