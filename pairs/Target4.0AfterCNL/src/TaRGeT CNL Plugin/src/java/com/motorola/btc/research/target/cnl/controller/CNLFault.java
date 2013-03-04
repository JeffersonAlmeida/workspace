/*
 * @(#)CNLFault.java
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

import org.eclipse.jface.viewers.TextCellEditor;

import com.motorola.btc.research.target.common.ucdoc.StepId;

/**
 * This class represents a CNL process fault.
 * @author 
 *
 */
public abstract class CNLFault
{
    private String sentence;

    private StepId step;

    private String resource;
    
    private TestCaseTextType field;
    
    TextCellEditor test = null;
    
    /**
     * The class constructor.
     * @param sentence the sentence that contains the fault
     * @param step the step where the fault occurred.
     * @param resource the document that contains the fault
     * @param field the test case text type
     */
    protected CNLFault(String sentence, StepId step, String resource, TestCaseTextType field)
    {
        this.step = step;
        this.resource = resource;
        this.sentence = sentence;
        this.field = field;
    }
    
    /**
     * This method returns the sentence attribute.
     * @return the sentence attribute.
     */
    public String getSentence()
    {
        return this.sentence;
    }
    
    /**
     * This method returns the step attribute.
     * @return the step attribute.
     */
    public String getStep()
    {
        return this.step.toString();
    }

    /**
     * This method returns the resource attribute.
     * @return the resource attribute.
     */
    public String getResource()
    {
        return this.resource;
    }
    
    /**
     * This method returns the fault details.
     * @return the fault details
     */
    public abstract String getDetails();

    /**
     * This method returns the field attribute.
     * @return the field attribute.
     */
    public TestCaseTextType getField()
    {
        return this.field;
    }
}
