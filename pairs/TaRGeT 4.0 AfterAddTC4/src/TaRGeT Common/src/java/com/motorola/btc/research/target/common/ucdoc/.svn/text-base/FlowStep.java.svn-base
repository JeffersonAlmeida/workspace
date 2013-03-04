/*
 * @(#)FlowStep.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Aug 15, 2006    none  		 Initial creation.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications and refactorings.
 * dhq348   Aug 23, 2007    LIBmm42774   Added method equals().
 * dhq348   Nov 26, 2007    LIBoo10574   Added the setup attribute.
 * dhq348   Jan 23, 2008    LIBoo10574   Rework after inspection LX229632.
 */
package com.motorola.btc.research.target.common.ucdoc;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * CLASS:
 * Represents a step in use case flow.
 *
 * RESPONSIBILITIES:
 * Encapsulates a single step in use case flow
 * 
 * </pre>
 */
public class FlowStep
{

    /** The id of the step */
    private StepId id;

    /** The user action */
    private String userAction;

    /** The system condition that allows the user action to be perfomed */
    private String systemCondition;

    /** The system response to the user action */
    private String systemResponse;

    /** A string containing the user action */
    private Set<String> relatedRequirements;

    /**
     * The flow step setup
     */
    private String setup;

    /**
     * This constructor initializes all flow step attributes.
     * 
     * @param id Step id
     * @param action Step user action
     * @param condition Step system condition
     * @param response Step system responses
     * @param setup the <code>setup</code> of the current flow step
     * @param relatedRequirements The requirements that are related to this step
     */
    public FlowStep(StepId id, String action, String condition, String response, String setup,
            Set<String> relatedRequirements)
    {
        this.id = id;
        userAction = action;
        systemCondition = condition;
        systemResponse = response;
        this.setup = setup;
        this.relatedRequirements = new HashSet<String>(relatedRequirements);
    }

    /**
     * Constructor that does not receive any setup data.
     * 
     * @param id Step id
     * @param action Step user action
     * @param condition Step system condition
     * @param response Step system responses
     * @param relatedRequirements The requirements that are related to this step
     */
    public FlowStep(StepId id, String action, String condition, String response,
            Set<String> relatedRequirements)
    {
        this(id, action, condition, response, "", relatedRequirements);
    }

    /**
     * Get method for <code>id</code> attribute.
     * 
     * @return Returns the flow step id.
     */
    public StepId getId()
    {
        return id;
    }

    /**
     * Get method for <code>systemCondition</code> attribute.
     * 
     * @return Returns the condition string.
     */
    public String getSystemCondition()
    {
        return systemCondition;
    }

    /**
     * Get method for <code>systemResponse</code> attribute.
     * 
     * @return Returns the system response string.
     */
    public String getSystemResponse()
    {
        return systemResponse;
    }

    /**
     * Get method for <code>userAction</code> attribute.
     * 
     * @return Returns the user action string.
     */
    public String getUserAction()
    {
        return userAction;
    }

    /**
     * Get method for <code>relatedRequirements</code> attribute.
     * 
     * @return Returns the the requirements related to the step.
     */
    public Set<String> getRelatedRequirements()
    {
        return new HashSet<String>(relatedRequirements);
    }

    /**
     * It calls the id attribute toString.
     */
    public String toString()
    {
        return this.getId().toString();
    }

    /**
     * Compares two steps based on their ids.
     */

    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof FlowStep)
        {
            FlowStep newFlowStep = (FlowStep) obj;
            if (newFlowStep.id.equals(this.id))
            {
                result = true;
            }
        }

        return result;
    }

    /**
     * @return The setup of the current flow step.
     */
    public String getSetup()
    {
        return setup;
    }
}
