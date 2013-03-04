/*
 * @(#)TextualStep.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Jan 8, 2007    LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modifications after inspection (LX135035).
 */
package com.motorola.btc.research.target.tcg.extractor;

import com.motorola.btc.research.target.common.ucdoc.StepId;

/**
 * Represents a textual test case step.
 * 
 * <pre>
 * CLASS:
 * Comprehends the step actions and its respective expect results.
 * 
 * RESPONSIBILITIES:
 * 1) Store the information of a unique test case step.
 * 
 * COLABORATORS:
 * 1) It is used by the TextualTestCase class
 * 
 * USAGE:
 * not applicable
 */
public class TextualStep
{

    /** Represents the value of a system state that is undefined */
    public static final String UNDEFINED_STATE = "";

    /** Represents the system state that allows the user to perform the action */
    private String systemState;

    /** Represents the user action */
    private String action;

    /** Represents the system response to the user action */
    private String response;

    /** Represents the id of the step */
    private StepId id;

    /**
     * Builds a textual step from an action and its response.
     * 
     * @param action The user action that stimulates the system under test.
     * @param response The system response to the user action.
     * @param id The step id.
     */
    public TextualStep(String action, String response, StepId id)
    {

        this.systemState = UNDEFINED_STATE;
        this.action = action;
        this.response = response;
        this.id = id;
    }

    /**
     * Builds a textual step from a system state, an action and its response.
     * 
     * @param systemState The system condition that allows the user to perform the given action of
     * the test step.
     * @param action The user action that stimulates the system under test.
     * @param response The system response to the user action. *
     * @param id The step id.
     */
    public TextualStep(String systemState, String action, String response, StepId id)
    {
        this.systemState = systemState;
        this.action = action;
        this.response = response;
        this.id = id;
    }

    /**
     * Get method for <code>action</code> attribute
     * 
     * @return The user action
     */
    public String getAction()
    {
        return this.action;
    }

    /**
     * Get method for <code>response</code> attribute
     * 
     * @return The system response to the user action
     */
    public String getResponse()
    {
        return this.response;
    }

    /**
     * Get method for the <code>systemState</code> attribute.
     * 
     * @return Returns the system state.
     */
    public String getSystemState()
    {
        return systemState;
    }

    /**
     * Get method for the <code>id</code> attribute.
     * 
     * @return Returns the id.
     */
    public StepId getId()
    {
        return id;
    }
}
